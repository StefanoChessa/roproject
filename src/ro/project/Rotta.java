package ro.project;

import java.util.ArrayList;

/**
 * Created by marco on 10/07/2018.
 */
public class Rotta implements  Cloneable {

    private int indiceVeicolo;
    private ArrayList<Nodo> nodi;
    private Double costo = 0.0;
    private int capacitaVeicolo = 0;


    public Rotta(int indice) {
        this.setIndiceVeicolo(indice);
        nodi = new ArrayList<>();
    }

    public void aggiungiAllaRotta(Nodo n) {
        if (nodi.size() > 0) {
            this.costo += MatriceDistanze.getInstanza().getDistanza(n.getId(), nodi.get(nodi.size() - 1).getId());
        }
        this.nodi.add(n);
    }

    public void chiudiRotta(Nodo nodoDeposito) {
        this.costo += MatriceDistanze.getInstanza().getDistanza(nodoDeposito.getId(), nodi.get(nodi.size() - 1).getId());
        this.nodi.add(nodoDeposito);
    }

    public void apriRotta(Nodo nodoDeposito) {

        this.costo += MatriceDistanze.getInstanza().getDistanza(nodoDeposito.getId(), nodi.get(nodi.size() - 1).getId());
        this.nodi.add(0,nodoDeposito);
    }

    public Double getCosto() {
        this.aggiornaCosto();
        return this.costo;
    }

    public ArrayList<Nodo> getNodi() {
        return nodi;
    }

    public void stampaRotta(ArrayList<Nodo> n) {
        for (Nodo a : n) {
            System.out.print(a.getId() + ",");
        }
    }

    public void rimuoviNodo(Nodo n){
        this.nodi.remove(n);
        this.aggiornaCosto();
    }

//    public void aggiungiNodo(Nodo nodo, int i){
//        this.nodi.add(i,nodo);
//        this.aggiornaCosto();
//    }

    public boolean aggiungiNodo(Nodo nodo, int i){
        if(this.capacitaVeicolo-((NodoCliente)nodo).getDelivery()>=0) {
            this.nodi.add(i, nodo);
            this.aggiornaCosto();
            this.setCapacitaVeicolo(this.getCapacitaVeicolo()-((NodoCliente)nodo).getDelivery());
            return true;
        }
        return false;
    }



    public int getIndiceVeicolo() {
        return indiceVeicolo;
    }

    public void setIndiceVeicolo(int indiceVeicolo) {
        this.indiceVeicolo = indiceVeicolo;
    }

    public void mergeRotte(Rotta r2){
        for(Nodo n : r2.getNodi()){
            this.aggiungiAllaRotta(n);
        }
    }

    public void aggiornaCosto(){
        this.costo = 0.0;
        costo += MatriceDistanze.getInstanza().getDistanza(0,this.getNodi().get(0).getId());
        for(int i = 0; i<this.getNodi().size()-1;i++){
            this.costo += MatriceDistanze.getInstanza().getDistanza(
                    this.getNodi().get(i).getId(),
                    this.getNodi().get(i+1).getId());
        }
        //TODO Aggiungere alla fine il nodo deposito
    }

    public void setCapacitaVeicolo(int capacitaVeicolo) { this.capacitaVeicolo=capacitaVeicolo; }

    public int getCapacitaVeicolo() {
        return this.capacitaVeicolo;
    }



    public Object clone() {
        try {
            Rotta r;
            r = (Rotta) super.clone();
            r.nodi = (ArrayList<Nodo>) this.nodi.clone();
            for(int i=0;i<r.nodi.size();i++) {
                r.nodi.set(i,((Nodo) this.nodi.get(i).clone()));
            }
            return r;
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
