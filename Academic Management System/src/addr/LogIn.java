package addr;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class LogIn extends JFrame{
	JPanel pHead, pInput, pButton;
	JLabel lLogin, lId, lPassword, lName;
	JTextField tId, tPassword, tName;
	JButton bEnter;
	
	public LogIn() {
		setTitle("LogIn");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setSize(700, 500);
		setLayout(null);
		
		Font f1 = new Font("맑은 고딕", Font.BOLD, 25);
		Font f2 = new Font("맑은 고딕", Font.BOLD, 15);
		
		ImageIcon icon1 = new ImageIcon("D://javaData//image//4_band.jpg");
		ImageIcon icon2 = new ImageIcon("D://javaData//image//2_insta.jpg");
		ImageIcon icon3 = new ImageIcon("D://javaData//image//1_talk.jpg");
		
		pHead = new JPanel();
		pHead.setLayout(null);
		pHead.setBackground(Color.darkGray);
		pHead.setBounds(0, 0, 700, 70);
		
		lLogin = new JLabel("로 그 인");		
		lLogin.setForeground(Color.white);
		lLogin.setBounds(289, 20, 100, 25);
		lLogin.setFont(f1);
		
		pInput = new JPanel();
		TitledBorder t = new TitledBorder("입 력");
		t.setTitleFont(new Font("맑은 고딕", Font.BOLD, 15));
		pInput.setBorder(t);
		pInput.setLayout(null);
		pInput.setBounds(2, 85, 680, 180);
		
		lId = new JLabel("아이디");		
		lId.setForeground(Color.black);
		lId.setBounds(30, 85, 100, 15);
		lId.setFont(f2);
		
		tId = new JTextField();
		tId.setForeground(Color.black);
		tId.setBounds(85, 81, 100, 25);
		
		lPassword = new JLabel("패스워드");
		lPassword.setForeground(Color.black);
		lPassword.setBounds(240, 85, 100, 15);
		lPassword.setFont(f2);
		
		tPassword = new JTextField();
		tPassword.setForeground(Color.black);
		tPassword.setBounds(310, 81, 100, 25);
		
		lName = new JLabel("사용자이름");
		lName.setForeground(Color.black);
		lName.setBounds(450, 85, 100, 15);
		lName.setFont(f2);
		
		tName = new JTextField();
		tName.setForeground(Color.black);
		tName.setBounds(535, 81, 100, 25);	
		
		pButton = new JPanel();
		pButton.setLayout(null);
		pButton.setBackground(Color.pink);
		pButton.setBounds(0, 280, 700, 80);
		
		bEnter = new JButton(" 확인", icon1);
		bEnter.setRolloverIcon(icon2);
		bEnter.setPressedIcon(icon3);
		bEnter.setFocusable(false); //버튼 누르면 버튼 테두리 
		bEnter.setContentAreaFilled(false); //버튼 영역 채우기 없기
		bEnter.setBorderPainted(false); //버튼 경계 감추기
		bEnter.setForeground(Color.white);
		bEnter.setBackground(Color.pink);
		bEnter.setBounds(450, 18, 150, 40);
		bEnter.setFont(f1);
		bEnter.addActionListener(new MyListener(this)); 

		pHead.add(lLogin);
		pButton.add(bEnter);
		pInput.add(lId);
		pInput.add(tId);
		pInput.add(lPassword);
		pInput.add(tPassword);
		pInput.add(lName);
		pInput.add(tName);
		
		add(pHead);
		add(pInput);
		add(pButton);

		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		LogIn l = new LogIn();	
	}
}

class MyListener implements ActionListener{
	JP_addr addrM;
	
	DbConnect con = new DbConnect();
	PreparedStatement pstmt;
	ResultSet rs;
		
	LogIn l;
	
	MyListener(LogIn l){
		this.l = l;
	}
	public void actionPerformed(ActionEvent e) {
		String value = "";
		value = l.tId.getText();
		
		if (e.getActionCommand().equals(" 확인")) {
			if (value.equals("")) {
				JOptionPane.showMessageDialog(this.l, "아이디를 다시 입력해주세요");
				l.tId.setText(null);
				l.tPassword.setText(null);
				l.tName.setText(null);
			}
			else {
			try {
				String sql = "SELECT * FROM login where id =?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, value);
				rs = pstmt.executeQuery();		
				
				while(rs.next()) { // 행을 하나씩 내려감
					String id = rs.getString("id");
					String password = rs.getString("password");
					String name = rs.getString("name");
					
					if (l.tId.getText().equals(id) && l.tPassword.getText().equals(password)) {
						JOptionPane.showMessageDialog(this.l, "로그인 성공");
						l.tName.setText(name);
						addrM = new JP_addr();
						addrM.setTitle("사용자: "+name);
						l.dispose();
						break;
						
					}else if (l.tId.getText().equals(id)){
						JOptionPane.showMessageDialog(this.l, "비밀번호를 다시 입력해주세요");
						l.tId.setText(null);
						l.tPassword.setText(null);
						l.tName.setText(null);
						break;
					}else if (l.tPassword.getText().equals(password)){
						JOptionPane.showMessageDialog(this.l, "아이디를 다시 입력해주세요");
						l.tId.setText(null);
						l.tPassword.setText(null);
						l.tName.setText(null);
						break;
					}else {
						JOptionPane.showMessageDialog(this.l, "아이디와 비밀번호를 다시 입력해주세요");
						l.tId.setText(null);
						l.tPassword.setText(null);
						l.tName.setText(null);
						break;
					}
					}
				}
			catch (SQLException e1) {
				e1.printStackTrace();
				}
			
			}
		}	
	}
}


