import java.io.IOException;
import java.util.LinkedList;

/*@version 1.0ermino
*@author Reyes Vera
*/


public class KenkenSolver {

	private TableroH tablero;

	public KenkenSolver(TableroH tablero){
<<<<<<< HEAD
=======
	public void KenKenSolver(TableroH tablero){
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
		this.tablero = tablero;
		this.initDomain();
	}

	private Boolean FuncionRecursiva(int x, int y, ConstraintEngine ce) {
		if (y == this.tablero.size()) {
<<<<<<< HEAD
			//System.out.println("termino");
=======
			System.out.println("termino");
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
			ce.storeSolution();
			//System.out.println(tablero);

		/*	try{
				System.in.read();
			}
			catch(IOException e){
				
			}*/
			return true;
			
		}
		Boolean check = false;
<<<<<<< HEAD
		Boolean done = false;
		int newx = (x+1)%this.tablero.size();
		int newy = y;
		if (newx == 0) newy++;
=======
			ce.storeSolution();
			return true;
		}
		Boolean done = false;
		int newx = (x+1)%this.tablero.size();
		int newy = y;
		if (newx == 0) y++;
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30

		for (Integer value : new LinkedList<Integer>(ce.getDomain(x,y))) {
			//System.out.println("hola");
			check = ce.propagate(x, y, value);
			//System.out.println(ce);
			//System.out.println("x: " + x + " y: " + y + " value: " + value + " entra");

			/*try{                       
				System.in.read();      
			}                          
			catch(IOException e){      
				                       
			}  */                        
			if (check){
				//System.out.println("x: " + x + " y: " + y + " value: " + value + " entra");
				done = this.FuncionRecursiva(newx,newy,ce);
			}
			else 
				//System.out.println("no entra");
			ce.depropagate(x,y);
			//System.out.println(ce);

			/*try{                       
				System.in.read();      
			}                          
			catch(IOException e){      
				                       
<<<<<<< HEAD
            } */                         
=======
            }                          
		for (Integer value : ce.getDomain(x,y)) {
			if (ce.propagate(x, y, value))
				done = this.FuncionRecursiva(newx,newy,ce);
			ce.depropagate(x,y);
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
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
