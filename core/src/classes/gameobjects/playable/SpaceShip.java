package classes.gameobjects.playable;

import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class SpaceShip extends Ship
{

    private GameManager gameManager;

    public SpaceShip(Vector2 position, float rotation, Sprite sprite)
    {

        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;

        setDefaults();
    }

    public SpaceShip(Vector2 position, float rotation, Sprite sprite, GameManager gameManager)
    {
        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;
        this.gameManager = gameManager;

        setDefaults();
    }

    // TODO translate these values to something useful.
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
    public void update()
    {
        super.update();

        final float rotNewtons = fixture.getBody().getMass() * 100;
        final float moveNewtons = fixture.getBody().getMass() * 1000;

        // TODO add this to inputManager

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            fixture.getBody().applyForceToCenter(getForward().scl(moveNewtons), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            //TODO slow down spaceship
            fixture.getBody().applyForceToCenter(getBackwards().scl(moveNewtons), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            fixture.getBody().applyForce(getLeft().scl(rotNewtons), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getRight().scl(rotNewtons), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            fixture.getBody().applyForce(getRight().scl(rotNewtons), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getLeft().scl(rotNewtons), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            Vector2 dir = new Vector2(fixture.getBody().getLinearVelocity()).rotate(180).nor();

//            fixture.getBody().
            fixture.getBody().applyForceToCenter(dir.scl(moveNewtons), false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.V))
        {
            //TODO add shooting
            gameManager.fireLaser(position, 100, rotation);
        }
    }

    public void setGameManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
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
