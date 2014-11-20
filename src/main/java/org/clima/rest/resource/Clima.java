package org.clima.rest.resource;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;
import org.clima.rest.dao.ClimaEntity;

/**
 * Clima resource placeholder for json/xml representation 
 * 
 * @author John
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Clima implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id  */
	@XmlElement(name = "id")	
	private Integer id;
	
	/** estado clima */
	@XmlElement(name = "estado")	
	private String estado;
	
	/** dia clima */
	@XmlElement(name = "dia")	
	private Integer dia;
		

	public Clima(ClimaEntity climaEntity){
		try {
			BeanUtils.copyProperties(this, climaEntity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Clima(String estado, Integer dia) {
		
		this.estado = estado;
		this.dia = dia;
		
	}
	
	public Clima(){}

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
