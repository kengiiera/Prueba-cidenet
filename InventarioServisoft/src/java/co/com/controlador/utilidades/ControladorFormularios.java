package co.com.controlador.utilidades;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Logger;


@ManagedBean
@RequestScoped
public class ControladorFormularios implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{param.page}")
    private String page;
    public static final Logger logger = Logger.getLogger(ControladorFormularios.class);

    public ControladorFormularios() {
        logger.info("se esta creando el PageController");
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String buscarFormulario() {
        return page;
    }
}
