import vue.*;

public class TestQB 
{
	
	public static void main(String[] args) 
	{
		/*	VueQuestionBinaire vqb ;
		QuestionBinaire qb ;
		
		qb = new QuestionBinaire ("J'aime les frites", 10, 12, "Peut �tre", "Sans doute pas");
		
		vqb = new VueQuestionBinaire ("R�pondre � une question", qb);
		vqb.setVisible (true);*/
		/*ControleurEditeurSondage c = new ControleurEditeurSondage();
		c.demarrerControleurEditeurSondage();*/
		VuePasseurEditeurSondage vpes = new VuePasseurEditeurSondage();
		vpes.setVisible(true);
		
	}	
}
