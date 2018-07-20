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

        ListaRotte lista = new ListaRotte();
        lista.inizializzaLineHaul(file);
        lista.inizializzaBackHaul(file);

        //prova di stampa dei nodi visitati da ogni rotta
        ArrayList<Rotta> c = lista.ottieniRotte();
        int num=0; //TODO aggiungere visualizzazione del nodo deposito all'inizio e alla fine
        /**Prova stampa linehaul**/
        for(Rotta a:c){
            System.out.println("");
            System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
            a.stampaRotta(a.getNodi());
            System.out.print(" Capacita = " + a.getCapacitaVeicolo());// lista.getVeicoli().get(a.getIndiceVeicolo()-1).getCapacita());
            num++;
            System.out.println();
            System.out.println("Costo prima = " + a.getCosto());
            a.aggiornaCosto();
            System.out.println("Costo dopo = " + a.getCosto());
        }



        /**Prova stampa backhaul**/
        System.out.println();
        System.out.println("______________________________________________________");
        ArrayList<Rotta> b = lista.ottieniRotteBH();
        num=0;
        for(Rotta a:b){
            System.out.println("");
            System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");

            a.stampaRotta(a.getNodi());
            System.out.print(" Capacita = " + a.getCapacitaVeicolo());
            num++;
        }

        /**faccio il bestExchnge**/
//        double costoTotalePrima = lista.getCostoTotale();
//        int h = 0;
//        do {
//            costoTotalePrima=lista.getCostoTotale();
//            h++;
        System.out.println("ORA EXCHANGE");
           // lista.bestExchange();
//            //num = 0; //TODO aggiungere visualizzazione del nodo deposito all'inizio e alla fine
//            //int i = 0;
//            //for (Rotta a : c) {
//                //System.out.println("");
//                //System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
//                //a.stampaRotta(a.getNodi());
//                //System.out.print(" Capacita = " + a.getCapacitaVeicolo());// lista.getVeicoli().get(a.getIndiceVeicolo()-1).getCapacita());
//            //    num++;
//            //    System.out.println();
//            //    System.out.println("Costo prima = " + costi.get(i));
//            //    i++;
//            //    a.aggiornaCosto();
//            //    System.out.println("Costo dopo = " + a.getCosto());
//            //}
//            System.out.println("________________________________________");
//            System.out.println("Costo Totale Prima= " + costoTotalePrima);
//            System.out.println("Costo Totale Dopo = " + lista.getCostoTotale());
//            if(costoTotalePrima<lista.getCostoTotale())
//                System.out.println("Porca bagassa***********************************");
//
//        }while(h<2);

        //////////////////////////////////////***************
        System.out.println("ORA IL RELOCATE");
        double costopri = lista.getCostoTotale();
        int g = 0;

            costopri=lista.getCostoTotale();
            lista.bestRelocate();
            //num = 0; //TODO aggiungere visualizzazione del nodo deposito all'inizio e alla fine
            //int i = 0;
            //for (Rotta a : c) {
            //System.out.println("");
            //System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
            //a.stampaRotta(a.getNodi());
            //System.out.print(" Capacita = " + a.getCapacitaVeicolo());// lista.getVeicoli().get(a.getIndiceVeicolo()-1).getCapacita());
            //    num++;
            //    System.out.println();
            //    System.out.println("Costo prima = " + costi.get(i));
            //    i++;
            //    a.aggiornaCosto();
            //    System.out.println("Costo dopo = " + a.getCosto());
            //}
            System.out.println("________________________________________");
            System.out.println("Costo Totale Prima= " + costopri);
            System.out.println("Costo Totale Dopo = " + lista.getCostoTotale());
            if(costopri<lista.getCostoTotale())
                System.out.println("Porca bagassa relocate");







    }
}
