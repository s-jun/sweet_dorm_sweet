package Test;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.net.*;

public class StudentInfo extends JFrame implements ActionListener{
	StudentInfo info;
	ServerHandler serverh;
	private Socket socket;
	protected ObjectInputStream obIn;
	protected ObjectOutputStream obOut;
	
	private ButtonGroup jbg1;
	private ButtonGroup jbg2;

    private JButton btnLogin;
    private JButton btnInit;
    private JTextField userText;
    private boolean bLoginCheck;
	private JRadioButton jrb1;
	private JRadioButton jrb2;
	
	private String sex;
	Cardkey ck = new Cardkey(null, sex, null, false);
	
	public StudentInfo(){
		setTitle("정보입력/수정");
		setSize(300, 200);
		setResizable(false);
        setLocation(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        // panel
        JPanel panel = new JPanel();
        placeInfoPanel(panel);
       
       
        // add
        add(panel);
       
        // visiible
        setVisible(true);
    }
   
    public void placeInfoPanel(JPanel panel){
        panel.setLayout(null);
        jrb1 = new JRadioButton("남자", false);
		jrb2 = new JRadioButton("여자", false);
		jbg1 = new ButtonGroup();
		
		jbg1.add(jrb1);
		jbg1.add(jrb2);
		
        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(10, 10, 80, 25);
        panel.add(nameLabel);
       
        JLabel sexLabel = new JLabel("성별");
        sexLabel.setBounds(10, 40, 80, 25);
        panel.add(sexLabel);
//        JLabel menLabel = new JLabel("남");
//        menLabel.setBounds(100, 40, 40, 25);
//        panel.add(menLabel);
//        JLabel womenLabel = new JLabel("여");
//        womenLabel.setBounds(170, 40, 40, 25);
//        panel.add(womenLabel);
        
        JLabel ageLabel = new JLabel("나이");
        ageLabel.setBounds(10, 70, 80, 25);
        panel.add(ageLabel);
       
        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);
        
        String name;  
        name = userText.getText();
        jrb1.setBounds(100,40,60,25);
        jrb2.setBounds(170,40,60,25);
        panel.add(jrb1);
        panel.add(jrb2);
        
        userText = new JTextField(20);
        userText.setBounds(100, 70, 160, 25);
        panel.add(userText);
        String age;
        age = userText.getText();
       
       
        btnLogin = new JButton("submit");
        btnLogin.setBounds(160, 100, 100, 25);
        panel.add(btnLogin);
        try {
			// ip, port를 인자로 Socket형 객체를 생성
			// 사용자 환경에 따라 ip와 port값을 변경해서 사용
			socket = new Socket("127.0.0.1", 9000);
			// 소켓으로부터 입력스트림을 획득
			obIn = new ObjectInputStream(socket.getInputStream());
			// 소켓으로부터 출력스트림을 획득
			obOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ie) {
			stop();
		}
        ck = new Cardkey(name, sex, age, false);
    }
        @Override
	public void actionPerformed(ActionEvent arg0) {
        //jrb1~jrb3중에서 선택된 버튼의 Text값을 size 변수에 대입 위한 제어문
        		if(jrb1.isSelected()){
        			sex = jrb1.getText();
        		}
        		else if(jrb2.isSelected()){
        			sex = jrb2.getText();
        		}
        		try {
					obOut.writeObject(ck);
					obOut.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
		
	}
        public void stop() {
    		try {
    			obIn.close();
    			obOut.close();
    			socket.close();

    		} catch (IOException e) {

    		}
    	}
}

