package com.school.spacegame;

import classes.managers.SceneManager;
import com.badlogic.gdx.Game;
import screens.MainMenuScreen;
import screens.MainScreen;

// TODO \/
// https://gist.github.com/Leejjon/7fb8aa3ea2e4024a9eba31fa4f3339fb
// https://github.com/libgdx/libgdx/wiki/Scene2d
// http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
public class Main extends Game
{

    private MainScreen mainScreen;
    private MainMenuScreen mainMenuScreen;
    private SceneManager sceneManager;

    @Override
    public void create()
    {
        mainScreen = new MainScreen();
        mainMenuScreen = new MainMenuScreen();
        setScreen(mainMenuScreen);
    }
}
