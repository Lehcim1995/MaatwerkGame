package classes.managers;

import com.school.spacegame.Main;
import screens.MainMenuScreen;
import screens.MainScreen;
import screens.OptionsScreen;

public class SceneManager
{
    private Main main;

    private MainScreen mainScreen;
    private MainMenuScreen mainMenuScreen;
    private OptionsScreen optionsScreen;
    // TODO add Server screen

    public SceneManager(Main main)
    {
        this.main = main;
        mainScreen = new MainScreen(main);
        mainMenuScreen = new MainMenuScreen(main);
        optionsScreen = new OptionsScreen(main);
    }

    public void LoadMainScreen()
    {
        main.setScreen(mainScreen);
    }

    public void LoadMainMenuScreen()
    {
        main.setScreen(mainMenuScreen);
    }
}
