package co.com.dao;

import static co.com.loaders.LoadersStatic.cargarConexionStatic;
import static co.com.loaders.LoadersStatic.cargarPropiedadQueryStatic;
import co.com.objetos.beanPersonas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import static co.com.loaders.GestorConexion.closeResource;

/**
 *
 * @author Kengie
 */
public class PersonaDAOSQLServer implements PersonaDAO {

    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;
    public static final Logger logger = Logger.getLogger(PersonaDAOSQLServer.class);

    @Override
    public beanPersonas obtenerPersonaPorId(String identificacion) {
        beanPersonas persona = new beanPersonas();
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("ObtenerPersonaPorId");
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, identificacion);

            resultados = sentencia.executeQuery();
            if (resultados.next()) {

                persona.setIdentificacion(resultados.getString("identificacion"));
                persona.setPrimernombre(resultados.getString("primernombre"));
                persona.setSegundonombre(resultados.getString("segundonombre"));
                persona.setTipoIdentificacion(resultados.getString("tipo_identificacion"));
                persona.setPrimerapellido(resultados.getString("primerapellido"));
                persona.setSegundoapellido(resultados.getString("segundoapellido"));
                persona.setPaisempleo(resultados.getString("paisempleo"));
                persona.setCorreo(resultados.getString("correo"));

            }
        } catch (Exception ex) {

            logger.error("ERROR EN EL MÉTODO ObtenerPersonaPorId = " + ex.getMessage());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return persona;
    }

    @Override
    public Boolean existePersona(String identificacion) {
        Boolean validado = false;
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("existePersona");
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, identificacion);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                validado = true;
            }
        } catch (Exception ex) {
            logger.error("ERROR EN EL MÉTODO existePersona  = " + ex.getMessage());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return validado;
    }

    
    @Override
    public int insert(beanPersonas persona) {
        int registrosAfectados = 0;
        try {
            String consulta = cargarPropiedadQueryStatic("insertPersona");
            conexion = cargarConexionStatic();
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, persona.getIdentificacion());
            sentencia.setString(2, persona.getPrimernombre());
            sentencia.setString(3, persona.getSegundonombre());
            sentencia.setString(4, persona.getTipoIdentificacion());
            sentencia.setString(5, persona.getPrimerapellido());
            sentencia.setString(6, persona.getSegundonombre());
            sentencia.setString(7, persona.getPaisempleo());
            sentencia.setString(8, persona.getCorreo());

            registrosAfectados = sentencia.executeUpdate();
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al guardar en tbl_persona. Método insert. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return registrosAfectados;
    }

   @Override
    public int update(beanPersonas persona) {
        int registrosAfectados = 0;
        try {
            String consulta = cargarPropiedadQueryStatic("updatePersona");
            conexion = cargarConexionStatic();
            sentencia = conexion.prepareStatement(consulta);

            sentencia.setString(1, persona.getPrimernombre());
            sentencia.setString(2, persona.getSegundonombre());

            sentencia.setString(3, persona.getPrimerapellido());
            sentencia.setString(4, persona.getSegundoapellido());
            sentencia.setString(5, persona.getPaisempleo());
            sentencia.setString(6, persona.getCorreo());
            sentencia.setString(7, persona.getTipoIdentificacion());
             sentencia.setString(8, persona.getIdentificacion());
            registrosAfectados = sentencia.executeUpdate();
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al guardar en tbl_persona. Método update. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return registrosAfectados;
    }

    @Override
    public List<beanPersonas> getAllPersonas() {
        List<beanPersonas> listaPersona = new ArrayList<>();
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("getAllPersonas");
            sentencia = conexion.prepareStatement(consulta);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                beanPersonas persona = new beanPersonas();

                persona.setIdentificacion(resultados.getString("identificacion"));
                persona.setPrimernombre(resultados.getString("primernombre"));
                persona.setSegundonombre(resultados.getString("segundonombre"));
                persona.setTipoIdentificacion(resultados.getString("tipo_identificacion"));
                persona.setPrimerapellido(resultados.getString("primerapellido"));
                persona.setSegundoapellido(resultados.getString("segundoapellido"));
                persona.setPaisempleo(resultados.getString("paisempleo"));
                persona.setCorreo(resultados.getString("correo"));
                listaPersona.add(persona);
            }
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al consultar en tbl_persona. Método getAllPersona. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return listaPersona;
    }

}
