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

import javax.swing.DefaultCellEditor;
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
import javax.swing.table.TableColumn;

public class Result_prof extends JFrame implements ActionListener {
	Database connect = new Database();
	
	JPanel jpHead;
	JLabel jlHead;
	
	
	JPanel jpInput;
	JLabel jlCode, jlName, jlyear, jlGrade, jlTerm;
	JTextField jtCode, jtName;
	
	JComboBox jcYear, jcGrade, jcTerm;
	String subject;
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck;
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"학 번", "이 름", "출 석", "레포트", "중 간", "기 말", "가산점", "합 계", "학 점"};
	Object[][] rowData = {};
	String[] hakjum = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};
	JComboBox cb;
	PreparedStatement pstmt;
	ResultSet rs;
	String stdCode = null;
	String stdName = null;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;

	JPanel jpButton;
	JButton jbSave, jbExit;
	String pc;
	
	public Result_prof(String pc){
		this.pc = pc;
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
		
		jlCode = new JLabel("교수코드");
		jlCode.setBounds(20, 30, 70, 30);
		jlCode.setFont(fInput);
		
		jtCode = new JTextField();
		jtCode.setBounds(90, 32, 90, 25);
		jtCode.setText(pc);
		jtCode.setEnabled(false);
		
		jpInput.add(jlCode);
		jpInput.add(jtCode);
		
///////////////////////////////////////////////////////////////////////////
		
		jlName = new JLabel("교수이름");
		jlName.setBounds(200, 30, 70, 30);
		jlName.setFont(fInput);
		
		jtName = new JTextField();
		jtName.setBounds(270, 32, 90, 25);
		
		try {
			String sql = "SELECT * FROM professor where code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
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
		
		try {
			String sql = "SELECT * FROM subject where prof_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
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
		
		try {
			String sql = "SELECT * FROM subject where prof_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String grade = rs.getString("grade");
				jcGrade.addItem(grade + "학년");
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
		
		try {
			String sql = "SELECT * FROM subject where prof_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String term = rs.getString("term");
				jcTerm.addItem(term+"학기");
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
		
		jcInput = new JComboBox();
		jcInput.addItem("검색할 교과목 선택하세요");
		jcInput.setSelectedIndex(0);
		jcInput.addActionListener(this);
		
		try {
			String sql = "SELECT * FROM subject where prof_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String subjCode = rs.getString("code");
				
				String sql1 = "SELECT * FROM subject where code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, subjCode);
				ResultSet rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					subject = rs1.getString("subject");
				}
				
				jcInput.addItem(subject);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
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
		
		model = new DefaultTableModel(rowData, columnNames);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 270, 590, 270);		
		table.setRowHeight(20); // JTable 행 높이 조절
		
		cb = new JComboBox(hakjum);
		TableColumn hc = table.getColumnModel().getColumn(8);
		hc.setCellEditor(new DefaultCellEditor(cb));
	
		add(scroll);
		table.addMouseListener(new Adapter());
		
///////////////////////////////////////////////////////////////////////////	

		Font fButton = new Font("고딕", Font.BOLD, 20);
		
		jpButton = new JPanel();
		jpButton.setLayout(null);
		jpButton.setBounds(0, 540, 600, 100);
		add(jpButton);
		
		jbSave = new JButton("저장");
		jbSave.setBounds(380, 15, 100, 30);
		jbSave.setFont(fButton);
		jbSave.addActionListener(this);
		jpButton.add(jbSave);
		
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
			String sql = "SELECT * FROM result where prof_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pc);
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				String code = rs.getString("std_code");
				String attended = rs.getString("attended");
				String report = rs.getString("report");
				String middle = rs.getString("middle");
				String fina = rs.getString("final");
				String added = rs.getString("added");
				String sum = rs.getString("sum");
				
				int att = Integer.parseInt(attended);
				int rep = Integer.parseInt(report);
				int mid = Integer.parseInt(middle);
				int fi = Integer.parseInt(fina);
				int add = Integer.parseInt(added);
				
				
				String sql1 = "SELECT * FROM student where code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, code);
			
				ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						stdName = rs1.getString("name");
					}
					
				Object[] newdata = {code, stdName, attended, report, middle, fina, added, sum};
				model.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if (ob == jcInput) {
			try {
				String sql = "SELECT * FROM subject where subject = ?";
				pstmt = connect.conn.prepareStatement(sql);
				pstmt.setString(1, jcInput.getSelectedItem().toString());
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					String code = rs.getString("code");
					jtCheck.setText(code);
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
		}
		
		if (ob == jbCheck) {
			getListAll();
		}
		if (ob == jbExit) {
			dispose();
		}
		if (ob == jbSave) {
			int count = table.getRowCount();
			
			try {
				for (int i = 0; i<count; i++) {
					String sql = "UPDATE result SET attended = ?, report = ?, middle = ?, final = ?, added = ?, sum = ?, grade_value = ? where prof_code = ? and std_code = ?";
					pstmt = connect.conn.prepareStatement(sql);
					pstmt.setString(1, model.getValueAt(i, 2).toString());
					pstmt.setString(2, model.getValueAt(i, 3).toString());
					pstmt.setString(3, model.getValueAt(i, 4).toString());
					pstmt.setString(4, model.getValueAt(i, 5).toString());
					pstmt.setString(5, model.getValueAt(i, 6).toString());
					pstmt.setString(6, model.getValueAt(i, 7).toString());
					pstmt.setString(7, model.getValueAt(i, 8).toString());
					pstmt.setString(8, pc);
					pstmt.setString(9, model.getValueAt(i, 0).toString());
					pstmt.executeUpdate();
				}
										
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "등록되었습니다.");
			getListAll();
			
		}
		
	}
	
	class Adapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
				
			srow = table.getSelectedRow();
			
			String attend = model.getValueAt(srow, 2).toString();		
			String report = model.getValueAt(srow, 3).toString();			
			String middle = model.getValueAt(srow, 4).toString();
			String fina = model.getValueAt(srow, 5).toString();
			String added = model.getValueAt(srow, 6).toString();
			
			int sum = Integer.parseInt(attend) + Integer.parseInt(report) + Integer.parseInt(middle) + Integer.parseInt(fina) + Integer.parseInt(added);
			if (sum > 100) {
				JOptionPane.showMessageDialog(null, "합계점수가 100이 넘었습니다.");
				
			}
			
			table.setValueAt(sum, srow, 7);
			
		}	
	}

}
