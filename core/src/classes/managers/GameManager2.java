package classes.managers;

import classes.factories.ShapeHelper;
import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.WaveSpawnerPlayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import interfaces.IGameLobby;
import interfaces.IGameObject;
import screens.MainScreen;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static classes.Constants.*;

@SuppressWarnings("ALL")
public class GameManager2
{
    // Managers
    private MainScreen mainScreen;
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private ShapeHelper shapeHelper;
    private float accumulator;

    // Gameobjects
    private List<IGameObject> gameObjects;

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

    public GameManager2(
            IGameLobby gameLobby,
            playerType type,
            String playerName,
            MainScreen mainScreen)
    {
        this.gameLobby = gameLobby;
        this.type = type;
        this.playerName = playerName;
        this.mainScreen = mainScreen;

        gameObjects = new CopyOnWriteArrayList<>();

        // create the right player;

        switch (type)
        {
            case Destroyer:
                break;
            case Spawner:
                break;
            case Spectator:
                break;
        }
    }

    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
        for (IGameObject gameObject : gameObjects)
        {
            gameObject.update();

            if (gameObject.isToDelete())
            {
                // Deleting this body is useful
                worldManager.world.destroyBody(gameObject.getFixture().getBody());
                gameObjects.remove(gameObject);
            }
        }
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
