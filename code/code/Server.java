import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.Vector;

public class Server extends UnicastRemoteObject implements ClientMethodsInterface
{
Vector<String> list;
static int count = 0;
String last_message = "";
	String new_message = "";
	String istatus = "off";
	String ifirst, ilast, iname, imail, isex;
	int uid;
	char[] ipwd;

	
	protected Server() throws RemoteException, SQLException {
		super();
	}

	
	/*Open Database Connection*/
	public Connection dbconnect() throws SQLException,ClassNotFoundException{
	
Class.forName("oracle.jdbc.OracleDriver");
	
Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","22022010");
	return con;
	}
	
	public void create_account(NewAccountPage obj) throws RemoteException {
		
		count = count + 1;		
		
		ifirst = obj.txt_first.getText();
		ilast = obj.txt_last.getText();
		iname = obj.txt_uname.getText();
		ipwd = obj.pwd.getPassword();
		isex = obj.sex;
		imail = obj.txt_email.getText();
		uid = count;
		istatus = "on";
		
		
/*Database Entry*/
		try{
			Connection con1 = dbconnect();
		PreparedStatement db_entry = con1.prepareStatement("insert into Client_info values(ifirst,ilast,iname,ipwd,isex,imail,uid,istatus");
		ResultSet rs = db_entry.executeQuery();
		} catch(SQLException sqle){
			System.err.println("SQL Exception: " + sqle);
		} catch(ClassNotFoundException cnfe){
			System.err.println("Class Not Found Exception: " + cnfe);
		}
		
		/*Server Vector Entry*/
		list.add(iname);
		System.out.println(list);
		
		/*Post Login Page appears*/
		obj.newacnt.setVisible(false);
		PostLoginPage plp = new PostLoginPage(iname,uid);
	}


public int sign_in(String un, char[] pw) throws RemoteException {
			int uid_ret = -1;
			
			try {
			Connection con1 = dbconnect();
			PreparedStatement query = con1.prepareStatement("select Status, User_id from Client_info where where User_name = par_uname and Password = par_pwd");	
			ResultSet rs = query.executeQuery();
			if(!rs.next()){
				uid_ret = -1;
			}
			else
			{
				istatus = rs.getString(1);
				uid_ret = rs.getInt(2);
				if(istatus == "on"){
				uid_ret = 0;
				}
				else{
				istatus = "on";
				con1.prepareStatement("update Client_info set Status = istatus where User_name = par_uname and Password = par_pwd");
				}
			}
			rs.close();
			query.close();
			list.add(un);
			} catch (ClassNotFoundException cnfe) {
			System.err.println("Class Not Found Exception: " + cnfe);
			} catch (SQLException sqle) {
			System.err.println("SQL Exception: " + sqle);
			}
		return uid_ret;	}
	

public int sign_out(String x) throws RemoteException {
			String unm = x;
			
			/*Status changed to "off" in Database*/
			try{
			Connection con1 = dbconnect();
			PreparedStatement log_out = con1.prepareStatement("update Client_info set Status = 'off' where User_name = unm");
			log_out.executeQuery();
			list.remove(x);
			System.out.println(list);
			} catch (ClassNotFoundException cnfe) {
			System.err.println("Class Not Found Exception: " + cnfe);
			} catch (SQLException sqle) {
			System.err.println("SQL Exception: " + sqle);
			}
			return -1;
		}

	
	/*Check database to see if entered user name is available*/
	public int check_avl(String username) throws RemoteException{
		
		String uname_avl = username;
		int ret = -1;
		
		try{
			Connection con1 = dbconnect();
			PreparedStatement uname_chk = con1.prepareStatement("select User_id from Client_info where User_name = uname_avl");
			ResultSet rs = uname_chk.executeQuery();
				if(rs.next()){
					ret = -1;
				}
				else{
					ret = 0;
				}
			} catch(ClassNotFoundException cnfe) {
				System.err.println("Class Not Found Exception: " + cnfe);
			} catch (SQLException sqle) {
			System.err.println("SQL Exception: " + sqle);
			}
			return ret;
		}


	
public Vector getMembers() throws RemoteException {
		return list;
	}


	
public boolean broadcast_message(String from, String msg) throws RemoteException {
		last_message = new_message;
		new_message = msg;
		System.out.println(new_message);
		return true;
	}


	
public String get_broadcast() throws RemoteException {
		if(new_message.equals(last_message)){
			return "";
		}
		else{
			return new_message;
		}
	}

	

	
	public static void main(String[] args)
	{
		String name = "rmi://localhost/Server";
		
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security manager installed.");
        } 
		else {
            System.out.println("Security manager already exists.");
        }
		
		try { 
            LocateRegistry.createRegistry(1099); 
            System.out.println("RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("RMI registry already exists.");
        }
        
		try 
		{
			Server server = new Server();
			Naming.rebind(name,server);
			System.out.println("UChat Server ready");
		}
		  catch(RemoteException rex) {
			System.err.println("Remote Exception:" + rex);
		} catch(MalformedURLException mue){
			System.err.println("Malformed URL Exception:" + mue);
		} catch (SQLException sqle) {
			System.err.println("SQL Exception:" + sqle);
		} 
	}

}	

