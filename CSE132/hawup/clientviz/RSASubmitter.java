package hawup.clientviz;

import hawup.client.RSA;
import hawup.client.SubmitsJob;
import hawup.client.JobIsh;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class RSASubmitter extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JTextField passsword;
	private JTextField numToBeFactored;
	private JTextField largestFactor;
	private JTextField hostName;
	private JTextField portNumber;
	private JTextField smallestFactor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RSASubmitter frame = new RSASubmitter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RSASubmitter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(232, 95, 134, 32);
		contentPane.add(userName);
		userName.setColumns(10);
		userName.setText(genRandomName(5));
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(50, 92, 88, 35);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(62, 139, 76, 16);
		contentPane.add(lblPassword);
		
		passsword = new JTextField();
		passsword.setText("xyzzy");
		passsword.setBounds(232, 133, 134, 28);
		contentPane.add(passsword);
		passsword.setColumns(10);
		
		JLabel lblNumFactor = new JLabel("Number to be factored");
		lblNumFactor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumFactor.setBounds(23, 179, 160, 16);
		contentPane.add(lblNumFactor);
		
		numToBeFactored = new JTextField();
		numToBeFactored.setText("128");
		numToBeFactored.setBounds(233, 173, 186, 28);
		contentPane.add(numToBeFactored);
		numToBeFactored.setColumns(10);
		
		JLabel lblLargestFactor = new JLabel("Largest factor to try");
		lblLargestFactor.setHorizontalAlignment(SwingConstants.CENTER);
		lblLargestFactor.setBounds(51, 213, 160, 16);
		contentPane.add(lblLargestFactor);
		
		largestFactor = new JTextField();
		largestFactor.setText("5000");
		largestFactor.setBounds(233, 207, 177, 28);
		contentPane.add(largestFactor);
		largestFactor.setColumns(10);
		JLabel lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(62, 53, 61, 16);
		contentPane.add(lblPort);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		lblHost.setBounds(62, 12, 61, 16);
		contentPane.add(lblHost);
		
		hostName = new JTextField();
		hostName.setText("localhost");
		hostName.setBounds(232, 6, 186, 28);
		contentPane.add(hostName);
		hostName.setColumns(10);
		
		portNumber = new JTextField();
		portNumber.setText("3000");
		portNumber.setBounds(232, 47, 78, 28);
		contentPane.add(portNumber);
		portNumber.setColumns(10);
		
		JButton btnGo = new JButton("Go!");
		btnGo.setBackground(Color.RED);
		btnGo.setOpaque(true);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RSA c = new RSA(
						userName.getText(),
						passsword.getText(),
						numToBeFactored.getText(),
						smallestFactor.getText(),
						largestFactor.getText()
						);
				
				new Thread(new SubmitsJob(hostName.getText(), Integer.parseInt(portNumber.getText()), c)).start();
				genInputs();
			}
		});
		btnGo.setBounds(378, 121, 53, 29);
		contentPane.add(btnGo);
		
		JLabel lblsmallestFactor = new JLabel("Smallest factor");
		lblsmallestFactor.setBounds(77, 238, 121, 16);
		contentPane.add(lblsmallestFactor);
		
		smallestFactor = new JTextField();
		smallestFactor.setBounds(232, 232, 178, 28);
		contentPane.add(smallestFactor);
		smallestFactor.setColumns(10);
		genInputs();

	}
	
	private void genInputs() {
		userName.setText(genRandomName(5));
		Random r = new Random();
		BigInteger p = BigInteger.probablePrime(25, r);
		BigInteger q = BigInteger.probablePrime(25, r);
		BigInteger num = p.multiply(q);
		BigInteger upper = p.multiply(BigInteger.ONE.add(BigInteger.ONE)).add(q);
		numToBeFactored.setText(num.toString());
		largestFactor.setText(upper.toString());
		smallestFactor.setText("0");
	}
	
	public static String genRandomName(int len) {
		Random r = new Random();
		char[] alpha = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };
		String ans = "";
		for (int i=0; i < len; ++i) {
			ans += alpha[r.nextInt(alpha.length)];
		}
		return ans;
	}
}
