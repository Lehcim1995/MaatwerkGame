package interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IGameObject extends Remote
{
    /**
     * @return
     */
    Vector2 getPosition() throws RemoteException;

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
     * @return
     */
    long getID();

    /**
     * @return
     */
    Polygon getHitbox();
}
