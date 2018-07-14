//linehaul=delivery
package ro.project;

import sun.security.krb5.internal.crypto.Aes128;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marco on 11/07/2018.
 */
public class ListaRotte {

    private ArrayList<Rotta> listaRotteSingole;
    private ArrayList<Rotta> listaRotteIniziali;
    private ArrayList<Rotta> listaRotteSingoleBackHaul;
    private ArrayList<Rotta> listaRotteInizialiBackHaul;
    private Double costoTotale = 0.0;
    private ArrayList<Nodo> nodiClienti;
    private ArrayList<Nodo> nodiClientiBackHaul;
    private Nodo nodoDeposito;
    private ArrayList<Veicolo> veicoli;
    private ArrayList<Veicolo> veicoliBH;

    public ListaRotte() {
        this.listaRotteSingole = new ArrayList<>();
        this.listaRotteIniziali = new ArrayList<>();
        this.listaRotteSingoleBackHaul = new ArrayList<>();
        this.listaRotteInizialiBackHaul = new ArrayList<>();
        this.veicoli = new ArrayList<>();
        this.veicoliBH = new ArrayList<>();
    }

//    public void inizializzaLineHaul(FileUploader file, MatriceSavings matrice) {
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

    public void inizializzaLineHaul(FileUploader istanza) {

        this.nodiClienti = istanza.getLineHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        for (Nodo cliente : nodiClienti) {
            Rotta r = new Rotta(-1);
            r.aggiungiAllaRotta(cliente);
            listaRotteSingole.add(r);
        }

        for(int i = 0; i< istanza.getNumeroVeicoli(); i++){
            Rotta rottaIniziale = listaRotteSingole.get(0);
            rottaIniziale.setIndiceVeicolo(i+1);
            listaRotteIniziali.add(rottaIniziale);
            Veicolo veicolo = new Veicolo(i+1, istanza.getCapacitaVeicolo());
            //Sottraggo alla capaità totale del veicolo, la capacità del primo nodo della prima rotta
            int newCapacita = veicolo.getCapacita() - ((NodoCliente)(rottaIniziale.getNodi().get(0))).getDelivery();
            veicolo.setCapacita(newCapacita);
            veicoli.add(veicolo);
            listaRotteSingole.remove(0);
        }
        while(listaRotteSingole.size()>0){
            for(int i = 0; i< istanza.getNumeroVeicoli() && listaRotteSingole.size()>0; i++){

                int random = (new Random()).nextInt(listaRotteSingole.size());
                Rotta rottaTemp = listaRotteSingole.get(random);
                int capacitaNodo = ((NodoCliente)rottaTemp.getNodi().get(0)).getDelivery();
                if(veicoli.get(i).getCapacita()>capacitaNodo){
                    listaRotteIniziali.get(i).merge(rottaTemp);
                    veicoli.get(i).setCapacita(veicoli.get(i).getCapacita()-capacitaNodo);
                    listaRotteSingole.remove(random);
                }
            }
        }

    }

    public void inizializzaBackHaul(FileUploader istanza) {

        this.nodiClientiBackHaul = istanza.getBackHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        for (Nodo cliente : nodiClientiBackHaul) {
            Rotta r = new Rotta(-1);
            r.aggiungiAllaRotta(cliente);
            listaRotteSingoleBackHaul.add(r);
        }
        System.out.println("Numero BackHaul totali: "+listaRotteSingoleBackHaul.size());
        int sizeBH = listaRotteSingoleBackHaul.size();
        for(int i = 0; i < istanza.getNumeroVeicoli() && !listaRotteSingoleBackHaul.isEmpty(); i++){
            Rotta rottaIniziale = listaRotteSingoleBackHaul.get(0);
            rottaIniziale.setIndiceVeicolo(i+1);
            listaRotteInizialiBackHaul.add(rottaIniziale);
            Veicolo veicolo = new Veicolo(i+1, istanza.getCapacitaVeicolo());
            //Sottraggo alla capaità totale del veicolo, la capacità del primo nodo della prima rotta
            int newCapacita = veicolo.getCapacita() - ((NodoCliente)(rottaIniziale.getNodi().get(0))).getPickup();
            veicolo.setCapacita(newCapacita);
            veicoliBH.add(veicolo);
            listaRotteSingoleBackHaul.remove(0);
            //int y =8+1; //TODO DA RIMUOVERE
            //System.out.print("|");
        }
        System.out.println("Numero BackHaul rimanenti: "+listaRotteSingoleBackHaul.size());
        while(!listaRotteSingoleBackHaul.isEmpty()){
            for(int i = 0; i< istanza.getNumeroVeicoli() && !listaRotteSingoleBackHaul.isEmpty(); i++) {

                int random = (new Random()).nextInt(listaRotteSingoleBackHaul.size());
                Rotta rottaTemp = listaRotteSingoleBackHaul.get(random);
                int capacitaNodo = ((NodoCliente) rottaTemp.getNodi().get(0)).getPickup();
                if (veicoliBH.get(i).getCapacita() > capacitaNodo) {
                    listaRotteInizialiBackHaul.get(i).merge(rottaTemp);
                    veicoliBH.get(i).setCapacita(veicoliBH.get(i).getCapacita() - capacitaNodo);
                    listaRotteSingoleBackHaul.remove(random);
                    int y = 8 + 1; //TODO DA RIMUOVERE
                }
            }
            System.out.println("Sto mettendo BackHaul");
        }
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
    public ArrayList<Rotta> ottieniRotteBH() {
        return this.listaRotteInizialiBackHaul;
    }

    public ArrayList<Veicolo> getVeicoli() {
        return veicoli;
    }
    public ArrayList<Veicolo> getVeicoliBH() {
        return veicoliBH;
    }

}
