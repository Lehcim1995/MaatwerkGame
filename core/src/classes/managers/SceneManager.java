package classes.managers;

import com.school.spacegame.Main;
import interfaces.IGameLobby;
import screens.*;

public class SceneManager
{
    private Main main;

    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;
    private OptionsScreen optionsScreen;
    private LobbyScreen lobbyScreen;
    private LobbyCreateScreen lobbyCreateScreen;
    private JoinedLobbyScreen joinedLobbyScreen;
    private ConnectToServerScreen serverScreen;
    // TODO add Server screen

    public SceneManager(Main main)
    {
        this.main = main;
    }

    public void LoadScreen(String screenName, Object[] ...parms)
    {
        // TODO

    }

    public void LoadMainScreen()
    {
        LoadMainScreen(null, "", GameManager.playerType.Destroyer);
    }

    public void LoadMainScreenSpawner()
    {
        LoadMainScreen(null, "", GameManager.playerType.Spawner);
    }

    public void LoadMainScreen(
            IGameLobby gameLobby,
            String playerName,
            GameManager.playerType type)
    {
        gameScreen = new GameScreen(main, gameLobby, playerName, type);
        main.setScreen(gameScreen);
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

    public void LoadServerScreen()
    {
        serverScreen = new ConnectToServerScreen(main);
        main.setScreen(serverScreen);
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

    public void LoadOptionsScreen()
    {
        optionsScreen = new OptionsScreen(main);
        main.setScreen(optionsScreen);
    }
}
