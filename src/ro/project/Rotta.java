package ro.project;


import java.util.ArrayList;

/**
 * Created by marco on 10/07/2018.
 */
public class Rotta {
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

}
