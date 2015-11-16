public class Partida{
	private String nomPartida;
	private int time;
	private int dificultat;
	TableroH taulerPartida;
	public Partida(String nomPartida, String username){
		this.nomPartida = nomPartida;
		this.time = 0;
		dificultat = 0;
		taulerPartida=null;
	}
	public String getNomPartida(){
		return nomPartida;
	}
	public int getDificultat(){
		return dificultat;
	}
	public int getTime(){
		return time;
	}
	public void setTime(int time){
		this.time = time;
	}
	public void setDificultat(int dificultat){
		this.dificultat = dificultat; //enumtype
	}
	public void setTauler(TableroH t){
		taulerPartida = t;
	}
	public TableroH getTauler(){
		return taulerPartida;
	}
}
