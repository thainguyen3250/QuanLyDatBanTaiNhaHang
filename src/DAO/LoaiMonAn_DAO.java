package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.LoaiMonAn;

public class LoaiMonAn_DAO {
	private ArrayList<LoaiMonAn> dsloai;
	public ArrayList<LoaiMonAn> getAllLoaiMonAn()
	{
		Connection con = ConnectDB.getInstance().getConnection();
		String sql ="Select * from LoaiMonAn";
		dsloai = new ArrayList<LoaiMonAn>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String maLoaiMonAn = rs.getString(1);
				String tenLoaiMonAn = rs.getString(2);
				dsloai.add(new LoaiMonAn(maLoaiMonAn,tenLoaiMonAn));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.dsloai;
	}
	
}
