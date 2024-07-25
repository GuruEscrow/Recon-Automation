package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchDataFromDB {

	public static double getAvlBlc(String ledger_label) {

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double avl_blc = 0.0;
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			String query = "SELECT * FROM bank.account_details where ledger_label='" + ledger_label + "'";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				avl_blc=resultSet.getDouble("avl_blc");
			}
		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			}catch(Exception e) {
				System.err.println("Problem in closing the getAvt Blc Data base connection: "+ e.getMessage());
			}
			
		}
		return avl_blc;
	}

	public static Map<String, String> getAccDetails(String ledger_label) {

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<String,String> accDetails=new LinkedHashMap<String,String>();
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			String query = "SELECT * FROM bank.account_details where ledger_label='" + ledger_label + "'";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				accDetails.put("acc_no",resultSet.getString("account_no"));
				accDetails.put("ifsc",resultSet.getString("ifsc"));

			}
		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();	
			}catch(Exception e) {
				System.out.println("Problem while closing get Acc details Data base connection: "+e.getMessage());
			}
		}
		return accDetails;
	}
	
	public static boolean validateForMoneyLoad(String ledger_label,String acc_no,String ifsc) {

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean flag=false;
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			String query = "SELECT * FROM bank.account_details where ledger_label='" + ledger_label + "'";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if(resultSet.getString("account_no").equals(acc_no)&&resultSet.getString("ifsc").equals(ifsc)) {
					flag=true;
				}else {
					flag=false;
				}

			}
		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			}catch(Exception e) {
				System.out.println("Problem while closing the validate for money data base connection: "+e.getMessage());
			}
			
		}
		return flag;
	}
}
