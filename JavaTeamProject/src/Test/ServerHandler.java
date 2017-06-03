package Test;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * class : ServerHandler 
 * public class ServerHandler는 서버로 들어온 클라이언트와의 연결을 
 * 개별적으로 처리하는 일을 맡은 Thread 클래스.  
 * 클라이언트로부터의 메시지를 받는 역할
 */
class CheckKey {
	static String adminKey = "root";
	static String[] studentKey = new String[10];;

	public CheckKey() {
		for (int i = 0; i < 10; i++) {
			studentKey[i] = "aaaa" + i;
		}
	}
}

class Room {
	public int rnum;
}

class Cardkey extends Room implements Serializable {
	String mykey;
	String name;
	String sex;
	String age;
	boolean exeat;

	public Cardkey(String n, String s, String a, boolean e) {
		name = n;
		sex = s;
		age = a;
		exeat = e;
	}
}

public class ServerHandler implements Runnable {
	CheckKey checkkey = new CheckKey();
	protected Socket socket;

	/**
	 * 생성자 : ServerHandler() 생성자는 클라이언트로부터 들어온 연결을 맡은 소켓인 socket의 참조자를 받아 복사해 둠.
	 */
	public ServerHandler(Socket socket) {
		this.socket = socket;
	}

	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	protected ObjectInputStream obIn;
	protected ObjectOutputStream obOut;
	protected Thread listener;

	/**
	 * 메소드 : init() init()는 입력/출력 스트림을 열고, 버퍼링 데이터 스트림을 사용하여, String을 주고받을 수 있는
	 * 기능을 제공 . 클라이언트 처리용 쓰레드인 listener를 새로 만들어 시작.
	 */
	public synchronized void loginit() {
		if (listener == null) {
			try {
				// 소켓으로부터 입력스트림을 획득.
				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				// 소켓으로부터 출력스트림을 획득.
				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				System.out.println("0");
			
				// listener 스레드를 생성하고 시작.
				// this는 Server에서 생성된 handler 객체를 의미.
				listener = new Thread(this);
				listener.start();
			} catch (IOException ignored) {
			}
		}

	}
	public synchronized void personinit() {
		if (listener == null) {
			try {
				// 소켓으로부터 입력스트림을 획득.
//				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//				// 소켓으로부터 출력스트림을 획득.
//				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				obIn = new ObjectInputStream(socket.getInputStream());
				obOut = new ObjectOutputStream(socket.getOutputStream());
				// listener 스레드를 생성하고 시작.
				// this는 Server에서 생성된 handler 객체를 의미.
				listener = new Thread(this);
				listener.start();
			} catch (IOException ignored) {
			}
		}
	}
	/**
	 * 메소드 : stop() listener 쓰레드에 인터럽트를 걸고 dataOut 스트림을 닫는 역할.
	 */
	public synchronized void stop() {
		if (listener != null) {
			try {
				if (listener != Thread.currentThread())
					listener.interrupt();
				listener = null;
				dataOut.close();
				obOut.close();
			} catch (IOException ignored) {
			}
		}
	}

	// 메소드 : run()
	// 쓰레드의 실행이 가장 먼저 시작되는 부분
	//
	public void run() {
		System.out.println("running");
		String name = null, sex = null;
		String age = null;
		boolean exeat = false;
		Cardkey ck = new Cardkey(name, sex, age, exeat);

		try {

			while (!Thread.interrupted()) {
				String loginkey = dataIn.readUTF();
				System.out.println("로그인시도");
				boolean checked = false;
				try {

					System.out.println(loginkey);

					for (int i = 0; i < 10; i++) {
						if (loginkey.equals(CheckKey.studentKey[i])) {
							dataOut.writeInt(2);
							// System.out.println("2반환");
							dataOut.flush();
							checked = true;
							ck.mykey = loginkey;
							obIn = new ObjectInputStream(socket.getInputStream());
							obOut = new ObjectOutputStream(socket.getOutputStream());
							if (fileIsLive(ck.mykey + ".txt")) {
							//	System.out.println("파일존재");
							} else {
								ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream(ck.mykey + ".txt"));
							//	System.out.println(ck.mykey + ".txt파일생성");
							}
						}
					}
					if (loginkey.equals(CheckKey.adminKey)) {
						dataOut.writeInt(1);
						// System.out.println("1반환");
						dataOut.flush();
						checked = true;
						ck.mykey = loginkey;
						if (fileIsLive(ck.mykey + ".txt")) {
							//System.out.println("파일존재");
							obIn = new ObjectInputStream(socket.getInputStream());
							obOut = new ObjectOutputStream(socket.getOutputStream());
						} else {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ck.mykey + ".txt"));
							//System.out.println(ck.mykey + ".txt파일생성");
						}
					} else if (checked == false) {
						dataOut.writeInt(3);
						// System.out.println("3반환");
						dataOut.flush();
					}
				} catch (NoSuchElementException e) {
					System.out.println("접속을 종료하셨습니다.");
					stop();
				}

			}
		} catch (EOFException ignored) {
			System.out.println("접속을 종료하셨습니다.");
		} catch (IOException ie) {
			System.out.println("접속을 종료하셨습니다.");
			if (listener == Thread.currentThread())
				ie.printStackTrace();
		} finally {
			stop();
		}
	}

	private static boolean fileIsLive(String isLivefile) {
		// TODO Auto-generated method stub
		File f1 = new File(isLivefile);
		if (f1.exists())
			return true;
		else
			return false;
	}
}
