package classes.managers;

import classes.CollisionMasks;
import classes.factories.ShapeCreator;
import classes.factories.ShapeHelper;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.playable.Spectator;
import classes.gameobjects.playable.WaveSpawnerPlayer;
import classes.gameobjects.unplayble.Laser;
import classes.gameobjects.unplayble.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameManager;
import interfaces.IGameObject;
import screens.MainScreen;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static classes.Constants.*;

@SuppressWarnings("ALL")
public class GameManager implements IGameManager
{
    // Managers
    protected MainScreen mainScreen;
    protected WorldManager worldManager;
    protected SpaceShipTexturesHelper spaceShipTexturesHelper;
    protected ShapeHelper shapeHelper;
    protected float accumulator;

    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();

    // Gameobjects
    protected List<IGameObject> gameObjects;

    protected SpaceShip player;
    protected WaveSpawnerPlayer waveSpawnerPlayer;
    protected Spectator spectator;

    // Online stuff
    protected playerType type;

    public GameManager(
            playerType type,
            MainScreen mainScreen)
    {
        this.worldManager = new WorldManager();
        this.spaceShipTexturesHelper = new SpaceShipTexturesHelper();
        shapeHelper = new ShapeHelper(worldManager.world);
        gameObjects = new CopyOnWriteArrayList<>();

        this.type = type;
        this.mainScreen = mainScreen;

        spawnDefault();
    }

    public void spawnDefault()
    {
        switch (this.type)
        {
            case Destroyer:
                createPlayer(new Vector2(0, 0), 0f);
                break;
            case Spawner:
                createSpawnerPlayer();
                break;
            case Spectator:
                // TODO add spectator
                break;
        }

        Sprite p = new Sprite(new Texture(Gdx.files.internal("core/assets/textures/planets/planet1.png")), 54, 54, 192, 192);

        Planet planet = new Planet(new Vector2(-500,0), 0f, p);

        ShapeCreator sc = new ShapeCreator(worldManager.world);
        Fixture fixture = shapeHelper.CreateCircleStatic(planet);
        fixture.setFilterData(CollisionMasks.ENEMY_FILTER);
        planet.setFixture(fixture);
        fixture.setUserData(planet);



        gameObjects.add(planet);
    }


    public void createSpawnerPlayer()
    {
        createSpawnerPlayer(new Vector2(0, 0));
    }

    public void createSpawnerPlayer(Vector2 pos)
    {
        waveSpawnerPlayer = new WaveSpawnerPlayer(this);
        waveSpawnerPlayer.setPosition(pos);

        gameObjects.add(waveSpawnerPlayer);
    }

    @Override
    public IGameObject createObject(IGameObject object)
    {
        return null;
    }

    @Override
    public void deleteObject(IGameObject object)
    {

    }

    @Override
    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
        for (IGameObject gameObject : gameObjects)
        {
            gameObject.update();

            if (gameObject.isToDelete())
            {
                // Deleting this body is useful
                System.out.println("Deleting object local " + gameObject.getID());
                worldManager.world.destroyBody(gameObject.getFixture().getBody());
                gameObjects.remove(gameObject);
            }
        }
    }

    @Override
    public Laser fireLaser(
            Vector2 pos,
            float speed,
            float rotation)
    {
        Laser laser = new Laser(new Vector2(pos), rotation, speed);
        Fixture fixture = shapeHelper.CreateCube(laser);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);

        laser.setFixture(fixture); // TODO refactor this.
        laser.setSpeed(speed);

        fixture.setUserData(laser);

        gameObjects.add(laser);
        return laser;
    }

    public SpaceShip createPlayer(Vector2 pos)
    {
        return createPlayer(pos, 0);
    }

    public SpaceShip createPlayer(
            Vector2 pos,
            float rotation)
    {
        SpaceShip ship = new SpaceShip(pos, rotation, spaceShipTexturesHelper.getSpaceShipSprite(1));
        ship.setGameManager(this);

        Fixture fixture = shapeHelper.CreateCube(ship);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);
        ship.setFixture(fixture);
        fixture.setUserData(ship);

        gameObjects.add(ship);
        player = ship;
        return ship;
    }

    public SpaceShipEnemy createEnemy(Vector2 pos)
    {
        return createEnemy(pos, 0f);
    }

    public SpaceShipEnemy createEnemy(
            Vector2 pos,
            float rotation)
    {
        SpaceShipEnemy enemy = new SpaceShipEnemy(pos, rotation, spaceShipTexturesHelper.getSpaceShipSprite(16), null);

        Fixture fixture = shapeHelper.CreateCube(enemy);
        fixture.setFilterData(CollisionMasks.ENEMY_FILTER);
        enemy.setFixture(fixture);
        fixture.setUserData(enemy);

        gameObjects.add(enemy);
        return enemy;
    }

    @Override
    public void draw(Batch batch)
    {
        for (IGameObject go : gameObjects)
        {
            go.Draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer)
    {
        for (IGameObject go : gameObjects)
        {
            go.Draw(shapeRenderer);
        }
    }

    @Override
    public List<IGameObject> getObjects()
    {
        return null;
    }

    public Spectator getSpectator()
    {
        return spectator;
    }

    private void doPhysicsStep(float deltaTime)
    {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP)
        {
            worldManager.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

            accumulator -= TIME_STEP;
        }
    }

    public SpaceShip getPlayer()
    {
        return player;
    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }

    public void dispose()
    {
        for (IGameObject go : gameObjects)
        {
            //TODO dispose all game objects
        }
    }

    public playerType getPlayerType()
    {
        return type;
    }

    public WaveSpawnerPlayer getWaveSpawnerPlayer()
    {
        return waveSpawnerPlayer;
    }

    public MainScreen getMainScreen()
    {
        return mainScreen;
    }

    public enum playerType
    {
        Destroyer,
        Spawner,
        Spectator
    }
}
