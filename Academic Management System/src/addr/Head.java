package addr;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class Head extends JPanel{
	JP_addr addrH;
	
	JLabel jlHead;
	
	Head(JP_addr addr){
		this.addrH = addr;
		System.out.println("제목 연결 완료");
		
		setLayout(null);
		setBounds(0,0,705,50);
		setBackground(Color.BLACK);
		
		jlHead = new JLabel();
		jlHead.setBounds(115, 10, 500, 30);
		jlHead.setText("컨버젼스 개발과정 주소록관리 프로그램");
		jlHead.setFont(new Font("돋움", Font.BOLD, 25));
		jlHead.setForeground(Color.white);
		
		add(jlHead);
		
		addrH.add(this);
	
}
}
