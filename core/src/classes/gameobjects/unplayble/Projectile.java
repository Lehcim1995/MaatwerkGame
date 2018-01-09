package classes.gameobjects.unplayble;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameObject
{
    private float speed;
    private Vector2 dirVector;

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
        if (sprite != null)
        {
            sprite.draw(batch);
        }
    }

    private float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
        if (fixture != null)
        {
            fixture.getBody().setLinearVelocity(getForward().scl(speed));
        }
    }

    public float getSpeed()
    {
        return speed;
    }
}
