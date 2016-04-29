import java.rmi.*;
import java.util.Vector;

/*This interface is implemented by the Server 
  and specifies the methods which can be invoked by the Clients using a remote object
*/

public interface ClientMethodsInterface extends Remote
{
	public void create_account(NewAccountPage nap) throws RemoteException;
	public int sign_in(String uname, char[] pwd) throws RemoteException;
	public int sign_out(String uname) throws RemoteException;
	public int check_avl(String uname) throws RemoteException;
	
	//Get list of clients currently logged in
	public Vector<String> getMembers() throws RemoteException;

	//Send message to be broadcast server
	public boolean broadcast_message(String from, String msg) throws RemoteException;

	//Message displayed on chatboard when broadcast is successful
	public String get_broadcast() throws RemoteException;
	
}


