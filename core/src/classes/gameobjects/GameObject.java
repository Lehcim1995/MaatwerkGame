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

/**
 * An implementation of the Gameobject interface
 */
public abstract class GameObject implements IGameObject, Serializable
{
    protected transient Fixture fixture;
    protected Vector2 position;
    protected float rotation;
    protected transient Polygon hitbox; // swap this with fixture
    protected long id;
    protected transient Sprite sprite;
    protected boolean toDelete = false;

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
     * @return returns default hitbox using the pararm's size
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
     * @return returns default circle hitbox using the pararm's size and vertices
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
    public void setID(long id)
    {
        this.id = id;
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

    /**
     * @param other The other Gameobject it collided with
     */
    @Override
    public abstract void onCollisionEnter(IGameObject other);

    /**
     * @param other The other Gameobject it ending collided with
     */
    @Override
    public abstract void onCollisionExit(IGameObject other);

    /**
     * @param other
     */
    @Override
    public abstract void onCollisionStay(IGameObject other);

    /**
     * For updating this gameobject
     */
    @Override
    public abstract void update();

    /**
     * @param shapeRenderer Shaperendere to draw into
     */
    @Override
    public abstract void Draw(ShapeRenderer shapeRenderer);

    /**
     * @param batch Batch to draw into
     */
    @Override
    public abstract void Draw(Batch batch);

    /**
     * DrawText on screen
     *
     * @param batch    Batch used
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
     * @return The sprite
     */
    public Sprite getSprite()
    {
        return sprite;
    }

    /**
     * Overwrite or set the current sprite
     *
     * @param sprite
     */
    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }

    /**
     *
     * @return the Fixture
     */
    @Override
    public Fixture getFixture()
    {
        return fixture;
    }

    /**
     *
     * @param fixture The new Fixture
     */
    public void setFixture(Fixture fixture)
    {
        this.fixture = fixture;
    }

    /**
     *
     *  @return a new Vector that points forwards and includes the rotations
     */
    public Vector2 getForward()
    {
        Vector2 forward = new Vector2(1, 0).rotate(rotation);

        return forward;
    }

    /**
     *
     * @return a new Vector that points backwards from forwards Vector
     */
    public Vector2 getBackwards()
    {
        Vector2 backwards = new Vector2(-1, 0).rotate(rotation);

        return backwards;
    }

    /**
     *
     * @return a new Vector that points left from forwards Vector
     */
    public Vector2 getLeft()
    {
        Vector2 left = new Vector2(0, 1).rotate(rotation);

        return left;
    }

    /**
     *
     * @return a new Vector that points right from forwards Vector
     */
    public Vector2 getRight()
    {
        Vector2 right = new Vector2(0, -1).rotate(rotation);

        return right;
    }

    /**
     *
     * @return a boolean to determine if this gameobject is set to be deleted.
     */
    public boolean isToDelete()
    {
        return toDelete;
    }

    @Override
    public void setToDelete(boolean delete)
    {
        toDelete = delete;
    }
}
