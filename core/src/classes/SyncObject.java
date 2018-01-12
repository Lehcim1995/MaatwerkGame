package classes;

import com.badlogic.gdx.math.Vector2;
import interfaces.ISyncObject;

public class SyncObject implements ISyncObject
{
    private Vector2 position;
    private Vector2 linearVelocity;
    private float rotation;
    private float angularVelocity;
    private boolean isAwake;
    private int shipSpriteId;

    private Long id;

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return linearVelocity;
    }

    @Override
    public void setLinearVelocity(Vector2 linearVelocity)
    {
        this.linearVelocity = linearVelocity;
    }

    @Override
    public float getRotation()
    {
        return rotation;
    }

    @Override
    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    @Override
    public float getAngularVelocity()
    {
        return angularVelocity;
    }

    @Override
    public void setAngularVelocity(float angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public boolean isAwake()
    {
        return isAwake;
    }

    @Override
    public void setAwake(boolean awake)
    {
        isAwake = awake;
    }

    @Override
    public int getShipSpriteId()
    {
        return shipSpriteId;
    }

    @Override
    public void setShipSpriteId(int shipSpriteId)
    {
        this.shipSpriteId = shipSpriteId;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }
}
