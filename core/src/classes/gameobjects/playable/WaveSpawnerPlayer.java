package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class WaveSpawnerPlayer extends GameObject
{

    private GameManager gameManager;

    public WaveSpawnerPlayer(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

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
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Vector2 mousePos = new Vector2(x, y);

            int spawn = 10;
            float radius = 500;
            float angle = 360 / spawn;
            Vector2 middle = new Vector2(mousePos);

            for (int i = 0; i < spawn; i++)
            {
                // TODO refactor
                // Make a vector with a radius
                Vector2 pos = new Vector2(radius, 0);
                pos.rotate(angle * i);
                pos.add(middle);

                gameManager.createEnemy(pos, angle * i);
            }
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
