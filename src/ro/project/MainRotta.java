package ro.project;

import java.util.ArrayList;

public class MainRotta {

    private ArrayList<Nodo> nodi;

    public MainRotta(){
        nodi=new ArrayList<>();
    }

    public void aggiungiAllaRotta(Nodo n){

        this.nodi.add(n);
    }

    public void chiudiRotta(){
        Nodo deposito=nodi.get(0);
        this.nodi.add(deposito);
    }

    public ArrayList<Nodo> getNodi() {
        return nodi;
    }


    public void stampaRotta(ArrayList<Nodo> n){
        for(Nodo a:n){
            System.out.print(a.getId() + ",");
        }
    }

}
