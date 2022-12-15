package application;

import Utilities.FileManager;
import onlineSearcher.Main;

public final class Launcher {

   private Launcher() { }
   public static void main(final String[] args) {
	   FileManager.InitializeDirectories();
       Main.main(args); 
   }
}