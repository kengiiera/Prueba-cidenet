package co.com.dao;

import static co.com.loaders.GestorConexion.closeResource;
import static co.com.loaders.LoadersStatic.cargarConexionStatic;
import static co.com.loaders.LoadersStatic.cargarPropiedadQueryStatic;
import co.com.objetos.beanUsuarios;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class UsuariosDAOSQLServer implements UsuariosDAO {

    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;
    public static final Logger logger = Logger.getLogger(UsuariosDAOSQLServer.class);

    @Override
    public beanUsuarios obtenerUsuarioPorIdUsuario(String idUsuario) {
        beanUsuarios usuario = new beanUsuarios();
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("getObtenerUsuarioPorIdUsuario");
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, idUsuario);

            resultados = sentencia.executeQuery();
            if (resultados.next()) {
                usuario.setIdUsuario(resultados.getString("id_usuario"));
                usuario.setIdentificacion(resultados.getString("identificacion"));
                usuario.setNombreUsuario(resultados.getString("nombre_usuario"));
                usuario.setApellidoUsuario(resultados.getString("apellido_usuario"));
                usuario.setClave(resultados.getString("clave"));
                usuario.setCorreo(resultados.getString("correo"));
                usuario.setEstado(resultados.getString("estado"));
            }
        } catch (Exception ex) {

            logger.error("ERROR EN EL MÉTODO obtenerUsuarioPorIdUsuario = " + ex.getMessage());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return usuario;
    }

    @Override
    public Boolean validarUsuarioContrasena(String idUsuario, String contrasena) {
        Boolean validado = false;
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("getValidarUsuarioContrasena");
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, idUsuario);
            sentencia.setString(2, contrasena);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                validado = true;
            }
        } catch (Exception ex) {

            logger.error("ERROR EN EL MÉTODO validarUsuarioContrasena  = " + ex.getMessage());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return validado;
    }

    @Override
    public Boolean existeUsuario(String idUsuario) {
        Boolean validado = false;
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("existeUsuario");
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, idUsuario);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                validado = true;
            }
        } catch (Exception ex) {

            logger.error("ERROR EN EL MÉTODO existeUsuario  = " + ex.getMessage());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return validado;
    }

    @Override
    public int insert(beanUsuarios usuario) {
        int registrosAfectados = 0;
        try {
            String consulta = cargarPropiedadQueryStatic("insertUsuario");
            conexion = cargarConexionStatic();
            sentencia = conexion.prepareStatement(consulta);

            sentencia.setString(1, usuario.getIdUsuario());
            sentencia.setString(2, usuario.getIdentificacion());
            sentencia.setString(3, usuario.getNombreUsuario());
            sentencia.setString(4, usuario.getApellidoUsuario());
            sentencia.setString(5, usuario.getClave());
            sentencia.setString(6, usuario.getCorreo());
            sentencia.setString(7, usuario.getEstado());

            registrosAfectados = sentencia.executeUpdate();
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al guardar en tbl_usuarios. Método insert. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return registrosAfectados;
    }

    @Override
    public int update(beanUsuarios usuario) {
        int registrosAfectados = 0;
        try {
            String consulta = cargarPropiedadQueryStatic("updateUsuario");
            conexion = cargarConexionStatic();
            sentencia = conexion.prepareStatement(consulta);

            sentencia.setString(1, usuario.getIdentificacion());
            sentencia.setString(2, usuario.getNombreUsuario());
            sentencia.setString(3, usuario.getApellidoUsuario());
            sentencia.setString(4, usuario.getClave());
            sentencia.setString(5, usuario.getCorreo());
            sentencia.setString(6, usuario.getIdUsuario());
            sentencia.setString(7, usuario.getEstado());

            registrosAfectados = sentencia.executeUpdate();
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al actualizar en tbl_usuarios. Método update. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return registrosAfectados;
    }

    @Override
    public List<beanUsuarios> getAllUsuarios() {
        List<beanUsuarios> listaUsuarios = new ArrayList<>();
        try {
            conexion = cargarConexionStatic();
            String consulta = cargarPropiedadQueryStatic("getAllUsuarios");
            sentencia = conexion.prepareStatement(consulta);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                beanUsuarios usuario = new beanUsuarios();
                usuario.setIdUsuario(resultados.getString("id_usuario"));
                usuario.setIdentificacion(resultados.getString("identificacion"));
                usuario.setNombreUsuario(resultados.getString("nombre_usuario"));
                usuario.setApellidoUsuario(resultados.getString("apellido_usuario"));
                usuario.setClave(resultados.getString("clave"));
                usuario.setCorreo(resultados.getString("correo"));
                usuario.setEstado(resultados.getString("estado"));

                listaUsuarios.add(usuario);
            }
        } catch (Exception exp) {

            logger.error("Ha ocurrido un error al consultar en tbl_usuarios. Método getAllUsuarios. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return listaUsuarios;
    }

    @Override
    public int updateContrasenaUsuario(String usuario, String clave, Date fechaExpiracion) {
        int registrosAfectados = 0;
        try {
            String consulta = cargarPropiedadQueryStatic("updateContrasenaUsuario");
            conexion = cargarConexionStatic();
            sentencia = conexion.prepareStatement(consulta);

            sentencia.setString(1, clave);
            sentencia.setDate(2, fechaExpiracion);
            sentencia.setString(3, usuario);
            registrosAfectados = sentencia.executeUpdate();
        } catch (Exception exp) {
            logger.error("Ha ocurrido un error al actualizar en tbl_usuarios. Metodo updateContrasenaUsuario. " + exp.getMessage() + ". " + exp.getCause());
        } finally {
            closeResource(conexion, sentencia, resultados);
        }
        return registrosAfectados;
    }
}
