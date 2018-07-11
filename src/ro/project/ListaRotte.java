package ro.project;

import java.util.ArrayList;

/**
 * Created by marco on 11/07/2018.
 */
public class ListaRotte {

    private ArrayList<Rotta> listaRotte;
    private Double costoTotale = 0.0;



    public ListaRotte(){
      this.listaRotte= new ArrayList<>();
    }

    public void inizializza(FileUploader file, MatriceSavings matrice){

        ArrayList<NodoCliente> linehaul=file.createLineHaulList();
        ArrayList<NodoCliente> backhaul=file.createBackHaulList();
        MatriceSavings matriceSavings=matrice;
        int numeroVeicoli=file.getNumeroVeicoli();
        int capacita=file.getCapacitaVeicolo();
        Veicolo[] veicoli= new Veicolo[file.getNumeroVeicoli()];
        ArrayList<SavingNodi> savingOrdinati=new ArrayList<>();
        savingOrdinati=matriceSavings.ordinaSaving();

        for(int n=0; n<numeroVeicoli;n++){
            veicoli[n]=new Veicolo(n,capacita);
        }

        for(int i=0; i<numeroVeicoli;i++){

            Rotta r= new Rotta(i);
            r.aggiungiAllaRotta(file.getTuttiNodi().get(0));

            for(int j=0; j<savingOrdinati.size();j++){
//                savingOrdinati.get(j).getNodoA().getDelivery()<0;
            }


            System.out.println("ciao");

        }

    }














}
