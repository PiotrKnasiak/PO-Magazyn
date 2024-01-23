package ProjektMagazyn;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.Serializable;

public class DaneLogowania implements Comparable<DaneLogowania>, Serializable {
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

   public DaneLogowania(int ID, String login, String haslo, boolean prank) { // bez dodawania
      this.ID = ID;
      this.login = login;
      this.haslo = haslo;
   }

   int ID = 0;
   String login = "test";
   String haslo = "Test";

   public static ArrayList<DaneLogowania> listaDanychLog = new ArrayList<>();

   public static int sprawdzLogwanie(String login, String haslo) {
      for (int i = 0; i < listaDanychLog.size(); i++) {
         if (listaDanychLog.get(i).login.compareTo(login) == 0) {
            if (listaDanychLog.get(i).haslo.compareTo(haslo) == 0) {
               return MyPanel.SUKCES;
            }

            return MyPanel.BLAD;
         }
      }

      return MyPanel.PORAZKA;
   }

   public static int sprawdzLogin(String login) {
      for (int i = 0; i < listaDanychLog.size(); i++) {
         if (listaDanychLog.get(i).login.compareTo(login) == 0) {
            return MyPanel.SUKCES;
         }

      }

      return MyPanel.PORAZKA;
   }

   public static int sprawdzHaslo(String haslo) {
      for (int i = 0; i < listaDanychLog.size(); i++) {
         if (listaDanychLog.get(i).haslo.compareTo(haslo) == 0) {
            return MyPanel.SUKCES;
         }
      }

      return MyPanel.PORAZKA;
   }

   public static int ostatnieID() {
      int rozmiar = listaDanychLog.size();

      if (rozmiar > 0) {
         listaDanychLog.sort(porownajID);
         return listaDanychLog.get(rozmiar - 1).ID;
      }

      return 0;
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

   @Override
   public String toString() {
      return ("\t Login: " + this.login + "\n\tHasło: " + this.haslo + "\n");
   }
}