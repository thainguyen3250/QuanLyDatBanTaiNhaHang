package ConnectDB;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	public static ConnectDB getInstance()
	{
		return instance;
	}
	public void connect()
	{
		String url ="jdbc:sqlserver://localhost:1433;databasename=quanlynhahang";
		String user ="sa";
		String pass ="sapassword";
		try
		{
			con = DriverManager.getConnection(url, user, pass);	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void disconnect()
	{
		if(con!=null)
		{
			try{con.close();}
			catch(Exception e)
			{e.printStackTrace();}
		}
	}
	public static Connection getConnection()
	{
		return con;
	}
}