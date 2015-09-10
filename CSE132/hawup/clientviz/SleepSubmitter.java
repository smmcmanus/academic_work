package hawup.clientviz;

import hawup.client.Sleep;
import hawup.client.SubmitsJob;
import hawup.client.JobIsh;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataOutputStream;
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

public class SleepSubmitter extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JTextField passsword;
	private JTextField numTasks;
	private JTextField maxSleepMS;
	private JTextField hostName;
	private JTextField portNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SleepSubmitter frame = new SleepSubmitter();
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
	public SleepSubmitter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(232, 118, 134, 32);
		contentPane.add(userName);
		userName.setColumns(10);
		userName.setText(genRandomName(5));
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(50, 115, 88, 35);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(62, 162, 76, 16);
		contentPane.add(lblPassword);
		
		passsword = new JTextField();
		passsword.setText("xyzzy");
		passsword.setBounds(232, 156, 134, 28);
		contentPane.add(passsword);
		passsword.setColumns(10);
		
		JLabel lblNumberOfTasks = new JLabel("Number of tasks");
		lblNumberOfTasks.setBounds(51, 202, 120, 16);
		contentPane.add(lblNumberOfTasks);
		
		numTasks = new JTextField();
		numTasks.setText("128");
		numTasks.setBounds(233, 196, 77, 28);
		contentPane.add(numTasks);
		numTasks.setColumns(10);
		
		JLabel lblMaxSleepTime = new JLabel("Max Sleep Time");
		lblMaxSleepTime.setBounds(51, 236, 259, 16);
		contentPane.add(lblMaxSleepTime);
		
		maxSleepMS = new JTextField();
		maxSleepMS.setText("5000");
		maxSleepMS.setBounds(233, 230, 77, 28);
		contentPane.add(maxSleepMS);
		maxSleepMS.setColumns(10);
		JLabel lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(62, 76, 61, 16);
		contentPane.add(lblPort);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		lblHost.setBounds(62, 35, 61, 16);
		contentPane.add(lblHost);
		
		hostName = new JTextField();
		hostName.setText("localhost");
		hostName.setBounds(232, 29, 186, 28);
		contentPane.add(hostName);
		hostName.setColumns(10);
		
		portNumber = new JTextField();
		portNumber.setText("3000");
		portNumber.setBounds(232, 70, 78, 28);
		contentPane.add(portNumber);
		portNumber.setColumns(10);
		
		JButton btnGo = new JButton("Go!");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sleep c = new Sleep(
						userName.getText(),
						passsword.getText(),
						Integer.parseInt(numTasks.getText()),
						Integer.parseInt(maxSleepMS.getText())
						);
				
				new Thread(new SubmitsJob(hostName.getText(), Integer.parseInt(portNumber.getText()), c)).start();
				userName.setText(genRandomName(5));
			}
		});
		btnGo.setBounds(333, 243, 117, 29);
		contentPane.add(btnGo);
		

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
