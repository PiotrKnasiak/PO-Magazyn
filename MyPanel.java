package ProjektMagazyn;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;

@SuppressWarnings("ALL")
public class MyPanel extends JPanel {

    public static enum panele {
        PRACOWNICY,
        TOWARY,
        TRANSPORT,
        DODAJ_PRAC,
        USUN_PRAC,
        DODAJ_TOWAR,
        WYSLIJ_TOWAR,
        LOGOWANIE
    }

    public static enum tabele {
        PRACOWNICY,
        TOWARY,
        TRANSPORT
    }

    public static enum pliki {
        LISTA_PRACOWNIKOW,
        LISTA_TOWAROW,
        LISTA_TRANSPORTOW,
        DANE_LOGOWANIA
    }

    // okienka
    public static JFrame fr_main;
    public static JFrame fr_dodajPrac;
    public static JFrame fr_usunPracownika;
    public static JFrame fr_dodajWysylke;
    public static JFrame fr_dodajPrzychodzoce;

    // elementy fr_main - ogólne
    public static JButton btn_prac;
    public static JButton btn_towary;
    public static JButton btn_trans;
    public static JButton btn_log;
    public static JLabel lbl_nazwaTabeli;
    public static JScrollPane scr_tabeli;
    public static JTable tbl_tabelaMain;
    public static JButton btn_dodaj;
    public static JButton btn_zapisz;
    public static JButton btn_usun;

    public static final int SUKCES = 0;
    public static final int PORAZKA = -2;
    public static final int BLAD = -1;

    public static Pracownik[] pracownicy;
    public static Towar[] towary;
    public static Transport[] transporty;

    // elementy fr_main - pracownicy

    // elementy fr_main - towary
    public static JButton btn_sortujID;
    public static JButton btn_sortujWaga;
    public static JButton btn_sortujWlasc;

    // elementy fr_main - transpotr

    // elementy fr_main - logowanie

    static int licznik = 0;
    private static String sortTow = "ID"; // ID, Wlasc, Waga
    private static boolean zalogowany = false;

    public static int zaloguj() {
        zalogowany = true;
        return 0;
    }

    public static void wyloguj() {
        zalogowany = false;
    }

    public static String[][] objektyNaListyStr(Object[] objekt, tabele typ) {
        // {"ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata"};
        // {"ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel"};
        // {"ID towaru", "Nazwa towaru", "Data", "Operacja"};

        String[][] stringi = { { "error" } };

        switch (typ) {
            case PRACOWNICY:
                stringi = new String[objekt.length][6];
                pracownicy = new Pracownik[objekt.length];

                for (int i = 0; i < objekt.length; i++) {
                    pracownicy[i] = (Pracownik) objekt[i];
                }

                for (int i = 0; i < pracownicy.length; i++) {
                    stringi[i][0] = String.valueOf(pracownicy[i].ID);
                    stringi[i][1] = pracownicy[i].nazwisko;
                    stringi[i][2] = pracownicy[i].imie;
                    stringi[i][3] = pracownicy[i].zmiana;
                    stringi[i][4] = pracownicy[i].pozycja;
                    stringi[i][5] = pracownicy[i].wypłata;
                }
                break;

            case TOWARY:
                stringi = new String[objekt.length][5];
                towary = new Towar[objekt.length];

                for (int i = 0; i < objekt.length; i++) {
                    towary[i] = (Towar) objekt[i];
                }

                for (int i = 0; i < towary.length; i++) {
                    stringi[i][0] = String.valueOf(towary[i].ID);
                    stringi[i][1] = towary[i].nazwa;
                    stringi[i][2] = towary[i].typ;
                    stringi[i][3] = String.valueOf(towary[i].wagaKG);
                    stringi[i][4] = towary[i].wlasciciel;
                }
                break;

            case TRANSPORT:
                stringi = new String[objekt.length][4];
                transporty = new Transport[objekt.length];

                for (int i = 0; i < objekt.length; i++) {
                    transporty[i] = (Transport) objekt[i];
                }

                for (int i = 0; i < transporty.length; i++) {
                    stringi[i][0] = String.valueOf(transporty[i].ID);
                    stringi[i][1] = transporty[i].nazwaTowaru;
                    stringi[i][2] = Transport.dataTransNaString(transporty[i].dataTransportu);
                    stringi[i][3] = Transport.stanyNaString(transporty[i].import_eksport);
                }
                break;

            default:
                break;

        }

        return stringi;
    }

