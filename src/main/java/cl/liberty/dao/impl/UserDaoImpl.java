/**

*

 */

package cl.liberty.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
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
import cl.liberty.dao.UserDao;
import cl.liberty.mapper.UserMapper;
import cl.liberty.model.Broker;
import cl.liberty.model.User;
import cl.liberty.request.UserRequest;
import cl.liberty.utils.UtilAMS;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	Environment env;

	@Autowired

	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(String userName) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userName", userName);
		String query = " select hu.id_usuario as id_user, hu.fecha_creacion as creationdate , hu.email as email, hu.apellido as lastname, hu.nombre as name, hu.estado as status, hu.username as username, hu.password as password, hps.id_pais as id_country, hps.nombre as countryname, hps.codigo_pais as countrycode, hp.id_perfil as id_profile, hp.nombre as profilename, hp.fecha_creacion profilecreationdate, hp.flagruta as flagroute from " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario hu inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario_perfil_pais ue on hu.id_usuario = ue.id_usuario and hu.estado = 1 and lower(hu.username) = lower(:userName)  inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_perfil hp on ue.id_perfil = hp.id_perfil  inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais hps on hps.id_pais = ue.id_pais and hps.id_pais = 1";
		return jdbc.queryForObject(query, namedParameters, new UserMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_USER_USERNAME, userName);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_user");
		Map out = simpleJdbcCall.execute(paramaters);
		User user = new User();
		user.setUserId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_ID))));
		user.setCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_CREATION_DATE)));
		user.setEmail(String.valueOf(out.get(Constantes.PARAMETER_USER_EMAIL)));
		user.setLastName(String.valueOf(out.get(Constantes.PARAMETER_LASTNAME)));
		user.setName(String.valueOf(out.get(Constantes.PARAMETER_USER_NAME)));
		user.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_STATUS))));
		user.setUserName(String.valueOf(out.get(Constantes.PARAMETER_USER_USERNAME)));
		user.setPassword(String.valueOf(out.get(Constantes.PARAMETER_USER_PASSWORD)));
		user.setCountryId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_ID))));
		user.setCountryName(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_NAME)));
		user.setCountryCode(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_CODE)));
		user.setProfileId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_ID))));
		user.setProfileName(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_NAME)));
		user.setProfileCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_CREATION_DATE)));
		user.setFlagRoute(String.valueOf(out.get(Constantes.PARAMETER_USER_FLAG_ROUTE)));
		return user;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(Integer userId, Integer status, Integer profileId, Integer countryId, String name,
			String nameSurname, String userName) {
		System.out.println( userId + " /  "+status + " /  "+profileId + " /  "+countryId + " /  "+name+ " /  "+ nameSurname + " /  "+ userName);
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USERID, userId)
				.addValue(Constantes.MAP_PARAMETER_USER_STATUS, status)
				.addValue(Constantes.MAP_PARAMETER_USER_PROFILEID, profileId)
				.addValue(Constantes.MAP_PARAMETER_USER_COUNTRYID, countryId)
				.addValue(Constantes.MAP_PARAMETER_USER_NAME, name + "%")
				.addValue(Constantes.MAP_PARAMETER_USER_NAME_SURNAME, nameSurname + "%")
				.addValue(Constantes.MAP_PARAMETER_USER_USERNAME, userName + "%");
		String query = ""
				+ " select"
				+ "		hu.id_usuario as id_user "
				+ "		, hu.fecha_creacion as creationdate"
				+ "     , hu.email as email, hu.apellido as lastname"
				+ "		, hu.nombre as name "
				+ "     , hu.estado as status "
				+ "		, hu.username as username "
				+ "		, hu.password as password"
				+ "     , hps.id_pais as id_country"
				+ "		, hps.nombre as countryname"
				+ "     , hps.codigo_pais as countrycode"
				+ "		, hp.id_perfil as id_profile"
				+ "     , hp.nombre as profilename"
				+ "		, hp.fecha_creacion profilecreationdate"
				+ "     , hp.flagruta as flagroute "
				+ " from   " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario hu inner join hipo_usuario_perfil_pais ue on hu.id_usuario = ue.id_usuario  "
				+ " inner join   " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_perfil hp on ue.id_perfil = hp.id_perfil   "
				+ " inner join   " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais hps on hps.id_pais = ue.id_pais  ";

		if (userId != -1) {
			query += " AND hu.id_usuario = :userId ";
		}

		if (status != -1) {
			query += "  AND hu.estado = :status ";
		}

		if (profileId != -1) {
			query += " AND hp.id_perfil = :profileId ";
		}

		if (countryId != -1) {
			query += " AND hps.id_pais = :countryId ";
		}

		if (name != null) {
			query += " AND upper(hu.nombre) LIKE upper(:name)";
		}

		if (nameSurname != null) {
			query += " AND upper(hu.nombre || ' ' || hu.apellido) LIKE upper(:nameSurname)";
		}

		if (userName != null) {
			query += " AND upper(hu.username) LIKE upper(:userName)";
		}
		
		query += " ORDER BY hu.id_usuario ASC ";
		return jdbc.query(query, namedParameters, new UserMapper());
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_USER_ID, userId)
				.addValue(Constantes.PARAMETER_USER_STATUS, status)
				.addValue(Constantes.PARAMETER_USER_PROFILE_ID, profileId)
				.addValue(Constantes.PARAMETER_USER_COUNTRY_ID, countryId)
				.addValue(Constantes.PARAMETER_USER_NAME, name )
				.addValue(Constantes.PARAMETER_USER_NAME_SURNAME, nameSurname)
				.addValue(Constantes.PARAMETER_USER_USERNAME, userName);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_users")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(User.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<User>) out.get(env.getProperty(Constantes.P_CURSOR)));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(Integer userId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_USERID,
				userId);
		String query = ""
				+ "select"
				+ "		hu.id_usuario as id_user "
				+ "		, hu.fecha_creacion as creationdate"
				+ "     , hu.email as email"
				+ "		, hu.apellido as lastname"
				+ "		, hu.nombre as name "
				+ "     , hu.estado as status "
				+ "		, hu.username as username "
				+ "		, hu.password as password"
				+ "     , hps.id_pais as id_country"
				+ "		, hps.nombre as countryname"
				+ "     , hps.codigo_pais as countrycode"
				+ "		, hp.id_perfil as id_profile"
				+ "     , hp.nombre as profilename"
				+ "		, hp.fecha_creacion profilecreationdate"
				+ "     , hp.flagruta as flagroute"
				+ " FROM  " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario hu inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario_perfil_pais ue on hu.id_usuario = ue.id_usuario   "
				+ " inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_perfil hp on ue.id_perfil = hp.id_perfil "
				+ " inner join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais hps on hps.id_pais = ue.id_pais " + " and hu.id_usuario =  :userId";
		return jdbc.queryForObject(query, namedParameters, new UserMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_USER_ID,
				userId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_user_identity");
		Map out = simpleJdbcCall.execute(paramaters);
		User user = new User();
		user.setUserId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_ID))));
		user.setCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_CREATION_DATE)));
		user.setEmail(String.valueOf(out.get(Constantes.PARAMETER_USER_EMAIL)));
		user.setLastName(String.valueOf(out.get(Constantes.PARAMETER_LASTNAME)));
		user.setName(String.valueOf(out.get(Constantes.PARAMETER_USER_NAME)));
		user.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_STATUS))));
		user.setUserName(String.valueOf(out.get(Constantes.PARAMETER_USER_USERNAME)));
		user.setPassword(String.valueOf(out.get(Constantes.PARAMETER_USER_PASSWORD)));
		user.setCountryId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_ID))));
		user.setCountryName(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_NAME)));
		user.setCountryCode(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_CODE)));
		user.setProfileId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_ID))));
		user.setProfileName(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_NAME)));
		user.setProfileCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_CREATION_DATE)));
		user.setFlagRoute(String.valueOf(out.get(Constantes.PARAMETER_USER_FLAG_ROUTE)));
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUserByUserName(String userName) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USER_USERNAME, userName);
		String query = ""
				+ "select "
				+ "		hu.id_usuario as id_user"
				+ "     , hu.fecha_creacion as creationdate"
				+ "		, hu.email as email"
				+ "     , hu.apellido as lastname"
				+ "		, hu.nombre as name"
				+ "     , hu.estado as status"
				+ "		, hu.username as username"
				+ "     , hu.password as password"
				+ "		, hps.id_pais as id_country"
				+ "     , hps.nombre as countryname"
				+ "		, hps.codigo_pais as countrycode"
				+ "     , hp.id_perfil as id_profile"
				+ "		, hp.nombre as profilename"
				+ "     , hp.fecha_creacion profilecreationdate"
				+ "		, hp.flagruta as flagroute"
				+ " from " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario hu left join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario_perfil_pais ue on hu.id_usuario = ue.id_usuario  "
				+ " left join  " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_perfil hp on ue.id_perfil = hp.id_perfil   "
				+ " left join  " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais hps on hps.id_pais = ue.id_pais" + " where hu.username =  :userName ";
		
		return jdbc.queryForObject(query, namedParameters, new UserMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_USER_USERNAME,
				userName);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_user_by_username");
		Map out = simpleJdbcCall.execute(paramaters);
		User user = new User();
		user.setUserId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_ID))));
		user.setCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_CREATION_DATE)));
		user.setEmail(String.valueOf(out.get(Constantes.PARAMETER_USER_EMAIL)));
		user.setLastName(String.valueOf(out.get(Constantes.PARAMETER_LASTNAME)));
		user.setName(String.valueOf(out.get(Constantes.PARAMETER_USER_NAME)));
		user.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_STATUS))));
		user.setUserName(String.valueOf(out.get(Constantes.PARAMETER_USER_USERNAME)));
		user.setPassword(String.valueOf(out.get(Constantes.PARAMETER_USER_PASSWORD)));
		user.setCountryId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_ID))));
		user.setCountryName(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_NAME)));
		user.setCountryCode(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_CODE)));
		user.setProfileId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_ID))));
		user.setProfileName(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_NAME)));
		user.setProfileCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_CREATION_DATE)));
		user.setFlagRoute(String.valueOf(out.get(Constantes.PARAMETER_USER_FLAG_ROUTE)));
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUserByEmail(String email) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
		String query = " "
				+ "select"
				+ "		hu.id_usuario as id_user"
				+ "     , hu.fecha_creacion as creationdate"
				+ "		, hu.email as email"
				+ "     , hu.apellido as lastname"
				+ "		, hu.nombre as name"
				+ "     , hu.estado as status"
				+ "		, hu.username as username"
				+ "     , hu.password as password"
				+ "		, hps.id_pais as id_country"
				+ "     , hps.nombre as countryname"
				+ "		, hps.codigo_pais as countrycode"
				+ "     , hp.id_perfil as id_profile"
				+ "		, hp.nombre as profilename"
				+ "     , hp.fecha_creacion profilecreationdate"
				+ "		, hp.flagruta as flagroute"
				+ " from " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario hu left join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_usuario_perfil_pais ue on hu.id_usuario = ue.id_usuario"
				+ " left join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_perfil hp on ue.id_perfil = hp.id_perfil"
				+ " left join " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais hps on hps.id_pais = ue.id_pais" + " where hu.email =  :email ";
		return jdbc.queryForObject(query, namedParameters, new UserMapper());
		*/
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_USER_EMAIL,
				email);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_user_by_email");
		Map out = simpleJdbcCall.execute(paramaters);
		User user = new User();
		user.setUserId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_ID))));
		user.setCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_CREATION_DATE)));
		user.setEmail(String.valueOf(out.get(Constantes.PARAMETER_USER_EMAIL)));
		user.setLastName(String.valueOf(out.get(Constantes.PARAMETER_LASTNAME)));
		user.setName(String.valueOf(out.get(Constantes.PARAMETER_USER_NAME)));
		user.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_STATUS))));
		user.setUserName(String.valueOf(out.get(Constantes.PARAMETER_USER_USERNAME)));
		user.setPassword(String.valueOf(out.get(Constantes.PARAMETER_USER_PASSWORD)));
		user.setCountryId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_ID))));
		user.setCountryName(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_NAME)));
		user.setCountryCode(String.valueOf(out.get(Constantes.PARAMETER_USER_COUNTRY_CODE)));
		user.setProfileId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_ID))));
		user.setProfileName(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_NAME)));
		user.setProfileCreationDate(String.valueOf(out.get(Constantes.PARAMETER_USER_PROFILE_CREATION_DATE)));
		user.setFlagRoute(String.valueOf(out.get(Constantes.PARAMETER_USER_FLAG_ROUTE)));
		return user;
	}

	@Override
	public void addUser(UserRequest userRequest) {
		Date now = new Date();
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USER_NAME, userRequest.getName())
				.addValue(Constantes.MAP_PARAMETER_USER_LASTNAME, userRequest.getLastName())
				.addValue(Constantes.MAP_PARAMETER_USER_USERNAME, userRequest.getUserName())
				.addValue(Constantes.MAP_PARAMETER_USER_EMAIL, userRequest.getEmail())
				.addValue(Constantes.MAP_PARAMETER_USER_PROFILEID, userRequest.getProfileId())
				.addValue(Constantes.MAP_PARAMETER_USER_STATUS, userRequest.getStatus())
				.addValue(Constantes.MAP_PARAMETER_USER_PASS_WORD, userRequest.getPassWord())
				.addValue(Constantes.MAP_PARAMETER_USER_CREATIONDATE, UtilAMS.formatDate(now, 3));
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO (ID_PERFIL, FECHA_CREACION, EMAIL, NOMBRE, APELLIDO, ESTADO, USERNAME, PASSWORD)  VALUES(:profileId, to_date(:creacionDate, 'YYYY-MM-DD HH24:MI:SS'), :email, :name, :lastName, :status, :userName, :passWord)";
		jdbc.update(query, namedParameters);*/
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_USER_NAME, userRequest.getName())
				.addValue(Constantes.PARAMETER_LASTNAME, userRequest.getLastName())
				.addValue(Constantes.PARAMETER_USER_USERNAME, userRequest.getUserName())
				.addValue(Constantes.PARAMETER_USER_EMAIL, userRequest.getEmail())
				.addValue(Constantes.PARAMETER_USER_PROFILE_ID, userRequest.getProfileId())
				.addValue(Constantes.PARAMETER_USER_STATUS, userRequest.getStatus())
				.addValue(Constantes.PARAMETER_USER_PASSWORD, userRequest.getPassWord())
				.addValue(Constantes.PARAMETER_USER_CREATION_DATE, UtilAMS.formatDate(now, 3));
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_add_user");
		simpleJdbcCall.execute(parameters);
		
	}

	@Override
	public void editUser(UserRequest userRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USERID, userRequest.getUserId())
				.addValue(Constantes.MAP_PARAMETER_USER_NAME, userRequest.getName())
				.addValue(Constantes.MAP_PARAMETER_USER_LASTNAME, userRequest.getLastName())
				.addValue(Constantes.MAP_PARAMETER_USER_USERNAME, userRequest.getUserName())
				.addValue(Constantes.MAP_PARAMETER_USER_EMAIL, userRequest.getEmail())
				.addValue(Constantes.MAP_PARAMETER_USER_PROFILEID, userRequest.getProfileId())
				.addValue(Constantes.MAP_PARAMETER_USER_STATUS, userRequest.getStatus())
				.addValue(Constantes.MAP_PARAMETER_USER_PASS_WORD, userRequest.getPassWord());
		String query = "UPDATE  " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO SET ID_PERFIL = :profileId,  EMAIL=:email, NOMBRE=:name, APELLIDO=:lastName, ESTADO=:status, USERNAME=:userName, " + Constantes.PASS_WORD + "=:passWord  WHERE ID_USUARIO = :userId";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_USER_ID, userRequest.getUserId())
				.addValue(Constantes.PARAMETER_USER_NAME, userRequest.getName())
				.addValue(Constantes.PARAMETER_LASTNAME, userRequest.getLastName())
				.addValue(Constantes.PARAMETER_USER_USERNAME, userRequest.getUserName())
				.addValue(Constantes.PARAMETER_USER_EMAIL, userRequest.getEmail())
				.addValue(Constantes.PARAMETER_USER_PROFILE_ID, userRequest.getProfileId())
				.addValue(Constantes.PARAMETER_USER_STATUS, userRequest.getStatus())
				.addValue(Constantes.PARAMETER_USER_PASSWORD, userRequest.getPassWord());
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_edit_user");
		simpleJdbcCall.execute(parameters);
	}

	@Override
	public void deleteUser(Integer userId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_USERID,
				userId);
		String query = "DELETE FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO where ID_USUARIO = :userId";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_USER_ID, userId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_delete_user");
		simpleJdbcCall.execute(paramaters);
	}

	@Override
	public void addUserProfile(Integer userId, Integer profileId, Integer countryId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USERID, userId)
				.addValue(Constantes.MAP_PARAMETER_USER_PROFILEID, profileId).addValue("countryId", countryId);
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO_PERFIL_PAIS (ID_USUARIO, ID_PERFIL, ID_PAIS) VALUES(:userId, :profileId, :countryId)";
		jdbc.update(query, namedParameters);
	}

	@Override
	public void editUserProfile(Integer userId, Integer profileId, Integer countryId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USERID, userId)
				.addValue(Constantes.MAP_PARAMETER_USER_PROFILEID, profileId).addValue("countryId", countryId);
		String query = "UPDATE " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO_PERFIL_PAIS SET ID_PERFIL=:profileId, ID_PAIS=:countryId WHERE ID_USUARIO = :userId";
		jdbc.update(query, namedParameters);

	}

	@Override
	public void deleteUserProfile(Integer userId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_USERID,
				userId);
		String query = "DELETE FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO_PERFIL_PAIS WHERE ID_USUARIO = :userId";
		jdbc.update(query, namedParameters);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validateUserExist(Integer userId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId", userId);
		String query = "SELECT COUNT(*) FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO WHERE ID_USUARIO = :userId";
		return jdbc.queryForObject(query, namedParameters, Integer.class) != 0;
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_USER_ID, userId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_user_exist");
		Map out = simpleJdbcCall.execute(paramaters);
		return Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_COUNT))) != 0;

	}

	@Override
	public void changeStatusUser(Integer userId, Integer status) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_USERID, userId)
				.addValue(Constantes.MAP_PARAMETER_USER_STATUS, status);
		String query = "UPDATE " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO SET ESTADO =:status  WHERE ID_USUARIO = :userId ";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_USER_ID,userId)
				.addValue(Constantes.PARAMETER_USER_STATUS, status);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_change_status_user");
		simpleJdbcCall.execute(parameters);

	}
}