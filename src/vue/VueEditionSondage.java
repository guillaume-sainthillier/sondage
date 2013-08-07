package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import controleur.*;

public class VueEditionSondage extends JFrame implements WindowListener,ActionListener,MouseListener,KeyListener{
	

	private static final long serialVersionUID = 1L;
	private ControleurEditeurSondage c;
	private JButton bAddQ,bDelQ,bTestQ,bSaveS,bLoadS,bNewS,bQuitter;
	private JTextField titreS;
	private JLabel nbQ;
	private JMenuBar menu;
	private JMenu fichier,question;
	private JMenuItem fichier_ouvrir,fichier_enregistrer,fichier_nouveau,fichier_quitter,
					question_tester,question_ajouter,question_supprimer;
	private JList listeQ;
	
	public VueEditionSondage(String titre,ControleurEditeurSondage ces)
	{
		super(titre);
		int x = Toolkit.getDefaultToolkit().getScreenSize().width*1/4;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height*1/4;
		this.setBounds(x, y, 800, 500);
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
			
		//Controleurs
		this.c = ces;
		
		//TextField & Label & JList
		this.titreS = new JTextField("Test");
		this.titreS.setPreferredSize(new Dimension(500, 20));
		this.nbQ 	= new JLabel("Nombre de questions: 0",JLabel.CENTER);
		this.listeQ = new JList();
		
       // JScrollPane pour les ascenseurs de la JList.
	       JScrollPane js = new JScrollPane ();
	       js.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	       js.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	       js.setViewportView(this.listeQ);

           // Créer la JList + paramétrage
           this.listeQ = new JList ();
           this.listeQ.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
           // On la met dans la JScrollPane
           	js.setViewportView (this.listeQ);
           	
		
		//JButton
		this.bAddQ 		= new JButton("Ajouter Qu.");
		this.bDelQ 		= new JButton("Supprimer Qu.");
		this.bTestQ	 	= new JButton("Tester Qu.");
		this.bSaveS 	= new JButton("Enregistrer So.");
		this.bLoadS 	= new JButton("Charger So.");
		this.bNewS 		= new JButton("Nouveau So.");
		this.bQuitter 	= new JButton("Quitter");
		
		//Menus
		this.menu = new JMenuBar();
		this.fichier = new JMenu("Fichier");
			this.fichier_ouvrir  = new JMenuItem("Ouvrir");
			this.fichier_enregistrer = new JMenuItem("Enregistrer");
			this.fichier_nouveau	 = new JMenuItem("Nouveau");
			this.fichier_quitter = new JMenuItem("Quitter");
		this.question = new JMenu("Question");
			this.question_ajouter   = new JMenuItem("Ajouter");
			this.question_supprimer = new JMenuItem("Supprimer");
			this.question_tester 	= new JMenuItem("Tester");
			
		this.menu.add(this.fichier);
			this.fichier.add(this.fichier_ouvrir);
			this.fichier.add(this.fichier_enregistrer);
			this.fichier.add(this.fichier_nouveau);
			this.fichier.add(this.fichier_quitter);
			
			
		this.menu.add(this.question);
			this.question.add(this.question_ajouter);
			this.question.add(this.question_supprimer);
			this.question.add(this.question_tester);
			
		this.setJMenuBar(this.menu);
		
		//Mnémonics
		this.bTestQ.setMnemonic('T');
		this.bSaveS.setMnemonic('E');
		this.bLoadS.setMnemonic('C');
		this.bDelQ.setMnemonic('S');
		this.bAddQ.setMnemonic('A');
		this.bNewS.setMnemonic('N');
		this.bQuitter.setMnemonic('Q');
		this.fichier.setMnemonic('F');
		this.fichier_ouvrir.setMnemonic('O');
		this.fichier_quitter.setMnemonic('R');
		this.fichier_ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK)); //Ctrl-O
		this.fichier_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK)); //Ctrl-Q
		this.question_tester.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,KeyEvent.CTRL_MASK)); //Ctrl-Q
		this.question_ajouter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,KeyEvent.CTRL_MASK)); //Ctrl-Q
		this.question_supprimer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,KeyEvent.CTRL_MASK)); //Ctrl-Q
		this.fichier_nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK)); //Ctrl-Q
		this.fichier_enregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK)); //Ctrl-S
		
		//Placements des composants
		JPanel panelBouton = new JPanel();
		panelBouton.setLayout(new GridLayout(3,3));
		panelBouton.add(this.bAddQ);
		panelBouton.add(this.bDelQ);
		panelBouton.add(this.bTestQ);
		panelBouton.add(this.bSaveS);
		panelBouton.add(this.bLoadS);
		panelBouton.add(this.bNewS);
		panelBouton.add(this.bQuitter);
		
		JPanel panelNord = new JPanel();
		panelNord.setLayout(new BorderLayout());
		JPanel panelNordNord = new JPanel(); //Flow Layout par défaut
			panelNordNord.add(new JLabel("Titre Sondage: "));
			panelNordNord.add(this.titreS);
		panelNord.add(panelNordNord,BorderLayout.NORTH);
		panelNord.add(this.nbQ,BorderLayout.SOUTH);
		
		
		this.setLayout(new BorderLayout());
		this.add(panelBouton,BorderLayout.SOUTH);
		this.add(panelNord,BorderLayout.NORTH);
		this.add(js,BorderLayout.CENTER);
		
		
		//Listeners
		this.addWindowListener(this);
		this.bAddQ.addActionListener(this);
		this.bDelQ.addActionListener(this);
		this.bLoadS.addActionListener(this);
		this.bNewS.addActionListener(this);
		this.bQuitter.addActionListener(this);
		this.bSaveS.addActionListener(this);
		this.bTestQ.addActionListener(this);
		this.fichier_ouvrir.addActionListener(this);
		this.fichier_quitter.addActionListener(this);
		this.fichier_enregistrer.addActionListener(this);
		this.fichier_nouveau.addActionListener(this);
		this.question_ajouter.addActionListener(this);
		this.question_supprimer.addActionListener(this);
		this.question_tester.addActionListener(this);
		this.listeQ.addMouseListener(this);
		this.listeQ.addKeyListener(this);
		
	}
	public void majVue()
	{
		this.titreS.setText(this.c.getLibelleSondage());
		this.nbQ.setText("Nombre de questions: "+this.c.getNbQuestionsSondage());
	}
	
	public void majBoutons()
	{
		if(this.c.getNbQuestionsSondage() == 0)
		{
			this.bDelQ.setEnabled(false);
			this.bTestQ.setEnabled(false);
			this.bSaveS.setEnabled(false);
		}else
		{
			this.bDelQ.setEnabled(true);
			this.bTestQ.setEnabled(true);
			this.bSaveS.setEnabled(true);
		}
	}
	public void majListe(int pSelectedIndex)
	{         
		 Vector<String> v = new Vector <String> ();  
         int nbQ = this.c.getNbQuestionsSondage();
          for (int i =0; i< nbQ; i++)
          {
       	   String s = this.c.getQuestionSondage(i).getQuestion();
       	   s += " ("+ this.c.getQuestionSondage(i).getLibOui()+'/'+ this.c.getQuestionSondage(i).getLibNon()+")";
       	   s += "-("+ this.c.getQuestionSondage(i).getNbOui()+"/"+this.c.getQuestionSondage(i).getNbNon()+")";
              v.add (s);
          }
  
          // On y met les données
          this.listeQ.setListData (v);
          
          if (v.size()>0)
              this.listeQ.setSelectedIndex(pSelectedIndex);
          
  	}
	
	
	public int getIndex()
	{
		int index = this.listeQ.getSelectedIndex();
		if(index < 0)
		{
			JOptionPane.showMessageDialog(this, "Vous n'avez selectionné aucune question! ");
		}
		return index;
	}
	public void testerQuestion()
	{
		int index = this.getIndex();
		if(index >= 0)
			this.c.controleurTesterQBDepuisEditionSondage(this.c.getQuestionSondage(index));
		
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == this.bQuitter || ae.getSource() == this.fichier_quitter)
			this.c.controleurQuitterEditeurQB();
		if(ae.getSource() == this.bLoadS || ae.getSource() == this.fichier_ouvrir)		
			this.c.controleurChargerUnSondage();
		if(ae.getSource() == this.bTestQ || ae.getSource() == this.question_tester)		
			this.testerQuestion();	
		if(ae.getSource() == this.bDelQ || ae.getSource() == this.question_supprimer)
			this.supprimerQ();
		if(ae.getSource() == this.bSaveS || ae.getSource() == this.fichier_enregistrer)
			this.c.controleurSauvegarderSondage();
		if(ae.getSource() == this.bNewS || ae.getSource() == this.fichier_nouveau)
			this.c.controleurNouveauSondage();
		if(ae.getSource() == this.bAddQ || ae.getSource() == this.question_ajouter)
			this.c.controleurAjouterQuestion();
		
	}
	public void windowClosing(WindowEvent arg0) {
		this.c.controleurArreterEditionSondage();
	}
	
	public void supprimerQ()
	{
		int index = this.getIndex();
		if(index >= 0)
		{
			this.c.controleurSupprimerQuestion(index);
		}
		
	}
	public void mouseClicked(MouseEvent me) {
		if(me.getClickCount() == 2){
			this.testerQuestion();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_END)
			this.supprimerQ();
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			this.testerQuestion();
	}
	
	public JTextField getTitreS()
	{
		return this.titreS;
	}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}	
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void mouseEntered(java.awt.event.MouseEvent e) {}
	public void mouseExited(java.awt.event.MouseEvent e) {}
	public void mousePressed(java.awt.event.MouseEvent e) {}
	public void mouseReleased(java.awt.event.MouseEvent e) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent e) {}
	
	

	
	
	

}
