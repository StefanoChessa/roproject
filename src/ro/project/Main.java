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

        MatriceSavings matriceSavings = new MatriceSavings(file.getClienti(),file.getNodoDeposito());
        matriceSavings.calcolaMatriceSaving();
        System.out.println(matriceSavings.getSavingAt(1,5));

    }
}
