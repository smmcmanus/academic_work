package lab1;
import nip.*;
import lab1.percent.Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** LAB 1
 * TA: Brian Choi
 * GRADE: see Blackboard
 *
 * Notes
 * 	Great job overall
 * 
 * 	For future labs, make sure to remove commented out code, add a few inline comments and javadocs
 * 
 * 	Also see inline comments below 
 */

public class RGB extends JPanel {

	final private Controller red, blue, green;
	final private ChangeListener a, b, c, aL, bL, cL;	// TA: try to avoid undescriptive variable names
	/**
	 * I'll talk about "final" below in lecture next week -- RKC
	 * 
	 * @param one An image from which pixels will be copied
	 * @param two An image, same size as one, to which pixels will be copied
	 */
	public RGB(final Image one, final Image two) {

		add(red   = new Controller("red"));
		add(blue   = new Controller("blue"));
		add(green  = new Controller("green"));
		//
		//  Add a green and blue Controller to this JPanel, too
		//  Until you do that, you'll get null pointer exceptions if you hit "go"
		//

		//JButton go = new JButton("go");
		//this.add(go);
		
		JToggleButton lock = new JToggleButton("lock");
		this.add(lock);

		//
		// When "go" button is pressed, call copyImage() to do the work
		//  This will fail until you initialize red, green, and blue
		//  The code below uses an anonymous inner class, which I will
		//  explain in upcoming lectures.
		//
		
		//Initialize two sets of changeListeners. One for synchronized sliders and
		//one for independent sliders. Independent sliders are turned on first.
		a = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				copyImage(one, two);	
			}
		};
		red.getModel().addChangeListener(a); // TA: this is 100% correct, but generally people use addChangeListener(new ChangeListener(){ ... });
		b = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				copyImage(one, two);	
			}
		};
		blue.getModel().addChangeListener(b);
		c = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				copyImage(one, two);	
			}
		};
		green.getModel().addChangeListener(c);
		aL = new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				blue.getModel().setValue(red.getModel().getValue());
				green.getModel().setValue(red.getModel().getValue());
				copyImage(one, two);
			}
		};
		bL = new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				red.getModel().setValue(blue.getModel().getValue());
				green.getModel().setValue(blue.getModel().getValue());
				copyImage(one, two);
			}
		};
		cL = new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				red.getModel().setValue(green.getModel().getValue());
				blue.getModel().setValue(green.getModel().getValue());
				copyImage(one, two);
			}
		};
		//Go button has been commented out as 
		//it is unnecessary with the sliders working on movement
		//go.addActionListener(
		//		new ActionListener() {
		//			
		//			public void actionPerformed(ActionEvent e) {
		//				copyImage(one, two);
		//			}
		//			
		//		}
		//);
		
		//Lock button toggles synchronized sliders on and off
		//Adds and removes the two sets of changeListeners based
		//on state of the lock button.
		
		// TA: this is correct, but instead of adding/removing ChangeListeners, it might be simpler to use a boolean (true/false) and putting an if statement inside of your change listeners.
		
		lock.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent ev) {
						if(ev.getStateChange()==ItemEvent.SELECTED){
							red.getModel().removeChangeListener(a);
							blue.getModel().removeChangeListener(b);
							green.getModel().removeChangeListener(c);
							red.getModel().addChangeListener(aL);
							blue.getModel().addChangeListener(bL);
							green.getModel().addChangeListener(cL);
	
						}	 
						else if(ev.getStateChange()==ItemEvent.DESELECTED){
							red.getModel().removeChangeListener(aL);
							blue.getModel().removeChangeListener(bL);
							green.getModel().removeChangeListener(cL);
							red.getModel().addChangeListener(a);
							blue.getModel().addChangeListener(b);
							green.getModel().addChangeListener(c);
						}
					}
				}
		);

	}

	/**
	 * Copy the in image to the out image.
	 * Each pixel is broken into its red, green, blue components.
	 * The appropriate percentage is taken of each color and combined into
	 *    a new color, written to the out image.
	 */
	public void copyImage(Image in, Image out) {
		for (int i=0; i < in.getWidth(); ++i) {
			for (int j=0; j < in.getHeight(); ++j) {

				Color c = in.getPixelColor(i, j);

				int r = red.getModel().computePercentOf(c.getRed());
				int g = green.getModel().computePercentOf(c.getGreen());
				int b = blue.getModel().computePercentOf(c.getBlue());

				out.setPixel(i, j, new Color(r, g, b));
			}
		}
		out.repaint();  // force the changes to get shown

	}	

	public static void main(String[] args) {
		/*
		 * picture by Melanie Cytron http://macytron.deviantart.com/
		 */
		NIP nip = new NIP(600, 400, 2, "images/Summit_Lake_II_by_macytron.jpg");
		RGB panel = new RGB(
				nip.getGraphicsPanel(0).getMainImage(),
				nip.getGraphicsPanel(1).getMainImage()
		);

		//
		// What you see below is what NIP did for you
		//
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
