package classes.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import interfaces.IGameObject;

public class ShockWave extends GameObject
{
    private FrameBuffer fbo;
    private String vertexShader ;
    private String fragmentShader ;
    private ShaderProgram shaderProgram;
    private float time;

    private boolean disabled;

    private float shockWavePositionX;
    private float shockWavePositionY;

    private Texture texture;

    public ShockWave() {
        disabled = false;
        time = 0;
        vertexShader = Gdx.files.internal("core/assets/shaders/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("core/assets/shaders/fragment.glsl").readString();
        texture = new Texture(Gdx.files.internal("core/assets/badlogic.jpg"));
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        shaderProgram.pedantic = false;

        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);

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
            //disabled =! disabled;
        }
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void Draw(Batch batch) {

        if (!disabled)
        {
//            batch.end();
////            batch.flush();
////            fbo.begin();
////            batch.begin();
////            Gdx.gl.glClearColor(0, 0, 0, 1);
////            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
////            batch.end();
////            batch.flush();
////
////            batch.begin();
////            batch.setShader(shaderProgram);
////            Vector2 v = new Vector2();
////            batch.draw(texture, position.x, position.y);
////            v = new Vector2(shockWavePositionX, shockWavePositionY);
////            v.x = v.x / Gdx.graphics.getWidth();
////            v.y = v.y / Gdx.graphics.getHeight();
////            shaderProgram.setUniformf("time", time);
////            shaderProgram.setUniformf("center", v);
////            Texture texture = fbo.getColorBufferTexture();
////            TextureRegion textureRegion = new TextureRegion(texture);
////            // and.... FLIP!  V (vertical) only
////            textureRegion.flip(false, true);
////            batch.draw(textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
////            batch.setShader(null);
////            fbo.end();
//            Mesh mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
//            mesh.setVertices(new float[]
//                    {-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
//                            0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1,
//                            0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
//                            -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
//            mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
//
//
//            texture.bind();
//            batch.end();
//            shaderProgram.begin();
//            //shaderProgram.setUniformMatrix("u_projTrans", matrix);
//            shaderProgram.setUniformi("u_texture", 0);

//            mesh.render(shaderProgram, GL20.GL_TRIANGLES);
//            shaderProgram.end();
//            batch.begin();

            batch.setShader(shaderProgram);
            batch.draw(texture, position.x, position.y);
            shaderProgram.setUniformf("time", time);
            shaderProgram.setUniformf("center", position);
            batch.setShader(null);

        }
    }
}
