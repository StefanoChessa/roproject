package ro.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by marco on 25/07/2018.
 */
public class SolutionUploader {
    private BufferedReader file;
    private String currentLine;
    private double costoTotaleSoluzione;
    private String separatore = " ";

    public void caricaIstanza(String file_path) {

        try {


            file = new BufferedReader(
                    new FileReader("project_files/RPA_Solutions/" + "Detailed_Solution_" + file_path + ".txt")
            );
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();

            currentLine = file.readLine();  //quarta riga
            String[] dati = currentLine.split(separatore);
            //System.out.println("dati = " + dati[3]);
            costoTotaleSoluzione = Double.parseDouble(dati[3]);


            file.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    double returnZP(){
        return this.costoTotaleSoluzione;
    }
}
