/**
 * 
 */
package cl.liberty.dao.impl;

import java.text.SimpleDateFormat;
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
import cl.liberty.dao.WalletDao;
import cl.liberty.mapper.ValidityStartMapper;
import cl.liberty.mapper.WalletCoverageMapper;
import cl.liberty.mapper.WalletHistoryMapper;
import cl.liberty.mapper.WalletMapper;
import cl.liberty.model.User;
import cl.liberty.model.ValidityStart;
import cl.liberty.model.Wallet;
import cl.liberty.model.WalletCoverage;
import cl.liberty.model.WalletHistory;
import cl.liberty.request.WalletRequest;
import cl.liberty.utils.UtilAMS;

/**
 * @author jgarrido
 *
 */

@Repository
public class WalletDaoImpl implements WalletDao {
	
	@Autowired
	Environment env;

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public Wallet getWallet(Integer policyNumber, Integer contractorCode, Integer brokerCode, String validInYears) {
		/*Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String systemDate = format.format(currentDate);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_CONTRACTOR_CODE, contractorCode)
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, brokerCode)
				.addValue(Constantes.MAP_PARAMETER_VALIDATE_IN_YEAR, validInYears)
				.addValue(Constantes.MAP_PARAMETER_SYSTEM_DATE, systemDate);

		String query = " SELECT  "
				+ "			HC.ID_CARTERA, HC.NOMBRE_CARTERA"
				+ "			, HC.NUMERO_POLIZA, HC.FECHA_VIGENCIA_INICIO"
				+ "			, HC.FECHA_VIGENCIA_FIN, HCONT.CODIGO_CONTRATANTE AS CODIGO_CONTRATANTE"
				+ "			, HCONT.DESCRIPCION AS DESCRIPCION_CONTRATANTE"
				+ "			, HCORR.CODIGO_CORREDOR AS CODIGO_CORREDOR"
				+ "			, HCORR.DESCRIPCION AS DESCRIPCION_CORREDOR"
				+ "			, HF.ID_FLUJO"
				+ "			, HF.DESCRIPCION AS DESCRIPCION_FLUJO"
				+ "			, HUS.ID_USO"
				+ "			, HUS.DESCRIPCION AS DESCRIPCION_USO"
				+ "			, ( CASE WHEN  ( (TO_DATE(:systemDate, 'YYYY-MM-DD') -  TO_DATE(TO_CHAR(HC.FECHA_VIGENCIA_FIN, 'YYYY-MM-DD'), 'YYYY-MM-DD'))> 0 ) THEN 0  ELSE 1 END ) AS ESTADO  "
				+ " 	FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA HC  "
				+ " 	INNER JOIN    " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CORREDOR HCORR ON HC.CODIGO_CORREDOR = HCORR.CODIGO_CORREDOR      "
				+ " 	INNER JOIN    " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CONTRATANTE HCONT ON HC.CODIGO_CONTRATANTE = HCONT.CODIGO_CONTRATANTE   "
				+ " 	INNER JOIN     " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_FLUJO 	HF ON HC.ID_FLUJO = HF.ID_FLUJO  "
				+ " 	INNER JOIN     " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USO HUS ON HC.ID_USO = HUS.ID_USO " + "	WHERE 1 = 1 ";

		if (policyNumber != 0) {
			query += " AND HC.NUMERO_POLIZA = :policyNumber";
		}

		if (contractorCode != 0) {
			query += "  AND HCONT.CODIGO_CONTRATANTE = :contractorCode";
		}

		if (brokerCode != 0) {
			query += " AND HCORR.CODIGO_CORREDOR = :brokerCode";
		}

		if (!"0".equals(validInYears)) {
			query += " AND  TO_CHAR((HC.FECHA_VIGENCIA_INICIO),'YYYY') = :validInYears";
		}
		System.out.println("query="+query.toLowerCase());
		return jdbc.queryForObject(query, namedParameters, new WalletMapper());
		*/
		

		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.PARAMETER_WALLET_CONTRACTOR_CODE, contractorCode)
				.addValue(Constantes.PARAMETER_WALLET_BROKER_CODE, brokerCode)
				.addValue(Constantes.PARAMETER_WALLET_VALID_IN_YEAR, validInYears);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_wallet");
		Map out = simpleJdbcCall.execute(paramaters);
		Wallet wallet = new Wallet();
		wallet.setWalletId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_ID))));
		wallet.setWalletName(String.valueOf(out.get(Constantes.PARAMETER_WALLET_NAME)));
		wallet.setPolicyNumber(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_POLICY_NUMBER))));
		wallet.setValidityStartDate(String.valueOf(out.get(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_START)));
		wallet.setValidityEndDate(String.valueOf(out.get(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_END)));
		wallet.setContractorCode(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_CONTRACTOR_CODE))));
		wallet.setContractorDescription(String.valueOf(out.get(Constantes.PARAMETER_WALLET_CONTRACTOR_DESCRIPTION)));
		wallet.setFlowId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_FLOW_ID))));
		wallet.setFlowDescription(String.valueOf(out.get(Constantes.PARAMETER_WALLET_FLOW_DESCRIPTION)));
		wallet.setUsingId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_USING_ID))));
		wallet.setUsingDescription(String.valueOf(out.get(Constantes.PARAMETER_WALLET_USING_DESCRIPTION)));
		wallet.setBrokerCode(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_BROKER_CODE))));
		wallet.setBrokerDescription(String.valueOf(out.get(Constantes.PARAMETER_WALLET_BROKER_DESCRIPTION)));
		wallet.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_WALLET_STATUS))));
		return wallet;
		
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wallet> getWallets(Integer policyNumber, Integer contractorCode, Integer brokerCode,
			String validInYears, Integer idPadre) {
		/*Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String systemDate = format.format(currentDate);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_CONTRACTOR_CODE, contractorCode)
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, brokerCode)
				.addValue(Constantes.MAP_PARAMETER_VALIDATE_IN_YEAR, validInYears)
				.addValue(Constantes.MAP_PARAMETER_SYSTEM_DATE, systemDate);
		String query = " SELECT "
				+ "			HC.ID_CARTERA"
				+ "			, HC.NOMBRE_CARTERA"
				+ "			, HC.NUMERO_POLIZA, HC.FECHA_VIGENCIA_INICIO, HC.FECHA_VIGENCIA_FIN"
				+ "			, HCONT.CODIGO_CONTRATANTE AS CODIGO_CONTRATANTE"
				+ "			, HCONT.DESCRIPCION AS DESCRIPCION_CONTRATANTE"
				+ "			, HCORR.CODIGO_CORREDOR AS CODIGO_CORREDOR, HCORR.DESCRIPCION AS DESCRIPCION_CORREDOR"
				+ "			, HF.ID_FLUJO"
				+ "			, HF.DESCRIPCION AS DESCRIPCION_FLUJO"
				+ "			, HUS.ID_USO"
				+ "			, HUS.DESCRIPCION AS DESCRIPCION_USO"
				+ " 		,( CASE WHEN  ( (TO_DATE(:systemDate, 'YYYY-MM-DD') -  TO_DATE(TO_CHAR(HC.FECHA_VIGENCIA_FIN, 'YYYY-MM-DD'), 'YYYY-MM-DD'))> 0 ) THEN 0  ELSE 1 END ) AS ESTADO   "
				+ " FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA HC"
				+ " INNER JOIN  " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CORREDOR HCORR ON HC.CODIGO_CORREDOR = HCORR.CODIGO_CORREDOR "
				+ " INNER JOIN  " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CONTRATANTE HCONT"
				+ "	ON HC.CODIGO_CONTRATANTE = HCONT.CODIGO_CONTRATANTE  INNER JOIN HIPO_FLUJO 	HF"
				+ "	ON HC.ID_FLUJO = HF.ID_FLUJO INNER JOIN HIPO_USO HUS ON HC.ID_USO = HUS.ID_USO " + "WHERE 1 = 1 ";

		if (policyNumber != 0) {
			query += " AND HC.NUMERO_POLIZA = :policyNumber ";
		}

		if (contractorCode != 0) {
			query += "  AND HCONT.CODIGO_CONTRATANTE = :contractorCode ";
		}

		if (brokerCode != 0) {
			query += " AND HCORR.CODIGO_CORREDOR = :brokerCode ";
		}

		if (!"0".equals(validInYears)) {
			query += " AND  TO_CHAR((HC.FECHA_VIGENCIA_INICIO),'YYYY') = :validInYears";
		}
		System.out.println("query="+query.toLowerCase());
		return jdbc.query(query, namedParameters, new WalletMapper());
		*/
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.PARAMETER_WALLET_CONTRACTOR_CODE, contractorCode)
				.addValue(Constantes.PARAMETER_WALLET_BROKER_CODE, brokerCode)
				.addValue(Constantes.PARAMETER_WALLET_VALID_IN_YEAR, validInYears)
				.addValue(Constantes.PARAMETER_WALLET_FATHER_ID, idPadre);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_wallets")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(Wallet.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<Wallet>) out.get(env.getProperty(Constantes.P_CURSOR)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WalletCoverage> getWalletCoverages(Integer policyNumber, Integer branchNumber, Integer idPadre) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_WALLET_BRANCH, branchNumber)
				.addValue(Constantes.MAP_PARAMETER_PROPERTY_ID_PADRE, idPadre);
		String query = " select "
				+ "			gar.cgarant		codigo_cobertura"
				+ "			, cob.tgarant    	cobertura "
				+ " 		, (CASE WHEN gar.cgarant IN (1801,1807) THEN 'Habitacional' WHEN gar.cgarant IN (1982,1983) THEN 'Comercial' ELSE 'N/A' END) uso_destino "
				+ " FROM  sisaxis.seguros   seg, sisaxis.garanseg  gar, sisaxis.garangen  cob,"
				+ " " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_property tbp,  " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_tipoproperty cbpt  WHERE 1 = 1  AND seg.ncertif   = 0  "
				+ "	AND seg.sseguro   = gar.sseguro      AND cob.cgarant   = gar.cgarant    "
				+ "	AND cbpt.id_tipoproperty   = cbpt.id_tipoproperty AND tbp.id_padre = :idPadre   AND   tbp.estado = 1  "
				+ "	AND  tbp.valor   = gar.cgarant ";

		if (policyNumber != 0) {
			query += " AND seg.npoliza = :policyNumber";
		}

		if (branchNumber != 0) {
			query += " AND seg.CRAMO = :branchNumber";
		}
		query += " GROUP BY gar.cgarant, gar.cgarant,cob.tgarant    ";
		System.out.println("query="+query.toLowerCase());
		return jdbc.query(query, namedParameters, new WalletCoverageMapper());*/
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.PARAMETER_WALLET_BRANCH_NUMBER, branchNumber)
				.addValue(Constantes.PARAMETER_WALLET_FATHER_ID, idPadre);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_wallet_coverages")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(WalletCoverage.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<WalletCoverage>) out.get(env.getProperty(Constantes.P_CURSOR)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WalletHistory> getWalletHistorys(Integer policyNumber, Integer idUser) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_USER_ID, idUser);
		String query = " SELECT "
				+ "			HL.ID_LOG, HL.VALOR_ANTIGUO, HL.VALOR_NUEVO"
				+ "			, TO_CHAR(TO_TIMESTAMP (HL.FECHA_MODIFICACION),'YYYY-MM-DD HH24:MI:SS') AS FECHA_MODIFICACION"
				+ "			, HE.ID_EVENTO, HE.NOMBRE AS NOMBRE_EVENTO"
				+ "			, HU.ID_USUARIO"
				+ "			, HU.USERNAME AS NOMBRE_USUARIO"
				+ "			, HC.NUMERO_POLIZA, HCORR.CODIGO_CORREDOR"
				+ "			, HCORR.DESCRIPCION AS DESCRIPCION_CORREDOR"
				+ "			, HCONT.CODIGO_CONTRATANTE"
				+ "			, HCONT.DESCRIPCION AS DESCRIPCION_CONTRATANTE "
				+ "	FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_LOG HL "
				+ " INNER JOIN    " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_EVENTO HE ON HL.ID_EVENTO = HE.ID_EVENTO "
				+ " INNER JOIN    " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_USUARIO HU ON HL.ID_USUARIO = HU.ID_USUARIO "
				+ " INNER JOIN     " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA HC ON HL.ID_CARTERA = HC.ID_CARTERA "
				+ " INNER JOIN   " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CORREDOR HCORR 	ON HC.CODIGO_CORREDOR = HCORR.CODIGO_CORREDOR"
				+ " INNER JOIN   " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CONTRATANTE HCONT ON HC.CODIGO_CONTRATANTE = HCONT.CODIGO_CONTRATANTE ";
		query += " WHERE 1 = 1 ";
		if (policyNumber != 0) {
			query += " AND HC.NUMERO_POLIZA = :policyNumber ";
		}

		if (idUser != 0) {
			query += "  AND HU.ID_USUARIO = :idUser ";
		}
		query += "  ORDER BY TO_CHAR(TO_TIMESTAMP (HL.FECHA_MODIFICACION),'YYYY-MM-DD HH24:MI:SS') DESC ";
		System.out.println("query="+query.toLowerCase());
		return jdbc.query(query, namedParameters, new WalletHistoryMapper());
		*/
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.PARAMETER_WALLET_USER_ID, idUser);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_wallet_historys")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(WalletHistory.class));
		Map<String, Object> out = simpleJdbcCall.execute(parameters);
		return ((List<WalletHistory>) out.get(env.getProperty(Constantes.P_CURSOR)));
	}

	@Override
	public void addWallet(WalletRequest walletRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_WALLET_NAME, walletRequest.getWalletName())
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, walletRequest.getPolicyNumber())
				.addValue(Constantes.MAP_PARAMETER_WALLET_START_DATE, walletRequest.getValidityStartDate())
				.addValue(Constantes.MAP_PARAMETER_WALLET_END_DATE, walletRequest.getValidityEndDate())
				.addValue(Constantes.MAP_PARAMETER_CONTRACTOR_CODE, walletRequest.getContractorCode())
				.addValue(Constantes.MAP_PARAMETER_FLOW_ID, walletRequest.getFlowId())
				.addValue(Constantes.MAP_PARAMETER_USING_ID, walletRequest.getUsingId())
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, walletRequest.getBrokerCode())
				.addValue(Constantes.MAP_PARAMETER_WALLET_STATUS, walletRequest.getStatus());
		String query = "INSERT INTO " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA (ID_USO, ID_FLUJO, CODIGO_CORREDOR, NOMBRE_CARTERA, NUMERO_POLIZA, FECHA_VIGENCIA_INICIO, FECHA_VIGENCIA_FIN, CODIGO_CONTRATANTE, ESTADO) "
				+ "VALUES(:usingId, :flowId, :brokerCode, :walletName, :policyNumber, to_date(:validityStartDate, 'YYYY-MM-DD HH24:MI:SS'), to_date(:validityEndDate, 'YYYY-MM-DD HH24:MI:SS'), :contractorCode, :status ) ";
		jdbc.update(query, namedParameters);
		*/
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_NAME, walletRequest.getWalletName())
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, walletRequest.getPolicyNumber())
				.addValue(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_START, walletRequest.getValidityStartDate())
				.addValue(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_END, walletRequest.getValidityEndDate())
				.addValue(Constantes.PARAMETER_WALLET_CONTRACTOR_CODE, walletRequest.getContractorCode())
				.addValue(Constantes.PARAMETER_WALLET_FLOW_ID, walletRequest.getFlowId())
				.addValue(Constantes.PARAMETER_WALLET_USING_ID, walletRequest.getUsingId())
				.addValue(Constantes.PARAMETER_WALLET_BROKER_CODE, walletRequest.getBrokerCode())
				.addValue(Constantes.PARAMETER_WALLET_STATUS, walletRequest.getStatus());
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_add_wallet");
		simpleJdbcCall.execute(parameters);
	}

	@Override
	public void editWallet(WalletRequest walletRequest) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_WALLET_ID, walletRequest.getWalletId())
				.addValue(Constantes.MAP_PARAMETER_WALLET_NAME, walletRequest.getWalletName())
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, walletRequest.getPolicyNumber())
				.addValue(Constantes.MAP_PARAMETER_WALLET_START_DATE, walletRequest.getValidityStartDate())
				.addValue(Constantes.MAP_PARAMETER_WALLET_END_DATE, walletRequest.getValidityEndDate())
				.addValue(Constantes.MAP_PARAMETER_CONTRACTOR_CODE, walletRequest.getContractorCode())
				.addValue(Constantes.MAP_PARAMETER_FLOW_ID, walletRequest.getFlowId())
				.addValue(Constantes.MAP_PARAMETER_USING_ID, walletRequest.getUsingId())
				.addValue(Constantes.MAP_PARAMETER_BROKER_CODE, walletRequest.getBrokerCode())
				.addValue(Constantes.MAP_PARAMETER_WALLET_STATUS, walletRequest.getStatus());
		String query = "UPDATE " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA SET ID_USO=:usingId, ID_FLUJO=:flowId, CODIGO_CORREDOR=:brokerCode, NOMBRE_CARTERA=:walletName, NUMERO_POLIZA=:policyNumber, FECHA_VIGENCIA_INICIO=to_date(:validityStartDate, 'YYYY-MM-DD HH24:MI:SS'), FECHA_VIGENCIA_FIN=to_date(:validityEndDate, 'YYYY-MM-DD HH24:MI:SS'), CODIGO_CONTRATANTE=:contractorCode, ESTADO=:status  WHERE ID_CARTERA=:walletId";
		jdbc.update(query, namedParameters);
		*/
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_ID, walletRequest.getWalletId())
				.addValue(Constantes.PARAMETER_WALLET_NAME, walletRequest.getWalletName())
				.addValue(Constantes.PARAMETER_WALLET_POLICY_NUMBER, walletRequest.getPolicyNumber())
				.addValue(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_START, walletRequest.getValidityStartDate())
				.addValue(Constantes.PARAMETER_WALLET_EFFECTIVE_DATE_END, walletRequest.getValidityEndDate())
				.addValue(Constantes.PARAMETER_WALLET_CONTRACTOR_CODE, walletRequest.getContractorCode())
				.addValue(Constantes.PARAMETER_WALLET_FLOW_ID, walletRequest.getFlowId())
				.addValue(Constantes.PARAMETER_WALLET_USING_ID, walletRequest.getUsingId())
				.addValue(Constantes.PARAMETER_WALLET_BROKER_CODE, walletRequest.getBrokerCode())
				.addValue(Constantes.PARAMETER_WALLET_STATUS, walletRequest.getStatus());
		
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_edit_wallet");
		simpleJdbcCall.execute(parameters);

	}

	@Override
	public void deleteWallet(Integer walletId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_WALLET_ID,
				walletId);
		String query = "DELETE FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA WHERE ID_CARTERA = :walletId";
		jdbc.update(query, namedParameters);
		*/
		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue(Constantes.PARAMETER_WALLET_ID, walletId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_delete_wallet");
		simpleJdbcCall.execute(paramaters);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validateWalletExist(Integer walletId) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Constantes.MAP_PARAMETER_WALLET_ID,
				walletId);
		String query = "SELECT COUNT(*) FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA WHERE ID_CARTERA = :walletId";
		return jdbc.queryForObject(query, namedParameters, Integer.class) != 0;
		*/
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_WALLET_ID, walletId);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_wallet_exist");
		Map out = simpleJdbcCall.execute(paramaters);
		return Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_COUNT))) != 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidityStart> getValidityStarts() {
		/*String query = ""
				+ "SELECT "
				+ "	EXTRACT(YEAR FROM TO_DATE(TO_CHAR(TO_TIMESTAMP ( (SELECT TO_CHAR(MIN(to_char(FECHA_VIGENCIA_INICIO,'YYYY-MM-DD HH24-MI-SS') )) FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA ), 'YYYY-MM-DD HH24-MI-SS'), 'YYYY-MM-DD HH24-MI-SS') ,'YYYY-MM-DD HH24-MI-SS')) +rownum-1 AS FECHA_VIGENCIA "
				+ "FROM DUAL "
				+ "CONNECT BY LEVEL <= ( SELECT TRUNC( (EXTRACT(YEAR FROM TO_DATE(TO_CHAR(TO_TIMESTAMP ((SELECT TO_CHAR(MAX(to_char(FECHA_VIGENCIA_FIN,'YYYY-MM-DD HH24-MI-SS') )) FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA), 'YYYY-MM-DD HH24-MI-SS'), 'YYYY-MM-DD HH24-MI-SS') ,'YYYY-MM-DD HH24-MI-SS')) - EXTRACT(YEAR FROM TO_DATE(TO_CHAR(TO_TIMESTAMP ((SELECT TO_CHAR(MIN(to_char(FECHA_VIGENCIA_INICIO,'YYYY-MM-DD HH24-MI-SS') )) FROM  " + env.getProperty(Constantes.ESCHEMA_DB) + ".HIPO_CARTERA), 'YYYY-MM-DD HH24-MI-SS'), 'YYYY-MM-DD HH24-MI-SS') ,'YYYY-MM-DD HH24-MI-SS')) )) +rownum  FROM DUAL ) "
				+ "ORDER BY FECHA_VIGENCIA ASC";
		System.out.println("query="+query.toLowerCase());
		return jdbc.query(query, new ValidityStartMapper());
		*/
	
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_validity_starts")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(ValidityStart.class));
		Map<String, Object> out = simpleJdbcCall.execute();
		return ((List<ValidityStart>) out.get(env.getProperty(Constantes.P_CURSOR)));
	}

}
