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

import univ.Student.Adapter;

public class Attended extends JFrame implements ActionListener {
	Database connect = new Database();
	String sc;
	
	JPanel jpHead;
	JLabel jlHead;
	
	
	JPanel jpInput;
	JLabel jlCode, jlName, jlDept;
	JTextField jtCode, jtName, jtDept;
	String deptCode;
	String profCode;
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck, jbCheckAll;
	
	JPanel jpTable1, jpTable2;
	JTable table, table1;
	DefaultTableModel model, model1;
	JScrollPane scroll, scroll1;
	String[] columnNames = {"교과목코드", "교과목명", "개설학과", "수업시수", "담당교수", "학점"};
	Object[][] rowData = {};
	String[] columnNames1 = {"교과목코드", "교과목명", "개설학과", "수업시수", "담당교수", "학점"};
	Object[][] rowData1 = {};
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	String deptName = null;
	String profName = null;
	
	int srow = -1;
	int srow1 = -1;
	
	DefaultTableCellRenderer center;
	
	
	JPanel jpButton;
	JButton jbDelete, jbExit;
	
	public Attended(String sc){
		this.sc = sc;
		
		setTitle("수강신청");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 700);
		setVisible(true);
		
/////////////////////////////////////////////////////////////////////////

		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("수 강 신 청");
		jlHead.setFont(new Font("고딕", Font.BOLD, 25));
		jlHead.setForeground(Color.white);
		jpHead.add(jlHead);
		
/////////////////////////////////////////////////////////////////////////////
		
		jpInput = new JPanel();
		jpInput.setLayout(null);
		jpInput.setBounds(5, 50, 590, 100);
		TitledBorder t = new TitledBorder("입력");
		t.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpInput.setBorder(t);
		add(jpInput);
		
/////////////////////////////////////////////////////////////////////////////
		
		Font fInput = new Font("고딕", Font.PLAIN, 13);
		
		jlCode = new JLabel("학생코드");
		jlCode.setBounds(20, 30, 60, 30);
		jlCode.setFont(fInput);
		jtCode = new JTextField();
		jtCode.setEnabled(false);
		jtCode.setText(sc);
		jtCode.setBounds(80, 32, 100, 25);
		
		
		jlName = new JLabel("이 름");
		jlName.setBounds(220, 30, 60, 30);
		jlName.setFont(fInput);
		jtName = new JTextField();
		jtName.setEnabled(false);
		jtName.setBounds(260, 32, 110, 25);
		
		jlDept = new JLabel("학 과");
		jlDept.setBounds(410, 30, 60, 30);
		jlDept.setFont(fInput);
		jtDept = new JTextField();
		jtDept.setEnabled(false);
		jtDept.setBounds(450, 32, 110, 25);
		
		jpInput.add(jlCode);
		jpInput.add(jtCode);
		jpInput.add(jlName);
		jpInput.add(jtName);
		jpInput.add(jlDept);
		jpInput.add(jtDept);
		
/////////////////////////////////////////////////////////////////////////////
		
		jpCheck = new JPanel();
		jpCheck.setLayout(null);
		jpCheck.setBounds(0, 155, 600, 65);
		jpCheck.setBackground(Color.gray);
		add(jpCheck);
		
		jcInput = new JComboBox();
		jcInput.addItem("개설학과 선택하세요");
		jcInput.setSelectedIndex(0);
		jcInput.addActionListener(this);
		
		try {
			String sql = "SELECT * FROM subject";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("subject");
				jcInput.addItem(name);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
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
		
		jpTable1 = new JPanel();
		jpTable1.setLayout(null);
		jpTable1.setBounds(5, 230, 590, 180);
		
		TitledBorder table3 = new TitledBorder("교과목 테이블");
		table3.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpTable1.setBorder(table3);
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		
		scroll.setBounds(5 , 20, 580, 140);		
		table.setRowHeight(20); // JTable 행 높이 조절
		
		add(jpTable1);
		jpTable1.add(scroll);
		
		table.addMouseListener(new Adapter());
		
///////////////////////////////////////////////////////////////////////////	
		
		jpTable2 = new JPanel();
		jpTable2.setLayout(null);
		jpTable2.setBounds(5, 420, 590, 180);
		
		TitledBorder table2 = new TitledBorder("수강신청 테이블");
		table2.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpTable2.setBorder(table2);
		
		model1 = new DefaultTableModel(rowData1, columnNames1) {
			// DefaultTableModel 셀 편집 불가능
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; 
			}
		};
		table1 = new JTable(model1);
		scroll1 = new JScrollPane(table1);
		
