package ro.project;

import java.util.ArrayList;

public class MatriceDistanze {

    private ArrayList<NodoCliente> listaClienti;
    private ArrayList<Nodo> nodi;
    private NodoDeposito nodoDeposito;
    private Double[][] matriceDistanze = null;

    public MatriceDistanze(ArrayList<NodoCliente> listaClienti, NodoDeposito nodoDeposito) {
        this.listaClienti = listaClienti;
        this.nodoDeposito = nodoDeposito;
    }

    public MatriceDistanze(ArrayList<Nodo> nodi) {
        this.nodi = nodi;
    }

    public ArrayList<NodoCliente> getListaClienti() {
        return listaClienti;
    }

    public ArrayList<Nodo> getListaNodi() {
        return nodi;
    }

    public void creaMatrice() {
        int i = 0;
        int z = 0;
        ArrayList<Nodo> listaNodi = this.getListaNodi();
        this.matriceDistanze = new Double[listaNodi.size()+1][listaNodi.size()+1];
        for (Nodo n : listaNodi) {
            for (Nodo m : listaNodi) {
                Double x = Math.pow(n.getX() - m.getX(), 2);
                Double y = Math.pow(n.getY() - m.getY(), 2);
                Double distanza = Math.sqrt(x + y);
                matriceDistanze[i][z] = distanza;
                z++;
            }
            z=0;
            i++;
        }

    }
}
