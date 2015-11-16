public class Perfil{
	private String usuari;
	private String contrasenya;
	private Puntuacio puntuacio = new Puntuacio();
	Partida partidaActual;
	public Perfil(){
		this.usuari = "invitado";
		this.contrasenya = "";
	}
	public Perfil(String usuari, String contrasenya){
		this.usuari = usuari;
		this.contrasenya = contrasenya;
	}
	public void add_puntuacio(Puntuacio puntuacio){
		this.puntuacio.facil += puntuacio.facil;
		this.puntuacio.mig += puntuacio.mig;
		this.puntuacio.dificil += puntuacio.dificil;
	}
	public String get_contrasenya(){
		return contrasenya;
	}
	public String get_usuari(){
		return usuari;
	}
	public Puntuacio get_puntuacio(){
		return puntuacio;
	}
	public Partida get_partida(){
		return partidaActual;
	}
	public void assignar_nova_partida(Partida partida){
		partidaActual = partida;
	}
}
