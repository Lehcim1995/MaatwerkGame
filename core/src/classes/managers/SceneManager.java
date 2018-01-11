package classes.managers;

import com.school.spacegame.Main;
import interfaces.IGameLobby;
import screens.*;

public class SceneManager
{
    private Main main;

    private MainScreen mainScreen;
    private MainMenuScreen mainMenuScreen;
    private OptionsScreen optionsScreen;
    private LobbyScreen lobbyScreen;
    private LobbyCreateScreen lobbyCreateScreen;
    private JoinedLobbyScreen joinedLobbyScreen;
    // TODO add Server screen

    public SceneManager(Main main)
    {
        this.main = main;
    }

    public void LoadMainScreen()
    {
        LoadMainScreen(false, GameManager.playerType.Destroyer);
    }

    public void LoadMainScreenSpawner()
    {
        LoadMainScreen(false, GameManager.playerType.Spawner);
    }

    public void LoadMainScreen(
            boolean online,
            GameManager.playerType type)
    {
        mainScreen = new MainScreen(main, online, type);
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

    public void LoadGameLobbyScreen(
            IGameLobby gameLobby,
            String playerName)
    {
        joinedLobbyScreen = new JoinedLobbyScreen(main, gameLobby, playerName);
        main.setScreen(joinedLobbyScreen);
    }

    public void LoadLobbyCreateScreen()
    {
        lobbyCreateScreen = new LobbyCreateScreen(main);
        main.setScreen(lobbyCreateScreen);
    }
}
