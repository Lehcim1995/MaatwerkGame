package classes.gameobjects.unplayble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class Laser extends Projectile
{
    private final IGameObject follow;
    private static final Texture Beams = new Texture(Gdx.files.internal("core/assets/textures/beams.png"));
    private static final Sprite BeamsSprite = new Sprite(Beams, 6, 7, 16, 22);

    public Laser(Vector2 position, float rotation, float speed)
    {
        follow = null;
        this.sprite = BeamsSprite;
        this.position = position;
        this.rotation = rotation;

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }

    @Override
    public void Draw(Batch batch)
    {
        super.Draw(batch);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void update()
    {
        super.update();

        if (follow != null)
        {
            Vector2 disPlayerPos = new Vector2(position);
            Vector2 disFollowPos = new Vector2(follow.getPosition());

            Vector2 towardsPlayer = disFollowPos.sub(disPlayerPos);
            towardsPlayer.nor();

            fixture.getBody().applyForceToCenter(towardsPlayer.scl(200000), false);

            follow.getPosition().angleRad(towardsPlayer);
        }
    }

    @Override
    public void onCollisionEnter(IGameObject other)
    {
        // Always set delete flag when colliding
        toDelete = true;
    }
}
