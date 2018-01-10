package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Stefan on 12/20/2016.
 */
public interface IServer extends Remote, Serializable
{
    int portNumber = 1099;
    String ServerManger = "servermanager";
    String connection = "connection";

    List<String> getLobbies() throws RemoteException;

    boolean createLobby(String name) throws RemoteException;

//    IGameManager JoinLobby(
//            String name,
//            IUser user) throws RemoteException;
}
