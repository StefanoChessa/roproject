//lainhoul=delivery
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
                NodoCliente a=savingOrdinati.get(j).getNodoA();
                NodoCliente b= savingOrdinati.get(j).getNodoB();
              if(a.getDelivery()<veicoli[i].getCapacita() && a.getDelivery()!=0){
                  r.aggiungiAllaRotta(a);
                  veicoli[i].setCapacita(veicoli[i].getCapacita() - a.getDelivery());
                  linehaul.remove(a);
                  //rimuovere il nodo dai lainhoul
              }else{
                  r.chiudiRotta();
              }

                if(b.getDelivery()<veicoli[i].getCapacita() && b.getDelivery()!=0){
                    r.aggiungiAllaRotta(b);
                    veicoli[i].setCapacita(veicoli[i].getCapacita() - b.getDelivery());
                    linehaul.remove(b);
                }else{
                    r.chiudiRotta();
                }


            }



            System.out.println("ciao");

        }

    }














}
