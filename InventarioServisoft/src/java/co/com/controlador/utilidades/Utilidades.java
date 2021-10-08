package co.com.controlador.utilidades;

import co.com.controlador.autenticacionSesion.AutenticacionSesionControlador;
import co.com.servisoft.util.loaderproperties.LectorPropiedades;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;


public class Utilidades {

    private Utilidades() {
    }

    public static final Logger logger = Logger.getLogger(Utilidades.class);

    public static Object getManagedBean(String beanName) {
        return getValueExpression(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance().getELContext());
    }

    public static ValueExpression getValueExpression(String el) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), el, java.lang.Object.class);
    }

    public static String getJsfEl(String value) {
        return (new StringBuilder()).append("#{").append(value).append("}").toString();
    }

    public static boolean isActiveSession() {
        String idUsuario = getIdUsuarioLoqueado();
        return !(idUsuario == null || idUsuario.trim().isEmpty());
    }

    public static void cerrarSession() {
        try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ((HttpSession) ctx.getSession(false)).invalidate();

            ctx.redirect(ctxPath + "/faces/index.xhtml");
        } catch (Exception ex) {
            logger.fatal("context", ex);
        }
    }

    public static void mostrarMensajesError(String mensaje, String idCampo) {
        FacesMessage mensajeAMostrar = new FacesMessage();
        mensajeAMostrar.setSummary(mensaje);
        mensajeAMostrar.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(idCampo, mensajeAMostrar);
    }

    public static void mostrarMensajesAceptado(String mensaje, String idCampo) {
        FacesMessage mensajeAMostrar = new FacesMessage();
        mensajeAMostrar.setSummary(mensaje);
        mensajeAMostrar.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(idCampo, mensajeAMostrar);
    }

    public static String obtenerPropiedadValidacionesSistema(String nombrePropiedad) {
        String valor = LectorPropiedades.obtenerValorproiedad("co.com.i18n.ValidacionesSistema", nombrePropiedad);
        if (valor == null || valor.isEmpty()) {
            logger.error("En el archivo ValidacionesSistema.properties no se ha encontrado ningún valor para la propiedad " + nombrePropiedad);
        }
        return valor;
    }

    public static String obtenerPropiedadMessagesEs(String nombrePropiedad) {
        String valor = LectorPropiedades.obtenerValorproiedad("co.com.i18n.Messages_es", nombrePropiedad);
        if (valor == null || valor.isEmpty()) {
            logger.error("En el archivo Messages_es.properties no se ha encontrado ningún valor para la propiedad " + nombrePropiedad);
        }
        return valor;
    }

    public static java.sql.Date convertirFechaHoraDateaSql(Date fechaOriginal) {
        java.sql.Date sqlDate = null;

        if (fechaOriginal != null) {
            sqlDate = new java.sql.Date(fechaOriginal.getTime());
        }
        return sqlDate;
    }

    public static java.sql.Date convertirFechaStringaDateSql(String fecha) {
        return convertirFechaHoraDateaSql(convertirFechaStringaDate(fecha));
    }

    public static Date convertirFechaStringaDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fec = null;
        try {
            fec = formatoDelTexto.parse(fecha);
        } catch (Exception ex) {
            logger.error("context", ex);
        }
        return fec;
    }

    public static String convertirFechaSplitString(String fecha, String separador) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return "";
        }
        String[] fechaRecort = fecha.split(separador);
        if (fechaRecort.length != 3) {
            return "";
        }
        switch (separador) {
            case "/":
                return fechaRecort[1] + "/" + fechaRecort[0] + "/" + fechaRecort[2];
            case "-":
                return fechaRecort[2] + "/" + fechaRecort[1] + "/" + fechaRecort[0];
            default:
                return "";
        }
    }

    public static Timestamp getFechaActualTimeStamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Boolean validarFechaMenor(Date fecha1, Date fecha2) {
        Boolean validado = true;
        try {
            validado = fecha1.before(fecha2);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return validado;
    }

    public static String getIdUsuarioLoqueado() {
        AutenticacionSesionControlador autenticacion = (AutenticacionSesionControlador) getManagedBean("autenticacionSesionControlador");
        return autenticacion.getUsuarioLogueado().getIdUsuario();
    }

    public static boolean tieneCadenaEspacios(String texto) {
        if (texto == null) {
            return true;
        } else {
            return !(texto.replaceAll(" ", "")).equals(texto);
        }
    }

    public static Date sumarAnioAFecha(Date fecha, int numero) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, numero);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date sumarMesAFecha(Date fecha, int numero) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, numero);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date sumarDiaAFecha(Date fecha, int numero) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, numero);

        return new Date(calendar.getTimeInMillis());
    }

    public static boolean esCorreoValido(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(correo).find();
    }

    public static boolean esIntNumerico(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            char valor = cadena.charAt(i);
            if (valor != '0' && valor != '1' && valor != '2' && valor != '3'
                    && valor != '4' && valor != '5' && valor != '6' && valor != '7'
                    && valor != '8' && valor != '9') {
                return false;
            }
        }
        return true;
    }

    public static String soloNumeros(String cadena) {
        if (cadena != null && !cadena.isEmpty()) {
            //Este elimina los caracteres especiales
            String regexSoloLetras = "[^\\dA-Za-z]";
            cadena = cadena.replaceAll(regexSoloLetras, "");

            //Este elimina las letras y espacios
            regexSoloLetras = "[A-Za-z Ññ]+";
            cadena = cadena.replaceAll(regexSoloLetras, "");
        }
        return cadena;
    }

    public static String obtenerRutaAbsoluta(String rutaRelativa) {
        String rutaReal = ((ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext()).getRealPath("/");
        String rutaAbsoluta;
        rutaAbsoluta = rutaReal + rutaRelativa;
        return rutaAbsoluta;
    }
public static boolean esSoloLetras(String cadena)
	{
		//Recorremos cada caracter de la cadena y comprobamos si son letras.
		//Para comprobarlo, lo pasamos a mayuscula y consultamos su numero ASCII.
		//Si está fuera del rango 65 - 90, es que NO son letras.
		//Para ser más exactos al tratarse del idioma español, tambien comprobamos
		//el valor 165 equivalente a la Ñ
 
		for (int i = 0; i < cadena.length(); i++)
		{
                   cadena = cadena.trim();
			char caracter = cadena.toUpperCase().charAt(i);
			int valorASCII = (int)caracter;
			if ( (valorASCII < 65 || valorASCII > 90) && valorASCII!=32 )
                            
                            
				return false; //Se ha encontrado un caracter que no es letra
		}
 
		//Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
		return true;
	}
       public static String  generarCorreo(String nombre, String apellido,String pais, Integer contador )
	{ String dominio="";
        String conteo="";
        String correo="";
		if ("Colombia".equals(pais)){
                dominio="@cidenet.com.co";
                }else if ("Estados Unidos".equals(pais)){
                dominio="@cidenet.com.co.eu";
                }
                
                if (contador!=0){
                
                contador=contador+1;
                conteo = contador.toString();}
                correo = nombre.trim()+apellido.replaceAll(" ","")+ dominio+conteo+dominio;
                
                
                return correo;
                
                
	}
}
