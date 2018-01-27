package fetchmail;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MailPanel extends JFrame {
	private ArrayList<String> emailText;
	JPanel panel = new JPanel();
	public MailPanel(ArrayList<String> emailText){
		this.emailText = emailText;
		initUI();
	}
	
	private void initUI(){
		panel = new JPanel();
		//Alliened to Y axis
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//declares the textbox where information about email is uotputted
		final JTextArea textBox = new JTextArea(30,40);
		textBox.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(textBox);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);
		
		for(int i=0;i<emailText.size();i++){
			textBox.setText(textBox.getText() + emailText.get(i)+"\n");
		}
		add(panel);
		pack();
		setTitle("FetchMail");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
