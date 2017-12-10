package classes.managers;

import static classes.Constants.*;

public class GameManager
{
    private WorldManager worldManager;
    private SpaceShipTexturesHelper spaceShipTexturesHelper;
    private SceneManager sceneManager;
    private float accumulator;

    private void doPhysicsStep(float deltaTime)
    {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP)
        {
            worldManager.getWorld().step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

            accumulator -= TIME_STEP;
        }
    }

}
