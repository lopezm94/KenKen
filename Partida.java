package domini;

public class Partida{
	private String nomPartida;
	private int time;
	private String dificultat;
	Tablero taulerPartida;
	public Partida(String nomPartida, String username){
		this.nomPartida = nomPartida;
		this.time = 0;
		dificultat = "";
		taulerPartida=null;
	}
	public String getNomPartida(){
		return nomPartida;
	}
	public String getDificultat(){
		return dificultat;
	}
	public int getTime(){
		return time;
	}
	public void setTime(int time){
		this.time = time;
	}
	public void setDificultat(String dificultat){
		this.dificultat = dificultat; //enumtype
	}
	public void setTauler(Tablero t){
		taulerPartida = t;
	}
	public Tablero getTauler(){
		return taulerPartida;
	}
}
