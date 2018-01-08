package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SpaceShipEnemy extends Ship
{
    private GameObject follow;

    protected SpaceShipEnemy()
    {
    }

    public SpaceShipEnemy(Vector2 position, float rotation, Sprite sprite, GameObject follow)
    {
        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;

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
    public void Draw(ShapeRenderer shapeRenderer)
    {
        if (follow != null)
        {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.line(position, follow.getPosition());


            Vector2 disPlayerPos = new Vector2(position);
            Vector2 disFollowPos = new Vector2(follow.getPosition());

            Vector2 towardsPlayer = disFollowPos.sub(disPlayerPos);
            disPlayerPos.add(towardsPlayer.nor().scl(10));

            shapeRenderer.setColor(Color.RED);
            shapeRenderer.line(position, disPlayerPos);

        }
    }

    @Override
    public void update()
    {
//        this.position = fixture.getBody().getPosition();
//
//        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
//        sprite.setRotation(rotation);

        super.update();

        //noinspection Duplicates
        if (follow != null)
        {
//            fixture.getBody().applyForceToCenter(getForward().scl(200000), false);
//            fixture.getBody().applyForce(getLeft().scl(200000), getForward().scl(sprite.getWidth() / 2), true);

            Vector2 disPlayerPos = new Vector2(position);
            Vector2 disFollowPos = new Vector2(follow.getPosition());

            Vector2 towardsPlayer = disFollowPos.sub(disPlayerPos);
            towardsPlayer.nor();

            fixture.getBody().applyForceToCenter(towardsPlayer.scl(200000), false);

//            rotation = (float) Math.toDegrees(towardsPlayer.angleRad());

            //tODO add rotation

//            fixture.getBody().setAngularVelocity();


            follow.getPosition().angleRad(towardsPlayer);
        }
    }

    public void setFollow(GameObject follow)
    {
        this.follow = follow;
    }
}
