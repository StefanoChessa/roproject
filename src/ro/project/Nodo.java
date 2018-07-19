package ro.project;

import java.util.ArrayList;

public class Nodo implements  Cloneable{

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
    public Object clone() {
        try {
            Nodo n;
            n = (Nodo) super.clone();
            return n;
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

