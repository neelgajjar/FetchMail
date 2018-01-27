package fetchmail;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginPanel extends JFrame {
	private JPanel panel = new JPanel();
	private String username;
	private String password;
	private String interval;
	private String protocol;
	private int time;
	private Timer timer;
	private static final int SECONDS = 1000;
	
	public LoginPanel(String username,String password, String interval, String protocol, int time){
		this.username = username;
		this.password = password;
		this.interval = interval;
		this.protocol = protocol;
		this.time = time;
		this.timer = new Timer();
		initUI();
			
	}
	class IntervalTask extends TimerTask{
		public void run(){
			// when times up, connects to the email
			MailFetcherConnection connection = new MailFetcherConnection(username, password, protocol);
			try {
				ArrayList<String> list = connection.fetchMail();
				MailPanel mailPanel = new MailPanel(list);
				mailPanel.setVisible(true);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void initUI(){
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		//lable panel
		JLabel infoLable = new JLabel("Checking" + username +" every " + interval);
		panel.add(infoLable);
		
		//cancle button
		JButton cancleButton = new JButton("Cancle Mail Fetcher");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				timer.cancel();
				dispose();
			}
		
		});
		panel.add(cancleButton);
		timer.schedule(new IntervalTask(), time*SECONDS, time*SECONDS);
		add(panel);
		pack();
		setTitle("Mail Fetcher");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
