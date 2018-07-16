package ro.project;

import java.util.ArrayList;

/**
 * Created by marco on 10/07/2018.
 */
public class Rotta {

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

    public void chiudiRotta() {
        Nodo deposito = nodi.get(0);
        this.costo += MatriceDistanze.getInstanza().getDistanza(deposito.getId(), nodi.get(nodi.size() - 1).getId());
        this.nodi.add(deposito);
    }

    public Double getCosto() {
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

    public void aggiungiNodo(Nodo nodo, int i){
        this.nodi.add(i,nodo);
        this.aggiornaCosto();
    }

    public void replace(Rotta r2, int i1, int i2){

    }


    public int getIndiceVeicolo() {
        return indiceVeicolo;
    }

    public void setIndiceVeicolo(int indiceVeicolo) {
        this.indiceVeicolo = indiceVeicolo;
    }

    public void merge(Rotta r2){
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

    public void setCapacitaVeicolo(int capacitaVeicolo) {
        this.capacitaVeicolo=capacitaVeicolo;
    }

    public int getCapacitaVeicolo() {
        return this.capacitaVeicolo;
    }
}
