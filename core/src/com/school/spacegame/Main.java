package com.school.spacegame;

import classes.managers.SceneManager;
import com.badlogic.gdx.Game;
import interfaces.IServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

// TODO \/
// https://gist.github.com/Leejjon/7fb8aa3ea2e4024a9eba31fa4f3339fb
// https://github.com/libgdx/libgdx/wiki/Scene2d
// http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
public class Main extends Game
{
    public SceneManager sceneManager;
    private Registry registry;
    private IServer server;

    public static String ip;

    private void connectToServer(String ipaddress)
    {

        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            ip = localhost.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            //Log this
            java.util.logging.Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: UnknownHostException: " + e.getMessage());
        }
        if (!ipaddress.isEmpty())
        {
            ip = ipaddress;
        }
        int portNumber = 1099;

        try
        {
            registry = LocateRegistry.getRegistry(ip, portNumber);
            String serverManger = IServer.ServerManger;
            server = (IServer) registry.lookup(serverManger);
        }
        catch (RemoteException e)
        {
            //Log this
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: RemoteExeption: " + e.getMessage());
            ip = "not connected";
        }
        catch (NotBoundException e)
        {
            //Log this
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Client: NotBoundException: " + e.getMessage());
            ip = "not connected";
        }
    }

    @Override
    public void create()
    {
        connectToServer("");

        sceneManager = new SceneManager(this);
        sceneManager.LoadMainMenuScreen();
    }

    public IServer getServer()
    {
        return server;
    }
}
