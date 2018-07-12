//linehaul=delivery
package ro.project;

import java.util.ArrayList;
import java.util.Random;

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

        Veicolo[] veicoli= new Veicolo[file.getNumeroVeicoli()];//creo un vettore di veicoli di dimensione pari al numero Totale Veicoli

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

              if(a.getDelivery()<veicoli[i].getCapacita() && linehaul.contains(a)){
                  r.aggiungiAllaRotta(a);
                  veicoli[i].setCapacita(veicoli[i].getCapacita() - a.getDelivery());
                  linehaul.remove(a);
              }

                if(b.getDelivery()<veicoli[i].getCapacita() && linehaul.contains(b)){
                    r.aggiungiAllaRotta(b);
                    veicoli[i].setCapacita(veicoli[i].getCapacita() - b.getDelivery());
                    linehaul.remove(b);
                }


            }


            r.chiudiRotta();
            costoTotale+=r.getCosto();
            listaRotte.add(r);
            System.out.println("rotta: " + i + " costo: " + r.getCosto());


           // System.out.println(linehaul.size());


        }
        System.out.println("CostoTotale: " + costoTotale);

    }

    public void relocate(ArrayList<Rotta> r){



    }

    public void exchange(ArrayList<Rotta> r){
        Random rand = new Random();

        int index1 = rand.nextInt(r.size());
        int index2 =rand.nextInt(r.size());
        while(index2== index1){
            index2=rand.nextInt(r.size());
        }

        Rotta rotta1=r.get(index1);
        Rotta rotta2=r.get(index2);

        //


    }


    public ArrayList<Rotta> ottieniRotte(){
        return this.listaRotte;
    }

}
