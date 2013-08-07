package vue;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import appli.metier.*;

public class VueQuestionBinaire extends JDialog implements WindowListener, ActionListener
{
	static public int QUITTER = 1;
	static public int DISPOSE = 2;
	static public int DISPOSE_SANS_DEMANDER = 3;
	private int option;
	private static final long serialVersionUID = 1L;
	private JButton bOui,bNon,bQuitter;
	private JLabel tResultat;
	
	QuestionBinaire laQb;
	
	public VueQuestionBinaire(String _t,QuestionBinaire qb)
	{
		this(_t,qb,VueQuestionBinaire.QUITTER);
	}
	public VueQuestionBinaire(String _t,QuestionBinaire qb,int pOption,JFrame parent)
	{
		super(parent,_t,true);
		this.setLocationRelativeTo(parent);
		this.laQb = qb;
		this.option = pOption;
		
		int x = Toolkit.getDefaultToolkit().getScreenSize().width*1/4;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height*1/4;
		this.setBounds(x, y, 450, 150);
		this.setPreferredSize(new Dimension(450,150));
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
			System.err.println("Mauvais look&feel employé");
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.MotifLookAndFeel");
			}catch(Exception e1)
			{
				e1.printStackTrace();
			}	
		}
		//Composants
		this.bOui		= new JButton(this.laQb.getLibOui());
		this.bNon 		= new JButton(this.laQb.getLibNon());
		this.bQuitter 	= new JButton("Quitter");
		JLabel question = new JLabel(this.laQb.getQuestion(),JLabel.CENTER);
		this.tResultat  = new JLabel(" ",JLabel.CENTER);

		//Layout
		this.setLayout(new BorderLayout());
		this.add(question,BorderLayout.NORTH);

		JPanel sud = new JPanel();
		sud.setLayout(new GridLayout(2,0));
		sud.add(this.tResultat);
		sud.add(this.bQuitter);
		this.add(sud,BorderLayout.SOUTH);
		this.add(this.bNon,BorderLayout.EAST);
		this.add(this.bOui,BorderLayout.WEST);
		
		//Listeners
		this.addWindowListener(this);
		this.bOui.addActionListener(this);
		this.bNon.addActionListener(this);
		this.bQuitter.addActionListener(this);
		
		this.pack(); 
	}
	public VueQuestionBinaire  (String _t, QuestionBinaire qb,int pOption)
	{
		this(_t,qb,pOption,null);
	}
	
	public void quitter()
	{
		if(this.option == VueQuestionBinaire.DISPOSE_SANS_DEMANDER)
			this.dispose();
		else
		{
			int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment quitter ?", "Quitter", JOptionPane.OK_CANCEL_OPTION);
			if(confirm == JOptionPane.OK_OPTION)
			{
				if(this.option == VueQuestionBinaire.QUITTER)
					System.exit(0);
				else
					if(this.option == VueQuestionBinaire.DISPOSE)
						this.dispose();		
			}
		}
	}
	
	public void oui()
	{
		this.laQb.enregOui();
		this.majResultat();
	}
	
	public void non()
	{
		this.laQb.enregNon();
		this.majResultat();
	}
	
	public void majResultat()
	{
		double pOui = this.round((double)(this.laQb.getNbOui() )/(this.laQb.getNbNon() + this.laQb.getNbOui() )*100,2);
		double pNon = this.round((double)(this.laQb.getNbNon() )/(this.laQb.getNbNon() + this.laQb.getNbOui() )*100,2);
		
		this.tResultat.setText(pOui+"% on répondu '"+this.laQb.getLibOui()
								+"' et \n"+pNon+"% ont répondu '"+this.laQb.getLibNon()+"' ");
	}
	
	public double round(double what, int howmuch) // Honteusement prise sur internet
	{
		return (double)( (int)(what * Math.pow(10,howmuch) + .5) ) / Math.pow(10,howmuch);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == this.bOui)
			this.oui();
		else
			if(ae.getSource() == this.bNon)
				this.non();
			else
				if(ae.getSource() == this.bQuitter)
					this.quitter();
	}	
	
	public void windowClosing(WindowEvent e) {
		this.quitter();
	}
	
	public JButton getBoutonOui(){
		return this.bOui;
	}
	
	public JButton getBoutonNon(){
		return this.bNon;
	}
	
	public QuestionBinaire getQuestion(){
		return this.laQb;
	}
	
	public JButton getBoutonQuitter(){
		return this.bQuitter;
	}
	
	public void setBoutonQuitter(String texte){
		this.bQuitter.setText(texte);
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}