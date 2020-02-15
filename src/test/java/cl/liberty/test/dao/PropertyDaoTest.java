/**
 * 
 */
package cl.liberty.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.liberty.configuration.Application;
import cl.liberty.constantes.Constantes;
import cl.liberty.dao.PropertyDao;
import cl.liberty.model.Property;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PropertyDaoTest {

	@Autowired
	PropertyDao propertyDao;
	
	@Ignore
	@Test
	public void getPropertyTest() {
		Property property = propertyDao.getProperty(Constantes.PROPERTY_NAME_COVERAGE);
		System.out.println("property=" + property.getDescription());
		assertNotNull("Propiedades es null", property);
	}
	
	@Ignore
	@Test
	public void getPropertysTest() {
		List<Property> propertys = propertyDao.getPropertys(Constantes.PROPERTY_ID_PADRE);
		for (int i = 0; i< propertys.size(); i++) {
			System.out.println(""+propertys.get(i).getDescription());
			
		}
		assertNotNull("Propiedades es null", propertys);
	}

}
