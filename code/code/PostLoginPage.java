import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PostLoginPage implements ActionListener{
		
		JFrame plpframe;
		
		JButton btchat,btout;
		JTextArea chatarea,enterchat;
		JPanel p1,p2;
		JLabel uchat,pictest;
		
		String uname;
		int uid;
boolean success = false;
		String url = "rmi://192.16.1.100/Server"; //Specify server IP here 
		
ClientMethodsInterface remobj_plp = null;
		
		
		/*Constructor*/
		public PostLoginPage(String txt, int id)
		{
			uname = txt;
			uid = id;
			try {
				remobj_plp = (ClientMethodsInterface) Naming.lookup(url);
			} catch (MalformedURLException mue) {
				System.err.println("Malformed URL Exception: " + mue);
			} catch (RemoteException re) {
				System.err.println("Remote Exception: " + re);
			} catch (NotBoundException nbe) {
				System.err.println("Not Bound Exception Exception: " + nbe);
			} 
			
			init();
		}
			
		
public void init(){
			plpframe = new JFrame("UChatBoard");
			
			/*Top Panel*/
			p1 = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			
		    chatarea = new JTextArea(10,10);
	        c.gridx = 0;
	        c.gridy = 0;
	        c.gridheight = 6;
	        c.anchor = GridBagConstraints.NORTH;
	        c.gridwidth = 50;
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.insets = new Insets(10,10,10,10);
	        chatarea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	        chatarea.setEditable(false);
	        chatarea.setLineWrap(true);
			p1.add(chatarea,c);
			
			enterchat = new JTextArea(10,5);
			c.gridx = 0;
	        	c.gridy = GridBagConstraints.RELATIVE;
	        	c.gridheight = 2;
	        	c.gridwidth = 50;
	        	c.fill = GridBagConstraints.HORIZONTAL;
	        	c.insets = new Insets(10,10,10,10);
	        enterchat.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	        enterchat.setEditable(true);
            p1.add(enterchat,c);

			btchat = new JButton("Send");
			c.gridx = 0; 
c.gridy = 9; 
c.gridwidth = 3;
	        	c.insets = new Insets(10,10,10,10);
	        	btchat.addActionListener(this);
			p1.add(btchat,c);
			
			btout = new JButton("Sign Out");
			c.gridx = 5;
			c.gridy = 9;
			c.gridwidth = 3;
c.insets = new Insets(10,10,10,10);
			btout.addActionListener(this);
			p1.add(btout,c);
			
			plpframe.add(p1, BorderLayout.NORTH);
			
			plpframe.setSize(800,800);
			plpframe.setVisible(true);
			plpframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
			
			
			
public void actionPerformed(ActionEvent ae) {
				String brdcst = "";
				
				if(ae.getSource() == btout){
					int test=0;
					try {
						test = remobj_plp.sign_out(uname);
						System.out.println("Sign out returned: " + test);
						System.out.println("Client has signed out!");
						
						/*Go back to Login page*/
						plpframe.setVisible(false);
						Login newob = new Login();
						
					} catch (RemoteException re) {
						System.err.println("Remote Exception: " + re);
					} 
				}
				else if(ae.getSource() == btchat){
					String msg = uname + ": " + enterchat.getText();
					System.out.println(msg);
					
					try {
						
						success = remobj_plp.broadcast_message(uname, msg);
						
					} catch (RemoteException re) {
						System.out.println("Could not broadcast message: " + re);
					}
					
					while(true){
						try {
							brdcst = remobj_plp.get_broadcast();
						} catch (RemoteException re) {
							System.out.println("Could not get latest broadcast: " + re);
						}
						if(brdcst == ""){}
						else{
							chatarea.append(brdcst);
						}
					}
				}
			}
