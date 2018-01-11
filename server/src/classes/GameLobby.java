package classes;

import interfaces.IGameLobby;
import interfaces.IGameObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLobby extends UnicastRemoteObject implements IGameLobby
{
    Map<String, List<IGameObject>> playerGameobjectList;
    boolean isRunning;
    String lobbyName;

    public GameLobby(String lobbyName) throws RemoteException
    {
        super();
        this.lobbyName = lobbyName;
        playerGameobjectList = new HashMap<>();
    }


    @Override
    public List<IGameObject> getUpdates()
    {
        return null;
    }

    @Override
    public List<IGameObject> getUpdatesFromUser(String user)
    {
        return null;
    }

    @Override
    public List<String> getPlayers()
    {
        return new ArrayList<>(playerGameobjectList.keySet());
    }

    @Override
    public void addUser(String name)
    {
        playerGameobjectList.put(name, new ArrayList<>());
    }

    @Override
    public void removeUser(String user)
    {
        playerGameobjectList.remove(user);
    }

    @Override
    public void addUpdate(
            List<IGameObject> update,
            String user) throws RemoteException
    {

    }

    @Override
    public List<IGameObject> getUpdates(String user) throws RemoteException
    {
        return null;
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
