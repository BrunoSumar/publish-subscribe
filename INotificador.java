package pubSub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotificador extends Remote {
    public void registrar(String topico, String id);
}
