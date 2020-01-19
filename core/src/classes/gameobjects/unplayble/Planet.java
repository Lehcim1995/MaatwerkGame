package classes.gameobjects.unplayble;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class Planet extends GameObject
{
    private float mass;
    private float size;

    public Planet(
            Vector2 position,
            float rotation,
            Sprite sprite)
    {

        this.sprite = sprite;
        this.rotation = rotation;
        this.position = position;



        mass = 50;
        size = 50;

        sprite.setScale(size);
    }

    @Override
    public void onCollisionEnter(IGameObject other) {

    }

    @Override
    public void onCollisionExit(IGameObject other) {

    }

    @Override
    public void onCollisionStay(IGameObject other) {

    }

    @Override
    public void update() {
        this.position = fixture.getBody().getPosition();
        this.rotation = (float) Math.toDegrees(fixture.getBody().getAngle());

        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
        sprite.setRotation(rotation);
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void Draw(Batch batch) {
        sprite.draw(batch);
    }
}
