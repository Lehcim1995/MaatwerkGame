package classes.managers;

import classes.CollisionMasks;
import classes.SyncObject;
import classes.factories.ShapeHelper;
import classes.gameobjects.playable.Ship;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.playable.WaveSpawnerPlayer;
import classes.gameobjects.unplayble.Laser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameLobby;
import interfaces.IGameObject;
import interfaces.ISyncObject;
import screens.MainScreen;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static classes.Constants.*;

@SuppressWarnings("ALL")
public class GameManager
{
    // Managers
    private MainScreen mainScreen;
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private ShapeHelper shapeHelper;
    private float accumulator;

    // Gameobjects
    private List<IGameObject> gameObjects;
    private List<IGameObject> serverGameObjects;

    private SpaceShip player;
    private WaveSpawnerPlayer waveSpawnerPlayer;

    // Online stuff
    private OnlineManager onlineManager;
    private IGameLobby gameLobby;
    private boolean online;
    private playerType type;
    private String playerName;
    private float onlineUpdateRate = 1 / 30f;
    private float onlineUpdateTimer = 0;

    public GameManager(
            IGameLobby gameLobby,
            playerType type,
            String playerName,
            MainScreen mainScreen)
    {
        this.worldManager = new WorldManager();
        this.spaceShipTexturesHelper = new SpaceShipTexturesHelper();
        shapeHelper = new ShapeHelper(worldManager.world);
        gameObjects = new CopyOnWriteArrayList<>();
        serverGameObjects = new CopyOnWriteArrayList<>();

        this.gameLobby = gameLobby;
        this.online = gameLobby != null;
        this.playerName = playerName;
        this.type = type;
        this.mainScreen = mainScreen;
        this.onlineManager = new OnlineManager(gameLobby, playerName);

        switch (this.type)
        {
            case Destroyer:
                createPlayer(new Vector2(0, 0));
                Gdx.input.setInputProcessor(player);
                break;
            case Spawner:
                createSpawnerPlayer();
                Gdx.input.setInputProcessor(waveSpawnerPlayer);
                break;
            case Spectator:
                // TODO add spectator
                break;
        }
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

    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
        for (IGameObject gameObject : gameObjects)
        {
            gameObject.update();
        }

        for (IGameObject gameObject : serverGameObjects)
        {
            gameObject.update();
        }

        updateOnline(deltaTime);

        for (IGameObject gameObject : serverGameObjects)
        {
            if (gameObject.isToDelete())
            {
                worldManager.world.destroyBody(gameObject.getFixture().getBody());
                serverGameObjects.remove(gameObject);
                System.out.println("Deleting server object local " + gameObject.getID());
            }
        }

        for (IGameObject gameObject : gameObjects)
        {
            if (gameObject.isToDelete())
            {
                // Deleting this body is useful
                deleteOnlineObject(gameObject);
                System.out.println("Deleting object local " + gameObject.getID());
                worldManager.world.destroyBody(gameObject.getFixture().getBody());
                gameObjects.remove(gameObject);
            }
        }
    }

    private void updateOnline(float deltaTime)
    {
        try
        {
            onlineManager.updateOnline(deltaTime);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public Laser fireLaser(
            Vector2 pos,
            float speed,
            float rotation,
            boolean addLocal)
    {
        Laser laser = new Laser(new Vector2(pos), rotation, speed);
        Fixture fixture = shapeHelper.CreateCube(laser);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);

        laser.setFixture(fixture); // TODO refactor this.
        laser.setSpeed(speed);

        fixture.setUserData(laser);

        createOnlineObject(laser);

        if (addLocal)
        {
            gameObjects.add(laser);
        }
        return laser;
    }

    public SpaceShip createPlayer(Vector2 pos)
    {
        return createPlayer(pos, 0, true);
    }

    public SpaceShip createPlayer(
            Vector2 pos,
            float rotation,
            boolean addLocal)
    {
        SpaceShip ship = new SpaceShip(pos, rotation, spaceShipTexturesHelper.getSpaceShipSprite(1));
        ship.setGameManager(this);

        Fixture fixture = shapeHelper.CreateCube(ship);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);
        ship.setFixture(fixture);
        fixture.setUserData(ship);

        createOnlineObject(ship);

        if (addLocal)
        {
            gameObjects.add(ship);
        }
        player = ship;
        return ship;
    }

    private void deleteOnlineObject(IGameObject gameObject)
    {
        if (online)
        {
            try
            {
                System.out.println("Deleting object online " + gameObject.getID());
                gameLobby.deleteObject(playerName, fromGameObjectTOSyncObject(gameObject));
            }
            catch (RemoteException e)
            {
                //TODO
            }
        }
    }

