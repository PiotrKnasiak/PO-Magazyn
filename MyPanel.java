package ProjektMagazyn;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ProjektMagazyn.Towar.stanyTowaru;
import ProjektMagazyn.Transport.stanyTransportu;

@SuppressWarnings("ALL")
public class MyPanel extends JPanel {

    // #region Enumy
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

    public static enum etapyLogowania {
        LOGOWANIE,
        KOD,
        REJESTRACJA
    }
    // #endregion

    // #region Okienka i ich elementy
    // okienka
    public static JFrame fr_main;
    public static JFrame fr_dodajPrac;
    public static JFrame fr_usunPracownika;
    public static JFrame fr_dodajEksport;
    public static JFrame fr_dodajImport;

    // elementy fr_main - ogólne
    public JButton btn_prac;
    public JButton btn_towary;
    public JButton btn_trans;
    public JButton btn_log;
    public JLabel lbl_nazwaTabeli;
    public JScrollPane scr_tabeli;
    public static JTable tbl_tabelaMain;
    public JButton btn_dodaj;
    public JButton btn_zapisz;
    public JButton btn_usun;
    public JLabel lbl_zalogojSie;

    public static final int SUKCES = 0;
    public static final int BLAD = -1; // częściowo błędne
    public static final int PORAZKA = -2;

    public static Towar[] towary;

    // elementy fr_main - pracownicy

    // elementy fr_main - towary
    public JButton btn_sortujID;
    public JButton btn_sortujWlasc;

    // elementy fr_main - transport

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

    // elementy fr_dodajPrac;
    private JButton btn_okPracDodaj;
    private JTextField txt_okPracPoz;
    private JTextField txt_okPracIm;
    private JTextField txt_okPracZmi;
    private JTextField txt_okPracNazw;
    private JLabel lbl_okPracPoz;
    private JLabel lbl_okPracIm;
    private JLabel lbl_okPracNazw;
    private JLabel lbl_okPracZmi;
    private JLabel lbl_okPrac;
    private JLabel lbl_okPracError;
    private JLabel lbl_okPracWypl;
    private JTextField txt_okPracWypl;

    // elementy fr_usunPracownika;
    private JButton btn_okPracUs;
    private JTextField txt_okPracUsID;
    private JLabel lbl_okPracUsID;
    private JLabel lbl_okPracUsIm;
    private JLabel lbl_okPracUsNazw;
    private JLabel lbl_okPracUsPoz;
    private JLabel lbl_okPracUs;
    private JLabel lbl_okPracUsZm;
    private JButton btn_okPracUsSpr;

    // elementy fr_dodajImport;
    private JButton btn_okImpDodaj;
    private JTextField txt_okImpNazw;
    private JTextField txt_okImpData;
    private JTextField txt_okImpTyp;
    private JTextField txt_okImpWlasc;
    private JLabel lbl_okImpNazw;
    private JLabel lbl_okImpData;
    private JLabel lbl_okImpWlasc;
    private JLabel lbl_okImpTyp;
    private JLabel lbl_okImp;
    private JLabel lbl_okImpError;
    private JLabel lbl_okImpWaga;
    private JTextField txt_okImpWaga;

    // elementy fr_dodajEksport;
    private JButton btn_okEkspDodaj;
    private JTextField txt_okEkspNazw;
    private JTextField txt_okEkspData;
    private JLabel lbl_okEkspNazw;
    private JLabel lbl_okEkspData;
    private JLabel lbl_okEkspWlasc;
    private JLabel lbl_okEkspTyp;
    private JLabel lbl_okEksp;
    private JLabel lbl_okEkspWaga;
    private JButton btn_okEkspSpr;
    // #endregion

    // #region Pomniejsze zmienne
    private Font czcionkaDefault = new Font("Dialog", Font.PLAIN, 14);
    private Font czcionka = new Font("Dialog", Font.PLAIN, 18);
    private Font czcionkaB = new Font("Dialog", Font.BOLD, 14);

    private static boolean zalogowany = true;
    static int licznik = 0;
    private static String sortTow = "ID"; // ID, Wlasc, Waga
    private static etapyLogowania etapLog = etapyLogowania.LOGOWANIE;
    public static final String KOD_ADMIN = "qwe123";
    // #endregion

