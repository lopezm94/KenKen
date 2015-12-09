/**
*@author Marc Ortiz
**/
public class GestioPartida extends Transaction{
	private long tstart;
	private Partida p;
	private String nomUser;
	private String nomkenken;
	private void ompleTauler(){
		//Omplim el tauler amb els valors de la partida anterior
		TableroH tauler = p.getTauler();
		int mida = tauler.size();
		int caselles[][] = dataEngine.getPartidaValues(nomficher,nomUser,mida);
		for(int i=0; i < mida; ++i){
			for(int j=0; j < mida; ++j){
				tauler.setCasillaVal(i, j, caselles[i][j]);
			}
		}
	}
	public GestioPartida(String nompartida, Boolean novaPartida, String nomkenken,String nomUser){
		//LOADING GAME
		this.nomUser = nomUser;
		this.nomkenken = nomkenken;
		GestioTauler ceator = new GestioTauler();
		if(novaPartida){
			p = new Partida(nompartida);
			p.setTime(0);
			p.setTauler(creator.creaTauler(nomkenken));
			//inicialitzar partida al sistema de dades
			dataEngine.guardarPartida(p,nomkenken);
		}else{
			//carrega la partida des de memòria
			String st[] = dataEngine.getPartidaHeaderInfo(nompartida,nomUser,0);
			p.setTime(Integer.parseInt(st[0]));
			String st1[] = dataEngine.getPartidaHeaderInfo(nompartida,nomUser,1);
			p.setTauler(creator.creaTauler(st1[0]));
			ompleTauler();
		}
	}
	public void start(){
		tstart = System.currentTimeMillis();
	}
	public void setValue(x,y,value){
		p.getTauler().setCasillaVal(x,y,valor);
	}
	public int getValue(x,y){
		return p.getTauler().getCasillaVal(x,y);
	}
	public long getTime(){
		long time_end = System.currentTimeMillis();
		long timeextra = p.getTime();
		timeextra  += (time_end/1000 - tstart/1000);
		return timeextra;
	}
	public boolean check(){
		return partida.getTauler().tableroCheck() && partida.getTauler().numerosCheck();
	}
	public void saveGame(){
		dataEngine.guardarPartida(p,nomkenken);
	}	
	public void deleteGame() throws IOException,FicheroNoExiste{
		try{
			dataEngine.Borrar_archivo("./"+nomUser,nompartida);
		}catch(IOException e){
			throw e;
		}catch(FicheroNoExiste f){
			throw f;
		}
	}
}
