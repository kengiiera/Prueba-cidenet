package co.com.controlador.persona;

import co.com.controlador.utilidades.Utilidades;
import co.com.dao.PersonaDAOSQLServer;
import co.com.objetos.beanPersonas;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.dao.PersonaDAO;

import java.util.stream.Collectors;
import org.apache.log4j.Logger;

/**
 *
 * @author Kengie
 */
@ManagedBean
@ViewScoped
public class PersonaControlador {

    public static final Logger logger = Logger.getLogger(PersonaControlador.class);
    private beanPersonas persona;
    private List<beanPersonas> listaPersona;
    private List<beanPersonas> listaFiltrosPersona;
    private String filtroPersona;
    private boolean estaModificando;
    String botonLimpiarPersona = "formularioPersona:botonLimpiar";
    String formularioPersona = "formularioPersona";

    public PersonaControlador() {
        inicializarVariables();
        cargarValores();
    }

    public beanPersonas getPersona() {
        return persona;
    }

    public void setPersona(beanPersonas persona) {
        this.persona = persona;
    }

    public List<beanPersonas> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<beanPersonas> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public List<beanPersonas> getListaFiltrosPersona() {
        return listaFiltrosPersona;
    }

    public void setListaFiltrosPersona(List<beanPersonas> listaFiltrosPersona) {
        this.listaFiltrosPersona = listaFiltrosPersona;
    }

    public String getFiltroPersona() {
        return filtroPersona;
    }

    public void setFiltroPersona(String filtroPersona) {
        this.filtroPersona = filtroPersona;
    }

    public boolean isEstaModificando() {
        return estaModificando;
    }

    public void setEstaModificando(boolean estaModificando) {
        this.estaModificando = estaModificando;
    }

    public void limpiarPersona() {
        inicializarVariables();
        cargarValores();
    }

    public void guardarPersona() {
        if (validarCamposPersona()) {
            PersonaDAO personaDAO = new PersonaDAOSQLServer();
            if (personaDAO.existePersona(persona.getIdentificacion())) {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("alertaExistePersona"), botonLimpiarPersona);
                return;
            }
            persona = llenarCamposPersona();
            if (personaDAO.insert(persona) > 0) {
                Utilidades.mostrarMensajesAceptado(Utilidades.obtenerPropiedadMessagesEs("guardadoExitoso"), botonLimpiarPersona);
                limpiarPersona();
            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("errorInsertar"), botonLimpiarPersona);

            }
        }
    }

    public void modificarPersona() {
        if (validarCamposPersona()) {
            PersonaDAO personaDAO = new PersonaDAOSQLServer();
            persona = llenarCamposPersona();

            if (personaDAO.update(persona) > 0) {
                Utilidades.mostrarMensajesAceptado(Utilidades.obtenerPropiedadMessagesEs("actualizadoExitoso"), botonLimpiarPersona);
                limpiarPersona();
            } else {
                Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("errorActualizar"), botonLimpiarPersona);
            }
        }
    }

    public void cargarInformacionPersona(beanPersonas personas) {
        persona = personas;
        estaModificando = true;
    }

    private beanPersonas llenarCamposPersona() {
        beanPersonas personaReturn = new beanPersonas();
        personaReturn.setIdentificacion(persona.getIdentificacion());
        personaReturn.setPrimernombre(persona.getPrimernombre().toUpperCase());
        personaReturn.setSegundonombre(persona.getSegundonombre().toUpperCase());
        personaReturn.setSegundoapellido(persona.getSegundoapellido().toUpperCase());
        personaReturn.setTipoIdentificacion(persona.getTipoIdentificacion().toUpperCase());
        personaReturn.setPrimerapellido(persona.getPrimerapellido().toUpperCase());
        personaReturn.setPaisempleo(persona.getPaisempleo());
        personaReturn.setCorreo(Utilidades.generarCorreo(persona.getPrimernombre().toUpperCase(), persona.getPrimerapellido().toUpperCase(),persona.getPaisempleo(), 0) );

        return personaReturn;
    }

    private boolean validarCamposPersona() {
        boolean validado = true;

        if (persona.getIdentificacion() == null || persona.getIdentificacion().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarIdentificacion"), formularioPersona);
            validado = false;
        }

//        if (!Utilidades.esIntNumerico(persona.getIdentificacion())) {
//            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("identificacionSoloNumeros"), formularioPersona);
//            validado = false;
//        }

        if ("S".equals(persona.getTipoIdentificacion())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("seleccionarTipoIdentificacion"), formularioPersona);
            validado = false;
        }
        if (persona.getPrimernombre() == null || persona.getPrimernombre().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarNombrePersona"), formularioPersona);
            validado = false;
        }

        if (!Utilidades.esSoloLetras(persona.getPrimernombre())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("soloadmitenLetrasnotildadas"), formularioPersona);
            validado = false;
        }
        if (persona.getPrimerapellido() == null || persona.getPrimerapellido().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarApellido"), formularioPersona);
            validado = false;
        }
        if (!Utilidades.esSoloLetras(persona.getSegundoapellido())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("soloadmitenLetrasnotildadas"), formularioPersona);
            validado = false;
        }
        
         if (!Utilidades.esSoloLetras(persona.getPrimerapellido())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("soloadmitenLetrasnotildadas"), formularioPersona);
            validado = false;
        }
        if (!Utilidades.esSoloLetras(persona.getSegundonombre())) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("soloadmitenLetrasnotildadas"), formularioPersona);
            validado = false;
        }
        if (persona.getSegundonombre() == null || persona.getSegundonombre().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarApellido"), formularioPersona);
            validado = false;
        }
     
   if (persona.getPaisempleo().equals("S") || persona.getPaisempleo().trim().isEmpty()) {
            Utilidades.mostrarMensajesError(Utilidades.obtenerPropiedadMessagesEs("debeDiligenciarpaisempleo"), formularioPersona);
            validado = false;
        }
   
   
        return validado;
    }

    private void inicializarVariables() {
        persona = new beanPersonas();
        listaPersona = new ArrayList<>();
        listaFiltrosPersona = new ArrayList<>();
        filtroPersona = "";
        estaModificando = false;
    }

    private void cargarValores() {
        PersonaDAO personaDAO = new PersonaDAOSQLServer();
        listaPersona = personaDAO.getAllPersonas();
    }

    public void filtrarTablaPersona() {
        PersonaDAO personaDAO = new PersonaDAOSQLServer();
        listaFiltrosPersona = personaDAO.getAllPersonas();
        listaPersona = listaFiltrosPersona.stream().filter(x -> x.getIdentificacion().contains(filtroPersona.toUpperCase()) || x.getTipoIdentificacion().contains(filtroPersona.toUpperCase())
             || x.getPrimernombre().contains(filtroPersona.toUpperCase()) || x.getPaisempleo().contains(filtroPersona.toUpperCase())  || x.getSegundoapellido().contains(filtroPersona.toUpperCase()) || x.getPrimerapellido().contains(filtroPersona.toUpperCase()) || x.getCorreo().contains(filtroPersona)).collect(Collectors.toList());
    }

}
