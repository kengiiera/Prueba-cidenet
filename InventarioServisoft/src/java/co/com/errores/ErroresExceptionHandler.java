/*
 * Servisoft 2013 (c)
 * www.servisoft.com.co
 */
package co.com.errores;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.apache.log4j.Logger;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.NavigationHandler;

/**
 * Clase ErroresExceptionHandler
 *
 * @author dquintero
 */
public class ErroresExceptionHandler extends ExceptionHandlerWrapper {

    private static Logger logger = Logger.getLogger(ErroresExceptionHandler.class);
    private ExceptionHandler exceptionHandler;

    public ErroresExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.exceptionHandler;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent exceptionQueuedEvent = i.next();
            logger.info("Iterating over ExceptionQueuedEvents.current:" + exceptionQueuedEvent.toString());
            ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) exceptionQueuedEvent.getSource();
            Throwable t = exceptionQueuedEventContext.getException();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

            try {
                requestMap.put("currentView", t.getMessage());
                facesContext.getExternalContext().getFlash().put("exceptionCause", t.getCause());
                facesContext.getExternalContext().getFlash().put("current", t.getMessage());
                logger.error("ERRORRRRRRRRR.... " + t.getCause() + " " + t.getMessage());
                logger.error("desc " + t.fillInStackTrace());
                logger.error(t.getCause() + " " + t.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                navigationHandler.handleNavigation(facesContext, null, "/index.xhtml?faces-redirect=true");
                facesContext.renderResponse();
            } catch (Exception ex) {
                logger.error("context", ex);
            } finally {
                i.remove();
            }
        }
        getWrapped().handle();
    }
}
