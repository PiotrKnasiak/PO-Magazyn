package ProjektMagazyn;

import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Osoba {
    public Osoba(String imie, String nazwisko, String dataUr) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUr = dataUr;
        listaOs.add(this);
    }
    public Osoba() {
        listaOs.add(this);
    }

    String nazwisko = "Kowalski";
    String imie = "Jan";
    String dataUr="2002-07-16";

    double wiek() {
        double suma = 0;
        String[] czesci = this.dataUr.split("-");
        int parsedR = Integer.parseInt(czesci[0]);
        int parsedM = Integer.parseInt(czesci[1]);
        int parsedD = Integer.parseInt(czesci[2]);

        suma = (bRok - parsedR) + (bMiesiac - parsedM)/12.0 + (bDzien - parsedD)/365.0;

        return (double)(int)(suma*100)/100;
    }
    double wiek(String dataPor) {
        double suma = 0;
        String[] czesci = this.dataUr.split("-");
        String[] czesci2 = dataPor.split("-");
        int parsedR = Integer.parseInt(czesci[0]);
        int parsedM = Integer.parseInt(czesci[1]);
        int parsedD = Integer.parseInt(czesci[2]);
        int parsedR2 = Integer.parseInt(czesci2[0]);
        int parsedM2 = Integer.parseInt(czesci2[1]);
        int parsedD2 = Integer.parseInt(czesci2[2]);

        suma = (parsedR2 - parsedR) + (parsedM2 - parsedM)/12.0 + (parsedD2 - parsedD)/365.0;

        return (double)(int)(suma*100)/100;
    }
    static LocalDate dzisiaj = LocalDate.now();
    static int bRok = dzisiaj.getYear();
    static int bMiesiac = dzisiaj.getMonthValue();
    static int bDzien = dzisiaj.getDayOfMonth();
    static Osoba starszy(Osoba o1, Osoba o2) {
        if(o1.wiek() > o2.wiek())
            return o1;
        return o2;
    }
    static ArrayList<Osoba> listaOs = new ArrayList<Osoba>();

    @Override
    public String toString() {
        return ("\tImię: " + this.imie + "\n\tNazwisko: " + this.nazwisko + "\n\tData urodzenia: " +this.dataUr +
            String.format("\n\tWiek: %5.2f", this.wiek()) + "\n");

    }
}
