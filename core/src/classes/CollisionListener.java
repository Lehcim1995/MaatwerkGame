package classes;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact) //TODO fix this bug
    {
        if (contact.getFixtureA().getUserData() instanceof GameObject || contact.getFixtureB().getUserData() instanceof GameObject)
        {
            GameObject a = (GameObject) contact.getFixtureA().getUserData();
            GameObject b = (GameObject) contact.getFixtureB().getUserData();

            a.onCollisionEnter(b);
            b.onCollisionEnter(a);
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        if (contact.getFixtureA().getUserData() instanceof GameObject || contact.getFixtureB().getUserData() instanceof GameObject)
        {
            GameObject a = (GameObject) contact.getFixtureA().getUserData();
            GameObject b = (GameObject) contact.getFixtureB().getUserData();

            a.onCollisionExit(b);
            b.onCollisionExit(a);
        }
    }

    @Override
    public void preSolve(
            Contact contact,
            Manifold oldManifold)
    {
        // When overlapping
    }

    @Override
    public void postSolve(
            Contact contact,
            ContactImpulse impulse)
    {
        // when finnished overlapping
    }
}
