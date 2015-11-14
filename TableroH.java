import java.util.LinkedList;
import java.lang.RuntimeException;
import java.util.ArrayList;

public class TableroH extends Tablero {
	private int[][] idAreas;
	//private Area[] cage;
	private ArrayList<Area> areas;
	//private int nombreAreas = 0;
	//private int medida;


	public TableroH(int medida){
		super(medida,medida);
		idAreas = new int[medida][medida];
		areas = new ArrayList<Area>();
		//this.medida = medida;
		//this.areas = new AreaCasilla[nombreAreas];
		//this.m = medida;
		//this.nombreAreas = nombreAreas;
	}

	public void afegirArea(Area a){
	      areas.add(a);
	}

	public void setid(int c, int x, int y){
	  idAreas[x][y] = c;
	}


	public int size(){
		return this.files;
	}

	public int getNumAreas(){
		return areas.size();
	}

	public Area getArea(int n){ //n = area, retorna una llista
		//if (n < 0 || n >=this.nombreAreas)
		//	throw RuntimeException("Area no existente");
		return areas.get(n);
	}

	public Boolean areaContains(Area area, int x, int y) {
		int id = area.get_posicio();
		return id == idAreas[x][y];
	}

	public Area getAreaByPos(int x, int y) {
		return areas.get(this.idAreas[x][y]);
	}

	public LinkedList<Integer> getCasillaDomain(int x, int y) {
		Casilla casilla = this.getCasilla(x,y);
		LinkedList<Integer> list = new LinkedList<Integer>();
		int tmp = casilla.getCan(0);
		for (int i=1; tmp != -1; i++) {
			tmp = casilla.getCan(i);
			list.add(tmp);
		}
		return list;
	}

	public Casilla getCasilla(int x, int y){
		//Exception
		//if (x < files and y < files)
		return this.tauler[x][y];
		//else return null;
	}

	public void setCasillaVal(int x, int y, int num){
		if  (! this.tauler[x][y].getFija()) this.tauler[x][y].valor = num;
	}

	public int getCasillaVal(int x, int y) {
		return this.tauler[x][y].valor;
	}

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
		for (int i = 0; i < files; ++i){
			Boolean vect[] = new Boolean[files];
			for (int j = 0; j < files; ++j){
				if (vect[(tauler[i][j].valor) -1 ]) return false;
				vect[(tauler[i][j].valor)-1] = true;
			}
		}
		for (int i = 0; i < files; ++i){
			Boolean vect[] = new Boolean [files];
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
}
