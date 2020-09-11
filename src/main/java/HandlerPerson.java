import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HandlerPerson {
    //Funciona casi igual que un DAO...
    public boolean updatePerson(HashMap hashMapPerson) throws SQLException {
        Person person = new Person();
       person.setId(Integer.parseInt(hashMapPerson.get("id").toString()));
        person.setName(hashMapPerson.get("name").toString());
        person.setLastname(hashMapPerson.get("lastname").toString());
        //Parse "Wrappers"
        person.setAge(Integer.parseInt(hashMapPerson.get("age").toString()));
        // hashMapPerson.get("height) <- OBJETO
        person.setHeight(Double.parseDouble(hashMapPerson.get("height").toString()));
        // Boolean.valueOf transforma los string a bolleanos
        person.setStatus(Boolean.valueOf(hashMapPerson.get("status").toString()));

        boolean flagUpdate = false;
        Connection connect = null;
        try{
            connect = ConexionMysql.getConnection();
            connect.setAutoCommit(false);
            String sqlQuery = " UPDATE person set  = ?, lastaname = ?, age = ?, height = ?, status = ? WHERE id =? ";
            PreparedStatement ps;
            ps = connect.prepareStatement(sqlQuery);
            ps.setString(1, person.getName());
            ps.setString(2, person.getLastname());
            ps.setInt(3, person.getAge());
            ps.setDouble(4, person.getHeight());
            ps.setBoolean(5, person.isStatus());

            flagUpdate = (ps.executeUpdate() == 1); //<--- INSERT
            ps.close(); //Cerrar el PreparedStatement
            connect.commit();//Aprueb alos cambios de la BD
            connect.close(); //Cerrar la conexión
        }catch (SQLException e){
            System.out.println("SQLException Error en UpdtadePerson"+e.getMessage());
            connect.rollback();//Si ocurre un error regresa la base de datos a como estaba en su estado anterior
        }
        //Regresar Resultado (Se inserto o no)
        return flagUpdate;
    }

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
    //Recuperar el listado desde la BD
    public ArrayList<HashMap> selectAllPersons() throws SQLException {
        ArrayList<HashMap> list = new ArrayList<>();
        try {
            Connection connect = ConexionMysql.getConnection();
            PreparedStatement ps = connect.prepareStatement("Select * FROM person");
            ResultSet rs = ps.executeQuery(); //executeQuery me permite
            while (rs.next()) {
                //LikedHashMap es un tipo de HashMap
                HashMap hashMapPerson = new LinkedHashMap();
                hashMapPerson.put("id", rs.getInt(1));
                hashMapPerson.put("id", rs.getInt(1));
                hashMapPerson.put("name", rs.getString(2));
                hashMapPerson.put("lastname", rs.getString(3));
                hashMapPerson.put("age", rs.getDouble(5));
                hashMapPerson.put("status", rs.getBoolean(6));

                list.add(hashMapPerson);
            }
            rs.close();
            ps.close();
            connect.close();
        } catch (SQLException e) {
            System.err.println("SQLException en selectALLperson" + e.getMessage());
        }
        return list;
        }
        public HashMap selectPersonById(int id) throws SQLException {
            HashMap hashMapPerson = new LinkedHashMap();
            try {
                Connection connect = ConexionMysql.getConnection();
                PreparedStatement ps = connect.prepareStatement("Select * FROM person");
                ResultSet rs = ps.executeQuery(); //executeQuery me permite
                while (rs.next()) {
                    hashMapPerson.put("id", rs.getInt(1));
                    hashMapPerson.put("id", rs.getInt(1));
                    hashMapPerson.put("name", rs.getString(2));
                    hashMapPerson.put("lastname", rs.getString(3));
                    hashMapPerson.put("age", rs.getDouble(5));
                    hashMapPerson.put("status", rs.getBoolean(6));

                }
                rs.close();
                ps.close();
                connect.close();
            } catch (SQLException e) {
                System.err.println("SQLException en selectPersonById" + e.getMessage());
            }
            return hashMapPerson;
        }
            public boolean deletePerson(int id) throws SQLException{
                boolean flagDelete = false;
                Connection  connect = null;
                try{
                    connect = ConexionMysql.getConnection();
                    connect.setAutoCommit(false);
                    PreparedStatement ps = connect.prepareStatement("DELETE FROM person WHERE id = ?");
                    ps.setInt(1, id);
                    flagDelete = (ps.executeUpdate() == 1);
                    connect.commit();
                    ps.close();
                    connect.close();
                }catch (SQLException e){
                    System.out.println("SQLException deletPerson"+e.getMessage());
                    connect.rollback();
                }
                return flagDelete;
            }


    //HashMap "Son arreglos que tienen un key+value"
    //'name' = 'Fulanito'

    //Array "Son arreglos que tienen posiciones"
    //[0] = 'Valor'

}
