package screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.school.spacegame.Main;

public class ConnectToServerScreen extends AbstractScreen
{
    private SelectBox<String> lobbySelectBox;

    public ConnectToServerScreen(Main main) {
        super(main);
    }

    @Override
    public void show()
    {
        super.show();

        TextButton backButton = new TextButton("Back", skin);
        TextButton startButton = new TextButton("Join", skin);
        TextButton deleteButton = new TextButton("Delete", skin);
        TextButton createLobbyButton = new TextButton("Create", skin);

        lobbySelectBox = new SelectBox<>(skin);

        Label labelLobbys = new Label("Servers", skin);
        labelLobbys.setFontScale(2);
        labelLobbys.setAlignment(Align.center);

        table.add(labelLobbys);
        table.row().pad(10, 0, 10, 0);
        table.add(lobbySelectBox).colspan(4).fillX();
        table.row();
        table.add(startButton);
        table.add(createLobbyButton);
        table.add(deleteButton);
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
    }
}
