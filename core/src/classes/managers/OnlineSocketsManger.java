package classes.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class OnlineSocketsManger
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) throws IOException {
//        OnlineSocketsManger client = new OnlineSocketsManger();
//        client.startConnection("127.0.0.1", 6666);
//        String response = client.sendMessage("hello server");
//        System.out.println(response);
//
//        Scanner scanner = new Scanner(System.in);
//
//        while(true)
//        {
//            String input = scanner. nextLine();
//            System.out.println("Sending input " + input);
//            String next = client.sendMessage(input);
//            System.out.println(next);
//        }

        try (ServerSocket listener = new ServerSocket(59090)) {
            System.out.println("The date server is running...");
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                }
            }
        }
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
            out.flush();
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
