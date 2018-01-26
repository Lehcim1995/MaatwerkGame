package interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IGameManager
{
    void update(float deltaTime);

    void draw(Batch batch);

    void draw(ShapeRenderer shapeRenderer);
}
