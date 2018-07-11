package ro.project;

import java.util.ArrayList;

public class MatriceSavings {

    private ArrayList<NodoCliente> nodi;
    private NodoDeposito nodoDeposito;
    //private MatriceDistanze matriceDistanze;
    private Double[][] matriceSaving = null ;

    public MatriceSavings(ArrayList<NodoCliente> nodiCliente, NodoDeposito nodoDeposito){
        this.nodi = nodiCliente;
        this.nodoDeposito = nodoDeposito;
        this.matriceSaving = new Double[nodi.size()][nodi.size()];

    }

    public void calcolaMatriceSaving (){
        for(int i = 0; i < nodi.size(); i++) {
            for (int j = 0; j < nodi.size(); j++) {
                NodoCliente primo = nodi.get(i);
                NodoCliente secondo = nodi.get(j);
                matriceSaving[i][j] = MatriceDistanze.getInstanza().getDistanza(primo.getId(), nodoDeposito.getId()) + MatriceDistanze.getInstanza().getDistanza(secondo.getId(), nodoDeposito.getId()) - MatriceDistanze.getInstanza().getDistanza(primo.getId(), secondo.getId());
            }
        }

    }

    public Double getSavingAt(int a, int b){

        return matriceSaving[a-1][b-1];

    }

}
