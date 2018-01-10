package classes.managers;

import classes.gameobjects.playable.SpaceShip;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class InputManagerPlayer implements InputProcessor
{

    private SpaceShip player;

    public InputManagerPlayer(SpaceShip player)
    {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode)
    {

        final Fixture fixture = player.getFixture();
        final float rotNewtons = fixture.getBody().getMass() * 100;
        final float moveNewtons = fixture.getBody().getMass() * 1000;

        //TODO make constance or make it modiviable, not all keyboard use wasd for moving.
        if (keycode == Input.Keys.W) // Forward
        {
            fixture.getBody().applyForceToCenter(player.getForward().scl(moveNewtons), false);
        }

        if (keycode == Input.Keys.S) // Backward
        {
            fixture.getBody().applyForceToCenter(player.getBackwards().scl(moveNewtons), false);
        }

        if (keycode == Input.Keys.D) // Turning right
        {
            fixture.getBody().applyForce(player.getRight().scl(rotNewtons), player.getForward().scl(player.getSprite().getWidth() / 2), true);
            fixture.getBody().applyForce(player.getLeft().scl(rotNewtons), player.getBackwards().scl(player.getSprite().getWidth() / 2), true);
        }

        if (keycode == Input.Keys.A) // Turning left
        {
            fixture.getBody().applyForce(player.getLeft().scl(rotNewtons), player.getForward().scl(player.getSprite().getWidth() / 2), true);
            fixture.getBody().applyForce(player.getRight().scl(rotNewtons), player.getBackwards().scl(player.getSprite().getWidth() / 2), true);
        }

        if (keycode == Input.Keys.SPACE) // Stopping?
        {
            Vector2 dir = new Vector2(fixture.getBody().getLinearVelocity()).rotate(180).nor();

            //TODO stop rotation
            fixture.getBody().applyForceToCenter(dir.scl(moveNewtons), false);
        }

        if (keycode == Input.Keys.V) // Shooting
        {
            player.getGameManager().fireLaser(player.getPosition(), 300, player.getRotation());
        }


        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
