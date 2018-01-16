package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.school.spacegame.Main;

import static com.school.spacegame.Main.ip;

public class MainMenuScreen implements Screen
{
    private Stage stage;
    private Main main;

    public MainMenuScreen(Main main)
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.main = main;
    }

    @Override
    public void show()
    {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.local("/core/assets/skins/neon/skin/neon-ui.json"));

        TextButton startGame = new TextButton("Local Destroyer Game", skin);
        TextButton startGameOther = new TextButton("Local Spawner Game", skin);
        TextButton lobby = new TextButton("Lobby's", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);

        Label label = new Label("Space game", skin);
        label.setFontScale(2);
        label.setAlignment(Align.center);

        Label serverIp = new Label("Server ip : " + ip, skin);
        label.setAlignment(Align.center);

        // TODO disable Lobby when no server is found
        // TODO add new button to connect to server

        table.add(label).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(startGame).fillX().uniformX();
        table.add(startGameOther).fillX().uniformX();
        table.row();
        table.add(lobby).fillX().uniformX().colspan(2);
        table.row();
        table.add(preferences).fillX().uniformX().colspan(2);
        table.row();
        table.add(exit).fillX().uniformX().colspan(2);
        table.row();
        table.add(serverIp);

        // create button listeners
        exit.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                Gdx.app.exit();
            }
        });

        startGame.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                System.out.println("new Game");
                main.sceneManager.LoadMainScreen();
            }
        });

        startGameOther.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                System.out.println("new Game");
                main.sceneManager.LoadMainScreenSpawner();
            }
        });

        lobby.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                System.out.println("Lobby");
                main.sceneManager.LoadLobbyScreen();
            }
        });

        preferences.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                System.out.println("Settings");
            }
        });


        lobby.setDisabled(main.getServer() == null);

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(
            int width,
            int height)
    {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

}