		getSelectListAll();
		getNotSelectListAll();
		
		
		scroll1.setBounds(5 , 20, 580, 140);		
		table1.setRowHeight(20); // JTable 행 높이 조절
		
		add(jpTable2);
		jpTable2.add(scroll1);
		
		table1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				srow1 = table1.getSelectedRow();
				String code = model1.getValueAt(srow1, 0).toString();
				String subject = model1.getValueAt(srow1, 1).toString();
				String dept = model1.getValueAt(srow1, 2).toString();		
				String time = model1.getValueAt(srow1, 3).toString();
				String prof = model1.getValueAt(srow1, 4).toString();
				String credit = model1.getValueAt(srow1, 5).toString();
				
				Object[] newdata = {code, subject, dept, time, prof, credit};		
			}
		});
		
///////////////////////////////////////////////////////////////////////////	

		Font fButton = new Font("고딕", Font.BOLD, 20);
		
		jpButton = new JPanel();
		jpButton.setLayout(null);
		jpButton.setBounds(0, 600, 600, 100);
		add(jpButton);
		
		jbDelete = new JButton("삭제");
		jbDelete.setBounds(380, 15, 100, 30);
		jbDelete.setFont(fButton);
		jbDelete.addActionListener(this);
		jpButton.add(jbDelete);
		
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
			String sql = "SELECT * FROM subject";
			pstmt = connect.conn.prepareStatement(sql);
		
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				String code = rs.getString("code");
				String subject = rs.getString("subject");
				String dept = rs.getString("dept_code");
				String time = rs.getString("time");
				String prof = rs.getString("prof_code");
				String credit = rs.getString("credit");	
				
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
				Object[] newdata = {code, subject, deptName, time, profName, credit};
				model.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public void getNotSelectListAll() {
		model.setRowCount(0);
		
		try {
			String sql = "select * from subject left outer join (select * from attended where std_code = ?) as att on code = att.subj_code;";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, sc);	
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String subj_code = rs.getString(13);
				
				if (subj_code != null) {
					continue;
				}
				
				if (subj_code == null) {
					String code = rs.getString(1);
					String subject = rs.getString(2);
					String dept = rs.getString(3);
					String time = rs.getString(7);
					String prof = rs.getString(8);
					String credit = rs.getString(9);
					
					String sql1 = "SELECT * FROM department where code = ?";
					pstmt = connect.conn.prepareStatement(sql1);
					pstmt.setString(1, dept);
					
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						deptName = rs.getString("department");
					}
					
					String sql2 = "SELECT * FROM professor where code = ?";
					pstmt = connect.conn.prepareStatement(sql2);
					pstmt.setString(1, prof);
					
					ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						profName = rs1.getString("name");
					} 
					
					Object[] newdata = {code, subject, deptName, time, profName, credit};
					model.addRow(newdata);
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////	

	public void getSelectListAll() {
		model1.setRowCount(0);
				
		try {
			String sql = "SELECT * FROM subject JOIN attended ON code = subj_code where attended.std_code = ?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, sc);	
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				String code = rs.getString("code");
				String subject = rs.getString("subject");
				String dept = rs.getString("dept_code");
				String time = rs.getString("time");
				String prof = rs.getString("prof_code");
				String credit = rs.getString("credit");	
				
				String sql1 = "SELECT * FROM department where code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, dept);
				
				ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						deptName = rs.getString("department");
					}
						
				String sql2 = "SELECT * FROM professor where code = ?";
				pstmt = connect.conn.prepareStatement(sql2);
				pstmt.setString(1, prof);
				
				ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						profName = rs1.getString("name");
					} 
						
				Object[] newdata = {code, subject, deptName, time, profName, credit};
				model1.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////	

	public int delete (String code) {
		int result = 0;
		
		try {
			String sql = "DELETE FROM attended WHERE std_code = ? and subj_code =?";
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, jtCode.getText());	
			pstmt.setString(2, code);	
			result = pstmt.executeUpdate(); 
			String sql1 = "DELETE FROM result WHERE std_code = ? and subj_code =?";
			pstmt = connect.conn.prepareStatement(sql1);
			pstmt.setString(1, jtCode.getText());	
			pstmt.setString(2, code);	
			pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
///////////////////////////////////////////////////////////////////////////

	void clear() {
	jcInput.setSelectedIndex(0);
	}

///////////////////////////////////////////////////////////////////////////	

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
		if (ob == jbDelete) {
			if (srow1 < 0) { // JTable에서 선택된 행이 없다면 -1 리턴
				JOptionPane.showMessageDialog(null, "삭제할 정보를 선택하세요");
				return;
			}
			String code = (String) model1.getValueAt(srow1, 0).toString();
						
			int yn = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까", "INDEX", JOptionPane.YES_NO_OPTION);
			
			if (yn == JOptionPane.YES_OPTION) {	
				int result = delete(code);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				getSelectListAll();
				getNotSelectListAll();
				clear();
			}else
				clear();
		}
		
		if (ob == jbExit) {
			dispose();
		}
		if (ob == jbCheck) {
			model.setRowCount(0);
			String field = jtCheck.getText();
			try {		
				String sql = "SELECT * FROM subject WHERE code = ?";
				pstmt = connect.conn.prepareStatement(sql);
				pstmt.setString(1, jtCheck.getText());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String code = rs.getString("code");
					String subject = rs.getString("subject");
					String dept = rs.getString("dept_code");
					String time = rs.getString("time");
					String prof = rs.getString("prof_code");
					String credit = rs.getString("credit");	
					
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
					Object[] newdata = {code, subject, deptName, time, profName, credit};
					model.addRow(newdata);
				} 
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (ob == jbCheckAll) {	
			getNotSelectListAll();
			getSelectListAll();
			clear();
		}
		
	}
	
///////////////////////////////////////////////////////////////////////////
	
	class Adapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			
			srow = table.getSelectedRow();
			String code = model.getValueAt(srow, 0).toString();
			String subject = model.getValueAt(srow, 1).toString();
			String dept = model.getValueAt(srow, 2).toString();		
			String time = model.getValueAt(srow, 3).toString();
			String prof = model.getValueAt(srow, 4).toString();
			String credit = model.getValueAt(srow, 5).toString();
						
			try {
				String sql = "select * From subject where code = " + code;
				pstmt = connect.conn.prepareStatement(sql);	
				rs = pstmt.executeQuery();
			
				while (rs.next()) {
					String stdCode = jtCode.getText();				
					String profCode = rs.getString("prof_code");				
					String subjCode = rs.getString("code");				
					String year = rs.getString("year");		
					String grade = rs.getString("grade");
					String term = rs.getString("term");
					
					String sql1 = "INSERT INTO attended(std_code, prof_code, subj_code, year) VALUE(?,?,?,?)";
					pstmt = connect.conn.prepareStatement(sql1);	
					
					pstmt.setString(1, stdCode);
					pstmt.setString(2, profCode);
					pstmt.setString(3, subjCode);
					pstmt.setString(4, year);
					pstmt.executeUpdate();
					
					String sql2 = "INSERT INTO result(indexno, prof_code, std_code, subj_code, year, grade, term, attended, report, middle, final, added, sum, grade_value) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					pstmt = connect.conn.prepareStatement(sql2);					
					pstmt.setString(1, "0");
					pstmt.setString(2, profCode);
					pstmt.setString(3, stdCode);
					pstmt.setString(4, subjCode);
					pstmt.setString(5, year);
					pstmt.setString(6, grade);
					pstmt.setString(7, term);
					pstmt.setString(8, "0");
					pstmt.setString(9, "0");
					pstmt.setString(10, "0");
					pstmt.setString(11, "0");
					pstmt.setString(12, "0");
					pstmt.setString(13, "0");
					pstmt.setString(14, "0");
					pstmt.executeUpdate();
				}
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
	
			model.removeRow(srow);
			getSelectListAll();

		}	
	}
}
