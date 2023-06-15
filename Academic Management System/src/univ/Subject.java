package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import addr.LogIn;
import univ.Student.Adapter;

public class Subject extends JFrame implements ActionListener{
	Database connect = new Database();
	
	JPanel jpHead;
	JLabel jlHead;
	
	JPanel jpInput;
	JLabel jlCode, jlSubject, jlyear, jlDept, jlGrade, jlTerm, jlTime, jlProf, jlCredit;
	JTextField jtCode, jtSubject, jtTime, jtCredit;
	JComboBox jcYear, jcDept, jcGrade, jcTerm, jcProf;
	String deptCode;
	String profCode;
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck, jbCheckAll;
	
	String[] fieldnames = {"subject", "dept_code", "grade", "prof_code"};
	String[] checkBox = {"교과명", "개설학과", "학년", "담당교수"};
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"교과코드", "교과명", "개설학과", "개설년도", "개설학년", "개설학기", "수업시수", "담당교수", "학점"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	String deptName = null;
	String profName = null;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;
	
	JPanel jpButton;
	JButton jbInput, jbChange, jbDelete, jbExit;
	
	LocalDate now = LocalDate.now();
	
	public Subject(){
		setTitle("교과목 관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 700);
		setVisible(true);
		
///////////////////////////////////////////////////////////////////////////		
		
		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("교과목 관리");
		jlHead.setFont(new Font("고딕", Font.BOLD, 25));
		jlHead.setForeground(Color.white);
		jpHead.add(jlHead);
	
///////////////////////////////////////////////////////////////////////////	

		jpInput = new JPanel();
		jpInput.setLayout(null);
		jpInput.setBounds(5, 50, 590, 200);
		TitledBorder t = new TitledBorder("입력");
		t.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpInput.setBorder(t);
		add(jpInput);

///////////////////////////////////////////////////////////////////////////		

		Font fInput = new Font("고딕", Font.PLAIN, 13);
		
		jlCode = new JLabel("교과목코드");
		jlCode.setBounds(20, 30, 70, 30);
		jlCode.setFont(fInput);
		
		jtCode = new JTextField();
		jtCode.setBounds(90, 32, 90, 25);
		
		jpInput.add(jlCode);
		jpInput.add(jtCode);
		
///////////////////////////////////////////////////////////////////////////
		
		jlSubject = new JLabel("개설강좌명");
		jlSubject.setBounds(200, 30, 70, 30);
		jlSubject.setFont(fInput);
		
		jtSubject = new JTextField();
		jtSubject.setBounds(270, 32, 90, 25);
		
		jpInput.add(jlSubject);
		jpInput.add(jtSubject);
		

//////////////////////////////////////////////////////////////////////////
		int year = now.getYear();
		
		jlyear = new JLabel("개설년도");
		jlyear.setBounds(380, 30, 70, 30);
		jlyear.setFont(fInput);
		
		jcYear = new JComboBox();
		jcYear.setBounds(450, 32, 120, 25);
		jcYear.addItem("개설년도");
		jcYear.addItem(year);
		
		
		jpInput.add(jlyear);
		jpInput.add(jcYear);
		
///////////////////////////////////////////////////////////////////////////
		
		jlDept = new JLabel("학 과");
		jlDept.setBounds(20, 80, 70, 30);
		jlDept.setFont(fInput);
		
		jcDept = new JComboBox();
		jcDept.setBounds(90, 82, 90, 25);
		jcDept.addItem("개설학과");
		
		try {
			String sql = "SELECT * FROM department";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("department");
				jcDept.addItem(name);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		jpInput.add(jlDept);
		jpInput.add(jcDept);
		
///////////////////////////////////////////////////////////////////////////
		
		jlGrade = new JLabel("개설학년");
		jlGrade.setBounds(200, 80, 70, 30);
		jlGrade.setFont(fInput);
		
		jcGrade = new JComboBox();
		jcGrade.setBounds(270, 82, 90, 25);
		jcGrade.addItem("개설학년");
		
		try {
			String sql = "SELECT DISTINCT grade FROM subject ORDER BY grade ASC";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String grade = rs.getString("grade");
				jcGrade.addItem(grade);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		jpInput.add(jlGrade);
		jpInput.add(jcGrade);
		
///////////////////////////////////////////////////////////////////////////
		
		jlTerm = new JLabel("개설학기");
		jlTerm.setBounds(380, 80, 70, 30);
		jlTerm.setFont(fInput);
		
		jcTerm = new JComboBox();
		jcTerm.setBounds(450, 82, 120, 25);
		jcTerm.addItem("개설학기");
		
		try {
			String sql = "SELECT DISTINCT term FROM subject ORDER BY term ASC";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String term = rs.getString("term");
				jcTerm.addItem(term);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		jpInput.add(jlTerm);
		jpInput.add(jcTerm);
		
///////////////////////////////////////////////////////////////////////////
		
		jlTime = new JLabel("수업시수");
		jlTime.setBounds(20, 130, 70, 30);
		jlTime.setFont(fInput);
		
		jtTime = new JTextField();
		jtTime.setBounds(90, 132, 90, 25);
		
		
		jpInput.add(jlTime);
		jpInput.add(jtTime);
		
///////////////////////////////////////////////////////////////////////////
		
		jlProf = new JLabel("담당교수");
		jlProf.setBounds(200, 130, 70, 30);
		jlProf.setFont(fInput);
		
		jcProf = new JComboBox();
		jcProf.setBounds(270, 132, 90, 25);
		jcProf.addItem("담당교수");
		
		try {
			String sql = "SELECT * FROM professor";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				while (rs.next()) {
				String name = rs.getString("name");
				jcProf.addItem(name);
				}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		jpInput.add(jlProf);
		jpInput.add(jcProf);
		
///////////////////////////////////////////////////////////////////////////
		
		jlCredit = new JLabel("개설학점");
		jlCredit.setBounds(380, 130, 70, 30);
		jlCredit.setFont(fInput);
		
		jtCredit = new JTextField();
		jtCredit.setBounds(450, 132, 120, 25);
		
		
		jpInput.add(jlCredit);
		jpInput.add(jtCredit);
		
///////////////////////////////////////////////////////////////////////////
		
		jpCheck = new JPanel();
		jpCheck.setLayout(null);
		jpCheck.setBounds(0, 255, 600, 65);
		jpCheck.setBackground(Color.gray);
		add(jpCheck);
		
		jcInput = new JComboBox(checkBox);
		jcInput.addItem("카테고리 선택");
		jcInput.setSelectedIndex(4);
		jcInput.setFont(fInput);
		jcInput.setBounds(20, 18, 150, 30);
		jpCheck.add(jcInput);
		
		jtCheck = new JTextField();
		jtCheck.setBounds(190, 18, 190, 30);
		jpCheck.add(jtCheck);
		
		jbCheck = new JButton("조회");
		jbCheck.setFont(fInput);
		jbCheck.setBackground(Color.green);
		jbCheck.setForeground(Color.red);
		jbCheck.setBounds(410, 18, 70, 30);
		jbCheck.addActionListener(this);
		jpCheck.add(jbCheck);
		
		jbCheckAll = new JButton("전체조회");
		jbCheckAll.setFont(fInput);
		jbCheckAll.setBackground(Color.green);
		jbCheckAll.setForeground(Color.red);
		jbCheckAll.setBounds(490, 18, 90, 30);
		jbCheckAll.addActionListener(this);
		jpCheck.add(jbCheckAll);
		
///////////////////////////////////////////////////////////////////////////	
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 325, 590, 270);		
		table.setRowHeight(20); // JTable 행 높이 조절
		
		table.getColumn("개설학과").setPreferredWidth(100);
		
		getListAll();
		add(scroll);
		table.addMouseListener(new Adapter());
		
///////////////////////////////////////////////////////////////////////////	

		Font fButton = new Font("고딕", Font.BOLD, 20);
		
		jpButton = new JPanel();
		jpButton.setLayout(null);
		jpButton.setBounds(0, 600, 600, 100);
		add(jpButton);
		
		jbInput = new JButton("등록");
		jbInput.setBounds(75, 10, 100, 30);
		jbInput.setFont(fButton);
		jbInput.addActionListener(this);
		jpButton.add(jbInput);
		
		
		jbChange = new JButton("수정");
		jbChange.setBounds(195, 10, 100, 30);
		jbChange.setFont(fButton);
		jbChange.addActionListener(this);
		jbChange.setEnabled(false);
		jpButton.add(jbChange);
		
		
		jbDelete = new JButton("삭제");
		jbDelete.setBounds(305, 10, 100, 30);
		jbDelete.setFont(fButton);
		jbDelete.addActionListener(this);
		jpButton.add(jbDelete);
		
		jbExit = new JButton("종료");
		jbExit.setBounds(415, 10, 100, 30);
		jbExit.setFont(fButton);
		jbExit.addActionListener(this);
		jpButton.add(jbExit);
		
///////////////////////////////////////////////////////////////////////////	
		
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	void clear() {
	jtCode.setText("");
	jtSubject.setText("");
	jtTime.setText("");
	jtCredit.setText("");
	jcInput.setSelectedIndex(4);
	jcYear.setSelectedIndex(0);
	jcDept.setSelectedIndex(0);
	jcGrade.setSelectedIndex(0);
	jcTerm.setSelectedIndex(0);
	jcProf.setSelectedIndex(0);
	jtCode.setEnabled(true);
	jbInput.setEnabled(true);
	jbChange.setEnabled(false);
	}

///////////////////////////////////////////////////////////////////////////	

	public void getListAll() {
		model.setRowCount(0);

		String sql = "SELECT * FROM subject";
		
		try {
			pstmt = connect.conn.prepareStatement(sql);
		
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				String code = rs.getString(1);
				String subject = rs.getString(2);
				String dept = rs.getString(3);
				String year = rs.getString(4);
				String grade = rs.getString(5);
				String term = rs.getString(6);
				String time = rs.getString(7);
				String prof = rs.getString(8);
				String credit = rs.getString(9);
				
				
				String sql1 = "SELECT * FROM department where code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, dept);
				
				ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						deptName = rs1.getString("department");
					}
					
				String sql2 = "SELECT * FROM professor where code = ?";
				pstmt = connect.conn.prepareStatement(sql2);
				pstmt.setString(1, prof);
					
				ResultSet rs2 = pstmt.executeQuery();
					while (rs2.next()) {
						profName = rs2.getString("name");
					} 
					
				Object[] newdata = {code, subject, deptName, year, grade, term, time, profName, credit};
				model.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////	

	public int regist(String code, String subject, String dept, String year, String grade, String term, String time, String prof, String credit) {
		int result = 0;
		String sql = "INSERT INTO subject(code, subject, dept_code, year, grade, term, time, prof_code, credit) VALUE(?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, code);
			pstmt.setString(2, subject);
			pstmt.setString(3, dept);
			pstmt.setString(4, year);
			pstmt.setString(5, grade);
			pstmt.setString(6, term);
			pstmt.setString(7, time);
			pstmt.setString(8, prof);
			pstmt.setString(9, credit);
			
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public int edit(String code, String subject, String dept, String year, String grade, String term, String time, String prof, String credit) {
		int result = 0;
		String sql = "UPDATE subject SET subject = ?, dept_code = ?, year = ?, grade = ?, term = ?, time = ?, prof_code = ?, credit = ? WHERE code = ?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, dept);
			pstmt.setString(3, year);
			pstmt.setString(4, grade);
			pstmt.setString(5, term);
			pstmt.setString(6, time);
			pstmt.setString(7, prof);
			pstmt.setString(8, credit);
			pstmt.setString(9, code);
			
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public int delete (String code) {
		int result = 0;
		String sql = "DELETE FROM subject WHERE code =?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, code);	
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
///////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		String empty = jtCheck.getText();
		
		String code = jtCode.getText();
		String subject = jtSubject.getText();
		String time = jtTime.getText();
		String credit = jtCredit.getText();
		String year = jcYear.getSelectedItem().toString();
		String dept = jcDept.getSelectedItem().toString();
		String grade = jcGrade.getSelectedItem().toString();
		String term = jcTerm.getSelectedItem().toString();
		String prof = jcProf.getSelectedItem().toString();
		
		try {
			String sql = "SELECT * FROM department where department =?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, dept);
			rs = pstmt.executeQuery();
				while (rs.next()) {
					deptCode = rs.getString("code");
				}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			String sql1 = "SELECT * FROM professor where name =?";
			pstmt = connect.conn.prepareStatement(sql1);
			pstmt.setString(1, prof);
			rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					profCode = rs1.getString("code");
				}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (ob == jbInput) {
			if (code.isEmpty()) {
				JOptionPane.showMessageDialog(this, "교과목코드를 입력해주세요");
				jtCode.requestFocus();
				return;
			}
			if (subject.isEmpty()) {
				JOptionPane.showMessageDialog(this, "개설강좌명을 입력해주세요");
				jtSubject.requestFocus();
				return;
			}
			if (time.isEmpty()) {
				JOptionPane.showMessageDialog(this, "수업시수을 입력해주세요");
				jtTime.requestFocus();
				return;
			}
			if (credit.isEmpty()) {
				JOptionPane.showMessageDialog(this, "개설학점을 입력해주세요");
				jtCredit.requestFocus();
				return;
			}
			if (year.equals("개설년도")) {
				JOptionPane.showMessageDialog(this, "개설년도를 입력해주세요");
				jcYear.requestFocus();
				return;
			}
			if (dept.equals("개설학과")) {
				JOptionPane.showMessageDialog(this, "개설학과을 입력해주세요");
				jcDept.requestFocus();
				return;
			}
			if (grade.equals("개설학년")) {
				JOptionPane.showMessageDialog(this, "개설학년을 입력해주세요");
				jcGrade.requestFocus();
				return;
			}
			if (term.equals("개설학기")) {
				JOptionPane.showMessageDialog(this, "개설학기을 입력해주세요");
				jcTerm.requestFocus();
				return;
			}
			if (prof.equals("담당교수")) {
				JOptionPane.showMessageDialog(this, "담당교수를 입력해주세요");
				jcProf.requestFocus();
				return;
			}
			
			
			Object rowData[] = {code, subject, deptCode, year, grade, term, time, profCode, credit}; 
			model.addRow(rowData);
			
			int result = regist(code, subject, deptCode, year, grade, term, time, profCode, credit);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "등록되었습니다.");
				getListAll();
				clear();
			}else
				clear();
			}
		
		
		if (ob == jbChange) {
			int result = edit(code, subject, deptCode, year, grade, term, time, profCode, credit);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "수정되었습니다.");
				getListAll();
				clear();
			}else
				clear();
		}
		
		if (ob == jbDelete) {
			if (srow < 0) { // JTable에서 선택된 행이 없다면 -1 리턴
				JOptionPane.showMessageDialog(null, "삭제할 정보를 선택하세요");
				return;
			}
			code = (String) model.getValueAt(srow, 0);
			
			int yn = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까", "INDEX", JOptionPane.YES_NO_OPTION);
			
			if (yn == JOptionPane.YES_OPTION) {
				int result = delete(code);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				getListAll();
				clear();
			}else
				clear();
		}
		
		if (ob == jbExit) {
			dispose();
		}
		
		if (ob == jbCheckAll) {	
			getListAll();
			clear();
		}else if (ob == jbCheck) {	
			if (empty.isEmpty()) {
				JOptionPane.showMessageDialog(this, "조회할 내용을 선택해주세요");			
			}
			else {
				String field = fieldnames[jcInput.getSelectedIndex()];
				String value = jtCheck.getText();
				model.setRowCount(0);
				try {		
					String sql = "SELECT * FROM subject WHERE " + field + " = ?";
					pstmt = connect.conn.prepareStatement(sql);
					pstmt.setString(1, value);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						code = rs.getString(1);
						subject = rs.getString(2);
						deptCode = rs.getString(3);
						year = rs.getString(4);
						grade = rs.getString(5);
						term = rs.getString(6);
						time = rs.getString(7);
						profCode = rs.getString(8);
						credit = rs.getString(9);
						
						String sql1 = "SELECT * FROM department where code = ?";
						pstmt = connect.conn.prepareStatement(sql1);
						pstmt.setString(1, dept);
						
						ResultSet rs1 = pstmt.executeQuery();
							while (rs1.next()) {
								deptName = rs1.getString("department");
							}
							
						String sql2 = "SELECT * FROM professor where code = ?";
						pstmt = connect.conn.prepareStatement(sql2);
						pstmt.setString(1, prof);
							
						ResultSet rs2 = pstmt.executeQuery();
							while (rs2.next()) {
								profName = rs2.getString("name");
							} 
						
						Object[] newdata = {code, subject, deptName, year, grade, term, time, profName, credit};
						model.addRow(newdata);
						}
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			jtCheck.setText("");
			clear();
		
		}
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	class Adapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			jbChange.setEnabled(true);
			jbInput.setEnabled(false);
			
			srow = table.getSelectedRow();
			
			String code = model.getValueAt(srow, 0).toString();
			String subject = model.getValueAt(srow, 1).toString();
			String dept = model.getValueAt(srow, 2).toString();		
			String year = model.getValueAt(srow, 3).toString();			
			String grade = model.getValueAt(srow, 4).toString();
			String term = model.getValueAt(srow, 5).toString();
			String time = model.getValueAt(srow, 6).toString();
			String prof = model.getValueAt(srow, 7).toString();
			String credit = model.getValueAt(srow, 8).toString();
		
			jtCode.setText(code);
			jtSubject.setText(subject);
			jtTime.setText(time);
			jtCredit.setText(credit);
			
			jcDept.setSelectedItem(dept);
			jcYear.setSelectedItem(year);
			jcGrade.setSelectedItem(grade);
			jcTerm.setSelectedItem(term);
			jcProf.setSelectedItem(prof);
			
			jtCode.setEnabled(false);
		}	
	}
}
