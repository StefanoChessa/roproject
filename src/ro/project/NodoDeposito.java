package ro.project;

public class NodoDeposito extends Nodo {

    private int capacitaVeicolo = 0;

    public NodoDeposito(int x,int y,int capacitaVeicolo, int id){
        super(x,y,id);
        this.capacitaVeicolo = capacitaVeicolo;
    }

    public int getCapacitaVeicolo() {
        return capacitaVeicolo;
    }

    private void setCapacitaVeicolo(int capacitaVeicolo) {
        this.capacitaVeicolo = capacitaVeicolo;
    }

}
