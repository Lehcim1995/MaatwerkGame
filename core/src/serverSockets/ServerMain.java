package serverSockets;

import interfaces.IGameLobby;
import interfaces.IServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class ServerMain implements IServer
{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {

        try
        {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            if ("hello server".equals(greeting))
            {
                out.println("hello client");
            }
            else
            {
                out.println("unrecognised greeting");
            }
        }
        catch (Exception ignored)
        {

        }
    }

    public void stop() {

        try
        {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch (Exception ignored)
        {

        }
    }
    public static void main(String[] args) {
        ServerMain server=new ServerMain();
        server.start(6666);
    }

    @Override
    public List<String> getLobbies() throws RemoteException {
        return null;
    }

    @Override
    public boolean createLobby(String name) throws RemoteException {
        return false;
    }

    @Override
    public void DeleteLobby(String name) throws RemoteException {

    }

    @Override
    public IGameLobby joinLobby(
            String lobbyName,
            String user) throws RemoteException
    {
        return null;
    }

    @Override
    public void leaveLobby(
            String lobbyName,
            String user) throws RemoteException
    {

    }

    @Override
    public long getUtfTime() {
        return 0;
    }
}
