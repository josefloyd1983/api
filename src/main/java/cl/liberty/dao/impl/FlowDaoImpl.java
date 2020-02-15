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
import cl.liberty.dao.FlowDao;
import cl.liberty.model.Flow;
import cl.liberty.request.FlowRequest;

/**
 * @author jgarrido
 *
 */

@Repository
public class FlowDaoImpl implements FlowDao {

	@Autowired
	Environment env;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Flow> getFlows() {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = " SELECT ID_FLUJO, DESCRIPCION FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_FLUJO";
		return jdbc.query(query, namedParameters, new FlowMapper());
		*/
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_flows")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(Flow.class));
		Map<String, Object> out = simpleJdbcCall.execute();
		return ((List<Flow>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
		
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Flow getFlow(Integer flowId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_FLOW_ID,
				flowId);
		String query = " SELECT ID_FLUJO, DESCRIPCION FROM " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_FLUJO WHERE ID_FLUJO = :flowId ";
		return jdbc.queryForObject(query, namedParameters, new FlowMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_FLOW_ID, flowId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_flow");
		Map out = simpleJdbcCall.execute(paramaters);
		Flow flow = new Flow();
		flow.setFlowId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_FLOW_ID))));
		flow.setFlowDescription(String.valueOf(out.get(Constantes.PARAMETER_FLOW_DESCRIPTION)));
		return flow;
		
	}

	@Override
	public void addFlow(FlowRequest flowRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_FLOW_DESCRIPTION, flowRequest.getFlowDescription());
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_FLUJO(DESCRIPCION )  VALUES(:flowDescription) ";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_FLOW_ID, flowRequest.getFlowId())
				.addValue(Constantes.PARAMETER_FLOW_DESCRIPTION, flowRequest.getFlowDescription());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_add_flow");
		simpleJdbcCall.execute(paramaters);
		
	}

	@Override
	public void editFlow(FlowRequest flowEditRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_FLOW_DESCRIPTION, flowEditRequest.getFlowDescription())
				.addValue(Constantes.MAP_PARAMETER_FLOW_ID, flowEditRequest.getFlowId());
		String query = "UPDATE " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_FLUJO SET  DESCRIPCION = :flowDescription WHERE ID_FLUJO = :flowId ";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_FLOW_ID, flowEditRequest.getFlowId())
				.addValue(Constantes.PARAMETER_FLOW_DESCRIPTION, flowEditRequest.getFlowDescription());
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_edit_flow");
		simpleJdbcCall.execute(paramaters);
		
	}

	@Override
	public void deleteFlow(Integer flowId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_FLOW_ID,
				flowId);
		String query = "DELETE FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_FLUJO where ID_FLUJO = :flowId";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_FLOW_ID, flowId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_delete_flow");
		simpleJdbcCall.execute(paramaters);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validateFlowExist(Integer flowId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_FLOW_ID,
				flowId);
		String query = "SELECT COUNT(*) FROM " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_FLUJO WHERE ID_FLUJO = :flowId";
		return jdbc.queryForObject(query, namedParameters, Integer.class) != 0;
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_FLOW_ID, flowId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_flow_exist");
		Map out = simpleJdbcCall.execute(paramaters);
		return Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_COUNT))) != 0;
		
	}
}
