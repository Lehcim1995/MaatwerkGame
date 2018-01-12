package interfaces;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.rmi.Remote;

public interface ISyncObject extends Remote, Serializable
{
    Vector2 getPosition();

    void setPosition(Vector2 position);

    Vector2 getLinearVelocity();

    void setLinearVelocity(Vector2 linearVelocity);

    float getRotation();

    void setRotation(float rotation);

    float getAngularVelocity();

    void setAngularVelocity(float angularVelocity);

    boolean isAwake();

    void setAwake(boolean awake);

    int getShipSpriteId();

    void setShipSpriteId(int shipSpriteId);

    Long getId();

    void setId(Long id);

    String getObjectType();

    void setObjectType(String objectType);
}
