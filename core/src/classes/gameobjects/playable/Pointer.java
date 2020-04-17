package classes.gameobjects.playable;

import classes.gameobjects.GameObject;
import classes.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import interfaces.IGameObject;

public class Pointer extends GameObject implements InputProcessor
{
    private GameManager gm;
    private int screenX;
    private int screenY;

    public Pointer(GameManager gm) {
        this.gm = gm;
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



        position.x = (gm.getGameScreen().getCamera().position.x + (Gdx.input.getX() * 2)) - (Gdx.graphics.getWidth()) ;
        position.y = (gm.getGameScreen().getCamera().position.y - (Gdx.input.getY() * 2)) + (Gdx.graphics.getHeight());

//        position.x = (gm.getGameScreen().getCamera().position.x + (screenX * 2)) - (Gdx.graphics.getWidth()) ;
//        position.y = (gm.getGameScreen().getCamera().position.y - (screenY * 2)) + (Gdx.graphics.getHeight());

        sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(
            int screenX,
            int screenY,
            int pointer)
    {
        this.screenX = screenX;
        this.screenY = screenY;

        return false;
    }

    @Override
    public boolean mouseMoved(
            int screenX,
            int screenY)
    {

        this.screenX = screenX;
        this.screenY = screenY;

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
