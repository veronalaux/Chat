/*
 * server
 *
 * aqui vamos a esuchar las conexiones 
 */

package chat;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class server extends Thread{ //hilo para abrir la aplicacion
    //es un proceso que se ejecuta al tiempo que se ejecuta el hilo principal
    //sin interferir el desarrollo de la aplicacion sin incurrir en concuerrencia 
    
    private int port; //el puerto que se configura en el servidor 
    private JFrame ventana; //donde se va a mostrar los mensajes 
    
    /** Creates a new instance of MServidor */
    public server(JFrame ventana, int port) {
        this.port = port;
        this.ventana = ventana;
    }
    
    public void run(){
        ServerSocket ss=null;
        try{
            ss=new ServerSocket(port);
            while (true)
            //
            {
                Socket s=ss.accept();
                //cuando escuchamos uno, lo insertamos dentro del arrayList 
                //y lo volvemos a escuchar, es por eso que lo metimos en un bucle
                //y asi otro cliente se pueda conectar a nuestro chat 
                //y ese nuevo se pueda insertar en la lista 
                GestorConexion.getInstance().conectaNuevo(new MSConexion(s));
            }
            //JOptionPane.showMessageDialog(ventana,"Se han conectado");
        }catch(Exception e){
            JOptionPane.showMessageDialog(ventana,"Error al abrir el puerto. Posiblemente ya est� en uso.");
        }
        try{
            ss.close();
        }catch(Exception e){
        }
    }
    
}
