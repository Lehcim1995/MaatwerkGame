package classes.managers;

import classes.CollisionMasks;
import classes.SyncObject;
import classes.gameobjects.playable.Ship;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.unplayble.Laser;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameLobby;
import interfaces.IGameObject;
import interfaces.ISyncObject;
import screens.GameScreen;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OnlineRmiManager extends GameManager
{

    private IGameLobby gameLobby;
    private String playerName;

    private float onlineUpdateRate = 1 / 30f;
    private float onlineUpdateTimer = 0;
    private List<IGameObject> serverGameObjects;

    public OnlineRmiManager(
            IGameLobby gameLobby,
            GameManager.playerType type,
            String playerName,
            GameScreen gameScreen)
    {
        super(type, gameScreen);
        this.gameLobby = gameLobby;
        this.playerName = playerName;
        serverGameObjects = new CopyOnWriteArrayList<>();

        spawnDefault();
    }

    @Override
    public void spawnDefault()
    {
        if (gameLobby != null)
        {
            super.spawnDefault();
        }
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        onlineUpdateTimer += deltaTime;
        if (onlineUpdateTimer > onlineUpdateRate)
        {
            onlineUpdateTimer -= onlineUpdateRate;

            try
            {
                for (IGameObject gameObject : ObjectManager.getGameObjects() /*gameObjects*/)
                {
                    gameLobby.addUpdate(playerName, fromGameObjectTOSyncObject(gameObject));
                }

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
                Logger.getAnonymousLogger().log(Level.SEVERE, "Remote Exception", e);
            }
        }

        for (IGameObject gameObject : serverGameObjects)
        {
            gameObject.update();
        }

        for (IGameObject gameObject : serverGameObjects)
        {
            if (gameObject.isToDelete())
            {
                System.out.println("Deleting serverRmi object local " + gameObject.getID());
                getWorldManager().world.destroyBody(gameObject.getFixture().getBody());
                serverGameObjects.remove(gameObject);
            }
        }
    }

    @Override
    public void draw(Batch batch)
    {
        super.draw(batch);
        for (IGameObject gameObject : serverGameObjects)
        {
            gameObject.Draw(batch);
        }
    }

    @Override
    public SpaceShipEnemy createEnemy(
            Vector2 pos,
            float rotation)
    {
        IGameObject obj = super.createEnemy(pos, rotation);
        try
        {
            System.out.println("Create online enemy");
            long id = gameLobby.createObject(playerName, fromGameObjectTOSyncObject(obj));
            obj.setID(id);
        }
        catch (RemoteException e)
        {
            System.out.println("Error");
        }
        return (SpaceShipEnemy) obj;
    }

    @Override
    public SpaceShip createPlayer(
            Vector2 pos,
            float rotation)
    {
        IGameObject obj = super.createPlayer(pos, rotation);
        try
        {
            System.out.println("Create online player");
            long id = gameLobby.createObject(playerName, fromGameObjectTOSyncObject(obj));
            obj.setID(id);
        }
        catch (RemoteException e)
        {
            System.out.println("Error");
        }
        return (SpaceShip) obj;
    }

    @Override
    public Laser fireLaser(
            Vector2 pos,
            float speed,
            float rotation)
    {
        IGameObject obj = super.fireLaser(pos, speed, rotation);
        try
        {
            System.out.println("Create online laser");
            long id = gameLobby.createObject(playerName, fromGameObjectTOSyncObject(obj));
            obj.setID(id);
        }
        catch (RemoteException e)
        {
            System.out.println("Error");
        }
        return (Laser) obj;
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

                Logger.getAnonymousLogger().log(Level.INFO, "Updating online " + syncObject.getId());

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

    private IGameObject createFromSyncObject(ISyncObject syncObject)
//    throws CantCreateObjectException // TODO maybe keep in Gamemanger
    {
        // TODO let his throw a custom (cant create) exception.
        IGameObject obj = null;

        System.out.println("object type " + syncObject.getObjectType());

        switch (syncObject.getObjectType()) // TODO refactor, this should create serverRmi objects. not local objects
        {
            case "Laser":
                System.out.println("Laser");
                obj = fireLaserOnline(syncObject.getPosition(), 300, syncObject.getRotation());
                obj.setID(syncObject.getId());
                break;
            case "SpaceShip":
                System.out.println("Player");
                obj = createPlayerOnline(syncObject.getPosition(), 0);
                obj.setID(syncObject.getId());
                break;
            case "SpaceShipEnemy":
                System.out.println("Enemy");
                obj = createEnemyOnline(syncObject.getPosition(), syncObject.getRotation());
                obj.setID(syncObject.getId());
                break;
            default:
//                throw new CantCreateObjectException("");
        }

        return obj;
    }

    private IGameObject fireLaserOnline(
            Vector2 position,
            int speed,
            float rotation)
    {
        Laser laser = new Laser(new Vector2(position), rotation, speed);
        Fixture fixture = shapeHelper.CreateCube(laser);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);

        laser.setFixture(fixture); // TODO refactor this.
        laser.setSpeed(speed);

        fixture.setUserData(laser);

//        gameObjects.add(laser);
        ObjectManager.Instantiate(laser);
        return laser;
    }

    private IGameObject createPlayerOnline(
            Vector2 position,
            float rotation)
    {
        SpaceShip ship = new SpaceShip(position, rotation, spaceShipTexturesHelper.getSpaceShipSprite(1));

        Fixture fixture = shapeHelper.CreateCube(ship);
        fixture.setFilterData(CollisionMasks.PLAYER_FILTER);
        ship.setFixture(fixture);
        fixture.setUserData(ship);

        serverGameObjects.add(ship);
        return ship;
    }

    private IGameObject createEnemyOnline(
            Vector2 position,
            float rotation)
    {
        SpaceShipEnemy enemy = new SpaceShipEnemy(position, rotation, spaceShipTexturesHelper.getSpaceShipSprite(16), null);

        Fixture fixture = shapeHelper.CreateCube(enemy);
        fixture.setFilterData(CollisionMasks.ENEMY_FILTER);
        enemy.setFixture(fixture);
        fixture.setUserData(enemy);

        serverGameObjects.add(enemy);
        return enemy;
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
}
