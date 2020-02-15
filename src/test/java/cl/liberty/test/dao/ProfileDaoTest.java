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
import cl.liberty.dao.ProfileDao;
import cl.liberty.model.Profile;
import cl.liberty.model.ProfileMenu;
import cl.liberty.model.User;

/**
 * @author jgarrido
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ProfileDaoTest {

	@Autowired
	ProfileDao profileDao;
	@Ignore
	@Test
	public void getProfileSTest() {
		List<Profile> profiles = profileDao.getProfiles();
		for (int i = 0; i< profiles.size(); i++) {
			System.out.println(""+profiles.get(i).getName());
			
		}
		assertNotNull("Perfiles es null", profiles);
	}
	//@Ignore
	@Test
	public void getProfileMenuTest() {
		User usuario = new User();
		usuario.setProfileId(1);
		List<ProfileMenu> profileMenus = profileDao.getProfileMenus(usuario);
		for (int i = 0; i< profileMenus.size(); i++) {
			System.out.println(""+profileMenus.get(i).getTitle());
			
		}
		assertNotNull("Perfil Menu es null", profileMenus);
	}
	//@Ignore
	@Test
	public void getProfileSubMenuTest() {
		User usuario = new User();
		usuario.setProfileId(1);
		List<ProfileMenu> profileSubMenus = profileDao.getProfileSubMenus(usuario, 1);
		for (int i = 0; i< profileSubMenus.size(); i++) {
			System.out.println(""+profileSubMenus.get(i).getTitle());
			
		}
		assertNotNull("Perfil Sub Menu es null", profileSubMenus);
	}

}
