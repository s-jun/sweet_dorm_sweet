package Test;

import java.net.*;

/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;*/
import java.awt.event.*;
/*import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;*/
import java.io.*;

import javax.swing.*;
/*import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;*/

public class LoginView extends JFrame implements ActionListener {
	private MainProcess main;
	private AdminMain adminCla;
	private StudentMain studentCla;

	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	private JButton btnLogin;
	private JButton btnInit;
	private JTextField userText;
	private boolean bLoginCheck;

	public static void main(String[] args) {
		// new LoginView();
	}

	public LoginView() {
		// setting
		setTitle("login");
		setSize(280, 150);
		setResizable(false);
		setLocation(800, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// panel
		JPanel panel = new JPanel();
		placeLoginPanel(panel);

		// add
		add(panel);

		// visiible
		setVisible(true);
	}

	public void placeLoginPanel(JPanel panel) {// �α��� GUI
		panel.setLayout(null);
		JLabel userLabel = new JLabel("Cardkey");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(10, 80, 100, 25);
		getContentPane().add(btnExit);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25); // ���̵� �ؽ�Ʈ ��ġ ũ��
		panel.add(userText);

		try {
			// ip, port�� ���ڷ� Socket�� ��ü�� ����
			// ����� ȯ�濡 ���� ip�� port���� �����ؼ� ���
			socket = new Socket("127.0.0.1", 9000);
			// �������κ��� �Է½�Ʈ���� ȹ��
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// �������κ��� ��½�Ʈ���� ȹ��
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException ie) {
			stop();
		}
		btnInit = new JButton("Reset");
		btnInit.setBounds(10, 80, 100, 25);// ���¹�ư ��ġ ũ��
		panel.add(btnInit);
		btnInit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
			}

		});

		btnLogin = new JButton("Login");
		btnLogin.setBounds(160, 80, 100, 25);// �α��� ��ư ��ġ
												// ũ��
		panel.add(btnLogin);
		btnLogin.setText("Login");
		btnLogin.addActionListener(this);
		
	}

	public void isLoginCheck() { // �����κ��� �α��� Ȯ���� Ŭ�󿬰�
		try {
			int lcheck = dataIn.readInt();
			if (lcheck == 1) {
				JOptionPane.showMessageDialog(null, "Admin");
				main.openAdmin();
			} else if (lcheck == 2) {
				JOptionPane.showMessageDialog(null, "Student");
				main.openStudent();
			} else if (lcheck == 3) {
				JOptionPane.showMessageDialog(null, "Failed");
			}
		} catch (IOException io) {
			stop();
		}

	}

	// mainProcess�� ����
	public void setMain(MainProcess main) {
		this.main = main;
	}

	public void stop() {
		try {
			dataIn.close();
			dataOut.close();
			socket.close();

		} catch (IOException e) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getActionCommand().equals("Login")) {
			try {
				dataOut.writeUTF(userText.getText());
				dataOut.flush();
				isLoginCheck();
			} catch (Exception e) {
			}
		}

	}
}