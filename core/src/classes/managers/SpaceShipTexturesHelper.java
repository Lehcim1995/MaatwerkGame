package classes.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * TODO this
 */
public class SpaceShipTexturesHelper
{
    // Spaceship types
    public static final String GUNSHIPS = "gunships";
    public static final String BATTLESHIPS = "battleships";
    public static final String CRUISERS = "cruisers";
    public static final String DESTROYERS = "destroyers";
    public static final String TRANSPORT = "transport";
    public static final String FIGHTERS = "fighter";
    public static final String BOMBERS = "bombers";
    public static final String COVERTS = "coverts";

    // The main texture
    private final Texture ships;

    // All the coordinations for the sprites in the sprite sheet aka main texture
    private int[][] shipCoords = {
            // Battleships
            // Amount 4
            // Start 0
            {0, 0, 318, 141}, {0, 141, 289, 135}, {0, 276, 288, 136}, {0, 412, 290, 124},
            // Cruisers
            // Amount 3
            // Start 4
            {0, 536, 224, 113}, {0, 649, 233, 100}, {0, 749, 227, 95},
            // Destroyers
            // Amount 3
            // Start 7
            {0, 844, 127, 45}, {0, 889, 127, 45}, {0, 934, 127, 47},
            // Coverts
            // Amount 2
            // Start 10
            {127, 844, 105, 69}, {127, 913, 84, 58},
            // Gunships
            // Amount 5
            // Start 12
            {233, 536, 55, 56}, {233, 592, 56, 54}, {233, 646, 58, 56}, {233, 702, 58, 51}, {233, 753, 55, 53},
            // Transport
            // Amount 3
            // Start 17
            {233, 806, 57, 39}, {233, 845, 61, 22}, {233, 867, 54, 19},
            // Bombers
            // Amount 2
            // Start 20
            {233, 886, 29, 37}, {233, 923, 30, 38},
            // Fighter
            // Amount 3
            // Start 22
            {265, 886, 31, 33}, {265, 920, 28, 25}, {265, 945, 22, 25}};

    public SpaceShipTexturesHelper()
    {
        this.ships = new Texture(Gdx.files.internal("/core/assets/textures/spaceships.png")); // TODO make this use a static string
    }

    /*
    battleships
    Start : Size
    0,0   , 318,141
    0,141 , 289,135
    0,276 , 288,136
    0,412 , 290,124

    cruisers
    0,536 , 224,113
    0,649 , 233,100
    0,749 , 227,95

    destroyers
    0,844 , 127,45
    0,889 , 127,45
    0,934 , 127,47

    corverts
    127,844 , 105,69
    127,913 , 84,58

    gunships
    233,412 , 55,56
    233,468 , 56,54
    233,522 , 58,56
    233,578 , 58,51
    233,629 , 55,53

    transport
    233,682 , 57,39
    233,721 , 61,22
    233,734 , 54,19

    bombers
    233,762 , 29,37
    233,799 , 30,38

    fighter
    233,837 , 31,33
    233,870 , 28,25
    233,895 , 22,25
    */

    /**
     * Returns a sprite based on the id.
     *
     * @param id The ship id
     * @return Sprite of a spaceship
     */
    public Sprite getSpaceShipSprite(int id)
    {
        if (id >= 0 && id < shipCoords.length)
        {
            int[] coords = shipCoords[id];
            return new Sprite(ships, coords[0], coords[1], coords[2], coords[3]);
        }

        return null;
    }

    private int getDifferentTypes(String type)
    {
        switch (type)
        {
            case GUNSHIPS:
                return 5;
            case BATTLESHIPS:
                return 4;
            case CRUISERS:
            case DESTROYERS:
            case TRANSPORT:
            case FIGHTERS:
                return 3;
            case BOMBERS:
            case COVERTS:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * TODO this
     *
     * @param type    Space ship the
     * @param version the variant of the spaceship
     * @return A sprite based on the inputs
     */
    public Sprite getSpaceShipSprite(String type, int version)
    {
        int start;

        if (getDifferentTypes(type) == 0 || getDifferentTypes(type) > version)
        {
            return null;
        }

        switch (type)
        {
            case GUNSHIPS:
                start = 0;
                break;
            case BATTLESHIPS:
                start = 4;
                break;
            case CRUISERS:
                start = 7;
                break;
            case DESTROYERS:
                start = 10;
                break;
            case TRANSPORT:
                start = 12;
                break;
            case FIGHTERS:
                start = 17;
                break;
            case BOMBERS:
                start = 20;
                break;
            case COVERTS:
                start = 22;
                break;
            default:
                return null;
        }

        return getSpaceShipSprite(start + version);
    }
}
