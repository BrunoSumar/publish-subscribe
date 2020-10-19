package publishSubscribe

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    public void registrar(String topico, String id);
}
