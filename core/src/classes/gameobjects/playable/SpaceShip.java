package classes.gameobjects.playable;

import classes.gameobjects.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameManager;
import interfaces.IGameObject;

public class SpaceShip extends Ship implements InputProcessor
{

    private IGameManager gameManager;

    //flags for movement
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean halt;
    private boolean shoot;

    private boolean isShooting;
    private float shootingTimer = 0;
    private float shootingDuration = 1 / 5f; // times per second

    private Turret turret;

    public SpaceShip(
            Vector2 position,
            float rotation,
            Sprite sprite)
    {

        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;

        setDefaults();
    }

    public SpaceShip(
            Vector2 position,
            float rotation,
            Sprite sprite,
            IGameManager gameManager)
    {
        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;
        this.gameManager = gameManager;

        setDefaults();
    }

    // TODO translate these values to something useful and less abstract.
    private void setDefaults()
    {
        maxSpeed = 10;
        speed = 0;
        acceleration = 1.1f;
        deAcceleration = 0.9f;
        rotSpeed = 80;
        maxSpaceshipSpeed = 15;
        currentSpeedVector = new Vector2(0, 0);

        turret = new Turret();
    }

    @Override
    public void Draw(Batch batch)
    {
        sprite.draw(batch);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        final float rotNewtons = fixture.getBody().getMass() * 100;
        final float moveNewtons = fixture.getBody().getMass() * 1000;



        if (up)
        {
            fixture.getBody().applyForceToCenter(getForward().scl(moveNewtons), false);
        }

        if (down)
        {
            //TODO slow down spaceship
            fixture.getBody().applyForceToCenter(getBackwards().scl(moveNewtons), false);
        }

        if (left)
        {
            fixture.getBody().applyForce(getLeft().scl(rotNewtons), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getRight().scl(rotNewtons), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (right)
        {
            fixture.getBody().applyForce(getRight().scl(rotNewtons), getForward().scl(sprite.getWidth() / 2), true);
            fixture.getBody().applyForce(getLeft().scl(rotNewtons), getBackwards().scl(sprite.getWidth() / 2), true);
        }

        if (halt)
        {
            Vector2 dir = new Vector2(fixture.getBody().getLinearVelocity()).rotate(180).nor();

//            fixture.getBody().
            fixture.getBody().applyForceToCenter(dir.scl(moveNewtons), false);
        }

        if (shoot && !isShooting)
        {
            isShooting = true;
            //TODO add shooting


            gameManager.fireLaser(position, 300, rotation);
        }

        if (isShooting)
        {
            shootingTimer += Gdx.graphics.getDeltaTime();
            if (shootingTimer > shootingDuration)
            {
                shootingTimer = 0;
                isShooting = false;
            }
        }


    }

    @Override
    public void update() {

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

    public IGameManager getGameManager()
    {
        return gameManager;
    }

    public void setGameManager(IGameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.W) // Forward
        {
            up = true;
        }

        if (keycode == Input.Keys.S) // Backward
        {
            down = true;
        }

        if (keycode == Input.Keys.D) // Turning right
        {
            right = true;
        }

        if (keycode == Input.Keys.A) // Turning left
        {
            left = true;
        }

        if (keycode == Input.Keys.SPACE) // Stopping?
        {
            halt = true;
        }

        if (keycode == Input.Keys.V) // Shooting
        {
            shoot = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if (keycode == Input.Keys.W) // Forward
        {
            up = false;
        }

        if (keycode == Input.Keys.S) // Backward
        {
            down = false;
        }

        if (keycode == Input.Keys.D) // Turning right
        {
            right = false;
        }

        if (keycode == Input.Keys.A) // Turning left
        {
            left = false;
        }

        if (keycode == Input.Keys.SPACE) // Stopping?
        {
            halt = false;
        }

        if (keycode == Input.Keys.V) // Shooting
        {
            shoot = false;
        }

        if (keycode == Input.Keys.ESCAPE) // Move this to the game manager itself
        {
            gameManager.getGameScreen().getMain().sceneManager.LoadMainMenuScreen();
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return true;
    }

    @Override
    public boolean touchDown(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(
            int screenX,
            int screenY,
            int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(
            int screenX,
            int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
