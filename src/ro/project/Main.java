//lainhoul=delivery
package ro.project;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String filename;
        FileUploader file = new FileUploader();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci il nome del file da importare (non includere il .txt):");
        filename = scanner.nextLine();

        file.caricaIstanza(filename);


        MatriceDistanze matr = new MatriceDistanze(file.getTuttiNodi());
        matr.creaMatrice();

        MatriceSavings matriceSavings = new MatriceSavings(file.getClienti(),file.getNodoDeposito());
        matriceSavings.calcolaMatriceSaving();


        ArrayList<SavingNodi> nodiSaving = matriceSavings.ordinaSaving();

       // Double valore = matriceSavings.getSavingAt(1,5);

//        for(int i=0; i < nodiSaving.size(); i++){
//            if (nodiSaving.get(i).getSaving() == valore){
//                System.out.println(nodiSaving.get(i).getNodoA().getId() + " " +
//                        nodiSaving.get(i).getNodoB().getId());
//            }
//        }

//        ListaRotte lista= new ListaRotte();
//        lista.inizializza(file,matriceSavings);
//        ArrayList<Rotta> c=lista.ottieniRotte();
//
//        //prova di stampa dei nodi visitati da ogni rotta
//        int num=0;
//        for(Rotta a:c){
//            System.out.println("");
//            System.out.print("Rotta " + num + ": ");
//            a.stampaRotta(a.getNodi());
//            num++;
//        }
//
//        lista.exchange(c);

    }
}
