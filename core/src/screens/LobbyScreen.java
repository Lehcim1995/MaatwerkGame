package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.school.spacegame.Main;
import interfaces.IServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class LobbyScreen implements Screen
{
    private Stage stage;
    private Main main;
    private Registry registry;
    private SelectBox<String> lobbySelectBox;


    public LobbyScreen(Main main)
    {
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.main = main;
    }

    private void getRemoteLobbies()
    {

        InetAddress localhost = null;
        try
        {
            localhost = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
            //Log this
            java.util.logging.Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: UnknownHostException: " + e.getMessage());
        }
        String ip = "";
        int portNumber = 1099;

        try
        {
            registry = LocateRegistry.getRegistry(ip, portNumber);
            String serverManger = IServer.ServerManger;
            IServer tempServer = (IServer) registry.lookup(serverManger);

            String[] lobbies = new String[tempServer.getLobbies().size()];
            tempServer.getLobbies().toArray(lobbies);
            lobbySelectBox.setItems(lobbies);
        }
        catch (RemoteException e)
        {
            //Log this
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: RemoteExeption: " + e.getMessage());
        }
        catch (NotBoundException e)
        {
            //Log this
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: NotBoundException: " + e.getMessage());
        }
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

        lobbySelectBox = new SelectBox<>(skin);
        SelectBox<String> playerTypeSelectBox = new SelectBox<>(skin);
        TextField playerName = new TextField("Playername", skin);

        playerTypeSelectBox.setItems("Destroyer", "Spawner", "Spectator");
//
//        // TODO get this data from the server
//        lobbySelectBox.setItems("a", "b", "c");

        Label labelLobbys = new Label("Lobby's", skin);
        labelLobbys.setFontScale(2);
        labelLobbys.setAlignment(Align.center);

        table.add(labelLobbys);
        table.row().pad(10, 0, 10, 0);
        table.add(lobbySelectBox).colspan(4).fillX();
        table.row();
        table.add(playerTypeSelectBox);
        table.add(playerName);
        table.add(startButton);
        table.add(createLobbyButton);
        table.row();
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
                main.sceneManager.LoadMainScreen(true);
            }
        });

        lobbySelectBox.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                //TODO fix this
            }
        });

        getRemoteLobbies();
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
