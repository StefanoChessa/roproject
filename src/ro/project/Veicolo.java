package ro.project;

public class Veicolo  {

    private int indice;
    private int capacita;

    public Veicolo(int indice,int capacitaVeicolo){
        this.indice=indice;
        this.setCapacita(capacitaVeicolo);
    }


    public int getIndice() {
        return indice;
    }


    public int getCapacita() {
        return capacita;
    }

    public void setCapacita(int capacita) {
        this.capacita = capacita;
    }
}
