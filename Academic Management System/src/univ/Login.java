package univ;

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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import addr.JP_addr;



public class Login extends JFrame implements ActionListener{
	Database connect = new Database();
	
	Menu menu;
	
	JPanel pHead, pInput, pButton;
	JLabel lLogin, lId, lPassword, lName;
	JTextField tId, tPassword, tName;
	JButton bEnter;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	Font f1, f2;
	
	String value = "";
	
	
	Login(){
		setTitle("LogIn");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setSize(700, 500);
		setLayout(null);
		
		f1 = new Font("맑은 고딕", Font.BOLD, 25);
		f2 = new Font("맑은 고딕", Font.BOLD, 15);
		
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
		pButton.setBackground(Color.LIGHT_GRAY);
		pButton.setBounds(0, 280, 700, 80);
		
		bEnter = new JButton(" 확인", icon1);
		bEnter.setRolloverIcon(icon2);
		bEnter.setPressedIcon(icon3);
		bEnter.setFocusable(false); //버튼 누르면 버튼 테두리 
		bEnter.setContentAreaFilled(false); //버튼 영역 채우기 없기
		bEnter.setBorderPainted(false); //버튼 경계 감추기
		bEnter.setForeground(Color.white);
		bEnter.setBounds(450, 18, 150, 40);
		bEnter.setFont(f1);
		bEnter.addActionListener(this); 

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
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		value = tId.getText();
		String profName = null;
		String stdName = null;
		String stdCode = null;
		String profCode = null;
		
		if (ob == bEnter) {
			if (value.equals("")) {
				JOptionPane.showMessageDialog(this, "아이디를 다시 입력해주세요");
				tId.setText(null);
				tPassword.setText(null);
				tName.setText(null);
				
			}else {
				try {
					String sql = "SELECT * FROM admin where id =?";
					pstmt = connect.conn.prepareStatement(sql);
					pstmt.setString(1, value);
					rs = pstmt.executeQuery();		
					
					while(rs.next()) { // 행을 하나씩 내려감
						String id = rs.getString("id");
						String password = rs.getString("password");
						String name = rs.getString("owner");
						String auth	= rs.getString("auth");
						
						if (tId.getText().equals(id) && tPassword.getText().equals(password)) {
							if (auth.equals("a")) {
								JOptionPane.showMessageDialog(this, name + "관리자 로그인 성공");
								menu = new Menu("a");
								menu.setTitle("학사관리시스템 사용자: "+ name + " " + "관리자");
								dispose();
								break;
							}
							if(auth.equals("p")) {
								String sql1 = "SELECT * FROM professor where code =?";
								pstmt = connect.conn.prepareStatement(sql1);
								pstmt.setString(1, name);
								rs = pstmt.executeQuery();		
									while(rs.next()) { // 행을 하나씩 내려감
										profName = rs.getString("name");
										profCode = rs.getString("code");
									}
								tName.setText(profName);
								JOptionPane.showMessageDialog(this, profName + "교수님 로그인 성공");
								menu = new Menu(profCode);
								menu.setTitle("학사관리시스템 사용자: "+ name + " " + profName);
								dispose();
								break;
							}
							if(auth.equals("s")) {
								String sql2 = "SELECT * FROM student where code =?";
								pstmt = connect.conn.prepareStatement(sql2);
								pstmt.setString(1, name);
								rs = pstmt.executeQuery();		
									while(rs.next()) { // 행을 하나씩 내려감
										stdName = rs.getString("name");
										stdCode = rs.getString("code");
									}
								tName.setText(stdName);
								JOptionPane.showMessageDialog(this, stdName + "학생 로그인 성공");
								menu = new Menu(stdCode);
								menu.setTitle("학사관리시스템 사용자: "+ name + " " + stdName);
								dispose();
								break;
							}
						
						}else if (tId.getText().equals(id)){
							JOptionPane.showMessageDialog(this, "비밀번호를 다시 입력해주세요");
							tId.setText(null);
							tPassword.setText(null);
							tName.setText(null);
							break;
						}else if (tPassword.getText().equals(password)){
							JOptionPane.showMessageDialog(this, "아이디를 다시 입력해주세요");
							tId.setText(null);
							tPassword.setText(null);
							tName.setText(null);
							break;
						}else {
							JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 다시 입력해주세요");
							tId.setText(null);
							tPassword.setText(null);
							tName.setText(null);
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
	public static void main(String[] args) {
		new Login();

	}
}
