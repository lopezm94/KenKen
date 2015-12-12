/**
*@version 1.0
*@author Marc Ortiz
*/
public class Partida{
	private String nomPartida;
	private int time;
	private TableroH taulerPartida;
	private String nomkenken;
	public Partida(String nomPartida, String username,String nomkenken){
		this.nomPartida = nomPartida;
		this.time = 0;
		this.nomkenken = nomkenken;
		taulerPartida=null;
	}
	public String getNomKenken(){
		return nomkenken;
	}
	public String getNomPartida(){
		return nomPartida;
	}
	public int getTime(){
		return time;
	}
	public void setTime(int time){
		this.time = time;
	}
	public void setTauler(TableroH t){
		taulerPartida = t;
	}
	public TableroH getTauler(){
		return taulerPartida;
	}
}
