//linehaul=delivery
package ro.project;

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
    private ArrayList<Rotta> rotteFinali;
    private Nodo nodoDeposito;


    public ListaRotte() {
        this.listaRotteSingole = new ArrayList<>();
        this.listaRotteIniziali = new ArrayList<>();
        this.listaRotteSingoleBackHaul = new ArrayList<>();
        this.listaRotteInizialiBackHaul = new ArrayList<>();
        this.rotteFinali = new ArrayList<>();
        //this.veicoli = new ArrayList<>();
        //this.veicoliBH = new ArrayList<>();
    }

    /**
     * @param istanza
     */
    public void inizializzaLineHaul(FileUploader istanza) {
        boolean flag = false;
        this.nodiClienti = istanza.getLineHaulList();
        this.nodoDeposito = istanza.getNodoDeposito();

        //do {
            flag = false;



            for (Nodo cliente : nodiClienti) {
                Rotta r = new Rotta(-1);
                r.aggiungiAllaRotta(cliente);
                listaRotteSingole.add(r);
            }


            for (int i = 0; i < istanza.getNumeroVeicoli(); i++) {
                int random = (new Random()).nextInt(listaRotteSingole.size());
                Rotta rottaIniziale = listaRotteSingole.get(random);
                rottaIniziale.setIndiceVeicolo(i + 1);
                listaRotteIniziali.add(rottaIniziale);
                rottaIniziale.setCapacitaVeicolo(istanza.getCapacitaVeicolo());
                //Veicolo veicolo = new Veicolo(i+1, istanza.getCapacitaVeicolo());
                //Sottraggo alla capaità totale del veicolo, la capacità del primo nodo della prima rotta
                int newCapacita = rottaIniziale.getCapacitaVeicolo() - ((NodoCliente) (rottaIniziale.getNodi().get(0))).getDelivery();
                rottaIniziale.setCapacitaVeicolo(newCapacita);
                //veicoli.add(veicolo);
                listaRotteSingole.remove(random);
            }


        while (listaRotteSingole.size() > 0  ) {
            for (int i = 0; i < listaRotteSingole.size(); i++) {

                int random = (new Random()).nextInt(listaRotteSingole.size());
                int randomRotta = (new Random()).nextInt(listaRotteIniziali.size());
                Rotta rottaTemp = listaRotteSingole.get(random);
                int capacitaNodo = ((NodoCliente) rottaTemp.getNodi().get(0)).getDelivery();
                if (listaRotteIniziali.get(randomRotta).getCapacitaVeicolo() > capacitaNodo) {
                    listaRotteIniziali.get(randomRotta).mergeRotte(rottaTemp);
                    listaRotteIniziali.get(randomRotta).setCapacitaVeicolo(listaRotteIniziali.get(randomRotta).getCapacitaVeicolo() - capacitaNodo);
                    listaRotteSingole.remove(random);
                }
            }
        }



            if (listaRotteSingole.isEmpty()) {
                // controllaVincolo(listaRotteSingole,listaRotteIniziali);
                System.out.println("ok" + listaRotteSingole.size());
                flag = true;
            }


        //} while (flag);
       // System.out.println("tutti visitati" + listaRotteSingole.size());

    }
