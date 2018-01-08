package classes.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import interfaces.IGameObject;

import java.io.Serializable;


public abstract class GameObject implements IGameObject, Serializable
{
    private static float COLLISIONDISTANCE = 1000;
    private static float COLLISIONSTAYTIME = 5; // in millisecs

    protected Fixture fixture;
    protected Vector2 position;
    protected float rotation;
    protected Polygon hitbox; // swap this with fixture
    protected long id;
    protected Sprite sprite;

    protected GameObject()
    {
        id = this.hashCode();
        position = new Vector2();
        rotation = 0;
        hitbox = verticisToPolygon(circleHitbox(5));
    }

    /**
     * Returns a hitbox made with the size given
     *
     * @param size Hit range
     * @return returns defaulthitbox using the pararm's size
     */
    public static Vector2[] defaultHitbox(float size)
    {
        return defaultHitbox(size, size);
    }

    /**
     * Returns a hitbox made with the size given
     *
     * @param size Hit range
     * @return returns default cercle hitbox using the pararm's size
     */
    public static Vector2[] circleHitbox(float size)
    {
        return circleHitbox(size, 18);
    }

    /**
     * Returns a hitbox made with the size given
     *
     * @param size     Hit range
     * @param vertices Vertices
     * @return returns default cercle hitbox using the pararm's size and vertices
     */
    public static Vector2[] circleHitbox(float size, int vertices)
    {
        Vector2[] vList = new Vector2[vertices];
        double rad = Math.toRadians((double) 360 / vertices);

        for (int i = 0; i < vertices; i++)
        {
            float x = MathUtils.sin((float) (rad * i));
            float y = MathUtils.cos((float) (rad * i));
            Vector2 v = new Vector2(x, y);
            v = v.scl(size);
            vList[i] = v;
        }

        return vList;
    }

    /**
     * Returns Vector made with height and width
     *
     * @param height Height of the vector
     * @param width  Width of the vector
     * @return returns Vector2 array of given params
     */
    public static Vector2[] defaultHitbox(float height, float width)
    {
        float y = height / 2;
        float x = width / 2;

        Vector2 v1 = new Vector2(x, y);
        Vector2 v2 = new Vector2(-x, y);
        Vector2 v3 = new Vector2(x, -y);
        Vector2 v4 = new Vector2(-x, -y);

        return new Vector2[]{v1, v2, v4, v3};
    }

    public Polygon getHitbox()
    {
        return hitbox;
    }

    public void setHitbox(Polygon hitbox)
    {
        this.hitbox = hitbox;
    } // TODO remove or change this to set the fixture of this object

    public void setHitbox(Vector2[] verticis)
    {
        hitbox = verticisToPolygon(verticis);
        hitbox.setOrigin(0, 0);
    }

    /**
     * Converts Vector2 Array to Polygon
     *
     * @param vertices Vector2 vertices array
     * @return Polygon
     */
    public Polygon verticisToPolygon(Vector2[] vertices)
    {
        if (vertices.length < 3)
        {
            throw new IllegalArgumentException("Need atleast 3 or more verticies");
        }

        float[] verticisList = new float[vertices.length * 2];

        for (int i = 0, j = 0; i < vertices.length; i++, j += 2)
        {
            verticisList[j] = vertices[i].x;
            verticisList[j + 1] = vertices[i].y;
        }

        return new Polygon(verticisList);
    }

    @Override
    public long getID()
    {
        return id;
    }

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public void setPosition(Vector2 pos)
    {
        position = pos;
        hitbox.setPosition(pos.x, pos.y);
        if (sprite != null)
        {
            sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
        }
    }

    @Override
    public void translate(Vector2 translate)
    {
        setPosition(getPosition().add(translate));
    }

    @Override
    public float getRotation()
    {
        return rotation;
    }

    @Override
    public void setRotation(float rot)
    {
        rotation = rot;
        hitbox.setRotation(rot);
        if (sprite != null)
        {
            sprite.setRotation(rot);
        }
    }

    @Override
    public void addRotation(float rot)
    {
        setRotation(getRotation() + rot);
    }

    @Override
    public void onCollisionEnter(IGameObject other)
    {
        // Has to have a Override.
    }

    @Override
    public void onCollisionExit(IGameObject other)
    {
        // Has to have a Override.
    }

    @Override
    public void onCollisionStay(IGameObject other)
    {
        // Has to have a Override.
    }

    @Override
    public void update()
    {
        // Has to have a Override.
    }

    @Override
    public void Draw(ShapeRenderer shapeRenderer)
    {
        // Has to have a Override.
    }

    @Override
    public void Draw(Batch batch)
    {
        // Has to have a Override.
    }

    /**
     * Draw objects
     *
     * @param shapeRenderer Shape
     * @param batch         Batch
     */
    public void Draw(ShapeRenderer shapeRenderer, Batch batch)
    {
        Draw(shapeRenderer);
    }

    /**
     * DrawText on screen
     *
     * @param batch
     * @param font     Font used
     * @param layout   Layout used
     * @param text     Text to draw
     * @param position Position to draw
     */
    public void DrawText(Batch batch, BitmapFont font, GlyphLayout layout, String text, Vector2 position)
    {
        layout.setText(font, text);
        font.draw(batch, layout, position.x, position.y);
    }

    /**
     * @return
     */
    public Sprite getSprite()
    {
        return sprite;
    }

    /**
     * @param sprite
     */
    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public Fixture getFixture()
    {
        return fixture;
    }

    public void setFixture(Fixture fixture)
    {
        this.fixture = fixture;
    }

    public Vector2 getForward()
    {
        Vector2 forward = new Vector2(1, 0).rotate(rotation);

        return forward;
    }

    public Vector2 getBackwards()
    {
        Vector2 backwards = new Vector2(-1, 0).rotate(rotation);

        return backwards;
    }

    public Vector2 getLeft()
    {
        Vector2 left = new Vector2(0, 1).rotate(rotation);

        return left;
    }

    public Vector2 getRight()
    {
        Vector2 right = new Vector2(0, -1).rotate(rotation);

        return right;
    }
}
