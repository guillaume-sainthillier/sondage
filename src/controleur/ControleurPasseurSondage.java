package controleur;

import javax.swing.*;


//Propres à l'application
import confort.*;
import vue.*;
import appli.metier.*;
import appli.persistance.GestionSauvegardes;

public class ControleurPasseurSondage {

	private Sondage s;
	private VueSoumettreSondage f;
	private JFrame pere;
	
	public ControleurPasseurSondage(JFrame pere){
		this.s = new Sondage("Satisfaction RU ");
        this.s.addQuestion(new QuestionBinaire("Aimez vous les repas ? ", 		 1, 2, "Oui"		    , "Non"));
        this.s.addQuestion(new QuestionBinaire("Souhaiteriez vous plus choix",   1, 2, "Pourquoi pas"   , "Suffisant"));
        this.s.addQuestion(new QuestionBinaire("Des légumes tous les jours ...", 1, 2, "C'est barbant"  , "Tant mieux"));
        this.s.addQuestion(new QuestionBinaire("Globalement, vous êtes : ", 	 1, 2, "Satisfait"		, "Pas satisfait"));
        
        this.f = null;
        this.pere = pere;
	}
	
	public void demarrerControleurPasseurSondage(){
		if(this.f == null){
			this.f = new VueSoumettreSondage(this);
			this.f.actualiserSondage();
			this.f.actualiserListe(0);
			this.f.setVisible(true);
			this.pere.setVisible(false);
		}
	}
	
	public String getLibelleSondage(){
		return this.s.getLibSondage();
	}
	
	public int getNbQuestionsSondage(){		
		return this.s.getNbQuestions();
	}
	
	public QuestionBinaire getQuestionSondage(int _index){
		return this.s.getQuestion(_index);
	}
	
