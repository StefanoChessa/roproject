//lainhoul=delivery
package ro.project;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        File folder = new File("project_files/Instances/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listaFile = new ArrayList<>();

        for (int numFiles = 0; numFiles < listOfFiles.length; numFiles++) {
            if (listOfFiles[numFiles].isFile()) {
                listaFile.add(listOfFiles[numFiles].getName().substring(0,2));
            }
        }




        String filename;

        Collections.sort(listaFile, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });



        for(int i=0; i<1;i++) {//for dei files
            if(!listaFile.get(i).contains("C2") && !listaFile.get(i).contains("D2") && !listaFile.get(i).contains("D3")
                    && !listaFile.get(i).contains("G2") && !listaFile.get(i).contains("I2") && !listaFile.get(i).contains("K2") &&
                    !listaFile.get(i).contains("M2") && !listaFile.get(i).contains("N2")) {
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


                MatriceDistanze matr = new MatriceDistanze(file.getTuttiNodi());
                matr.creaMatrice();
                int numero = 0;
                ArrayList<ListaRotte> percorsi = new ArrayList();
                do {
                    numero++;
                    ListaRotte lista = new ListaRotte();
                    lista.inizializzaLineHaul(file);
                    lista.inizializzaBackHaul(file);
                    //lista.bestExchangeLinehaul();
                    lista.bestRelocateLinehaul();
                    //lista.bestExchangeBackhaul();
                    lista.bestRelocateBackhaul();
                    lista.merge();
                    percorsi.add(lista);
                } while (numero < 10);

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

                System.out.print("Nodi totali dopo " + nodiTotali);
                System.out.println("Costo totale = " + listaMigliore.getCostoTotale("F"));

                Double errore = (listaMigliore.getCostoTotale("F") - sol.returnZP()) / Math.abs(sol.returnZP());

                PrintResults risultati = new PrintResults(errore);
                risultati.printOnFile(filename);
            }


        }//fine for dei file
    }
}
