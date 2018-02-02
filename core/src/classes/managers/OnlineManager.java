package classes.managers;

import classes.SyncObject;
import classes.gameobjects.playable.Ship;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.unplayble.Laser;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameLobby;
import interfaces.IGameObject;
import interfaces.ISyncObject;
import screens.MainScreen;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OnlineManager extends GameManager
{

    private IGameLobby gameLobby;
    private String playerName;

    private float onlineUpdateRate = 1 / 30f;
    private float onlineUpdateTimer = 0;
    private List<IGameObject> serverGameObjects;

    public OnlineManager(
            IGameLobby gameLobby,
            GameManager.playerType type,
            String playerName,
            MainScreen mainScreen)
    {
        super(type, mainScreen);
        this.gameLobby = gameLobby;
        this.playerName = playerName;
        serverGameObjects = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        onlineUpdateTimer += deltaTime;
        if (onlineUpdateTimer > onlineUpdateRate)
        {
            onlineUpdateTimer = 0;

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
                Logger.getAnonymousLogger().log(Level.SEVERE, "Remote Exception", e);
            }
        }

        for (IGameObject gameObject : serverGameObjects)
        {
            if (gameObject.isToDelete())
            {
                getWorldManager().world.destroyBody(gameObject.getFixture().getBody());
                serverGameObjects.remove(gameObject);
                System.out.println("Deleting server object local " + gameObject.getID());
            }
        }
    }

    private IGameObject createFromSyncObject(ISyncObject syncObject) // TODO maybe keep in Gamemanger
    {
        // TODO let his throw a custom (cant create) exception.
        IGameObject obj = null;

        System.out.println("object type " + syncObject.getObjectType());

        switch (syncObject.getObjectType()) // TODO refactor, this should create server objects. not local objects
        {
            case "Laser":
                System.out.println("Laser");
                obj = fireLaser(syncObject.getPosition(), 300, syncObject.getRotation());
                obj.setID(syncObject.getId());
                break;
            case "SpaceShip":
                System.out.println("Player");
                obj = createPlayer(syncObject.getPosition(), 0);
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
}