	/**
	 * Sauvegarde le sondage en mémoire dans un fichier avec une auto-extention (.don) si aucune extension n'est précisée
	 */	
	public void controleurSauvegarderSondage(){
		
		if(!this.f.isTitreSOk())
		{
			JOptionPane.showMessageDialog(this.f,"Le titre du sondage doit être inscrit", 
					 this.f.getTitle(),   
					 JOptionPane.OK_OPTION);  
			
		}else
		{
			JFileChooser choix = new JFileChooser();
			
			choix.addChoosableFileFilter(new MonFiltre(".don","Fichiers de sondage (.don)"));		
			choix.setApproveButtonText("Enregistrer") ; 
			choix.setApproveButtonToolTipText ("Enregistrer un fichier de sondage"); 
			choix.setDialogTitle("Enregistrer fichier de sondage");
			choix.setFileSelectionMode (JFileChooser.FILES_ONLY);
			
			
			// Utilisation		
			int returnVal = 0;
			
			int retour ;
			 boolean bonDeroulement = false;
			 
			
			 while(!bonDeroulement){
				 
					// Affichage du dialogue en mode modal (jusqu’à fermeture par l’utilisateur)
					returnVal = choix.showSaveDialog(null);		
				      if(returnVal == JFileChooser.APPROVE_OPTION && choix.getSelectedFile().exists()){
				    	  
					       retour = JOptionPane.showConfirmDialog(
					               null, choix.getSelectedFile().getName() + " existe déjà.\nVoulez-vous le remplacer?", "Confirmer l'enregistrement",
					                JOptionPane.YES_NO_OPTION);
					      
					      if(retour == JOptionPane.YES_OPTION)
					    	  bonDeroulement = true;
				      }else
				    	  bonDeroulement = true;
				     
					}
		
			 
				// … l’utilisateur a-t-il cliqué sur "Enregistrer" ?
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					    	  String nomFic = choix.getSelectedFile().getAbsolutePath();
					    	
					    	  int indexP = choix.getSelectedFile().getName().indexOf('.');
								if (indexP == -1) //Si aucune extention n'est précisée
									nomFic = nomFic + ".don";
								else
								{								
									String extension = nomFic.substring(nomFic.length()-4, nomFic.length());
									if(!extension.equals(".don"))
										nomFic = nomFic + ".don";																	
					
								}
					    	  
								if(!GestionSauvegardes.enregistreSondage(nomFic, this.s)){
									JOptionPane.showMessageDialog(this.f,"La sauvegarde du fichier a echoué !", 
											 this.f.getTitle(),   
											 JOptionPane.OK_OPTION); 
									
								}	      
			      }		
			}
	}
	
	public void controleurChargerAutreSondage(){
		

		JFileChooser choix = new JFileChooser();
		// Paramétrage
			//Filtre pour les fichiers à afficher dans le JFileChooser
				choix.addChoosableFileFilter(new MonFiltre(".don","Fichiers de sondage (.don)"));
				
			choix.setApproveButtonText("Ouvrir") ; 
			choix.setApproveButtonToolTipText ("Ouvrir un fichier de sondage"); 
			choix.setDialogTitle("Ouvrir fichier de sondage");
			choix.setFileSelectionMode (JFileChooser.FILES_ONLY);

		
		// Utilisation		
		int returnVal ;
		
		// Affichage du dialogue en mode modal (jusqu’à fermeture par l’utilisateur)
		returnVal = choix.showOpenDialog(null);

		// … l’utilisateur a-t-il cliqué "Ouvrir" ?
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
		
			
			Sondage s = GestionSauvegardes.chargeSondage(choix.getSelectedFile().getAbsolutePath()); // On charge en mémoire le contenu du fichier
			
			if(s == null){
				JOptionPane.showMessageDialog(null,"L'ouverture du fichier de sondage a échoué", 
						 this.f.getTitle(),   
						 JOptionPane.OK_OPTION);  
			}else{
				this.s = s;
				this.f.actualiserSondage(); // On actualise la JListe de la fenêtre principale
			}
			
		}
	}
	
	
	public void controleurInitialiserSondage(){
		int retour = JOptionPane.showConfirmDialog(this.f,"Ceci va remettre à zero le sondage courant, continuer?","Réinitialiser sondage",JOptionPane.YES_NO_OPTION);
		if(retour == JOptionPane.YES_OPTION)
		{
			this.s.setReinitialise();
			this.f.actualiserSondage();
			this.f.actualiserListe(0);
		}

	}
	
	public void controleurQuitterPasseurSondage(){
		 int retour = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment quitter?", 
					this.f.getTitle(),   
				 JOptionPane.YES_NO_OPTION);  
		
	
		 if(retour == JOptionPane.YES_OPTION){
			 this.f.dispose();
			 this.pere.setVisible(true);
		 }
		
	
	}
	
	public static void test(JList liste, int _index){
		liste.updateUI();
	}
	
	public void controleurSaisirReponsesUnInterroge(){
		
		int taille =  this.getNbQuestionsSondage() , i = 0;
		
		
		QuestionBinaire tabQB[] = new QuestionBinaire[taille];  
		
		
			VueReponseQuestionBinaire vrqb;
		do{
			//A UNE question dans le sondage est associée UNE VueReponseQB.
			//De plus le tableau de QuestionBinaire temporaire reprend le résultat du sondé et l'enregistre 
			//Dans un NOUVEAU sondage SI l'utilisateur a entré toutes les réponses
			
			vrqb = new VueReponseQuestionBinaire("Question " + (i+1) + "/" +taille,this.getQuestionSondage(i)); 
			vrqb.setVisible(true);
			
			tabQB[i] = vrqb.getQuestion(); // idRef de questionTMP contenu dans VRQB
			i++;
			
		}while(i < taille && !(vrqb.getAQuitte() )); //vrqb.getAQuitte() faut true lorsque l'utilisateur
		//a quitté une question 
		
		
		
		if(!vrqb.getAQuitte()) // -> S'il a répondu à toutes les questions
		{
			 int retour = JOptionPane.showConfirmDialog(null,"Toutes les réponses ont été saisi.\n Voulez-vous enregistrer? ", 
					 "Gestion de sondage",   
					 JOptionPane.YES_NO_OPTION);  
			
			 if(retour == JOptionPane.YES_OPTION){
				 //On recrée le sondage à partir du tableau temporaire (qui reprends les anciennes réponses + la dernière en date)
				 
				 this.s = new Sondage(this.s.getLibSondage());
				 for(i = 0 ; i < taille ; i++){
					 this.s.addQuestion(tabQB[i]);
				 }
			 }
		}
		
		 this.f.actualiserSondage();
		 this.f.actualiserListe(0);

	}
}
