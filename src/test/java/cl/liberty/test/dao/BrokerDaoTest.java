/**
 * 
 */
package cl.liberty.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.liberty.configuration.Application;
import cl.liberty.constantes.Errores;
import cl.liberty.dao.BrokerDao;
import cl.liberty.model.Broker;
import cl.liberty.request.BrokerRequest;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BrokerDaoTest {

	@Autowired
	BrokerDao brokerDao;
	
	@Ignore
	//@Test
	public void getBrokersTest() {
		List<Broker> brokers = brokerDao.getBrokers();
		for (int i = 0; i< brokers.size(); i++) {
			System.out.println(""+brokers.get(i).getBrokerCode());
			
		}
		assertNotNull("Corredor es null", brokers);
	}
	
	@Ignore
	@Test
	public void getBrokerTest() {
		Broker broker = brokerDao.getBroker(1440032);
		assertNotNull("Corredor es null", broker);
	}
	
	@Ignore
	@Test
	public void getBrokerByCodeTest() {
		Broker broker = brokerDao.getBrokerByCode(1440032);
		System.out.println("====="+ broker.getBrokerCode());
		assertNotNull("Corredor es null", broker);
	}

	@Ignore
	@Test
	public void addBrokerTest() {
		BrokerRequest brokerRequest = new BrokerRequest();
		brokerRequest.setBrokerCode(211111);
		brokerRequest.setBrokerDescription("Te1st1121");
		try {
			brokerDao.addBroker(brokerRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void editBrokerTest() {
		BrokerRequest brokerEditRequest = new BrokerRequest();
		brokerEditRequest.setBrokerCode(211111);
		brokerEditRequest.setBrokerDescription("corredor");
		try {
			brokerDao.editBroker(brokerEditRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void deleteBrokerTest() {
		Integer brokerCode = 211111;
		try {
			brokerDao.deleteBroker(brokerCode);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void validateBrokerExistTest() {
		try {
			boolean result = brokerDao.validateBrokerExist(1440032);
			assertTrue("Codigo corredor no existe", result);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
}
