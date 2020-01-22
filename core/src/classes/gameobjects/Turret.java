package classes.gameobjects;

import classes.gameobjects.unplayble.Projectile;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import interfaces.IGameObject;

public class Turret extends GameObject
{
    private Projectile projectile;
    private float turnRate = 1;
    private float shootSpeed = 1;
    private int clipSize = 10;
    private int currentClip = 10;
    private boolean reloading = false;

    public void Shoot()
    {

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

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void Draw(Batch batch) {

    }
}
