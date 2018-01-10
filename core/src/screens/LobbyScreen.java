package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.school.spacegame.Main;

public class LobbyScreen implements Screen
{
    private Stage stage;
    private Main main;

    public LobbyScreen(Main main)
    {
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.main = main;
    }

    @Override
    public void show()
    {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.local("/core/assets/skins/neon/skin/neon-ui.json"));

        TextButton backButton = new TextButton("Back", skin);
        TextButton startButton = new TextButton("Join", skin);
        TextButton createLobbyButton = new TextButton("Create", skin);

        List<String> listBox = new List<>(skin);
        TextField playerName = new TextField("Playername", skin);
        // TODO get this data from the server
//        listBox.setItems("1", "2", "3");

        table.add(listBox).colspan(3).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(playerName);
        table.add(startButton);
        table.add(createLobbyButton);
        table.row();
        table.add(backButton).fillX().uniformX().right();

        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                main.sceneManager.LoadMainMenuScreen();
            }
        });

        createLobbyButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                main.sceneManager.LoadLobbyCreateScreen();
            }
        });
    }

    @Override
    public void render(float delta)
    {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(
            int width,
            int height)
    {
        // change the stage's viewport when teh screen size is changed
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
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}
