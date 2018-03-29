/*
 * GestorConexion.java
 *este archivo permitira varias conexiones 
 */

package chat;

import java.util.ArrayList;

/**
 *
 * @author 
 */
public class GestorConexion {
    //este es lo que permite que se haga en si 
    //la funcion como tal del chat, ya que permite la conexion de varios clientes en
    //en nuestro servidor y entre ellos interactuen 
    private static GestorConexion singleton=new GestorConexion();
    public  static GestorConexion getInstance(){
        return singleton;
    }
    //cuando alguien se conecta, lo inserto en el arrayList
    private ArrayList<ControlConexion> conexiones = new ArrayList<ControlConexion>();
    //y cuando alguien envia un mensaje, a traves del arrayList 
    //enviare el mensaje a todos los que se han conectado en la sesion
    public void enviarTrama(int nCodigo, String sTrama){
        for (ControlConexion ms:conexiones){
            //estamos indicando de que se esta enviando una Trama 
            ms.enviarTrama(nCodigo, sTrama);
            //de manera que cuando envie una trama, lo que tengo que hacer es mandaer 
            //la trama al resto de las conexiones que hay en el servidor 
        }
    }
    
    public void conectaNuevo(ControlConexion nuevo){
        //aqui el socket se conecta a uno nuevo
        
        //recorre todos los elementos que hay de la lista y al nuevo notificamos que alguien mas se conecto en el chat 
        for (ControlConexion ms:conexiones){
            nuevo.enviarTrama(1, ms.getNick()); //el getNick lee el nuevo usuario
            //enviamos la trama con el Nickname de la persona que se ha conectadoo
        }
        conexiones.add(nuevo);
    }
    
    public void desconecta(ControlConexion eliminar){
        //aqui se envia el elemento que se quiere deconectar
        int nPos=-1;
        
        //aqui vamos a recorrer todas las conexiones y comunicaresmos que 
        for (int n=0;n<conexiones.size();n++){
            //el numero "X" se va a desconectar
            //nos referimos como numero a la persona y es posible que el nick se pueda repetir 
            if (conexiones.get(n)==eliminar){
                nPos=n;
            }
        }
        //aqui se envia la posicion de quie se conecto para que se pueda deconectar
        //siguiendo la linea de que posiblemente tengamos a dos personas con el mismo nickname
        if (nPos!=-1){
            for (int n=0;n<conexiones.size();n++){
                if (n!=nPos){
                    conexiones.get(n).enviarTrama(3, ""+nPos);//enviamos la posiscion de quien se tiene que desconectar
                }
            }
            conexiones.remove(nPos);
        }
    }    
    
}
