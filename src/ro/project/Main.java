package ro.project;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.print("Ciao");
        FileUploader file = new FileUploader();

        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Inserisci il nome del file:");
        name = scanner.nextLine();

        System.out.println(name);
    }
}
