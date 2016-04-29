
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import javax.swing.*;


public class Login implements ActionListener
{
		JFrame home;
		JPanel backpanel,frontpanel; 
		JLabel background,lbl_uname, lbl_pwd, lbl_noacnt, lbl_signup;
		JTextField txt_uname;
		JButton btlogin, btcancel, btsignup;
		JPasswordField pwd;
		String url = "rmi://192.16.1.100/Server"; //Specify Server IP here 
		
		
	    public Login()
	    {
		home = new JFrame("Login to UChat");
	
		backpanel = new JPanel(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		
		JLabel wc = new JLabel("Welcome To UChat");
		c1.anchor = GridBagConstraints.CENTER;
		backpanel.add(wc,c1);
		
		ImageIcon bg = new ImageIcon("src/Images/logo.jpg"); 
		
		
background = new JLabel(bg);
		c1.gridy = GridBagConstraints.RELATIVE;
		
		backpanel.add(background,c1);
        	backpanel.setOpaque(false);
        home.getContentPane().add(backpanel,BorderLayout.PAGE_START); 
		
		
		JPanel frontpanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lbl_uname = new JLabel("User name: ");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(lbl_uname,c);
		
		txt_uname = new JTextField();
		c.gridx = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(txt_uname,c);
		
		lbl_pwd = new JLabel("Password: ");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(lbl_pwd,c);
		
		pwd = new JPasswordField(20);
		c.gridx = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(pwd,c);
		
		btlogin = new JButton("Login");
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(btlogin,c);
		btlogin.addActionListener(this);
		
		btcancel = new JButton("Cancel");
		c.gridy = 2;
		c.gridx = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10,10,10,10);
		btcancel.addActionListener(this);
		frontpanel.add(btcancel,c); 
		
		lbl_noacnt = new JLabel("Do not have an account yet?");
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(lbl_noacnt,c);
		
		lbl_signup = new JLabel("Sign up here!");
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,10,10);
		frontpanel.add(lbl_signup,c);
		
		btsignup = new JButton("Sign up");
		c.gridy = 6;
		c.fill = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10,10,10,10);
		btsignup.addActionListener(this);
		
		frontpanel.setBackground(Color.WHITE);
					   frontpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		frontpanel.add(btsignup,c);   
		frontpanel.setOpaque(false);
		
		
		home.getContentPane().add(frontpanel, BorderLayout.AFTER_LAST_LINE);
		home.setVisible(true);
		home.setSize(600,600);
		home.pack();
	home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    }
	


	public void actionPerformed(ActionEvent ae) {
		 String par_uname = "";
		 char[] par_pwd = {};
		 int uid = -1;
		 
		 if(ae.getSource() == btlogin){
			 par_uname = txt_uname.getText();
			 par_pwd = pwd.getPassword();
			 
		      /*Check if User Name has been provided*/
if(txt_uname.getText().equals("")){
				 JOptionPane.showMessageDialog(home, "Please enter your user name!", "Warning: User name missing!", JOptionPane.WARNING_MESSAGE); 
			 }
			 else{
			   /*Check if Password has been provided*/
				 if(par_pwd.length == 0){
				 JOptionPane.showMessageDialog(home, "Please enter your password!", "Password missing!", JOptionPane.WARNING_MESSAGE); 
				 }
				 else{
					 /*Sign In*/
					 try {
						 ClientMethodsInterface remobj_log = (ClientMethodsInterface)Naming.lookup(url);
						 System.out.println("Found Server!");
						 uid = remobj_log.sign_in(par_uname, par_pwd);
					   } catch (MalformedURLException mue) {
						 System.err.println("Malformed URL Exception: "+ mue);
					   } catch (RemoteException re) {
						 System.err.println("Remote Exception: "+ re);
					   } catch (NotBoundException nbe) {
						 System.err.println("Not Bound Exception: "+ nbe);
					   }
						 if(uid == -1){
				/*Specified User does not exist*/
							 JOptionPane.showMessageDialog(home, "Specified user name does not exist!", "Warning: Invalid user name!", JOptionPane.WARNING_MESSAGE);
							 txt_uname.setText("");
							 pwd.setText("");
						 }
						 else if(uid == 0){
							 JOptionPane.showMessageDialog(home, "Specified user has already signed in!", "Warning: User logged in!", JOptionPane.WARNING_MESSAGE);
							 txt_uname.setText("");
							 pwd.setText("");
						 }
						 else{
							PostLoginPage plp = new PostLoginPage(par_uname,uid);
						 }
				 	}
			 }
		 }
			else if(ae.getSource() == btcancel){
				home.setVisible(false);
			}
			

else if(ae.getSource() == btsignup){
				
/*Create a new account*/
				NewAccountPage nap = new NewAccountPage();
				home.setVisible(false);
				
			 }
		
	}
		
		public static void main(String[] args) 
		{
			Login ob = new Login();
			if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new RMISecurityManager());
	            System.out.println("Security manager installed.");
	        } 
			else {
	            System.out.println("Security manager already exists.");
	        }
			
		
		}

}



