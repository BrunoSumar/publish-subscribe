package pubSub;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class Ouvinte implements IOuvinte{
    public void notificar( String dados ) {
        Scanner sc = new Scanner(System.in);
        System.out.print( dados );
        sc.next();
   }

    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Scanner sc = new Scanner(System.in);

            //Gerando o ID aleatório.
            Random gerador =new Random();
            String ID = Integer.toString(gerador.nextInt());

            //Criando o o bjeto remoto e exportando e registrando no rmiregistry
            IOuvinte ouvinte = new Ouvinte();
            IOuvinte stub = (IOuvinte) UnicastRemoteObject.exportObject(ouvinte, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("nome_aleatorio", stub);
            /*
            //Menuzinho
            int opcao = -1;
            while(opcao != 0){
                System.out.println("Escolha uma opção: ");
                System.out.println("0 -> Sair");
                System.out.println("1 -> Registrar em topico");

                opcao = sc.nextInt();
                sc.nextLine();

                if (opcao == 1){
                    //this.registrarTopico();//Não completei, parei aqui
                }
            }
            */
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }
}
