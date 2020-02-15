/**
 * 
 */
package cl.liberty.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import cl.liberty.constantes.Constantes;
import cl.liberty.dao.PolicyDao;
import cl.liberty.mapper.PolicyCoverageMapper;
import cl.liberty.mapper.PolicyMapper;
import cl.liberty.model.Policy;
import cl.liberty.model.PolicyCoverage;

/**
 * @author jgarrido
 *
 */

@Repository
public class PolicyDaoImpl implements PolicyDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Override
	public Policy getPolicy(int policyNumber, int branchNumber) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER_BRANCH, branchNumber);

		String query = " " + " SELECT NUMERO_POLIZA, CODIGO_RAMO, RAMO, CODIGO_CORREDOR, CODIGO_PERSONA_CORREDOR, RUT_CORREDOR, DESCRIPCION_CORREDOR, CODIGO_PERSONA_CONTRATANTE, RUT_CONTRATANTE, DESCRIPCION_CONTRATANTE, VIGENCIA_INICIO, VIGENCIA_FIN " + 
				" FROM HIPO_POL seg"
				+ " WHERE 1 = 1 " ;

		if (policyNumber != 0) {
			query += " AND seg.NUMERO_POLIZA = :policyNumber";
		}

		if (branchNumber != 0) {
			query += " AND seg.CODIGO_RAMO = :branchNumber";
		}

		query += " AND  ROWNUM  <= 1";
		System.out.println("query="+query);
		return jdbc.queryForObject(query, namedParameters, new PolicyMapper());
	}

	@Override
	public List<Policy> getPolicys(int policyNumber, int branchNumber) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber)
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER_BRANCH, branchNumber);

		String query = " " + " SELECT NUMERO_POLIZA, CODIGO_RAMO, RAMO, CODIGO_CORREDOR, CODIGO_PERSONA_CORREDOR, RUT_CORREDOR, DESCRIPCION_CORREDOR, CODIGO_PERSONA_CONTRATANTE, RUT_CONTRATANTE, DESCRIPCION_CONTRATANTE, VIGENCIA_INICIO, VIGENCIA_FIN" +
				" FROM HIPO_POL seg "
				+ " WHERE 1 = 1 ";
		if (policyNumber != 0) {
			query += " AND seg.NUMERO_POLIZA = :policyNumber";
		}

		if (branchNumber != 0) {
			query += " AND seg.CODIGO_RAMO = :branchNumber";
		}

		query += " AND  ROWNUM  <= 1";
		System.out.println("query="+query);
		return jdbc.query(query, namedParameters, new PolicyMapper());
	}

	@Override
	public List<PolicyCoverage> getPolicyCoverages(int policyNumber, int branchNumber, int idPadre) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("policyNumber", policyNumber)
				.addValue("branchNumber", branchNumber).addValue("idPadre", idPadre);
		String query = "SELECT \"cgarant\" AS codigo_cobertura, \"tgarant\" AS cobertura , (CASE WHEN gar.\"cgarant\" IN (1801,1807) THEN 'Habitacional' WHEN gar.\"cgarant\" IN (1982,1983) THEN 'Comercial' ELSE 'N/A' END) uso_destino" + 
				" FROM HIPO_POL_COBERTURA gar " 
				+ "	WHERE 1 = 1 ";

		if (policyNumber != 0) {
			query += " AND gar.NUMERO_POLIZA = :policyNumber ";
		}

		if (branchNumber != 0) {
			query += " AND gar.CODIGO_RAMO = :branchNumber ";
		}

		//query += " GROUP BY gar.\"cgarant\", gar.\"tgarant\"    ";
		System.out.println("query="+query);
		return jdbc.query(query, namedParameters, new PolicyCoverageMapper());
	}

	@Override
	public List<Policy> getPolicyNonMortgages(int policyNumber) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Constantes.MAP_PARAMETER_POLICY_NUMBER, policyNumber);

		String query = " " + " SELECT NUMERO_POLIZA, CODIGO_RAMO, RAMO, CODIGO_CORREDOR, CODIGO_PERSONA_CORREDOR, RUT_CORREDOR, DESCRIPCION_CORREDOR, CODIGO_PERSONA_CONTRATANTE, RUT_CONTRATANTE, DESCRIPCION_CONTRATANTE, VIGENCIA_INICIO, VIGENCIA_FIN " +
				" FROM HIPO_POL seg "
				+ " WHERE 1 = 1 ";
		if (policyNumber != 0) {
			query += " AND seg.NUMERO_POLIZA = :policyNumber";
		}

		query += " AND seg.CODIGO_RAMO != 20";

		query += " AND  ROWNUM  <= 1";
		System.out.println("query="+query);
		return jdbc.query(query, namedParameters, new PolicyMapper());
	}

}
