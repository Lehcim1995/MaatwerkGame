package scenes;

import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainScene implements Screen
{

    private GameManager gameManager;
    private Batch batch;
    private Camera camera;

    // Background
    private Texture background;

    @Override
    public void show()
    {
        // init
        gameManager = new GameManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        gameManager.update(delta);

        batch.begin();
        gameManager.draw(batch);
        batch.end();
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
        batch.dispose();
    }

    private void backGround()
    {
        final float textureHeight = 1024;
        final float textureWidth = 1024;

        final float offSetHeight = textureHeight * 2;
        final float offSetWidth = textureWidth * 2;

        final int totalHeight = (int) (textureHeight * 4);
        final int totalWidth = (int) (textureWidth * 4);

//        batch.draw(background, -offSetWidth + (textureWidth * (int) (ship.getPosition().x / textureWidth)), -offSetHeight + (textureHeight * (int) (ship.getPosition().y / textureHeight)), 0, 0, totalWidth, totalHeight);
    }
}
