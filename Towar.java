package ProjektMagazyn;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public class Towar implements Comparable<Towar>, Serializable {
    public static enum stanyTowaru {
        DO_ODBIORU,
        W_MAGAZYNIE,
        WYWIEZIONY,
        BLAD
    }

    public static Towar znajdzPoID(int ID, boolean alert) {
        Towar sprawdzany = null;

        for (int i = 0; i < Towar.listaTowarow.size(); i++) {
            if (Towar.listaTowarow.get(i).ID == ID) {
                return Towar.listaTowarow.get(i);
            }
        }

        if (sprawdzany == null) {
            if (alert) {
                System.out.println(
                        "\n*** Error!\n\tNiepowodzenie w Towar.znajdzPoID(" + ID
                                + ", true)!\n\t  Nie ma takiego towaru");
            }
        }

        return sprawdzany;
    }

    public static int miejsceWTab(int ID) {
        int miejsce = 0;

        for (; miejsce < Towar.listaTowarow.size(); miejsce++) {
            if (Towar.listaTowarow.get(miejsce).ID == ID) {
                return miejsce;
            }
        }

        return miejsce;
    }

    /*
     * public static boolean sprawdzMagazyn(int ID) {
     * for (Towar t : listaTowarow) {
     * if (t.ID == ID) {
     * Towar.stanyTowaru stanTow = Transport.zwrocStanTowaru(t.ID);
     * if ((stanTow == stanyTowaru.BLAD && t.stanTowaru == stanyTowaru.W_MAGAZYNIE)
     * || stanTow == stanyTowaru.W_MAGAZYNIE) {
     * return true;
     * }
     * break;
     * }
     * }
     * return false;
     * }
     */
    public static Towar[] zwrocMagazyn() {
        Towar tow[] = new Towar[0];
        ArrayList<Towar> mag = new ArrayList<>();

        for (Towar t : listaTowarow) {
            Towar.stanyTowaru stanTow = Transport.zwrocStanTowaru(t.ID);
            if ((stanTow == stanyTowaru.BLAD && t.stanTowaru == stanyTowaru.W_MAGAZYNIE)
                    || stanTow == stanyTowaru.W_MAGAZYNIE) {
                mag.add(t);
            }
        }

        ileMagazyn = mag.size();
        return mag.toArray(tow);
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

        if (znajdzPoID(ID, false) == null)
            listaTowarow.add(this);
        else
            System.out.println("\n*** Error!\n\tTowar o takim ID już istnieje! Nie został dodany to listy!\n");
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
    static List<Towar> listaTowarowWMag = new ArrayList<>();
    public static int ileMagazyn = 0;

    static Collator kolator = Collator.getInstance(new Locale("pl"));

    @Override
    public int compareTo(Towar t) {
        return kolator.compare(this.nazwa, t.nazwa);
    }

    // #region Konparatory
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
    // #endregion

    @Override
    public String toString() {
        return "\n   ID:" + this.ID + "\n\tNazwa: " + this.nazwa + "\n\tWłaściciel: "
                + this.wlasciciel + "\n\tWaga: " + this.wagaKG;
    }
}