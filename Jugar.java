import java.util.*;
/**
 * @author reyes vera
 *
 */

public class Jugar {
	
	MainController mc = null;
	
	
	
	public Jugar(MainController a){
		mc = a;
	}
	
	
	public void poner(int a,int b, String text){
		int var = Integer.parseInt(text);
		mc.posar_pos(a,b,var);
	}
	
	public Boolean comprobar(){
		mc.imprimir();
		if (mc.comp()) return true;
		return false;
	}
}
