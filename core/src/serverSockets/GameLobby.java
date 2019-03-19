package serverSockets;

import interfaces.IGameLobby;
import interfaces.ISyncObject;

import java.rmi.RemoteException;
import java.util.List;

public class GameLobby implements IGameLobby
{
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

    }

    @Override
    public Long createObject(
            String user,
            ISyncObject syncObject) throws RemoteException
    {
        return null;
    }

    @Override
    public void deleteObject(
            String user,
            ISyncObject syncObject) throws RemoteException
    {

    }

    @Override
    public List<ISyncObject> getUpdates() throws RemoteException {
        return null;
    }

    @Override
    public List<ISyncObject> getUpdates(String user) throws RemoteException {
        return null;
    }

    @Override
    public List<ISyncObject> getNewObjects(String user) throws RemoteException {
        return null;
    }

    @Override
    public List<String> getPlayers() throws RemoteException {
        return null;
    }

    @Override
    public void addUser(String name) throws RemoteException {

    }

    @Override
    public void removeUser(String user) throws RemoteException {

    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void start() throws RemoteException {

    }

    @Override
    public String getLobbyName() throws RemoteException {
        return null;
    }

    @Override
    public boolean isRunning() throws RemoteException {
        return false;
    }
}
