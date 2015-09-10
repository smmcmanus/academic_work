package lab1.percent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller extends JPanel {

	final PercentModel model;
	public Controller(String s) {
		model = new PercentModel();
		this.add(new JLabel(s));
		this.add(new ViewAsSlider(model));
		this.add(new ViewAsText(model));
	}


	public PercentModel getModel() {
		return model;
	}
	
	public static void main(String[] args) {
		Controller panel = new Controller("Test");
		//
		// What you see below is what YOPS did for you
		//
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
