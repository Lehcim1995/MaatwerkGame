package classes.managers;

import classes.gameobjects.GameObject;
import interfaces.IGameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObjectManager
{

    // TODO add object pooling
    public static List<IGameObject> gameObjects = new CopyOnWriteArrayList<>();

    public static void Instantiate(IGameObject go)
    {
        gameObjects.add(go);
    }

    public static void Destroy(IGameObject go)
    {
        gameObjects.remove(go);
    }
}
