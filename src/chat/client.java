/*
 * client.java
 *
 * hilo para el cliente 
 */

package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */

public class client extends Thread{ //crear un hilo 
    
    //enviar parametros
    private int port;
    private String url;
    private Socket s;
    private boolean bConectado;
    VentanaCliente ventana; //viene del Jframe
    private String nick;
    
    /** constructor  */
    public client(int port, String url, String nick, VentanaCliente ventana) {
        //asignamos las variables a los atributos de las clases 
        this.port=port;
        this.url=url;
        this.ventana=ventana;
        this.nick=nick;
    }
    
    //tarea que se ejecuta en concurrencia 
    public void run(){
        //aqui vamos a abrir el pueto y notificar si ha funcionado o no
        try{
            s=new Socket(url, port);
            //el Input recibe la informacion del servidor
            DataInputStream dis=new DataInputStream(s.getInputStream());
            enviarTrama(1, nick);
            bConectado=true;
            while(bConectado){ //vamos a ir leyendo strings
                int nCodigo =dis.readInt();
                String sTrama=dis.readUTF(); //a traves del hilo se comunicara con nosotros y nos envaiara el mensaje 

                switch(nCodigo){
                    case 1:
                        ventana.nuevaPersona(sTrama);
                        break;
                    case 2:
                        ventana.mensajeRecibido(sTrama);
                        break;
                    case 3:
                        try{
                            int nPos = Integer.parseInt(sTrama);
                            ventana.borrarPersona(nPos);
                        }catch(Exception e2){
                        }
                        break;
                }  
            }
            //JOptionPane.showMessageDialog(ventana, "Se ha podido conectar");
        }catch(Exception e){
            //notificacion del puerto si ha funcionado o no
            JOptionPane.showMessageDialog(ventana, "No se pudo establecer la conexión");
        }
    }
    
    public void enviarMensaje(String sMensaje){
        //aqui se envia como tal el mensaje
        enviarTrama(2, sMensaje);
    }
    
    public void enviarTrama(int nCodigo, String sTrama){
        try{
            //envio de bytes y facilita la comunicacion por lo que lo convierte
            //a vector de bytes y los comunica entre ellos
            DataOutputStream dos=new DataOutputStream(s.getOutputStream());
            dos.writeInt(nCodigo);
            dos.writeUTF(sTrama);
        }catch(Exception e){
            JOptionPane.showMessageDialog(ventana, "No se pudo enviar el mensaje");
        }
        
    }

}
