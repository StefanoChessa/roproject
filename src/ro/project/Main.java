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
        ArrayList<Rotta> c = lista.ottieniRotteLH();

        int numLH = 0; //TODO aggiungere visualizzazione del nodo deposito all'inizio e alla fine
        /**Prova stampa linehaul**/

        for(Rotta a:c){
            System.out.println("");
            System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
            a.stampaRotta(a.getNodi());
            System.out.print(" Capacita = " + a.getCapacitaVeicolo());// lista.getVeicoli().get(a.getIndiceVeicolo()-1).getCapacita());
            //num++;
            System.out.println();
            System.out.println("Costo prima = " + a.getCosto());
            a.aggiornaCosto();
            System.out.println("Costo dopo = " + a.getCosto());
            numLH+=a.getNodi().size();
        }
        System.out.print("Nodi totali prima LH " + numLH);



        /**Prova stampa backhaul**/
        System.out.println();
        System.out.println("______________________________________________________");
        ArrayList<Rotta> b = lista.ottieniRotteBH();
        int numBH = 0;
        for(Rotta a:b){
            System.out.println("");
            System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");

            a.stampaRotta(a.getNodi());
            System.out.print(" Capacita = " + a.getCapacitaVeicolo());
            //num++;
            System.out.println();
            System.out.println("Costo prima = " + a.getCosto());
            a.aggiornaCosto();
            System.out.println("Costo dopo = " + a.getCosto());
            numBH += a.getNodi().size();
        }

        System.out.print("Nodi totali prima BH " + numBH);

        /**faccio il bestExchnge o bestRelocate**/
        double costoTotalePrimaLH = lista.getCostoTotale("LH");
        double costoTotalePrimaBH = lista.getCostoTotale("BH");
        //int h = 0;
        //do {

        //h++;
        System.out.println("");
        costoTotalePrimaLH = lista.getCostoTotale("LH");
        costoTotalePrimaBH = lista.getCostoTotale("BH");
        System.out.println("Applico l'algoritmo best Improovement RL o EX");
        //lista.bestExchangeLinehaul();
        lista.bestRelocateLinehaul();
        //lista.bestExchangeBackhaul();
        lista.bestRelocateBackhaul();

        System.out.println("________________________________________");
        System.out.println("Costo Totale Prima LineHaul= " + costoTotalePrimaLH);
        System.out.println("Costo Totale Dopo LineHaul = " + lista.getCostoTotale("LH"));
        System.out.println("Costo Totale Prima= " + costoTotalePrimaLH);
        System.out.println("Costo Totale Dopo = " + lista.getCostoTotale("LH"));
        if(costoTotalePrimaLH<lista.getCostoTotale("LH") || costoTotalePrimaBH<lista.getCostoTotale("BH"))
        System.out.println("ATTENZIONE***********************************");

        //}while(h<2);

        lista.merge();
        ArrayList<Rotta> rottefinali=lista.ottieniRotteLH();
        int nodiTotaliLH = 0;
        for(Rotta a:rottefinali){
            System.out.println("");
            System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
            a.stampaRotta(a.getNodi());
            System.out.print(" Capacita = " + a.getCapacitaVeicolo());// lista.getVeicoli().get(a.getIndiceVeicolo()-1).getCapacita());

            System.out.println();
            System.out.println("Costo prima = " + a.getCosto());
            a.aggiornaCosto();
            System.out.println("Costo dopo = " + a.getCosto());

            nodiTotaliLH += a.getNodi().size();
        }

        System.out.print("Nodi totali dopo " + nodiTotaliLH);
    }
}
