/*
 * MSConexion.java
 * si alguien recibe del cliente que se envio el mensaje, se estaran conectando 
 * digamos que los clientes envian su mensaje, yo como servidor, lo que tengo que hacer
 * es tratar de establecer esa comunicacion que se esta realizando entre los clientes 
 */

package chat;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author 
 */
public class MSConexion extends Thread{ 
    //creamos el hilo que es el que estara escuchando  
    
    
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String nick;
    
    /** Creates a new instance of MSConexion */
    public MSConexion(Socket s) {
        try{
            this.s=s; //aqui es la parte del server 
            dis=new DataInputStream(s.getInputStream());//recibo ese mensaje //cliente  me manda ese mensaje
            dos=new DataOutputStream(s.getOutputStream());//yo server, se lo mando al cliente2
            //soy el intermedio de la comunicacion
            start(); //inicializar el hilo 
        }catch(Exception e){
        }
    }
    
    public String getNick(){
        return nick;
    }
    
    public void run(){
        //el hilo que esta escuchando
        //lo hara de manera "infinita" 
        //esa conexion que se esta realizando entre los clientes 
        while (true){
            try{
                int nCodigo=dis.readInt(); //la escucha de esa trama por medio de un entero
                String sTrama=dis.readUTF();//leo ese mensaje
                switch(nCodigo){
                    //cuando se envia un mensaje, lo que hace es que sabemos quien de los clientes ha enviado un mensaje 
                    //enviando una concatenacion de parametros 
                    case 1:
                        nick=sTrama;
                        
                        GestorConexion.getInstance().enviarTrama(nCodigo, sTrama);
                        break;
                    case 2:
                        sTrama="<" + nick + "> - " + sTrama;
                        GestorConexion.getInstance().enviarTrama(nCodigo, sTrama);
                        break;
                        
                        //para el servidor 
                    case 3:
                        //vamos a desconectar a la persona para que identifique la posicion de dicha persona 
                        GestorConexion.getInstance().desconecta(this);
                        break;
                }
                
            }catch(Exception e){
            }
            
        }
    }
    //envio ese mensaje al reto de clientes que se han conectado 
    public void enviarTrama(int nCodigo, String sTrama){
        try{
           dos.writeInt(nCodigo);
           dos.writeUTF(sTrama);
        }catch(Exception e){
        }
    }
}
