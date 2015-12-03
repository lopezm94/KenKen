/**
 * @author reyes vera
 *
 */

public class Jugar {
	public void poner(int a,int b, String text){
		int var = Integer.parseInt(text);
		MainController mc = new MainController();
		mc.posar_pos(a,b,var);
	}
	
	public Boolean comprobar(){
		MainController mc = new MainController();
		if (mc.comp()) return true;
		return false;
	}
}
