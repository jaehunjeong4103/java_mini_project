package univ;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener{
	Database connect = new Database();
	
	Admin admin;
	Department department;
	Professor professor;
	Student student;
	Subject subject;
	Attended attended;
	Result_prof reProf;
	Result_std reStd;
	
	JMenuBar menuBar;
	JMenu stManage, profManage, deptManage, subManage, userResist, attManage, resultManage;
	JMenuItem stm, profm, deptm, subm, userm, attm, resultProf, resultStd;
	JPanel panel;
	
	
	
	PreparedStatement pstmt;
	ResultSet rs;
	String name = null;
	String deptName = null;
	String stdCode = "";
	String profCode = "";
	
	public Menu(String s){
		setSize(500, 500);
				
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		panel = new JPanel();
		add(panel);
		
		stManage = new JMenu("학생관리");
		stm = new JMenuItem("학생관리");

		profManage = new JMenu("교수관리");
		profm = new JMenuItem("교수관리");

		deptManage = new JMenu("학과관리");
		deptm = new JMenuItem("학과관리");
		
		subManage = new JMenu("교과목관리");
		subm = new JMenuItem("교과목관리");
		
		attManage = new JMenu("수강관리");
		attm = new JMenuItem("수강관리");
		
		resultManage = new JMenu("성적관리");
		resultProf = new JMenuItem("성적관리");
		resultStd = new JMenuItem("성적관리");
		
		userResist = new JMenu("사용자등록");
		userm = new JMenuItem("사용자등록");

		
		if ("p".equals(s.substring(0, 1))) {
			profCode = s;
			professor();
		}else if (s.equals("a")) {
			admin();
		}else if ("s".equals(s.substring(0, 1))) {
			stdCode = s;
			student();
		}
		
		setVisible(true);
	}
	
	public void admin() {
		menuBar.add(stManage);
		stManage.add(stm);
		stm.addActionListener(this);
		
		menuBar.add(profManage);
		profManage.add(profm);
		profm.addActionListener(this);
		
		menuBar.add(deptManage);
		deptManage.add(deptm);
		deptm.addActionListener(this);
		
		menuBar.add(subManage);
		subManage.add(subm);
		subm.addActionListener(this);
		
		menuBar.add(userResist);
		userResist.add(userm);
		userm.addActionListener(this);		
	}
	
	public void professor() {
		menuBar.add(subManage);
		subManage.add(subm);
		subm.addActionListener(this);
		
		menuBar.add(resultManage);
		resultManage.add(resultProf);
		resultProf.addActionListener(this);
		
		menuBar.add(userResist);
		userResist.add(userm);
		userm.addActionListener(this);
	}
	
	public void student() {
		menuBar.add(attManage);
		attManage.add(attm);
		attm.addActionListener(this);
		
		menuBar.add(resultManage);
		resultManage.add(resultStd);
		resultStd.addActionListener(this);
		
		menuBar.add(userResist);
		userResist.add(userm);
		userm.addActionListener(this);
	}
	
	
	
	public static void main(String[] args) {
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();	
		if (ob == userm) {
			admin = new Admin();
			admin.setTitle(this.getTitle());
		}
		if (ob == deptm) {
			department = new Department();
		}
		if (ob == profm) {
			professor = new Professor();
		}
		if (ob == stm) {
			student = new Student();
		}
		if (ob == subm) {
			subject = new Subject();
		}
		if (ob == resultProf) {
			reProf = new Result_prof(profCode);
			
		}
		if (ob == resultStd) {
			reStd = new Result_std(stdCode);
		}
		if (ob == attm) {
			attended = new Attended(stdCode);
			try {
				String sql = "SELECT * FROM student where code =?";
				pstmt = connect.conn.prepareStatement(sql);
				pstmt.setString(1, stdCode);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String name = rs.getString("name");
					String dept = rs.getString("dept_code");
					
					String sql1 = "SELECT * FROM department where code =?";
					pstmt = connect.conn.prepareStatement(sql1);
					pstmt.setString(1, dept);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						deptName = rs.getString("department");
						
					}
					attended.jtName.setText(name);
					attended.jtDept.setText(deptName);
				}
				
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			attended.jtCode.setText(stdCode);
		}
		
	}

}
