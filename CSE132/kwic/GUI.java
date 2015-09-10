package kwic;

import java.awt.BorderLayout; 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.beans.PropertyChangeSupport;

import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;

public class GUI extends JFrame {

	private JPanel contentPane;
	private KWIC kwic;
	private PropertyChangeSupport pcs;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtWord;
	private JTextField txtPhrase;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 459);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		kwic = new KWIC();
		pcs = kwic.getPCS();
		kwic.addPhrases(new File("datafiles/kwic/fortunes.txt"));
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(209, 8, 398, 241);
		contentPane.add(scrollPane_1);
		
		final JList list_1 = new JList();
		list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setViewportView(list_1);
		
		Set<Word> wordset = kwic.getWords();
		Object[] labels = wordset.toArray();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 191, 241);
		contentPane.add(scrollPane);
		final JList list = new JList();
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Word w = (Word) list.getSelectedValue();
				Set<Phrase> phraseSet = kwic.getPhrases(w);
				Object[] labels_1 = phraseSet.toArray();
				list_1.setListData(labels_1);
				list_1.setSelectedIndex(0);
				
			}	
		});
		scrollPane.setViewportView(list);
		list.setListData(labels);
		
		JButton btnAddWord = new JButton("Add Word");
		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = textField_1.getText();
				Word s = new Word(text);
				Set<Phrase> f = new HashSet<Phrase>();
				kwic.map.put(new Word(s.getMatchWord()), f);
				Set<Word> wordset = kwic.getWords();
				Object[] labels = wordset.toArray();
				list.setListData(labels);
				textField_1.setText("");
			}
		});
		btnAddWord.setBounds(6, 303, 143, 29);
		contentPane.add(btnAddWord);
		
		JButton btnDeleteWord = new JButton("Delete Word");
		btnDeleteWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kwic.deleteWord((Word)list.getSelectedValue());
				Set<Word> wordset = kwic.getWords();
				Object[] labels = wordset.toArray();
				list.setListData(labels);
				list.setSelectedIndex(0);
				Word w = (Word) list.getSelectedValue();
				Set<Phrase> phraseSet = kwic.getPhrases(w);
				Object[] labels_1 = phraseSet.toArray();
				list_1.setListData(labels_1);
				list_1.setSelectedIndex(0);
			}
		});
		btnDeleteWord.setBounds(6, 259, 143, 29);
		
		contentPane.add(btnDeleteWord);
		
		JButton btnAddPhrase = new JButton("Add Phrase");
		btnAddPhrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = textField_2.getText();
				kwic.addPhrase(text);
				Set<Word> wordset = kwic.getWords();
				Object[] labels = wordset.toArray();
				list.setListData(labels);
				textField_2.setText("");
			}
		});
		btnAddPhrase.setBounds(209, 303, 165, 29);
		contentPane.add(btnAddPhrase);
		
		JButton btnDeletePhrase = new JButton("Delete Phrase");
		btnDeletePhrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!list_1.isSelectionEmpty()){
					kwic.deletePhrase((Phrase)list_1.getSelectedValue());
					Word w = (Word) list.getSelectedValue();
					Set<Phrase> phraseSet = kwic.getPhrases(w);
					Object[] labels_1 = phraseSet.toArray();
					list_1.setListData(labels_1);
				}
			}
		});
		btnDeletePhrase.setBounds(209, 261, 165, 29);
		contentPane.add(btnDeletePhrase);
		
		textField_1 = new JTextField();
		textField_1.setBounds(6, 342, 154, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(209, 342, 398, 29);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		txtWord = new JTextField();
		txtWord.setText("Word");
		txtWord.setBounds(6, 388, 120, 28);
		contentPane.add(txtWord);
		txtWord.setColumns(10);
		
		txtPhrase = new JTextField();
		txtPhrase.setText("Phrase");
		txtPhrase.setBounds(270, 388, 337, 28);
		contentPane.add(txtPhrase);
		txtPhrase.setColumns(10);
		
		JButton btnForceAssociation = new JButton("Force Association");
		btnForceAssociation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String word = txtWord.getText();
				String phrase = txtPhrase.getText();
				Word w = new Word(word);
				Phrase p = new Phrase(phrase);
				kwic.forceAssoc(new Word(w.getMatchWord()), p);
			}
		});
		btnForceAssociation.setBounds(130, 389, 143, 29);
		
		contentPane.add(btnForceAssociation);
		
		JButton btnDropAssociationselected = new JButton("Drop Association");
		btnDropAssociationselected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Word w = (Word) list.getSelectedValue();
				Phrase p = (Phrase) list_1.getSelectedValue();
				kwic.dropAssoc(w, p);
				Set<Phrase> phraseSet = kwic.getPhrases(w);
				Object[] labels_1 = phraseSet.toArray();
				list_1.setListData(labels_1);
			}
		});
		btnDropAssociationselected.setBounds(431, 278, 176, 29);
		contentPane.add(btnDropAssociationselected);
		
		
	}
}
