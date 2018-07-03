package ro.project;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUploader {

    private int numeroClienti;
    private int numeroVeicoli;
    private int capacitaVeicolo;
    private NodoDeposito nodoDeposito;
    private BufferedReader file;
    private String currentLine;
    private String separatore = "   ";

    public FileUploader() {
        this.numeroClienti = 0;
        this.numeroVeicoli = 0;
        this.capacitaVeicolo = 0;
        this.nodoDeposito = null;
        this.file = null;
    }

    public void caricaIstanza(String file_path) {

        try {

            int x, y, delivery, pickup = 0;

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

            nodoDeposito = new NodoDeposito(x, y, delivery);

            while ((currentLine = file.readLine()) != null) {

                dati = currentLine.split(separatore);

                x = Integer.parseInt(dati[0]);
                y = Integer.parseInt(dati[1]);
                delivery = Integer.parseInt(dati[2]);
                pickup = Integer.parseInt(dati[3]);

                System.out.println(x + " " + y + " " + delivery + " " + pickup);

            }

            file.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
