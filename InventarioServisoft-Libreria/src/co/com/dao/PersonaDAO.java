package co.com.dao;

import co.com.objetos.beanPersonas;
import java.util.List;

/**
 *
 * @author Kengie
 */
public interface PersonaDAO {

    public beanPersonas obtenerPersonaPorId(String identificacion);

    public Boolean existePersona(String identificacion);

    public int insert(beanPersonas persona);

    public int update(beanPersonas persona);
    
    public List<beanPersonas> getAllPersonas();
}
