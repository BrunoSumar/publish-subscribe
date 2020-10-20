package pubSub;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Notificador implements INotificador {

    private ArrayList<Topico> topicos = new ArrayList(); // lita de tópicos

    public Notificador() { // Construtor
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

    public int indexOfTopico(String nome){
        for(Topico t: topicos){
            if(t.getNome().equals(nome))
                return topicos.indexOf(t);
        }
        return -1;
    }

    public void registrar(String topico, String id){
        int index = this.indexOfTopico(topico);
        Topico t;
        if(index != -1){
            t = topicos.get(index);
        }else{
            t = this.adicionarTopico(topico);
        }
        t.addId(id);
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null)  {
            System.setSecurityManager(new SecurityManager());
        }
        try { // Registra o objeto notificador no RMI
            //System.setProperty("java.rmi.server.hostname","127.0.0.1");
            Scanner sc = new Scanner(System.in);
            String nome = "Notificador";
            INotificador notificador = new Notificador();
            INotificador stub =
                (INotificador) UnicastRemoteObject.exportObject(notificador, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind(nome, stub);
            System.out.println("Notificador pronto.");

            sc.nextLine();
            IOuvinte stub2 = (IOuvinte) registry.lookup("nome_aleatorio");
            stub2.notificar("eai funcionou?");
        } catch (Exception e) {
            System.err.println("Erro:");
            e.printStackTrace();
        }
        // loop principal
        Scanner sc = new Scanner(System.in);
        String input = "";
        do{
            System.out.println("Digite 'x' pra sair");
            input = sc.nextLine();
        }while(!input.equals("x"));
    }

}
