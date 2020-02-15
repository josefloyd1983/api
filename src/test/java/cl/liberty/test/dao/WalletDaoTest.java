/**
 * 
 */
package cl.liberty.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.liberty.configuration.Application;
import cl.liberty.constantes.Constantes;
import cl.liberty.constantes.Errores;
import cl.liberty.dao.PropertyDao;
import cl.liberty.dao.WalletDao;
import cl.liberty.model.Property;
import cl.liberty.model.ValidityStart;
import cl.liberty.model.Wallet;
import cl.liberty.model.WalletCoverage;
import cl.liberty.model.WalletHistory;
import cl.liberty.request.WalletRequest;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class WalletDaoTest {

	@Autowired
	WalletDao walletDao;

	@Autowired
	PropertyDao propertyDao;
	@Ignore
	@Test
	public void getWalletTest() {
		Integer brokerCode = -1;
		Integer policyNumber = -1;
		Integer contractorCode = 2;
		String validInYears = "2018";
		Wallet wallet = walletDao.getWallet(policyNumber, contractorCode, brokerCode, validInYears);
		System.out.println("wallet="+wallet);
		assertNotNull("Cartera es null", wallet);
	}
	@Ignore
	@Test
	public void getWalletCoverageTest() {
		Integer brachCode = 0;
		Integer policyNumber = 20323898;
		List<WalletCoverage> walletCoverages = walletDao.getWalletCoverages(policyNumber, brachCode,
				Constantes.PROPERTY_ID_PADRE);
		
		assertNotNull("Cartera con Coberturas es null", walletCoverages);
	}
	@Ignore
	@Test
	public void getWalletHistoryTest() {
		Integer policyNumber = -1;
		Integer idUser = -1;
		List<WalletHistory> walletHistorys = walletDao.getWalletHistorys(policyNumber, idUser);
		for (int i = 0; i< walletHistorys.size(); i++) {
			System.out.println(""+walletHistorys.get(i).getPolicyNumber());
			
		}
		assertNotNull("Historial cartera es null", walletHistorys);
	}
	@Ignore
	@Test
	public void getWalletsTest() {
		Integer brokerCode = -1;
		Integer policyNumber = -1;
		Integer contractorCode = 2;
		String validInYears = "-1";
		Property property = propertyDao.getProperty(Constantes.PROPERTY_NAME_COVERAGE);
		System.out.println("property="+property);
		List<Wallet> wallets = walletDao.getWallets(policyNumber, contractorCode, brokerCode, validInYears,
				property.getPropertyId());
		System.out.println("wallets="+wallets);
		for (int i = 0; i< wallets.size(); i++) {
			System.out.println(""+wallets.get(i).getWalletName());
			
		}
		assertNotNull("Carteras es null", wallets);
	}
	@Ignore
	@Test
	public void addBrokerTest() {
		WalletRequest walletRequest = new WalletRequest();
		walletRequest.setWalletName("Incendio y Sismo 2018");
		walletRequest.setPolicyNumber(20323892);
		walletRequest.setValidityStartDate("2019-01-01 01:00:00");
		walletRequest.setValidityEndDate("2019-12-31 23:59:58");
		walletRequest.setContractorCode(2);
		walletRequest.setFlowId(1);
		walletRequest.setUsingId(1);
		walletRequest.setBrokerCode(2);
		walletRequest.setStatus(0);

		ArrayList<WalletCoverage> walletCoverages = new ArrayList<>();

		WalletCoverage walletCoverage = new WalletCoverage();
		walletCoverage.setWalletCoverageCode(12);
		walletCoverage.setWalletCoverageName("sssss");

		WalletCoverage walletCoverage2 = new WalletCoverage();
		walletCoverage2.setWalletCoverageCode(13);
		walletCoverage2.setWalletCoverageName("sssss");

		walletCoverages.add(walletCoverage);
		walletCoverages.add(walletCoverage2);
		try {

			walletDao.addWallet(walletRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	@Ignore
	@Test
	public void editBrokerTest() {
		WalletRequest walletRequest = new WalletRequest();
		walletRequest.setWalletId(5);
		walletRequest.setWalletName("Incendiooo y Sismo 20181");
		walletRequest.setPolicyNumber(20323892);
		walletRequest.setValidityStartDate("2019-01-01 01:00:01");
		walletRequest.setValidityEndDate("2019-12-31 23:59:59");
		walletRequest.setContractorCode(2);
		walletRequest.setFlowId(2);
		walletRequest.setUsingId(2);
		walletRequest.setBrokerCode(2);
		walletRequest.setStatus(1);
		try {
			walletDao.editWallet(walletRequest);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	//@Ignore
	@Test
	public void deleteWalletTest() {
		Integer walletId = (5);
		try {
			walletDao.deleteWallet(walletId);
			assertTrue(true);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	@Ignore
	@Test
	public void validateWalletExistTest() {
		try {
			boolean result = walletDao.validateWalletExist(2);
			assertTrue("Codigo corredor no existe", result);
		} catch (Exception e) {
			Assert.fail(Errores.EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	@Ignore
	@Test
	public void getValidityStartsTest() {
		List<ValidityStart> validityStarts = walletDao.getValidityStarts();
		System.out.println("validityStarts="+validityStarts);
		assertNotNull("Cartera con Coberturas es null", validityStarts);
	}

}
