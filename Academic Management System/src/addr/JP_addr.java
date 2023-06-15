package addr;

import java.awt.Color;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class JP_addr extends JFrame{
	DbConnect connect = new DbConnect();
	Head head = new Head(this);
	Input input = new Input(this);
	Check check = new Check(this);
	Table table = new Table(this);
	Button button = new Button(this);
	
	public JP_addr(){
		setTitle("자바 주소록 프로그램");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setLayout(null);
		setSize(720, 900);
		setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new JP_addr();
//
//	}

}
