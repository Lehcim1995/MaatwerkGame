package classes.gameobjects.playable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.school.spacegame.Main;

public class SpaceShip extends Ship
{

    private Main main;

    public SpaceShip(Vector2 position, float rotation, Sprite sprite)
    {
        setDefaults();
    }

    public SpaceShip(Vector2 position, float rotation, Sprite sprite, Main main)
    {
        this.main = main;
        mass = sprite.getHeight() * sprite.getWidth();
        mass /= 10;
        setDefaults();
    }

    private void setDefaults()
    {
        maxSpeed = 10;
        speed = 0;
        acceleration = 1.1f;
        deAcceleration = 0.9f;
        rotSpeed = 80;
        maxSpaceshipSpeed = 15;
        currentSpeedVector = new Vector2(0, 0);
    }

    @Override
    public void Draw(Batch batch)
    {
        sprite.draw(batch);
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {
    }

    @Override
    public void update()
    {
        super.update();

//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
//        {
//            Projectile projectile = new Projectile(new Vector2(position), rotation, 10000);
//            main.addProjectile(projectile);
//        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            addRotation(rotSpeed * getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            addRotation(-rotSpeed * getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            if (speed < maxSpeed)
            {
                if (speed == 0)
                {
                    speed = 0.1f;
                }
                speed *= acceleration;
            }
        }
        else
        {
            if (speed > 0)
            {
                speed *= deAcceleration;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            currentSpeedVector.setLength(getSpeed() > 0.1f ? getSpeed() * deAcceleration : 0);
        }

        if (speed < 0.1f)
        {
            speed = 0;
        }

        if (speed > maxSpeed)
        {
            speed = maxSpeed;
        }

        float x = MathUtils.cosDeg(rotation);
        float y = MathUtils.sinDeg(rotation);
        Vector2 pos = new Vector2(x, y).setLength(speed * getDeltaTime());

        currentSpeedVector.add(pos);

        translate(currentSpeedVector);

        if (currentSpeedVector.len() > maxSpaceshipSpeed)
        {
            currentSpeedVector.setLength(maxSpaceshipSpeed);
        }
    }

    private float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }
}
