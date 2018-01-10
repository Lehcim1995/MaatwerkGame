package classes.managers;

import com.school.spacegame.Main;
import screens.*;

public class SceneManager
{
    private Main main;

    private MainScreen mainScreen;
    private MainMenuScreen mainMenuScreen;
    private OptionsScreen optionsScreen;
    private LobbyScreen lobbyScreen;
    private LobbyCreateScreen lobbyCreateScreen;
    // TODO add Server screen

    public SceneManager(Main main)
    {
        this.main = main;
    }

    public void LoadMainScreen()
    {
        mainScreen = new MainScreen(main);
        main.setScreen(mainScreen);
    }

    public void LoadMainMenuScreen()
    {
        mainMenuScreen = new MainMenuScreen(main);
        main.setScreen(mainMenuScreen);
    }

    public void LoadLobbyScreen()
    {
        lobbyScreen = new LobbyScreen(main);
        main.setScreen(lobbyScreen);
    }

    public void LoadLobbyCreateScreen()
    {
        lobbyCreateScreen = new LobbyCreateScreen(main);
        main.setScreen(lobbyCreateScreen);
    }
}
