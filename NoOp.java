/**
*@version 1.0
*@author Joan Grau
*/

public class NoOp extends Area{
	private static final char op = '.';
	
	public NoOp(int pos){
		super(pos);
	}
	
	public String toString(){
		String res="";
		res = "pos: " + pos + ", "
				+ "op: " + op + ", "
				+ "res: " + this.res + ", "
				+ "lista: " + caselles + ", "
				+ "resact: " + resact + "\n ";
		return res;
	}
	
	public char get_operacio(){
		return op;
	}
	
	public Boolean check(int n){
		return true;
	}
	
	public void calcular_resultat(){
		res = caselles.get(0).getSolucion();
	}
	
	public void calcular_resultatactual(){
		resact = caselles.get(0).getValor();
	}
	
}