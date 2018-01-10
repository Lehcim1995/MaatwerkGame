package classes;

import interfaces.IServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain extends UnicastRemoteObject implements IServer
{
    private transient Registry registry;

    private List<String> lobbies;

    public ServerMain() throws RemoteException, UnknownHostException
    {
        registry = LocateRegistry.createRegistry(portNumber);
        registry.rebind(ServerManger, this);
//        registry.rebind(connection, connectionInstance); // Database shizzle
        System.out.println(InetAddress.getLocalHost().getHostAddress());

        lobbies = new ArrayList<>();
        lobbies.add("1");
        lobbies.add("2");
        lobbies.add("3");
        lobbies.add("4");
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
        catch (UnknownHostException e)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not locate an ip address");
        }

        while (true)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = null;
            try
            {
                input = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if (input.equals("Exit"))
            {
                break;
            }
        }
    }

    @Override
    public List<String> getLobbies() throws RemoteException
    {
        return lobbies;
    }
}
