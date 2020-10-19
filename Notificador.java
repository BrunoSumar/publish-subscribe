package publishSubscribe;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Notificador implements INotificador {

    private ArrayList<Topico> topicos;

    public Notificador() {
        super();
    }

    public Topico adicionarTopico(String nome){
        Topico t = new Topico(nome);
        topicos.add(t);
        return t;
    }

    public void removerTopico(String nome){
        int index = this.indexOfTopico(nome);
        if(index != -1)
            topicos.remove(index);
    }

    public int indexOfTopico(string nome){
        for(Topico t: topicos){
            if(t.nome.equals(nome))
                return topicos.indexOf(t);
        }
        return -1;
    }

    public registrar(String topico, String id){
        int index = this.indexOfTopico(topico);
        Topico t;
        if(index != -1){
            t = topicos.get(index);
        }else{
            t = this.adicionarTopico(topico);
        }
        t.add(id);
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String nome = "Notificador";
            INotificador notificador = new Notificador();
            INotificador stub =
                (INotificador) UnicastRemoteObject.exportObject(notificador, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(nome, stub);
            System.out.println("Notificador pronto.");
        } catch (Exception e) {
            System.err.println("Erro:");
            e.printStackTrace();
        }
    }
}
