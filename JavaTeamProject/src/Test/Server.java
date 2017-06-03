package Test;
import java.io.*;
import java.net.*;

// class : Server 
// public static void main(String args[]) throws IOException ...
// Ŭ���̾�Ʈ�� ������ ����ؼ� ��ٸ��� ���ӽ� �̸� PizzaHandler�� �����ڿ� �Ѱ��ְ�, 
// PizzaHandler�� ��ü�� ����.

public class Server {
	
//  method : main() 
//  ������ �°� ���Դ��� ��������(������ ����� ��Ʈ ��ȣ�� �˻�), 
//  �� ���� ServerSocket��ü�� �����ϰ� ������ �����鼭, ServerSocket�� accept() 
//  �޼ҵ带 ���� Ŭ���̾�Ʈ�κ����� �������. 
//  �� ���ῡ ���Ͽ� PizzaHandler Ŭ������ �ν��Ͻ��� ���� ����µ�, 
//  ���⿡ accept()�� ��� �� �� ������ �������� �Ű������� ��. 
//  �ڵ鷯 ��ü�� ���� �Ŀ��� start() �޼ҵ�� �̰��� ���۽�Ű��, 
//  �̶� �־��� ������ ó���� �� �ִ� �����尡 ���� ���ư��� ��.  
//  �׸��� �� ����, ���� ������ ����Ͽ� ������ ���鼭 ���ο� ������ ��ٸ��� ��.

	public static void main(String args[]) throws IOException {
	// ���� �� ���ڷ� ������ port��ȣ�� ���� �ʾҴٸ� ���ܸ� �߻����� 
	// ��Ʈ��ȣ�� ���ڷ� �ٽ� ����.
	  if (args.length != 1)
		throw new IllegalArgumentException("Syntax: Server <port>");
		// ���ڷ� ���� String�� ��Ʈ��ȣ�� int�� port�� ����.
		int port = Integer.parseInt(args[0]);
		// �������� ����.
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server Started..!!!");
		// �ټ��� Ŭ���̾�Ʈ�� ������ �޾Ƶ帮�� ���ؼ� ���ѹݺ� ����.
		while (true) {
			// Ŭ���̾�Ʈ�� ������ ���.
			// Ŭ���̾�Ʈ�� �����ϸ� Socket�� ��ü client�� ����.
		Socket client = server.accept();
		// ������ Ŭ���̾�Ʈ�� ������ ���.
		System.out.println("Accepted from: " + client.getInetAddress());
		// Ŭ���̾�Ʈ socket��ü�� ���ڷ� PizzaHandler�� ��ü�� ����.
		ServerHandler handler = new ServerHandler(client);
		// handler�� loginit() �޼ҵ带 ȣ��.
		
		handler.loginit();
		}

	}
}
