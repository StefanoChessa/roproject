package ro.project;

import java.util.ArrayList;

public class MatriceSavings {

    private ArrayList<NodoCliente> nodi;
    private NodoDeposito nodoDeposito;
    private MatriceDistanze matriceDistanze;
    private Double[][] matriceSaving = null ;

    public MatriceSavings(ArrayList<NodoCliente> nodiCliente, NodoDeposito nodoDeposito, MatriceDistanze matriceDistanze){
        this.nodi = nodiCliente;
        this.nodoDeposito = nodoDeposito;
        this.matriceDistanze = matriceDistanze;
        this.matriceSaving = new Double[nodi.size()-1][nodi.size()-1];
        //calcolaMatriceSaving();
    }

//    public void calcolaMatriceSaving (){
//        for(int i = 0; i < nodi.size(); i++){
//            for (int j = 0; i < nodi.size(); j++){
//                NodoCliente primo = nodi.get(i);
//                NodoCliente secondo = nodi.get(j);
//                //matriceSaving[i][j] = matriceDistanze.getDistanza(primo.getId(),nodoDeposito.getId()) + matriceDistanze.getDistanza(secondo.getId(),nodoDeposito.getId()) - matriceDistanze.getDistanza(primo.getId(),secondo.getId()) ;
//            }
//        }
//        //System.out.println(matriceSaving[1][2]);
//    }



}
