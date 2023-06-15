package addr;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Check extends JPanel implements ActionListener{
	JP_addr addrC;
	
	JComboBox jcInput;
	JTextField jtCheck = null;
	JButton jbCheck, jbCheckAll;
	
	String[] fieldnames = {"name", "age", "gender", "addr"};
	String[] checkBox = {"이름", "나이", "성별", "주소"};
	
	String sql;
	
	Font f2 = new Font("돋움", Font.BOLD, 15);
	
	Check(JP_addr addr){
		this.addrC = addr;
		System.out.println("조회 연결 완료");
		
		setLayout(null);
		setBounds(0, 270, 705, 80);
		setBackground(Color.blue);
		
		jcInput = new JComboBox(checkBox);
		jcInput.addItem("카테고리 선택");
		jcInput.setSelectedIndex(4);
		jcInput.setFont(f2);
		add(jcInput);
		jcInput.setBounds(50, 25, 180, 30);
		
		jtCheck = new JTextField();
		add(jtCheck);
		jtCheck.setBounds(250, 25, 200, 30);
		
		jbCheck = new JButton("조회");
		jbCheck.setFont(f2);
		jbCheck.setBackground(Color.green);
		jbCheck.setForeground(Color.red);
		add(jbCheck);
		jbCheck.setBounds(470, 25, 80, 30);
		jbCheck.addActionListener(this);
		
		jbCheckAll = new JButton("전체조회");
		jbCheckAll.setFont(f2);
		jbCheckAll.setBackground(Color.green);
		jbCheckAll.setForeground(Color.red);
		add(jbCheckAll);
		jbCheckAll.setBounds(570, 25, 100, 30);
		jbCheckAll.addActionListener(this);
				
		addrC.add(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object oc = e.getSource();
		String empty = jtCheck.getText();
			
		if (oc == jbCheckAll) {	
			addrC.table.getListAll();
			addrC.button.initTF();
		}	
		else if (oc == jbCheck) {	
			if (empty.isEmpty()) {
				JOptionPane.showMessageDialog(this, "조회할 내용을 선택해주세요");			
			}
			else {
				String field = fieldnames[jcInput.getSelectedIndex()];
				String value = jtCheck.getText();
				addrC.table.model.setRowCount(0);
				try {		
					sql = "SELECT * FROM FRIEND WHERE " + field + " = ?";
					addrC.table.pstmt = addrC.connect.conn.prepareStatement(sql);
					addrC.table.pstmt.setString(1, value);
					ResultSet rs = addrC.table.pstmt.executeQuery();
					while (rs.next()) {
						String name = rs.getString("name");
						String age = rs.getString("age");
						String gender = rs.getString("gender");
						String addr = rs.getString("addr");
						String phone = rs.getString("phone");
						String email = rs.getString("email");
						int idx = rs.getInt("idx");
						Object[] newdata = {name, age, gender, addr, phone, email, idx};
						addrC.table.model.addRow(newdata);
						}
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			jtCheck.setText("");
			addrC.button.initTF();
			
		
		}
	}		
}
