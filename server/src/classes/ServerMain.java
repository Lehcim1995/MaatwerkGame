package classes;

import interfaces.IServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain implements IServer
{
    private transient Registry registry;

    private List<String> lobbies;

    public ServerMain() throws RemoteException
    {
        registry = LocateRegistry.createRegistry(portNumber);
        registry.rebind(ServerManger, this);
//        registry.rebind(connection, connectionInstance); // Database shizzle
    }

    public static void main(String[] args)
    {
        try
        {
            new ServerMain();
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not instantiate a server");
        }
    }

    @Override
    public List<String> getLobbies() throws RemoteException
    {
        return new ArrayList<>();
    }
}
