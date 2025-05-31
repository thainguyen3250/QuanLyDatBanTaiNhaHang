package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import ConnectDB.ConnectDB;
import Entity.LoaiNhanVien;

public class LoaiNhanVien_DAO {
	private ArrayList<LoaiNhanVien> dslnv;
	public ArrayList<LoaiNhanVien> getAllLoaiNhanVien()
	{
		try
		{
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="select * from LoaiNhanVien";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				dslnv.add(new LoaiNhanVien(rs.getString(1),rs.getString(2)));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return this.dslnv;
	}
}
