package vue;

//General
import java.awt.*;
import java.awt.event.*;

import java.util.Vector;

import javax.swing.*;

//Propre à l'application

import controleur.ControleurPasseurSondage;



/**
 * 
 * Lance une fenêtre permettant d'enregistrer les réponses d'un sondé à une serie de questions
 *
 */
public class VueSoumettreSondage extends JFrame implements ActionListener,WindowListener{

	
	private static final long serialVersionUID = 1L;
	
	
	private JLabel titreS,nbPos,nbNeg;
	private JButton saisirR,loadS,saveS,initS,quitter;
	private JList listeS;
	private ControleurPasseurSondage cps;
	

	/**
	 * 
	 * @param _cps Contrôleur
	 */
	public VueSoumettreSondage(ControleurPasseurSondage _cps){
				
		
		super("Soumettre un sondage");
		

		
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		//Position de la fenêtre sur l'ecran
		int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    //Définition position / taille fenêtre
		this.setBounds(largeurEcran*3/8,hauteurEcran*3/8,450,300);
		this.setPreferredSize(new Dimension(450,300));
		
		this.setIconImage(new ImageIcon("Ressources/logo.png").getImage()); //Logo de l'iut en icône :)
		
		
		//Look&Feel
		try  // Gestion des exceptions éventuelles pour le Look&Feel
		{
		   // Choix du Look&Feel Natif :
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		}catch (Exception e)
		{
		   // Dans le cas où l'exception est levée, cela est généralement du
		   // au fait que la classe de Look&Feel n'a pas été trouvée.
		   // Réessayer en vérifiant l'orthographe du paramètre d'appel de 
		   // setLookAndFeel.
			System.out.println("Mauvais look&feel employé");
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.MotifLookAndFeel");
			}catch(Exception e1)
			{
				System.out.println(e1);
			}
			
			
		}
		
		//Label constants
		JLabel kTitreS   = new JLabel("Résultats de : ");
		JLabel kNbRepPos = new JLabel("Réponses Positives : ");
		JLabel kNbRepNeg = new JLabel("Réponses Négatives : ");
		
		//Contrôleur
		this.cps = _cps;
		
		//Boutons
		this.saisirR = new JButton("Saisir réponses d'un sondé");
		this.loadS 	 = new JButton("Charger autre sondage");
		this.saveS   = new JButton("Enregistrer sondage courant");
		this.initS   = new JButton("Remettre sondage à zéro");
		this.quitter = new JButton("Quitter");
		
		
		//JLabel
		this.titreS  = new JLabel("NC");
		this.nbPos   = new JLabel("NC");
		this.nbNeg   = new JLabel("NC");
		
		//Liste + ScrollPane
			this.listeS  = new JList();
			this.listeS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			
			
			JScrollPane js = new JScrollPane ();
		    js.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    js.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		    js.setViewportView(this.listeS); // On l'incorpore dans la JListe
		    
			
		//Panel de la zone nord (constante de titre + titre du sondage)
			JPanel nordTitres = new JPanel();
			nordTitres.add(kTitreS);
			nordTitres.add(this.titreS);

		
		//Panel d'une partie de la zone sud (Nb Rep Pos : this.nbPos)
			JPanel tmpRepPos = new JPanel();
			tmpRepPos.add(kNbRepPos);
			tmpRepPos.add(this.nbPos);
		
		//Panel d'une partie de la zone sud (Nb Rep Neg : this.nbNeg)
			JPanel tmpRepNeg = new JPanel();
			tmpRepNeg.add(kNbRepNeg);
			tmpRepNeg.add(this.nbNeg);
		
		//Panel de la zone sud( = 2 panels d'avant + les 5 boutons)
			
