package classes.managers;

import classes.gameobjects.playable.SpaceShip;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor
{

    private SpaceShip player;

    public InputManager(SpaceShip player)
    {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode)
    {

        //TODO make constance or make it modiviable, not all keyboard use wasd for moving.
        if (keycode == Input.Keys.W) // Forward
        {

        }

        if (keycode == Input.Keys.S) // Backward
        {

        }

        if (keycode == Input.Keys.D) // Turning right
        {

        }

        if (keycode == Input.Keys.A) // Turning left
        {

        }

        if (keycode == Input.Keys.SPACE) // Stopping?
        {

        }

        if (keycode == Input.Buttons.LEFT) // Shooting
        {

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
