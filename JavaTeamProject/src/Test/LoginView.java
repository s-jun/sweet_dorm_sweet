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

	public void placeLoginPanel(JPanel panel) {// 로그인 GUI
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
		userText.setBounds(100, 10, 160, 25); // 아이디 텍스트 위치 크기
		panel.add(userText);

		try {
			// ip, port를 인자로 Socket형 객체를 생성
			// 사용자 환경에 따라 ip와 port값을 변경해서 사용
			socket = new Socket("127.0.0.1", 9000);
			// 소켓으로부터 입력스트림을 획득
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// 소켓으로부터 출력스트림을 획득
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException ie) {
			stop();
		}
		btnInit = new JButton("Reset");
		btnInit.setBounds(10, 80, 100, 25);// 리셋버튼 위치 크기
		panel.add(btnInit);
		btnInit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
			}

		});

		btnLogin = new JButton("Login");
		btnLogin.setBounds(160, 80, 100, 25);// 로그인 버튼 위치
												// 크기
		panel.add(btnLogin);
		btnLogin.setText("Login");
		btnLogin.addActionListener(this);
		
	}

	public void isLoginCheck() { // 서버로부터 로그인 확인후 클라연결
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

	// mainProcess와 연동
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