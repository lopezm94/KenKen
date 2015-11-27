import java.util.ArrayList;
import java.lang.Math;

/**
*@version 2.0
*@author Joan Grau
*/
public abstract class Area{

	//Atributs

	protected int pos;//identificador area, va de 0 a nombreAreas-1
//	private char op;//tipus operacio, sense op es casella sola
	protected ArrayList<Casilla > caselles;//conte la llista de caselles
	protected int res;//resultat real de l'area
	protected int resact;


	//Metodes

	public Area(int pos){
		this.pos = pos;
		res = 0;
		resact = 0;
		caselles = new ArrayList<Casilla>();
	}

	abstract public String toString();

	abstract public char get_operacio();

	abstract public Boolean check(int n);

	abstract public void calcular_resultat();

	abstract public void calcular_resultatactual();

	public int get_resultat(){
		return res;
	}

	public int get_resultatactual(){
		this.calcular_resultatactual();
		return resact;
	}

	public int get_posicio(){
		return pos;
	}

	public Boolean correcte(){
	this.calcular_resultatactual();
		if(res == 0) this.calcular_resultat();
		return res == resact;
	}

	public void afegir_casella(Casilla cas){//es fara 1 cop si =;2 per - i /; 2 o mes * i +
		caselles.add(cas);
	}

	public int get_tamany(){
		return caselles.size();
	}

	public Casilla get_casella(int i){
		return caselles.get(i);
	}

	public void set_casella(Casilla cas, int i){
		caselles.set(i,cas);
	}

	public void set_res(int res){
		this.res = res;
	}

}
