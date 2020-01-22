package classes.gameobjects.unplayble;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class Projectile extends GameObject
{
    float lifeTime = 1; // Seconds
    float spriteOffsetRotation = 0;
    private float speed;
    private Vector2 dirVector;
    private int damage = 25;
    private int armorPenetration;

    @Override
    public void update(float delta)
    {
        this.position = fixture.getBody().getPosition();
        this.rotation = (float) Math.toDegrees(fixture.getBody().getAngle());

        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
        sprite.setRotation(rotation + spriteOffsetRotation);

        lifeTime -= Gdx.graphics.getDeltaTime();
        if (lifeTime <= 0)
        {
            toDelete = true;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void Draw(Batch batch)
    {
        if (sprite != null)
        {
            sprite.draw(batch);
        }
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
        if (fixture != null)
        {
            fixture.getBody().setLinearVelocity(getForward().scl(speed));
        }
    }

    public int getDamage()
    {
        return damage;
    }

    @Override
    public void onCollisionEnter(IGameObject other)
    {

    }

    @Override
    public void onCollisionExit(IGameObject other)
    {

    }

    @Override
    public void onCollisionStay(IGameObject other)
    {

    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {

    }
}
