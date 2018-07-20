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
public class ListaRotte implements Cloneable {

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

    /**
     *
     * @param istanza
     */
    public void inizializzaLineHaul(FileUploader istanza) {

        this.nodiClienti = istanza.getLineHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        for (Nodo cliente : nodiClienti) {
            Rotta r = new Rotta(-1);
            r.aggiungiAllaRotta(cliente);
            listaRotteSingole.add(r);
        }

        for (int i = 0; i < istanza.getNumeroVeicoli(); i++) {
            Rotta rottaIniziale = listaRotteSingole.get(0);
            rottaIniziale.setIndiceVeicolo(i + 1);
            listaRotteIniziali.add(rottaIniziale);
            rottaIniziale.setCapacitaVeicolo(istanza.getCapacitaVeicolo());
            //Veicolo veicolo = new Veicolo(i+1, istanza.getCapacitaVeicolo());
            //Sottraggo alla capaità totale del veicolo, la capacità del primo nodo della prima rotta
            int newCapacita = rottaIniziale.getCapacitaVeicolo() - ((NodoCliente) (rottaIniziale.getNodi().get(0))).getDelivery();
            rottaIniziale.setCapacitaVeicolo(newCapacita);
            //veicoli.add(veicolo);
            listaRotteSingole.remove(0);
        }
        while (listaRotteSingole.size() > 0) {
            for (int i = 0; i < istanza.getNumeroVeicoli() && listaRotteSingole.size() > 0; i++) {

                int random = (new Random()).nextInt(listaRotteSingole.size());
                Rotta rottaTemp = listaRotteSingole.get(random);
                int capacitaNodo = ((NodoCliente) rottaTemp.getNodi().get(0)).getDelivery();
                if (listaRotteIniziali.get(i).getCapacitaVeicolo() > capacitaNodo) {
                    listaRotteIniziali.get(i).merge(rottaTemp);
                    listaRotteIniziali.get(i).setCapacitaVeicolo(listaRotteIniziali.get(i).getCapacitaVeicolo() - capacitaNodo);
                    listaRotteSingole.remove(random);
                }
            }
        }

    }

