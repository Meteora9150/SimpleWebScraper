package application;

import onlineSearcher.Main;

/*
* This class represents the Launcher of the system, to bypass JAVA 11 modules constraints.
*/
public final class Launcher {

   private Launcher() { }
   public static void main(final String[] args) {
       Main.main(args);
   }
}