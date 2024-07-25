package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateDataIntoDB {

	public static void updateAvlBalanceForLoad(double amt, String label) {

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Map<String, String> accDetails = new LinkedHashMap<String, String>();
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			double updateAvlBlc = FetchDataFromDB.getAvlBlc(label) + amt;
			String query = "UPDATE `bank`.`account_details` SET `avl_blc` = '" + updateAvlBlc
					+ "' WHERE (`ledger_label` = '" + label + "')";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.err.println(
						"Problem while closing update avl balance for load data base connection: " + e.getMessage());
			}
		}
	}

	public static void updateAvlBalanceForPayout(double amt, String label){

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Map<String, String> accDetails = new LinkedHashMap<String, String>();
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			double updateAvlBlc = FetchDataFromDB.getAvlBlc(label) - amt;
			String query = "UPDATE `bank`.`account_details` SET `avl_blc` = '" + updateAvlBlc
					+ "' WHERE (`ledger_label` = '" + label + "')";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.err.println(
						"Problem while closing update avl balance for payout data base connection: " + e.getMessage());
			}
		}
	}

	public static void insertSubBranchStatement(String utr, String subStm) {
		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Map<String, String> accDetails = new LinkedHashMap<String, String>();
		// Establishing the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);

			String query = "insert into bank.sub_branch_statement (utr,txn_type,sub_statement) values('" + utr
					+ "','DR','" + subStm + "')";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.err.println(
						"Problem while closing insert sub branch statement data base connection: " + e.getMessage());
			}

		}

	}

	/*-----------------------------------------------------To delete the txn from the sub branch queue----------------------------------------------*/
	public static void deleteTxnFromQueue(String utr){

		// JDBC URL, username, and password of the MySQL database
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
		String username = "Guruprasad";
		String password = "MySql@#123";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);

			String query = "delete from bank.sub_branch_statement where utr = '" + utr + "'";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.err.println("Connection failed. Error: " + e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.err.println(
						"Problem while closing delet txn form queue data base connection: " + e.getMessage());
			}
		}

	}
}
