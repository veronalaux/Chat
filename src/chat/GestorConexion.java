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
    private ArrayList<MSConexion> conexiones = new ArrayList<MSConexion>();
    //y cuando alguien envia un mensaje, a traves del arrayList 
    //enviare el mensaje a todos los que se han conectado en la sesion
    public void enviarTrama(int nCodigo, String sTrama){
        for (MSConexion ms:conexiones){
            ms.enviarTrama(nCodigo, sTrama);
        }
    }
    
    public void conectaNuevo(MSConexion nuevo){
        //aqui el socket se conecta a uno nuevo
        for (MSConexion ms:conexiones){
            nuevo.enviarTrama(1, ms.getNick());
        }
        conexiones.add(nuevo);
    }
    
    public void desconecta(MSConexion eliminar){
        int nPos=-1;
        for (int n=0;n<conexiones.size();n++){
            if (conexiones.get(n)==eliminar){
                nPos=n;
            }
        }
        if (nPos!=-1){
            for (int n=0;n<conexiones.size();n++){
                if (n!=nPos){
                    conexiones.get(n).enviarTrama(3, ""+nPos);
                }
            }
            conexiones.remove(nPos);
        }
    }    
    
}