    // #region Przetwarzanie danych i tabele
    public static String[][] obiektyNaListyStr(Object[] obiekty, tabele typ) {
        // {"ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata"};
        // {"ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel"};
        // {"ID towaru", "Nazwa towaru", "Data", "Operacja"};

        String[][] stringi = { { "error" } };

        switch (typ) {
            case PRACOWNICY:
                stringi = new String[obiekty.length][6];
                Pracownik[] pracownicy = new Pracownik[obiekty.length];

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
                Transport[] transporty = new Transport[obiekty.length];

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

        Object[] obiekty = new Object[str.length]; // ok
        System.out.println("str.length w listyStrNaObiekty() to: " + str.length);

        switch (typ) {
            case PRACOWNICY:
                // {"ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata"};
                // int ID, String zmiana, String imie, String nazwisko,
                // String pozycja, String wypłata, boolean prank
                for (int i = 0; i < obiekty.length; i++) {
                    int ID = Integer.valueOf(str[i][0]);
                    Pracownik pracownik = new Pracownik(ID, str[i][3],
                            str[i][2], str[i][1], str[i][4], str[i][5], false);

                    obiekty[i] = (Object) pracownik;
                }
                break;

            case TOWARY:
                // {"ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel"};
                // int ID, String nazwa, String typ, int wagaKG, String wlasciciel,
                // stanyTowaru stanTowaru,boolean prank
                for (int i = 0; i < obiekty.length; i++) {
                    int ID = Integer.valueOf(str[i][0]);
                    Towar towar = new Towar(ID, str[i][1], str[i][2], Integer.valueOf(str[i][3]), str[i][4],
                            Transport.zwrocStanTowaru(ID), false);

                    obiekty[i] = (Object) towar;
                }
                break;

            case TRANSPORT:
                // {"ID towaru", "Nazwa towaru", "Data", "Operacja"};
                // int ID, String nazwaTowaru, int rok, int miesiac, int dzien, int godzina,
                // int minuta, stanyTransportu import_eksport, boolean prank
                for (int i = 0; i < obiekty.length; i++) {
                    int ID = Integer.valueOf(str[i][0]);
                    // 03.02.1, 04:05 dd.mm.y hh:minmin
                    String dataIczas[];

                    if (str[i][2].contains(","))
                        dataIczas = str[i][2].replace(" ", "").split(",");
                    else
                        dataIczas = str[i][2].split(" ");

                    String dmy[] = dataIczas[0].split(".");
                    String hm[] = dataIczas[1].split(":");
                    Transport transport = new Transport(ID, str[i][1], Integer.parseInt(dmy[2]),
                            Integer.parseInt(dmy[1]), Integer.parseInt(dmy[0]), Integer.parseInt(hm[0]),
                            Integer.parseInt(hm[1]), Transport.stringNaStany(str[i][3]), false);

                    obiekty[i] = (Object) transport;
                }
                break;

            default:
                break;

        }

        return obiekty;
    }

    public static boolean stworzTabele(tabele typ) {

        if (typ == tabele.PRACOWNICY) {
            String kolumny[] = { "ID", "Nazwisko", "Imie", "Zmiana", "Pozycja", "Wypłata" };

            // Pracownik.listaPracownikow.clear();
            /* zastąpić łądownaiem z pliku */

            // TU !!! wczytaj

            Pracownik.listaPracownikow.sort(Pracownik.porownajID);

            String dane[][] = obiektyNaListyStr(Pracownik.listaPracownikow.toArray(), typ);

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);

        } else if (typ == tabele.TOWARY) {
            String kolumny[] = { "ID", "Nazwa", "Typ produktu", "Waga [kg]", "Właściciel" };

            // Towar.listaTowarow.clear();
            /* zastąpić łądownaiem z pliku */
            // TU !!! wczytaj

            switch (sortTow) {
                case "Wlasc":
                    Towar.listaTowarow.sort(Towar.porownajWlasc);
                    break;

                default:
                    Towar.listaTowarow.sort(Towar.porownajID);
                    break;

            }
            towary = Towar.zwrocMagazyn();

            String dane[][] = obiektyNaListyStr(towary, typ);

            tbl_tabelaMain = new JTable(dane, kolumny);
            tbl_tabelaMain.setBounds(20, 100, 760, 390);

        } else if (typ == tabele.TRANSPORT) {
            String kolumny[] = { "ID towaru", "Nazwa towaru", "Data", "Operacja" };

            // Transport.listaTransportow.clear();
            /* zastąpić łądownaiem z pliku */
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
    // #endregion

    public MyPanel(panele panel) {

        boolean sukcesTabeli = true;

        if (panel == panele.PRACOWNICY || panel == panele.TOWARY || panel == panele.TRANSPORT
                || panel == panele.LOGOWANIE) {

            btn_prac = new JButton("Pracownicy");
            btn_towary = new JButton("Lista Towarów");
            btn_trans = new JButton("Transfery");
            btn_log = new JButton("Logowanie");

            // ogólny actionLietener dla fr_main
            ActionListener listenMain = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (event.getSource() == btn_prac) {
                        fr_main.getContentPane().removeAll();
                        fr_main.getContentPane().add(new MyPanel(panele.PRACOWNICY));
                    }
                    if (event.getSource() == btn_towary) {
                        fr_main.getContentPane().removeAll();
                        fr_main.getContentPane().add(new MyPanel(panele.TOWARY));
                    }
                    if (event.getSource() == btn_trans) {
                        fr_main.getContentPane().removeAll();
                        fr_main.getContentPane().add(new MyPanel(panele.TRANSPORT));
                    }
                    if (event.getSource() == btn_log) {
                        fr_main.getContentPane().removeAll();
                        fr_main.getContentPane().add(new MyPanel(panele.LOGOWANIE));
                    }
                    fr_main.revalidate();
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

                if (!zalogowany)
                    break;

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
                lbl_nazwaTabeli.setBounds(20, 65, 200, 30);
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
                        if (event.getSource() == btn_dodaj) {
                            fr_dodajPrac.setVisible(true);
                        }
                        if (event.getSource() == btn_usun) {
                            fr_usunPracownika.setVisible(true);
                        }
                        if (event.getSource() == btn_zapisz) {

                            TableModel model = tbl_tabelaMain.getModel();
                            String numdata[][] = new String[model.getRowCount()][model.getColumnCount()];

                            for (int countR = 0; countR < model.getRowCount(); countR++) {

                                for (int countC = 0; countC < model.getColumnCount(); countC++) {
                                    numdata[countR][countC] = model.getValueAt(countR, countC).toString();
                                }
                            }

                            Object[] obi = listyStrNaObiekty(numdata, tabele.PRACOWNICY);
                            for (int i = 0; i < obi.length; i++) {
                                Pracownik.listaPracownikow.set(i, (Pracownik) obi[i]);
                            }

                        }
                    }
                };

                btn_dodaj.addActionListener(listenMainPrac);
                btn_usun.addActionListener(listenMainPrac);
                btn_zapisz.addActionListener(listenMainPrac);

                break;

            case TOWARY:
                System.out.println("Tworzenie okeinka TOWARY");

                if (!zalogowany)
                    break;

                // przypisywanie komponentów
                tbl_tabelaMain = new JTable();
                lbl_nazwaTabeli = new JLabel("Lista towarów");
                btn_sortujID = new JButton("Sortuj towary po ID");
                btn_sortujWlasc = new JButton("Sortuj po właścicielach");
                btn_zapisz = new JButton("Zapisz zmiany");

                // wymiary okna i layout
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                // dodanie komponentow
                add(lbl_nazwaTabeli);
                // tabela dodana poniżej
                add(btn_sortujID);
                add(btn_sortujWlasc);
                add(btn_zapisz);

                // Aboslutna pozycja i rozmiary

                lbl_nazwaTabeli.setBounds(20, 65, 200, 30);
                btn_sortujID.setBounds(55, 520, 190, 30);
                btn_sortujWlasc.setBounds(555, 520, 190, 30);
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
                        if (event.getSource() == btn_sortujID) {

                            tbl_tabelaMain = new JTable();
                            sortTow = "ID";
                            boolean sukcesTab = stworzTabele(tabele.TOWARY);

                            if (!sukcesTab)
                                System.out.println("Niepowodzenie w 'stworzTabele(tabele.TOWARY)', sortowanie po ID");

                            scr_tabeli = new JScrollPane(tbl_tabelaMain);
                            revalidate();
                        }
                        if (event.getSource() == btn_sortujWlasc) {

                            tbl_tabelaMain = new JTable();
                            sortTow = "Wlasc";
                            boolean sukcesTab = stworzTabele(tabele.TOWARY);

                            if (!sukcesTab)
                                System.out.println(
                                        "Niepowodzenie w 'stworzTabele(tabele.TOWARY)', sortowanie po Właścicielach");

                            scr_tabeli = new JScrollPane(tbl_tabelaMain);
                            revalidate();
                        }
                        if (event.getSource() == btn_zapisz) {

                            TableModel model = tbl_tabelaMain.getModel();
                            String numdata[][] = new String[model.getRowCount()][model.getColumnCount()];

                            for (int countR = 0; countR < model.getRowCount(); countR++) {

                                for (int countC = 0; countC < model.getColumnCount(); countC++) {
                                    numdata[countR][countC] = model.getValueAt(countR, countC).toString();
                                }
                            }

                            Object[] obi = listyStrNaObiekty(numdata, tabele.TOWARY);

                            for (int i = 0; i < obi.length; i++) {
                                Towar.listaTowarow.set(Towar.miejsceWTab(((Towar) obi[i]).ID), (Towar) obi[i]);
                            }
                        }
                    }
                };

