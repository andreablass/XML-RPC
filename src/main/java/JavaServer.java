import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;
import java.util.logging.Handler;

public class JavaServer{
    public static void main(String[] args) throws XmlRpcException, IOException {
        System.out.println("Iniciar servidor para XMl");
        /*Mappeo/Registro de los "manupuladores de datos"(Handler)*/
        PropertyHandlerMapping mapping = new PropertyHandlerMapping();
        mapping.addHandler("HandlerMath",Handler.class);
        mapping.addHandler("HndlerPerson",HandlerPerson.class);
        //mapping.addHandler("HandlerStudent",HandlerStudent.class);
        //DAOStudent.insert ----HndlerMath.suma;

        //Objeto que creara el servidor .. se indica en el constructor el puerto
        WebServer server = new WebServer(1200);
        //Servidor va a ocupar el mapping que nosostros creamos anteriormente
        server.getXmlRpcServer().setHandlerMapping(mapping);
        //Iniciar el servidor
        server.start();//Agregar IOException
        System.out.println("Esperando peticiones...");

    }
}