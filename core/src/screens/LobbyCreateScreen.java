package screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.school.spacegame.Main;

import java.rmi.RemoteException;

public class LobbyCreateScreen extends AbstractScreen
{

    public LobbyCreateScreen(Main main)
    {
        super(main);
    }

    @Override
    public void show()
    {
        super.show();
        Label labelLobbys = new Label("Create new lobby", skin);
        labelLobbys.setFontScale(2);
        labelLobbys.setAlignment(Align.center);

        TextButton backButton = new TextButton("Back", skin);
        TextButton createLobby = new TextButton("Create", skin);

        TextField lobbyName = new TextField("Lobby name", skin);

        table.add(labelLobbys);
        table.row();
        table.add(lobbyName).fillX();
        table.add(createLobby).fillX();
        table.row();
        table.add();
        table.add(backButton).right();

        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                main.sceneManager.LoadLobbyScreen();
            }
        });

        createLobby.addListener(new ChangeListener()
        {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor)
            {
                try
                {
                    main.getServer().createLobby(lobbyName.getText());
                    main.sceneManager.LoadLobbyScreen();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace(); // TODO throw error

                }
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
        super.dispose();
    }
}
