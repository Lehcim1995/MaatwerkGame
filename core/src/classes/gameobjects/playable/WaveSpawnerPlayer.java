package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import interfaces.IGameObject;

public class WaveSpawnerPlayer extends GameObject
{

    private GameManager gameManager;
    private boolean hasSpawned;

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

            if (!hasSpawned)
            {
                int x = Gdx.input.getX();

                hasSpawned = true;
                int y = Gdx.input.getY();
                Vector2 mousePos = new Vector2(x, y);

                int spawn = 10;
                float radius = 10;
                float angle = 360 / spawn;
                Vector3 vector3 = new Vector3(mousePos.x, mousePos.y, 0);
                Vector3 screenpos = gameManager.getMainScreen().getCamera().unproject(vector3);
                Vector2 middle = new Vector2(screenpos.x, screenpos.y);

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

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if (hasSpawned = true)
            {
                hasSpawned = false;
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
