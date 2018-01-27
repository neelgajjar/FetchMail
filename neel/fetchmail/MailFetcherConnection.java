package fetchmail;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailFetcherConnection {
	private String username;
	private String password;
	private String host;
	private String protocol;
	//the host server for google and yahoo
	private final static String imapGmailHost = "imap.gmail.com";
	private final static String popGmailHost = "pop.gmail.com";
	private final static String imapYahooHost = "imap.mail.yahoo.com";
	private final static String popYahooHost = "pop.mail.yahoo.com";
	
	/* Constructor for the mailchecker connection class
	 * @param username login id for email address.
	 * @param password password for email address
	 * @param protocol the protocol selected for the email address. */
	
	public MailFetcherConnection(String username, String password, String protocol){
		username = username.trim();
		password = password.trim();
		protocol = protocol.trim();
		this.username = username;
		this.password = password;
		if(username.contains("yahoo")){
			if(protocol.equals("imaps")){
				host = imapYahooHost;
			}else{
				host = popYahooHost;
			}
		}else{
			if(protocol.equals("imaps")){
				host = imapGmailHost;
			}else{
				host = popGmailHost;
			}
		}
		this.protocol = protocol;
	}
	
	/* Connection to the email address provided and find any unread messeges(maximum of 50)
	 * @return retrn an arraylist of unread messeges, a single messeges notifying user that there ar no new messeges*/
	
	public ArrayList<String> fetchMail() throws MessagingException{
		Properties props = null;
		Session session = null;
		Store store = null;
		Folder inbox = null;
		Message messages[] = null;
		Flags seen = new Flags(Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		ArrayList<String> newMessages = new ArrayList<String>();
	try {
			
			props = System.getProperties();
			session = Session.getDefaultInstance(props, null);
			store = session.getStore(protocol);
			store.connect(host, username, password);	
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			if(inbox.getUnreadMessageCount() == 0){
				newMessages.add("There are no new messages.");
				return newMessages;
			}
			messages = inbox.search(unseenFlagTerm);
			int maximum = 50, i = messages.length-1;
			if(maximum > messages.length){
				maximum = messages.length; 
			}
			while(maximum >= 1){
				Message msg = messages[i];
				newMessages.add("From: "+msg.getFrom()[0].toString()+" Subject: "+msg.getSubject());
				i--;
				maximum--;
			}
			return newMessages;
		} catch (NoSuchProviderException e) {
			newMessages.add("There was a problem with the provider: "+e);
			return newMessages;
		} catch (MessagingException e) {
			newMessages.add("There was a problem with the email address connection: "+e);
			return newMessages;
		}
	}
}