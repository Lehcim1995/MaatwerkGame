package interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


public interface IGameObject
{
    /**
     * @return
     */
    Vector2 getPosition();

    /**
     * @param pos
     */
    void setPosition(Vector2 pos);

    /**
     * @param translate
     */
    void translate(Vector2 translate);

    /**
     * @return
     */
    float getRotation();

    /**
     * @param rot
     */
    void setRotation(float rot);

    /**
     * @param rot
     */
    void addRotation(float rot);

    /**
     * @param Other
     */
    void onCollisionEnter(IGameObject Other);

    /**
     * @param Other
     */
    void onCollisionExit(IGameObject Other);

    /**
     * @param Other
     */
    void onCollisionStay(IGameObject Other);

    /**
     *
     */
    void update();

    /**
     * @param shapeRenderer
     */
    void Draw(ShapeRenderer shapeRenderer);

    /**
     * @param batch
     */
    void Draw(Batch batch);

    /**
     * @param shapeRenderer
     * @param batch
     */
    void Draw(ShapeRenderer shapeRenderer, Batch batch);

    /**
     * @return
     */
    long getID();

    /**
     * @return
     */
    Polygon getHitbox();
}
