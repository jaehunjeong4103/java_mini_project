package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Professor extends JFrame implements ActionListener, KeyListener{
	Database connect = new Database();
	
	JPanel jpHead;
	JLabel jlHead;
	
	JPanel jpInput;
	JLabel jlCode, jlName, jlJu1, jlJu2, jlAddr, jlmPhon1, jlmPhon2, jlmPhon3, jlPhon, jlentYear, jlDegree, jlDept, jlRoomno, jlGender;
	JTextField jtCode, jtName, jtJu1, jtJu2, jtAddr, jtmPhon1, jtmPhon2, jtmPhon3, jtPhon, jtentYear, jtDegree, jtRoomno;
	JComboBox jcdept;
	JRadioButton[] genders;
	ButtonGroup group;
	String gender = null;
	String deptCode;
	
	JPanel jpCheck;
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck, jbCheckAll;
	
	String[] fieldnames = {"code", "name", "addr", "gender"};
	String[] checkBox = {"교수코드", "이름", "주소", "성별"};
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"교수코드", "이름", "주소", "주민등록번호", "휴대폰", "전화번호", "임용년도", "학위", "학과", "연구실", "성별"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs;
	String deptName = null;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;
	
	JPanel jpButton;
	JButton jbInput, jbChange, jbDelete, jbExit;
	
	
	
	public Professor(){
		setTitle("교수관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 700);
		setVisible(true);
		
///////////////////////////////////////////////////////////////////////////		
		
		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("교 수 관 리");
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
		
		jlCode = new JLabel("교수코드");
		jlCode.setBounds(10, 20, 60, 30);
		jlCode.setFont(fInput);
		
		jtCode = new JTextField();
		jtCode.setBounds(70, 22, 80, 25);
		
		jpInput.add(jlCode);
		jpInput.add(jtCode);
		
///////////////////////////////////////////////////////////////////////////
		
		jlName = new JLabel("이 름");
		jlName.setBounds(165, 20, 40, 30);
		jlName.setFont(fInput);
		
		jtName = new JTextField();
		jtName.setBounds(205, 22, 80, 25);
		
		jpInput.add(jlName);
		jpInput.add(jtName);
		
///////////////////////////////////////////////////////////////////////////
		
		jlJu1 = new JLabel("주민번호");
		jlJu1.setBounds(300, 20, 60, 30);
		jlJu1.setFont(fInput);
		
		jtJu1 = new JTextField();
		jtJu1.setBounds(360, 22, 95, 25);
		jtJu1.addKeyListener(this);
		
		jpInput.add(jlJu1);
		jpInput.add(jtJu1);
		
///////////////////////////////////////////////////////////////////////////
		
		jlJu2 = new JLabel("-");
		jlJu2.setBounds(465, 20, 5, 30);
		jlJu2.setFont(fInput);
		
		jtJu2 = new JTextField();
		jtJu2.setBounds(480, 22, 95, 25);
		jtJu2.addKeyListener(this);
				
		jpInput.add(jlJu2);
		jpInput.add(jtJu2);
		
///////////////////////////////////////////////////////////////////////////
		
		jlAddr = new JLabel("주 소");
		jlAddr.setBounds(10, 55, 60, 30);
		jlAddr.setFont(fInput);
		
		jtAddr = new JTextField();
		jtAddr.setBounds(70, 57, 505, 25);
		
		jpInput.add(jlAddr);
		jpInput.add(jtAddr);
		
///////////////////////////////////////////////////////////////////////////
		
		jlmPhon1 = new JLabel("휴대폰");
		jlmPhon1.setBounds(10, 90, 60, 30);
		jlmPhon1.setFont(fInput);
		
		jtmPhon1 = new JTextField();
		jtmPhon1.setBounds(70, 92, 70, 25);
		jtmPhon1.addKeyListener(this);
		
		jpInput.add(jlmPhon1);
		jpInput.add(jtmPhon1);
		
///////////////////////////////////////////////////////////////////////////
		
		jlmPhon2 = new JLabel("-");
		jlmPhon2.setBounds(150, 90, 5, 30);
		jlmPhon2.setFont(fInput);
		
		jtmPhon2 = new JTextField();
		jtmPhon2.setBounds(165, 92, 70, 25);
		jtmPhon2.addKeyListener(this);
		
		jpInput.add(jlmPhon2);
		jpInput.add(jtmPhon2);
		
///////////////////////////////////////////////////////////////////////////
		
		jlmPhon3 = new JLabel("-");
		jlmPhon3.setBounds(245, 90, 5, 30);
		jlmPhon3.setFont(fInput);
		
		jtmPhon3 = new JTextField();
		jtmPhon3.setBounds(260, 92, 70, 25);
		jtmPhon3.addKeyListener(this);
		
		jpInput.add(jlmPhon3);
		jpInput.add(jtmPhon3);
		
///////////////////////////////////////////////////////////////////////////
		
		jlPhon = new JLabel("전화번호");
		jlPhon.setBounds(360, 90, 60, 30);
		jlPhon.setFont(fInput);
		
		jtPhon = new JTextField();
		jtPhon.setBounds(420, 92, 155, 25);
		jtPhon.addKeyListener(this);
		
		jpInput.add(jlPhon);
		jpInput.add(jtPhon);
		
///////////////////////////////////////////////////////////////////////////
		
		jlentYear = new JLabel("임용년도");
		jlentYear.setBounds(10, 125, 60, 30);
		jlentYear.setFont(fInput);
		
		jtentYear = new JTextField();
		jtentYear.setBounds(70, 127, 70, 25);
		
		jpInput.add(jlentYear);
		jpInput.add(jtentYear);
		
///////////////////////////////////////////////////////////////////////////
		
		jlDegree = new JLabel("학 위");
		jlDegree.setBounds(190, 125, 40, 30);
		jlDegree.setFont(fInput);
		
		jtDegree = new JTextField();
		jtDegree.setBounds(230, 127, 100, 25);
		
		jpInput.add(jlDegree);
		jpInput.add(jtDegree);
		
///////////////////////////////////////////////////////////////////////////
		
		jlDept = new JLabel("학 과");
		jlDept.setBounds(360, 125, 60, 30);
		jlDept.setFont(fInput);
		
		jcdept = new JComboBox();
		jcdept.setBounds(420, 127, 155, 25);
		jcdept.addItem("개설학과");
		
		try {
			String sql = "SELECT * FROM department";
			pstmt = connect.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("department");
				jcdept.addItem(name);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		jpInput.add(jlDept);
		jpInput.add(jcdept);
		
		
///////////////////////////////////////////////////////////////////////////
		
		jlRoomno = new JLabel("연구실");
		jlRoomno.setBounds(10, 160, 60, 30);
		jlRoomno.setFont(fInput);
		
		jtRoomno = new JTextField();
		jtRoomno.setBounds(70, 162, 70, 25);
		
		jpInput.add(jlRoomno);
		jpInput.add(jtRoomno);
		
///////////////////////////////////////////////////////////////////////////
		
		group = new ButtonGroup();
		
		jlGender = new JLabel("성  별");
		jlGender.setFont(fInput);
		jlGender.setBounds(190, 160, 40, 30);
		jpInput.add(jlGender);
		
		genders = new JRadioButton[2];
		String[] text = {"남자", "여자"};
		for (int i = 0; i<genders.length; i++) {
			genders[i] = new JRadioButton(text[i]);
			group.add(genders[i]);
			genders[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(genders[0].isSelected())
						gender = "남자";
					if(genders[1].isSelected())
						gender = "여자";
				}
			});
		}
		genders[0].setFont(fInput);
		genders[1].setFont(fInput);
		genders[0].setBounds(240, 162, 60, 25);
		genders[1].setBounds(300, 162, 60, 25);
		jpInput.add(genders[0]);
		jpInput.add(genders[1]);
		
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
		jtName.setText("");
		jtJu1.setText("");
		jtJu2.setText("");
		jtAddr.setText("");
		jtmPhon1.setText("");
		jtmPhon2.setText("");
		jtmPhon3.setText("");
		jtPhon.setText("");
		jtentYear.setText("");
		jtDegree.setText("");
		jtRoomno.setText("");		
		jtCheck.setText("");
		jcInput.setSelectedIndex(4);
		jcdept.setSelectedIndex(0);
		jbInput.setEnabled(true);
		jbChange.setEnabled(false);
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public int regist(String code, String name, String addr, String jumin, String mphon, String phon, String ent, String degree, String dept, String room, String gender) {
		int result = 0;
		String sql = "INSERT INTO professor(code, name, addr, juminno, mphone, phone, ent_year, degree, dept_code, roomno, gender) VALUE(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, code);
			pstmt.setString(2, name);
			pstmt.setString(3, addr);
			pstmt.setString(4, jumin);
			pstmt.setString(5, mphon);
			pstmt.setString(6, phon);
			pstmt.setString(7, ent);
			pstmt.setString(8, degree);
			pstmt.setString(9, dept);
			pstmt.setString(10, room);
			pstmt.setString(11, gender);
			
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public int edit(String code, String name, String addr, String jumin, String mphon, String phon, String ent, String degree, String dept, String room, String gender) {
		int result = 0;
		String sql = "UPDATE professor SET name = ?, addr = ?, juminno = ?, mphone = ?, phone = ?, ent_year = ?, degree = ?, dept_code = ?, roomno = ?, gender = ? WHERE code = ?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, addr);
			pstmt.setString(3, jumin);
			pstmt.setString(4, mphon);
			pstmt.setString(5, phon);
			pstmt.setString(6, ent);
			pstmt.setString(7, degree);
			pstmt.setString(8, dept);
			pstmt.setString(9, room);
			pstmt.setString(10, gender);
			pstmt.setString(11, code);
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
///////////////////////////////////////////////////////////////////////////	
	
	public int delete (String code) {
		int result = 0;
		String sql = "DELETE FROM professor WHERE code =?";
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
	
	public void getListAll() {
		model.setRowCount(0);

		String sql = "SELECT * FROM professor";
		
		try {
			pstmt = connect.conn.prepareStatement(sql);
		
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				String code = rs.getString(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);
				String jumin = rs.getString(4);
				String mphon = rs.getString(5);
				String phon = rs.getString(6);
				String ent = rs.getString(7);
				String degree = rs.getString(8);
				String dept = rs.getString(9);
				String room = rs.getString(10);
				String gender = rs.getString(11);
				
				String sql1 = "SELECT * FROM department where code = ?";
				pstmt = connect.conn.prepareStatement(sql1);
				pstmt.setString(1, dept);
				
				ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						deptName = rs1.getString("department");
					}
				 
				Object[] newdata = {code, name, addr, jumin, mphon, phon, ent, degree, deptName, room, gender};
				model.addRow(newdata);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		String empty = jtCheck.getText();
		String code = jtCode.getText();
		String name = jtName.getText();
		String jumin1 = jtJu1.getText();
		String jumin2 = jtJu2.getText(); 
		String jumin = (jumin1 + "-" + jumin2);
		String addr = jtAddr.getText();
		String mphon1 = jtmPhon1.getText();  
		String mphon2 = jtmPhon2.getText();  
		String mphon3 = jtmPhon3.getText();  
		String mphon = (mphon1 + "-" + mphon2 + "-" + mphon3);  
		String phon = jtPhon.getText();
		String ent = jtentYear.getText();
		String degree = jtDegree.getText();
		String dept = jcdept.getSelectedItem().toString();
		String room = jtRoomno.getText();
		
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
		
		if (ob == jbInput) {
			if (code.isEmpty()) {
				JOptionPane.showMessageDialog(this, "교수코드를 입력해주세요");
				jtCode.requestFocus();
				return;
			}
			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(this, "이름을 입력해주세요");
				jtName.requestFocus();
				return;
			}
			if (jumin1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "주민번호를 입력해주세요");
				jtJu1.requestFocus();
				return;
			}
			if (jumin2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "주민번호를 입력해주세요");
				jtJu2.requestFocus();
				return;
			}
			if (addr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "주소를 입력해주세요");
				jtAddr.requestFocus();
				return;
			}
			if (mphon1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "휴대폰번호를 입력해주세요");
				jtmPhon1.requestFocus();
				return;
			}
			if (mphon2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "휴대폰번호를 입력해주세요");
				jtmPhon1.requestFocus();
				return;
			}
			if (mphon2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "휴대폰번호를 입력해주세요");
				jtmPhon1.requestFocus();
				return;
			}
			if (phon.isEmpty()) {
				JOptionPane.showMessageDialog(this, "전화번호를 입력해주세요");
				jtPhon.requestFocus();
				return;
			}
			if (ent.isEmpty()) {
				JOptionPane.showMessageDialog(this, "임용년도를 입력해주세요");
				jtentYear.requestFocus();
				return;
			}
			if (degree.isEmpty()) {
				JOptionPane.showMessageDialog(this, "학위를 입력해주세요");
				jtDegree.requestFocus();
				return;
			}
			if (dept.isEmpty()) {
				JOptionPane.showMessageDialog(this, "학과를 입력해주세요");
				jcdept.requestFocus();
				return;
			}
			if (room.isEmpty()) {
				JOptionPane.showMessageDialog(this, "연구실을 입력해주세요");
				jtRoomno.requestFocus();
				return;
			}
			
			Object rowData[] = {code, name, addr, jumin, mphon, phon, ent, degree, deptName, room, gender}; 
			model.addRow(rowData);
			
			int result = regist(code, name, addr, jumin, mphon, phon, ent, degree, deptCode, room, gender);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "등록되었습니다.");
				getListAll();
				clear();
			}else
				clear();
			}
		
		if (ob == jbChange) {
			int result = edit(code, name, addr, jumin, mphon, phon, ent, degree, deptCode, room, gender);
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
					String sql = "SELECT * FROM professor WHERE " + field + " = ?";
					pstmt = connect.conn.prepareStatement(sql);
					pstmt.setString(1, value);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						code = rs.getString(1);
						name = rs.getString(2);
						addr = rs.getString(3);
						jumin = rs.getString(4);
						mphon = rs.getString(5);
						phon = rs.getString(6);
						ent = rs.getString(7);
						degree = rs.getString(8);
						dept = rs.getString(9);
						room = rs.getString(10);
						gender = rs.getString(11);	
						
						String sql1 = "SELECT * FROM department where code = ?";
						pstmt = connect.conn.prepareStatement(sql1);
						pstmt.setString(1, dept);
						
						ResultSet rs1 = pstmt.executeQuery();
							while (rs1.next()) {
								deptName = rs1.getString("department");
							}
						
						Object[] newdata = {code, name, addr, jumin, mphon, phon, ent, degree, deptName, room, gender};
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
			String name = model.getValueAt(srow, 1).toString();
			String addr = model.getValueAt(srow, 2).toString();
			String jumin = model.getValueAt(srow, 3).toString();		
			String mphon = model.getValueAt(srow, 4).toString();			
			String phon = model.getValueAt(srow, 5).toString();
			String ent = model.getValueAt(srow, 6).toString();
			String degree = model.getValueAt(srow, 7).toString();
			String dept = model.getValueAt(srow, 8).toString();
			String room = model.getValueAt(srow, 9).toString();
			String gender = model.getValueAt(srow, 10).toString();
			
			String tempjumin[] = jumin.split("-");
			String jumin1 = null;
			String jumin2 = null;
			if (tempjumin != null && tempjumin.length >= 2) {
				jumin1 = tempjumin[0];
				jumin2 = tempjumin[1];
			}
			
			String tempmPhone[] = mphon.split("-");
			String mphone1 = null;
			String mphone2 = null;
			String mphone3 = null;
			if (tempmPhone != null && tempmPhone.length >= 2) {
				mphone1 = tempmPhone[0];
				mphone2 = tempmPhone[1];
				mphone3 = tempmPhone[2];
			}
			
			jtCode.setText(code);
			jtName.setText(name);
			jtAddr.setText(addr);
			jtJu1.setText(jumin1);
			jtJu2.setText(jumin2);
			jtmPhon1.setText(mphone1);
			jtmPhon2.setText(mphone2);
			jtmPhon3.setText(mphone3);
			jtPhon.setText(phon);
			jtentYear.setText(ent);
			jtDegree.setText(degree);
			jtRoomno.setText(room);
			
			if (gender.equals("남자")) {
				genders[0].setSelected(true);
			}
			
			if (gender.equals("여자")) {
				genders[1].setSelected(true);
			}
			jtCode.setEnabled(false);
			jcdept.setSelectedItem(dept);
		}	
	}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	Object k = e.getSource();
	
	if (k == jtJu1) {
		if (5 <= jtJu1.getText().length()) {
			jtJu2.requestFocus();
		}
	}
	if (k == jtJu2) {
		if (6 <= jtJu2.getText().length()) {
			jtAddr.requestFocus();
		}
	}
	if (k == jtmPhon1) {
		if (2 <= jtmPhon1.getText().length()) {
			jtmPhon2.requestFocus();
		}
	}
	if (k == jtmPhon2) {
		if (3 <= jtmPhon2.getText().length()) {
			jtmPhon3.requestFocus();
		}
	}
	if (k == jtmPhon3) {
		if (3 <= jtmPhon3.getText().length()) {
			jtPhon.requestFocus();
		}
	}
	if (k == jtPhon) {
		if (9 <= jtPhon.getText().length()) {
			jtentYear.requestFocus();
		}
	}
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
	
///////////////////////////////////////////////////////////////////////////	

}
