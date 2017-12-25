package classes.gameobjects.playable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    }

    private float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }
}
