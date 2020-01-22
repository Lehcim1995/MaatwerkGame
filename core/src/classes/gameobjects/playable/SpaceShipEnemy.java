package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.gameobjects.unplayble.Projectile;
import classes.managers.AI.DefaultAIManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.AIManager;
import interfaces.IGameObject;

public class SpaceShipEnemy extends Ship
{
    private GameObject follow;
    private AIManager aiManager;

    protected SpaceShipEnemy()
    {
    }

    public SpaceShipEnemy(
            Vector2 position,
            float rotation,
            Sprite sprite,
            GameObject follow)
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
        maxHealth = 100;
        health = maxHealth;
        aiManager = new DefaultAIManager();
    }

    @Override
    public void Draw(Batch batch)
    {
        sprite.draw(batch);
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {
    }

    @Override
    public void update(float delta)
    {
//        this.position = fixture.getBody().getPosition();
//
//        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
//        sprite.setRotation(rotation);

        super.update(delta);

        aiManager.Move();

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

        if (health <= 0)
        {
            toDelete = true;
        }
    }

    @Override
    public void update()
    {

    }

    public void setFollow(GameObject follow)
    {
        this.follow = follow;
    }

    @Override
    public void onCollisionEnter(IGameObject other)
    {
        if (other instanceof Projectile)
        {
            Projectile p = (Projectile) other;
            health -= p.getDamage();
        }
    }

    @Override
    public void onCollisionExit(IGameObject other)
    {

    }

    @Override
    public void onCollisionStay(IGameObject other)
    {

    }
}
