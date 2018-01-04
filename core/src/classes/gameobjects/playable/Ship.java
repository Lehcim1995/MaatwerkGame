package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

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
    public void update()
    {
        this.position = fixture.getBody().getPosition();
        this.rotation = (float) Math.toDegrees(fixture.getBody().getAngle());

        sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
        sprite.setRotation(rotation);
    }

    @Override
    public void Draw(Batch batch)
    {
        super.Draw(batch);
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
