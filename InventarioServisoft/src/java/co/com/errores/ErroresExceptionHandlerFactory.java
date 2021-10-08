/*
 * Servisoft 2013 (c)
 * www.servisoft.com.co
 */
package co.com.errores;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Clase ErroresExceptionHandlerFactory
 *
 * @author dquintero
 */
public class ErroresExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory exceptionHandlerFactory;

    public ErroresExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = exceptionHandlerFactory.getExceptionHandler();
        result = new ErroresExceptionHandler(result);
        return result;
    }
}
