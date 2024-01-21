package ProjektMagazyn;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

//import Testy.Ksiazka;

public class Pracownik extends Osoba implements Comparable<Pracownik> {

    Pracownik() {
        listaPracownikow.add(this);
        this.ID = -2;
    }

    Pracownik(int ID, String zmiana, String imie, String nazwisko, String pozycja, String wypłata) {
        this.ID = ID;
        this.zmiana = zmiana;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pozycja = pozycja;
        this.wypłata = wypłata;
        // dodanie instancji obiektu do statycznej listy
        listaPracownikow.add(this);
    }

    Pracownik(int ID, String zmiana, String imie, String nazwisko, String pozycja, String wypłata, boolean prank) { // bez
                                                                                                                    // dodania
        this.ID = ID;
        this.zmiana = zmiana;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pozycja = pozycja;
        this.wypłata = wypłata;
        // dodanie instancji obiektu do statycznej listy
    }

    int ID = 0;
    String zmiana = "Dzienna";
    String pozycja = "niewolnik (praktykant)";
    String wypłata = "0 zł";
    // klasowe (statyczne) pole
    // static String uczelnia = "UMG";

    // klasowa (statyczna) lista pracowników
    static List<Pracownik> listaPracownikow = new ArrayList<>();

    public static Pracownik znajdzPoID(int ID, boolean alert) {
        Pracownik sprawdzany = null;

        for (int i = 0; i < Pracownik.listaPracownikow.size(); i++) {
            if (Pracownik.listaPracownikow.get(i).ID == ID) {
                return Pracownik.listaPracownikow.get(i);
            }
        }

        if (sprawdzany == null) {
            if (alert) {
                System.out.println(
                        "\n*** Error!\n\tNiepowodzenie w Pracownik.znajdzPoID(" + ID
                                + ", true)!\n\t  Nie ma takiego pracownika");
            }
        }

        return sprawdzany;
    }

    public static void drukujPracownikow() {
        String studenci = "";
        for (Pracownik s : listaPracownikow) {
            studenci += s + "\n";
        }
        System.out.print(studenci);
    }

    public static void czyscListe() {
        listaPracownikow = new ArrayList<Pracownik>();
    }

    public static void czyscListeRem() {
        int len = listaPracownikow.size();

        for (int i = 0; i < len; i++) {
            Pracownik.listaPracownikow.remove(0);
        }
    }

    public static boolean zmianaImienia(String noweIm, int index) {
        if (index >= listaPracownikow.size()) {
            return false;
        }

        Pracownik stu = listaPracownikow.get(index);
        int osInd = 0;
        for (Osoba o : Osoba.listaOs) {
            if (compareToOs(stu, o))
                break;
            osInd++;
        }
        if (osInd >= Osoba.listaOs.size())
            return false;

        stu.imie = noweIm;
        listaPracownikow.set(index, stu);
        Osoba.listaOs.set(osInd, (Osoba) stu);
        return true;
    }

    public static boolean compareToOs(Pracownik s, Osoba o) {
        if (s.imie == o.imie && s.nazwisko == o.nazwisko)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "\n   ID:" + this.ID + "\n" + super.toString() + "\tzmiana: " + this.zmiana + "\n\tpozycja: "
                + this.pozycja + "\n\twypłata: " + this.wypłata.toString();
    }

    static Collator kolator = Collator.getInstance(new Locale("pl"));

    static Comparator<Pracownik> porownajImie = new Comparator<Pracownik>() {
        @Override
        public int compare(Pracownik p1, Pracownik p2) {
            int x = kolator.compare(p1.nazwisko, p2.nazwisko);
            int y = kolator.compare(p1.imie, p2.imie);
            if (x == 0)
                return y;
            return x;
        }
    };
    static Comparator<Pracownik> porownajID = new Comparator<Pracownik>() {
        @Override
        public int compare(Pracownik p1, Pracownik p2) {
            int rozn = p1.ID - p2.ID;
            if (rozn < 0)
                return -1;
            if (rozn > 0)
                return 1;
            return porownajImie.compare(p1, p2);
        }
    };

    @Override
    public int compareTo(Pracownik p) {
        int x = kolator.compare(this.nazwisko, p.nazwisko);
        int y = kolator.compare(this.imie, p.imie);
        if (x == 0)
            return y;
        return x;
    }

}
