/**
 * 
 */
package cl.liberty.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.liberty.configuration.Application;
import cl.liberty.constantes.Constantes;
import cl.liberty.dao.PolicyDao;
import cl.liberty.dao.PropertyDao;
import cl.liberty.model.Policy;
import cl.liberty.model.PolicyCoverage;
import cl.liberty.model.Property;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PolicyDaoTest {

	@Autowired
	PolicyDao policyDao;

	@Autowired
	PropertyDao propertyDao;

	@Test
	public void getPolicyTest() {
		int policyNumber = 20314861;
		int branchNumber = 0;
		Policy policy = policyDao.getPolicy(policyNumber, branchNumber);
		assertNotNull("Policy es null", policy);
	}

	@Test
	public void getPolicysTest() {
		int policyNumber = 20314861;
		int branchNumber = 0;
		List<Policy> policys = policyDao.getPolicys(policyNumber, branchNumber);
		assertNotNull("Poliza es null", policys);
	}
	
	@Test
	public void getPolicyNonMortgagesTest() {
		int policyNumber = 6000001;
		List<Policy> policys = policyDao.getPolicyNonMortgages(policyNumber);
		assertNotNull("Poliza es null", policys);
	}

	@Test
	public void getPolicyCoverageTest() {
		int policyNumber = 20323898;
		int branchNumber = 0;
		Property property = propertyDao.getProperty(Constantes.PROPERTY_NAME_COVERAGE);
		List<PolicyCoverage> policyCoverages = policyDao.getPolicyCoverages(policyNumber, branchNumber,
				property.getPropertyId());
		assertNotNull("Poliza con Coberturas es null", policyCoverages);
	}

}
