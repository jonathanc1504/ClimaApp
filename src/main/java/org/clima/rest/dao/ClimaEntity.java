package org.clima.rest.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.clima.rest.resource.Clima;

/**
 * Clima entity 
 * 
 * @author John
 *
 */
@Entity
@Table(name="clima",schema="clima")
public class ClimaEntity implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the clima */
	@Id
	@GeneratedValue
	@Column(name="clima_id")
	private Integer id;
	
	/** estado of the clima */
	@Column(name="clima_estado")
	private String estado;
		
	/** link of the planeta on planetapedia.org */
	@Column(name="clima_dia")
	private Integer dia;
	
	
	public ClimaEntity(){}
	
	public ClimaEntity(String estado, Integer dia) {
		
		this.estado = estado;
		this.dia = dia;
		
	}
	
	public ClimaEntity(Clima clima){
		try {
			BeanUtils.copyProperties(this, clima);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
		

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}


}
