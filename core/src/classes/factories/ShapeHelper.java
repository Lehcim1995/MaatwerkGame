package classes.factories;

import classes.gameobjects.GameObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

public class ShapeHelper
{
    // TODO fix duplicate code
    // TODO add object pooling
    // TODO add Material

    private World world;

    public ShapeHelper(World world)
    {
        // Start box2d?
        this.world = world;
    }

    public Fixture CreateSuperShape(
            int x,
            int y,
            Polygon polygon)
    {
        // Create our body in the world using our body definition
        Body body = createDynamicBody(x, y, 0);

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(polygon.getTransformedVertices());

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(defaultFixtureDef(chainShape));

        fixture.getUserData();

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        chainShape.dispose();

        return fixture;
    }

    public Fixture CreateChainShape(
            int x,
            int y,
            List<Vector2> polygon)
    {
        // Create our body in the world using our body definition
        Body body = createDynamicBody(x, y, 0);

        ChainShape chainShape = new ChainShape();

        Vector2[] polygonArray = new Vector2[polygon.size()];
        chainShape.createLoop(polygon.toArray(polygonArray));

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(defaultFixtureDef(chainShape));

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        chainShape.dispose();

        return fixture;
    }

    public Fixture CreateCube(GameObject go)
    {
        return CreateCube((int) go.getPosition().x, (int) go.getPosition().y, (int) go.getSprite().getWidth(), (int) go.getSprite().getHeight(), go.getRotation());
    }

    public Fixture CreateCube(
            int x,
            int y,
            int width,
            int height,
            float rotation)
    {
        // Create our body in the world using our body definition
        Body body = createDynamicBody(x, y, rotation);

        // Create a circle shape and set its radius to 6
        PolygonShape square = new PolygonShape();
        square.setAsBox(width / 2, height / 2);

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(defaultFixtureDef(square));

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        square.dispose();

        return fixture;
    }

    public Fixture CreateCircle(
            int x,
            int y,
            int size)
    {
        // Create our body in the world using our body definition
        Body body = createDynamicBody(x, y, 0);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(size);

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(defaultFixtureDef(circle));

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        return fixture;
    }

    public Fixture CreateCircle(GameObject go) {
        return CreateCircle((int) go.getPosition().x, (int) go.getPosition().y, (int) go.getSprite().getWidth()/2);
    }

    public Fixture CreateCircleStatic(
            int x,
            int y,
            int size)
    {
        // Create our body in the world using our body definition
        Body body = createBody(x, y, 0, BodyDef.BodyType.StaticBody);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(size);

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(defaultFixtureDef(circle));

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        return fixture;
    }

    public Fixture CreateCircleStatic(GameObject go) {

        float scale = go.getSprite().getScaleX();
        float width = go.getSprite().getWidth();

        return CreateCircleStatic((int) go.getPosition().x, (int) go.getPosition().y, (int) (scale * width)/2);
    }

    private Body createDynamicBody(
            float x,
            float y,
            float rotation)
    {
        return createBody(x, y, rotation, BodyDef.BodyType.DynamicBody);
    }

    private Body createBody(
            float x,
            float y,
            float rotation,
            BodyDef.BodyType bodyType)
    {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = bodyType;

        // Set our body's starting position in the world
        bodyDef.position.set(x, y);

        // set rotation
        bodyDef.angle = (float) Math.toRadians(rotation);

        return world.createBody(bodyDef);
    }

    private FixtureDef createFixtureDef(
            Shape shape,
            Material materials)
    {
        FixtureDef fixtureDef = materials.getFixtureDef();
        fixtureDef.shape = shape;

        return fixtureDef;
    }

    private FixtureDef defaultFixtureDef(Shape shape)
    {
        return createFixtureDef(shape, Material.DEFAULT);
    }

    public enum Material
    {
        METAL(0, 0, 0),
        WOOD(0, 0, 0),
        DEFAULT(0.5f, 0.4f, 0.6f);

        private final FixtureDef fixtureDef;

        Material(
                float density,
                float friction,
                float restitution)
        {
            fixtureDef = new FixtureDef();
            fixtureDef.density = density;
            fixtureDef.friction = friction;
            fixtureDef.restitution = restitution;
        }

        public FixtureDef getFixtureDef()
        {
            return fixtureDef;
        }
    }
}
