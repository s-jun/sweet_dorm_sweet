package Test;

import java.io.IOException;

import javax.swing.JFrame;

public class MainProcess {
	LoginView loginView;
	private StudentInfo sInfo;
	private AdminMain adminCla;
	private StudentMain studentCla;

	public static void main(String[] args) {

		// 메인클래스 실행
		MainProcess main = new MainProcess();
		main.loginView = new LoginView(); // 로그인창 보이기
		main.loginView.setMain(main); // 로그인창에게 메인 클래스보내기
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