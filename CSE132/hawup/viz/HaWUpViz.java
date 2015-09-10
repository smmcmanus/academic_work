package hawup.viz;

import hawup.core.Node;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import kwic.KWIC;

import java.awt.GridLayout;
import java.awt.Panel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import java.awt.BorderLayout;

public class HaWUpViz extends JFrame {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HaWUpViz frame = new HaWUpViz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public HaWUpViz() {
		this(genNodes(5),10);
	}
	
	private static Node[] genNodes(int num) {
		Node[] ans = new Node[num];
		for (int i=0; i < num; ++i) {
			ans[i] = new Node(i, 5);
		}
		return ans;
	}

	/**
	 * Create the frame.
	 */
	public HaWUpViz(final Node[] nodes, final int maxQueueSize) {
		setBounds(100, 100, 646, 459);
		final JPanel[][] jp = new JPanel[nodes.length][maxQueueSize + 1];
		for(int i = 0; i < nodes.length; i++){
			for(int j = 0; j < maxQueueSize +1; j++){
					jp[i][j] = new JPanel();
					getContentPane().add(jp[i][j]);
			}
		}
		for(int i = 0; i < nodes.length; i++){
			for(int j = 1; j < maxQueueSize +1; j++){
					jp[i][j].setBackground(Color.WHITE);
			}
		}
		for(int i = 0; i < nodes.length; i++){
			final int j = i;
			nodes[i].getPCS().addPropertyChangeListener("idle",
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
								jp[j][0].setBackground(Color.GREEN);
								
						}

					}
			);
			nodes[i].getPCS().addPropertyChangeListener("busy",
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							jp[j][0].setBackground(Color.RED);
						}

					}
			);
//			nodes[i].getPCS().addPropertyChangeListener("taskadded",
//					new PropertyChangeListener() {
//						@Override
//						public void propertyChange(PropertyChangeEvent evt) {
//							int p = nodes[j].getNumWaiting();
//							jp[j][p].setBackground(Color.YELLOW);
//							
//						}
//
//					}
//			);
			nodes[i].getPCS().addPropertyChangeListener(
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							int p = nodes[j].getNumWaiting();
							for(int r = 1; r <= p; r++){
								jp[j][r].setBackground(Color.YELLOW);
							}
							for(int d = p+1; d <= maxQueueSize; d++){
								jp[j][d].setBackground(Color.WHITE);
							}
						}
					}
			);

			getContentPane().setLayout(new GridLayout(nodes.length,maxQueueSize +1, 10 ,10));

			
		}
		
		
	}
	
}
