package classes.gameobjects.playable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;

        setDefaults();
    }

    public SpaceShip(Vector2 position, float rotation, Sprite sprite, Main main)
    {
        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;
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

        final float rotNewtons = fixture.getBody().getMass() * 100;
        final float moveNewtons = fixture.getBody().getMass() * 100;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            fixture.getBody().applyForceToCenter(getForward().scl(20000000), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            //TODO slow down spaceship
            fixture.getBody().applyForceToCenter(getBackwards().scl(20000000), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            fixture.getBody().applyForce(getLeft().scl(2000000), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getRight().scl(2000000), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            fixture.getBody().applyForce(getRight().scl(2000000), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getLeft().scl(2000000), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            Vector2 dir = new Vector2(fixture.getBody().getLinearVelocity()).rotate(180).nor();

            fixture.getBody().applyForceToCenter(dir.scl(20000000), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.V))
        {
            //TODO add shooting
        }

    }
}
