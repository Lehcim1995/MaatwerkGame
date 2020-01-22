package classes.managers;

import com.badlogic.gdx.graphics.Texture;
import exceptions.TextureNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class AssetManager
{
    //TODO add loading of images and stuff

    static Map<String, String> TextureLocationMap;

    static Map<String, Texture> Textures;

    {
        TextureLocationMap = new HashMap<>();
        TextureLocationMap.put("Spaceships", "");


        Textures = new HashMap<>();

        for (Map.Entry<String, String> entry : TextureLocationMap.entrySet())
        {
            try
            {
                Texture t = new Texture(entry.getValue());

                Textures.put(entry.getKey(), t);
            }
            catch (Exception e)
            {
                // Catch for non found files
            }
        }

    }

    public static Texture getTexture(String name) throws TextureNotFoundException
    {
        if (!Textures.containsKey(name))
            throw new TextureNotFoundException();

        return Textures.get(name);
    }
}
