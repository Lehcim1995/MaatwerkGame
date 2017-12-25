package scenes;

import classes.managers.GameManager;
import com.badlogic.gdx.Screen;

public class MainScene implements Screen
{

    private GameManager gameManager;

    @Override
    public void show()
    {
        // init
        gameManager = new GameManager();
    }

    @Override
    public void render(float delta)
    {
        gameManager.update(delta);
        gameManager.draw(delta);
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        gameManager.dispose();
    }
}
