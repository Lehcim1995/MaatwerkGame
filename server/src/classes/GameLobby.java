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

    public GameLobby() throws RemoteException
    {
        super();
        playerGameobjectList = new HashMap<>();
    }

    @Override
    public void addUpdate()
    {

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
}
