package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGameLobby extends Remote, Serializable
{
    void addUpdates(
            List<ISyncObject> update,
            String user) throws RemoteException;

    void addUpdate(
            String user,
            ISyncObject syncObject) throws RemoteException;

    Long createObject(
            String user,
            ISyncObject syncObject) throws RemoteException;

    void deleteObject(
            String user,
            ISyncObject syncObject) throws RemoteException;

    List<ISyncObject> getUpdates() throws RemoteException;

    List<ISyncObject> getUpdates(String user) throws RemoteException;

    List<String> getPlayers() throws RemoteException;

    void addUser(String name) throws RemoteException;

    void removeUser(String user) throws RemoteException;

    void stop() throws RemoteException;

    void start() throws RemoteException;

    String getLobbyName() throws RemoteException;

    boolean isRunning() throws RemoteException;
}
