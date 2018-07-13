package ro.project;

import java.util.ArrayList;

/**
 * Created by marco on 10/07/2018.
 */
public class Rotta {

    private int indiceVeicolo;
    private ArrayList<Nodo> nodi;
    private Double costo = 0.0;


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
        MatriceDistanze m = MatriceDistanze.getInstanza();
        Nodo deposito = nodi.get(0);
        if(n.equals(deposito)){
            this.costo -= m.getDistanza(nodi.get(nodi.size()-2).getId(),nodi.get(nodi.size()-1).getId());
            nodi.remove(nodi.size()-1);
        }else {

            int index = nodi.indexOf(n);
            this.costo -= m.getDistanza(nodi.get(index - 1).getId(), n.getId());
            
            if (index < nodi.size()) {
                this.costo -= m.getDistanza(nodi.get(index + 1).getId(), n.getId());
                this.costo+=m.getDistanza(nodi.get(index - 1).getId(),nodi.get(index + 1).getId());
            }
            this.nodi.remove(n);
        }
    }

    public int getIndiceVeicolo() {
        return indiceVeicolo;
    }

    public void setIndiceVeicolo(int indiceVeicolo) {
        this.indiceVeicolo = indiceVeicolo;
    }
}
