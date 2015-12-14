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
	
	public int ponerAjuda(int x, int y){
		int var = mc.getCas(x,y);
		mc.posar_pos(x,y,var);
		return var;
	}
	
	public void neteja(){
		for (int x = 0; x < mc.tamany(); ++x){
			for (int y = 0; y < mc.tamany(); ++y){
				mc.posar_pos(x,y,-1);
			}
		}
	}
	
	public int show(int x,int y){
		return mc.getCas(x, y);
	}
	
	public Boolean fija(int x, int y){
		return mc.es_fija(x,y);
	}
	
	public int num(int x, int y){
		System.out.println(mc.numC(x, y));
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
