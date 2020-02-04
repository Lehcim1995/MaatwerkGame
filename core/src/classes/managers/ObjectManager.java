package classes.managers;

import classes.gameobjects.playable.SpaceShip;
import classes.gameobjects.playable.SpaceShipEnemy;
import classes.gameobjects.unplayble.Laser;
import classes.gameobjects.unplayble.Station;
import interfaces.IGameObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObjectManager
{

    // TODO add object pooling
    private static List<IGameObject> gameObjects = new CopyOnWriteArrayList<>();

    private static Dictionary<String, List<IGameObject>> dictGameobjects = new Hashtable<>();

    private static List<Laser> lasers = new CopyOnWriteArrayList<>();
    private static List<Station> stations = new CopyOnWriteArrayList<>();
    private static List<SpaceShipEnemy> spaceShipEnemies = new CopyOnWriteArrayList<>();
    private static List<SpaceShip> spaceShips = new CopyOnWriteArrayList<>();

    public static List<IGameObject> getGameObjects() {
//        List<IGameObject> tempList = new ArrayList<>();
//        tempList.addAll(gameObjects);
//        lasers.addAll(stations);


        return gameObjects;
    }

    public static void Instantiate(IGameObject go)
    {
        gameObjects.add(go);
    }

    public static void Destroy(IGameObject go)
    {
        gameObjects.remove(go);
    }
}
