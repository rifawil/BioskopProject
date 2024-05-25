
package bioskopproject.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Koneksi {
    
    private static Connection konek;
    public static Connection getConnection()throws SQLException{
        try{
            String url = "jdbc:mysql://localhost:3306/bioskop"; //url database
            String user = "root"; //user database
            String pass = ""; //password database
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            konek = DriverManager.getConnection(url, user, pass);
        } catch (Exception e){
            System.err.println("Koneksi Gagal "+e.getMessage()); //Menampilkan error koneksi
        }
        return konek;
    }
}
