package vue;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



import appli.metier.QuestionBinaire;

/**
 * 
 * Cette classe redefinit juste l'action sur un bouton d'une des deux réponses binaires de la classe VueQuestionBinaire ,
 * sur un objet de type QuestionBinaire
 *
 */

public class VueReponseQuestionBinaire extends VueQuestionBinaire {

	private static final long serialVersionUID = 1L;
	private QuestionBinaire laQbTmp = null;
	private boolean aQuitte ;
	
	/**
	 * 
	 * @param _t Titre de la fenêtre
	 * @param _qb Question Binaire à tester
	 */
	public VueReponseQuestionBinaire(String _t, QuestionBinaire _qb) {
		super(_t, _qb);		
		
		this.laQbTmp = new QuestionBinaire(super.getQuestion().getQuestion(),
										   super.getQuestion().getNbOui(),
										   super.getQuestion().getNbNon(),
										   super.getQuestion().getLibOui(),
										   super.getQuestion().getLibNon());
		
		this.setBoutonQuitter("Abandonner saisie");
		this.aQuitte = false;
		this.setIconImage(new ImageIcon("Ressources/logo.png").getImage()); //Logo de l'iut en icône :)
	}
	
	//Fonction de l'interface actionListener
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == this.getBoutonOui() )
		{
			 this.laQbTmp.enregOui();
				this.setVisible(false);
		}
		
		if(ae.getSource() == this.getBoutonNon() )
		{
			 this.laQbTmp.enregNon();
			 this.setVisible(false);
		}
		
		if(ae.getSource() == this.getBoutonQuitter() )
			this.quitter();

	}
		
	public void quitter(){
		int retour = JOptionPane.showConfirmDialog(this,"Abandonner la saisie des réponses du sondage?", 
				 "Gestion de sondage",   
				 JOptionPane.YES_NO_OPTION);  
		
		if(retour == JOptionPane.YES_OPTION)	
		{
			this.aQuitte = true;
			this.setVisible(false);
		}
	}

	public QuestionBinaire getQuestion(){
		return this.laQbTmp;
	}
	
	public boolean getAQuitte(){
		return this.aQuitte;
	}

}
