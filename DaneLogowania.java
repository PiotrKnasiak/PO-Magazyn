package ProjektMagazyn;

import java.util.ArrayList;
import java.util.Comparator;

public class DaneLogowania implements Comparable<DaneLogowania> {
   public DaneLogowania() {
      this.ID = -2;

      listaDanychLog.add(this);
   }

   public DaneLogowania(int ID, String login, String haslo) {
      this.ID = ID;
      this.login = login;
      this.haslo = haslo;

      listaDanychLog.add(this);
   }

   int ID = 0;
   String login = "test";
   String haslo = "Test";

   private static ArrayList<DaneLogowania> listaDanychLog = new ArrayList<>();

   public static int sprawdzLogwanie(String login, String haslo) {
      for (int i = 0; i < listaDanychLog.size(); i++) {
         if (listaDanychLog.get(i).login == login) {
            if (listaDanychLog.get(i).haslo == haslo) {
               return MyPanel.SUKCES;
            }

            return MyPanel.BLAD;
         }
      }

      return MyPanel.PORAZKA;
   }

   public static Comparator<DaneLogowania> porownajID = new Comparator<DaneLogowania>() {
      @Override
      public int compare(DaneLogowania dl1, DaneLogowania dl2) {
         int roznica = dl1.ID - dl2.ID;
         if (roznica < 0)
            return -1;
         if (roznica > 0)
            return 1;
         System.out.println("\n\n*** Error!\n\tDuplikat ID w danych logowania!\n\n");
         return 0;
      }
   };

   @Override
   public int compareTo(DaneLogowania dl) {
      return kolator.compare(this.ID, dl.ID);
   }
}