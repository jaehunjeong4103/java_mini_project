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

public class Admin extends JFrame implements ActionListener{
	Database connect = new Database();
	Menu m;
	JPanel jpHead;
	JLabel jlHead;
	
	JPanel jpInput;
	JLabel jlId, jlPass1, jlPass2, jlUser, jlAuth, jlDate;
	JTextField jtId, jtPass1, jtPass2, jtUser, jtAuth, jtDate;
	JButton jbId, jbPassword;
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"아이디", "패스워드", "사용자", "사용권한", "등록날짜"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs;
	
	DefaultTableCellRenderer center;
	
	int srow = -1;

	JPanel jpButton;
	JButton jbInput, jbChange, jbDelete, jbExit;
	
	Boolean idchk = false;
	Boolean pwchk = false;
	
	
	Admin(){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setBounds(0, 0, 615, 700);

/////////////////////////////////////////////////////////////////////////////		
		
		jpHead = new JPanel();
		jpHead.setBounds(0, 0, 600, 40);
		jpHead.setBackground(Color.BLACK);
		add(jpHead);
		
		jlHead = new JLabel("사 용 자 관 리");
		jlHead.setFont(new Font("고딕", Font.BOLD, 25));
		jlHead.setForeground(Color.white);
		jpHead.add(jlHead);
		
/////////////////////////////////////////////////////////////////////////////
		
		jpInput = new JPanel();
		jpInput.setLayout(null);
		jpInput.setBounds(5, 50, 590, 185);
		TitledBorder t = new TitledBorder("입력");
		t.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		jpInput.setBorder(t);
		add(jpInput);
		
/////////////////////////////////////////////////////////////////////////////
		Font fInput = new Font("고딕", Font.PLAIN, 13);
		
		jlId = new JLabel("아 이 디");
		jlId.setBounds(20, 20, 60, 30);
		jlId.setFont(fInput);
		
		jtId = new JTextField();
		jtId.setBounds(80, 22, 100, 25);
		
		jpInput.add(jlId);
		jpInput.add(jtId);
		
/////////////////////////////////////////////////////////////////////////////
		
		jlPass1 = new JLabel("패스워드");
		jlPass1.setBounds(20, 60, 60, 30);
		jlPass1.setFont(fInput);
		
		jtPass1 = new JTextField();
		jtPass1.setBounds(80, 62, 100, 25);
		
		jpInput.add(jlPass1);
		jpInput.add(jtPass1);
		
/////////////////////////////////////////////////////////////////////////////		
		
		
		jlPass2 = new JLabel("패스워드확인");
		jlPass2.setBounds(220, 60, 80, 30);
		jlPass2.setFont(fInput);
		
		jtPass2 = new JTextField();
		jtPass2.setBounds(310, 62, 100, 25);
		
		jpInput.add(jlPass2);
		jpInput.add(jtPass2);
		
/////////////////////////////////////////////////////////////////////////////
		
		jlUser = new JLabel("사 용 자");
		jlUser.setBounds(20, 100, 60, 30);
		jlUser.setFont(fInput);
		
		jtUser = new JTextField();
		jtUser.setBounds(80, 102, 100, 25);
		
		jpInput.add(jlUser);
		jpInput.add(jtUser);
		
/////////////////////////////////////////////////////////////////////////////		
		
		jlAuth = new JLabel("사용권한");
		jlAuth.setBounds(220, 100, 80, 30);
		jlAuth.setFont(fInput);
		
		jtAuth = new JTextField();
		jtAuth.setBounds(310, 102, 100, 25);
		
		jpInput.add(jlAuth);
		jpInput.add(jtAuth);
		
/////////////////////////////////////////////////////////////////////////////
		
		jlDate = new JLabel("등록날짜");
		jlDate.setBounds(20, 140, 60, 30);
		jlDate.setFont(fInput);
		
		jtDate = new JTextField();
		jtDate.setBounds(80, 142, 100, 25);
		
		jpInput.add(jlDate);
		jpInput.add(jtDate);
		
/////////////////////////////////////////////////////////////////////////////
		
		jbId = new JButton("ID 중복확인");
		jbId.setBounds(220 , 22, 100, 25);
		jbId.addActionListener(this);
		
		jbPassword = new JButton("PW 중복확인");
		jbPassword.setBounds(440, 62, 120, 25);
		jbPassword.addActionListener(this);
		
		jpInput.add(jbId);
		jpInput.add(jbPassword);
		
/////////////////////////////////////////////////////////////////////////////
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 250, 590, 350);
		
		table.setRowHeight(20); // JTable 행 높이 조절
		center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		
		table.getColumn("아이디").setPreferredWidth(25);
		table.getColumn("아이디").setCellRenderer(center);
		table.getColumn("패스워드").setPreferredWidth(25);
		table.getColumn("패스워드").setCellRenderer(center);
		table.getColumn("사용자").setPreferredWidth(25);
		table.getColumn("사용권한").setPreferredWidth(25);
		table.getColumn("사용권한").setCellRenderer(center);
		table.getColumn("등록날짜").setPreferredWidth(25);
		
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
		
