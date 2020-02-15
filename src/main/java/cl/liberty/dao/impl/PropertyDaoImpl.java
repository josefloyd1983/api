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
import cl.liberty.dao.PropertyDao;
import cl.liberty.model.Property;

/**
 * @author jgarrido
 *
 */

@Repository
public class PropertyDaoImpl implements PropertyDao {
	
	@Autowired
	Environment env;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Property getProperty(String name) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name",
				name);
		String query = ""
				+ " SELECT"
				+ " 	tbp.id_property"
				+ "		, tbp.id_pais"
				+ "		, tbp.id_tipoproperty"
				+ "		, tbp.nombre"
				+ "		, tbp.descripcion"
				+ "		, tbp.valor"
				+ "		, tbp.estado"
				+ "		, tbp.id_padre"
				+ " FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_property tbp"
				+ " INNER JOIN " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais cbs ON cbs.id_pais = tbp.id_pais"
				+ " WHERE 1 = 1"
				+ " AND tbp.id_tipoproperty > 0"
				+ " AND tbp.nombre = :name"
				+ " AND tbp.estado = 1"
				+ " ORDER BY tbp.id_padre ASC";
		return jdbc.queryForObject(query, namedParameters, new PropertyMapper());
		*/
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_PROPERYS_NAME, name);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_property");
		Map out = simpleJdbcCall.execute(paramaters);
		Property property = new Property();
		property.setDescription(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_DESCRIPTION)));
		property.setName(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_NAME)));
		property.setPadreId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_FAHTER_ID))));
		property.setPaisId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_COUNTRY_ID))));
		property.setPropertyId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_ID))));
		property.setStatus(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_STATUS))));
		property.setTipoPropertyId(Integer.parseInt(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_TYPE))));
		property.setValue(String.valueOf(out.get(Constantes.PARAMETER_PROPERYS_VALUE)));
		return property;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getPropertys(Integer idPadre) {
		/*SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("idPadre",
				idPadre);
		String query = ""
				+ " SELECT"
				+ " 	tbp.id_property"
				+ "		, tbp.id_pais"
				+ "		, tbp.id_tipoproperty"
				+ "		, tbp.nombre"
				+ "		, tbp.descripcion"
				+ "		, tbp.valor"
				+ "		, tbp.estado"
				+ "		, tbp.id_padre"
				+ " FROM " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_property tbp"
				+ " INNER JOIN " + env.getProperty(Constantes.ESCHEMA_DB) + ".hipo_pais cbs ON cbs.id_pais = tbp.id_pais"
				+ " WHERE 1 = 1"
				+ " AND tbp.id_tipoproperty > 0"
				+ " AND tbp.id_padre = :idPadre"
				+ " AND tbp.estado = 1"
				+ " ORDER BY tbp.id_padre ASC";
		return jdbc.query(query, namedParameters, new PropertyMapper());
		*/
		
		
		SqlParameterSource paramaters = new MapSqlParameterSource().addValue(Constantes.PARAMETER_PROPERYS_FAHTER_ID,
				idPadre);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(env.getProperty(Constantes.ESCHEMA_DB))
				.withCatalogName(env.getProperty(Constantes.ESCHEMA_PACKAGE)).withProcedureName("p_propertys")
				.returningResultSet(env.getProperty(Constantes.P_CURSOR),
						BeanPropertyRowMapper.newInstance(Property.class));
		Map<String, Object> out = simpleJdbcCall.execute(paramaters);
		return ((List<Property>) out.get(env.getProperty(Constantes.P_CURSOR)));
		
	}
	
}
