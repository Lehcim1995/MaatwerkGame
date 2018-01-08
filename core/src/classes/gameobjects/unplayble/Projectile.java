package classes.gameobjects.unplayble;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameObject
{
    private float speed;
    private Vector2 dirVector;

    @Override
    public void update()
    {
        super.update();

    }

    @Override
    public void Draw(Batch batch)
    {
        super.Draw(batch);
        if (sprite != null)
        {
            sprite.draw(batch);
        }
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(position.x, position.y, 5);
        super.Draw(shapeRenderer);
    }

    private float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }
}
