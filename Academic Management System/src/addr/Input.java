package addr;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Input extends JPanel implements ActionListener{
	JP_addr addrI;

	JLabel jlName, jlAge, jlAddr, jlgender, jlPhone, jlEmail, jlBar1, jlBar2, jlDot;
	JTextField jtName, jtAge, jtAddr, jtPhone, jtEmail, jtBar1, jtBar2, jtDot;
	JRadioButton[] genders;
	ButtonGroup group;
	JComboBox jcMail;
	
	
	String gender = null;
	
	Font f1 = new Font("맑은 고딕", Font.BOLD, 15);
	
	Input(JP_addr addr){
		this.addrI = addr;
		System.out.println("입력 연결 완료");
		
		TitledBorder t = new TitledBorder("입력");
		t.setTitleFont(new Font("맑은고딕",Font.PLAIN,13));
		setBorder(t);
		setLayout(null);
		setBounds(5, 60, 695, 200);
		
		
////////////////////////////////////////////////////////////////////////////////////////////////		
		
		jlName = new JLabel("이  름");
		jlName.setBounds(25, 25, 50, 30);
		jlName.setFont(f1);
		add(jlName);
		
		jtName = new JTextField();
		jtName.setBounds(75, 25, 220, 30);
		add(jtName);

////////////////////////////////////////////////////////////////////////////////////////////////		
	
		jlAge = new JLabel("나  이");
		jlAge.setBounds(320, 25, 50, 30);
		jlAge.setFont(f1);
		add(jlAge);
		
		jtAge = new JTextField();
		jtAge.setBounds(370, 25, 100, 30);
		add(jtAge);
		
////////////////////////////////////////////////////////////////////////////////////////////////
		
		group = new ButtonGroup();
		
		jlgender = new JLabel("성  별");
		jlgender.setFont(f1);
		jlgender.setBounds(490, 25, 50, 30);
		add(jlgender);
		
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
		genders[0].setFont(f1);
		genders[1].setFont(f1);
		genders[0].setBounds(545, 25, 60, 30);
		genders[1].setBounds(610, 25, 60, 30);
		add(genders[0]);
		add(genders[1]);
			
////////////////////////////////////////////////////////////////////////////////////////////////
		
		jlAddr = new JLabel("주  소");
		jlAddr.setFont(f1);
		jlAddr.setBounds(25, 65, 50, 30);
		add(jlAddr);
		
		jtAddr = new JTextField();
		jtAddr.setBounds(75, 65, 589, 30);
		add(jtAddr);

////////////////////////////////////////////////////////////////////////////////////////////////
		
		jlPhone = new JLabel("연락처");
		jlPhone.setFont(f1);
		jlPhone.setBounds(25, 105, 80, 30);
		add(jlPhone);
		
		jtPhone = new JTextField();
		jtPhone.setBounds(75, 105, 173, 30);
		add(jtPhone);
		
		jlBar1 = new JLabel("-");
		jlBar1.setFont(f1);
		jlBar1.setBounds(263, 105, 10, 30);
		add(jlBar1);
		
		jtBar1 = new JTextField();
		jtBar1.setBounds(283, 105, 173, 30);
		add(jtBar1);
		
		jlBar2 = new JLabel("-");
		jlBar1.setFont(f1);
		jlBar2.setBounds(471, 105, 10, 30);
		add(jlBar2);
		
		jtBar2 = new JTextField();
		jtBar2.setBounds(491, 105, 173, 30);
		add(jtBar2);
		
////////////////////////////////////////////////////////////////////////////////////////////////
		
		jlEmail = new JLabel("이메일");
		jlEmail.setFont(f1);
		jlEmail.setBounds(25, 145, 80, 30);
		add(jlEmail);
		
		jtEmail = new JTextField();
		jtEmail.setBounds(75, 145, 173, 30);
		add(jtEmail);
		
				
		jlDot = new JLabel("@");
		jlDot.setBounds(259, 145, 20, 30);
		jlDot.setFont(f1);
		add(jlDot);
				
		jtDot = new JTextField();
		jtDot.setBounds(283, 145, 200, 30);
		add(jtDot);
				
		jcMail = new JComboBox();
		jcMail.setBounds(500, 144, 163, 30);
		jcMail.addItem("선택");
		jcMail.addItem("naver.com");
		jcMail.addItem("daum.net");
		jcMail.addItem("google.com");
		jcMail.addActionListener(this);
		
		add(jcMail);
		
		addrI.add(this);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == jcMail) {
			if(jcMail.getSelectedItem() == "naver.com") {
				jtDot.setText("naver.com");
			}
			if(jcMail.getSelectedItem() == "daum.net") {
				jtDot.setText("daum.net");
			}
			if(jcMail.getSelectedItem() == "google.com") {
				jtDot.setText("google.com");
			}
			
		}
		
	}
	
}
