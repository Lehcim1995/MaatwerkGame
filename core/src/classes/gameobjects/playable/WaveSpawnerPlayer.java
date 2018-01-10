package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import interfaces.IGameObject;

public class WaveSpawnerPlayer extends GameObject
{


    @Override
    public void onCollisionEnter(IGameObject other)
    {

    }

    @Override
    public void onCollisionExit(IGameObject other)
    {

    }

    @Override
    public void onCollisionStay(IGameObject other)
    {

    }

    @Override
    public void update()
    {
        //TODO change pos when moving

        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            position.x -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            position.x += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            position.y += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            position.y -= 1;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            // TODO spawn
        }
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {

    }

    @Override
    public void Draw(Batch batch)
    {

    }
}