                btn_sortujID.addActionListener(listenMainTow);
                btn_sortujWlasc.addActionListener(listenMainTow);
                btn_zapisz.addActionListener(listenMainTow);

                for (Towar t : Towar.listaTowarow) {
                    System.out.println(t.toString());
                }

                break;

            case TRANSPORT:
                System.out.println("Tworzenie okeinka TRANSPORT");

                if (!zalogowany)
                    break;

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

                lbl_nazwaTabeli.setBounds(20, 65, 200, 30);
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
                        if (event.getSource() == btn_usun) {
                        }
                        if (event.getSource() == btn_zapisz) {

                            TableModel model = tbl_tabelaMain.getModel();
                            String numdata[][] = new String[model.getRowCount()][model.getColumnCount()];

                            for (int countR = 0; countR < model.getRowCount(); countR++) {

                                for (int countC = 0; countC < model.getColumnCount(); countC++) {
                                    numdata[countR][countC] = model.getValueAt(countR, countC).toString();
                                }
                            }

                            Object[] obi = listyStrNaObiekty(numdata, tabele.TRANSPORT);
                            for (int i = 0; i < obi.length; i++) {
                                Transport.listaTransportow.set(i, (Transport) obi[i]);
                            }
                        }
                        revalidate();
                    }
                };

                break;

            case LOGOWANIE:

                System.out.println("Tworzenie okeinka LOGOWANIE");

                setPreferredSize(new Dimension(800, 600));

                // construct components
                lbl_logRej = new JLabel("Logowanie/Rejestracja", SwingConstants.CENTER); // Logowanie/Rejestracja
                btn_loginZatw = new JButton("Zaloguj/Zatwierdz"); // Zaloguj/Zatwierdz
                btn_rejCof = new JButton("Rejestracja/Cofnij"); // Rejestracja/Cofnij
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
                lbl_logRej.setBounds(200, 130, 400, 30);

                lbl_login.setBounds(180, 190, 150, 25);
                txt_login.setBounds(175, 220, 450, 45);
                lbl_haslo.setBounds(180, 305, 150, 25);
                txt_haslo.setBounds(175, 335, 450, 45);

                btn_loginZatw.setBounds(315, 420, 170, 35);
                btn_rejCof.setBounds(315, 485, 170, 35);

                lbl_kodAdmin.setBounds(330, 250, 145, 30);
                txt_kodAdmin.setBounds(305, 290, 190, 45);

                txt_login.setFont(czcionka);
                txt_haslo.setFont(czcionka);
                txt_kodAdmin.setFont(czcionka);
                lbl_logRej.setFont(czcionkaDefault);
                lbl_login.setFont(czcionkaB);
                lbl_haslo.setFont(czcionkaB);

                new DaneLogowania(3, "t", "t");

                ActionListener listenMainLog = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        switch (etapLog) {
                            case LOGOWANIE:
                                if (event.getSource() == btn_loginZatw) {
                                    System.out.println("Lista użytkowników: \n" + DaneLogowania.testListaDL());

                                    int poprawnie = DaneLogowania.sprawdzLogwanie(txt_login.getText(),
                                            txt_haslo.getText());
                                    if (poprawnie == SUKCES) {
                                        etapLog = etapyLogowania.LOGOWANIE;

                                        zalogowany = true;
                                        lbl_logRej.setText("Zalogowano!");
                                        lbl_logRej.setForeground(new Color(0, 184, 0));

                                        txt_login.setText("");
                                        txt_haslo.setText("");
                                    } else {
                                        System.out.println(
                                                "You stupid! " + txt_login.getText() + " " + txt_haslo.getText());
                                        lbl_logRej.setForeground(new Color(204, 0, 0));
                                        lbl_logRej.setText("Błędny login lub hasło");
                                        txt_haslo.setText("");
                                    }
                                } else if (event.getSource() == btn_rejCof) {
                                    etapLog = etapyLogowania.KOD;

                                    lbl_logRej.setText("Podaj kod administratora"); // Logowanie/Rejestracja
                                    lbl_logRej.setForeground(new Color(0, 0, 0));

                                    lbl_login.setVisible(false);
                                    txt_login.setVisible(false);
                                    txt_login.setText("");

                                    lbl_haslo.setVisible(false);
                                    txt_haslo.setVisible(false);
                                    txt_haslo.setText("");

                                    txt_kodAdmin.setVisible(true);
                                    lbl_kodAdmin.setVisible(true);

                                    btn_loginZatw.setText("Zatwierdź"); // Zaloguj/Zatwierdz
                                    btn_rejCof.setText("Cofnij"); // Rejestracja/Cofnij
                                }
                                break;
                            case KOD:
                                if (event.getSource() == btn_loginZatw) {

                                    if (txt_kodAdmin.getText().compareTo(KOD_ADMIN) == 0) {
                                        etapLog = etapyLogowania.REJESTRACJA;

                                        lbl_logRej.setText("Rejestracja"); // Logowanie/Rejestracja
                                        lbl_logRej.setForeground(new Color(0, 0, 0));

                                        lbl_login.setText("Nowy login");
                                        lbl_login.setVisible(true);
                                        txt_login.setVisible(true);
                                        lbl_haslo.setText("Nowe hasło");
                                        lbl_haslo.setVisible(true);
                                        txt_haslo.setVisible(true);

                                        txt_kodAdmin.setVisible(false);
                                        lbl_kodAdmin.setVisible(false);
                                    } else {
                                        lbl_logRej.setText("Podano błędny kod"); // Logowanie/Rejestracja
                                        lbl_logRej.setForeground(new Color(204, 0, 0));

                                    }
                                } else if (event.getSource() == btn_rejCof) {
                                    etapLog = etapyLogowania.LOGOWANIE;

                                    lbl_logRej.setText("Logowanie"); // Logowanie/Rejestracja
                                    lbl_logRej.setForeground(new Color(0, 0, 0));

                                    lbl_login.setText("Login");
                                    lbl_login.setVisible(true);
                                    txt_login.setVisible(true);
                                    txt_login.setText("");

                                    lbl_haslo.setText("Hasło");
                                    lbl_haslo.setVisible(true);
                                    txt_haslo.setVisible(true);
                                    txt_haslo.setText("");

                                    txt_kodAdmin.setVisible(false);
                                    lbl_kodAdmin.setVisible(false);
                                    txt_kodAdmin.setText("");

                                    btn_loginZatw.setText("Zaloguj"); // Zaloguj/Zatwierdź
                                    btn_rejCof.setText("Rejestracja"); // Rejestracja/Cofnij

                                    if (zalogowany) {
                                        lbl_logRej.setText("Zalogowano!");
                                        lbl_logRej.setForeground(new Color(0, 184, 0));

                                        txt_login.setText("");
                                        txt_haslo.setText("");
                                    }
                                }
                                break;
                            case REJESTRACJA:
                                if (event.getSource() == btn_loginZatw) {
                                    boolean spacje = (txt_login.getText().contains(" "))
                                            || (txt_haslo.getText().contains(" "));
                                    int rej = DaneLogowania.sprawdzLogin(txt_login.getText());

                                    boolean pusto = (txt_login.getText().replace(" ", "").compareTo("") == 0)
                                            || (txt_haslo.getText().replace(" ", "").compareTo("") == 0);

                                    boolean powodzenie = (rej == PORAZKA) && !spacje && !pusto;

                                    if (powodzenie) {
                                        etapLog = etapyLogowania.LOGOWANIE;
                                        zalogowany = true;

                                        new DaneLogowania(DaneLogowania.ostatnieID(), txt_login.getText(),
                                                txt_haslo.getText());

                                        lbl_logRej.setText("Zalogowano!");
                                        lbl_logRej.setForeground(new Color(0, 184, 0));

                                        lbl_login.setText("Login");
                                        lbl_login.setVisible(true);
                                        txt_login.setVisible(true);
                                        txt_login.setText("");

                                        lbl_haslo.setText("Hasło");
                                        lbl_haslo.setVisible(true);
                                        txt_haslo.setVisible(true);
                                        txt_haslo.setText("");

                                        txt_kodAdmin.setVisible(false);
                                        lbl_kodAdmin.setVisible(false);

                                        btn_loginZatw.setText("Zaloguj"); // Zaloguj/Zatwierdź
                                        btn_rejCof.setText("Rejestracja"); // Rejestracja/Cofnij
                                    } else if (pusto) {
                                        lbl_logRej.setForeground(new Color(204, 0, 0));
                                        lbl_logRej.setText("Login lub hasło jest puste");

                                        if (txt_login.getText().replace(" ", "").compareTo("") == 0) {
                                            txt_login.setText("");
                                        }
                                        if (txt_haslo.getText().replace(" ", "").compareTo("") == 0) {
                                            txt_haslo.setText("");
                                        }
                                    } else if (rej == SUKCES) {
                                        lbl_logRej.setForeground(new Color(204, 0, 0));
                                        lbl_logRej.setText("Login już istnieje");

                                        txt_login.setText("");
                                    } else if (spacje) {
                                        lbl_logRej.setForeground(new Color(204, 0, 0));
                                        lbl_logRej.setText("Login lub hasło ma w sobie spacje");

                                    } else {
                                        lbl_logRej.setForeground(new Color(204, 0, 0));
                                        lbl_logRej.setText("Błąd loginu lub hasła");
                                    }

                                } else if (event.getSource() == btn_rejCof) {
                                    etapLog = etapyLogowania.LOGOWANIE;

                                    lbl_logRej.setText("Logowanie"); // Logowanie/Rejestracja
                                    lbl_logRej.setForeground(new Color(0, 0, 0));

                                    lbl_login.setText("Login");
                                    lbl_login.setVisible(true);
                                    txt_login.setVisible(true);
                                    txt_login.setText("");

                                    lbl_haslo.setText("Hasło");
                                    lbl_haslo.setVisible(true);
                                    txt_haslo.setVisible(true);
                                    txt_haslo.setText("");

                                    txt_kodAdmin.setVisible(false);
                                    lbl_kodAdmin.setVisible(false);

                                    btn_loginZatw.setText("Zaloguj"); // Zaloguj/Zatwierdź
                                    btn_rejCof.setText("Rejestracja"); // Rejestracja/Cofnij

                                    if (zalogowany) {
                                        lbl_logRej.setText("Zalogowano!");
                                        lbl_logRej.setForeground(new Color(0, 184, 0));

                                        txt_login.setText("");
                                        txt_haslo.setText("");
                                    }
                                }
                                break;
                        }
                        revalidate();
                    }
                };

                lbl_logRej.setText("Logowanie"); // Logowanie/Rejestracja
                btn_loginZatw.setText("Zaloguj"); // Zaloguj/Zatwierdz
                btn_rejCof.setText("Rejestracja"); // Rejestracja/Cofnij

                txt_kodAdmin.setVisible(false);
                lbl_kodAdmin.setVisible(false);

                btn_loginZatw.addActionListener(listenMainLog);
                btn_rejCof.addActionListener(listenMainLog);

                if (zalogowany) {
                    lbl_logRej.setText("Zalogowano!");
                    lbl_logRej.setForeground(new Color(0, 184, 0));

                    txt_login.setText("");
                    txt_haslo.setText("");
                }
                break;

            case DODAJ_PRAC:
                System.out.println("Tworzenie okeinka DODAJ_PRAC");

                // construct components
                btn_okPracDodaj = new JButton("Dodaj pracownika");
                txt_okPracPoz = new JTextField(5);
                txt_okPracIm = new JTextField(5);
                txt_okPracZmi = new JTextField(5);
                txt_okPracNazw = new JTextField(5);
                lbl_okPracPoz = new JLabel("Pozycja");
                lbl_okPracIm = new JLabel("Imię");
                lbl_okPracNazw = new JLabel("Nazwisko");
                lbl_okPracZmi = new JLabel("Zmiana");
                lbl_okPrac = new JLabel("Dodawanie pracownika");
                lbl_okPracError = new JLabel(
                        "Tu error?................................................................................");
                lbl_okPracWypl = new JLabel("Wypłata");
                txt_okPracWypl = new JTextField(5);

                // adjust size and set layout
                setPreferredSize(new Dimension(600, 500));
                setLayout(null);

                // add components
                add(btn_okPracDodaj);
                add(txt_okPracPoz);
                add(txt_okPracIm);
                add(txt_okPracZmi);
                add(txt_okPracNazw);
                add(lbl_okPracPoz);
                add(lbl_okPracIm);
                add(lbl_okPracNazw);
                add(lbl_okPracZmi);
                add(lbl_okPrac);
                add(lbl_okPracError);
                add(lbl_okPracWypl);
                add(txt_okPracWypl);

                // set component bounds (only needed by Absolute Positioning)
                btn_okPracDodaj.setBounds(200, 440, 200, 30);
                txt_okPracPoz.setBounds(50, 120, 500, 25);
                txt_okPracIm.setBounds(50, 220, 225, 25);
                txt_okPracZmi.setBounds(50, 320, 225, 25);
                txt_okPracNazw.setBounds(325, 220, 225, 25);
                lbl_okPracPoz.setBounds(50, 90, 200, 25);
                lbl_okPracIm.setBounds(50, 190, 200, 25);
                lbl_okPracNazw.setBounds(325, 190, 200, 25);
                lbl_okPracZmi.setBounds(50, 290, 200, 25);
                lbl_okPrac.setBounds(150, 25, 300, 30);
                lbl_okPracError.setBounds(150, 375, 300, 30);
                lbl_okPracWypl.setBounds(325, 290, 200, 25);
                txt_okPracWypl.setBounds(325, 320, 225, 25);

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

                // construct components
                btn_okPracUs = new JButton("Usuń pracownika");
                txt_okPracUsID = new JTextField(5);
                lbl_okPracUsID = new JLabel("ID");
                lbl_okPracUsIm = new JLabel("Imię : ");
                lbl_okPracUsNazw = new JLabel("Nazwisko : ");
                lbl_okPracUsPoz = new JLabel("Pozycja : ");
                lbl_okPracUs = new JLabel("Usówanie pracownika");
                lbl_okPracUsZm = new JLabel("Zmiana : ");
                btn_okPracUsSpr = new JButton("Sprawdź informacje pracownika (ID)");

                // adjust size and set layout
                setPreferredSize(new Dimension(600, 500));
                setLayout(null);

                // add components
                add(btn_okPracUs);
                add(txt_okPracUsID);
                add(lbl_okPracUsID);
                add(lbl_okPracUsIm);
                add(lbl_okPracUsNazw);
                add(lbl_okPracUsPoz);
                add(lbl_okPracUs);
                add(lbl_okPracUsZm);
                add(btn_okPracUsSpr);

                // set component bounds (only needed by Absolute Positioning)
                btn_okPracUs.setBounds(190, 440, 220, 30);
                txt_okPracUsID.setBounds(70, 110, 100, 25);
                lbl_okPracUsID.setBounds(70, 85, 30, 25);
                lbl_okPracUsIm.setBounds(75, 215, 200, 25);
                lbl_okPracUsNazw.setBounds(75, 265, 200, 25);
                lbl_okPracUsPoz.setBounds(75, 315, 200, 25);
                lbl_okPracUs.setBounds(150, 25, 300, 30);
                lbl_okPracUsZm.setBounds(75, 365, 200, 25);
                btn_okPracUsSpr.setBounds(275, 105, 250, 30);

                ActionListener listenSubUsunPrac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_okPracUs) {
                        }
                        if (event.getSource() == btn_okPracUsSpr) {
                        }
                    }
                };

                btn_okPracUs.addActionListener(listenSubUsunPrac);
                btn_okPracUsSpr.addActionListener(listenSubUsunPrac);

                break;

            case DODAJ_TOWAR:
                System.out.println("Tworzenie okeinka DODAJ_TOWAR");

                // construct components
                btn_okImpDodaj = new JButton("Dodaj import");
                txt_okImpNazw = new JTextField(5);
                txt_okImpData = new JTextField(5);
                txt_okImpTyp = new JTextField(5);
                txt_okImpWlasc = new JTextField(5);
                lbl_okImpNazw = new JLabel("Nazwa");
                lbl_okImpData = new JLabel("Data (dd.mm.yyyy hh:mm)");
                lbl_okImpWlasc = new JLabel("Właściciel");
                lbl_okImpTyp = new JLabel("Kategoria towaru");
                lbl_okImp = new JLabel("Dodawanie importu towaru");
                lbl_okImpError = new JLabel(
                        "Tu error?................................................................................");
                lbl_okImpWaga = new JLabel("Waga towaru");
                txt_okImpWaga = new JTextField(5);

                // adjust size and set layout
                setPreferredSize(new Dimension(600, 500));
                setLayout(null);

                // add components
                add(btn_okImpDodaj);
                add(txt_okImpNazw);
                add(txt_okImpData);
                add(txt_okImpTyp);
                add(txt_okImpWlasc);
                add(lbl_okImpNazw);
                add(lbl_okImpData);
                add(lbl_okImpWlasc);
                add(lbl_okImpTyp);
                add(lbl_okImp);
                add(lbl_okImpError);
                add(lbl_okImpWaga);
                add(txt_okImpWaga);

                // set component bounds (only needed by Absolute Positioning)
                btn_okImpDodaj.setBounds(200, 440, 200, 30);
                txt_okImpNazw.setBounds(50, 120, 500, 25);
                txt_okImpData.setBounds(50, 220, 225, 25);
                txt_okImpTyp.setBounds(50, 320, 225, 25);
                txt_okImpWlasc.setBounds(325, 220, 225, 25);
                lbl_okImpNazw.setBounds(50, 90, 200, 25);
                lbl_okImpData.setBounds(50, 190, 200, 25);
                lbl_okImpWlasc.setBounds(325, 190, 200, 25);
                lbl_okImpTyp.setBounds(50, 290, 200, 25);
                lbl_okImp.setBounds(150, 25, 300, 30);
                lbl_okImpError.setBounds(150, 375, 300, 30);
                lbl_okImpWaga.setBounds(325, 290, 200, 25);
                txt_okImpWaga.setBounds(325, 320, 225, 25);

                ActionListener listenSubDodajTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_okImpDodaj) {
                        }
                    }
                };

                btn_okImpDodaj.addActionListener(listenSubDodajTow);

                break;

            case WYSLIJ_TOWAR:
                System.out.println("Tworzenie okeinka WYSLIJ_TOWAR");
                setPreferredSize(new Dimension(600, 500));
                setLayout(null);

                // construct components
                btn_okEkspDodaj = new JButton("Dodaj Eksport");
                txt_okEkspNazw = new JTextField(5);
                txt_okEkspData = new JTextField(5);
                lbl_okEkspNazw = new JLabel("ID");
                lbl_okEkspData = new JLabel("Data (dd.mm.yyyy, hh:mm)");
                lbl_okEkspWlasc = new JLabel("Właściciel : ");
                lbl_okEkspTyp = new JLabel("Kategoria towaru : ");
                lbl_okEksp = new JLabel("Dodawanie Eksportu towaru");
                lbl_okEkspWaga = new JLabel("Waga towaru : ");
                btn_okEkspSpr = new JButton("Sprawdź informacje towaru (ID)");

                // add components
                add(btn_okEkspDodaj);
                add(txt_okEkspNazw);
                add(txt_okEkspData);
                add(lbl_okEkspNazw);
                add(lbl_okEkspData);
                add(lbl_okEkspWlasc);
                add(lbl_okEkspTyp);
                add(lbl_okEksp);
                add(lbl_okEkspWaga);
                add(btn_okEkspSpr);

                // set component bounds (only needed by Absolute Positioning)
                btn_okEkspDodaj.setBounds(190, 440, 220, 30);
                txt_okEkspNazw.setBounds(75, 110, 100, 25);
                txt_okEkspData.setBounds(75, 190, 225, 25);
                lbl_okEkspNazw.setBounds(75, 80, 30, 25);
                lbl_okEkspData.setBounds(75, 160, 200, 25);
                lbl_okEkspWlasc.setBounds(80, 265, 200, 25);
                lbl_okEkspTyp.setBounds(80, 315, 200, 25);
                lbl_okEksp.setBounds(150, 25, 300, 30);
                lbl_okEkspWaga.setBounds(80, 365, 200, 25);
                btn_okEkspSpr.setBounds(305, 105, 220, 30);

                ActionListener listenSubWyslijTow = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == btn_okEkspDodaj) {
                        }
                        if (event.getSource() == btn_okEkspSpr) {
                        }
                    }
                };

                btn_okEkspDodaj.addActionListener(listenSubWyslijTow);
                btn_okEkspSpr.addActionListener(listenSubWyslijTow);

                break;

            default:
                System.out.println("Nie ma takiego okienka");
                break;
        }

        if (panel == panele.PRACOWNICY || panel == panele.TOWARY || panel == panele.TRANSPORT) {

            lbl_zalogojSie = new JLabel("Nie jeseś zalogowany!", SwingConstants.CENTER);

            fr_main.add(lbl_zalogojSie);
            lbl_zalogojSie.setBounds(225, 250, 350, 40);
            lbl_zalogojSie.setFont(new Font("Dialog", Font.BOLD, 30));

            lbl_zalogojSie.setVisible(false);

            if (!zalogowany) {
                lbl_zalogojSie.setVisible(true);
                System.out.println("Niezalogowany! (stoopid)");
                setPreferredSize(new Dimension(800, 600));
                setLayout(null);

                btn_prac.setBounds(20, 17, 125, 30);
                btn_towary.setBounds(160, 17, 125, 30);
                btn_trans.setBounds(300, 17, 125, 30);
                btn_log.setBounds(440, 17, 125, 30);
            }
        }
        revalidate();
    }

    public static void main(String[] args) {

        new Pracownik(1, "Nocna", "Filip", "Wisiewski", "praktykant (niewolnik)", "0");
        new Pracownik(2, "Dzienna", "Tymoteusz", "Mareewski", "praktykant (niewolnik)", "0");
        new Pracownik(3, "Dzienna i nocna", "Marcin", "Walaszek", "Operator wózka widłowego ", "5700");
        new Pracownik(4, "Dzienna", "Arkadiusz", "Poranny", "Kierowca ciężarówki", "3700");

        new Towar(1, "Cegły", "Materiały budowlane", 2000, "SP-bud z.o.o.", Towar.stanyTowaru.DO_ODBIORU);
        new Towar(2, "Wykałaczki", "Spożywcze", 50, "Jeff", Towar.stanyTowaru.DO_ODBIORU);
        new Towar(3, "Miedź", "Surowce", 4500, "z-MiedzioMiedź s.a.", Towar.stanyTowaru.DO_ODBIORU);
        new Towar(4, "Karton-gips", "Materiały budowlane", 800, "SP-bud z.o.o.", Towar.stanyTowaru.DO_ODBIORU);

        new Transport(1, "Cegły", 2022, 11, 18, 17, 15, Transport.stanyTransportu.IMPORT);
        new Transport(1, "Cegły", 2023, 11, 18, 17, 15, Transport.stanyTransportu.EKSPORT);
        new Transport(2, "Wykałaczki", 2024, 3, 8, 15, 12, Transport.stanyTransportu.IMPORT);
        new Transport(3, "Miedź", 2003, 10, 1, 7, 6, Transport.stanyTransportu.IMPORT);
        new Transport(4, "Karton-gips", 2023, 11, 18, 17, 15, Transport.stanyTransportu.IMPORT);
        new Transport(4, "Karton-gips", 2024, 11, 18, 17, 15, Transport.stanyTransportu.EKSPORT);

        // Towar wMag[] = Towar.zwrocMagazyn();
        for (int i = 0; i < Towar.listaTowarow.size(); i++) {
            Towar towar = Towar.listaTowarow.get(i);
            towar.stanTowaru = Transport.zwrocStanTowaru(towar.ID);
            Towar.listaTowarow.set(i, towar);
        }

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

        fr_dodajPrac = new JFrame("Dodawanie pracownika");
        fr_dodajPrac.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zamknij tylko to
        fr_dodajPrac.getContentPane().add(new MyPanel(panele.DODAJ_PRAC));
        fr_dodajPrac.pack();

        fr_dodajPrac.setVisible(false); // czy rozpocząć proces, nie tylko czy pokazać

        fr_usunPracownika = new JFrame("Usówanie pracownika");
        fr_usunPracownika.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zamknij tylko to
        fr_usunPracownika.getContentPane().add(new MyPanel(panele.USUN_PRAC));
        fr_usunPracownika.pack();
        fr_usunPracownika.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                ((JFrame) (e.getComponent())).dispose();
            }
        });

        fr_usunPracownika.setVisible(false); // czy rozpocząć proces, nie tylko czy pokazać

        fr_dodajImport = new JFrame("Logowanie odbioru towaru");
        fr_dodajImport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zamknij tylko to
        fr_dodajImport.getContentPane().add(new MyPanel(panele.DODAJ_TOWAR));
        fr_dodajImport.pack();
        fr_dodajImport.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                ((JFrame) (e.getComponent())).dispose();
            }
        });

        fr_dodajImport.setVisible(false); // czy rozpocząć proces, nie tylko czy pokazać

        fr_dodajEksport = new JFrame("Planowanie wysyłki towaru");
        fr_dodajEksport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zamknij tylko to
        fr_dodajEksport.getContentPane().add(new MyPanel(panele.WYSLIJ_TOWAR));
        fr_dodajEksport.pack();
        fr_dodajEksport.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                ((JFrame) (e.getComponent())).dispose();
            }
        });

        fr_dodajEksport.setVisible(false); // czy rozpocząć proces, nie tylko czy pokazać

        System.out.println("\n\nKoniec main\n\n");
    }
}