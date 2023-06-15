package addr;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Button extends JPanel implements ActionListener {
	JP_addr addrB;
	
	JButton jbInput, jbChange, jbDelete, jbExit;
	Color color;
	Font f3 = new Font("고딕", Font.PLAIN, 25);
	
	Button(JP_addr addr){
		this.addrB = addr;
		System.out.println("버튼 연결 완료");
		
		setLayout(null);
		setBounds(0, 750, 705, 150);
		
		jbInput = new JButton("입력");
		jbInput.setBounds(8, 50, 165, 50);
		jbInput.setBackground(color.blue);
		jbInput.setForeground(color.yellow);
		jbInput.setFont(f3);
		add(jbInput);
		jbInput.addActionListener(this);
		
		jbChange = new JButton("수정");
		jbChange.setBounds(183, 50, 165, 50);
		jbChange.setBackground(color.yellow);
		jbChange.setForeground(color.black);
		jbChange.setFont(f3);
		jbChange.setEnabled(false);
		add(jbChange);
		jbChange.addActionListener(this);
		
		jbDelete = new JButton("삭제");
		jbDelete.setBounds(358, 50, 165, 50);
		jbDelete.setBackground(color.green);
		jbDelete.setForeground(color.red);
		jbDelete.setFont(f3);
		add(jbDelete);
		jbDelete.addActionListener(this);
		
		jbExit = new JButton("종료");
		jbExit.setBounds(533, 50, 165, 50);
		jbExit.setBackground(color.magenta);
		jbExit.setForeground(color.white);
		jbExit.setFont(f3);
		add(jbExit);
		jbExit.addActionListener(this);
		
		addrB.add(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		String name = addrB.input.jtName.getText();
		String age = addrB.input.jtAge.getText();
		String addr = addrB.input.jtAddr.getText();
		String phone1 = addrB.input.jtPhone.getText();
		String phone2 = addrB.input.jtBar1.getText();
		String phone3 = addrB.input.jtBar2.getText();
		String email1 = addrB.input.jtEmail.getText();
		String email2 = addrB.input.jtDot.getText();
		String phone = (phone1 + "-" + phone2 + "-" + phone3);
		String email = (email1 + "@" + email2);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		if (ob == jbInput) {

			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(this, "이름을 입력해주세요");
				addrB.input.jtName.requestFocus();
				return;
			}
			if (age.isEmpty()) {
				JOptionPane.showMessageDialog(this, "나이를 입력해주세요");
				addrB.input.jtAge.requestFocus();
				return;
			}
			if (addr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "주소를 입력해주세요");
				addrB.input.jtAddr.requestFocus();
				return;
			}
			if (phone1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "연락처를 입력해주세요");
				addrB.input.jtPhone.requestFocus();
				return;
			}
			if (phone2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "연락처를 입력해주세요");
				addrB.input.jtBar1.requestFocus();
				return;
			}
			if (phone3.isEmpty()) {
				JOptionPane.showMessageDialog(this, "연락처를 입력해주세요");
				addrB.input.jtBar2.requestFocus();
				return;
			}
			if (email1.isEmpty()) {
				JOptionPane.showMessageDialog(this, "이메일을 입력해주세요");
				addrB.input.jtEmail.requestFocus();
				return;
			}
			if (email2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "이메일을 입력해주세요");
				addrB.input.jtDot.requestFocus();
				return;
			}
			
			Object rowData[] = {name, age, addrB.input.gender, addr, phone, email}; 
			addrB.table.model.addRow(rowData);
			
			int result = regist(name, age, addrB.input.gender, addr, phone, email);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "등록성공");
				addrB.table.getListAll();
				initTF();
			}else
				initTF(); 
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		else if (ob == jbChange) {
			int result = edit(name, age, addrB.input.gender, addr, phone, email);
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "수정성공");
				addrB.table.getListAll();
				initTF();
			}else
				initTF(); 
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		else if (ob == jbDelete) {
			if (addrB.table.srow < 0) { // JTable에서 선택된 행이 없다면 -1 리턴
				JOptionPane.showMessageDialog(null, "삭제할 정보를 선택하세요");
				return;
			}
			name = (String) addrB.table.model.getValueAt(addrB.table.srow, 0);
			
			int yn = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까", "INDEX", JOptionPane.YES_NO_OPTION);
			
			if (yn == JOptionPane.YES_OPTION) {
				int result = delete(name);
				addrB.table.getListAll();
				initTF(); 
			}else
				initTF();
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		else if (ob == jbExit) {
			addrB.dispose();
		}
		
	}
	public int regist(String name, String age, String gender, String addr, String phone, String email) {
		int result = 0;
		String sql = "INSERT INTO friend(name, age, gender, addr, phone, email) VALUE(?,?,?,?,?,?)";
		try {
			addrB.table.pstmt = addrB.connect.conn.prepareStatement(sql);
			addrB.table.pstmt.setString(1, name);
			addrB.table.pstmt.setString(2, age);
			addrB.table.pstmt.setString(3, gender);
			addrB.table.pstmt.setString(4, addr);
			addrB.table.pstmt.setString(5, phone);
			addrB.table.pstmt.setString(6, email);
			result = addrB.table.pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int edit(String name, String age, String gender, String addr, String phone, String email) {
		int result = 0;
		String sql = "UPDATE friend SET age = ?, gender = ?, addr = ?, phone = ?, email = ? WHERE name =?";
		try {
			addrB.table.pstmt = addrB.connect.conn.prepareStatement(sql);
			addrB.table.pstmt.setString(1, age);
			addrB.table.pstmt.setString(2, gender);
			addrB.table.pstmt.setString(3, addr);
			addrB.table.pstmt.setString(4, phone);
			addrB.table.pstmt.setString(5, email);
			addrB.table.pstmt.setString(6, name);
			result = addrB.table.pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public int delete (String name) {
		int result = 0;
		String sql = "DELETE FROM friend WHERE name =?";
		try {
			addrB.table.pstmt = addrB.connect.conn.prepareStatement(sql);
			addrB.table.pstmt.setString(1, name);	
			result = addrB.table.pstmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	void initTF() {
		addrB.input.jtName.setEditable(true);
		addrB.input.jtName.setText("");
		addrB.input.jtAge.setText("");
		addrB.input.jtAddr.setText("");
		addrB.input.jtPhone.setText("");
		addrB.input.jtEmail.setText("");
		addrB.input.jtBar1.setText("");
		addrB.input.jtBar2.setText("");
		addrB.input.jtDot.setText("");	
		jbInput.setEnabled(true);
		jbChange.setEnabled(false);
		addrB.input.jtName.setEnabled(true);
		addrB.input.jcMail.setSelectedIndex(0);
		addrB.check.jcInput.setSelectedIndex(4);
		addrB.input.jtName.requestFocus();
	}
}
