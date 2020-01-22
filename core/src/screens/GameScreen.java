package screens;

import classes.Util.TextureToSprite;
import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import classes.managers.OnlineRmiManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.school.spacegame.Main;
import interfaces.IGameLobby;

import java.rmi.RemoteException;

//http://badlogicgames.com/forum/viewtopic.php?t=19454&p=81586
public class GameScreen extends AbstractScreen
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
    private Texture test;

    //Debug
    private Box2DDebugRenderer box2DDebugRenderer;

    private boolean online;
    private GameManager.playerType type;
    private Label fpsLabel;

    private IGameLobby gameLobby;
    private String playerName;
    private ProgressBar playerHealth;

    private InputMultiplexer inputMultiplexer;

    public GameScreen(
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

        // Multiple input processors
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show()
    {
        // init
        super.show();

        try
        {
            if (gameLobby != null)
            {
                gameManager = new OnlineRmiManager(gameLobby, type, playerName, this);
            }
            else
            {
                gameManager = new GameManager(type, this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // exit
            main.sceneManager.LoadMainMenuScreen();
        }

        batch = new SpriteBatch();
        textBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        switch (gameManager.getPlayerType())
        {

            case Destroyer:
                camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                inputMultiplexer.addProcessor(gameManager.getPlayer());
                break;
            case Spawner:
                camera = new OrthographicCamera(Gdx.graphics.getWidth() * 4, Gdx.graphics.getHeight() * 4);
                inputMultiplexer.addProcessor(gameManager.getWaveSpawnerPlayer());
                break;
            case Spectator:
                camera = new OrthographicCamera(Gdx.graphics.getWidth() * 4, Gdx.graphics.getHeight() * 4);
                inputMultiplexer.addProcessor(gameManager.getSpectator());
                break;
        }

        camera.update();

        box2DDebugRenderer = new Box2DDebugRenderer();
//        box2DDebugRenderer.setDrawBodies(false);
//        box2DDebugRenderer.setDrawVelocities(false);

        font = new BitmapFont();
        layout = new GlyphLayout();

        background = new Texture(Gdx.files.local("/core/assets/textures/seamless space.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        test = new Texture(Gdx.files.local("/core/assets/textures/pasmateRs_crosshairs64/crosshairs64.png"));

        //
        fpsLabel = new Label("0", skin);
        fpsLabel.setAlignment(Align.left);

        // TODO change
        Button b = new Button(skin);
        b.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                playerHealth.setValue(100);
            }
        });
        table.add(b);
        table.row();

        playerHealth = new ProgressBar(0, 100, 1, false, skin);
        playerHealth.setValue(50);

        table.right().bottom();
        table.add(fpsLabel).left();

        stage.addActor(playerHealth);
    }

    @Override
    public void render(float delta)
    {
        // Clear frame
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

        // Draw background and batch items
        batch.begin();
        backGround();
        gameManager.draw(batch);
        batch.end();
        gameManager.update(delta);

        // Draw shapes
        shapeRenderer.begin();
        gameManager.draw(shapeRenderer);
        shapeRenderer.end();

        //TODO add timer to only update fps every so seconds
        int fps = (int) (1 / delta);
        fpsLabel.setText("Fps: " + fps);
        fpsLabel.setColor(fps < 30 ? Color.RED : Color.WHITE);

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
        switch (gameManager.getPlayerType())
        {

            case Destroyer:
                camera.viewportWidth = width * 2;
                camera.viewportHeight = height * 2;
                break;
            case Spawner:
                camera.viewportWidth = width * 4;// TODO dont use hardcoded numbers
                camera.viewportHeight = height * 4;
                break;
            case Spectator:
                break;
        }


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
        try
        {
            if (gameLobby != null)
            {
                gameLobby.removeUser(playerName);
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        batch.dispose();

        super.dispose();
    }

    private void backGround()
    {
        final float textureHeight = this.background.getHeight();
        final float textureWidth = this.background.getWidth();

        final float offSetHeight = textureHeight * 2;
        final float offSetWidth = textureWidth * 2;

        final int totalHeight = (int) (textureHeight * 4);
        final int totalWidth = (int) (textureWidth * 4);

        GameObject background = null;
        switch (type)
        {
            case Destroyer:
                background = gameManager.getPlayer();
                break;
            case Spawner:
                background = gameManager.getWaveSpawnerPlayer();
                break;
            case Spectator:
                // Exit
                return;
//                break;
        }

        batch.draw(this.background, -offSetWidth + (textureWidth * (int) (background.getPosition().x / textureWidth)), -offSetHeight + (textureHeight * (int) (background.getPosition().y / textureHeight)), 0, 0, totalWidth, totalHeight);
    }

    public Camera getCamera()
    {
        return camera;
    }
}