    public static boolean stworzTabele(tabele typ) {

        if (typ == tabele.PRACOWNICY) {
            String kolumny[] = { "ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata" };

            Pracownik.listaPracownikow.clear();
            /* zastąpić łądownaiem z pliku */
            new Pracownik();
            new Pracownik(4, "Dzienna", "Arkadiusz", "Poranny", "Kierowca ciężarówki (pet)", "3700");
            new Pracownik(5, "Nocna", "Marcin", "Walaszek", "Operator wózka widłowego (baza)", "5700");
            new Pracownik();
            // TU !!! wczytaj

            Pracownik.listaPracownikow.sort(Pracownik.porownajID);

            String dane[][] = objektyNaListyStr(Pracownik.listaPracownikow.toArray(), typ);

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);

        } else if (typ == tabele.TOWARY) {
            String kolumny[] = { "ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel" };

            Towar.listaTowarow.clear();
            /* zastąpić łądownaiem z pliku */
            new Towar();
            new Towar(4, "Karton-gips", "Materiały budowlane", 800, "SP-bud z.o.o.", Towar.stanyTowaru.DO_ODBIORU);
            new Towar();
            new Towar();
            // TU !!! wczytaj

            switch (sortTow) {
                case "Wlasc":
                    Towar.listaTowarow.sort(Towar.porownajWlasc);
                    break;

                case "Waga":
                    Towar.listaTowarow.sort(Towar.porownajWag);
                    break;

                default:
                    Towar.listaTowarow.sort(Towar.porownajID);
                    break;

            }
            Towar.listaTowarow.sort(Towar.porownajID);

            String dane[][] = objektyNaListyStr(Towar.listaTowarow.toArray(), typ);

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);

        } else if (typ == tabele.TRANSPORT) {
            String kolumny[] = { "ID towaru", "Nazwa towaru", "Data", "Operacja" };

            Transport.listaTransportow.clear();
            /* zastąpić łądownaiem z pliku */
            new Transport();
            new Transport();
            new Transport();
            new Transport();
            // TU !!! wczytaj

            Transport.listaTransportow.sort(Transport.porownajID);

            String dane[][] = objektyNaListyStr(Transport.listaTransportow.toArray(), typ);

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);

        } else {
            System.out.println("Nieprawidłowy typ tabeli w 'stworzTabele()'");

            String dane[][] = { { "Nieprawidłowy typ tabeli w 'stworzTabele()'" } };
            String kolumny[] = { "Niepowodzenie" };

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);
            return false;
        }

        return true;
    }

    public static Object WczytajPlik(String sciezka, pliki typ) {
        return new Object();
    }

    public static boolean ZapiszPlik(Object doZapisu, pliki typ) {
        return true;
    }

    public MyPanel(panele panel) {

        System.out.println("\nTworzenie nowego okienka");
        boolean sukcesTabeli = true;

        btn_prac = new JButton("Pracownicy");
        btn_towary = new JButton("Lista Towarów");
        btn_trans = new JButton("Transfery");
        btn_log = new JButton("Logowanie");

        System.out.println("Tworzenie głównego listenera");
        // ogólny actionLietener dla fr_main
        ActionListener listenMain = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == btn_prac) {
                    System.out.println("Działa...");
                    fr_main.getContentPane().removeAll();
                    fr_main.getContentPane().add(new MyPanel(panele.PRACOWNICY));
                    fr_main.revalidate();
                }
                if (event.getSource() == btn_towary) {
                    System.out.println("Działa...");
                    fr_main.getContentPane().removeAll();
                    fr_main.getContentPane().add(new MyPanel(panele.TOWARY));
                    fr_main.revalidate();
                }
                if (event.getSource() == btn_trans) {
                    System.out.println("Działa...");
                    fr_main.getContentPane().removeAll();
                    fr_main.getContentPane().add(new MyPanel(panele.PRACOWNICY));
                    fr_main.revalidate();
                }
                if (event.getSource() == btn_log) {
                    System.out.println("Działa...");
                    fr_main.getContentPane().removeAll();
                    fr_main.getContentPane().add(new MyPanel(panele.TOWARY));
                    fr_main.revalidate();
                }
            }
        };

        switch (panel) {
            case PRACOWNICY:
                System.out.println("Tworzenie okeinka PRACOWNICY");

                // przypisywanie komponentów
                tbl_tabelaMain = new JTable();
                btn_usun = new JButton("Usuń pracownika");
                btn_dodaj = new JButton("Dodaj pracownika");
                btn_zapisz = new JButton("Zapisz zmiany");
                lbl_nazwaTabeli = new JLabel("Lista pracowników");

                // wymiary okna i layout
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                // dodanie komponentow
                add(btn_prac);
                add(btn_towary);
                add(btn_trans);
                add(btn_log);
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_dodaj);
                add(btn_zapisz);
                add(btn_usun);

                // Aboslutna pozycja i rozmiary
                btn_prac.setBounds(20, 15, 125, 30);
                btn_towary.setBounds(160, 15, 125, 30);
                btn_trans.setBounds(300, 15, 125, 30);
                btn_log.setBounds(440, 15, 125, 30);
                lbl_nazwaTabeli.setBounds(20, 65, 135, 30);
                btn_dodaj.setBounds(55, 520, 190, 30);
                btn_usun.setBounds(530, 520, 190, 30);
                btn_zapisz.setBounds(305, 520, 190, 30);

                // ładowanie i wstępne przypisane danych (test/temp)
                sukcesTabeli = stworzTabele(tabele.PRACOWNICY);
                if (!sukcesTabeli)
                    System.out.println("Niepowodzenie w 'stworzTabele(tabele.PRACOWNICY)'");

                scr_tabeli = new JScrollPane(tbl_tabelaMain);
                tbl_tabelaMain.setFillsViewportHeight(true);

                add(scr_tabeli);
                scr_tabeli.setBounds(20, 100, 760, 390);

                // Action Listener
                ActionListener listenMainPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_log) {
                        }
                    }
                };

                break;

            case TOWARY:
                System.out.println("Tworzenie okeinka TOWARY");

                // przypisywanie komponentów
                tbl_tabelaMain = new JTable();
                lbl_nazwaTabeli = new JLabel("Lista towarów");
                btn_dodaj = new JButton("Dodaj towar bezpośrednio");
                btn_usun = new JButton("Usuń towar bezpośrednio");
                btn_zapisz = new JButton("Zapisz zmiany");

                // wymiary okna i layout
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                // dodanie komponentow
                add(btn_prac);
                add(btn_towary);
                add(btn_trans);
                add(btn_log);
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_dodaj);
                add(btn_zapisz);
                add(btn_usun);

                // Aboslutna pozycja i rozmiary
                btn_prac.setBounds(20, 15, 125, 30);
                btn_towary.setBounds(160, 15, 125, 30);
                btn_trans.setBounds(300, 15, 125, 30);
                btn_log.setBounds(440, 15, 125, 30);
                lbl_nazwaTabeli.setBounds(20, 65, 135, 30);
                btn_dodaj.setBounds(55, 520, 190, 30);
                btn_usun.setBounds(530, 520, 190, 30);
                btn_zapisz.setBounds(305, 520, 190, 30);

                // ładowanie i wstępne przypisane danych (test/temp)
                sukcesTabeli = stworzTabele(tabele.TOWARY);
                if (!sukcesTabeli)
                    System.out.println("Niepowodzenie w 'stworzTabele(tabele.TOWARY)'");

                scr_tabeli = new JScrollPane(tbl_tabelaMain);
                tbl_tabelaMain.setFillsViewportHeight(true);

                add(scr_tabeli);
                scr_tabeli.setBounds(20, 100, 760, 390);

                // Action Listener
                ActionListener listenMainTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_log) {
                        }
                    }
                };

                break;

            case TRANSPORT:
                System.out.println("Tworzenie okeinka TRANSPORT");
                setPreferredSize(new Dimension(800, 600));

                ActionListener listenMainTrans = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            case LOGOWANIE:
                System.out.println("Tworzenie okeinka LOGOWANIE");
                setPreferredSize(new Dimension(800, 600));

                ActionListener listenMainLog = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            case DODAJ_PRAC:
                System.out.println("Tworzenie okeinka DODAJ_PRAC");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubDodajPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            case USUN_PRAC:
                System.out.println("Tworzenie okeinka USUN_PRAC");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubUsunPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            case DODAJ_TOWAR:
                System.out.println("Tworzenie okeinka DODAJ_TOWAR");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubDodajTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            case WYSLIJ_TOWAR:
                System.out.println("Tworzenie okeinka WYSLIJ_TOWAR");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubWyslijTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_prac) {
                        }
                    }
                };

                break;

            default:
                System.out.println("Nie ma takiego okienka");
                break;
        }

        System.out.println("Kaniec");
        btn_prac.addActionListener(listenMain);
        btn_towary.addActionListener(listenMain);
    }

    public static void main(String[] args) {
        fr_main = new JFrame("Zarządzanie magazynem");
        fr_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr_main.getContentPane().add(new MyPanel(panele.PRACOWNICY));
        fr_main.pack();
        fr_main.setVisible(true);
        fr_main.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                ((JFrame) (e.getComponent())).dispose();
            }
        });

        /*
         * fr_dodajPrac = new JFrame ("Dodawanie pracownika");
         * fr_dodajPrac.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); // zamknij
         * tylko to
         * fr_dodajPrac.getContentPane().add (new MyPanel(panele.DODAJ_PRAC));
         * fr_dodajPrac.pack();
         * fr_dodajPrac.addComponentListener(new ComponentAdapter() {
         * 
         * @Override
         * public void componentHidden(ComponentEvent e) {
         * System.out.println("Replace sysout with your method call");
         * ((JFrame)(e.getComponent())).dispose();
         * }
         * });
         * 
         * fr_dodajPrac.setVisible (true); // czy rozpocząć proces, nie tylko czy
         * pokazać
         */

        /*
         * fr_usunPracownika = new JFrame ("Usówanie pracownika");
         * fr_usunPracownika.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); //
         * zamknij tylko to
         * fr_usunPracownika.getContentPane().add (new MyPanel(panele.USUN_PRAC));
         * fr_usunPracownika.pack();
         * fr_usunPracownika.addComponentListener(new ComponentAdapter() {
         * 
         * @Override
         * public void componentHidden(ComponentEvent e) {
         * System.out.println("Replace sysout with your method call");
         * ((JFrame)(e.getComponent())).dispose();
         * }
         * });
         * 
         * fr_usunPracownika.setVisible (true); // czy rozpocząć proces, nie tylko czy
         * pokazać
         */

        /*
         * fr_dodajPrzychodzoce = new JFrame ("Logowanie odbioru towaru");
         * fr_dodajPrzychodzoce.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); //
         * zamknij tylko to
         * fr_dodajPrzychodzoce.getContentPane().add (new MyPanel(panele.DODAJ_TOWAR));
         * fr_dodajPrzychodzoce.pack();
         * fr_dodajPrzychodzoce.addComponentListener(new ComponentAdapter() {
         * 
         * @Override
         * public void componentHidden(ComponentEvent e) {
         * System.out.println("Replace sysout with your method call");
         * ((JFrame)(e.getComponent())).dispose();
         * }
         * });
         * 
         * fr_dodajPrzychodzoce.setVisible (true); // czy rozpocząć proces, nie tylko
         * czy pokazać
         */

        /*
         * fr_dodajWysylke = new JFrame ("Planowanie wysyłki towaru");
         * fr_dodajWysylke.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); //
         * zamknij tylko to
         * fr_dodajWysylke.getContentPane().add (new MyPanel(panele.WYSLIJ_TOWAR));
         * fr_dodajWysylke.pack();
         * fr_dodajWysylke.addComponentListener(new ComponentAdapter() {
         * 
         * @Override
         * public void componentHidden(ComponentEvent e) {
         * System.out.println("Replace sysout with your method call");
         * ((JFrame)(e.getComponent())).dispose();
         * }
         * });
         * 
         * fr_dodajWysylke.setVisible (true); // czy rozpocząć proces, nie tylko czy
         * pokazać
         */
    }
}