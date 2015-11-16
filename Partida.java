
/**
*@version 1.0
*@author Marc Ortiz
*/
public class Partida{
	private String nomPartida;
	private int time;
	private TableroH taulerPartida;
	public Partida(String nomPartida, String username){
		this.nomPartida = nomPartida;
		this.time = 0;
		taulerPartida=null;
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
