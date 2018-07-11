package ro.project;

public class SavingNodi {

    private NodoCliente nodoA;
    private NodoCliente nodoB;
    private Double saving;

    public SavingNodi(NodoCliente a, NodoCliente b, Double s){

        this.setNodoA(a);
        this.setNodoB(b);
        this.setSaving(s);

    }


    public NodoCliente getNodoA() {
        return nodoA;
    }

    public void setNodoA(NodoCliente nodoA) {
        this.nodoA = nodoA;
    }

    public NodoCliente getNodoB() {
        return nodoB;
    }

    public void setNodoB(NodoCliente nodoB) {
        this.nodoB = nodoB;
    }

    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }
}
