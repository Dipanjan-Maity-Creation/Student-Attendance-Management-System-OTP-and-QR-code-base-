package dao;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class tables {
    
    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;

        try {
            // Obtain a connection to the database
            con = ConnectionProvider.getCon();
            st = con.createStatement();

            // Create userdetails table if it doesn't exist
            if (!tableExists(st, "userdetails")) {
                st.executeUpdate("CREATE TABLE userdetails (\n" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    gender VARCHAR(50) NOT NULL,\n" +
                    "    email VARCHAR(255) NOT NULL,\n" +
                    "    contact VARCHAR(20) NOT NULL,\n" +
                    "    address VARCHAR(500),\n" +
                    "    state VARCHAR(100),\n" +
                    "    country VARCHAR(100),\n" +
                    "    uniqueregid VARCHAR(100) NOT NULL,\n" +
                    "    imagename VARCHAR(100)\n" +
                    ");");
            }

            // Create userattendance table if it doesn't exist
            if (!tableExists(st, "userattendance")) {
                st.executeUpdate("CREATE TABLE userattendance (\n" +
                    "    userid INT NOT NULL,\n" +
                    "    date DATE NOT NULL,\n" +
                    "    checkin DATETIME,\n" +
                    "    checkout DATETIME,\n" +
                    "    workduration VARCHAR(100)\n" +
                    ");");
            }
            
            // Show success message
            JOptionPane.showMessageDialog(null, "Tables created successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            // Close resources
            try {
                if (con != null) con.close();
                if (st != null) st.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Check if a table exists in the database
    private static boolean tableExists(Statement st, String tableName) throws Exception {
        ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
        return resultSet.next();
    }
}
