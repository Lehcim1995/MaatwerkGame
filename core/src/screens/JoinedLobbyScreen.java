package screens;

import classes.managers.GameManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.school.spacegame.Main;
import interfaces.IGameLobby;

import java.rmi.RemoteException;

public class JoinedLobbyScreen extends AbstractScreen
{
    private List<String> playersSelectBox;
    private IGameLobby lobby;
    private String playerName;

    public JoinedLobbyScreen(
            Main main,
            IGameLobby lobby,
            String playerName)
    {
        super(main);
        this.lobby = lobby;
        this.playerName = playerName;
    }

    @Override
    public void show()
    {
        super.show();

        TextButton backButton = new TextButton("Back", skin);
        TextButton startButton = new TextButton("Start", skin);
        TextButton createLobbyButton = new TextButton("Create", skin);

        playersSelectBox = new List<>(skin);
        SelectBox<String> playerTypeSelectBox = new SelectBox<>(skin);
        Label playerNameLabel = new Label(playerName, skin);

        playerTypeSelectBox.setItems("Destroyer", "Spawner", "Spectator");

        String names[] = new String[0];
        try
        {
            if (lobby != null)
            {
                names = new String[lobby.getPlayers().size()];
                lobby.getPlayers().toArray(names);
                playersSelectBox.setItems(names);
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        Label labelLobbys = new Label("Lobby's", skin);
        labelLobbys.setFontScale(2);
        labelLobbys.setAlignment(Align.center);

        table.add(labelLobbys);
        table.row().pad(10, 0, 10, 0);
        table.add(playersSelectBox).colspan(4).expandY().fillX();
        table.row();
        table.add(playerTypeSelectBox);
        table.add(playerNameLabel);
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
//                main.sceneManager.LoadMainMenuScreen();
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
                if (playersSelectBox.getSelected().isEmpty())
                {
                    System.out.println("Nothing selected");
                    //TODO show error
                }
                // TODO select the current selected rol
                main.sceneManager.LoadMainScreen(true, GameManager.playerType.Destroyer);
            }
        });
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
    }

    @Override
    public void resize(
            int width,
            int height)
    {
        super.resize(width, height);
    }
}
