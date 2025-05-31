package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.KhuVuc;

public class KhuVuc_DAO {
	private ArrayList<KhuVuc> dskv = null;
	public ArrayList<KhuVuc> layToanBoKhuVuc()
	{	
		try
		{
			this.dskv = new ArrayList<KhuVuc>();
			Connection con  = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("Select * from KhuVuc");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				this.dskv.add(new KhuVuc(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return this.dskv;
	}
}
