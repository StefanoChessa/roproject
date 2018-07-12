package ro.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatriceSavings {

    private ArrayList<Nodo> nodi;
    private NodoDeposito nodoDeposito;
    //private MatriceDistanze matriceDistanze;
    private Double[][] matriceSaving = null ;

    public MatriceSavings(ArrayList<Nodo> nodiCliente, NodoDeposito nodoDeposito){
        this.nodi = nodiCliente;
        this.nodoDeposito = nodoDeposito;
        this.matriceSaving = new Double[nodi.size()][nodi.size()];

    }

    public void calcolaMatriceSaving (){
        for(int i = 0; i < nodi.size(); i++) {
            for (int j = 0; j < nodi.size(); j++) {
                Nodo primo = nodi.get(i);
                Nodo secondo = nodi.get(j);
                matriceSaving[i][j] = MatriceDistanze.getInstanza().getDistanza(primo.getId(), nodoDeposito.getId()) + MatriceDistanze.getInstanza().getDistanza(secondo.getId(), nodoDeposito.getId()) - MatriceDistanze.getInstanza().getDistanza(primo.getId(), secondo.getId());
            }
        }

    }

    public Double getSavingAt(int a, int b){

        return matriceSaving[a-1][b-1];

    }

    public ArrayList<SavingNodi> creaSaving(){

        ArrayList<SavingNodi> listaNodi = new ArrayList<>();

        for(int i = 0; i < nodi.size(); i++) {
            for (int j = 0; j < nodi.size(); j++) {
                listaNodi.add(new SavingNodi(nodi.get(i), nodi.get(j), getSavingAt(i+1,j+1)));
            }
        }

        return listaNodi;
    }

    public ArrayList<SavingNodi> ordinaSaving(){

        ArrayList<SavingNodi> listaSaving = creaSaving();

        //ordinati al contrario
        Collections.sort(listaSaving, new Comparator<SavingNodi>() {
            public int compare(SavingNodi s1, SavingNodi s2) {
                return s2.getSaving().compareTo(s1.getSaving());
            }
        });

        return listaSaving;
    }

}
