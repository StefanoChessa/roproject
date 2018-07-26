package ro.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PrintResults {

    double errore;

    public PrintResults(double errore){

        this.errore = errore;

    }

    void printOnFile(String filename){

        try {
            Files.write(Paths.get("myfile.txt"), "the text".getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

        try(FileWriter fw = new FileWriter("risultati.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("File analizzato: " + filename);
            //more code
            out.println("Errore relativo: " + errore);
            //more code
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }


    }
}