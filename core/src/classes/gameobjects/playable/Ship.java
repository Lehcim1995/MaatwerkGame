package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.math.Vector2;

public abstract class Ship extends GameObject
{
    protected float maxSpaceshipSpeed;
    protected float maxSpeed;
    protected float speed;
    protected float acceleration;
    protected float deAcceleration;
    protected float rotSpeed;
    protected float mass;

    protected int maxHealth;
    protected float health;

    protected int maxArmor;
    protected float armor;

    //TODO add weapon system.

    protected Vector2 currentSpeedVector;

    @Override
    public void update()
    {
        this.position = fixture.getBody().getPosition();
        this.rotation = (float) Math.toDegrees(fixture.getBody().getAngle());

        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
        sprite.setRotation(rotation);
    }

    protected void onDestroy() // Make abstract?
    {
        //TODO add stuff
    }

    public void SetRotSpeed(float degPerSec)
    {
        // Calculate how fast it needs to be to rotate that fast/slow

    }
}
