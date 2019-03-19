package serverRmi;

import interfaces.IGameLobby;
import interfaces.ISyncObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameLobby extends UnicastRemoteObject implements IGameLobby
{
    private static AtomicLong idCounter = new AtomicLong();
    private Map<String, Map<Long, ISyncObject>> playerGameobjectList;
    private Map<String, Map<Long, ISyncObject>> newGameobjectList;
    private boolean isRunning;
    private String lobbyName;

    //TODO add Times for match time and such

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
        // TODO
    }

    @Override
    public void addUpdate(
            String user,
            ISyncObject syncObject) throws RemoteException
    {

        Logger.getAnonymousLogger().log(Level.INFO, "update object " + syncObject.getId());
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

        Logger.getAnonymousLogger().log(Level.INFO, "Create object " + id);

        playerGameobjectList.get(user).put(id, syncObject);

        for (Map.Entry<String, Map<Long, ISyncObject>> entry : newGameobjectList.entrySet())
        {
            // Add new object for everybody but me
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
        newGameobjectList.put(name, new HashMap<>());
    }

    @Override
    public void removeUser(String user)
    {
        playerGameobjectList.remove(user);
        newGameobjectList.remove(user);
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
