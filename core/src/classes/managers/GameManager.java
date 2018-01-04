package classes.managers;

import classes.gameobjects.GameObject;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameObject;

import java.util.ArrayList;
import java.util.List;

import static classes.Constants.*;

public class GameManager
{
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private SceneManager sceneManager;
    private ShapeFactory shapeFactory;
    private float accumulator;

    private List<IGameObject> gameObjects;

    private SpaceShip player;

    public GameManager()
    {
        this.worldManager = new WorldManager();
        this.spaceShipTexturesHelper = new SpaceShipTexturesHelper();
        this.sceneManager = new SceneManager();
        shapeFactory = new ShapeFactory(worldManager.world);
        gameObjects = new ArrayList<>();

        createPlayer();
        Fixture f = shapeFactory.CreateCube((int) getPlayer().getPosition().x, (int) getPlayer().getPosition().y, (int) getPlayer().getSprite().getWidth(), (int) getPlayer().getSprite().getHeight());
        player.setFixture(f);

        GameObject en = createEnemy(new Vector2(100, 100));
        Fixture f2 = shapeFactory.CreateCube((int) getPlayer().getPosition().x, (int) getPlayer().getPosition().y, (int) getPlayer().getSprite().getWidth(), (int) getPlayer().getSprite().getHeight());
        en.setFixture(f2);

        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    }

    public void update()
    {
        update(Gdx.graphics.getDeltaTime());
    }


    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
        for (IGameObject go : gameObjects)
        {
            go.update();
        }
    }

    public IGameObject CreateGameObject()
    {
        return null;
    }

    public SpaceShip createPlayer()
    {
        SpaceShip ship = new SpaceShip(new Vector2(50, 50), 0f, spaceShipTexturesHelper.getSpaceShipSprite(1));

        gameObjects.add(ship);
        player = ship;
        return ship;
    }

    public SpaceShipEnemy createEnemy(Vector2 pos)
    {
        SpaceShipEnemy enemy = new SpaceShipEnemy(pos, 0, spaceShipTexturesHelper.getSpaceShipSprite(2), null);

        gameObjects.add(enemy);
        return enemy;
    }

    public void draw(Batch batch)
    {
        for (IGameObject go : gameObjects)
        {
            go.Draw(batch);
        }
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
}
