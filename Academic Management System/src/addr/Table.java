package addr;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class Table extends JPanel{
	JP_addr addrT;
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String[] columnNames = {"이름", "나이", "성별", "주소", "연락처", "이메일", "INDEX"};
	Object[][] rowData = {};
	PreparedStatement pstmt;
	ResultSet rs;
	
	int srow = -1;
	
	DefaultTableCellRenderer center;
	
	Table(JP_addr addr){
		this.addrT = addr;
		System.out.println("테이블 연결 완료");
		
		setLayout(null);
		setBounds(0, 0, 710, 800);		
		
		model = new DefaultTableModel(rowData, columnNames) {
			   // DefaultTableModel 셀 편집 불가능
			   public boolean isCellEditable(int rowIndex, int colIndex) {
				   return false; 
			   }
		};
		table = new JTable(model);
		scroll = new JScrollPane(table);
		center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
				
		table.getColumn("이름").setPreferredWidth(25);
		table.getColumn("이름").setCellRenderer(center);	
		table.getColumn("나이").setPreferredWidth(25);			
		table.getColumn("나이").setCellRenderer(center);			
		table.getColumn("성별").setPreferredWidth(25);			
		table.getColumn("성별").setCellRenderer(center);			
		table.getColumn("주소").setPreferredWidth(25);			
		table.getColumn("주소").setCellRenderer(center);			
		table.getColumn("연락처").setPreferredWidth(100);			
		table.getColumn("이메일").setPreferredWidth(115);			
		table.getColumn("INDEX").setPreferredWidth(10);			
		
		table.setRowHeight(20); // JTable 행 높이 조절
		
		scroll.setBounds(5, 355, 695, 400);
		
		add(scroll);
		
		getListAll();
		
		addrT.add(this);		
		table.addMouseListener(new Adapter());
	}
	public void getListAll() {
		model.setRowCount(0);
		
		String sql = "SELECT * FROM friend";
		
		try {
			pstmt = addrT.connect.conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String name = rs.getString("name");
			String age = rs.getString("age");
			String gender = rs.getString("gender");
			String addr = rs.getString("addr");
			String phone = rs.getString("phone");
			String email = rs.getString("email");
			int idx = rs.getInt("idx");
			
			Object[] newdata = {name, age, gender, addr, phone, email, idx};
			model.addRow(newdata);
		} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	class Adapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			addrT.button.jbChange.setEnabled(true);
			addrT.button.jbInput.setEnabled(false);
			
			srow = table.getSelectedRow();
			
			String name = model.getValueAt(srow, 0).toString();
			String age = model.getValueAt(srow, 1).toString();
			String gender = model.getValueAt(srow, 2).toString();
			String addr = model.getValueAt(srow, 3).toString();
			String phone = model.getValueAt(srow, 4).toString();
			String email = model.getValueAt(srow, 5).toString();
			
			String tempEmail[] = email.split("@");
			String email1 = null;
			String email2 = null;
			if (tempEmail != null && tempEmail.length >= 2) {
				email1 = tempEmail[0];
				email2 = tempEmail[1];
			}
			
			String tempPhone[] = phone.split("-");
			String phone1 = null;
			String phone2 = null;
			String phone3 = null;
			if (tempPhone != null && tempPhone.length >= 2) {
				phone1 = tempPhone[0];
				phone2 = tempPhone[1];
				phone3 = tempPhone[2];
			}
			
			addrT.input.jtName.setText(name);
			addrT.input.jtAge.setText(age);
			addrT.input.jtAddr.setText(addr);
			addrT.input.jtPhone.setText(phone1);
			addrT.input.jtBar1.setText(phone2);
			addrT.input.jtBar2.setText(phone3);
			addrT.input.jtEmail.setText(email1);
			addrT.input.jtDot.setText(email2);
			
			if (gender.equals("남자")) {
				addrT.input.genders[0].setSelected(true);
			}
			
			if (gender.equals("여자")) {
				addrT.input.genders[1].setSelected(true);
			}
			addrT.input.jtName.setEnabled(false);
			
		}
	
	
	}
}
