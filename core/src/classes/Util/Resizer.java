package classes.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Resizer
{
    public Sprite Resize(Texture texture, int newX, int newY)
    {
        return new Sprite(texture, newX, newY);
    }
}
