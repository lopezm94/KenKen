//Made by Ferran Noguera Vall 4-11-15
public class Tablero{
  //Dades
  protected int files;

  protected int columnes;

  /*protected Array<Array<integer>> Tablero;*/
  protected Casilla tauler[][];

  //Funcions
  public Tablero(){
    //Constructor por defecto
    files = 0;
    columnes = 0;
    tauler = new Casilla[0][0];
  }

  public Tablero(int files, int columnes){
    this.files = files;
    this.columnes = columnes;
    tauler = new Casilla[files][columnes];
  }

  public Tablero clonaTablero(){
    Tablero copytablero = new Tablero(files,columnes);
    for (int i = 0; i<files; ++i) {
      for (int j = 0; j<columnes; ++j) {
	Casilla copycasilla = tauler[i][j].clonaCasilla();
	copytablero.setCasilla(copycasilla,i,j);
	}
    }
    return copytablero;
  }


  public Casilla getCasilla(int x, int y){
    return tauler[x][y];
  }

  public void setCasilla(Casilla c, int x, int y){
    tauler[x][y] = c;
  }

  public int getFiles(){
    return files;
  }

  public int getColumnes(){
    return columnes;
  }
}
