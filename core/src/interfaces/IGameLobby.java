package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGameLobby extends Remote, Serializable
{
    void addUpdate() throws RemoteException;

    List<IGameObject> getUpdates() throws RemoteException;

    List<IGameObject> getUpdatesFromUser(String user) throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    void addUser(String name) throws RemoteException;

    void removeUser(String user);
}
