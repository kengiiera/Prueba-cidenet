package co.com.controlador.autenticacionSesion;


import co.com.controlador.utilidades.Encriptacion;
import co.com.controlador.utilidades.Utilidades;

import co.com.dao.UsuariosDAO;
import co.com.dao.UsuariosDAOSQLServer;

import co.com.objetos.beanUsuarios;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import java.util.Date;

@ManagedBean()
@SessionScoped
public class AutenticacionSesionControlador {

    public static final Logger logger = Logger.getLogger(AutenticacionSesionControlador.class);
    private Boolean[] mapaPermisosPerfil;
    private beanUsuarios usuarioLogueado;
    private String nombreUsuario;
    private String c0ntr4sen4;
    private String cntrasnaNueva;
    private String confirmCntrasnaNueva;
    String mensajeIngreso = "formularioIngreso:mensajeIngreso";
    String mensajeCambioClave = "popupSubViewCambioClave:frmCambioClave:botonCambiarClave";
    private boolean mostrarPopUpCambioClave;

    public AutenticacionSesionControlador() {
        usuarioLogueado = new beanUsuarios();
        mostrarPopUpCambioClave = false;
        nombreUsuario = "";
        c0ntr4sen4 = "";
    }

    public String getCntrasnaNueva() {
        return cntrasnaNueva;
    }

    public void setCntrasnaNueva(String cntrasnaNueva) {
        this.cntrasnaNueva = cntrasnaNueva;
    }

    public String getConfirmCntrasnaNueva() {
        return confirmCntrasnaNueva;
    }

    public void setConfirmCntrasnaNueva(String confirmCntrasnaNueva) {
        this.confirmCntrasnaNueva = confirmCntrasnaNueva;
    }

    public boolean isMostrarPopUpCambioClave() {
        return mostrarPopUpCambioClave;
    }

    public void setMostrarPopUpCambioClave(boolean mostrarPopUpCambioClave) {
        this.mostrarPopUpCambioClave = mostrarPopUpCambioClave;
    }

    public Boolean[] getMapaPermisosPerfil() {
        return mapaPermisosPerfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return c0ntr4sen4;
    }

    public void setContrasena(String contrasena) {
        this.c0ntr4sen4 = contrasena;
    }

    public beanUsuarios getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(beanUsuarios usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String ingreso() {
        String proximaPagina = "index.xhtml";
        if (nombreUsuario == null || nombreUsuario.equals("")
                || c0ntr4sen4 == null || c0ntr4sen4.equals("")) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarUsuarioYContrasena"), mensajeIngreso);
            return proximaPagina;
        }
        UsuariosDAO usuariosDAO = new UsuariosDAOSQLServer();
        //c0ntr4sen4 = Encriptacion.getEncryptSha1(c0ntr4sen4);
        Boolean validarIngreso = usuariosDAO.validarUsuarioContrasena(
                nombreUsuario.toUpperCase(), c0ntr4sen4);

        if (validarIngreso) {
            usuarioLogueado = usuariosDAO.obtenerUsuarioPorIdUsuario(nombreUsuario.toUpperCase());
            if ("A".equals(usuarioLogueado.getEstado())) {

                usuarioLogueado = usuariosDAO.obtenerUsuarioPorIdUsuario(nombreUsuario.toUpperCase());
                proximaPagina = "showInicio";
                logger.info(Utilidades.obtenerPropiedadMessagesEs("ingresoValidado") + nombreUsuario.toUpperCase());

            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("usuarioInactivo"), mensajeIngreso);
            }
        } else {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("usuarioOContrasenaNosonCorrectos"), mensajeIngreso);
        }
        return proximaPagina;
    }

    public void cambiarClave() {
        if (validarCamposCambioClave()) {
            UsuariosDAO usuarioDAO = new UsuariosDAOSQLServer();
            
            //cntrasnaNueva = Encriptacion.getEncryptSha1(cntrasnaNueva);

            Date fechaProximaExp = Utilidades.sumarAnioAFecha(new Date(), 1);
            if (usuarioDAO.updateContrasenaUsuario(nombreUsuario, cntrasnaNueva, Utilidades.convertirFechaHoraDateaSql(fechaProximaExp)) > 0) {
                Utilidades.mostrarMensajesAceptado(Utilidades.obtenerPropiedadMessagesEs("actualizadoExitosoClave"), "formularioIngreso");
                mostrarPopUpCambioClave = false;
            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("errorActualizar"), "formularioIngreso");
            }
        }
    }

    public void cerrarSesion() {
        mostrarPopUpCambioClave = false;
        Utilidades.cerrarSession();
    }

    private boolean validarCamposCambioClave() {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("diligenciarUsuario"), mensajeCambioClave);
            return false;
        }

        if (c0ntr4sen4 == null || c0ntr4sen4.trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarContrasenaActual"), mensajeCambioClave);
            return false;
        }

        if (cntrasnaNueva == null || cntrasnaNueva.trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarContrasenaNueva"), mensajeCambioClave);
            return false;
        }

        if (confirmCntrasnaNueva == null || confirmCntrasnaNueva.trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarConfirmacionContrasena"), mensajeCambioClave);
            return false;
        }

        if (confirmCntrasnaNueva != null && cntrasnaNueva != null
                && !confirmCntrasnaNueva.trim().isEmpty() && !cntrasnaNueva.trim().isEmpty()
                && !confirmCntrasnaNueva.equals(cntrasnaNueva)) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("contrasenasNoCoinciden"), mensajeCambioClave);
            return false;
        }

        UsuariosDAO usuarioDAO = new UsuariosDAOSQLServer();
        //c0ntr4sen4 = Encriptacion.getEncryptSha1(c0ntr4sen4);
        String claveDB = usuarioDAO.obtenerUsuarioPorIdUsuario(nombreUsuario.toUpperCase()).getClave();

        if (!claveDB.equals(c0ntr4sen4)) {
            Utilidades.mostrarMensajesError("La contraseña actual no es correcta", "formularioIngreso");
            return false;
        }
        return true;
    }

}
