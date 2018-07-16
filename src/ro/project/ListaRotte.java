//linehaul=delivery
package ro.project;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    //private ArrayList<Veicolo> veicoli;
    //private ArrayList<Veicolo> veicoliBH;

    public ListaRotte() {
        this.listaRotteSingole = new ArrayList<>();
        this.listaRotteIniziali = new ArrayList<>();
        this.listaRotteSingoleBackHaul = new ArrayList<>();
        this.listaRotteInizialiBackHaul = new ArrayList<>();
        //this.veicoli = new ArrayList<>();
        //this.veicoliBH = new ArrayList<>();
    }

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
            rottaIniziale.setCapacitaVeicolo(istanza.getCapacitaVeicolo());
            //Veicolo veicolo = new Veicolo(i+1, istanza.getCapacitaVeicolo());
            //Sottraggo alla capaità totale del veicolo, la capacità del primo nodo della prima rotta
            int newCapacita = rottaIniziale.getCapacitaVeicolo() - ((NodoCliente)(rottaIniziale.getNodi().get(0))).getDelivery();
            rottaIniziale.setCapacitaVeicolo(newCapacita);
            //veicoli.add(veicolo);
            listaRotteSingole.remove(0);
        }
        while(listaRotteSingole.size()>0){
            for(int i = 0; i< istanza.getNumeroVeicoli() && listaRotteSingole.size()>0; i++){

                int random = (new Random()).nextInt(listaRotteSingole.size());
                Rotta rottaTemp = listaRotteSingole.get(random);
                int capacitaNodo = ((NodoCliente)rottaTemp.getNodi().get(0)).getDelivery();
                if(listaRotteIniziali.get(i).getCapacitaVeicolo()>capacitaNodo){
                    listaRotteIniziali.get(i).merge(rottaTemp);
                    listaRotteIniziali.get(i).setCapacitaVeicolo(listaRotteIniziali.get(i).getCapacitaVeicolo()-capacitaNodo);
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
            rottaIniziale.setCapacitaVeicolo(istanza.getCapacitaVeicolo());
            int newCapacita = rottaIniziale.getCapacitaVeicolo() - ((NodoCliente)(rottaIniziale.getNodi().get(0))).getPickup();
            rottaIniziale.setCapacitaVeicolo(newCapacita);
            //veicoliBH.add(veicolo);
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
                if (listaRotteInizialiBackHaul.get(i).getCapacitaVeicolo() > capacitaNodo) {
                    listaRotteInizialiBackHaul.get(i).merge(rottaTemp);
                    listaRotteInizialiBackHaul.get(i).setCapacitaVeicolo(listaRotteInizialiBackHaul.get(i).getCapacitaVeicolo() - capacitaNodo);
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


    public void exchange(){
        int indiceRotta = -1, indiceNodo = -1;
        double costoTotaleTemp = this.getCostoTotale();
        for(Rotta r1: this.ottieniRotte()){
            for (Nodo n1: r1.getNodi()){
                for(Rotta r2: this.ottieniRotte()){
                    for(Nodo n2: r2.getNodi()){
                        if(!n1.equals(n2)){
                            Rotta temp1, temp2;
                            temp1 = r1;
                            temp2 = r2;
                            if(scambia(r1,n1,r2,n2)){
                                if(this.getCostoTotale()<costoTotaleTemp){
                                    costoTotaleTemp = this.getCostoTotale();
                                    indiceRotta = this.ottieniRotte().indexOf(r2);
                                    indiceNodo = r2.getNodi().indexOf(n2);
                                }
                            }
                            r1 = temp1;
                            r2 = temp2;
                        }
                    }
                }
                Rotta conveniente = this.ottieniRotte().get(indiceRotta);
                scambia(r1,n1, conveniente, conveniente.getNodi().get(indiceNodo));
            }
        }
    }

    public double getCostoTotale(){
        double tot = 0;
        for (Rotta r : this.ottieniRotte())
            tot += r.getCosto();
        return tot;
    }

    private Boolean scambia(Rotta r1, Nodo n1, Rotta r2, Nodo n2){
        Rotta temp1, temp2;
        temp1 = r1;
        temp2 = r2;
        int iNodo1, iNodo2;
        int cap1, cap2;
        iNodo1 = r1.getNodi().indexOf(n1);
        iNodo2 = r2.getNodi().indexOf(n2);
        cap1 = ((NodoCliente)n1).getDelivery();
        cap2 = ((NodoCliente)n2).getDelivery();
        temp1.rimuoviNodo(n1);
        temp1.setCapacitaVeicolo(temp1.getCapacitaVeicolo()+cap1);
        if(temp1.getCapacitaVeicolo()-cap2>0){
            temp2.rimuoviNodo(n2);
            temp2.setCapacitaVeicolo(temp2.getCapacitaVeicolo()+cap2);
            if(temp2.getCapacitaVeicolo()-cap1>0){
                temp1.aggiungiNodo(n2,iNodo2);
                temp2.aggiungiNodo(n1,iNodo1);
                r1=temp1;
                r2=temp2;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

}
