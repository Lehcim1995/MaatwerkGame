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

        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
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

    public Vector2 getForward()
    {
        Vector2 forward = new Vector2(1, 0).rotate(rotation);

        return forward;
    }

    public Vector2 getBackwards()
    {
        Vector2 backwards = new Vector2(-1, 0).rotate(rotation);

        return backwards;
    }

    public Vector2 getLeft()
    {
        Vector2 left = new Vector2(0, 1).rotate(rotation);

        return left;
    }

    public Vector2 getRight()
    {
        Vector2 right = new Vector2(0, -1).rotate(rotation);

        return right;
    }

}
