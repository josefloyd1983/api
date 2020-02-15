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
import cl.liberty.dao.FlowDao;
import cl.liberty.model.Flow;
import cl.liberty.request.FlowRequest;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class FlowDaoTest {

	@Autowired
	FlowDao flowDao;
	
	
	@Ignore
	@Test
	public void getFlowsTest() {
		List<Flow> flows = flowDao.getFlows();
		for (int i = 0; i< flows.size(); i++) {
			System.out.println(""+flows.get(i).getFlowDescription());
			
		}
		assertNotNull("Flujo es null", flows);
	}
	
	@Ignore
	@Test
	public void getFlowTest() {
		Flow flow = flowDao.getFlow(1);
		assertNotNull("Flujo es null", flow);
	}
	
	

	@Ignore
	@Test
	public void addFlowTest() {
		FlowRequest flowRequest = new FlowRequest();
		flowRequest.setFlowDescription("Te1st1121");
		try {
			flowDao.addFlow(flowRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void editFlowTest() {
		FlowRequest flowRequest = new FlowRequest();
		flowRequest.setFlowId(Long.valueOf(3));
		flowRequest.setFlowDescription("111");
		try {
			flowDao.editFlow(flowRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void deleteFlowTest() {
		Integer flowId = 4;
		try {
			flowDao.deleteFlow(flowId);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	//@Ignore
	@Test
	public void validateBrokerExistTest() {
		try {
			boolean result = flowDao.validateFlowExist(1);
			assertTrue("Codigo corredor no existe", result);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}

}
