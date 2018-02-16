package classes.managers;

import classes.CollisionListener;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import static classes.Constants.GRAVITY;

public class WorldManager
{
    public World world;

    // Shape helper?
    private final ContactListener contactListener = new CollisionListener();

    public WorldManager()
    {
        Box2D.init();
        reloadWorld();
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
        world.setContactListener(contactListener);
    }


}
