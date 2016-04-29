import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;


public class NewAccountPage implements ActionListener, FocusListener{
		JFrame newacnt;
		JLabel lbl_first, lbl_last, lbl_pwd, lbl_conf, lbl_uname, lbl_sex, lbl_email, lbl_img;
		JTextField txt_first, txt_last, txt_uname,txt_email;
		JPasswordField pwd, conf;
		ImageIcon img;
		JButton btcreate, btcancel, btcheck;
		JRadioButton male, female;
		String init1, init2, init3,sex;
		String url = "rmi://192.16.1.100/Server"; //Specify server IP here 
		
		public NewAccountPage()
		{
			newacnt = new JFrame("Create your UChat Account");
			
			JPanel panel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			


			lbl_first = new JLabel("First Name: ");
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(10,10,10,10);
			panel.add(lbl_first,c);
			
			txt_first = new JTextField("Enter first name");
			init1 = "Enter first name";
			c.gridx = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.HORIZONTAL;
			txt_first.addFocusListener(this);
			panel.add(txt_first,c);
			
			lbl_last = new JLabel("Last Name: ");
			c.gridx = 0;
			c.gridy = 1;
			panel.add(lbl_last,c);
			
			txt_last = new JTextField("Enter last name");
			init2 = "Enter last name";
			c.gridx = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridy = 1;
			txt_last.addFocusListener(this);
			panel.add(txt_last,c);
			
			lbl_uname = new JLabel("User Name: ");
			c.gridx = 0;
			c.gridy = 2;
			panel.add(lbl_uname,c);
			
			txt_uname = new JTextField("Enter user name");
			init3 = "Enter user name";
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 2;
			txt_uname.addFocusListener(this);
			panel.add(txt_uname,c);
			
			btcheck = new JButton("Check availability");
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 2;
			c.fill = GridBagConstraints.HORIZONTAL;
			btcheck.addActionListener(this);
			panel.add(btcheck,c);
			
			lbl_pwd = new JLabel("Password: ");
			c.gridx = 0;
			c.gridy = 3;
			panel.add(lbl_pwd,c);
			pwd = new JPasswordField(20);
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 3;
			panel.add(pwd,c);
			
			lbl_conf = new JLabel("Confirm Password: ");
			c.gridx = 0;
			c.gridy = 4;
			panel.add(lbl_conf,c);
			conf = new JPasswordField(20);
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 4;
			c.fill = GridBagConstraints.HORIZONTAL;
			panel.add(conf,c);
			
			lbl_sex = new JLabel("Sex: ");
			c.gridx = 0;
			c.gridy = 5;
			panel.add(lbl_sex,c);
			
			male = new JRadioButton("Male");
			c.gridy = 5;
			c.gridx = GridBagConstraints.RELATIVE;
			male.setOpaque(false);
			male.setActionCommand("m");
			male.addActionListener(this);
			
			
female = new JRadioButton("Female");
			female.setOpaque(false);
			female.setActionCommand("f");
			female.addActionListener(this);
			
			
ButtonGroup rbgroup = new ButtonGroup();
			rbgroup.add(male);
			rbgroup.add(female);
			panel.add(male,c);
			panel.add(female,c);
			
			lbl_email = new JLabel("Email address: ");
			c.gridx = 0;
			c.gridy = 6;
			panel.add(lbl_email,c);
			txt_email = new JTextField();
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 6;
			c.fill = GridBagConstraints.HORIZONTAL;
			panel.add(txt_email,c);
			
			btcreate = new JButton("Create Account");
			c.gridx = 0;
			c.gridy = 7;
			btcreate.addActionListener(this);
			panel.add(btcreate,c);
			
			btcancel = new JButton("Cancel");
			c.gridx = GridBagConstraints.RELATIVE;
			c.gridy = 7;
			btcancel.addActionListener(this);
			panel.add(btcancel,c);
			
panel.setBackground(Color.LIGHT_GRAY);
	   panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			newacnt.setVisible(true);
			newacnt.setSize(600,500);
			
						newacnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			newacnt.getContentPane().add(panel, BorderLayout.NORTH);
		}

	
		public void actionPerformed(ActionEvent ae) {
			
ImageIcon warning = new ImageIcon("src/Images/warning"); 
			if(ae.getSource() == btcheck){

		/*Check if specified User name is available*/
				String ucheck = txt_uname.getText();
				try {
					ClientMethodsInterface remobj_nap = (ClientMethodsInterface)Naming.lookup(url);
					int avl = remobj_nap.check_avl(ucheck);
					if(avl == -1){
			JOptionPane.showMessageDialog(newacnt,"Entered user name is not available! Please select a new one!","Sorry! User name not available!", JOptionPane.INFORMATION_MESSAGE,warning);
						txt_uname.setText("");
					}
					else{
						JOptionPane.showMessageDialog(newacnt,"Entered user name is available!","User name available!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (MalformedURLException mue) {
					System.err.println("Malformed URL Exception: " + mue);
				} catch (RemoteException re) {
					System.err.println("Remote Exception: " + re);
				} catch (NotBoundException nbe) {
					System.err.println("Not Bound Exception: " + nbe);
				}
			}
			else if(ae.getSource() == btcancel){
				newacnt.setVisible(false);
				Login ob = new Login();
			}
			else if(ae.getSource() == btcreate){
				char[] pcheck = {};
				char[] cnfcheck = {};
				pcheck = pwd.getPassword();
				cnfcheck = conf.getPassword();
				if(txt_uname.getText().equals(init3)){
					JOptionPane.showMessageDialog(newacnt, "Please enter preferred username!", "Warning: Username missing!", JOptionPane.INFORMATION_MESSAGE, warning);					
				}
				else if(pcheck.length == 0){
					JOptionPane.showMessageDialog(newacnt, "Please enter your password!", "Warning: Password missing!", JOptionPane.INFORMATION_MESSAGE, warning);
				}
				else if(cnfcheck.length == 0){
					JOptionPane.showMessageDialog(newacnt, "Please confirm your password!", "Warning: Password missing!", JOptionPane.INFORMATION_MESSAGE, warning);
				}
				else if(!checkpwd(pcheck,cnfcheck)){
					ImageIcon error =  new ImageIcon("src/Images/error5.png");
					JOptionPane.showMessageDialog(newacnt,"Entered passwords do not match!","Error: Password mismatch!", JOptionPane.ERROR_MESSAGE,error); 
					pwd.setText("");
conf.setText("");
				}
				else if(ae.getSource() == male || ae.getSource() == female){
					sex = ae.getActionCommand();
					System.out.println(sex);				}
				



else
				{
					/*Create New Account*/
					try
					{
					ClientMethodsInterface remobj_nap = (ClientMethodsInterface)Naming.lookup(url);
								  remobj_nap.create_account(this);
					}
					catch(RemoteException rex){
					System.err.println("RemoteException:" + rex);
					}
					catch(MalformedURLException mue)
					{
						System.err.println("Not a valid RMI URL");
					} catch (NotBoundException nbe) {
						System.err.println("Requested remote object not found in RMI registry");
					}
				}
			}
		}

		
/*Check if password and confirm password fields have same entry*/
		private boolean checkpwd(char[] pcheck, char[] cnfcheck) {
			
			if(Arrays.equals(pcheck,cnfcheck)){
				return true;
			}
			else
				return false;
		}

		
	
		

public void focusGained(FocusEvent fe) {
			if(fe.getSource() == txt_first){
				if(txt_first.getText().equals(init1)){
					txt_first.setText("");
				}}
			else if(fe.getSource() == txt_last){
				if(txt_last.getText().equals(init2)){
					txt_last.setText("");
				}}
			else if(fe.getSource() == txt_uname){
				if(txt_uname.getText().equals(init3)){
					txt_uname.setText("");
				}}
			}


		public void focusLost(FocusEvent fe) {
			if(fe.getSource() == txt_first){
				if(txt_first.getText().isEmpty()){
					txt_first.setText(init1);
				}}
			else if(fe.getSource() == txt_last){
				if(txt_last.getText().isEmpty()){
					txt_last.setText(init2);
				}}
			else if(fe.getSource() == txt_uname){
				if(txt_uname.getText().isEmpty()){
					txt_uname.setText(init3);
				}}
			}
		}
