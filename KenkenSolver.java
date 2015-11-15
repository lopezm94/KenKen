<<<<<<< HEAD
import java.io.IOException;

/*@version 1.0ermino
=======
/*@version 1.0
>>>>>>> c7467b778e02c0e80413c97674a1b80c6e3da45b
*@author Reyes Vera
*/


public class KenkenSolver {

	private TableroH tablero;

	public KenkenSolver(TableroH tablero){
	public void KenKenSolver(TableroH tablero){
		this.tablero = tablero;
		this.initDomain();
	}

	private Boolean FuncionRecursiva(int x, int y, ConstraintEngine ce) {
		if (y == this.tablero.size()) {
			System.out.println("termino");
			ce.storeSolution();
			try{
				System.in.read();
			}
			catch(IOException e){
				
			}
			return true;
			
		}
		Boolean check = false;
			ce.storeSolution();
			return true;
		}
		Boolean done = false;
		int newx = (x+1)%this.tablero.size();
		int newy = y;
		if (newx == 0) y++;

		for (Integer value : ce.getDomain(x,y)) {
			//System.out.println("hola");
			check = ce.propagate(x, y, value);
			System.out.println(ce);
			
			try{                       
				System.in.read();      
			}                          
			catch(IOException e){      
				                       
			}                          
			if (check){
				System.out.println("x: " + x + " y: " + y + " value: " + value + " entra");
				done = this.FuncionRecursiva(newx,newy,ce);
			}
			else 
				System.out.println("no entra");
			ce.depropagate(x,y);
			System.out.println(ce);

			try{                       
				System.in.read();      
			}                          
			catch(IOException e){      
				                       
            }                          
		for (Integer value : ce.getDomain(x,y)) {
			if (ce.propagate(x, y, value))
				done = this.FuncionRecursiva(newx,newy,ce);
			ce.depropagate(x,y);
			if (done) break;
		}
		return done;
	}

	public Boolean solveKenken(){
		ConstraintEngine ce = new ConstraintEngine(tablero);
		return this.FuncionRecursiva(0,0,ce);
	}

	public void initDomain(){
		for (int i = 0; i < tablero.size(); ++i){
			for (int j = 0; j < tablero.size();++j){
				for (int k = 1; k <= tablero.size();++k){
					tablero.getCasilla(i,j).addCan(k);
				}
			}
		}
	//	System.out.println(tablero);

	}


}
