package screens;

import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Align;
import com.school.spacegame.Main;
import interfaces.IGameLobby;

import java.rmi.RemoteException;

//http://badlogicgames.com/forum/viewtopic.php?t=19454&p=81586
public class MainScreen extends AbstractScreen
{
    private GameManager gameManager;

    // Camera and drawing
    private Batch batch;
    private Batch textBatch;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private float zoomLevel = 5;
    private GameObject textDrawer;

    // Text stuff
    private BitmapFont font;
    private GlyphLayout layout;

    // Background
    private Texture background;

    //Debug
    private Box2DDebugRenderer box2DDebugRenderer;

    private boolean online;
    private GameManager.playerType type;
    private Label labelLobbys;

    private IGameLobby gameLobby;
    private String playerName;
    private ProgressBar playerHealth;

    public MainScreen(
            Main parent,
            IGameLobby gameLobby,
            String playerName,
            GameManager.playerType type)
    {
        super(parent);
        this.gameLobby = gameLobby;
        this.playerName = playerName;
        this.online = gameLobby != null;
        this.type = type;
    }

    @Override
    public void show()
    {
        // init
        super.show();

        try
        {
            gameManager = new GameManager(gameLobby, type, playerName, this);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            // exit
            main.sceneManager.LoadMainMenuScreen();
        }
        batch = new SpriteBatch();
        textBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        box2DDebugRenderer = new Box2DDebugRenderer();

        font = new BitmapFont();
        layout = new GlyphLayout();

        background = new Texture(Gdx.files.local("/core/assets/textures/seamless space.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //
        labelLobbys = new Label("0", skin);
        labelLobbys.setAlignment(Align.left);

        playerHealth = new ProgressBar(0, 100, 1, false, skin);
        playerHealth.setValue(50);

        table.right().bottom();
        table.add(labelLobbys).left();

        stage.addActor(playerHealth);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (gameManager.getPlayerType())
        {
            case Destroyer:
                camera.position.set(gameManager.getPlayer().getPosition(), 0);
                break;
            case Spawner:
                camera.position.set(gameManager.getWaveSpawnerPlayer().getPosition(), 0);
                break;
        }
        camera.update();

        batch.begin();
        backGround();
        gameManager.draw(batch);
        batch.end();
        gameManager.update(delta);

        shapeRenderer.begin();
        gameManager.draw(shapeRenderer);
        shapeRenderer.end();

        int fps = (int) (1 / delta);
        labelLobbys.setText("Fps: " + fps);
        labelLobbys.setColor(fps < 30 ? Color.RED : Color.WHITE);

        box2DDebugRenderer.setDrawBodies(true);
        box2DDebugRenderer.setDrawVelocities(true);
        box2DDebugRenderer.render(gameManager.getWorldManager().world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        if (gameManager.getPlayer() != null)
        {
            Vector3 world = new Vector3(gameManager.getPlayer().getPosition().x, gameManager.getPlayer().getPosition().y, 0);
            Vector3 view = camera.project(world);
            playerHealth.setPosition(view.x - playerHealth.getWidth() / 2, view.y);
        }

        // Draw UI
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(
            int width,
            int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        textBatch = new SpriteBatch();

        super.resize(width, height);
    }

    @Override
    public void pause()
    {
        super.pause();
    }

    @Override
    public void resume()
    {
        super.resume();
    }

    @Override
    public void hide()
    {
        super.hide();
    }

    @Override
    public void dispose()
    {
        gameManager.dispose();
        batch.dispose();

        super.dispose();
    }

    private void backGround()
    {
        final float textureHeight = 1024;
        final float textureWidth = 1024;

        final float offSetHeight = textureHeight * 2;
        final float offSetWidth = textureWidth * 2;

        final int totalHeight = (int) (textureHeight * 4);
        final int totalWidth = (int) (textureWidth * 4);

        GameObject player = null;
        switch (type)
        {
            case Destroyer:
                player = gameManager.getPlayer();
                break;
            case Spawner:
                player = gameManager.getWaveSpawnerPlayer();
                break;
            case Spectator:
                // Exit
                return;
//                break;
        }

        batch.draw(background, -offSetWidth + (textureWidth * (int) (player.getPosition().x / textureWidth)), -offSetHeight + (textureHeight * (int) (player.getPosition().y / textureHeight)), 0, 0, totalWidth, totalHeight);
    }

    public Camera getCamera()
    {
        return camera;
    }
}
