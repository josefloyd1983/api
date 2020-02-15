/**
 * 
 */
package cl.liberty.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import cl.liberty.constantes.Constantes;
import cl.liberty.dao.ProfileDao;
import cl.liberty.mapper.ProfileMenuMapper;
import cl.liberty.model.Profile;
import cl.liberty.model.ProfileMenu;
import cl.liberty.model.User;

/**
 * @author jgarrido
 *
 */

@Repository
public class ProfileDaoImpl implements ProfileDao {

	@Autowired
	Environment env;

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Profile> getProfiles() {
		/*String query = " SELECT ID_PERFIL, NOMBRE, FECHA_CREACION, FLAGRUTA FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_PERFIL ORDER BY ID_PERFIL ASC";
		return jdbc.query(query, new ProfileMapper());
		*/
		
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_profiles")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(Profile.class));
		Map<String, Object> out = simpleJdbcCall.execute();
		return ((List<Profile>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfileMenu> getProfileMenus(User usuario) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id_profile", usuario.getProfileId());
		String query = " SELECT CBM.ID_MODULO as id_module, CBM.NOMBRE as name, CBM.URL as url, CBM.ICON as icon, CBA.ID_ACCESO as id_access, CBA.NOMBRE accessName, CBA.FECHA_CREACION as creationdate, CBM.estado FROM "
				+ env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_MODULO CBM INNER JOIN "
				+ env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_MODULO_PERFIL RBMP ON RBMP.ID_MODULO = CBM.ID_MODULO  AND RBMP.ID_PERFIL = :id_profile INNER JOIN "
				+ env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_ACCESO CBA ON RBMP.ID_ACCESO = CBA.ID_ACCESO  AND CBA.ID_ACCESO > 1   WHERE 1 = 1  AND CBM.ESTADO = 1  AND CBM.ID_PADRE = 0  ORDER BY CBM.ORDEN ASC";
		return jdbc.query(query, namedParameters, new ProfileMenuMapper());
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_PROFILE_ID, usuario.getProfileId());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_profile_menu")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(ProfileMenu.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<ProfileMenu>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfileMenu> getProfileSubMenus(User usuario, int fatherId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id_profile", usuario.getProfileId())
				.addValue("father_id", fatherId);
		String query = " SELECT  CBM.ID_MODULO as id_module, CBM.NOMBRE as name, CBM.URL, CBM.ICON, CBA.ID_ACCESO as id_access, CBA.NOMBRE as accessName, CBA.FECHA_CREACION as creationdate, CBM.estado FROM "
				+ env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_MODULO CBM	INNER JOIN "
				+ env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_MODULO_PERFIL RBMP ON RBMP.ID_MODULO = CBM.ID_MODULO  AND RBMP.ID_PERFIL = :id_profile  INNER JOIN "
				+ env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_ACCESO CBA ON RBMP.ID_ACCESO = CBA.ID_ACCESO  AND CBA.ID_ACCESO > 1  WHERE 1 = 1  AND CBM.ESTADO = 1  AND CBM.ID_PADRE = :father_id  ORDER BY CBM.ORDEN ASC";
		return jdbc.query(query, namedParameters, new ProfileMenuMapper());
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_PROFILE_ID, usuario.getProfileId())
				.addValue(Constantes.PARAMETER_PROFILE_FATHER_ID, fatherId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_profile_sub_menu")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(ProfileMenu.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<ProfileMenu>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
		
		
	}

}
