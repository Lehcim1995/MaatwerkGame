package interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


public interface IGameObject
{
    Vector2 getPosition();

    void setPosition(Vector2 pos);

    void translate(Vector2 translate);

    float getRotation();

    void setRotation(float rot);

    void addRotation(float rot);

    void onCollisionEnter(IGameObject Other);

    void onCollisionExit(IGameObject Other);

    void onCollisionStay(IGameObject Other);

    void update();

    void Draw(ShapeRenderer shapeRenderer);

    void Draw(Batch batch);

    void Draw(ShapeRenderer shapeRenderer, Batch batch);

    boolean isHit(IGameObject go2);

    long getID();

    Polygon getHitbox();
}
