package controleur;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import confort.MonFiltre;

import vue.*;
import vue.VueQuestionBinaire;
import appli.metier.*;
import appli.persistance.GestionSauvegardes;

public class ControleurEditeurSondage {

	private VueEditionSondage fen;
	private JFrame pere;
	private Sondage s;
	public ControleurEditeurSondage(JFrame pere)
	{
		this.fen = null;
		this.pere = pere;
		this.s = new Sondage("La bouffe au RU");
		this.s.addQuestion(new QuestionBinaire("Aimez vous les repas ? ", 0, 0, "Oui", "Non"));
        this.s.addQuestion(new QuestionBinaire("Souhaiteriez vous plus choix", 0, 0, "Pourquoi pas", "Suffisant"));
        this.s.addQuestion(new QuestionBinaire("Des légumes tous les jours ...", 0, 0, "C'est barbant", "Tant mieux"));
        this.s.addQuestion(new QuestionBinaire("Globalement, vous êtes : ", 0, 0, "Satisfait", "Pas satisfait"));
	}
	public void demarrerControleurEditeurSondage()
	{
		if(this.fen == null)
		{
			this.fen = new VueEditionSondage("Sondage",this);
			this.fen.setVisible(true);
			this.fen.majVue();
			this.fen.majListe(0);
			this.fen.majBoutons();
			this.pere.setVisible(false);
			
		}
	}
	public String getLibelleSondage()
	{
		return this.s.getLibSondage();
	}
	public int getNbQuestionsSondage()
	{
		return this.s.getNbQuestions();
	}	
	public QuestionBinaire getQuestionSondage(int pIndex)
	{
		return new QuestionBinaire (
			this.s.getQuestion(pIndex).getQuestion(),
			this.s.getQuestion(pIndex).getNbOui(),
			this.s.getQuestion(pIndex).getNbNon(),
			this.s.getQuestion(pIndex).getLibOui(),
			this.s.getQuestion(pIndex).getLibNon()
		);
	}
	public void setLibelleSondage(String pNom)
	{
		this.s.setLibSondage(pNom);
	}
	
	public void controleurSauvegarderSondage()
	{
		if(this.fen.getTitreS().getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this.fen, "Le titre du sondage est vide",this.fen.getTitle(),JOptionPane.OK_OPTION);
		}else
		{
			JFileChooser menu = new JFileChooser();
			menu.setApproveButtonText("Enregistrer");
			menu.setDialogTitle(this.fen.getTitle());
			menu.setFileSelectionMode (JFileChooser.FILES_ONLY);
			
			int retour = menu.showOpenDialog(null);
			if(retour == JFileChooser.APPROVE_OPTION) 
			{
				this.setLibelleSondage(this.fen.getTitreS().getText());
				String nomFic = menu.getSelectedFile().getAbsolutePath();
				if(!nomFic.endsWith(".don"))
					nomFic += ".don";
				GestionSauvegardes.enregistreSondage(nomFic,this.s);
				this.fen.majBoutons();
			}
		}
	}
	public void controleurChargerUnSondage()
	{
		JFileChooser menu = new JFileChooser();
		menu.addChoosableFileFilter(new MonFiltre(".don","Fichiers de sondage (.don)"));
		menu.setApproveButtonText("Charger");
		menu.setDialogTitle(this.fen.getTitle());
		menu.setFileSelectionMode (JFileChooser.FILES_ONLY);
		
		int retour = menu.showOpenDialog(null);
		if(retour == JFileChooser.APPROVE_OPTION) 
		{
			Sondage s  = GestionSauvegardes.chargeSondage(menu.getSelectedFile().getAbsolutePath());
			if(s == null)
			{
				JOptionPane.showMessageDialog(this.fen, "Le sondage n'a pu être chargé");
			}else
			{
				this.s = s;
				this.fen.majVue();	
				this.fen.majListe(0);
				this.fen.majBoutons();
			}
		}

	}
	public void controleurNouveauSondage()
	{
		int retour = JOptionPane.showConfirmDialog(this.fen, "Ceci va effacer le sondage courant, continuer?", this.fen.getTitle(), JOptionPane.YES_NO_OPTION);
		if(retour == JOptionPane.YES_OPTION)
		{
			this.s = new Sondage("");
			this.fen.majVue();
			this.fen.majListe(0);
			this.fen.majBoutons();
		}
	}
	public void controleurSupprimerQuestion(int pIndex)
	{
		int retour = JOptionPane.showConfirmDialog(this.fen, "Voulez-vous vraiment supprimer cette question? ",this.fen.getTitle(),JOptionPane.YES_NO_OPTION);
		if(retour == JOptionPane.YES_OPTION)
		{
			if(!this.s.removeQuestion(this.s.getQuestion(pIndex)))
			{
				JOptionPane.showMessageDialog(this.fen,"La suppression de la question a echoué", 
						 this.fen.getTitle(),   
						 JOptionPane.OK_OPTION);  
			}else
			{
				this.fen.majVue();
				this.fen.majListe(pIndex-1);
				this.fen.majBoutons();
			}
		}
	}
	public void controleurAjouterQuestion()
	{
		VueEditerQuestion veq =  new VueEditerQuestion("Nouvelle question",this);
		veq.setVisible(true);
		this.fen.majBoutons();
	}
	public void controleurArreterEditionSondage()
	{
		int retour = JOptionPane.showConfirmDialog(this.fen,"Voulez-vous vraiment quitter ?",this.fen.getTitle(),JOptionPane.YES_NO_OPTION);
		if(retour == JOptionPane.YES_OPTION)
		{
			this.fen.dispose();
			this.fen = null;
			this.pere.setVisible(true);
		}
	}
	public void controleurTesterQBDepuisEditionSondage(QuestionBinaire pQuestion)
	{
		QuestionBinaire qb = new QuestionBinaire(pQuestion.getQuestion(),
				pQuestion.getNbOui(),
				pQuestion.getNbNon(),
				pQuestion.getLibOui(),
				pQuestion.getLibNon());
		
		VueQuestionBinaire v = new VueQuestionBinaire(pQuestion.getQuestion() ,
				qb,VueQuestionBinaire.DISPOSE);
				v.setVisible(true);
	}
	public void controleurTesterQBEnEdition(QuestionBinaire pQuestion)
	{
		VueQuestionBinaire vq = new VueQuestionBinaire(this.getLibelleSondage(),pQuestion,VueQuestionBinaire.DISPOSE_SANS_DEMANDER);
		vq.setVisible(true);
	}
	public void controleurEnregistrerQB(QuestionBinaire pQuestion)
	{
		this.s.addQuestion(pQuestion);
		this.fen.majListe((this.getNbQuestionsSondage()-1));
		this.fen.majVue();
	}
	public void controleurQuitterEditeurQB()
	{
		int retour = JOptionPane.showConfirmDialog(this.fen,"Voulez-vous vraiment quitter ?",this.fen.getTitle(),JOptionPane.YES_NO_OPTION);
		if(retour == JOptionPane.YES_OPTION)
		{
			this.fen.dispose();
			this.pere.setVisible(true);
		}
	}
}
