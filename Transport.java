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

public class Transport implements Comparable<Transport>{
   public static enum stanyTransportu {
      IMPORT,
      EKSPORT
   }

   public static String stanyNaString(stanyTransportu sT) {
      if(sT == stanyTransportu.EKSPORT)
         return "Przywóz do magazynu";
      return "Wywóz z magazynu";
   }

   public static String dataTransNaString(LocalDateTime dt) {
      LocalDate data = dt.toLocalDate();
      LocalTime czas = dt.toLocalTime();

      ZonedDateTime zonedDateTime = ZonedDateTime.of(data, czas, ZoneId.of("Europe/Helsinki"));
      String dataICzas = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime);
      
      return dataICzas;
   }
   public static LocalDateTime stringNaDataTrans(int rok, int miesiac,  int dzien, int godzina, int minuta) {

      LocalDateTime ldt = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);

      return ldt;
   }

   public Transport() {
      this.ID = -2;
      listaTransportow.add(this);
   }

   public Transport(int rok, int miesiac,  int dzien, int godzina, int minuta) {
      this.ID = -1;
      this.nazwaTowaru = "konstrukor_testDaty";
      this.dataTransportu = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);
      listaTransportow.add(this);
   }

   public Transport(int ID, String nazwaTowaru, int rok, int miesiac,  int dzien, int godzina, int minuta, stanyTransportu import_eksport) {
      this.ID = ID;
      this.nazwaTowaru = nazwaTowaru;
      this.dataTransportu = LocalDateTime.of(rok, miesiac, dzien, godzina, minuta, 0);
      this.import_eksport = import_eksport;

      listaTransportow.add(this);
   }

   int ID = 0;
   String nazwaTowaru = "Brak nazwy";
   LocalDateTime dataTransportu = LocalDateTime.now().plus(1,ChronoUnit.YEARS);
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
         if (t1.ID > t2.ID) return 1;
         if (t1.ID < t2.ID) return -1;
         return 0;
      }
   };
   static Comparator<Transport> porownajData = new Comparator<Transport>() {
      @Override
      public int compare(Transport t1, Transport t2) {
         return kolator.compare(t1, t2);
      }
   };

   // testy
   public static void main(String[] args) {
      /*
      Transport tr = new Transport(1,2,3,4,5);

      //System.out.println(tr.dataTransportu.toLocalTime().toString());
      //return;
      //System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(
      //   ZonedDateTime.of(tr.dataTransportu.toLocalDate(), tr.dataTransportu.toLocalTime(), ZoneId.of("Europe/Budapest"))
      //   ));

      
      //LocalDate anotherSummerDay = LocalDate.of(2016, 8, 23);
      //LocalTime anotherTime = LocalTime.of(13, 12, 45);
      //ZonedDateTime zonedDateTime = ZonedDateTime.of(anotherSummerDay, anotherTime, ZoneId.of("Europe/Helsinki"));

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
      */
   }
}