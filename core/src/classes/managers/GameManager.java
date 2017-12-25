package classes.managers;

import classes.gameobjects.playable.SpaceShip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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

    public GameManager()
    {
        this.worldManager = new WorldManager();
        this.spaceShipTexturesHelper = new SpaceShipTexturesHelper();
        this.sceneManager = new SceneManager();
        gameObjects = new ArrayList<>();
    }

    public void update()
    {
        update(Gdx.graphics.getDeltaTime());
    }

    public void draw()
    {
        draw(Gdx.graphics.getDeltaTime());
    }

    public void update(float deltaTime)
    {
        doPhysicsStep(deltaTime);
    }

    public IGameObject CreateGameObject()
    {
        return null;
    }

    public SpaceShip createPlayer()
    {
        SpaceShip ship = new SpaceShip(new Vector2(0, 0), 0f, spaceShipTexturesHelper.getSpaceShipSprite(1));


        gameObjects.add(ship);
        return ship;
    }

    public void draw(float deltaTime)
    {

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

    public void dispose()
    {

    }
}
