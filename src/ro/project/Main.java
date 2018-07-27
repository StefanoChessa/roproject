package ro.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        double mediaAritmetica = 0;
        double operatoreMedia = 0;
        double deviazioneStandard = 0;
        ArrayList<Double> errorelista = new ArrayList<>();
        double powDev = 0;
        Cronometro tempo = new Cronometro();

        File folder = new File("project_files/Instances/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listaFile = new ArrayList<>();

        for (int numFiles = 0; numFiles < listOfFiles.length; numFiles++) {
            if (listOfFiles[numFiles].isFile()) {
                listaFile.add(listOfFiles[numFiles].getName().substring(0, 2));
            }
        }

        String filename;

        Collections.sort(listaFile, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });


        for (int i = 0; i < listaFile.size(); i++) {//for dei files

            filename = listaFile.get(i);

            System.out.println(
                    "\n############### Sto calcolando il file " +
                            filename + "############ \n");

            FileUploader file = new FileUploader();
            SolutionUploader sol = new SolutionUploader();
            //Scanner scanner = new Scanner(System.in);

            //System.out.println("Inserisci il nome del file da importare (non includere il .txt):");
            //filename = scanner.nextLine();

            file.caricaIstanza(filename);
            sol.caricaIstanza(filename);

            tempo.reset();
            tempo.start();

            MatriceDistanze matr = new MatriceDistanze(file.getTuttiNodi());
            matr.creaMatrice();
            int numero = 0;
            ArrayList<ListaRotte> percorsi = new ArrayList();
            do {
                numero++;
                ListaRotte lista = new ListaRotte();
                while (!lista.inizializzaLineHaul(file)) ;
                while (!lista.inizializzaBackHaul(file)) ;
                lista.bestExchangeLinehaul();
                lista.bestRelocateLinehaul();
                lista.bestExchangeBackhaul();
                lista.bestRelocateBackhaul();
                lista.merge();
                percorsi.add(lista);
                //System.out.println("Tentativo numero " + numero);
            } while (numero < 100);

            double min = 99999999999999999999999999.0;
            ListaRotte listaMigliore = new ListaRotte();
            for (ListaRotte a : percorsi) {
                if (a.getCostoTotale("F") < min)
                    min = a.getCostoTotale("F");
                listaMigliore = a;
            }

            ArrayList<Rotta> rottefinali = listaMigliore.getRotteFinali();
            int nodiTotali = 0;
            for (Rotta a : rottefinali) {
                System.out.println("");
                System.out.print("Rotta " + a.getIndiceVeicolo() + ": ");
                a.stampaRotta(a.getNodi());
                System.out.print(" Capacita = " + a.getCapacitaVeicolo());

                System.out.println();
                System.out.println("Costo prima = " + a.getCosto());
                a.aggiornaCosto();
                System.out.println("Costo dopo = " + a.getCosto());

                nodiTotali += a.getNodi().size();
            }

            tempo.stop();

            System.out.print("Nodi totali dopo " + nodiTotali);
            System.out.println("Costo totale = " + listaMigliore.getCostoTotale("F"));

            Double errore = (listaMigliore.getCostoTotale("F") - sol.returnZP()) / Math.abs(sol.returnZP());

            PrintResults risultati = new PrintResults(errore);
            risultati.addTime(tempo.getSeconds());
            risultati.printOnFile(filename);

            operatoreMedia += errore;

            errorelista.add(errore);

        }//fine for dei file

        mediaAritmetica = operatoreMedia / listaFile.size();

        //calcolo deviazione standard

        for(int i = 0; i < errorelista.size(); i++){

            powDev += Math.pow((errorelista.get(i) - mediaAritmetica),2);

        }

        deviazioneStandard = Math.sqrt(powDev / listaFile.size());

        System.out.println("Media aritmetica: " + mediaAritmetica );
        System.out.println("Deviazione standard: " + deviazioneStandard );

    }
}