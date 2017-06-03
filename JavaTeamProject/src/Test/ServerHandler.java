package Test;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * class : ServerHandler 
 * public class ServerHandler�� ������ ���� Ŭ���̾�Ʈ���� ������ 
 * ���������� ó���ϴ� ���� ���� Thread Ŭ����.  
 * Ŭ���̾�Ʈ�κ����� �޽����� �޴� ����
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
	 * ������ : ServerHandler() �����ڴ� Ŭ���̾�Ʈ�κ��� ���� ������ ���� ������ socket�� �����ڸ� �޾� ������ ��.
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
	 * �޼ҵ� : init() init()�� �Է�/��� ��Ʈ���� ����, ���۸� ������ ��Ʈ���� ����Ͽ�, String�� �ְ���� �� �ִ�
	 * ����� ���� . Ŭ���̾�Ʈ ó���� �������� listener�� ���� ����� ����.
	 */
	public synchronized void loginit() {
		if (listener == null) {
			try {
				// �������κ��� �Է½�Ʈ���� ȹ��.
				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				// �������κ��� ��½�Ʈ���� ȹ��.
				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				System.out.println("0");
			
				// listener �����带 �����ϰ� ����.
				// this�� Server���� ������ handler ��ü�� �ǹ�.
				listener = new Thread(this);
				listener.start();
			} catch (IOException ignored) {
			}
		}

	}
	public synchronized void personinit() {
		if (listener == null) {
			try {
				// �������κ��� �Է½�Ʈ���� ȹ��.
//				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//				// �������κ��� ��½�Ʈ���� ȹ��.
//				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				obIn = new ObjectInputStream(socket.getInputStream());
				obOut = new ObjectOutputStream(socket.getOutputStream());
				// listener �����带 �����ϰ� ����.
				// this�� Server���� ������ handler ��ü�� �ǹ�.
				listener = new Thread(this);
				listener.start();
			} catch (IOException ignored) {
			}
		}
	}
	/**
	 * �޼ҵ� : stop() listener �����忡 ���ͷ�Ʈ�� �ɰ� dataOut ��Ʈ���� �ݴ� ����.
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

	// �޼ҵ� : run()
	// �������� ������ ���� ���� ���۵Ǵ� �κ�
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
				System.out.println("�α��νõ�");
				boolean checked = false;
				try {

					System.out.println(loginkey);

					for (int i = 0; i < 10; i++) {
						if (loginkey.equals(CheckKey.studentKey[i])) {
							dataOut.writeInt(2);
							// System.out.println("2��ȯ");
							dataOut.flush();
							checked = true;
							ck.mykey = loginkey;
							obIn = new ObjectInputStream(socket.getInputStream());
							obOut = new ObjectOutputStream(socket.getOutputStream());
							if (fileIsLive(ck.mykey + ".txt")) {
							//	System.out.println("��������");
							} else {
								ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream(ck.mykey + ".txt"));
							//	System.out.println(ck.mykey + ".txt���ϻ���");
							}
						}
					}
					if (loginkey.equals(CheckKey.adminKey)) {
						dataOut.writeInt(1);
						// System.out.println("1��ȯ");
						dataOut.flush();
						checked = true;
						ck.mykey = loginkey;
						if (fileIsLive(ck.mykey + ".txt")) {
							//System.out.println("��������");
							obIn = new ObjectInputStream(socket.getInputStream());
							obOut = new ObjectOutputStream(socket.getOutputStream());
						} else {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ck.mykey + ".txt"));
							//System.out.println(ck.mykey + ".txt���ϻ���");
						}
					} else if (checked == false) {
						dataOut.writeInt(3);
						// System.out.println("3��ȯ");
						dataOut.flush();
					}
				} catch (NoSuchElementException e) {
					System.out.println("������ �����ϼ̽��ϴ�.");
					stop();
				}

			}
		} catch (EOFException ignored) {
			System.out.println("������ �����ϼ̽��ϴ�.");
		} catch (IOException ie) {
			System.out.println("������ �����ϼ̽��ϴ�.");
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
