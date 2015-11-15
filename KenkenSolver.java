public class KenkenSolver {

	private Boolean Ok = false;
	private TableroH tablero;

	public void KenKenSolver(TableroH tablero){
		this.tablero = tablero;
		this.initDomain();
	}

	/*private Boolean FuncionRecursiva(int x, int y, ConstraintEngine ce) {
		if (y == this.tablero.size())
			return true;
		Boolean done = false;
		int newx = (x+1)%this.tablero.size();
		int newy = y;
		if (newx == 0) y++;
		for (Integer value : ce.getDomain(x,y)) {
			if (newce.propagate(x, y, value))
				done = this.FuncionRecursiva(newx,newy,newce);
			if (done) break;
		}
	}*/

	public Boolean solveKenken(){
		ConstraintEngine ce = new ConstraintEngine(tablero);
		//Ok = this.FuncionRecursiva(0,0,ce);
		return Ok;
	}

	public void initDomain(){
		for (int i = 0; i < tablero.size(); ++i){
			for (int j = 0; j < tablero.size();++j){
				for (int k = 1; k <= tablero.size();++k){
					tablero.getCasilla(i,j).addCan(k);
				}
			}
		}

		//Propagar las fijas
	}


}
