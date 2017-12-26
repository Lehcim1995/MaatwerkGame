package com.school.spacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import scenes.MainScene;

// TODO \/
// https://gist.github.com/Leejjon/7fb8aa3ea2e4024a9eba31fa4f3339fb
// https://github.com/libgdx/libgdx/wiki/Scene2d
// http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
public class Main extends ApplicationAdapter
{

    private MainScene mainScene;

    @Override
    public void create()
    {
        mainScene = new MainScene();
        mainScene.show();
    }

    @Override
    public void render()
    {
        mainScene.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose()
    {
    }
}
