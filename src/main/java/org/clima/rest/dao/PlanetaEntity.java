package org.clima.rest.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.clima.rest.resource.Planeta;

/**
 * Planeta entity 
 * 
 * @author John
 *
 */
@Entity
@Table(name="planeta")
public class PlanetaEntity implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the planeta */
	@Id
	@GeneratedValue
	@Column(name="planeta_id")
	private Long id;
	
	/** nombre of the planeta */
	@Column(name="planeta_nombre")
	private String nombre;
		
	/** velocidad of the planeta */
	@Column(name="planeta_vel")
	private Float velocidad;
	
	/** distancia of the planeta */
	@Column(name="planeta_dist")
	private Float distancia;
	
	public PlanetaEntity(){}
	
	public PlanetaEntity(String nombre, Float velocidad, Float distancia) {
		
		this.nombre = nombre;
		this.velocidad = velocidad;
		this.distancia = distancia;
		
	}
	
	public PlanetaEntity(Planeta planeta){
		try {
			BeanUtils.copyProperties(this, planeta);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Float velocidad) {
		this.velocidad = velocidad;
	}

	public Float getDistancia() {
		return distancia;
	}

	public void setDistancia(Float distancia) {
		this.distancia = distancia;
	}


}
