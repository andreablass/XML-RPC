//Permitir la conexión con BD Mysql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMysql {
    private static String ipAdress = "localhost"; //Dirección IP
    private static String dataBaseName = "xmlrpc"; //DNombre de la BD
    private static String user = "root"; //Usuario de la BD (MysQL)
    private static String password = null; //Contraseña BD (MysQL)
    private static String port = "3306"; //Puerto del Servicio de MysQL

    public static Connection getConnection() throws SQLException {
        try {
            //Buscar el Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            //No encontró el Driver
            System.out.println("No se encontró el Driver ...."+ex);
        }
        //Cadena URL  de Conexión
        String con = "jdbc:mysql://"+ipAdress+":"+port+"/"+dataBaseName;
        //Realiza la conexión
        return  DriverManager.getConnection(con, user, password);

    }

    public static void main(String[] args) {
        Connection con;
        try{
            con = getConnection();
            System.out.println("Status connection ON "+con);
            con.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        }
     }

