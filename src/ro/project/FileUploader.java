package ro.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileUploader {

    private int numeroClienti;
    private int numeroVeicoli;
    private int capacitaVeicolo;
    private NodoDeposito nodoDeposito;
    private BufferedReader file;
    private String currentLine;
    private String separatore = "   ";
    private ArrayList<NodoCliente> clienti;

    public FileUploader() {
        this.numeroClienti = 0;
        this.numeroVeicoli = 0;
        this.capacitaVeicolo = 0;
        this.nodoDeposito = null;
        this.file = null;
        clienti = new ArrayList<>();
    }

    public void caricaIstanza(String file_path) {

        try {

            int x, y, delivery, pickup = 0;
            int i = 1;

            file = new BufferedReader(
                    new FileReader("project_files/Instances/" + file_path + ".txt")
            );

            numeroClienti = Integer.parseInt(file.readLine());
            file.readLine(); //scarto la seconda riga, sempre 1
            numeroVeicoli = Integer.parseInt(file.readLine());

            currentLine = file.readLine();  //quarta riga

            String[] dati = currentLine.split(separatore);

            x = Integer.parseInt(dati[0]);
            y = Integer.parseInt(dati[1]);
            delivery = Integer.parseInt(dati[2]);

            nodoDeposito = new NodoDeposito(x, y, delivery,i);

            while ((currentLine = file.readLine()) != null) {

                dati = currentLine.split(separatore);

                x = Integer.parseInt(dati[0]);
                y = Integer.parseInt(dati[1]);
                delivery = Integer.parseInt(dati[2]);
                pickup = Integer.parseInt(dati[3]);

                System.out.println(x + " " + y + " " + delivery + " " + pickup + " id: " + i);

                clienti.add(new NodoCliente(x, y, delivery, pickup,i));
                i++;
            }

            file.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public ArrayList<NodoCliente> getClienti(){

        return this.clienti;
    }

    public NodoDeposito getNodoDeposito(){

        return this.nodoDeposito;
    }

    public ArrayList<Nodo> getTuttiNodi(){

        ArrayList<Nodo> nodi = new ArrayList<>();

        nodi.add(this.nodoDeposito);
        nodi.addAll(this.clienti);

        return nodi;

    }

    public ArrayList<NodoCliente> createLineHaulList(){
        ArrayList<NodoCliente> lineHaulList = null;
        for(NodoCliente cliente : this.getClienti()){
            if(cliente.getDelivery() == 0){

            }
            else {
                lineHaulList.add(cliente);
            }
        }
        return lineHaulList;
    }

    public ArrayList<NodoCliente> createBackHaulList(){
        ArrayList<NodoCliente> backHaulList = null;
        for(NodoCliente cliente : this.getClienti()){
            if(cliente.getPickup() == 0){

            }
            else {
                backHaulList.add(cliente);
            }
        }
        return  backHaulList;
    }

}
