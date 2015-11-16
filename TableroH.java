import java.util.LinkedList;
//import java.lang.RuntimeException;
import java.util.ArrayList;

/*@version 1.0
*@author Reyes Vera
*/


public class TableroH extends Tablero {
	private int[][] idAreas;
	private ArrayList<Area> areas;
	


	public TableroH(int medida){
		super(medida,medida);
		idAreas = new int[medida][medida];
		for (int i = 0; i < files;++i){
			for (int j= 0;j < files;++j) idAreas[i][j] = -1;
		}
		areas = new ArrayList<Area>();
	}

	public void afegirArea(Area a, int ress){
			a.set_res(ress);
			//System.out.println(ress);
	      areas.add(a);
	      
	}

	public void setid(int c, int x, int y){
	  idAreas[x][y] = c;
	  areas.get(c).afegir_casella(tauler[x][y]);
	}

	public void colocaRes(){
		for (int i = 0; i < areas.size();++i) areas.get(i).calcular_resultat();
	}

	public int size(){
		return this.files;
	}

	public int getNumAreas(){
		return areas.size();
	}

	public Area getArea(int n){ //n = area, retorna una llista
		return areas.get(n);
	}

	public Boolean areaContains(Area area, int x, int y) {
		int id = area.get_posicio();
		return id == idAreas[x][y];
	}

	public Area getAreaByPos(int x, int y) {
		return areas.get(this.idAreas[x][y]);
	}

	public Boolean casillaIsFija(int x, int y) {
		return this.tauler[x][y].getFija();
	}

	public LinkedList<Integer> getCasillaDomain(int x, int y) {
		Casilla casilla = this.getCasilla(x,y);
		LinkedList<Integer> list = new LinkedList<Integer>();
		int tmp = casilla.getCan(0);
		for (int i=1; tmp != -1; i++) {
			list.add(tmp);
			tmp = casilla.getCan(i);
		}
		return list;
	}

	public Casilla getCasilla(int x, int y){
		return this.tauler[x][y];
	}

	public void setCasillaVal(int x, int y, int num){
		if  (! this.tauler[x][y].getFija()) this.tauler[x][y].valor = num;
	}
	
	public void setCasillaFija(int val, int x, int y){
		this.tauler[x][y].fija = true;
		this.tauler[x][y].solucion = val;
		this.tauler[x][y].valor = val;
	}

	public int getCasillaVal(int x, int y) {
		return this.tauler[x][y].valor;
	}
	
/*	public void checkarea(){
		for (int i = 0; i < this.areas.size(); ++i) areas.get(i).calcular_resultatactual();
	}*/

	public void setCasillaSol(int x, int y, int valor){
		this.tauler[x][y].solucion = valor;
	}

	public int getCasillaSol(int x, int y){
		return this.tauler[x][y].solucion;
	}

	public Boolean areaCheck(int area){
		return areas.get(area).correcte();
	}

	public Boolean tableroCheck(){
		for (int i = 0; i < areas.size(); ++i){
			if (! areaCheck(i)) return false;
		}
		return true;
	}

	public Boolean numerosCheck(){	//numeros no repes
		Boolean vect[] = new Boolean[files];

		for (int i = 0; i < files; ++i){
			for (int k = 0; k < files; ++k) vect[k] = false;
			for (int j = 0; j < files; ++j){
				if (vect[(tauler[i][j].valor) -1 ]) return false;
				vect[(tauler[i][j].valor)-1] = true;
			}
		}
		for (int i = 0; i < files; ++i){
			for (int k = 0; k < files; ++k) vect[k] = false;
			for (int j = 0; j < files; ++j){
				if (vect[(tauler[j][i].valor) -1 ]) return false;
				vect[(tauler[j][i].valor)-1] = true;
			}
		}
		return true;
	}

	public int getAreaID(int x, int y){
		return this.idAreas[x][y];
	}
	

	public Area getArea(int x, int y){ // Devuelve el area asociada a la posicion x,y.
		int a = this.idAreas[x][y];
		return areas.get(a);
	}
	
	public int calcuDif(){
		int var = 0;
		for (int i = 0; i < files; ++i){
			for (int j = 0; j < files; ++j){
				if (tauler[i][j].fija) ++var;
			}
		}
		if (var >= 1 && var <= 16) var = 0;
		else if (var > 16 && var <= 49) var = 1;
		else var = 2;
		return var;
	}

	public String toString(){ //devuelve los contenidos de un tablero en una string
		String res = "";
		for (int i = 0; i < files;++i){
			for (int j = 0; j < files; ++j){
				res = res + getCasillaVal(i,j) + " ";
			}
			res = res + "\n";
		}
	/*	res = res + "\n";
		res = res + "\n";

		for (int i = 0; i < files;++i){
			for (int j = 0; j < files; ++j){
				res = res + getCasillaSol(i,j) + " ";
			}
			res = res + "\n";
		}
		
		res = res + "\n";
		res = res + "\n";
		
		for (int i = 0; i < files;++i){
			for (int j = 0; j < files; ++j){
				res = res + idAreas[i][j] + " ";
			}
			res = res + "\n";
		}
		
		res = res + "\n";
		res = res + "\n";
		
		System.out.println(areas);
		*/
		return res;
	}
}
