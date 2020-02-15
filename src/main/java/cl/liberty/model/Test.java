/**
 * 
 */
package cl.liberty.model;

/**
 * @author jgarrido
 *
 */
public class Test {

	private Long codigoCorredor;
	private String descripcion;
	
	@Override
	public String toString() {
		return "Test [codigoCorredor=" + codigoCorredor + ", descripcion=" + descripcion + "]";
	}

	public Long getCodigoCorredor() {
		return codigoCorredor;
	}

	public void setCodigoCorredor(Long codigoCorredor) {
		this.codigoCorredor = codigoCorredor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
