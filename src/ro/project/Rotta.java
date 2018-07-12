package ro.project;


import java.util.ArrayList;

/**
 * Created by marco on 10/07/2018.
 */
public class Rotta extends MainRotta {

    private int indiceVeicolo;
    private ArrayList<Nodo> nodi;
    private Double costo= 0.0;


    public Rotta(int indice){
        this.indiceVeicolo=indice;
        nodi=new ArrayList<>();
    }

    public void aggiungiAllaRotta(Nodo n){

        if(nodi.size()>0) {
             this.costo += MatriceDistanze.getInstanza().getDistanza(n.getId(),nodi.get(nodi.size()-1).getId());
        }

        this.nodi.add(n);
    }

    public void chiudiRotta(){
        Nodo deposito=nodi.get(0);
        this.costo+=MatriceDistanze.getInstanza().getDistanza(deposito.getId(),nodi.get(nodi.size() - 1).getId());
        this.nodi.add(deposito);
    }

    public Double getCosto() {
        return this.costo;
    }

    public ArrayList<Nodo> getNodi() {
        return nodi;
    }

    public Rotta rimuoviNodo(){
        //eliminare il nodo dalla rotta aggiornando il costo e aumentare nuovamente la capacità del veicolo
        return this;
    }

    public void stampaRotta(ArrayList<Nodo> n){
        for(Nodo a:n){
            System.out.print(a.getId() + ",");
        }
    }

}
