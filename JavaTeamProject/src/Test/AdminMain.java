package Test;
import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
 
public class AdminMain extends JFrame{
    public AdminMain() {
        setSize(300, 300);
        setTitle("AdminClient");
        setVisible(true);
        Container ct = getContentPane();
        GridLayout gl = new GridLayout(2,2,10,10);
        ct.setLayout(gl);
        ct.add(new JButton("ȣ�ǰ���"));
        ct.add(new JButton("����"));
        ct.add(new JButton("�Ĵ�ǥ ������Ʈ"));
        ct.add(new JButton("�ܹڽ�ûȮ��"));
    }
}