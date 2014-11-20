package org.clima.rest.resource;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;
import org.clima.rest.dao.PlanetaEntity;

/**
 * Planeta resource placeholder for json/xml representation 
 * 
 * @author John
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Planeta implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the planeta */
	@XmlElement(name = "id")	
	private Long id;
	
	/** title of the planeta */
	@XmlElement(name = "nombre")	
	private String nombre;
		
	/** velocidad of the planeta */
	@XmlElement(name = "velocidad")	
	private Float velocidad;
	
	/** distancia of the planeta */
	@XmlElement(name = "distancia")	
	private Float distancia;
	

	public Planeta(PlanetaEntity planetaEntity){
		try {
			BeanUtils.copyProperties(this, planetaEntity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Planeta(String nombre, Float velocidad, Float distancia) {
		
		this.nombre = nombre;
		this.velocidad = velocidad;
		this.distancia = distancia;
		
	}
	
	public Planeta(){}

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
