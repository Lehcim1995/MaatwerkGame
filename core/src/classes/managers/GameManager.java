package classes.managers;

import classes.gameobjects.playable.SpaceShip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import interfaces.IGameObject;

import java.util.ArrayList;
import java.util.List;

import static classes.Constants.*;

public class GameManager
{
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private SceneManager sceneManager;
    private float accumulator;

    private List<IGameObject> gameObjects;

    private Sprite sprite;

    private SpaceShip player;

    public GameManager()
    {
        this.worldManager = new WorldManager();
        this.spaceShipTexturesHelper = new SpaceShipTexturesHelper();
        this.sceneManager = new SceneManager();
        gameObjects = new ArrayList<>();

        createPlayer();
        sprite = spaceShipTexturesHelper.getSpaceShipSprite(1);

        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    }

    public void update()
    {
        update(Gdx.graphics.getDeltaTime());
    }


    public void update(float deltaTime)
    {
        for (IGameObject go : gameObjects)
        {
            go.translate(new Vector2(0, 1));
            go.addRotation(5);
        }
        doPhysicsStep(deltaTime);
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

    public void dispose()
    {
        for (IGameObject go : gameObjects)
        {
            //TODO dispose all game objects
        }
    }
}
