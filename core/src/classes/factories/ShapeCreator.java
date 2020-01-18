package classes.factories;

import com.badlogic.gdx.physics.box2d.*;

public class ShapeCreator
{
    private World world;

    private Body body;
    private Shape shape;
    private Fixture fixture;
    private FixtureDef fixtureDef;

    public ShapeCreator(World world) {
        this.world = world;
    }

    public ShapeCreator CreateBody( float x,
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

        body = world.createBody(bodyDef);

        return this;
    }

    public ShapeCreator CreateDynamicBody(float x,
                                          float y,
                                          float rotation)
    {
        return CreateBody(x,y,rotation, BodyDef.BodyType.DynamicBody);
    }

    public ShapeCreator CreateStaticBody(float x,
                                          float y,
                                          float rotation)
    {
        return CreateBody(x,y,rotation, BodyDef.BodyType.StaticBody);
    }

    public ShapeCreator CreateChainShape()
    {
        return this;
    }

    public ShapeCreator CreateCubeShape(float height, float width)
    {
        PolygonShape square = new PolygonShape();
        square.setAsBox(width / 2, height / 2);

        this.shape = square;

        return this;
    }

    public ShapeCreator CreateFixureDef()
    {
        fixtureDef = new FixtureDef();

        return this;
    }

    public Fixture getFixture()
    {
        fixture = body.createFixture(fixtureDef);
        shape.dispose();

        return fixture;
    }
}
