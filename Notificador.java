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
        if(indexOfTopico(nome)!=-1)
            return null;
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
        if(index != -1){
            Topico t = topicos.get(index);
            t.addId(id);
        }
    }

    public void publicar(Registry reg, String topico, String info){
        int index = this.indexOfTopico(topico);
        if(index != -1){
            Topico t = topicos.get(index);
            for(String nome: t.getIds()){
                try{
                    IOuvinte temp = (IOuvinte) reg.lookup(nome);
                    temp.notificar(info, topico);
                }catch(Exception e){
                    System.err.println("Erro:");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null)  {
            System.setSecurityManager(new SecurityManager());
        }
        try { // Registra o objeto notificador no RMI
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            Scanner sc = new Scanner(System.in);
            String nome = "Notificador";
            Notificador notificador = new Notificador();
            INotificador stub =
                (INotificador) UnicastRemoteObject.exportObject(notificador, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind(nome, stub);
            System.out.println("Notificador pronto.");

            //Menu
            int opcao = -1;
            String top, info;
            while(opcao != 0){
                System.out.println("Escolha uma opção: ");
                System.out.println("\t0 -> Sair");
                System.out.println("\t1 -> Cadastrar novo topico");
                System.out.println("\t2 -> Remover topico");
                System.out.println("\t3 -> Publicar informação\n");

                try{
                    opcao = sc.nextInt();
                } catch (Exception e) {
                    opcao = -1; //Para que, caso seja colocado um valor que não é numero, não continue selecionando a opção selecionada previamente
                }
                sc.nextLine();

                switch(opcao){
                    case 1:
                        System.out.println("Digite o nome do tópico:");
                        top = sc.nextLine();
                        if(notificador.adicionarTopico(top)==null)
                            System.out.println("--Não foi possivel criar o tópico--");
                        break;
                    case 2:
                        System.out.println("Digite o nome do tópico:");
                        top = sc.nextLine();
                        notificador.removerTopico(top);
                        break;
                    case 3:
                        System.out.println("Digite o nome do tópico:");
                        top = sc.nextLine();
                        System.out.println("Digite a informação a ser publicada:");
                        info = sc.nextLine();
                        notificador.publicar(registry, top, info);
                        break;
                }
               
                System.out.println("");

            }
        } catch (Exception e) {
            System.err.println("Erro:");
            e.printStackTrace();
        }
        System.exit(0);
    }

}
