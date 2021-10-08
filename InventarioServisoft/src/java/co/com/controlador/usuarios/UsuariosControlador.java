package co.com.controlador.usuarios;

import co.com.controlador.utilidades.Encriptacion;
import co.com.controlador.utilidades.Utilidades;

import co.com.dao.UsuariosDAO;
import co.com.dao.UsuariosDAOSQLServer;

import co.com.objetos.beanUsuarios;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;


@ManagedBean
@ViewScoped
public class UsuariosControlador {

    public static final Logger logger = Logger.getLogger(UsuariosControlador.class);
    private beanUsuarios usuario;
    private List<beanUsuarios> listaUsuarios;
    private List<beanUsuarios> listaFiltroUsuarios;
    private String filtroUsuarios;
    private boolean estaModificando;
    private String confirmacionContrasena;
    private String fechaExpiracion;
    String botonLimpiarUsuarios = "formularioUsuarios:botonLimpiar";
    String formularioUsuarios = "formularioUsuarios";

    public UsuariosControlador() {
        inicializarVariables();
        cargarValores();
    }

   

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getConfirmacionContrasena() {
        return confirmacionContrasena;
    }

    public void setConfirmacionContrasena(String confirmacionContrasena) {
        this.confirmacionContrasena = confirmacionContrasena;
    }

    public beanUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(beanUsuarios usuario) {
        this.usuario = usuario;
    }

    public List<beanUsuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<beanUsuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<beanUsuarios> getListaFiltroUsuarios() {
        return listaFiltroUsuarios;
    }

    public void setListaFiltroUsuarios(List<beanUsuarios> listaFiltroUsuarios) {
        this.listaFiltroUsuarios = listaFiltroUsuarios;
    }

    public String getFiltroUsuarios() {
        return filtroUsuarios;
    }

    public void setFiltroUsuarios(String filtroUsuarios) {
        this.filtroUsuarios = filtroUsuarios;
    }

    public boolean isEstaModificando() {
        return estaModificando;
    }

    public void setEstaModificando(boolean estaModificando) {
        this.estaModificando = estaModificando;
    }

    public void limpiarUsuario() {
        inicializarVariables();
        cargarValores();
    }

    public void guardarUsuario() {
        if (validarCamposUsuario()) {
            UsuariosDAO usuarioDAO = new UsuariosDAOSQLServer();
            if (usuarioDAO.existeUsuario(usuario.getIdUsuario().toUpperCase())) {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("alertaExisteUsuario"), botonLimpiarUsuarios);
                return;
            }
            usuario = llenarCamposUsuario();

            if (usuarioDAO.insert(usuario) > 0) {
                Utilidades.mostrarMensajesAceptado(Utilidades.obtenerPropiedadMessagesEs("guardadoExitoso"), botonLimpiarUsuarios);
                limpiarUsuario();
            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("errorInsertar"), botonLimpiarUsuarios);
            }
        }
    }

    public void modificarUsuario() {
        if (validarCamposUsuario()) {
            UsuariosDAO usuarioDAO = new UsuariosDAOSQLServer();
            usuario = llenarCamposUsuario();

            if (usuarioDAO.update(usuario) > 0) {
                Utilidades.mostrarMensajesAceptado(Utilidades.obtenerPropiedadMessagesEs("actualizadoExitoso"), botonLimpiarUsuarios);
                limpiarUsuario();
            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("errorActualizar"), botonLimpiarUsuarios);
            }
        }
    }

    public void cargarInformacionUsuario(beanUsuarios usuarioSeleccionado) {
        usuario = usuarioSeleccionado;
       
        estaModificando = true;
    }

    private beanUsuarios llenarCamposUsuario() {
        beanUsuarios usuarioReturn = new beanUsuarios();
        usuarioReturn.setIdUsuario(usuario.getIdUsuario().toUpperCase());
        usuarioReturn.setIdentificacion(usuario.getIdentificacion());
        usuarioReturn.setNombreUsuario(usuario.getNombreUsuario().trim().toUpperCase());
        usuarioReturn.setApellidoUsuario(usuario.getApellidoUsuario().trim().toUpperCase());
        usuarioReturn.setClave(usuario.getClave());

        usuarioReturn.setCorreo(usuario.getCorreo());
        usuarioReturn.setEstado(usuario.getEstado());

        return usuarioReturn;
    }

    private boolean validarCamposUsuario() {
        boolean validado = true;

        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("diligenciarUsuario"), formularioUsuarios);
            validado = false;
        }

        if (usuario.getIdUsuario() != null && usuario.getIdUsuario().contains(" ")) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("usuarioNoEspacios"), formularioUsuarios);
            validado = false;
        }

        if (usuario.getIdentificacion() == null || usuario.getIdentificacion().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarIdentificacion"), formularioUsuarios);
            validado = false;
        }

        if (!Utilidades.esIntNumerico(usuario.getIdentificacion())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("identificacionSoloNumeros"), formularioUsuarios);
            validado = false;
        }

        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarNombreUsuario"), formularioUsuarios);
            validado = false;
        }

        if (usuario.getApellidoUsuario() == null || usuario.getApellidoUsuario().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarApellido"), formularioUsuarios);
            validado = false;
        }

        if (usuario.getClave() == null || usuario.getClave().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarContrasena"), formularioUsuarios);
            validado = false;
        }

        if (confirmacionContrasena == null || confirmacionContrasena.trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarConfirmacionContrasena"), formularioUsuarios);
            validado = false;
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarCorreo"), formularioUsuarios);
            validado = false;
        }

        if (confirmacionContrasena != null && usuario.getClave() != null && !confirmacionContrasena.equals(usuario.getClave())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("contrasenasNoCoinciden"), formularioUsuarios);
            validado = false;
        }

        if (fechaExpiracion == null) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarFechaExpiracion"), formularioUsuarios);
            validado = false;
        }

     
        if (usuario.getEstado() == null) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarEstado"), formularioUsuarios);
        }

        return validado;
    }

    private void inicializarVariables() {
        usuario = new beanUsuarios();
        listaUsuarios = new ArrayList<>();
        listaFiltroUsuarios = new ArrayList<>();
        filtroUsuarios = "";
        estaModificando = false;
        fechaExpiracion = null;
    }

    private void cargarValores() {
        UsuariosDAO usuariosDAO = new UsuariosDAOSQLServer();
       
        listaUsuarios = usuariosDAO.getAllUsuarios();
    }

}
