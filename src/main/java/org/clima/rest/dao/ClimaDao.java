package org.clima.rest.dao;

import java.util.List;


/**
 * @author John
 *
 */
public interface ClimaDao {
	
	/**
	 * @param orderByDay
	 * @param estado
	 * @return
	 */
	public List<ClimaEntity> getClimasByEstado(String estado);

	/**
	 * Returns a clima given its dia
	 * 
	 * @param dia
	 * @return
	 */
	public ClimaEntity getClimaByDia(Integer dia);
	

	/**
	 * @param clima
	 * @return
	 */
	public Integer createClima(ClimaEntity clima);

	/** removes all climas */
	public void deleteClimas();

	public void updateClima(ClimaEntity clima);

}
