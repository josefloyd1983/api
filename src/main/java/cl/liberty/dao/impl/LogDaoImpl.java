/**
 * 
 */
package cl.liberty.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import cl.liberty.constantes.Constantes;
import cl.liberty.dao.LogDao;
import cl.liberty.request.LogRequest;
import cl.liberty.utils.UtilAMS;

/**
 * @author jgarrido
 *
 */

@Repository
public class LogDaoImpl implements LogDao {
	
	@Autowired
	Environment env;

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addLog(LogRequest logRequest) {
		Date now = new Date();
		
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("eventId", logRequest.getEventId())
				.addValue("userId", logRequest.getUserId())
				.addValue("walletId", logRequest.getWalletId())
				.addValue("valueNew", logRequest.getValueNew())
				.addValue("valueOld", logRequest.getValueOld())
				.addValue("modificatioDate", UtilAMS.formatDate(now, 3));
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB)
				+ ".HIPO_LOG (ID_USUARIO, ID_CARTERA, ID_EVENTO, VALOR_ANTIGUO, VALOR_NUEVO, FECHA_MODIFICACION) "
				+ "VALUES(:userId, :walletId, :eventId, :valueOld, :valueNew, to_date(:modificatioDate, 'YYYY-MM-DD HH24:MI:SS'))";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_LOG_EVENT_ID, logRequest.getEventId())
				.addValue(Constantes.PARAMETER_LOG_USER_ID, logRequest.getUserId())
				.addValue(Constantes.PARAMETER_LOG_WALLET_ID, logRequest.getWalletId())
				.addValue(Constantes.PARAMETER_LOG_VALUE_NEW, logRequest.getValueNew())
				.addValue(Constantes.PARAMETER_LOG_VALUE_OLD, logRequest.getValueOld())
				.addValue(Constantes.PARAMETER_LOG_MODIFICATION_DATE, UtilAMS.formatDate(now, 3));
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_add_log");
		simpleJdbcCall.execute(paramaters);
		

	}

}