    /**
     *
     * @param istanza
     */
    public void inizializzaBackHaul(FileUploader istanza) {

        this.nodiClientiBackHaul = istanza.getBackHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        for (Nodo cliente : nodiClientiBackHaul) {
            Rotta r = new Rotta(-1);
            r.aggiungiAllaRotta(cliente);
            listaRotteSingoleBackHaul.add(r);
        }
        System.out.println("Numero BackHaul totali: " + listaRotteSingoleBackHaul.size());
        int sizeBH = listaRotteSingoleBackHaul.size();
        for (int i = 0; i < istanza.getNumeroVeicoli() && !listaRotteSingoleBackHaul.isEmpty(); i++) {
            Rotta rottaIniziale = listaRotteSingoleBackHaul.get(0);
            rottaIniziale.setIndiceVeicolo(i + 1);
            listaRotteInizialiBackHaul.add(rottaIniziale);
            rottaIniziale.setCapacitaVeicolo(istanza.getCapacitaVeicolo());
            int newCapacita = rottaIniziale.getCapacitaVeicolo() - ((NodoCliente) (rottaIniziale.getNodi().get(0))).getPickup();
            rottaIniziale.setCapacitaVeicolo(newCapacita);
            //veicoliBH.add(veicolo);
            listaRotteSingoleBackHaul.remove(0);
            //int y =8+1; //TODO DA RIMUOVERE
            //System.out.print("|");
        }
        System.out.println("Numero BackHaul rimanenti: " + listaRotteSingoleBackHaul.size());
        while (!listaRotteSingoleBackHaul.isEmpty()) {
            for (int i = 0; i < istanza.getNumeroVeicoli() && !listaRotteSingoleBackHaul.isEmpty(); i++) {

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

    /**
     *
     * @param rotte
     */

    private void setListaRotte(ArrayList<Rotta> rotte) {
        this.listaRotteIniziali = rotte;
    }

    public ArrayList<Rotta> ottieniRotte() {
        return this.listaRotteIniziali;
    }

    public ArrayList<Rotta> ottieniRotteBH() {
        return this.listaRotteInizialiBackHaul;
    }

    /**
     * Effettua lo scambio di due nodi con costo minimo
     */
    public void bestExchange() {
        double tempCosto = this.getCostoTotale();
        int n1 = -1, n2 = -1;
        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotte().size(); i++) {
                    for (int j = 0; j < this.ottieniRotte().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotte().size(); k++) {
                            for (int l = 0; l < this.ottieniRotte().get(k).getNodi().size(); l++) {

                                int uno, due;
                                uno = this.ottieniRotte().get(i).getNodi().get(j).getId();//identificativo nodo da spostare
                                due = this.ottieniRotte().get(k).getNodi().get(l).getId();

                                l2.scambia(uno, due);

                                if (l2.getCostoTotale() < tempCosto) {
                                    tempCosto = l2.getCostoTotale();
                                    n1 = this.ottieniRotte().get(i).getNodi().get(j).getId();
                                    n2 = this.ottieniRotte().get(k).getNodi().get(l).getId();
                                    flag = true;

                                }

                                l2 = (ListaRotte) this.clone();  //rimetto tutto apposto
                            }
                        }

                    }
                    if (n1 >= 0 && n2 >= 0) {

                        this.scambia(n1, n2);
                        n1 = n2 = -1;
                        l2 = (ListaRotte) this.clone();

                    }
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void bestRelocate() {
        double tempCosto = this.getCostoTotale();
        int idNodo=-1;
        int indice =-1;//la POSIZIONE nella quale si vuole aggiungere il nodo nella seconda rotta
        int indice_rotta = -1;

        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotte().size(); i++) {
                    for (int j = 0; j < this.ottieniRotte().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotte().size(); k++) {
                            for (int l = 0; l < this.ottieniRotte().get(k).getNodi().size(); l++) {

                                int uno;
                                uno = this.ottieniRotte().get(i).getNodi().get(j).getId();//identificativo nodo da spostare


                                boolean flag2=l2.sposta(uno,k,l);

                                if (flag2) {//if flag2

                                      if (l2.getCostoTotale() < tempCosto) {
                                          tempCosto = l2.getCostoTotale();
                                          idNodo = uno;
                                          indice = l; //la posizione nella lista di nodi dove provo ad effettuare il relocate
                                          indice_rotta=indice;
                                          flag = true;
                                    }

                                }//if flag2
                                l2=(ListaRotte) this.clone();
                            }
                        }

                    }
                    if (idNodo >= 0 && indice_rotta >= 0 && indice>=0) {
                        this.sposta(idNodo,indice_rotta,indice);
                        idNodo = indice_rotta =indice= -1;
                        l2 = (ListaRotte) this.clone();
                    }
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param idNodo
     * @param idRotta
     * @return
     */
    public boolean sposta(int idNodo, int idRotta,int posizione) {
        Nodo nodoTemp = this.findNodoById(idNodo);
        Rotta a, b;

        a = this.ottieniRotte().get(this.findRottaByNodo(findNodoById(idNodo)));//la rotta dove devo rimuovere il nodo
        b = this.ottieniRotte().get(idRotta);//la rotta dove devo aggiungere il nodo

            if (a.equals(b)) {
                //System.out.println("Non posso fare il relocate tra rotte uguali");
                return false;
            } else {
                int capacitaInizialeA = a.getCapacitaVeicolo();//quando RIMUOVO il nodo devo aggiornare la capacità
                int capacitaInizialeB = b.getCapacitaVeicolo();//quando AGGIUNGO il nodo devo aggiornare la capacità

                   if (capacitaInizialeB  - ((NodoCliente)nodoTemp).getDelivery() >= 0) {
                    //Esegui il relocate
                    //elimino il nodo dalla rotta a e aggiorno la capacita e costo della rotta
                    a.rimuoviNodo(nodoTemp);
                    a.setCapacitaVeicolo(a.getCapacitaVeicolo()+((NodoCliente) nodoTemp).getDelivery());
                    a.aggiornaCosto();
                    //ora lo aggiungo alla rotta b e aggiorno le capacità e i costi
                    b.getNodi().set(posizione,nodoTemp);
                    b.setCapacitaVeicolo(capacitaInizialeB-((NodoCliente) nodoTemp).getDelivery());
                    b.aggiornaCosto();
                    return  true;
                } else {
                    return false;
                }
            }
    }

    public double getCostoTotale() {
        double tot = 0;
        for (Rotta r : this.ottieniRotte())
            tot += r.getCosto();
        return tot;
    }

    private Nodo findNodoById(int id) {

        for (Rotta r : this.ottieniRotte())
            for (Nodo n : r.getNodi())
                if (n.getId() == id)
                    return n;
        return null;
    }

    private int findRottaByNodo(Nodo nodo) {
        for (Rotta r : this.ottieniRotte())
            for (Nodo n : r.getNodi())
                if (n.equals(nodo))
                    return this.ottieniRotte().indexOf(r);
        return -1;
    }

    public boolean scambia(int id1, int id2) {
        Nodo temp = this.findNodoById(id1);
        Rotta a, b;
        //System.out.println("a = " + id1 + ", b = "+ id2);
        a = this.ottieniRotte().get(this.findRottaByNodo(findNodoById(id1)));
        b = this.ottieniRotte().get(this.findRottaByNodo(findNodoById(id2)));
        if (id1 != id2) {
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
        } else {
            return false;
        }

        return true;
    }

    public void stampaMatrice() {
        System.out.println("\nStampo Matrice Rotte/Nodi");
        for (Rotta r : this.ottieniRotte()) {
            System.out.println("R = " + r.getIndiceVeicolo());
            for (Nodo n : r.getNodi())
                System.out.print(n.getId() + ", ");

        }
    }

    public Object clone() {
        try {
            ListaRotte listaRotte;
            listaRotte = (ListaRotte) super.clone();

            listaRotte.listaRotteIniziali = (ArrayList<Rotta>) this.listaRotteIniziali.clone();
            for (int i = 0; i < listaRotte.listaRotteIniziali.size(); i++) {
                listaRotte.listaRotteIniziali.set(i, (Rotta) this.listaRotteIniziali.get(i).clone());
            }
            return listaRotte;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
