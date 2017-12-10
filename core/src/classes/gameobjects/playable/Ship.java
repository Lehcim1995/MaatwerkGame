package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.gameobjects.unplayble.Projectile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class Ship extends GameObject
{
    protected float maxSpaceshipSpeed;
    protected float maxSpeed;
    protected float speed;
    protected float acceleration;
    protected float deAcceleration;
    protected float rotSpeed;
    protected float mass;

    protected Vector2 currentSpeedVector;

    @Override
    public void onCollisionEnter(IGameObject other)
    {
        if (other instanceof Ship)
        {
            //TODO fix slow method

            Vector2 myPos = new Vector2(position);
            Vector2 otherpos = new Vector2(other.getPosition());

            float myKineticEnergy = getKineticEnergy();

            float otherKineticEnergy = ((Ship) other).getKineticEnergy();

            //TODO fix it, formula not right, need to calculate directions with it.
            float myNewKineticEnergy = myKineticEnergy - otherKineticEnergy;

            Vector2 diff = myPos.sub(otherpos).setLength(200 * Gdx.graphics.getDeltaTime());

            //TODO remove sqrt, or atleast optimize it.
            float vel = (float) StrictMath.sqrt((2 * Math.abs(myNewKineticEnergy)) / mass);

            if (myNewKineticEnergy < 0)
            {
                currentSpeedVector.set(diff).setLength(vel);
            }
            else
            {
                currentSpeedVector.setLength(vel);
            }
        }

        if (other instanceof Projectile)
        {
            //Do something
            this.destroy();
        }
    }

    private void destroy()
    {

    }

    public float getSpeed()
    {
        return currentSpeedVector.len();
    }

    public float getMass()
    {
        return mass;
    }

    public float getKineticEnergy()
    {
        return 0.5f * mass * (currentSpeedVector.len() * currentSpeedVector.len());
    }

    public Vector2 getCurrentSpeedVector()
    {
        return currentSpeedVector;
    }
}
