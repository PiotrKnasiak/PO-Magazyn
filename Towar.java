package ProjektMagazyn;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Towar implements Comparable<Towar> {
    public static enum stanyTowaru {
        DO_ODBIORU,
        W_MAGAZYNIE,
        WYWIEZIONY,
        BLAD
    }

    public static Towar znajdzPoID(int ID) {
        Towar sprawdzany = null;

        for (int i = 0; i < Towar.listaTowarow.size(); i++) {
            if (Towar.listaTowarow.get(i).ID == ID) {
                return Towar.listaTowarow.get(i);
            }
        }

        if (sprawdzany == null) {
            System.out.println("\n*** Error!\n\tNiepowodzenie w Towar.znajdzPoID(" + ID + ")!\n");
        }

        return sprawdzany;
    }

    public Towar() {
        this.ID = -2;

        listaTowarow.add(this);
    }

    public Towar(String nazwa, String typ, String wlasciciel) {
        this.ID = -1;
        this.nazwa = nazwa;
        this.typ = typ;
        this.wlasciciel = wlasciciel;

        listaTowarow.add(this);
    }

    public Towar(int ID, String nazwa, String typ, int wagaKG, String wlasciciel, stanyTowaru stanTowaru) {
        this.ID = ID;
        this.nazwa = nazwa;
        this.typ = typ;
        this.wagaKG = wagaKG;
        this.wlasciciel = wlasciciel;
        this.stanTowaru = stanTowaru;

        listaTowarow.add(this);
    }

    public Towar(int ID, String nazwa, String typ, int wagaKG, String wlasciciel, stanyTowaru stanTowaru,
            boolean prank) { // bez dodawania
        this.ID = ID;
        this.nazwa = nazwa;
        this.typ = typ;
        this.wagaKG = wagaKG;
        this.wlasciciel = wlasciciel;
        this.stanTowaru = stanTowaru;

    }

    int ID = 0;
    String nazwa = "Karotn czekolady";
    String typ = "Produkt spożywczy";
    int wagaKG = 50;
    String wlasciciel = "Milka";
    stanyTowaru stanTowaru = stanyTowaru.W_MAGAZYNIE;

    static List<Towar> listaTowarow = new ArrayList<>();

    static Collator kolator = Collator.getInstance(new Locale("pl"));

    @Override
    public int compareTo(Towar t) {
        return kolator.compare(this.nazwa, t.nazwa);
    }

    static Comparator<Towar> porownajNaz = new Comparator<Towar>() {
        @Override
        public int compare(Towar t1, Towar t2) {
            return kolator.compare(t1.nazwa, t2.nazwa);
        }
    };
    static Comparator<Towar> porownajTyp = new Comparator<Towar>() {
        @Override
        public int compare(Towar t1, Towar t2) {
            return kolator.compare(t1.typ, t2.typ);
        }
    };
    static Comparator<Towar> porownajWlasc = new Comparator<Towar>() {
        @Override
        public int compare(Towar t1, Towar t2) {
            return kolator.compare(t1.wlasciciel, t2.wlasciciel);
        }
    };
    static Comparator<Towar> porownajWag = new Comparator<Towar>() {
        @Override
        public int compare(Towar t1, Towar t2) {
            int rozn = t1.wagaKG - t2.wagaKG;
            if (rozn < 0)
                return -1;
            if (rozn > 0)
                return 1;
            return porownajNaz.compare(t1, t2);
        }
    };
    static Comparator<Towar> porownajID = new Comparator<Towar>() {
        @Override
        public int compare(Towar t1, Towar t2) {
            int rozn = t1.ID - t2.ID;
            if (rozn < 0)
                return -1;
            if (rozn > 0)
                return 1;

            System.out.println("\n*** Error!\n\tDuplikat ID w towarach!\n");
            return porownajNaz.compare(t1, t2);
        }
    };
}