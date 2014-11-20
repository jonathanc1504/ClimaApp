package org.clima.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;

import org.clima.rest.dao.ClimaDao;
import org.clima.rest.dao.ClimaEntity;
import org.clima.rest.errorhandling.AppException;
import org.clima.rest.errorhandling.CustomReasonPhraseException;
import org.clima.rest.filters.AppConstants;
import org.clima.rest.resource.Clima;
import org.clima.rest.resource.Planeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ClimaServiceImpl implements ClimaService {

	@Autowired
	ClimaDao climaDao;
		
	private Float pendienteDiaAnterior=new Float(0);


	public Float getPendienteDiaAnterior() {
		return pendienteDiaAnterior;
	}

	public void setPendienteDiaAnterior(Float pendienteDiaAnterior) {
		this.pendienteDiaAnterior = pendienteDiaAnterior;
	}

	@Override
	@Transactional
	public Integer createClima(Clima clima) throws AppException {
		return climaDao.createClima(new ClimaEntity(clima));
	}

	@Override
	public List<Clima> getClimasByEstado(String estado) throws AppException {
		List<ClimaEntity> climasByEstado = climaDao.getClimasByEstado(estado);
		
		return getClimasFromEntities(climasByEstado);
	}

	@Override
	public Clima getClimaByDia(Integer dia) throws AppException {
		ClimaEntity clima = climaDao.getClimaByDia(dia);
		if(clima == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"La informacion de clima para el dia " + dia + " no se encuentra en la base de datos.",
					"Verificar que la informacion para el dia " + dia + " existe fue calculada correctamente.",
					AppConstants.BLOG_POST_URL);			
		}
		
		return new Clima(climaDao.getClimaByDia(dia));
	}

	@Override
	@Transactional
	public void deleteClimas() {
		climaDao.deleteClimas();
	}
	
	private List<Clima> getClimasFromEntities(List<ClimaEntity> climaEntities) {
		List<Clima> response = new ArrayList<Clima>();
		for(ClimaEntity climaEntity : climaEntities){
			response.add(new Clima(climaEntity));					
		}
		
		return response;
	}
	
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException {		
		throw new CustomReasonPhraseException(4000, "message attached to the Custom Reason Phrase Exception");		
	}
	

	private boolean isSequia(int dia, Planeta ferengi, Planeta betasoide, Planeta vulcano){
		boolean isSequia=false;
		Float anguloFerengi = ferengi.getVelocidad() * dia;
		Float anguloBetasoide = betasoide.getVelocidad() * dia;
		Float anguloVulcano = vulcano.getVelocidad() * dia;
		System.out.println("isSequia : dia" +dia);
		System.out.println("isSequia : anguloFerengi: " +anguloFerengi);
		System.out.println("isSequia : anguloBetasoide: " +anguloBetasoide);
		System.out.println("isSequia : anguloVulcano: " +anguloVulcano);
		Float anguloResult1= (anguloFerengi - anguloBetasoide)/180;
		Float anguloResult2= (anguloBetasoide - anguloVulcano)/180;
		System.out.println("isSequia : anguloResult1:" +anguloResult1 + " anguloResult2:"+anguloResult2);
		if ((anguloResult1 == Math.round(anguloResult1))
				&& (anguloResult2 == Math.round(anguloResult2))) {
			isSequia=true;
			System.out.println(" Hay Sequia : dia" +dia);
		}
		return isSequia;
	}

	private boolean isLluvia(int dia, Planeta ferengi, Planeta betasoide, Planeta vulcano, HashMap<Integer, Float>  mapaLluvias){
		boolean isLluvia=false;
		System.out.println("isLluvia : dia" +dia);
		Float anguloFerengi = ferengi.getVelocidad() * dia;
		anguloFerengi = calculoAnguloReducido(anguloFerengi);
		System.out.println("isLluvia : angulo reducido : anguloFerengi: " +anguloFerengi);
		Float anguloBetasoide = betasoide.getVelocidad() * dia;
		anguloBetasoide = calculoAnguloReducido(anguloBetasoide);
		System.out.println("isLluvia : angulo reducido : anguloBetasoide: " +anguloBetasoide);
		Float anguloVulcano = vulcano.getVelocidad() * dia;
		anguloVulcano = calculoAnguloReducido(anguloVulcano);
		System.out.println("isLluvia : angulo reducido : anguloVulcano: " +anguloVulcano);

		Float anguloResult1= Math.abs((anguloFerengi - anguloBetasoide));
		System.out.println("isLluvia : anguloResult1:" +anguloResult1);
		Float anguloResult2= Math.abs((anguloFerengi - anguloVulcano));
		System.out.println("isLluvia : anguloResult2:" +anguloResult2);
		Float anguloResult3= Math.abs((anguloBetasoide - anguloVulcano));
		System.out.println("isLluvia : anguloResult3:" +anguloResult3);
		
		int comparacion1= Float.compare(anguloResult1,new Float("180"));
		int comparacion2= Float.compare(anguloResult2,new Float("180"));
		int comparacion3= Float.compare(anguloResult3,new Float("180"));
		System.out.println("isLluvia : comparacion1:" +comparacion1);
		System.out.println("isLluvia : comparacion2:" +comparacion2);
		System.out.println("isLluvia : comparacion3:" +comparacion3);

		if(comparacion1<0 && comparacion2<0 && comparacion3<0){
			isLluvia=false;
		}else{
			isLluvia=true;
			System.out.println(" Hay Lluvia : dia" +dia);
			mapaLluvias.put(new Integer(dia), calculoCondicionPicoLluvia(dia, ferengi, betasoide, vulcano));
		}
		return isLluvia;
	}

	private Float calculoCondicionPicoLluvia(int dia, Planeta ferengi,
			Planeta betasoide, Planeta vulcano) {
		Float anguloFerengi = ferengi.getVelocidad() * dia;
		Float anguloBetasoide = betasoide.getVelocidad() * dia;
		Float anguloVulcano = vulcano.getVelocidad() * dia;

		double senoFerengi = Math.sin(Math.toRadians(anguloFerengi.floatValue()));
		double senoBetasoide = Math.sin(Math.toRadians(anguloBetasoide.floatValue()));
		double senoVulcano = Math.sin(Math.toRadians(anguloVulcano.floatValue()));
		System.out.println("calculoCondicionPicoLluvia : senoFerengi: " +senoFerengi + " senoBetasoide: " + senoBetasoide + " senoVulcano:"+senoVulcano);
		double cosenoFerengi = Math.cos(Math.toRadians(anguloFerengi.floatValue()));
		double cosenoBetasoide = Math.cos(Math.toRadians(anguloBetasoide.floatValue()));
		double cosenoVulcano = Math.cos(Math.toRadians(anguloVulcano.floatValue()));
		System.out.println("calculoCondicionPicoLluvia : cosenoFerengi: " +cosenoFerengi + " cosenoBetasoide: " + cosenoBetasoide + "cosenoVulcano:"+cosenoVulcano);
		
		Float valorYferengi= round(new Float(senoFerengi) * ferengi.getDistancia(),2);
		Float valorYbetasoide= round(new Float(senoBetasoide) * betasoide.getDistancia(),2);
		Float valorYvulcano= round((new Float(senoVulcano) * vulcano.getDistancia()),2);
		System.out.println("calculoCondicionPicoLluvia : valorYferengi:" +valorYferengi + " valorYbetasoide:" + valorYbetasoide + "valorYvulcano:"+valorYvulcano);
		Float valorXferengi= round(new Float(cosenoFerengi) * ferengi.getDistancia(),2);
		Float valorXbetasoide= round(new Float(cosenoBetasoide) * betasoide.getDistancia(),2);
		Float valorXvulcano= round(new Float(cosenoVulcano) * vulcano.getDistancia(),2);
		System.out.println("calculoCondicionPicoLluvia : valorXferengi:" +valorXferengi + " valorXbetasoide:" + valorXbetasoide + "valorXvulcano:"+valorXvulcano);
		double argumento1= Math.pow((valorYbetasoide-valorYvulcano), 2);
		double argumento2= Math.pow((valorXbetasoide-valorXvulcano), 2);
		double argumento3= Math.pow((valorYbetasoide-valorYferengi), 2);		
		double argumento4= Math.pow((valorXbetasoide-valorXferengi), 2);	
		double argumento5= Math.pow((valorYvulcano-valorYferengi), 2);	
		double argumento6= Math.pow((valorXvulcano-valorXferengi), 2);	
		
		double perimetro1=Math.sqrt(argumento1+argumento2);
		double perimetro2=Math.sqrt(argumento3+argumento4);
		double perimetro3=Math.sqrt(argumento5+argumento6);
		
		double perimetro=perimetro1+perimetro2+perimetro3;
		Float perimetroTotal=new Float(perimetro);
		return round(perimetroTotal,2);
	}

	private Float calculoAnguloReducido(Float angulo) {
		if(Math.abs(angulo) >=360){
			Float anguloReducido = Math.abs(angulo);
			while (anguloReducido>=360){
				anguloReducido=anguloReducido-360;
			}
			angulo=anguloReducido;
		}
		return angulo;
	}
	
	private boolean isCondicionesOptimas(int dia, Planeta ferengi, Planeta betasoide, Planeta vulcano,HashMap<Integer, Float> mapaCondicionesPendientes){
		System.out.println("isCondicionesOptimas : dia" +dia);

		boolean isCondicOptimas=false;
		Float condicionDiaAnterior=mapaCondicionesPendientes.get(new Integer(dia-1));
		Float condicionDiaActual=calculoCondicionPendiente(dia,ferengi, betasoide, vulcano);
		System.out.println("isCondicionesOptimas : condicionDiaAnterior: " +condicionDiaAnterior + " condicionDiaActual: "+condicionDiaActual);
		if(((condicionDiaActual*condicionDiaAnterior)<0)
				&& (Math.abs(condicionDiaActual*condicionDiaAnterior)<1)){
			isCondicOptimas=true;
			System.out.println("Hay Condiciones optimas : dia" +dia);
		}
		return isCondicOptimas;
	}

	private Float calculoCondicionPendiente(int dia, Planeta ferengi, Planeta betasoide,
			Planeta vulcano) {
		Float anguloFerengi = ferengi.getVelocidad() * dia;
		Float anguloBetasoide = betasoide.getVelocidad() * dia;
		Float anguloVulcano = vulcano.getVelocidad() * dia;

		double senoFerengi = Math.sin(Math.toRadians(anguloFerengi.floatValue()));
		double senoBetasoide = Math.sin(Math.toRadians(anguloBetasoide.floatValue()));
		double senoVulcano = Math.sin(Math.toRadians(anguloVulcano.floatValue()));
		System.out.println("calculoCondicionPendiente : senoFerengi: " +senoFerengi + " senoBetasoide: " + senoBetasoide + " senoVulcano:"+senoVulcano);
		double cosenoFerengi = Math.cos(Math.toRadians(anguloFerengi.floatValue()));
		double cosenoBetasoide = Math.cos(Math.toRadians(anguloBetasoide.floatValue()));
		double cosenoVulcano = Math.cos(Math.toRadians(anguloVulcano.floatValue()));
		System.out.println("calculoCondicionPendiente : cosenoFerengi: " +cosenoFerengi + " cosenoBetasoide: " + cosenoBetasoide + "cosenoVulcano:"+cosenoVulcano);
		
		Float valorYferengi= round(new Float(senoFerengi) * ferengi.getDistancia(),2);
		Float valorYbetasoide= round(new Float(senoBetasoide) * betasoide.getDistancia(),2);
		Float valorYvulcano= round((new Float(senoVulcano) * vulcano.getDistancia()),2);
		System.out.println("calculoCondicionPendiente : valorYferengi:" +valorYferengi + " valorYbetasoide:" + valorYbetasoide + "valorYvulcano:"+valorYvulcano);
		Float valorXferengi= round(new Float(cosenoFerengi) * ferengi.getDistancia(),2);
		Float valorXbetasoide= round(new Float(cosenoBetasoide) * betasoide.getDistancia(),2);
		Float valorXvulcano= round(new Float(cosenoVulcano) * vulcano.getDistancia(),2);
		System.out.println("calculoCondicionPendiente : valorXferengi:" +valorXferengi + " valorXbetasoide:" + valorXbetasoide + "valorXvulcano:"+valorXvulcano);
		
		int condicionCero1=Float.compare(valorXferengi,new Float("0"));
		int condicionCero2=Float.compare(valorXvulcano,new Float("0"));
		int condicionCero3=Float.compare(valorXbetasoide,new Float("0"));
		System.out.println("calculoCondicionPendiente : condicionCero1: " +condicionCero1 + " condicionCero2: " +condicionCero2 + " condicionCero3: " +condicionCero3);
		Float pendiente1=new Float(0);
		Float pendiente2=new Float(0);
		if(((condicionCero1>0 || condicionCero1<0))
				&&((condicionCero2>0 || condicionCero2<0))){
			pendiente1= round(((valorYferengi - valorYvulcano)/(valorXferengi - valorXvulcano)),2);
		}
		System.out.println("calculoCondicionPendiente : pendiente1:" +pendiente1);
		if(((condicionCero3>0 || condicionCero3<0))
				&&((condicionCero2>0 || condicionCero2<0))){
			pendiente2= round(((valorYvulcano - valorYbetasoide)/(valorXvulcano - valorXbetasoide)),2);
		}
		System.out.println("calculoCondicionPendiente : pendiente2:" +pendiente2);

		Float condicionPendiente=round(pendiente1-pendiente2,2);
		return condicionPendiente;
	}

	@Override
	public void calculateClima() throws AppException{
		try{
			Planeta ferengi=new Planeta("Ferengi",new Float("-1"),new Float("500"));
			Planeta betasoide=new Planeta("Betasoide",new Float("-3"),new Float("2000"));
			Planeta vulcano=new Planeta("Vulcano",new Float("5"),new Float("1000"));
			Integer dias=3650;
			HashMap<Integer, Float> mapaCondicionesPendientes= new HashMap<Integer, Float>();
			mapaCondicionesPendientes.put(new Integer(0), new Float(0));
			
			HashMap<Integer, Float> mapaLluvias= new HashMap<Integer, Float>();
			
				 for(int i=1; i<=dias; i++){
					 Clima clima = new Clima();
					 clima.setDia(i);
					 mapaCondicionesPendientes.put(new Integer(i), calculoCondicionPendiente(i,ferengi, betasoide, vulcano));
					 System.out.print("COMIENZO Calculo dia:"+i);
					 if(isSequia(i, ferengi, betasoide, vulcano)){
						 clima.setEstado("SEQUIA");
					 }else if (isCondicionesOptimas(i, ferengi, betasoide, vulcano,mapaCondicionesPendientes)){
						 clima.setEstado("CONDICIONES OPTIMAS");
					 }else if (isLluvia(i, ferengi, betasoide, vulcano,mapaLluvias)){
						 clima.setEstado("LLUVIA");
						// if(climaPicoLluvia.)
						 
					 }else{
						 clima.setEstado("SOLEADO");
					 }
					 System.out.print("call createClima:"+clima);
					 this.createClima(clima);
					 System.out.print("After createClima:"+clima);
					 System.out.print("FIN Calculo dia:"+i);
				 }
				 System.out.print("Calculo pico de lluvias");
				 calculoPicoLluvia(mapaLluvias);
			
		}catch (Exception ex){
			System.out.print("calculateClima Exception:"+ex.getMessage());
		}
		
	}
	
	
	private void calculoPicoLluvia(HashMap<Integer, Float> mapaLluvias){
		Entry<Integer,Float> maxEntry = null;
		for(Entry<Integer,Float> entry : mapaLluvias.entrySet()) {
		    if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
		        maxEntry = entry;
		    }
		}
		try{
			Clima climaActual= this.getClimaByDia(maxEntry.getKey());
			System.out.print("calculoPicoLluvia dia:"+maxEntry.getKey());
			climaActual.setEstado("PICO LLUVIA");
			this.updateClima(climaActual);
		}catch (Exception ex){
			System.out.print("calculoPicoLluvia Exception:"+ex.getMessage());
		}
		
		
	}
	 private static float round(float d, int decimalPlace) {
	        BigDecimal bd = new BigDecimal(Float.toString(d));
	        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	        return bd.floatValue();
	    }

		@Transactional
		private void updateClima(Clima clima) throws AppException {
			climaDao.updateClima(new ClimaEntity(clima));
		}


	
}
