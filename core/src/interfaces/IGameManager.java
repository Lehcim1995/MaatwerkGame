package interfaces;

import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.unplayble.Laser;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import screens.GameScreen;

import java.util.List;

public interface IGameManager
{
    /**
     * Adds an object to the update pool
     *
     * @param object
     * @return
     */
    IGameObject createObject(IGameObject object);

    /**
     * Removes a object from the update pool
     *
     * @param object
     */
    void deleteObject(IGameObject object);

    /**
     * Updates all the objects int the update pool
     *
     * @param deltaTime
     */
    void update(float deltaTime);

    /**
     * Draws all the batch draw call from the objects in the update pool
     *
     * @param batch
     */
    void draw(Batch batch);

    /**
     * Draws all the shape draw call from the objects in the update pool
     *
     * @param shapeRenderer
     */
    void draw(ShapeRenderer shapeRenderer);

    List<IGameObject> getObjects();

    GameScreen getGameScreen();

    SpaceShipEnemy createEnemy(
            Vector2 pos,
            float v);

    Laser fireLaser(
            Vector2 pos,
            float speed,
            float rotation);

    //TODO rename parameters and add method add createPlayer
}
