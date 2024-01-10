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
    private JLabel lbl_logRej;
    private JButton btn_loginZatw;
    private JButton btn_rejCof;
    private JLabel lbl_login;
    private JLabel lbl_haslo;
    private JTextField txt_haslo;
    private JTextField txt_kodAdmin;
    private JLabel lbl_kodAdmin;
    public JTextField txt_login;

    private Font czcionka = new Font("Dialog", Font.PLAIN, 18);
    private Font czcionkaB = new Font("Dialog", Font.BOLD, 14);

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

    public static String[][] obiektyNaListyStr(Object[] obiekty, tabele typ) {
        // {"ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata"};
        // {"ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel"};
        // {"ID towaru", "Nazwa towaru", "Data", "Operacja"};

        String[][] stringi = { { "error" } };

        switch (typ) {
            case PRACOWNICY:
                stringi = new String[obiekty.length][6];
                pracownicy = new Pracownik[obiekty.length];

                for (int i = 0; i < obiekty.length; i++) {
                    pracownicy[i] = (Pracownik) obiekty[i];
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
                stringi = new String[obiekty.length][5];
                towary = new Towar[obiekty.length];

                for (int i = 0; i < obiekty.length; i++) {
                    towary[i] = (Towar) obiekty[i];
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
                stringi = new String[obiekty.length][4];
                transporty = new Transport[obiekty.length];

                for (int i = 0; i < obiekty.length; i++) {
                    transporty[i] = (Transport) obiekty[i];
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

    public static Object[] listyStrNaObiekty(String[][] str, tabele typ) {
        // {"ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata"};
        // {"ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel"};
        // {"ID towaru", "Nazwa towaru", "Data", "Operacja"};

        Object[] obiekty = new Object[str.length]; // ok

        /*
         * switch (typ) {
         * case PRACOWNICY:
         * stringi = new String[obiekt.length][6];
         * pracownicy = new Pracownik[obiekt.length];
         * 
         * for (int i = 0; i < obiekt.length; i++) {
         * pracownicy[i] = (Pracownik) obiekt[i];
         * }
         * 
         * for (int i = 0; i < pracownicy.length; i++) {
         * stringi[i][0] = String.valueOf(pracownicy[i].ID);
         * stringi[i][1] = pracownicy[i].nazwisko;
         * stringi[i][2] = pracownicy[i].imie;
         * stringi[i][3] = pracownicy[i].zmiana;
         * stringi[i][4] = pracownicy[i].pozycja;
         * stringi[i][5] = pracownicy[i].wypłata;
         * }
         * break;
         * 
         * case TOWARY:
         * stringi = new String[obiekt.length][5];
         * towary = new Towar[obiekt.length];
         * 
         * for (int i = 0; i < obiekt.length; i++) {
         * towary[i] = (Towar) obiekt[i];
         * }
         * 
         * for (int i = 0; i < towary.length; i++) {
         * stringi[i][0] = String.valueOf(towary[i].ID);
         * stringi[i][1] = towary[i].nazwa;
         * stringi[i][2] = towary[i].typ;
         * stringi[i][3] = String.valueOf(towary[i].wagaKG);
         * stringi[i][4] = towary[i].wlasciciel;
         * }
         * break;
         * 
         * case TRANSPORT:
         * stringi = new String[obiekt.length][4];
         * transporty = new Transport[obiekt.length];
         * 
         * for (int i = 0; i < obiekt.length; i++) {
         * transporty[i] = (Transport) obiekt[i];
         * }
         * 
         * for (int i = 0; i < transporty.length; i++) {
         * stringi[i][0] = String.valueOf(transporty[i].ID);
         * stringi[i][1] = transporty[i].nazwaTowaru;
         * stringi[i][2] = Transport.dataTransNaString(transporty[i].dataTransportu);
         * stringi[i][3] = Transport.stanyNaString(transporty[i].import_eksport);
         * }
         * break;
         * 
         * default:
         * break;
         * 
         * }
         */
        return obiekty;
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

            String dane[][] = obiektyNaListyStr(Pracownik.listaPracownikow.toArray(), typ);

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

            String dane[][] = obiektyNaListyStr(Towar.listaTowarow.toArray(), typ);

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

            String dane[][] = obiektyNaListyStr(Transport.listaTransportow.toArray(), typ);

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

        if (panel == panele.PRACOWNICY || panel == panele.TOWARY || panel == panele.TRANSPORT
                || panel == panele.LOGOWANIE) {

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
                        fr_main.getContentPane().add(new MyPanel(panele.TRANSPORT));
                        fr_main.revalidate();
                    }
                    if (event.getSource() == btn_log) {
                        System.out.println("Działa...");
                        fr_main.getContentPane().removeAll();
                        fr_main.getContentPane().add(new MyPanel(panele.LOGOWANIE));
                        fr_main.revalidate();
                    }
                }
            };
            add(btn_prac);
            add(btn_towary);
            add(btn_trans);
            add(btn_log);

            btn_prac.setBounds(20, 17, 125, 30);
            btn_towary.setBounds(160, 17, 125, 30);
            btn_trans.setBounds(300, 17, 125, 30);
            btn_log.setBounds(440, 17, 125, 30);

            btn_prac.addActionListener(listenMain);
            btn_towary.addActionListener(listenMain);
            btn_trans.addActionListener(listenMain);
            btn_log.addActionListener(listenMain);
        }

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
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_dodaj);
                add(btn_zapisz);
                add(btn_usun);

                // Aboslutna pozycja i rozmiary
                lbl_nazwaTabeli.setBounds(20, 65, 135, 30);
                btn_dodaj.setBounds(55, 520, 190, 30);
                btn_usun.setBounds(555, 520, 190, 30);
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
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_dodaj);
                add(btn_zapisz);
                add(btn_usun);

                // Aboslutna pozycja i rozmiary

                lbl_nazwaTabeli.setBounds(20, 65, 135, 30);
                btn_dodaj.setBounds(55, 520, 190, 30);
                btn_usun.setBounds(555, 520, 190, 30);
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

                // przypisywanie komponentów
                tbl_tabelaMain = new JTable();
                lbl_nazwaTabeli = new JLabel("Lista przywozów i wywozów");
                btn_dodaj = new JButton("Dodaj przywóz");
                btn_usun = new JButton("Dodaj wywóz");
                btn_zapisz = new JButton("Zapisz zmiany");

                // wymiary okna i layout
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                // dodanie komponentow
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_dodaj);
                add(btn_zapisz);
                add(btn_usun);

                // Aboslutna pozycja i rozmiary

                lbl_nazwaTabeli.setBounds(20, 65, 135, 30);
                btn_dodaj.setBounds(55, 520, 190, 30);
                btn_usun.setBounds(555, 520, 190, 30);
                btn_zapisz.setBounds(305, 520, 190, 30);

                // ładowanie i wstępne przypisane danych (test/temp)
                sukcesTabeli = stworzTabele(tabele.TRANSPORT);
                if (!sukcesTabeli)
                    System.out.println("Niepowodzenie w 'stworzTabele(tabele.TRANSPORT)'");

                scr_tabeli = new JScrollPane(tbl_tabelaMain);
                tbl_tabelaMain.setFillsViewportHeight(true);

                add(scr_tabeli);
                scr_tabeli.setBounds(20, 100, 760, 390);

                // Action Listener

                ActionListener listenMainTrans = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_dodaj) {
                        }
                    }
                };

                break;

            case LOGOWANIE:

                System.out.println("Tworzenie okeinka LOGOWANIE");

                setPreferredSize(new Dimension(800, 600));

                // construct components
                lbl_logRej = new JLabel("Logowanie/Rejestracja");
                btn_loginZatw = new JButton("Zaloguj/Zatwierdz");
                btn_rejCof = new JButton("Rejestracja/Cofnij");
                lbl_login = new JLabel("Login");
                lbl_haslo = new JLabel("Hasło");
                txt_haslo = new JTextField(40);
                txt_kodAdmin = new JTextField(20);
                lbl_kodAdmin = new JLabel("Podaj kod administratora");
                txt_login = new JTextField(40);

                // adjust size and set layout
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                // add components
                add(lbl_logRej);
                add(btn_loginZatw);
                add(btn_rejCof);
                add(lbl_login);
                add(lbl_haslo);
                add(txt_haslo);
                add(txt_kodAdmin);
                add(lbl_kodAdmin);
                add(txt_login);

                // set component bounds (only needed by Absolute Positioning)
                lbl_logRej.setBounds(330, 130, 140, 30);
                lbl_login.setBounds(180, 190, 100, 25);
                txt_login.setBounds(175, 220, 450, 45);
                lbl_haslo.setBounds(180, 305, 100, 25);
                txt_haslo.setBounds(175, 335, 450, 45);
                btn_loginZatw.setBounds(315, 420, 170, 35);
                btn_rejCof.setBounds(315, 485, 170, 35);

                lbl_kodAdmin.setBounds(330, 250, 145, 30);
                txt_kodAdmin.setBounds(305, 290, 190, 45);

                txt_login.setFont(czcionka);
                txt_haslo.setFont(czcionka);
                txt_kodAdmin.setFont(czcionka);

                ActionListener listenMainLog = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // if (event.getSource() == btn_dodaj) {
                        // }
                    }
                };

                break;

            case DODAJ_PRAC:
                System.out.println("Tworzenie okeinka DODAJ_PRAC");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubDodajPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // if (event.getSource() == btn_dodaj) {
                        // }
                    }
                };

                break;

            case USUN_PRAC:
                System.out.println("Tworzenie okeinka USUN_PRAC");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubUsunPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // if (event.getSource() == btn_dodaj) {
                        // }
                    }
                };

                break;

            case DODAJ_TOWAR:
                System.out.println("Tworzenie okeinka DODAJ_TOWAR");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubDodajTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // if (event.getSource() == btn_prac) {
                        // }
                    }
                };

                break;

            case WYSLIJ_TOWAR:
                System.out.println("Tworzenie okeinka WYSLIJ_TOWAR");
                setPreferredSize(new Dimension(600, 500));

                ActionListener listenSubWyslijTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // if (event.getSource() == btn_prac) {
                        // }
                    }
                };

                break;

            default:
                System.out.println("Nie ma takiego okienka");
                break;
        }

        System.out.println("Kaniec");
    }

    public static void main(String[] args) {
        MyPanel panelLog = new MyPanel(panele.LOGOWANIE);
        // String[][] sty = new String[5][6];

        fr_main = new JFrame("Zarządzanie magazynem");
        fr_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr_main.getContentPane().add(panelLog);
        fr_main.pack();
        fr_main.setVisible(true);
        fr_main.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                ((JFrame) (e.getComponent())).dispose();
            }
        });

        // System.out.println(sty.length);
        // System.out.println(panelLog.txt_login.getFont().toString());

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