package ro.project;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String filename;
        FileUploader file = new FileUploader();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci il nome del file da importare (non includere il .txt):");
        filename = scanner.nextLine();

        file.caricaIstanza(filename);

        MatriceDistanze matr = new MatriceDistanze(file.getTuttiNodi());
        matr.creaMatrice();

        System.out.println("distanza: " + matr.getDistanza(0,1));

    }
}