    private void createOnlineObject(IGameObject gameObject)
    {
        if (online)
        {
            try
            {
                long id = gameLobby.createObject(playerName, fromGameObjectTOSyncObject(gameObject));
                gameObject.setID(id);
            }
            catch (RemoteException e)
            {
                //TODO
            }
        }
    }

    public SpaceShipEnemy createEnemy(Vector2 pos)
    {
        return createEnemy(pos, 0f, true);
    }

    public SpaceShipEnemy createEnemy(
            Vector2 pos,
            float rotation,
            boolean addLocal)
    {
        SpaceShipEnemy enemy = new SpaceShipEnemy(pos, rotation, spaceShipTexturesHelper.getSpaceShipSprite(16), null);

        Fixture fixture = shapeHelper.CreateCube(enemy);
        fixture.setFilterData(CollisionMasks.ENEMY_FILTER);
        enemy.setFixture(fixture);
        fixture.setUserData(enemy);

        createOnlineObject(enemy);

        if (addLocal)
        {
            gameObjects.add(enemy);
        }
        return enemy;
    }

    private IGameObject createFromSyncObject(ISyncObject syncObject)
    {
        // TODO let his throw a custom (cant create) exception.
        online = false; // TODO remove this,

        IGameObject obj = null;

        System.out.println("object type " + syncObject.getObjectType());

        switch (syncObject.getObjectType()) // TODO refactor, this should create server objects. not local objects
        {
            case "Laser":
                System.out.println("Laser");
                obj = fireLaser(syncObject.getPosition(), 300, syncObject.getRotation(), false);
                obj.setID(syncObject.getId());
                break;
            case "SpaceShip":
                System.out.println("Player");
                obj = createPlayer(syncObject.getPosition(), 0, false);
                obj.setID(syncObject.getId());
                break;
            case "SpaceShipEnemy":
                System.out.println("Enemy");
                obj = createEnemy(syncObject.getPosition(), syncObject.getRotation(), false);
                obj.setID(syncObject.getId());
                break;
            default:
                break;
        }
        online = true;

        return obj;
    }

    public void updateFromSyncObject(ISyncObject syncObject)
    {
        // TODO
        for (IGameObject serverGameObject : serverGameObjects)
        {
            if (syncObject.getId() == null)
            {
                System.out.println("id is null");
                continue;
            }

            if (serverGameObject.getID() == syncObject.getId())
            {
                serverGameObject.setPosition(syncObject.getPosition());
                serverGameObject.setRotation(syncObject.getRotation());
                serverGameObject.setToDelete(serverGameObject.isToDelete());

                if (serverGameObject.getFixture() != null)
                {
                    Fixture f = serverGameObject.getFixture();
                    f.getBody().setActive(false);
                    f.getBody().setTransform(syncObject.getPosition(), (float) Math.toRadians(syncObject.getRotation()));
                    f.getBody().setLinearVelocity(syncObject.getLinearVelocity());
                    f.getBody().setAngularVelocity(syncObject.getAngularVelocity());
                    f.getBody().setActive(true);
                }
                break;
            }
        }
    }

    private ISyncObject fromGameObjectTOSyncObject(IGameObject gameObject)
    {
        ISyncObject syncObject = new SyncObject();

        Fixture f = gameObject.getFixture();

        syncObject.setId(gameObject.getID());
        if (f != null)
        {
            syncObject.setAngularVelocity(f.getBody().getAngularVelocity());
            syncObject.setAwake(f.getBody().isAwake());
            syncObject.setLinearVelocity(f.getBody().getLinearVelocity());
        }
        syncObject.setPosition(gameObject.getPosition());
        syncObject.setRotation(gameObject.getRotation());

        if (gameObject instanceof Laser) // TODO add method for this
        {
            syncObject.setObjectType("Laser");
        }
        else if (gameObject instanceof SpaceShip)
        {
            syncObject.setObjectType("SpaceShip");
        }
        else if (gameObject instanceof SpaceShipEnemy)
        {
            syncObject.setObjectType("SpaceShipEnemy");
        }

        if (gameObject instanceof Ship)
        {
            // TODO add param for ship sprite.
            syncObject.setShipSpriteId(1);
        }
        return syncObject;
    }

    public void draw(Batch batch)
    {
        for (IGameObject go : gameObjects)
        {
            go.Draw(batch);
        }

        for (IGameObject go : serverGameObjects)
        {
            go.Draw(batch);
        }
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        for (IGameObject go : gameObjects)
        {
            go.Draw(shapeRenderer);
        }

        for (IGameObject go : serverGameObjects)
        {
            go.Draw(shapeRenderer);
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

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
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
