package pubSub;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import publisherSubscribe.IOuvinte;
import java.util.Scanner;

public class Ouvinte implements IOuvinte{
    public void notificar( String dados ) {
        Scanner sc = new Scanner();
        System.out.print( dados );
        sc.next();
   }

    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Scanner sc = new Scanner();

            //Gerando o ID aleatório.
            Random gerador = new Random();
            String ID = gerador.nextInt().toString();

            //Criando o objeto remoto e exportando e registrando no rmiregistry
            Ouvinte ouvinte = new Ouvinte();
            Ouvinte stub = (Ouvinte) UnicastRemoteObject.exportObject(ouvinte, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(ID, stub);

            //Menuzinho
            int opcao = -1;
            while(opcao != 0){
                System.out.println("Escolha uma opção: ");
                System.out.println("0 -> Sair")
                System.out.println("1 -> Registrar em topico")

                opcao = sc.nextInt();
                sc.nextLine();

                if (opcao == 1){
                    this.registrarTopico()//Não completei, parei aqui
                }
            }
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }
}