//
//    public void controllaVincolo(ArrayList<Rotta> nodiNonVisitati, ArrayList<Rotta> rotteEsistenti){
//
//        for(int i=0;i<nodiNonVisitati.size();i++){
//            Nodo nodo= nodiNonVisitati.get(i).getNodi().get(0);
//            for(int j=0;j<rotteEsistenti.size();j++){
//                int capacita=rotteEsistenti.get(j).getCapacitaVeicolo();
//                Rotta a=rotteEsistenti.get(j);
//                 for(int k=0;k< a.getNodi().size();k++){
//                     Nodo n= ((NodoCliente)a.getNodi().get(k));
//                     int capacitaNodo= ((NodoCliente)a.getNodi().get(k)).getDelivery();
//                     if(capacita + capacitaNodo > ((NodoCliente)nodo).getDelivery()){
//                         Rotta r = new Rotta(-1);
//                         r.aggiungiAllaRotta(n);
//                         a.getNodi().set(a.getNodi().indexOf(n),nodo);
//                         a.setCapacitaVeicolo(a.getCapacitaVeicolo() + capacitaNodo - ((NodoCliente)nodo).getDelivery());
//                         nodiNonVisitati.remove(nodiNonVisitati.get(i));
//                         nodiNonVisitati.add(r);
//
//                     }
//                 }
//            }
//
//        }
//    }

    /**
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

        }

        while (!listaRotteSingoleBackHaul.isEmpty()) {
            for (int i = 0; i < istanza.getNumeroVeicoli() && !listaRotteSingoleBackHaul.isEmpty(); i++) {

                int random = (new Random()).nextInt(listaRotteSingoleBackHaul.size());
                Rotta rottaTemp = listaRotteSingoleBackHaul.get(random);
                int capacitaNodo = ((NodoCliente) rottaTemp.getNodi().get(0)).getPickup();
                if (listaRotteInizialiBackHaul.get(i).getCapacitaVeicolo() > capacitaNodo) {
                    listaRotteInizialiBackHaul.get(i).mergeRotte(rottaTemp);
                    listaRotteInizialiBackHaul.get(i).setCapacitaVeicolo(listaRotteInizialiBackHaul.get(i).getCapacitaVeicolo() - capacitaNodo);
                    listaRotteSingoleBackHaul.remove(random);

                }
            }

        }
    }

    /**
     * @param rotte
     */

    private void setListaRotte(ArrayList<Rotta> rotte) {

        this.listaRotteIniziali = rotte;
    }

    public ArrayList<Rotta> ottieniRotteLH() {
        return this.listaRotteIniziali;
    }

    public ArrayList<Rotta> ottieniRotteBH() {
        return this.listaRotteInizialiBackHaul;
    }

    /**
     * Effettua lo scambio di due nodi con costo minimo
     */
    public void bestExchangeLinehaul() {
        double tempCosto = this.getCostoTotale("LH");
        int n1 = -1, n2 = -1;
        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotteLH().size(); i++) {
                    for (int j = 0; j < this.ottieniRotteLH().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotteLH().size(); k++) {
                            for (int l = 0; l < this.ottieniRotteLH().get(k).getNodi().size(); l++) {

                                int uno, due;
                                uno = this.ottieniRotteLH().get(i).getNodi().get(j).getId();//identificativo nodo da spostare
                                due = this.ottieniRotteLH().get(k).getNodi().get(l).getId();

                                l2.scambiaLinehaul(uno, due);

                                if (l2.getCostoTotale("LH") < tempCosto) {
                                    tempCosto = l2.getCostoTotale("LH");
                                    n1 = this.ottieniRotteLH().get(i).getNodi().get(j).getId();
                                    n2 = this.ottieniRotteLH().get(k).getNodi().get(l).getId();
                                    flag = true;

                                }

                                l2 = (ListaRotte) this.clone();  //rimetto tutto apposto
                            }
                        }

                    }
                    if (n1 >= 0 && n2 >= 0) {

                        this.scambiaLinehaul(n1, n2);
                        n1 = n2 = -1;
                        l2 = (ListaRotte) this.clone();

                    }
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            System.exit(1);
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void bestRelocateLinehaul() {
        double tempCosto = this.getCostoTotale("LH");
        int idNodo = -1;
        int indice = -1;//la POSIZIONE nella quale si vuole aggiungere il nodo nella seconda rotta
        int indice_rotta = -1;

        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotteLH().size(); i++) {
                    for (int j = 0; j < this.ottieniRotteLH().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotteLH().size(); k++) {
                            for (int l = 0; l < this.ottieniRotteLH().get(k).getNodi().size(); l++) {

                                int uno;
                                uno = this.ottieniRotteLH().get(i).getNodi().get(j).getId();//identificativo nodo da spostare


                                boolean flag2 = l2.spostaLinehaul(uno, k, l);

                                if (flag2) {//if flag2

                                    if (l2.getCostoTotale("LH") < tempCosto) {
                                        tempCosto = l2.getCostoTotale("LH");
                                        idNodo = uno;
                                        indice = l; //la posizione nella lista di nodi dove provo ad effettuare il relocate
                                        indice_rotta = k;
                                        flag = true;
                                    }

                                }//if flag2
                                l2 = (ListaRotte) this.clone();
                            }
                        }

                    }
                    if (idNodo >= 0 && indice_rotta >= 0 && indice >= 0) {
                        this.spostaLinehaul(idNodo, indice_rotta, indice);
                        idNodo = indice_rotta = indice = -1;
                        l2 = (ListaRotte) this.clone();
                    }
                }
            } while (flag);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            System.exit(1);
            e.printStackTrace();
        }
    }

    /**
     * @param idNodo
     * @param idRotta
     * @param posizione
     * @return
     */
    public boolean spostaLinehaul(int idNodo, int idRotta, int posizione) {
        Nodo nodoTemp = this.findNodoById(idNodo, ottieniRotteLH());
        Rotta a, b;

        a = this.ottieniRotteLH().get(this.findRottaByNodo(findNodoById(idNodo, this.ottieniRotteLH()), this.ottieniRotteLH()));//la rotta dove devo rimuovere il nodo
        b = this.ottieniRotteLH().get(idRotta);//la rotta dove devo aggiungere il nodo

        if (a.equals(b)) {
            //System.out.println("Non posso fare il relocate tra rotte uguali");
            return false;
        } else {
            int capacitaInizialeA = a.getCapacitaVeicolo();//quando RIMUOVO il nodo devo aggiornare la capacità
            int capacitaInizialeB = b.getCapacitaVeicolo();//quando AGGIUNGO il nodo devo aggiornare la capacità

            if (capacitaInizialeB - ((NodoCliente) nodoTemp).getDelivery() >= 0 && a.getNodi().size() > 1) {
                //Esegui il relocate
                //elimino il nodo dalla rotta a e aggiorno la capacita e costo della rotta
                a.rimuoviNodo(nodoTemp);
                a.setCapacitaVeicolo(capacitaInizialeA + ((NodoCliente) nodoTemp).getDelivery());
                a.aggiornaCosto();
                //ora lo aggiungo alla rotta b e aggiorno le capacità e i costi
                b.getNodi().add(posizione, nodoTemp);
                b.setCapacitaVeicolo(capacitaInizialeB - ((NodoCliente) nodoTemp).getDelivery());
                b.aggiornaCosto();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * @param id1
     * @param id2
     * @return
     */
    public boolean scambiaLinehaul(int id1, int id2) {
        Nodo temp = this.findNodoById(id1, this.ottieniRotteLH());
        Rotta a, b;
        //System.out.println("a = " + id1 + ", b = "+ id2);
        a = this.ottieniRotteLH().get(this.findRottaByNodo(findNodoById(id1, this.ottieniRotteLH()), this.ottieniRotteLH()));
        b = this.ottieniRotteLH().get(this.findRottaByNodo(findNodoById(id2, this.ottieniRotteLH()), this.ottieniRotteLH()));
        if (id1 != id2) {
            if (a.equals(b)) {

                a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2, this.ottieniRotteLH())); //Sostituisco nella prima rotta il nodo 1 col nodo 2 ||||| Da problemi di indici: verificare index e id
                b.getNodi().set(b.getNodi().indexOf(findNodoById(id2, this.ottieniRotteLH())), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                a.aggiornaCosto();
                b.aggiornaCosto();
            } else {
                int capacitaInizialeA = a.getCapacitaVeicolo();
                int capacitaInizialeB = b.getCapacitaVeicolo();
                if (capacitaInizialeA + ((NodoCliente) findNodoById(id1, this.ottieniRotteLH())).getDelivery() - ((NodoCliente) findNodoById(id2, this.ottieniRotteLH())).getDelivery() >= 0 &&
                        capacitaInizialeB + ((NodoCliente) findNodoById(id2, this.ottieniRotteLH())).getDelivery() - ((NodoCliente) findNodoById(id1, this.ottieniRotteLH())).getDelivery() >= 0) {
                    //Esegui lo scambio
                    a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2, this.ottieniRotteLH())); //Sostituisco nella prima rotta il nodo 1 col nodo 2
                    b.getNodi().set(b.getNodi().indexOf(findNodoById(id2, this.ottieniRotteLH())), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                    a.setCapacitaVeicolo(capacitaInizialeA + ((NodoCliente) findNodoById(id1, this.ottieniRotteLH())).getDelivery() - ((NodoCliente) findNodoById(id2, this.ottieniRotteLH())).getDelivery());
                    b.setCapacitaVeicolo(capacitaInizialeB + ((NodoCliente) findNodoById(id2, this.ottieniRotteLH())).getDelivery() - ((NodoCliente) findNodoById(id1, this.ottieniRotteLH())).getDelivery());
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

    public double getCostoTotale(String tipo) {
        double tot = 0;
        if (tipo.equalsIgnoreCase("LH")) {
            for (Rotta r : this.ottieniRotteLH()) {//TODO costo totale dev essere di tutto non dei soli linehaul o dei soli backhaul
                tot += r.getCosto();
            }
        } else if (tipo.equalsIgnoreCase("BH")) {
            for (Rotta r : this.ottieniRotteBH()) {//TODO costo totale dev essere di tutto non dei soli linehaul o dei soli backhaul
                tot += r.getCosto();
            }
        } else if (tipo.equalsIgnoreCase("F")) {
            for (Rotta r : this.getRotteFinali()) {//TODO costo totale dev essere di tutto non dei soli linehaul o dei soli backhaul
                tot += r.getCosto();
            }
        }
        return tot;
    }

    private Nodo findNodoById(int id, ArrayList<Rotta> rotte) {

        for (Rotta r : rotte)
            for (Nodo n : r.getNodi())
                if (n.getId() == id)
                    return n;
        return null;
    }

    private int findRottaByNodo(Nodo nodo, ArrayList<Rotta> rotte) {
        for (Rotta r : rotte)
            for (Nodo n : r.getNodi())
                if (n.equals(nodo))
                    return rotte.indexOf(r);
        return -1;
    }


    /**
     * bestExchange sui backhaul
     */

    public void bestExchangeBackhaul() {
        double tempCosto = this.getCostoTotale("BH");
        int n1 = -1, n2 = -1;
        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotteBH().size(); i++) {
                    for (int j = 0; j < this.ottieniRotteBH().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotteBH().size(); k++) {
                            for (int l = 0; l < this.ottieniRotteBH().get(k).getNodi().size(); l++) {

                                int uno, due, tre, quattro;
                                uno = this.ottieniRotteBH().get(i).getNodi().get(j).getId();//identificativo nodo da spostare
                                due = this.ottieniRotteBH().get(k).getNodi().get(l).getId();

                                l2.scambiaBackhaul(uno, due);


                                if (l2.getCostoTotale("BH") < tempCosto) {
                                    tempCosto = l2.getCostoTotale("BH");
                                    n1 = this.ottieniRotteBH().get(i).getNodi().get(j).getId();
                                    n2 = this.ottieniRotteBH().get(k).getNodi().get(l).getId();
                                    flag = true;

                                }

                                l2 = (ListaRotte) this.clone();  //rimetto tutto apposto
                            }
                        }

                    }
                    if (n1 >= 0 && n2 >= 0) {

                        this.scambiaBackhaul(n1, n2);
                        n1 = n2 = -1;
                        l2 = (ListaRotte) this.clone();

                    }
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            System.exit(1);
            e.printStackTrace();
        }
    }

    /**
     * BestRelocate sulle rotte dei Backhaul
     */

    public void bestRelocateBackhaul() {
        double tempCosto = this.getCostoTotale("BH");
        int idNodo = -1;
        int indice = -1;//la POSIZIONE nella quale si vuole aggiungere il nodo nella seconda rotta
        int indice_rotta = -1;

        boolean flag = false;
        try {
            do {
                ListaRotte l2 = (ListaRotte) this.clone();

                flag = false;
                for (int i = 0; i < this.ottieniRotteBH().size(); i++) {
                    for (int j = 0; j < this.ottieniRotteBH().get(i).getNodi().size(); j++) {
                        for (int k = 0; k < this.ottieniRotteBH().size(); k++) {
                            for (int l = 0; l < this.ottieniRotteBH().get(k).getNodi().size(); l++) {

                                int uno;
                                uno = this.ottieniRotteBH().get(i).getNodi().get(j).getId();//identificativo nodo da spostare


                                boolean flag2 = l2.spostaBackhaul(uno, k, l);

                                if (flag2) {//if flag2

                                    if (l2.getCostoTotale("BH") < tempCosto) {
                                        tempCosto = l2.getCostoTotale("BH");
                                        idNodo = uno;
                                        indice = l; //la posizione nella lista di nodi dove provo ad effettuare il relocate
                                        indice_rotta = k;
                                        flag = true;
                                    }

                                }//if flag2
                                l2 = (ListaRotte) this.clone();
                            }
                        }

                    }
                    if (idNodo >= 0 && indice_rotta >= 0 && indice >= 0) {
                        this.spostaBackhaul(idNodo, indice_rotta, indice);
                        idNodo = indice_rotta = indice = -1;
                        l2 = (ListaRotte) this.clone();
                    }
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("ERRORE nella clonazione dell'oggetto di tipo ListaRotte");
            System.exit(1);
            e.printStackTrace();
        }
    }

    /**
     * @param id1
     * @param id2
     * @return
     */
    public boolean scambiaBackhaul(int id1, int id2) {
        Nodo temp = this.findNodoById(id1, this.ottieniRotteBH());
        Rotta a, b;
        //System.out.println("a = " + id1 + ", b = "+ id2);
        a = this.ottieniRotteBH().get(this.findRottaByNodo(findNodoById(id1, this.ottieniRotteBH()), this.ottieniRotteBH()));
        b = this.ottieniRotteBH().get(this.findRottaByNodo(findNodoById(id2, this.ottieniRotteBH()), this.ottieniRotteBH()));
        if (id1 != id2) {
            if (a.equals(b)) {
                a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2, this.ottieniRotteBH())); //Sostituisco nella prima rotta il nodo 1 col nodo 2 ||||| Da problemi di indici: verificare index e id
                b.getNodi().set(b.getNodi().indexOf(findNodoById(id2, this.ottieniRotteBH())), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                a.aggiornaCosto();
                b.aggiornaCosto();
            } else {
                int capacitaInizialeA = a.getCapacitaVeicolo();
                int capacitaInizialeB = b.getCapacitaVeicolo();
                if ((capacitaInizialeA + ((NodoCliente) findNodoById(id1, this.ottieniRotteBH())).getPickup() - ((NodoCliente) findNodoById(id2, this.ottieniRotteBH())).getPickup() >= 0 &&
                        capacitaInizialeB + ((NodoCliente) findNodoById(id2, this.ottieniRotteBH())).getPickup() - ((NodoCliente) findNodoById(id1, this.ottieniRotteBH())).getPickup() >= 0)) {
                    //Esegui lo scambio
                    a.getNodi().set(a.getNodi().indexOf(temp), findNodoById(id2, this.ottieniRotteBH())); //Sostituisco nella prima rotta il nodo 1 col nodo 2
                    b.getNodi().set(b.getNodi().indexOf(findNodoById(id2, this.ottieniRotteBH())), temp); //Sostituisco nella seconda rotta il nodo 2 col nodo 1
                    a.setCapacitaVeicolo(capacitaInizialeA + ((NodoCliente) findNodoById(id1, this.ottieniRotteBH())).getPickup() - ((NodoCliente) findNodoById(id2, this.ottieniRotteBH())).getPickup());
                    b.setCapacitaVeicolo(capacitaInizialeB + ((NodoCliente) findNodoById(id2, this.ottieniRotteBH())).getPickup() - ((NodoCliente) findNodoById(id1, this.ottieniRotteBH())).getPickup());
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

    /**
     * @param idNodo
     * @param idRotta
     * @param posizione
     * @return
     */
    public boolean spostaBackhaul(int idNodo, int idRotta, int posizione) {
        ArrayList<Rotta> rotte = this.ottieniRotteBH();
        Nodo nodoTemp = this.findNodoById(idNodo, rotte);
        Rotta a, b;


        a = this.ottieniRotteBH().get(this.findRottaByNodo((findNodoById(idNodo, this.ottieniRotteBH())), this.ottieniRotteBH()));//la rotta dove devo rimuovere il nodo
        b = this.ottieniRotteBH().get(idRotta);//la rotta dove devo aggiungere il nodo

        if (a.equals(b)) {
            //System.out.println("Non posso fare il relocate tra rotte uguali");
            return false;
        } else {
            int capacitaInizialeA = a.getCapacitaVeicolo();//quando RIMUOVO il nodo devo aggiornare la capacità
            int capacitaInizialeB = b.getCapacitaVeicolo();//quando AGGIUNGO il nodo devo aggiornare la capacità

            if (capacitaInizialeB - ((NodoCliente) nodoTemp).getPickup() >= 0 && a.getNodi().size() > 1) {
                //Esegui il relocate
                //elimino il nodo dalla rotta a e aggiorno la capacita e costo della rotta
                a.rimuoviNodo(nodoTemp);
                a.setCapacitaVeicolo(capacitaInizialeA + ((NodoCliente) nodoTemp).getPickup());
                a.aggiornaCosto();
                //ora lo aggiungo alla rotta b e aggiorno le capacità e i costi
                b.getNodi().add(posizione, nodoTemp);
                b.setCapacitaVeicolo(capacitaInizialeB - ((NodoCliente) nodoTemp).getPickup());
                b.aggiornaCosto();
                return true;
            } else {
                return false;
            }
        }
    }


    public void stampaMatrice() {
        System.out.println("\nStampo Matrice Rotte/Nodi");
        for (Rotta r : this.ottieniRotteLH()) {
            System.out.println("R = " + r.getIndiceVeicolo());
            for (Nodo n : r.getNodi())
                System.out.print(n.getId() + ", ");

        }
    }

    public ArrayList<Rotta> getRotteFinali() {
        return rotteFinali;
    }

    public void merge() {

        ArrayList<Rotta> lh = this.ottieniRotteLH();
        ArrayList<Rotta> bh = this.ottieniRotteBH();
        int ai, bi;
        Double costo = -1.0;
        Rotta temp = null;
        for (Rotta a : lh) {
            ai = a.getIndiceVeicolo();
            costo = -1.0;
            for (Rotta b : bh) {
                bi = b.getIndiceVeicolo();
                //if (a.getIndiceVeicolo()==b.getIndiceVeicolo()){
                if ((costo == -1.0 || costo < getCostoDifferenzaRotte(a, b)) && bi != -1) {
                    costo = getCostoDifferenzaRotte(a, b);
                    temp = b;
                }
            }
            if (temp != null) {
                for (Nodo nodo : temp.getNodi()) {
                    a.getNodi().add(nodo);
                    a.aggiornaCosto();
                    temp.setIndiceVeicolo(-1);
                }
                a.apriRotta(this.nodoDeposito);
                a.chiudiRotta(this.nodoDeposito);
            }
        }
        this.rotteFinali = lh;

    }

    public Double getCostoDifferenzaRotte(Rotta a, Rotta b) {
        int id_ultimo = a.getNodi().size() - 1;
        MatriceDistanze.getInstanza().getDistanza(a.getNodi().get(id_ultimo).getId(), b.getNodi().get(0).getId());
        return 0.0;
    }

    public Object clone() {
        try {
            ListaRotte listaRotte;
            listaRotte = (ListaRotte) super.clone();

            listaRotte.costoTotale = this.costoTotale + 0;
            listaRotte.listaRotteIniziali = (ArrayList<Rotta>) this.listaRotteIniziali.clone();
            for (int i = 0; i < listaRotte.listaRotteIniziali.size(); i++) {
                listaRotte.listaRotteIniziali.set(i, (Rotta) this.listaRotteIniziali.get(i).clone());
            }

            listaRotte.listaRotteInizialiBackHaul = (ArrayList<Rotta>) this.listaRotteInizialiBackHaul.clone();

            for (int i = 0; i < listaRotte.listaRotteInizialiBackHaul.size(); i++) {
                listaRotte.listaRotteInizialiBackHaul.set(i, (Rotta) this.listaRotteInizialiBackHaul.get(i).clone());
            }

            listaRotte.listaRotteSingole = (ArrayList<Rotta>) this.listaRotteSingole.clone();

            for (int i = 0; i < listaRotte.listaRotteSingole.size(); i++) {
                listaRotte.listaRotteSingole.set(i, (Rotta) this.listaRotteSingole.get(i).clone());
            }
            listaRotte.listaRotteSingoleBackHaul = (ArrayList<Rotta>) this.listaRotteSingoleBackHaul.clone();
            for (int i = 0; i < listaRotte.listaRotteSingoleBackHaul.size(); i++) {
                listaRotte.listaRotteSingoleBackHaul.set(i, (Rotta) this.listaRotteSingoleBackHaul.get(i).clone());
            }


            return listaRotte;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
