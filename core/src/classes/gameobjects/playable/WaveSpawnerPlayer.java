package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.gameobjects.WaveSpawner;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import interfaces.IGameManager;
import interfaces.IGameObject;

public class WaveSpawnerPlayer extends GameObject implements InputProcessor
{

    private IGameManager gameManager;
    private WaveSpawner waveSpawner;
    private boolean hasSpawned;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public WaveSpawnerPlayer(IGameManager gameManager)
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
    public void update()
    {
        //TODO change pos when moving

        final float speed = 10;

        if (left)
        {
            position.x -= speed;
        }

        if (right)
        {
            position.x += speed;
        }

        if (up)
        {
            position.y += speed;
        }

        if (down)
        {
            position.y -= speed;
        }
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {

    }

    @Override
    public void Draw(Batch batch)
    {

    }

    private void spawnEnemys(
            int screenX,
            int screenY)
    {
        spawnEnemys(new Vector2(screenX, screenY));
    }

    public void spawnEnemys(Vector2 mousePos)
    {
        int spawn = 10;
        float radius = 10;
        float angle = 360 / spawn;
        Vector3 vector3 = new Vector3(mousePos.x, mousePos.y, 0);
        Vector3 screenpos = gameManager.getGameScreen().getCamera().unproject(vector3);
        Vector2 middle = new Vector2(screenpos.x, screenpos.y);

        for (int i = 0; i < spawn; i++)
        {
            // TODO refactor
            // Make a vector with a radius
            Vector2 pos = new Vector2(radius, 0);
            pos.rotate(angle * i);
            pos.add(middle);

            gameManager.createEnemy(pos, angle * i);
        }
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

        if (keycode == Input.Keys.ESCAPE) // Shooting
        {
            gameManager.getGameScreen().getMain().sceneManager.LoadMainMenuScreen();
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        spawnEnemys(screenX, screenY);
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
