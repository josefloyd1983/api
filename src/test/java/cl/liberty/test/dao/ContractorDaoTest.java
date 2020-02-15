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
import cl.liberty.dao.ContractorDao;
import cl.liberty.model.Contractor;
import cl.liberty.request.ContractorRequest;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ContractorDaoTest {

	@Autowired
	ContractorDao contractorDao;
	//@Ignore
	@Test
	public void getContractorsTest() {
		List<Contractor> contractors = contractorDao.getContractors();
		for (int i = 0; i< contractors.size(); i++) {
			System.out.println(""+contractors.get(i).getContractorCode());
			
		}
		assertNotNull("Contratante es null", contractors);
	}
	
	@Ignore
	@Test
	public void getContractorTest() {
		Contractor contractor = contractorDao.getContractor(1391202);
		assertNotNull("Contratante es null", contractor);
	}
	
	
	@Ignore
	@Test
	public void addContractorTest() {
		ContractorRequest contractorAddRequest = new ContractorRequest();
		contractorAddRequest.setContractorCode(2);
		contractorAddRequest.setContractorDescription("Test2");
		try {
			contractorDao.addContractor(contractorAddRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	@Ignore
	@Test
	public void deleteContractorTest() {
		Integer contractorCode = 2;
		try {
			contractorDao.deleteContractor(contractorCode);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	//@Ignore
	@Test
	public void validateContractorExistTest() {
		try {
			boolean result = contractorDao.validateContractorExist(1391202);
			assertTrue("Codigo contratante no existe", result);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}

}
