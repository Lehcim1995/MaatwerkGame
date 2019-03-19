package serverRmi;

import interfaces.IGameLobby;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain extends UnicastRemoteObject implements IServer
{
    private transient Registry registry;

    private Map<String, IGameLobby> lobbies;

    public ServerMain() throws RemoteException, UnknownHostException
    {
        registry = LocateRegistry.createRegistry(portNumber);
        registry.rebind(ServerManger, this);
//        registry.rebind(connection, connectionInstance); // Database shizzle
        System.out.println(InetAddress.getLocalHost().getHostAddress());

        lobbies = new HashMap<>();
    }

    public static void main(String[] args)
    {
        try
        {
            new ServerMain();
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not instantiate a serverRmi");
        }
        catch (UnknownHostException e)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not locate an ip address");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("Exit"))
        {
            try
            {
                input = br.readLine();
            }
            catch (IOException e)
            {
                //TODO
            }
        }
    }

    @Override
    public List<String> getLobbies() throws RemoteException
    {
        return new ArrayList<>(lobbies.keySet());
    }

    @Override
    public void leaveLobby(
            String lobbyName,
            String user) throws RemoteException
    {
        if (!lobbies.containsKey(lobbyName))
        {
            // Maybe throw error
            return;
        }

        lobbies.get(lobbyName).removeUser(user);

        if (lobbies.get(lobbyName).getPlayers().isEmpty())
        {
            // TODO maybe stop?
            lobbies.remove(lobbyName);
        }
    }

    @Override
    public boolean createLobby(String name) throws RemoteException
    {
        if (lobbies.containsKey(name))
        {
            System.out.println("Lobby already exists");
            return false;
        }

        lobbies.put(name, new GameLobby(name));
        System.out.println("Created lobby " + name);
        return true;
    }

    @Override
    public IGameLobby joinLobby(
            String lobbyName,
            String user) throws RemoteException
    {
        if (!lobbies.containsKey(lobbyName))
        {
            // Maybe throw error
            return null;
        }

        lobbies.get(lobbyName).addUser(user);
        return lobbies.get(lobbyName);
    }

    @Override
    public void DeleteLobby(String name) throws RemoteException
    {
        lobbies.remove(name);
    }

    @Override
    public long getUtfTime() {
        return new Date().getTime();
    }
}
