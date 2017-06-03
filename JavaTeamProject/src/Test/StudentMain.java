package Test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class StudentMain extends JFrame implements ActionListener {
	StudentInfo info;
	MainProcess main;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	private JTextArea jta;
	
	public StudentMain() throws IOException {
		JButton button1;
		JButton button2;
		JButton button3;
		
		try {
			// ip, port�� ���ڷ� Socket�� ��ü�� ����
			socket = new Socket("127.0.0.1", 9000); // ����� ȯ�濡 ���� ip�� port����
													// �����ؼ� ���
			// �������κ��� �Է½�Ʈ���� ȹ��
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// �������κ��� ��½�Ʈ���� ȹ��
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException ie) {
			stop();
		}
		setSize(400, 400);
		setTitle("StudentClient");
		setVisible(true);
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		jta = new JTextArea(10, 30);
		jta.setEditable(false);
		JPanel p1 = new JPanel();
		ct.add(jta);
		button1=new JButton();
		button1.setText("�����Է�/����");
		button1.addActionListener(this);
		button2=new JButton();
		button2.setText("�Ĵ�ǥ");
		button3=new JButton();
		button3.setText("�ܹڽ�û");
		ct.add(button1);
		ct.add(button2);
		ct.add(button3);
		try {
			String str;
			BufferedReader br = new BufferedReader(new FileReader("����.txt"));
			while ((str = br.readLine()) != null)
				jta.append(str + "\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (event.getActionCommand().equals("�����Է�/����")) {
			try {
				main = new MainProcess();
				main.openStuInfo();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
