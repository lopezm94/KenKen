import java.io.IOException;

/**
*@author Marc Ortiz
**/
public class GestioPartida{
	private long tstart;
	private Partida p;
	private String nomUser;
	private String nomkenken;
	private void ompleTauler(){
		//Omplim el tauler amb els valors de la partida anterior
		TableroH tauler = p.getTauler();
		int mida = tauler.size();
		int caselles[][] = GestioDadesH.getPartidaValues(p.getNomPartida(),nomUser,mida);
		for(int i=0; i < mida; ++i){
			for(int j=0; j < mida; ++j){
				tauler.setCasillaVal(i, j, caselles[i][j]);
			}
		}
	}
	
	public GestioPartida(String nompartida, Boolean novaPartida, String nomkenken,String nomUser) throws FicheroNoExiste{
		//LOADING GAME
		this.nomUser = nomUser;
		if(novaPartida){
			this.nomkenken = nomkenken;
			p = new Partida(nompartida,nomkenken);
			p.setTime(0);
			try {
				p.setTauler(GestioTauler.creaTauler(nomkenken));
			} catch (FicheroNoExiste e) {
				throw e;
			}
		}else{
			//carrega la partida des de memoria
			String st[] = GestioDadesH.getPartidaHeaderInfo(nompartida,nomUser,0);
			String st1[] = GestioDadesH.getPartidaHeaderInfo(nompartida,nomUser,1);
			this.nomkenken = st1[0];
			p = new Partida(nompartida,this.nomkenken);
			p.setTime(Integer.parseInt(st[0]));
			try {
				p.setTauler(GestioTauler.creaTauler(st1[0]));
			} catch (FicheroNoExiste e) {
				throw e;
			}
			ompleTauler();
		}
	}
	
	public void TancarPartida(){
		p = null;
		nomUser = null;
		nomkenken = null;
	}
	
	public Partida getPartida(){
		return p;
	}	
	
	public void start(){
		tstart = System.currentTimeMillis();
	}

	public long getTime(){
		long time_end = System.currentTimeMillis();
		long timeextra = p.getTime();
		timeextra  += (time_end/1000 - tstart/1000);
		return timeextra;
	}
	
	public String getDiff(){
		return KenkenHandler.getDifficulty(p.getTauler());
	}
	
	public int getVacias(){
		return KenkenHandler.getEmptyCells(p.getTauler());
	}
	
	public void setValue(int x,int y,int value){
		p.getTauler().setCasillaVal(x,y,value);
	}
	
	public int getValue(int x,int y){
		return p.getTauler().getCasillaVal(x,y);
	}
	
	public int getSol(int x,int y){
		return p.getTauler().getCasillaSol(x, y);
	}

	public Boolean fija(int x, int y){
		return p.getTauler().casillaIsFija(x, y);
	}
	
	public int getTamany(){
		return p.getTauler().getFiles();
	}
	
	public int getNumAreas(){
		return p.getTauler().getNumAreas();
	}
	
	public int getAreaID(int x, int y){
		return p.getTauler().getAreaID(x, y);
	}
	
	public char getOperacio(int x, int y){
		return p.getTauler().getArea(x, y).get_operacio();
	}
	
	public char getOperacioA(int x){
		return p.getTauler().getArea(x).get_operacio();
	}
	
	public int getResultatArea(int x, int y){
		return p.getTauler().getArea(x, y).get_resultat();
	}
	
	public int getResultatAreaA(int x){
		return p.getTauler().getArea(x).get_resultat();
	}
	
	public boolean check(){
		return p.getTauler().tableroCheck() && p.getTauler().numerosCheck();
	}
	
	public void neteja(){
		for(int i=0; i < getTamany(); ++i)
			for(int j=0; j < getTamany(); ++j)
				setValue(i,j,-1);
	}
	
	public void saveGame(Perfil p1){
		p1.get_partida().setTime((int)getTime());
		start();
		GestioDadesH.guardarPartida(p1,nomkenken);
	}
	
	public void deleteGame() throws IOException,FicheroNoExiste{
		try{
			Gestio_Dades.Borrar_archivo(p.getNomPartida(),"./Games/"+nomUser);
		}catch(IOException e){
			throw e;
		}catch(FicheroNoExiste f){
			throw f;
		}
	}
}
