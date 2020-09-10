import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class HandlerPerson {
    //Funciona casi igual que un DAO...

    public boolean insertPerson(HashMap hashMapPerson) throws SQLException {
        //["name]='Fulanito',['lastname'] = 'Peresz'....
        Person person = new Person();
        //Obtener del hasmap el valo donde la clase (key) sea "name"
        // Y guardalo en Name Person
        person.setName(hashMapPerson.get("name").toString());
        person.setLastname(hashMapPerson.get("lastname").toString());
       //Parse "Wrappers"
        person.setAge(Integer.parseInt(hashMapPerson.get("age").toString()));
        // hashMapPerson.get("height) <- OBJETO
        person.setHeight(Double.parseDouble(hashMapPerson.get("height").toString()));
       // Boolean.valueOf transforma los string a bolleanos
        person.setStatus(Boolean.valueOf(hashMapPerson.get("status").toString()));

        boolean flagInsert = false;
        Connection connect = null;
        try{
            connect = ConexionMysql.getConnection();
            connect.setAutoCommit(false);
            String sqlQuery = " INSERT INTO person (name, lastaname, age, height, status) VALUES (?,?,?,?,?) ";
            PreparedStatement ps;
            ps = connect.prepareStatement(sqlQuery);
            ps.setString(1, person.getName());
            ps.setString(2, person.getLastname());
            ps.setInt(3, person.getAge());
            ps.setDouble(4, person.getHeight());
            ps.setBoolean(5, person.isStatus());
            //ps.executeUpdate() Regresa 1 si se insertó correctamente...y regresa cualquier otro numero si no se inserto
            flagInsert = (ps.executeUpdate() == 1); //<--- INSERT
            ps.close(); //Cerrar el PreparedStatement
            connect.commit();//Aprueb alos cambios de la BD
            connect.close(); //Cerrar la conexión
        }catch (SQLException e){
            System.out.println("SQLException Error Insesperado"+e.getMessage());
            connect.rollback();//Si ocurre un error regresa la base de datos a como estaba en su estado anterior
        }
        //Regresar Resultado (Se inserto o no)
        return flagInsert;
    }

    //HashMap "Son arreglos que tienen un key+value"
    //'name' = 'Fulanito'

    //Array "Son arreglos que tienen posiciones"
    //[0] = 'Valor'

}
