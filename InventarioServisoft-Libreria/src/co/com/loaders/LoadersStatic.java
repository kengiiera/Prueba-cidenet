/*
 * Servisoft 2013 (c)
 * www.servisoft.com.co
 */
package co.com.loaders;

import co.com.servisoft.util.loaderproperties.CargadorPropiedades;
import java.sql.Connection;

/**
 * Clase LoadersStatic
 *
 * @author dquintero
 */
public class LoadersStatic {

    private LoadersStatic() {
    }

    public static String cargarPropiedadQueryStatic(String clave) {
        CargadorPropiedades cargadorPropiedades = new CargadorPropiedades("/resource/queryGenerales.properties");
        return cargadorPropiedades.cargarPropiedad(clave);
    }

    public static Connection cargarConexionStatic() {
        GestorConexion gestorConexion = new GestorConexion();
        return gestorConexion.buscarConexion();
    }
}
