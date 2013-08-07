package vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controleur.*;

/**
 * 
 * Exercice3. Lance une fen�tre permettant de lancer soit le passeur sondage soit l'�diteur sondage
 *
 */
public class VuePasseurEditeurSondage extends JFrame implements ActionListener , WindowListener{
	
	
	private static final long serialVersionUID = 1L;
	private JButton bSoumettre,bEditer;
	JMenuBar menu;
	private JMenu aPropos,fichier;
	private JMenuItem aPropos_Auteur, fichier_Quitter,fichier_Soumettre,fichier_Editer;
	private static double version = 1.2;
	
	
	
	public VuePasseurEditeurSondage(){
		

		super("Sondage Gest' v."+version);
			
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //On laisse le traitement pour le listener
		
		
		//Position de la fen�tre sur l'ecran
			int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
			int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    //D�finition position / taille fen�tre
			this.setBounds(largeurEcran*3/8,hauteurEcran*3/8 -75,300,100);
			this.setPreferredSize(new Dimension(300,100));
		
		this.setIconImage(new ImageIcon("Ressources/logo.png").getImage()); //Logo de l'iut en ic�ne :)
		
		
		//Boutons 
			this.bEditer = new JButton("Editer un sondage");			
			this.bSoumettre = new JButton("Soumettre sondage � un sond�");
		
		JPanel boutons = new JPanel(new GridLayout(2,0));
		boutons.add(this.bEditer); //[0,0]
		boutons.add(this.bSoumettre); //[1,0]

		//Look&Feel
		try  // Gestion des exceptions �ventuelles pour le Look&Feel
		{
		   // Choix du Look&Feel Natif :
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		}catch (Exception e)
		{
		   // Dans le cas o� l'exception est lev�e, cela est g�n�ralement du
		   // au fait que la classe de Look&Feel n'a pas �t� trouv�e.
		   // R�essayer en v�rifiant l'orthographe du param�tre d'appel de 
		   // setLookAndFeel.
			System.out.println("Mauvais look&feel employ�");
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.MotifLookAndFeel");
			}catch(Exception e1)
			{
				System.out.println(e1);
			}
		}
		
		//MenuBar
			menu = new JMenuBar();
			
	    //Menu
			this.aPropos = new JMenu("?");
			this.aPropos.setMnemonic('?');
			
			this.fichier = new JMenu("Fichier");
			this.fichier.setMnemonic('F');
			
		//Sous menus
			
			//Fichier
			this.fichier_Quitter = new JMenuItem("Quitter");
			this.fichier_Quitter.setMnemonic('Q');
			
			this.fichier_Editer = new JMenuItem("Editer un sondage");
			this.fichier_Editer.setMnemonic('E');
			
			this.fichier_Soumettre = new JMenuItem("Soumettre un sondage");
			this.fichier_Soumettre.setMnemonic('S');
			
			this.fichier.add(this.fichier_Editer);
			this.fichier.add(this.fichier_Soumettre);
			this.fichier.add(this.fichier_Quitter);				
			
			//A propos
			this.aPropos_Auteur = new JMenuItem("� propos");	
			this.aPropos_Auteur.setMnemonic('A');
			this.aPropos.add(this.aPropos_Auteur);

			
			
		//MenuBar -> Fichier, ?
			menu.add(this.fichier);
			menu.add(this.aPropos);
    
    	
    	//Ajout de la barre de menus sur la fen�tre
    	this.setJMenuBar(menu);

		//Mise en place sur la main frame
		this.add(boutons);
	

		//Abonnement aux listeners :)
		this.addWindowListener(this);
    	this.bEditer.addActionListener(this);
		this.bSoumettre.addActionListener(this);
    	this.fichier_Quitter.addActionListener(this);
    	this.fichier_Editer.addActionListener(this);
    	this.fichier_Soumettre.addActionListener(this);
    	this.aPropos_Auteur.addActionListener(this);

    	
    	this.pack();
	}

	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == this.bEditer    || ae.getSource() == this.fichier_Editer)
			this.editer();
		
		if(ae.getSource() == this.bSoumettre || ae.getSource() == this.fichier_Soumettre)
			this.soumettre();
		
		if(ae.getSource() == this.aPropos_Auteur)
			this.auteur();	
		
		if(ae.getSource() == this.fichier_Quitter)
			this.quitter();
		
	}
	
	public void soumettre(){
		ControleurPasseurSondage masterCps = new ControleurPasseurSondage(this);
		masterCps.demarrerControleurPasseurSondage();
	
	}
	
	public void editer(){
		ControleurEditeurSondage masterCes;
		masterCes = new ControleurEditeurSondage(this);
		masterCes.demarrerControleurEditeurSondage();
		
	}
	
	public void auteur(){
		
		JOptionPane.showMessageDialog(this,"Version "+VuePasseurEditeurSondage.version +"\n" +
									       "Auteur : SAINTHILLIER Guillaume\n " +
									       "Mail : guillaume.sainthillier@univ-tlse2.fr", 
									       "A propos",JOptionPane.PLAIN_MESSAGE); 
	}
	
	public void quitter(){
		 int retour = JOptionPane.showConfirmDialog(this,"Voulez vous vraiment quitter?", 
					this.getTitle(),   
				 JOptionPane.YES_NO_OPTION);  
		 
	
		 if(retour == JOptionPane.YES_OPTION){
			 System.exit(0);
		 }
	}
	
	
	//Fonction de l'interface WindowListener	
	public void windowClosing(WindowEvent arg0) {
		this.quitter();
	}
	
	
	// Reste des fonctions non impl�ment�es des interface WindowListener et MouseListener
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	
	

}
