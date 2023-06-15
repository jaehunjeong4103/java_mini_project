package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Result_std extends JFrame implements ActionListener {
Database connect = new Database();
	
	JPanel jpHead;
	JLabel jlHead;
	
	
	JPanel jpInput;
	JLabel jlCode, jlName, jlyear, jlGrade, jlTerm;
	JTextField jtCode, jtName;
	
	JComboBox jcYear, jcGrade, jcTerm;
	
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck;
	
	String[] fieldnames = {"code", "department", "major"};
	String[] checkBox = {"학과코드", "학과명", "전공명"};
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"교과목명", "출 석", "레포트", "중 간", "기 말", "가산점", "합 계", "학 점"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs;
	String subject = null;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;
	
	
	JPanel jpButton;
	JButton jbExit;
	
	String sc;
	
	public Result_std(String sc){
		this.sc = sc;
		
		setTitle("성적관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 640);
		setVisible(true);
		
/////////////////////////////////////////////////////////////////////////

		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("성 적 관 리");
		jlHead.setFont(new Font("고딕", Font.BOLD, 25));
		jlHead.setForeground(Color.white);
		jpHead.add(jlHead);
		
/////////////////////////////////////////////////////////////////////////////
		
		jpInput = new JPanel();
		jpInput.setLayout(null);
		jpInput.setBounds(5, 50, 590, 130);
		TitledBorder t = new TitledBorder("입력");
		t.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpInput.setBorder(t);
		add(jpInput);
		
/////////////////////////////////////////////////////////////////////////////		

		Font fInput = new Font("고딕", Font.PLAIN, 13);
		
		jlCode = new JLabel("학생코드");
		jlCode.setBounds(20, 30, 70, 30);
		jlCode.setFont(fInput);
		
		jtCode = new JTextField();
		jtCode.setBounds(90, 32, 90, 25);
		jtCode.setText(sc);
		jtCode.setEnabled(false);
		
		jpInput.add(jlCode);
		jpInput.add(jtCode);
		
///////////////////////////////////////////////////////////////////////////
		
		jlName = new JLabel("학생이름");
		jlName.setBounds(200, 30, 70, 30);
		jlName.setFont(fInput);
		
		jtName = new JTextField();
		jtName.setBounds(270, 32, 90, 25);
		
		try {
			String sql = "SELECT * FROM student where code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, sc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				jtName.setText(name);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		jtName.setEnabled(false);
		
		jpInput.add(jlName);
		jpInput.add(jtName);
		
///////////////////////////////////////////////////////////////////////////

		jlyear = new JLabel("개설년도");
		jlyear.setBounds(380, 30, 70, 30);
		jlyear.setFont(fInput);
		
		jcYear = new JComboBox();
		jcYear.setBounds(450, 32, 120, 25);
		jcYear.addItem("개설년도");
		
		try {
			String sql = "SELECT DISTINCT year FROM subject ORDER BY year ASC";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String year = rs.getString("year");
				jcYear.addItem(year);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		jpInput.add(jlyear);
		jpInput.add(jcYear);
		
///////////////////////////////////////////////////////////////////////////

		jlGrade = new JLabel("개설학년");
		jlGrade.setBounds(20, 80, 70, 30);
		jlGrade.setFont(fInput);
		
		jcGrade = new JComboBox();
		jcGrade.setBounds(90, 82, 90, 25);
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
		jlTerm.setBounds(200, 80, 70, 30);
		jlTerm.setFont(fInput);
		
		jcTerm = new JComboBox();
		jcTerm.setBounds(270, 82, 90, 25);
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
		
		jpCheck = new JPanel();
		jpCheck.setLayout(null);
		jpCheck.setBounds(0, 200, 600, 65);
		jpCheck.setBackground(Color.gray);
		add(jpCheck);
		
		jcInput = new JComboBox(checkBox);
		jcInput.addItem("검색할 교과목 선택하세요");
		jcInput.setSelectedIndex(3);
		jcInput.setFont(fInput);
		jcInput.setBounds(20, 18, 180, 30);
		jpCheck.add(jcInput);
		
		jtCheck = new JTextField();
		jtCheck.setBounds(220, 18, 190, 30);
		jpCheck.add(jtCheck);
		
		jbCheck = new JButton("조회");
		jbCheck.setFont(fInput);
		jbCheck.setBackground(Color.green);
		jbCheck.setForeground(Color.red);
		jbCheck.setBounds(490, 18, 90, 30);
		jbCheck.addActionListener(this);
		jpCheck.add(jbCheck);
		
///////////////////////////////////////////////////////////////////////////	
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 270, 590, 270);		
		table.setRowHeight(20); // JTable 행 높이 조절
	
		getListAll();
		add(scroll);
		//table.addMouseListener(new Adapter());
		
///////////////////////////////////////////////////////////////////////////	

		Font fButton = new Font("고딕", Font.BOLD, 20);
		
		jpButton = new JPanel();
		jpButton.setLayout(null);
		jpButton.setBounds(0, 540, 600, 100);
		add(jpButton);
		
		jbExit = new JButton("종료");
		jbExit.setBounds(492, 15, 100, 30);
		jbExit.setFont(fButton);
		jbExit.addActionListener(this);
		jpButton.add(jbExit);
		
///////////////////////////////////////////////////////////////////////////	
	}
	
public void getListAll() {
		
		
		model.setRowCount(0);
		
		try {
			String sql = "SELECT * FROM result where std_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, sc);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String code = rs.getString("std_code");
				String profCode = rs.getString("prof_code");
				String attended = rs.getString("attended");
				String report = rs.getString("report");
				String middle = rs.getString("middle");
				String fina = rs.getString("final");
				String added = rs.getString("added");
				String grade = rs.getString("grade_value");
				
				int att = Integer.parseInt(attended);
				int rep = Integer.parseInt(report);
				int mid = Integer.parseInt(middle);
				int fi = Integer.parseInt(fina);
				int add = Integer.parseInt(added);
				int sum = att+rep+mid+fi+add;
				
				String sql1 = "SELECT * FROM subject where prof_code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, profCode);
			
				ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						subject = rs1.getString("subject");
					}
					
				Object[] newdata = {subject, attended, report, middle, fina, added, sum, grade};
				model.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if (ob == jbExit) {
			dispose();
		}
		
	}
	
}
