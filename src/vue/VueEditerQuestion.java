package vue;

import javax.swing.*;


import controleur.*;

import appli.metier.QuestionBinaire;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class VueEditerQuestion extends JDialog implements ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	
	private JButton bQuitter,bTester,bEnregistrer;
	private JTextField libelleQuestion,nbOui,nbNon;
	private JComboBox repPos,repNeg;
	private ControleurEditeurSondage ces;
	private QuestionBinaire qb;
	
	/**
	 * Construit une fen�tre modale ajoutant une question au sondage de la fen�tre principale.
	 * Permet �galement de tester la question en cours de cr�ation
	 * @param f fen�tre principale
	 */
	public VueEditerQuestion(String titre,ControleurEditeurSondage Ces){
		
		super();
		this.setModal(true);
		this.setTitle(titre);
	
		this.ces = Ces;
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setIconImage(new ImageIcon("Ressources/logo.png").getImage()); //Logo de l'iut en ic�ne :)
		
		//Position de la fen�tre sur l'ecran
		int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    //D�finition position / taille fen�tre
		this.setBounds(largeurEcran*3/8,hauteurEcran*3/8,450,300);
		this.setPreferredSize(new Dimension(450,300));
		
	
	
		this.setLayout(new GridLayout(7,2));
		
		//[0,0]
			this.add(new JLabel("Question"));
		//[0,1]
			this.libelleQuestion = new JTextField("Entrez ici le libell� de la question");
			this.libelleQuestion.selectAll();
			this.add(this.libelleQuestion);
		//[1,0]
			this.add(new JLabel("Libell� r�ponse positive"));
		//[1,1]	 Les templates de r�ponses positives
			Vector<String>vPos = new Vector<String>();
			vPos.add("Oui");
			vPos.add("Pour");
			vPos.add("Favorable");
			vPos.add("D'accord");
			vPos.add("Autre (saisir)");
			this.repPos = new JComboBox(vPos);
			this.repPos.setEditable(true);
			this.add(this.repPos);		
		//[2,0]
			this.add(new JLabel("Libell� r�ponse n�gative"));
		//[2,1] Les templates de r�ponses n�gatives
			Vector<String>vNeg = new Vector<String>();			
			vNeg.add("Non");
			vNeg.add("Contre");
			vNeg.add("D�favorable");
			vNeg.add("Pas d'accord");
			vNeg.add("Autre (saisir)");
			this.repNeg = new JComboBox(vNeg);
			this.repNeg.setEditable(true);
			this.add(this.repNeg);
		//[3,0]
			this.add(new JLabel("Nombre de r�ponses positives"));
		//[3,1]
			this.nbOui = new JTextField("0");
			this.add(this.nbOui);
		//[4,0]
			this.add(new JLabel("Nombre de r�ponses n�gatives"));
		//[4,1]
			this.nbNon = new JTextField("0");
			this.add(this.nbNon);
		//[5,0]
			this.bTester = new JButton("Tester la question");
			this.add(this.bTester);
		//[5,1]
			this.add(new Container());		
		//[6,0]
			this.bQuitter = new JButton("Quitter sans sauver");
			this.add(this.bQuitter);
		//[6,1]
			this.bEnregistrer = new JButton("Enregistrer la question");
			this.add(this.bEnregistrer);
		
		
		//Abonnement aux listeners
			this.bQuitter.addActionListener(this);
			this.bEnregistrer.addActionListener(this);
			this.bTester.addActionListener(this);
			
		//this.pack();
		
	}

	//Fonction de l'interface actionListener
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == bQuitter)
			this.quitter();			
		
		if(ae.getSource() == bTester)
			this.tester();
		
		if(ae.getSource() == bEnregistrer )
			this.enregistrer();	
		
	}
	

	/**
	 * Quitte la fen�tre si l'utilisateur le d�sire
	 */
	public void quitter(){
		 int retour = JOptionPane.showConfirmDialog(this,"Voulez vous vraiment quitter?", 
				 this.getTitle(),
				 JOptionPane.YES_NO_OPTION);  
		 
		 if(retour == JOptionPane.YES_OPTION)
			 this.dispose();
	}
	
	/**
	 * Teste la question en cours de cr�ation sans l'enregistrer dans le sondage
	 */
	public void tester(){
		
		if(isGood(0)){
			this.enregistrerQB();					
			this.ces.controleurTesterQBEnEdition(qb);
		}
		
	}
	
	/**
	 * Enregistre la question cr��e dans le sondage courant
	 */
	public void enregistrer(){
		
		if(isGood(1)){
			
			this.enregistrerQB();
			
			 int retour = JOptionPane.showConfirmDialog(this,"Voulez vous vraiment enregistrer? " ,
					 "Gestion de sondage",   
					 JOptionPane.YES_NO_OPTION);  
			 
			 if (retour == JOptionPane.YES_OPTION){
				 this.ces.controleurEnregistrerQB(qb);
				 this.dispose();
			 }
			 
		}
		
	}
	
	
	/**
	 * Enregistre la question courante dans une variable de type QuestionBinaire
	 * Pr�condition : isGood()
	 * 
	 */
	private void enregistrerQB() {
		
		qb = new QuestionBinaire(this.libelleQuestion.getText(), 
				convertionStringInt(this.nbOui.getText()),
				convertionStringInt(this.nbNon.getText()), 
				 this.repPos.getSelectedItem().toString(), 
				 this.repNeg.getSelectedItem().toString());
		
	}
	
	/**
	 * Effectue les contr�les n�c�ssaires sur les champs de saisies
	 *  /!\ Initialise �galement la valeur du nombre de r�ponses positives/n�gatives � 0 si elles ne sont pas pr�cis�es ou non valides( <0)
	 * @return true si les champs sont correctement saisis , false sinon
	 */
	private boolean isGood(int mode){
		
		boolean retour = false;
		if( 	   this.libelleQuestion.getText().length() == 0
				|| this.repPos.getSelectedItem().toString().length() == 0
				|| this.repNeg.getSelectedItem().toString().length() == 0 
				|| (mode == 1 && this.isLibelleDejaPris( libelleQuestion.getText()) )
				|| this.repPos.getSelectedItem().toString().toUpperCase().equals( this.repNeg.getSelectedItem().toString().toUpperCase() )  )	{
		//Si le libelle de la question/r�ponse positive/r�ponse n�gative n'est pas saisi ou que le libelle existe d�j�
			
					String message = null; // Forcement chang� apr�s , ne devrait jamais afficher null
					if( this.libelleQuestion.getText().length() == 0) // Si libelle question non saisi
						message = "Le libelle de la question doit �tre saisi";
					else					
						if( this.repPos.getSelectedItem().toString().length() == 0) // Si libelle r�ponse positive non saisi
							message = "La r�ponse positive doit �tre saisie";
						else
							if( this.repNeg.getSelectedItem().toString().length() == 0) // Si libelle r�pons� n�gative non saisi
								message = "La r�ponse n�gative doit �tre saisie";
							else
								if( mode == 1 && this.isLibelleDejaPris(libelleQuestion.getText()))
										message = "Le libell� '" + libelleQuestion.getText() + "' existe d�j� ";
								else
									if(this.repPos.getSelectedItem().toString().toUpperCase().equals( this.repNeg.getSelectedItem().toString().toUpperCase() ))
										message = "Les 2 r�ponses sont identiques !";
					
					
					JOptionPane.showMessageDialog(this,message, 
							 this.getTitle(),   
							 JOptionPane.YES_OPTION); 
									
			}else
			{
				
				this.isGood2();
				retour = true;
			}
				return retour;
	}
	
	
	private boolean isEntier(String msg)
	{
		boolean ok;

		try 
		{	  			
		    Integer.parseInt (msg);    
		    ok = true;
		}
		catch (NumberFormatException nfm) 
		{    // Traitement des erreurs
		    ok = false;
		}
		
		return ok;
	}
	
	private void isGood2()
	{
		if(this.nbOui.getText().length() == 0
				|| this.nbNon.getText().length() == 0
				|| this.convertionStringInt(this.nbOui.getText() ) < 0
				|| this.convertionStringInt(this.nbNon.getText()) < 0 
				|| !this.isEntier(this.nbOui.getText())
				|| !this.isEntier(this.nbNon.getText())){
				//Si le nombre de r�ponse positive/n�gative n'est pas �crit ou invalide
					
					String msg = "La r�ponse ";
					if(this.nbOui.getText().length() == 0 || this.convertionStringInt(this.nbOui.getText()) < 0 || !this.isEntier(this.nbOui.getText()))
					{
						msg += "positive";
						this.nbOui.setText("0");						
					}else
						if(this.nbNon.getText().length() == 0 || this.convertionStringInt(this.nbNon.getText()) < 0 || !this.isEntier(this.nbNon.getText()))
						{
							msg += "n�gative";
							this.nbNon.setText("0");
						}
					msg += " est incorrecte, elle est plac�e � 0";
					JOptionPane.showMessageDialog(this,msg,this.getTitle(),JOptionPane.OK_OPTION);
						
				}
	}
	
	/**
	 * Permet de comparer le libell� de la question dans la fen�tre courante avec les libell�s des questions du sondage
	 * @param s Libell� de la question � comparer
	 * @return true si le libelle de la question existe d�j� dans le sondage ,false sinon
	 */
	public boolean isLibelleDejaPris(String s){
		
		boolean retour = false;
		
		int taille = this.ces.getNbQuestionsSondage();
		for(int i = 0; i < taille && !retour; i++){
			if(s.equals(this.ces.getQuestionSondage(i).getQuestion()))
				retour = true;
		}
		
		return retour;
	}
	
	/**
	 * 
	 * @param valeur � convertir
	 * @return la valeur enti�re de la valeur pass�e en param�tre , 0 si la conversion a �chou�
	 */
	public int convertionStringInt(String valeur){
		
		int val = 0;
		boolean ok;

		try 
		{	  			
		    val = Integer.parseInt (valeur);    
		    ok = true;
		}
		catch (NumberFormatException nfm) 
		{    // Traitement des erreurs
		    ok = false;
		}
		if (!ok)  		
			return 0;		
		else
			return val;
		
	}

	
}
