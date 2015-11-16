//Made by Yeray Bosoms Blasco 4-11-15 
//new modification 10-11-15
import java.util.ArrayList;



public class Casilla{

  // Datos
	protected int valor;
	protected boolean fija;
	protected int solucion;
	protected ArrayList<Integer> can;

	//funciones
	public Casilla(){
		valor = -1;
		fija = false;
		solucion = 0;
		can = new ArrayList<Integer>(0);
	}

	public String toString(){
		String res = "";
		res = "valor: " + this.valor + ", "
				+ "fija: " + this.fija + ", "
				+ "solucion: " + this.solucion + ", "
				+ "posibilidades: " + this.can;
		return res;
	}
	
	public Casilla (int valor, boolean fija, int solucion){
		this.valor= valor;
		this.fija = fija;
		this.solucion = solucion;
		can = new ArrayList<Integer>(0);
	}
	public Casilla clonaCasilla(){
		Casilla aux = new Casilla(valor, fija, solucion);
		for(int i = 0; i<can.size(); ++i){
			Integer aux2 = can.get(i);
			aux.addCan(aux2.intValue());
			}
		return aux;
	}

	public boolean getLlena(){
		return ((-1) != valor);
	}

	public int getValor(){
		return valor;
	}


	public int getCan(int x){
		if (can.size()>x) return can.get(x).intValue();
		else return -1;
	}

	public void addCan(int x){
	Integer aux = new Integer(x);
		if(!can.contains(aux))can.add(aux);
	}

	public boolean candidato(int x){
		return can.contains(new Integer(x));
	}

	public void borrarcandidatos(){
		can.clear();
	}

	public int numerocandidatos(){
		return can.size();
	}

	public void quitarcandidato(int x){
		can.remove(new Integer(x));
	}

	public boolean getFija(){
		return fija;
	}

	public int getSolucion(){
		return solucion;
	}

	public void setFija(boolean estafija){
		fija = estafija;
	}

	public void vaciar(){
		valor = -1;
	}

	public void setValor(int valor_a_dar){
		valor = valor_a_dar;
	}

	public void setSolucion(int solucion_a_dar){
		solucion = solucion_a_dar;
	}
}
