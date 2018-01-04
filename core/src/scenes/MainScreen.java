package scenes;

import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

//http://badlogicgames.com/forum/viewtopic.php?t=19454&p=81586
public class MainScreen implements Screen
{
    private GameManager gameManager;

    // Camera and drawing
    private Batch batch;
    private Batch textBatch;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private float zoomLevel = 5;

    // Text stuff
    private BitmapFont font;
    private GlyphLayout layout;

    // Background
    private Texture background;

    //Debug
    private Box2DDebugRenderer box2DDebugRenderer;

    @Override
    public void show()
    {
        // init
        gameManager = new GameManager();
        batch = new SpriteBatch();
        textBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        box2DDebugRenderer = new Box2DDebugRenderer();

        font = new BitmapFont();
        layout = new GlyphLayout();

        background = new Texture(Gdx.files.local("/core/assets/textures/seamless space.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        camera.position.set(gameManager.getPlayer().getPosition(), 0);
        camera.update();

        batch.begin();
        backGround();
        gameManager.draw(batch);
        batch.end();
        gameManager.update(delta);

//        shapeRenderer.begin();
//        gameManager.draw(shapeRenderer);
//        shapeRenderer.end();

        textBatch.begin();
        int fps = (int) (1 / delta);
        if (fps < 30)
        {
            font.setColor(Color.RED);
        }
        else
        {
            font.setColor(Color.WHITE);
        }
        final Vector2 pos = new Vector2(100, 100);
        gameManager.getPlayer().DrawText(textBatch, font, layout, "Fps: " + fps, pos);
        textBatch.end();

        box2DDebugRenderer.render(gameManager.getWorldManager().world, camera.combined);
    }

    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        textBatch = new SpriteBatch();
//        textBatch.setProjectionMatrix(camera.combined);
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

        GameObject player = gameManager.getPlayer();

        batch.draw(background, -offSetWidth + (textureWidth * (int) (player.getPosition().x / textureWidth)), -offSetHeight + (textureHeight * (int) (player.getPosition().y / textureHeight)), 0, 0, totalWidth, totalHeight);
    }
}
