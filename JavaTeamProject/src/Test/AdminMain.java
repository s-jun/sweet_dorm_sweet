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
        ct.add(new JButton("호실관리"));
        ct.add(new JButton("공지"));
        ct.add(new JButton("식단표 업데이트"));
        ct.add(new JButton("외박신청확인"));
    }
}