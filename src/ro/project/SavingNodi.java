package ro.project;

public class SavingNodi {

    private Nodo nodoA;
    private Nodo nodoB;
    private Double saving;

    public SavingNodi(Nodo a, Nodo b, Double s){

        this.setNodoA(a);
        this.setNodoB(b);
        this.setSaving(s);

    }


    public Nodo getNodoA() {
        return nodoA;
    }

    public void setNodoA(Nodo nodoA) {
        this.nodoA = nodoA;
    }

    public Nodo getNodoB() {
        return nodoB;
    }

    public void setNodoB(Nodo nodoB) {
        this.nodoB = nodoB;
    }

    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }
}
