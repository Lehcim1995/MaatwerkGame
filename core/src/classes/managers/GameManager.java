package classes.managers;

import classes.CollisionMasks;
import classes.SyncObject;
import classes.factories.ShapeHelper;
import classes.gameobjects.GameObject;
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
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static classes.Constants.*;

public class GameManager extends UnicastRemoteObject
{
    private MainScreen mainScreen;
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private ShapeHelper shapeHelper;
    private float accumulator;

    private List<IGameObject> gameObjects;
    private List<IGameObject> serverGameObjects;

    private SpaceShip player;
    private WaveSpawnerPlayer waveSpawnerPlayer;

    // Online stuff
    private IGameLobby gameLobby;
    private boolean online;
    private playerType type;
    private String playerName;

    public GameManager(
            IGameLobby gameLobby,
            playerType type,
            String playerName,
            MainScreen mainScreen) throws RemoteException
    {
        super();
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
        }

        // TODO add lobby name
    }

    public void createSpawnerPlayer()
    {
        createSpawnerPlayer(new Vector2(0, 0));
        // TODO make this method
    }

    public void createSpawnerPlayer(Vector2 pos)
    {
        waveSpawnerPlayer = new WaveSpawnerPlayer(this);
        waveSpawnerPlayer.setPosition(pos);

        gameObjects.add(waveSpawnerPlayer);
        // TODO make this method
    }

    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
        for (Iterator<IGameObject> it = gameObjects.iterator(); it.hasNext(); )
        {
            IGameObject gameObject = it.next();
            gameObject.update();
        }

        for (IGameObject gameObject : serverGameObjects)
        {
            if (gameObject == null)
            {
                continue;
            }
            gameObject.update();
        }

        // TODO can this be merged?

        for (Iterator<IGameObject> it = gameObjects.iterator(); it.hasNext(); )
        {
            GameObject gameObject = (GameObject) it.next();
            if (gameObject.isToDelete())
            {
                // Deleting this body is useful
                worldManager.world.destroyBody(gameObject.getFixture().getBody());
                //TODO let the server know i deleted this gameobject
                if (online)
                {
                    try
                    {
                        gameLobby.deleteObject(playerName, fromGameObjectTOSyncObject(gameObject));
                    }
                    catch (RemoteException e)
                    {
                        // TODO errors
                    }
                }
                gameObjects.remove(gameObject);
            }
        }

        if (online)
        {
            // TODO recive other shizzle
            try
            {
                for (ISyncObject syncObject : gameLobby.getNewObjects(playerName))
                {
                    //
                    System.out.println("New objects");
                    IGameObject obj = createFromSyncObject(syncObject);
                    if (obj == null)
                    {
                        continue;
                    }
                    serverGameObjects.add(obj);
                }

                for (ISyncObject syncObject : gameLobby.getUpdates(playerName))
                {
                    updateFromSyncObject(syncObject);
                }
            }
            catch (RemoteException e)
            {
                // TODO add error
                // or return to start screen
            }


            // TODO Send own shizzle
            for (IGameObject gameObject : gameObjects)
            {
                try
                {
                    gameLobby.addUpdate(playerName, fromGameObjectTOSyncObject(gameObject));
                }
                catch (RemoteException e)
                {
                    // TODO add error
                    // or return to start screen
                }
            }
        }
    }

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

        createOnlineObject(laser);

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

        createOnlineObject(ship);

        gameObjects.add(ship);
        player = ship;
        return ship;
    }

    private void deleteOnlineObject(IGameObject gameObject)
    {
        if (online)
        {
            try
            {
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

    public SpaceShip createNonPlayer(Vector2 pos)
    {
        return createNonPlayer(pos, 0f);
    }

    public SpaceShip createNonPlayer(
            Vector2 pos,
            float rotation)
    {
        //TODO create this method
        return null;
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

        createOnlineObject(enemy);

        gameObjects.add(enemy);
        return enemy;
    }

    private IGameObject createFromSyncObject(ISyncObject syncObject)
    {
        online = false;

        IGameObject obj = null;

        System.out.println("object type " + syncObject.getObjectType());

        switch (syncObject.getObjectType())
        {
            case "Laser":
                System.out.println("Laser");
                obj = fireLaser(syncObject.getPosition(), 300, syncObject.getRotation());
                obj.setID(syncObject.getId());
                break;
            case "SpaceShip":
                System.out.println("Player");
                obj = createPlayer(syncObject.getPosition());
                obj.setID(syncObject.getId());
                break;
            case "SpaceShipEnemy":
                System.out.println("Enemy");
                obj = createEnemy(syncObject.getPosition(), syncObject.getRotation());
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

        if (gameObject instanceof Laser)
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
            // TODO change
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
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        for (IGameObject go : gameObjects)
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
