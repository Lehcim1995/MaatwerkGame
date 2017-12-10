package classes.managers;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

public class WorldManager
{
    private World world;

    private WorldManager(World world)
    {
        Box2D.init();
        this.world = world;
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public void reloadWorld()
    {
        if (world != null)
        {
            world.dispose();
        }
        // TODO add new world
    }
}
