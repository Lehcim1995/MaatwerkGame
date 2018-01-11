package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGameLobby extends Remote, Serializable
{
    void addUpdate(
            List<IGameObject> update,
            String user) throws RemoteException;

    List<IGameObject> getUpdates() throws RemoteException;

    List<IGameObject> getUpdates(String user) throws RemoteException;

    List<IGameObject> getUpdatesFromUser(String user) throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    void addUser(String name) throws RemoteException;

    void removeUser(String user) throws RemoteException;

    void stop() throws RemoteException;

    void start() throws RemoteException;

    String getLobbyName() throws RemoteException;

    boolean isRunning() throws RemoteException;
}
