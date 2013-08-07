package confort;

import java.io.File;
import javax.swing.filechooser.*;

public class MonFiltre extends FileFilter {
	
	  private String extension;
	  private String  description;
	
	   /**
	    * Etablit un filtre d'affichage sur les fichiers comportants une extention entrée en paramètre
	    * @param suffixe du fichier
	    * @param description du type de filtre
	    */
	   public MonFiltre(String suffixe,String description){
	      this.extension = suffixe;
	      this.description = description;
	   }
	   
	   /**
	    * Fonction de l'interface FileFilter
	    * @return true si le fichier porte l'extension entrée en paramètre , false sinon
	    */
	   public boolean accept(File file){

		   if(file.isDirectory()) { 
		         return true; 
		    } 

	      String nomFichier = file.getName().toLowerCase(); 

	      return nomFichier.endsWith(extension);
	   }

		//Fonction de l'interface FileFilter
		public String getDescription() {		
			return description;
	}
	      

	}