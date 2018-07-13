//linehaul=delivery
package ro.project;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marco on 11/07/2018.
 */
public class ListaRotte {

    private ArrayList<Rotta> listaRotteSingole;
    private ArrayList<Rotta> listaRotteIniziali;
    private Double costoTotale = 0.0;
    private ArrayList<Nodo> nodiClienti;
    private Nodo nodoDeposito;

    public ListaRotte() {
        this.listaRotteSingole = new ArrayList<>();
        this.listaRotteIniziali = new ArrayList<>();
    }

//    public void inizializza(FileUploader file, MatriceSavings matrice) {
//
//        ArrayList<NodoCliente> linehaul = file.createLineHaulList();
//        ArrayList<NodoCliente> backhaul = file.createBackHaulList();
//        MatriceSavings matriceSavings = matrice;
//        int numeroVeicoli = file.getNumeroVeicoli();
//        int capacita = file.getCapacitaVeicolo();
//
//        Veicolo[] veicoli = new Veicolo[file.getNumeroVeicoli()];//creo un vettore di veicoli di dimensione pari al numero Totale Veicoli
//
//        ArrayList<SavingNodi> savingOrdinati = new ArrayList<>();
//        savingOrdinati = matriceSavings.ordinaSaving();
//
//        for (int n = 0; n < numeroVeicoli; n++) {
//            veicoli[n] = new Veicolo(n, capacita);
//        }
//
//        for (int i = 0; i < numeroVeicoli; i++) {
//
//            Rotta r = new Rotta(i);
//            r.aggiungiAllaRotta(file.getTuttiNodi().get(0));
//
//            for (int j = 0; j < savingOrdinati.size(); j++) {
//                NodoCliente a = savingOrdinati.get(j).getNodoA();
//                NodoCliente b = savingOrdinati.get(j).getNodoB();
//
//                if (a.getDelivery() < veicoli[i].getCapacita() && linehaul.contains(a)) {
//                    r.aggiungiAllaRotta(a);
//                    veicoli[i].setCapacita(veicoli[i].getCapacita() - a.getDelivery());
//                    linehaul.remove(a);
//                }
//
//                if (b.getDelivery() < veicoli[i].getCapacita() && linehaul.contains(b)) {
//                    r.aggiungiAllaRotta(b);
//                    veicoli[i].setCapacita(veicoli[i].getCapacita() - b.getDelivery());
//                    linehaul.remove(b);
//                }
//            }
//            r.chiudiRotta();
//            costoTotale += r.getCosto();
//            listaRotteSingole.add(r);
//            System.out.println("rotta: " + i + " costo: " + r.getCosto());
//            // System.out.println(linehaul.size());
//
//        }
//        System.out.println("CostoTotale: " + costoTotale);
//    }

    public void inizializza(FileUploader istanza) {

        this.nodiClienti = istanza.getLineHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        for (Nodo cliente : nodiClienti) {
            Rotta r = new Rotta(-1);
//            r.aggiungiAllaRotta(nodoDeposito);
            r.aggiungiAllaRotta(cliente);
//            r.aggiungiAllaRotta(nodoDeposito);
            listaRotteSingole.add(r);

        }

        for(int i = 0; i< istanza.getNumeroVeicoli(); i++){
            Rotta rottaIniziale = listaRotteSingole.get(0);
            rottaIniziale.setIndiceVeicolo(i+1);
            listaRotteIniziali.add(rottaIniziale);
            listaRotteSingole.remove(0);
        }

        for(int i = 0; i< istanza.getNumeroVeicoli(); i++){
            
        }

    }

    public Rotta merge(Rotta r1, Rotta r2){

        return null;
    }

    public void relocate(ArrayList<Rotta> r) {
    }

    public void exchange(ArrayList<Rotta> r) {
        Random rand = new Random();

        int index1 = rand.nextInt(r.size());
        int index2 = rand.nextInt(r.size());
        while (index2 == index1) {
            index2 = rand.nextInt(r.size());
        }

        Rotta rotta1 = r.get(index1);
        Rotta rotta2 = r.get(index2);
        //
    }


    public ArrayList<Rotta> ottieniRotte() {
        return this.listaRotteIniziali;
    }

}
