package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.managers.SpaceShipTexturesHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SpaceShipEnemy extends Ship
{
    private GameObject follow;

    protected SpaceShipEnemy()
    {
    }

    public SpaceShipEnemy(GameObject follow)
    {
        this(new Vector2(0, 0), 0, new SpaceShipTexturesHelper().getSpaceShipSprite(22), follow);
        this.setHitbox(GameObject.defaultHitbox(sprite.getHeight(), sprite.getWidth()));
    }

    public SpaceShipEnemy(SpaceShipEnemy sse)
    {
        this(new Vector2(0, 0), 0, new Sprite(sse.sprite), sse.follow);
        this.setHitbox(GameObject.defaultHitbox(sprite.getHeight(), sprite.getWidth()));
    }

    public SpaceShipEnemy(Vector2 position, float rotation, Sprite sprite, GameObject follow)
    {
        this.sprite = sprite;
        mass = sprite.getHeight() * sprite.getWidth();
        mass /= 10;
        setDefaults();

        this.follow = follow;
    }

    private void setDefaults()
    {
        maxSpeed = 4;
        speed = 5;
        acceleration = 1.01f;
        deAcceleration = 0.09f;
        rotSpeed = 30;
        maxSpaceshipSpeed = 4;
        currentSpeedVector = new Vector2(0, 0);
    }

    @Override
    public void Draw(Batch batch)
    {
        sprite.draw(batch);
    }

    @Override
    public void update()
    {
        if (follow != null)
        {

            Vector2 otherPos = new Vector2(follow.getPosition());
            Vector2 myPos = new Vector2(getPosition());

            float rot = myPos.sub(otherPos).angle() - 180; // 180 because im lazy
            // TODO add max rot speed

            setRotation(rot);

            float x = MathUtils.cosDeg(getRotation());
            float y = MathUtils.sinDeg(getRotation());
            Vector2 pos = new Vector2(x, y).setLength(speed * getDeltaTime());

            currentSpeedVector.add(pos);

            translate(currentSpeedVector);

            if (currentSpeedVector.len() > maxSpaceshipSpeed)
            {
                currentSpeedVector.setLength(maxSpaceshipSpeed);
            }
        }
    }

    private float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }

    public void setFollow(GameObject follow)
    {
        this.follow = follow;
    }
}
