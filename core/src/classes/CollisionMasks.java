package classes;

public class CollisionMasks
{
    final static short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    final static short CATEGORY_ENEMY = 0x0002; // 0000000000000010 in binary
    final static short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary

    final static short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_SCENERY; // or ~CATEGORY_PLAYER
    final static short MASK_MONSTER = CATEGORY_PLAYER | CATEGORY_SCENERY; // or ~CATEGORY_MONSTER
    final static short MASK_SCENERY = -1;
}
