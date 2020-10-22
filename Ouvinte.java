package pubSub;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class Ouvinte implements IOuvinte{
    public void notificar( String dados, String topico ) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n\n//----------------// N O T I F I C A Ç Ã O //------------------//");
        System.out.println("//----------------// T O P I C O : " + topico + "\n");
        System.out.print( "\t"+dados );
        System.out.println("\n\n//----------------// N O T I F I C A Ç Ã O //------------------//\n\n\n");
    }

    private void registrarTopico( INotificador servidor, String id ) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Qual o nome do tópico que deseja se registrar? ");
        String topico = sc.nextLine();

        try {
        servidor.registrar( topico, id );
        } catch(Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Scanner sc = new Scanner(System.in);

            //Gerando o ID aleatório.
            Random gerador = new Random();
            String ID = Integer.toString(gerador.nextInt());

            //Criando o o bjeto remoto e exportando e registrando no rmiregistry
            Ouvinte ouvinte = new Ouvinte();
            IOuvinte stub = (IOuvinte) UnicastRemoteObject.exportObject(ouvinte, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(ID, stub);

            INotificador servidor = (INotificador) registry.lookup("Notificador");

            //Menu
            int opcao = -1;
            while(opcao != 0){
                System.out.println("Escolha uma opção: ");
                System.out.println("0 -> Sair");
                System.out.println("1 -> Registrar em topico");

                try{
                    opcao = sc.nextInt();
                } catch (Exception e) {
                    opcao = -1; //Para que, caso seja colocado um valor que não é numero, não continue selecionando a opção selecionada previamente
                }

                sc.nextLine();

                if (opcao == 1){
                    ouvinte.registrarTopico(servidor, ID);
                }

            }
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
        System.exit(0);
    }
}
