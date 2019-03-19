package classes.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OnlineSocketsManger
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        OnlineSocketsManger client = new OnlineSocketsManger();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        System.out.println(response);
    }

    public void startConnection(String ip, int port) {
        try
        {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception ignored)
        {

        }
    }

    public String sendMessage(String msg) {
        try
        {
            out.println(msg);
            String resp = in.readLine();
            return resp;
        }
        catch (Exception ignored)
        {

        }

        return "";
    }

    public void stopConnection() {
        try
        {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (Exception ignored)
        {

        }
    }

}
