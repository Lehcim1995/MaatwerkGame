package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.school.spacegame.Main;
import interfaces.IGameLobby;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class LobbyScreen extends AbstractScreen
{
    private Registry registry;
    private SelectBox<String> lobbySelectBox;


    public LobbyScreen(Main main)
    {
        super(main);
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.main = main;
    }

    @Override
    public void show()
    {
        TextButton backButton = new TextButton("Back", skin);
        TextButton startButton = new TextButton("Join", skin);
        TextButton createLobbyButton = new TextButton("Create", skin);

        lobbySelectBox = new SelectBox<>(skin);
        TextField playerName = new TextField("Player name", skin);

        loadLobbys();

        Label labelLobbys = new Label("Lobby's", skin);
        labelLobbys.setFontScale(2);
        labelLobbys.setAlignment(Align.center);

        table.add(labelLobbys);
        table.row().pad(10, 0, 10, 0);
        table.add(lobbySelectBox).colspan(4).fillX();
        table.row();
        table.add(playerName);
        table.add(startButton);
        table.add(createLobbyButton);
        table.row();
        table.add();
        table.add();
        table.add();
        table.add(backButton).right().fillX().uniformX();

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

        startButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                if (lobbySelectBox.getSelected().isEmpty())
                {
                    System.out.println("Nothing selected");
                    //TODO show error
                }
                // TODO select the current selected rol
//                main.sceneManager.LoadMainScreen(true, GameManager.playerType.Destroyer);
                IGameLobby gameLobby = null;
                try
                {
                    String lobbyname = lobbySelectBox.getSelected();
                    gameLobby = main.getServer().joinLobby(lobbyname, playerName.getText());
                    main.sceneManager.LoadGameLobbyScreen(gameLobby, playerName.getText());
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadLobbys()
    {
        String[] lobbies;
        try
        {
            if (main.getServer() != null)
            {
                lobbies = new String[main.getServer().getLobbies().size()];
                main.getServer().getLobbies().toArray(lobbies);
                lobbySelectBox.setItems(lobbies);
            }
            else
            {
                main.sceneManager.LoadMainMenuScreen();
            }
        }
        catch (RemoteException e)
        {
            // Error
            main.sceneManager.LoadMainMenuScreen();
        }
    }

    @Override
    public void render(float delta)
    {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        loadLobbys();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
}