		String sql = "SELECT * FROM admin";
		
		try {
			pstmt = connect.conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String id = rs.getString("id");
			String password = rs.getString("password");
			String owner = rs.getString("owner");
			String auth = rs.getString("auth");
			String date = rs.getString("date");
			
			Object[] newdata = {id, password, owner, auth, date};
			model.addRow(newdata);
		} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

/////////////////////////////////////////////////////////////////////////////
	
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		String id = jtId.getText();
		String pass1 = jtPass1.getText();
		String pass2 = jtPass2.getText();
		String owner = jtUser.getText();
		String auth = jtAuth.getText();
		String date = jtDate.getText();
//		jbPassword
		
		if (ob == jbId) {
			String sql = "SELECT * FROM admin where id = " + id + "";
			
			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
				jtId.requestFocus();
				return;
			}
			
			try {
				pstmt = connect.conn.prepareStatement(sql);
			
				rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JOptionPane.showMessageDialog(this, "해당 ID는 이미 사용중입니다.");
				jtId.requestFocus();
				jtId.selectAll();
				return;
			} 
			idchk = true;
			JOptionPane.showMessageDialog(this, "해당 ID는 사용가능합니다.");
			
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if (ob == jbPassword) {
			
			if (pass1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				jtPass1.requestFocus();
				return;
			}
			if (pass2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				jtPass2.requestFocus();
				return;
			}
			
			if (!(pass1.equals(pass2))) {
				JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요.");
				jtPass1.requestFocus();
				jtPass1.selectAll();
			}else
				pwchk = true;
				JOptionPane.showMessageDialog(this, "비밀번호 사용가능합니다.");
		}
		
		if (ob == jbInput) {
			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
				jtId.requestFocus();
				return;
			}
			if (pass1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				jtPass1.requestFocus();
				return;
			}
			if (pass2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				jtPass2.requestFocus();
				return;
			}
			if (owner.isEmpty()) {
				JOptionPane.showMessageDialog(this, "사용자를 입력해주세요");
				jtUser.requestFocus();
				return;
			}
			if (auth.isEmpty()) {
				JOptionPane.showMessageDialog(this, "사용자권한를 입력해주세요");
				jtAuth.requestFocus();
				return;
			}
			if (date.isEmpty()) {
				JOptionPane.showMessageDialog(this, "등록날짜를 입력해주세요");
				jtDate.requestFocus();
				return;
			}
			
			if (idchk == false || pwchk == false) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 확인해주세요");
			}else {
				Object rowData[] = {id, pass1, owner, auth, date}; 
				model.addRow(rowData);
				int result = regist(id, pass1, owner, auth, date);
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "등록되었습니다");
					getListAll();
					clear();
				}else
					clear();
			}
		}
		
		if (ob == jbChange) {
			int result = edit(id, pass1, owner, auth, date);
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
			id = (String) model.getValueAt(srow, 0);
			
			int yn = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까", "INDEX", JOptionPane.YES_NO_OPTION);
			
			if (yn == JOptionPane.YES_OPTION) {
				int result = delete(id);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				getListAll();
				clear();
			}else
				clear();
		}
		
		if (ob == jbExit) {
			dispose();
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////
	
	void clear() {
		jtId.setText("");			
		jtPass1.setText("");
		jtPass2.setText("");
		jtUser.setText("");
		jtAuth.setText("");
		jtDate.setText("");
		jbInput.setEnabled(true);
		jbChange.setEnabled(false);
	}
	
/////////////////////////////////////////////////////////////////////////////
	
	public int regist(String id, String pass1, String owner, String auth, String date) {
		int result = 0;
		String sql = "INSERT INTO admin(id, password, owner, auth, date) VALUE(?,?,?,?,?)";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass1);
			pstmt.setString(3, owner);
			pstmt.setString(4, auth);
			pstmt.setString(5, date);
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
/////////////////////////////////////////////////////////////////////////////
	
	public int edit(String id, String pass1, String owner, String auth, String date) {
		int result = 0;
		String sql = "UPDATE admin SET password = ?, owner = ?, auth = ?, date = ? WHERE id =?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, pass1);
			pstmt.setString(2, owner);
			pstmt.setString(3, auth);
			pstmt.setString(4, date);
			pstmt.setString(5, id);
			
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
/////////////////////////////////////////////////////////////////////////////
	
	public int delete (String id) {
		int result = 0;
		String sql = "DELETE FROM admin WHERE id =?";
		try {
			pstmt = connect.conn.prepareStatement(sql);
			pstmt.setString(1, id);	
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			
			String id = model.getValueAt(srow, 0).toString();
			String password = model.getValueAt(srow, 1).toString();
			String owner = model.getValueAt(srow, 2).toString();
			String auth = model.getValueAt(srow, 3).toString();
			String date = model.getValueAt(srow, 4).toString();
			
			jtId.setText(id);			
			jtPass1.setText(password);
			jtPass2.setText(password);
			jtUser.setText(owner);
			jtAuth.setText(auth);
			jtDate.setText(date);
			
		}	
	}
}
