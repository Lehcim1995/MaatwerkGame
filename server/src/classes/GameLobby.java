package classes;

import interfaces.IGameLobby;
import interfaces.ISyncObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class GameLobby extends UnicastRemoteObject implements IGameLobby
{
    private static AtomicLong idCounter = new AtomicLong();
    private Map<String, Map<Long, ISyncObject>> playerGameobjectList;
    private Map<String, Map<Long, ISyncObject>> newGameobjectList;
    private boolean isRunning;
    private String lobbyName;

    public GameLobby(String lobbyName) throws RemoteException
    {
        super();
        this.lobbyName = lobbyName;
        playerGameobjectList = new HashMap<>();
        newGameobjectList = new HashMap<>();
        idCounter.set(348552); // TODO generate random number
    }

    public static Long createID()
    {
        return idCounter.getAndIncrement();
    }

    @Override
    public void addUpdates(
            List<ISyncObject> update,
            String user) throws RemoteException
    {

    }

    @Override
    public void addUpdate(
            String user,
            ISyncObject syncObject) throws RemoteException
    {
//        System.out.println("uploading gameobject id " + syncObject.getId() + " from user " + user);
//        System.out.println("uploading gameobject rot " + syncObject.getRotation());
        // TODO replace the object with the id
        playerGameobjectList.get(user).put(syncObject.getId(), syncObject);
    }

    @Override
    public List<ISyncObject> getNewObjects(String user) throws RemoteException
    {
        List<ISyncObject> s = new ArrayList<>();
        if (newGameobjectList.get(user) != null)
        {
            s = new ArrayList<>(newGameobjectList.get(user).values());
        }
        newGameobjectList.put(user, new HashMap<>());
        return s;
    }

    @Override
    public Long createObject(
            String user,
            ISyncObject syncObject) throws RemoteException
    {
        long id = createID();
        syncObject.setId(id);

        for (Map.Entry<String, Map<Long, ISyncObject>> entry : playerGameobjectList.entrySet())
        {
            if (entry.getKey().equals(user))
            {
                continue;
            }

            entry.getValue().put(id, syncObject);
        }

        for (Map.Entry<String, Map<Long, ISyncObject>> entry : newGameobjectList.entrySet())
        {
            if (entry.getKey().equals(user))
            {
                continue;
            }

            entry.getValue().put(id, syncObject);
        }


        return id;
    }

    @Override
    public void deleteObject(
            String user,
            ISyncObject syncObject) throws RemoteException
    {
        playerGameobjectList.get(user).remove(syncObject.getId());
    }

    @Override
    public List<ISyncObject> getUpdates() throws RemoteException
    {
        List<ISyncObject> syncObjects = new ArrayList<>();

        for (Map.Entry<String, Map<Long, ISyncObject>> stringMapEntry : playerGameobjectList.entrySet())
        {
            syncObjects.addAll(stringMapEntry.getValue().values());
        }

        return syncObjects;
    }

    @Override
    public synchronized List<ISyncObject> getUpdates(String user) throws RemoteException
    {
        List<ISyncObject> syncObjects = new ArrayList<>();

        for (Map.Entry<String, Map<Long, ISyncObject>> stringMapEntry : playerGameobjectList.entrySet())
        {
            if (stringMapEntry.getKey().equals(user))
            {
                continue;
            }

            syncObjects.addAll(stringMapEntry.getValue().values()); // TODO fix java.util.ConcurrentModificationException
        }

        return syncObjects;
    }

    @Override
    public synchronized List<String> getPlayers()
    {
        return new ArrayList<>(playerGameobjectList.keySet());
    }

    @Override
    public void addUser(String name)
    {
        playerGameobjectList.put(name, new HashMap<>());
    }

    @Override
    public void removeUser(String user)
    {
        playerGameobjectList.remove(user);
    }

    @Override
    public void stop() throws RemoteException
    {
        isRunning = false;
    }

    @Override
    public void start() throws RemoteException
    {
        isRunning = true;
    }

    @Override
    public boolean isRunning() throws RemoteException
    {
        return isRunning;
    }

    @Override
    public String getLobbyName()
    {
        return lobbyName;
    }
}
