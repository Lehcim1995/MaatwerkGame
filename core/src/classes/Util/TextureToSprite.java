package classes.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureToSprite
{
    private Texture texture;
    private int columns;
    private int rows;
    private int columnWidth;
    private int rowHeight;

    public TextureToSprite(
            Texture texture,
            int columns,
            int rows)
    {
        this.texture = texture;
        this.columns = columns;
        this.rows = rows;

        columnWidth = texture.getWidth() / columns;
        rowHeight = texture.getHeight() / rows;
    }

    public Sprite getSprite(int row, int column)
    {

        return new Sprite(texture, columnWidth * column, rowHeight * row, columnWidth, rowHeight);
    }
}
