/**
*@version 1.0
*@author Joan Grau
*/

public class Suma extends Area{
	private static final char op = '+';
	
	public Suma(int pos){
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
		Boolean b1;
		int suma = 0, contador = 0;
		for(int i=0; i < caselles.size(); ++i){
			if(caselles.get(i).getValor() != -1){
			suma += caselles.get(i).getValor();
			}else{
				++contador;
			}
		}
		if(contador != 0){
			if(suma >= res){
				b1 = false;
			}else{
				if(res-suma < contador || res-suma > n*contador){
					b1 = false;
				}else{
					b1 = true;
				}
			}
		}else{
			b1 = suma == res;
		}
		return b1;
	}
	
	public void calcular_resultat(){
		res = 0;
		for(int i=0; i < caselles.size(); ++i){
			res += caselles.get(i).getSolucion();
		}
	}
	
	public void calcular_resultatactual(){
		resact = 0;
		for(int i=0; i < caselles.size(); ++i){
			resact += caselles.get(i).getValor();
		}
	}
	
}