package co.com.dao;

import co.com.objetos.beanUsuarios;
import java.sql.Date;
import java.util.List;


public interface UsuariosDAO {

    public beanUsuarios obtenerUsuarioPorIdUsuario(String idUsuario);

    public Boolean validarUsuarioContrasena(String idUsuario, String contrasena);

    public Boolean existeUsuario(String idUsuario);

    public int insert(beanUsuarios usuario);

    public int update(beanUsuarios usuario);

    public int updateContrasenaUsuario(String usuario, String clave, Date fechaExpiracion);

    public List<beanUsuarios> getAllUsuarios();
}
