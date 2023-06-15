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

import univ.Admin.Adapter;

public class Department extends JFrame implements ActionListener{
	Database connect = new Database();
	
	JPanel jpHead;
	JLabel jlHead;
	
	
	JPanel jpInput;
	JLabel deptCode, deptName, majorName;
	JTextField deptCodeT, deptNameT, majorNameT;
	
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck, jbCheckAll;
	
	String[] fieldnames = {"code", "department", "major"};
	String[] checkBox = {"학과코드", "학과명", "전공명"};
	
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"학과코드", "학과명", "전공명"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;
	
	
	JPanel jpButton;
	JButton jbInput, jbChange, jbDelete, jbExit;
	
	
	public	Department(){
		setTitle("학과관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 700);

		
///////////////////////////////////////////////////////////////////////////		
		
		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("학 과 관 리");
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
		
		deptCode = new JLabel("학과코드");
		deptCode.setBounds(20, 20, 60, 30);
		deptCode.setFont(fInput);
		deptCodeT = new JTextField();
		deptCodeT.setBounds(80, 22, 100, 25);
		
		deptName = new JLabel("학 과 명");
		deptName.setBounds(200, 20, 60, 30);
		deptName.setFont(fInput);
		deptNameT = new JTextField();
		deptNameT.setBounds(260, 22, 110, 25);
		
		majorName = new JLabel("전 공 명");
		majorName.setBounds(390, 20, 60, 30);
		majorName.setFont(fInput);
		majorNameT = new JTextField();
		majorNameT.setBounds(450, 22, 110, 25);
		
		jpInput.add(deptCode);
		jpInput.add(deptCodeT);
		jpInput.add(deptName);
		jpInput.add(deptNameT);
		jpInput.add(majorName);
		jpInput.add(majorNameT);
		
/////////////////////////////////////////////////////////////////////////////
		
		Font f = new Font("돋움", Font.BOLD, 13);
		
		jpCheck = new JPanel();
		jpCheck.setLayout(null);
		jpCheck.setBounds(0, 185, 600, 70);
		jpCheck.setBackground(Color.gray);
		add(jpCheck);
		
		jcInput = new JComboBox(checkBox);
		jcInput.addItem("카테고리 선택");
		jcInput.setSelectedIndex(3);
		jcInput.setFont(f);
		jcInput.setBounds(20, 22, 150, 30);
		jpCheck.add(jcInput);
		
		jtCheck = new JTextField();
		jtCheck.setBounds(190, 22, 190, 30);
		jpCheck.add(jtCheck);
		
		jbCheck = new JButton("조회");
		jbCheck.setFont(f);
		jbCheck.setBackground(Color.green);
		jbCheck.setForeground(Color.red);
		jbCheck.setBounds(410, 22, 70, 30);
		jbCheck.addActionListener(this);
		jpCheck.add(jbCheck);
		
		jbCheckAll = new JButton("전체조회");
		jbCheckAll.setFont(f);
		jbCheckAll.setBackground(Color.green);
		jbCheckAll.setForeground(Color.red);
		jbCheckAll.setBounds(490, 22, 90, 30);
		jbCheckAll.addActionListener(this);
		jpCheck.add(jbCheckAll);
		
/////////////////////////////////////////////////////////////////////////////
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 260, 590, 340);
		table.setRowHeight(20); // JTable 행 높이 조절
		
		getListAll();
		add(scroll);
		table.addMouseListener(new Adapter());
		
/////////////////////////////////////////////////////////////////////////////
		
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
		
/////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}
/////////////////////////////////////////////////////////////////////////////
	
	public void getListAll() {
		model.setRowCount(0);
		
		String sql = "SELECT * FROM department";
		
		try {
			pstmt = connect.conn.prepareStatement(sql);
		
			rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String code = rs.getString("code");
			String department = rs.getString("department");
			String major = rs.getString("major");
			
			
			Object[] newdata = {code, department, major};
			model.addRow(newdata);
		} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
/////////////////////////////////////////////////////////////////////////////	
	
	void clear() {
		deptCodeT.setText("");			
		deptNameT.setText("");
		majorNameT.setText("");
		jtCheck.setText("");
		jcInput.setSelectedIndex(3);
		jbInput.setEnabled(true);
		jbChange.setEnabled(false);
	}
/////////////////////////////////////////////////////////////////////////////
	
	public int regist(String code, String department, String major) {
		int result = 0;
		String sql = "INSERT INTO department(code, department, major) VALUE(?,?,?)";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, code);
			pstmt.setString(2, department);
			pstmt.setString(3, major);
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
/////////////////////////////////////////////////////////////////////////////
	
	public int edit(String code, String department, String major) {
		int result = 0;
		String sql = "UPDATE department SET department = ?, major = ? WHERE code = ?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, department);
			pstmt.setString(2, major);
			pstmt.setString(3, code);
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
/////////////////////////////////////////////////////////////////////////////	
	
	public int delete (String code) {
		int result = 0;
		String sql = "DELETE FROM department WHERE code =?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, code);	
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
/////////////////////////////////////////////////////////////////////////////	
	
	class Adapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			jbChange.setEnabled(true);
			jbInput.setEnabled(false);
			
			srow = table.getSelectedRow();
			
			String code = model.getValueAt(srow, 0).toString();
			String department = model.getValueAt(srow, 1).toString();
			String major = model.getValueAt(srow, 2).toString();
			
			deptCodeT.setText(code);			
			deptNameT.setText(department);
			majorNameT.setText(major);
			
		
		}	
	}

/////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		String code = deptCodeT.getText();
		String department = deptNameT.getText();
		String major = majorNameT.getText();
		
		String empty = jtCheck.getText();
		
		if (ob == jbInput) {
			if (code.isEmpty()) {
				JOptionPane.showMessageDialog(this, "학과코드를 입력해주세요");
				deptCodeT.requestFocus();
				return;
			}
			if (department.isEmpty()) {
				JOptionPane.showMessageDialog(this, "학과명을 입력해주세요");
				deptNameT.requestFocus();
				return;
			}
			if (major.isEmpty()) {
				JOptionPane.showMessageDialog(this, "전공명을 입력해주세요");
				majorNameT.requestFocus();
				return;
			}
			
			Object rowData[] = {code, department, major}; 
			model.addRow(rowData);
			
			int result = regist(code, department, major);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "등록되었습니다.");
				getListAll();
				clear();
			}else
				clear();
			}
		
		if (ob == jbChange) {
			int result = edit(code, department, major);
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
		}	
		
		else if (ob == jbCheck) {	
			if (empty.isEmpty()) {
				JOptionPane.showMessageDialog(this, "조회할 내용을 선택해주세요");			
			}
			else {
				String field = fieldnames[jcInput.getSelectedIndex()];
				String value = jtCheck.getText();
				model.setRowCount(0);
				try {		
					String sql = "SELECT * FROM department WHERE " + field + " = ?";
					pstmt = connect.conn.prepareStatement(sql);
					pstmt.setString(1, value);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						String code1 = rs.getString("code");
						String department1 = rs.getString("department");
						String major1 = rs.getString("major");
						
						Object[] newdata = {code1, department1, major1};
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

}
