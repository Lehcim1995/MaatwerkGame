package classes;

import com.badlogic.gdx.physics.box2d.Filter;

public class CollisionMasks
{
    public final static short CATEGORY_PLAYER = 0x0001;
    public final static short CATEGORY_ENEMY = 0x0002;
    public final static short CATEGORY_SCENERY = 0x0004;


    // -1 is collide with all
    public final static short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_SCENERY;
    public final static short MASK_ENEMY = -1;
    public final static short MASK_SCENERY = -1;

    public final static Filter PLAYER_FILTER;
    public final static Filter ENEMY_FILTER;

    static
    {
        PLAYER_FILTER = new Filter();
        PLAYER_FILTER.categoryBits = CATEGORY_PLAYER;
        PLAYER_FILTER.maskBits = MASK_PLAYER;

        ENEMY_FILTER = new Filter();
        ENEMY_FILTER.categoryBits = CATEGORY_ENEMY;
        ENEMY_FILTER.maskBits = MASK_ENEMY;
    }
}