			JPanel sudBoutons = new JPanel(new GridLayout(4,2)); // 4 Lignes 2Colones : [0,1]->[3,1]
			
			
			sudBoutons.add(tmpRepPos);		// [0,0]
			sudBoutons.add(tmpRepNeg);		// [0,1]			
			sudBoutons.add(this.saisirR);	// [1,0]		
			sudBoutons.add(this.initS);		// [1,1]
			sudBoutons.add(this.saveS);		// [2,0]		
			sudBoutons.add(this.loadS);		// [2,1]		
			sudBoutons.add(this.quitter);	// [3,0]
			//[3,1]  Vide
			
			
		//Panels sur la fenêtre principale 
			this.setLayout(new BorderLayout());
			this.add(nordTitres,	BorderLayout.NORTH);
			this.add(js,			BorderLayout.CENTER);
			this.add(sudBoutons,	BorderLayout.SOUTH);
			
		
		//Abonnement aux listeners :)
			this.quitter.addActionListener(this);
			this.saisirR.addActionListener(this);
			this.initS.addActionListener(this);
			this.loadS.addActionListener(this);
			this.saveS.addActionListener(this);
			this.addWindowListener(this);
			
		
			
	}
	
	
	public void actualiserListe(int index) {
		this.listeS.updateUI(); 
		ControleurPasseurSondage.test(this.listeS,0);
		if(index == 0)
			this.listeS.setSelectedIndex(0);
		if(index >= 1)
			this.listeS.setSelectedIndex(index -1);
		
	}


	//Fonction de l'interface ActionListener
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == this.quitter){
			this.quitter();
		}
		
		if(ae.getSource() == this.saisirR){
			if(this.cps.getNbQuestionsSondage() <= 0){
				JOptionPane.showMessageDialog(this,"Aucune question à répondre", 
						this.getTitle(),   
					 JOptionPane.PLAIN_MESSAGE);  
			}else
			this.cps.controleurSaisirReponsesUnInterroge();
		}
			
		
		if(ae.getSource() == this.initS)
			this.cps.controleurInitialiserSondage();
		
		if(ae.getSource() == this.loadS){
			this.cps.controleurChargerAutreSondage();
			this.actualiserVue();
		}
		
		
		if(ae.getSource() == this.saveS){
			this.cps.controleurSauvegarderSondage();
		}
	}
	
private void quitter() {
		this.cps.controleurQuitterPasseurSondage();
		
	}


public void actualiserSondage(){
		
		
		Vector<String> v = new Vector<String>();
		
		int taille =  this.cps.getNbQuestionsSondage();
	
		
		
		//Extraction des infos du sondage dans le vecteur 
		for(int i = 0; i < taille ;i++){
			v.add(this.cps.getQuestionSondage(i).getQuestion() 
				+ "(" 
				+ this.cps.getQuestionSondage(i).getLibOui()
				+ "/"
				+ this.cps.getQuestionSondage(i).getLibNon()
				+ ")-("
				+ this.cps.getQuestionSondage(i).getNbOui()
				+ "/"
				+ this.cps.getQuestionSondage(i).getNbNon()
				+ ")" 
				);
	
			//Question? (nbOui/nbNon)-(libelleOui/libelleNon)
		}			
		
		
		this.listeS.setListData(v);
		
		if(taille > 0)
			this.listeS.setSelectedIndex(0);
		this.titreS.setText(this.cps.getLibelleSondage());
		this.nbPos.setText(""+this.getTotalNbOui());
		this.nbNeg.setText(""+this.getTotalNbNon());
		}

	public int getTotalNbOui(){
		int taille = this.cps.getNbQuestionsSondage();
		int nbQ = 0;
		for(int i = 0;i < taille ; i++){
			nbQ+= this.cps.getQuestionSondage(i).getNbOui();
		}
		return nbQ;
	}
	
	public int getTotalNbNon(){
		int taille = this.cps.getNbQuestionsSondage();
		int nbQ = 0;
		for(int i = 0;i < taille ; i++){
			nbQ+= this.cps.getQuestionSondage(i).getNbNon();
		}
		return nbQ;
	}
	
	
	public boolean isTitreSOk(){
		return !(this.titreS.getText().length() == 0);
	}
	
	//Fonction de l'interface WindowListener	
	public void windowClosing(WindowEvent arg0) {
		this.quitter();
	}
	
	public void actualiserVue(){
		if(this.cps.getNbQuestionsSondage() == 0 || this.listeS.getSelectedIndex() < 0)
		{
			this.saisirR.setEnabled(false);
			this.saveS.setEnabled(false);
			this.initS.setEnabled(false);
			
		}
			
		else{
			this.saisirR.setEnabled(true);
			this.saveS.setEnabled(true);
			this.initS.setEnabled(true);
		}
	}
	
	public void windowOpened(WindowEvent arg0) {
		this.actualiserVue();
	}
	// Reste des fonctions non implémentées des interface WindowListener et MouseListener
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}

}

