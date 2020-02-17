package classes.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import interfaces.IGameObject;

public class ShockWave extends GameObject
{
    private String vertexShader ;
    private String fragmentShader ;
    private ShaderProgram shaderProgram;
    private float time;

    private boolean disabled;

    private float shockWavePositionX;
    private float shockWavePositionY;

    private Texture texture;

    public ShockWave() {

        time = 0;
        vertexShader = Gdx.files.internal("core/assets/shaders/Planet/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("core/assets/shaders/Planet/fragment.glsl").readString();
        texture = new Texture(Gdx.files.internal("core/assets/textures/checkermap.png"));
//        texture = new Texture(512,512, Pixmap.Format.RGBA8888);
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        System.out.println(shaderProgram.isCompiled());
        System.out.println(shaderProgram.getLog());
        shaderProgram.pedantic = false;

        this.shockWavePositionX = -250;
        this.shockWavePositionY = -250;
        time = 0;
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
        time+=delta;
        if (time > 1)
        {
            time = 0;
        }


    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void Draw(Batch batch) {

        batch.setShader(shaderProgram);
        batch.draw(texture, position.x, position.y);
        shaderProgram.begin();
        shaderProgram.setUniformf("time", time);
        //shaderProgram.setUniformf("center", new Vector2(0.5f, 0.5f));
        shaderProgram.end();
        batch.setShader(null);
    }
}
