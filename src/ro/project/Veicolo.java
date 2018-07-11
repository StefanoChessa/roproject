package ro.project;

public class Veicolo extends NodoDeposito {

    int indice;

    public Veicolo(int x,int y,int capacitaVeicolo, int id, int indice){
        super(x,y,capacitaVeicolo,id);

    }

    int getIndice(){
        return indice;
    }



}
