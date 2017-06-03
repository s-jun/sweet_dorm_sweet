package Test;

import java.io.IOException;

import javax.swing.JFrame;

public class MainProcess {
	LoginView loginView;
	private StudentInfo sInfo;
	private AdminMain adminCla;
	private StudentMain studentCla;

	public static void main(String[] args) {

		// ����Ŭ���� ����
		MainProcess main = new MainProcess();
		main.loginView = new LoginView(); // �α���â ���̱�
		main.loginView.setMain(main); // �α���â���� ���� Ŭ����������
	}

	public void openAdmin() {
		loginView.dispose();
		this.adminCla = new AdminMain();
	}

	public void openStudent() throws IOException {
		loginView.dispose();
		this.studentCla = new StudentMain();
	}

	public void openStuInfo() throws IOException {
		this.sInfo = new StudentInfo();
	}
}