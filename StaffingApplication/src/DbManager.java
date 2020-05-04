/* DBManager.java
 * Author: Daniel Collins 
 * Date: May 2020
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class DbManager {
 
    public String date = new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date());
    //private final String createTable = "CREATE TABLE `" + date + "` (id INT, data VARCHAR(100));";    
    
    ResultSet rs;
    ResultSetMetaData rsmd;
    int rsSize; 
    
    private static final int LoginTimeout = 10;
    
    // get methods    
    public ResultSet getResultSet(){
		return rs;
	}
	
	public ResultSetMetaData getResultSetMetaData(){
		return rsmd;
	}
	
	public int getRsSize(){
		try {
			rs.last();
			rsSize = rs.getRow();
			rs.beforeFirst();
		}
		catch(Exception ex) {
			return 0;
		}
		return rsSize;
	}
 
    public DbManager() {    }
 
    public Connection createConnection() throws IOException, ClassNotFoundException, SQLException {
        Properties prop = new Properties();
        //System.out.println("\n\n=======================\nJDBC Connector Test " + date);
        //System.out.println("User home directory: " + System.getProperty("user.home"));
        String host;
        String username;
        String password;
        String driver;
        try {
            //prop.load(new java.io.FileInputStream(System.getProperty("user.home") + "/mydb.cfg"));
            prop.load(new java.io.FileInputStream("mydb.cfg"));
 
            host = prop.getProperty("host").toString();
            username = prop.getProperty("username").toString();
            password = prop.getProperty("password").toString();
            driver = prop.getProperty("driver").toString();
        } catch (IOException e) {
            System.out.println("Unable to find mydb.cfg in " + System.getProperty("user.home") + "\n Please make sure that configuration file created in this folder.");
             
            host = "Unknown HOST";
            username = "Unknown USER";
            password = "Unknown PASSWORD";
            driver = "Unknown DRIVER";
        }
 
        //System.out.println("host: " + host + "\nusername: " + username + "\npassword: " + password + "\ndriver: " + driver);
 
        Class.forName(driver);
        //System.out.println("--------------------------");
        //System.out.println("DRIVER: " + driver);
        //System.out.println("Set Login Timeout: " + LoginTimeout);
        DriverManager.setLoginTimeout(LoginTimeout);
        Connection connection = DriverManager.getConnection(host, username, password);
        //System.out.println("CONNECTION: " + connection);
 
        return connection;
    }
 
    public String runSqlStatement(String sqlQuery) {
        String result = "";
        try {
            Statement statement = createConnection().createStatement();	//ResultSet.TYPE_SCROLL_INSENSITIVE, 
            //ResultSet.CONCUR_READ_ONLY);                           

            rs = statement.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            
            /* test display of result set information
            String name = rsmd.getColumnName(3);
            System.out.println("column name (3):" + name);            
            
			while(rs.next())
			{
				System.out.println(rs.getString(3)); //or rs.getString("column name");
			}
			*/
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception occurred: " + ex);
            result = ex.getMessage();
        } catch (SQLException ex) {
			System.out.println("Error...");
            ex.printStackTrace();
            result = ex.getMessage();
        }
        return result;
    }
}
