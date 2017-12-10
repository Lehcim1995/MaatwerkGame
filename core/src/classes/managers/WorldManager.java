package classes.managers;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import static classes.Constants.GRAVITY;

public class WorldManager
{
    public World world;

    public WorldManager()
    {
        Box2D.init();
        world = new World(GRAVITY, false);
    }

    public WorldManager(World world)
    {
        Box2D.init();
        this.world = world;
    }

    public void reloadWorld()
    {
        if (world != null)
        {
            world.dispose();
        }

        world = new World(GRAVITY, false);
    }
}
