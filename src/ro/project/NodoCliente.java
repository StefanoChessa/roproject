package ro.project;

public class NodoCliente extends Nodo {

    private int pickup, delivery;

    NodoCliente(int x,int y, int delivery, int pickup){
        super(x,y);

        this.pickup = pickup;
        this.delivery = delivery;

    }

    public int getPickup() {
        return pickup;
    }

    public int getDelivery() {
        return delivery;
    }


}
