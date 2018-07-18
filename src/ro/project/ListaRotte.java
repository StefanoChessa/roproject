//linehaul=delivery
package ro.project;

import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.security.krb5.internal.crypto.Aes128;

import javax.print.attribute.standard.NumberOfDocuments;
import java.io.Console;
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
    private void setListaRotte(ArrayList<Rotta> rotte){
        this.listaRotteIniziali=rotte;
    }

    public ArrayList<Rotta> ottieniRotte() {
        return this.listaRotteIniziali;
    }
    public ArrayList<Rotta> ottieniRotteBH() {
        return this.listaRotteInizialiBackHaul;
    }

    public void bestExchange(){
        double tempCosto = this.getCostoTotale();
        int n1 = -1, n2 = -1;
        try {
            ListaRotte l2 = new ListaRotte();
            l2.setListaRotte((ArrayList<Rotta>) this.ottieniRotte().clone());

            for (int i = 0; i < this.ottieniRotte().size(); i++){
                for (int j = 0; j < this.ottieniRotte().get(i).getNodi().size(); j++){
                    for(int k = 0; k<this.ottieniRotte().size(); k++){
                        for(int l = 0; l < this.ottieniRotte().get(k).getNodi().size(); l++){
                            l2.scambia( this.ottieniRotte().get(i).getNodi().get(j).getId(),
                                        this.ottieniRotte().get(k).getNodi().get(l).getId());

                            if (l2.getCostoTotale()<tempCosto){
                                tempCosto = l2.getCostoTotale();
                                n1 = this.ottieniRotte().get(i).getNodi().get(j).getId();
                                n2 = this.ottieniRotte().get(k).getNodi().get(l).getId();

                            }
                            l2.scambia( this.ottieniRotte().get(k).getNodi().get(l).getId(),
                                    this.ottieniRotte().get(i).getNodi().get(j).getId());
                        }
                    }

                }
                if(n1>=0 && n2 >=0){
                    this.scambia(n1,n2);
                    n1 = n2 = -1;
                    this.setListaRotte(l2.ottieniRotte());
                }
            }
        } catch (Exception e) {
        System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
        e.printStackTrace();
    }
    }

    public double getCostoTotale(){
        double tot = 0;
        for (Rotta r : this.ottieniRotte())
            tot += r.getCosto();
        return tot;
    }

    private Nodo findNodoById(int id){

        for(Rotta r : this.ottieniRotte())
            for(Nodo n : r.getNodi())
                if(n.getId() == id)
                    return n;
        return null;
    }
    private int findRottaByNodo(Nodo nodo){
        for(Rotta r : this.ottieniRotte())
            for(Nodo n : r.getNodi())
                if(n.equals(nodo))
                    return this.ottieniRotte().indexOf(r);
        return -1;
    }
    public boolean scambia(int id1, int id2){
        Nodo temp = this.findNodoById(id1);
        Rotta a,b;
        //System.out.println("a = " + id1 + ", b = "+ id2);
        a = this.ottieniRotte().get(this.findRottaByNodo(findNodoById(id1)));
        b = this.ottieniRotte().get(this.findRottaByNodo(findNodoById(id2)));
        if(id1!=id2) {
            if (a.equals(b)) {
                //scambia i due nodi senza aggiornare la capacità residua
                //System.out.println("");
                //System.out.println("a.getNodi().indexOf(temp) = " + a.getNodi().indexOf(temp));
                //System.out.println("findNodoById(id2) = "+findNodoById(id2));
                a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2)); //Sostituisco nella prima rotta il nodo 1 col nodo 2 ||||| Da problemi di indici: verificare index e id
                b.getNodi().set(b.getNodi().indexOf(findNodoById(id2)), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                a.aggiornaCosto();
                b.aggiornaCosto();
            } else {
                int capacitaInizialeA = a.getCapacitaVeicolo();
                int capacitaInizialeB = b.getCapacitaVeicolo();
                if (capacitaInizialeA + ((NodoCliente) findNodoById(id1)).getDelivery() - ((NodoCliente) findNodoById(id2)).getDelivery() >= 0 &&
                        capacitaInizialeB + ((NodoCliente) findNodoById(id2)).getDelivery() - ((NodoCliente) findNodoById(id1)).getDelivery() >= 0) {
                    //Esegui lo scambio
                    a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2)); //Sostituisco nella prima rotta il nodo 1 col nodo 2
                    b.getNodi().set(b.getNodi().indexOf(findNodoById(id2)), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                    a.aggiornaCosto();
                    b.aggiornaCosto();
                } else {
                    return false;
                }
            }
        }else{
            return false;
        }

        return true;
    }

    public void stampaMatrice(){
        System.out.println("\nStampo Matrice Rotte/Nodi");
        for(Rotta r:this.ottieniRotte()) {
            System.out.println("R = " + r.getIndiceVeicolo());
            for(Nodo n:r.getNodi())
            System.out.print(n.getId() + ", ");

        }
    }

}
