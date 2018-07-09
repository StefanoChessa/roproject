package ro.project;

import java.util.ArrayList;

public class MatriceDistanze {
    private ArrayList<NodoCliente> listaClienti;
    private NodoDeposito nodoDeposito;

    public MatriceDistanze(ArrayList<NodoCliente> listaClienti, NodoDeposito nodoDeposito) {
        this.listaClienti = listaClienti;
        this.nodoDeposito = nodoDeposito;
    }

    public ArrayList<NodoCliente> getListaClienti() {
        return listaClienti;
    }
    
}
