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
	
	public Boolean fija(int x, int y){
		return mc.es_fija(x,y);
	}
	
	public int num(int x, int y){
		return mc.numC(x,y);
	}
	
	public void poner(int a,int b, String text){
		if (! (text.isEmpty())){
			int var = Integer.parseInt(text);
			mc.posar_pos(a,b,var);
		}
	}
	
	public Boolean comprobar(){
		if (mc.comp()) return true;
		return false;
	}
	
	public void guardar(String a){
		mc.guarda(a);
	}
}
