package ProjektMagazyn;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class Transport implements Comparable<Transport> {
   public static enum stanyTransportu {
      IMPORT,
      EKSPORT
   }

   public static String stanyNaString(stanyTransportu sT) {
      if (sT == stanyTransportu.IMPORT)
         return "Przywóz do magazynu";
      return "Wywóz z magazynu";
   }

   public static stanyTransportu stringNaStany(String str) {
      str = str.toLowerCase().replace(" ", "").replace("ó", "o");

      if (str == "przywozdomagazynu" || str == "import")
         return stanyTransportu.IMPORT;
      return stanyTransportu.EKSPORT;
   }

   public static String dataTransNaString(LocalDateTime dt) {
      LocalDate data = dt.toLocalDate();
      LocalTime czas = dt.toLocalTime();

      ZonedDateTime zonedDateTime = ZonedDateTime.of(data, czas, ZoneId.of("Europe/Helsinki"));
      String dataICzas = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime);

      return dataICzas;
   }

   public static LocalDateTime stringNaDataTrans(int rok, int miesiac, int dzien, int godzina, int minuta) {

      LocalDateTime ldt = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);

      return ldt;
   }

   public static Transport[] listaTransTowaru(int ID) {
      ArrayList<Transport> transporty = new ArrayList<>();

      for (int i = 0; i < Transport.listaTransportow.size(); i++) {
         if (Transport.listaTransportow.get(i).ID == ID) {
            transporty.add(Transport.listaTransportow.get(i));
         }
      }

      if (transporty.size() == 0)
         return null;

      return transporty.toArray(new Transport[0]);
   }

   static Towar.stanyTowaru stanTowaruTeraz(Transport trans) {

      if (trans == null)
         return Towar.stanyTowaru.BLAD;

      int porownanieCzas = LocalDateTime.now().compareTo(trans.dataTransportu);

      // teraz większe od daty -> 1
      // teraz równe dacie -> 0
      // teraz mniejsze od daty -> -1

      // data w przeszłości -> 1
      // data teraz -> 0
      // data w przyszłośi -> -1

      if (trans.import_eksport == Transport.stanyTransportu.IMPORT) {
         if (porownanieCzas >= 0) {
            return Towar.stanyTowaru.W_MAGAZYNIE;
         } else {
            return Towar.stanyTowaru.DO_ODBIORU;
         }
      } else {
         if (porownanieCzas >= 0) {
            return Towar.stanyTowaru.WYWIEZIONY;
         } else {
            return Towar.stanyTowaru.W_MAGAZYNIE;
         }
      }
   }

   public static Towar.stanyTowaru zwrocStanTowaru(int ID) {

      Transport[] sprawdzane = listaTransTowaru(ID);
      boolean znaleziony = true;
      Transport imp = null, eksp = null;

      if (Towar.znajdzPoID(ID, false) == null) {
         znaleziony = false;
      }

      if (sprawdzane == null || !znaleziony) {
         System.out.println("\n*** Error!\n\tNiepowodzenie w zwrocStanTowaru(" + ID + ")!");
         if (!znaleziony) {
            System.out.println("\t\tNie ma takiego towaru");
         } else if (sprawdzane == null) {
            System.out.println("\t\tNie ma takiego transportu - Domyślnie w magazynie");
            return Towar.stanyTowaru.W_MAGAZYNIE;
         }
         return Towar.stanyTowaru.BLAD;
      }

      System.out.println("Towar o ID: " + ID + " ma " + sprawdzane.length + " transportów");

      if (sprawdzane.length == 1) {
         return stanTowaruTeraz(sprawdzane[0]);
      }

      if (sprawdzane.length >= 2) {
         if (sprawdzane[sprawdzane.length - 1].import_eksport == stanyTransportu.IMPORT) {
            imp = sprawdzane[sprawdzane.length - 1];
            for (int i = sprawdzane.length - 2; i >= 0; i--) {
               if (sprawdzane[i].import_eksport == stanyTransportu.EKSPORT) {
                  eksp = sprawdzane[i];
                  break;
               }
            }
         } else {
            eksp = sprawdzane[sprawdzane.length - 1];
            for (int i = sprawdzane.length - 2; i >= 0; i--) {
               if (sprawdzane[i].import_eksport == stanyTransportu.IMPORT) {
                  imp = sprawdzane[i];
                  break;
               }
            }
         }
         if (eksp == null)
            System.out.println("Eksp to null");
         if (imp == null)
            System.out.println("Imp to null");

         Towar.stanyTowaru stanE = stanTowaruTeraz(eksp);
         Towar.stanyTowaru stanI = stanTowaruTeraz(imp);

         System.out.println("data stanI to" + dataTransNaString(imp.dataTransportu));
         System.out.println("data stanE to" + dataTransNaString(eksp.dataTransportu));
         if (stanI == Towar.stanyTowaru.DO_ODBIORU)
            System.out.print("stanI to: DO_ODBIORU, ");
         else if (stanI == Towar.stanyTowaru.BLAD)
            System.out.print("stanI to: BLAD, ");
         else
            System.out.print("stanI to: W_MAGAZYNIE, ");

         if (stanE == Towar.stanyTowaru.WYWIEZIONY)
            System.out.println("stanE to: WYWIEZIONY ");
         else if (stanE == Towar.stanyTowaru.BLAD)
            System.out.println("stanE to: BLAD ");
         else
            System.out.println("stanE to: W_MAGAZYNIE ");
         System.out.println();

         if (stanE == null)
            return stanI;
         if (imp == null)
            return stanE;

         if (stanE == Towar.stanyTowaru.WYWIEZIONY)
            return stanE;
         return stanI;
      }

      return Towar.stanyTowaru.W_MAGAZYNIE;
   }

   public Transport() {
      this.ID = -2;
      listaTransportow.add(this);
   }

   public Transport(int rok, int miesiac, int dzien, int godzina, int minuta) {
      this.ID = -1;
      this.nazwaTowaru = "konstrukor_testDaty";
      this.dataTransportu = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);
      listaTransportow.add(this);
   }

   public Transport(int ID, String nazwaTowaru, int rok, int miesiac, int dzien, int godzina, int minuta,
         stanyTransportu import_eksport) {
      this.ID = ID;
      this.nazwaTowaru = nazwaTowaru;
      this.dataTransportu = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);
      this.import_eksport = import_eksport;

      listaTransportow.add(this);
   }

   public Transport(int ID, String nazwaTowaru, int rok, int miesiac, int dzien, int godzina, int minuta, // bez
                                                                                                          // dodawania
         stanyTransportu import_eksport, boolean prank) {

      this.ID = ID;
      this.nazwaTowaru = nazwaTowaru;
      this.dataTransportu = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);
      this.import_eksport = import_eksport;
   }

   int ID = 0;
   String nazwaTowaru = "Brak nazwy";
   LocalDateTime dataTransportu = LocalDateTime.now().plus(1, ChronoUnit.YEARS);
   stanyTransportu import_eksport = stanyTransportu.IMPORT;

   static List<Transport> listaTransportow = new ArrayList<>();

   static Collator kolator = Collator.getInstance(new Locale("pl"));

   @Override
   public int compareTo(Transport t) {
      return kolator.compare(this.ID, t.ID);
   }

   static Comparator<Transport> porownajID = new Comparator<Transport>() {
      @Override
      public int compare(Transport t1, Transport t2) {
         if (t1.ID > t2.ID)
            return 1;
         if (t1.ID < t2.ID)
            return -1;
         return porownajData.compare(t1, t2);
      }
   };
   static Comparator<Transport> porownajData = new Comparator<Transport>() {
      @Override
      public int compare(Transport t1, Transport t2) {
         return kolator.compare(dataTransNaString(t1.dataTransportu), dataTransNaString(t2.dataTransportu));
      }
   };

   // testy
   public static void main(String[] args) {

      Transport tr = new Transport(1, 2, 3, 4, 5);

      // System.out.println(tr.dataTransportu.toLocalTime().toString());
      // return;
      // System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(
      // ZonedDateTime.of(tr.dataTransportu.toLocalDate(),
      // tr.dataTransportu.toLocalTime(), ZoneId.of("Europe/Budapest"))
      // ));

      // LocalDate anotherSummerDay = LocalDate.of(2016, 8, 23);
      // LocalTime anotherTime = LocalTime.of(13, 12, 45);
      // ZonedDateTime zonedDateTime = ZonedDateTime.of(anotherSummerDay, anotherTime,
      // ZoneId.of("Europe/Helsinki"));

      LocalDate anotherSummerDay = tr.dataTransportu.toLocalDate();
      LocalTime anotherTime = tr.dataTransportu.toLocalTime();
      ZonedDateTime zonedDateTime = ZonedDateTime.of(anotherSummerDay, anotherTime, ZoneId.of("Europe/Helsinki"));

      System.out.println(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                  .format(zonedDateTime));
      System.out.println(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                  .format(zonedDateTime));
      System.out.println(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                  .format(zonedDateTime));
      System.out.println(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                  .format(zonedDateTime));
      /**/
   }
}