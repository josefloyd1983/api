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
import cl.liberty.dao.BrokerDao;
import cl.liberty.mapper.BrokerMapper;
import cl.liberty.model.Broker;
import cl.liberty.request.BrokerRequest;

/**
 * @author jgarrido
 *
 */

@Repository
public class BrokerDaoImpl implements BrokerDao {

	@Autowired
	Environment env;

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Broker> getBrokers() {
		//String query = "SELECT CODIGO_CORREDOR, DESCRIPCION FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CORREDOR";
		//return jdbc.query(query, new BrokerMapper());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_brokers")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(Broker.class));
		Map<String, Object> out = simpleJdbcCall.execute();
		return ((List<Broker>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Broker getBroker(Integer brokerCode) {
		
		/*SqlParameterSource namedParameters = new  MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_BROKER_CODE, brokerCode);
		String query = " SELECT CODIGO_CORREDOR, DESCRIPCION FROM " + env.getProperty(Constantes.ESCHEMA_DB) +  ".HIPO_CORREDOR WHERE CODIGO_CORREDOR = :brokerCode "; 
		return jdbc.queryForObject(query, namedParameters, new BrokerMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_BROKER_CODE, brokerCode);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_broker");
		Map out = simpleJdbcCall.execute(paramaters);
		Broker broker = new Broker();
		broker.setBrokerCode(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_BROKER_CODE))));
		broker.setBrokerDescription(String.valueOf(out.get(Constantes.PARAMETER_BROKER_DESCRIPTION)));
		return broker;
		
	}

	@Override
	public Broker getBrokerByCode(Integer code) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("brokerCode", code);
		String query = " SELECT CODIGO_CORREDOR, DESCRIPCION FROM " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_CORREDOR WHERE CODIGO_CORREDOR = :brokerCode ";
		return jdbc.queryForObject(query, namedParameters, new BrokerMapper());
	}

	@Override
	public void addBroker(BrokerRequest brokerRequest) {
		
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, brokerRequest.getBrokerCode())
				.addValue(Constantes.MAP_PARAMETER_BROKER_DESCRIPTION, brokerRequest.getBrokerDescription());
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_CORREDOR(CODIGO_CORREDOR, DESCRIPCION )  VALUES(:brokerCode,:brokerDescription) ";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_BROKER_CODE, brokerRequest.getBrokerCode())
				.addValue(Constantes.PARAMETER_BROKER_DESCRIPTION, brokerRequest.getBrokerDescription());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_add_broker");
		simpleJdbcCall.execute(paramaters);
		
	}

	@Override
	public void editBroker(BrokerRequest brokerRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, brokerRequest.getBrokerCode())
				.addValue(Constantes.MAP_PARAMETER_BROKER_DESCRIPTION, brokerRequest.getBrokerDescription());
		String query = "UPDATE " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_CORREDOR SET CODIGO_CORREDOR = :brokerCode, DESCRIPCION = :brokerDescription WHERE CODIGO_CORREDOR = :brokerCode ";
		jdbc.update(query, namedParameters);
		*/
		
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_BROKER_CODE, brokerRequest.getBrokerCode())
				.addValue(Constantes.PARAMETER_BROKER_DESCRIPTION, brokerRequest.getBrokerDescription());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_edit_broker");
		simpleJdbcCall.execute(paramaters);
		
	}

	@Override
	public void deleteBroker(Integer brokerCode) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_BROKER_CODE,
				brokerCode);
		String query = "DELETE FROM " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_CORREDOR where CODIGO_CORREDOR = :brokerCode";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_BROKER_CODE, brokerCode);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_delete_broker");
		simpleJdbcCall.execute(paramaters);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validateBrokerExist(Integer brokerCode) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("broker_code", brokerCode);
		String query = "SELECT COUNT(*) FROM " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_CORREDOR WHERE CODIGO_CORREDOR = :broker_code";
		return jdbc.queryForObject(query, namedParameters, Integer.class) != 0;
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_BROKER_CODE, brokerCode);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_broker_exist");
		Map out = simpleJdbcCall.execute(paramaters);
		return Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_COUNT))) != 0;
		
	}
}
