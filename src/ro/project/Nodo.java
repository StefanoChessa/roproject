package ro.project;

public class Nodo {

    private int x = 0;
    private int y = 0;



    private int id;

    public Nodo(int x, int y, int id) {
        this.setX(x);
        this.setY(y);
        this.id=id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

}