package ro.project;

import java.io.*;

public class FileUploader {

    private int numeroClienti;
    private int numeroVeicoli;
    private int capacitaVeicolo;
    private NodoDeposito nodoDeposito;
    private BufferedReader file;

    public FileUploader() {
        this.numeroClienti = 0;
        this.numeroVeicoli = 0;
        this.capacitaVeicolo = 0;
        this.nodoDeposito = null;
        this.file = null;
    }

    public void caricaIstanza(String file_path) {
        try {

            file = new BufferedReader(
                    new FileReader("project_files/Instances/" + file_path + ".txt")
            );


        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
