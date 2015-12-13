/**
*@version 1.0
*@author Joan Grau
*/

public class Multiplicacio extends Area{
	private static final char op = '*';

	public Multiplicacio(int pos){
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
		int mult = 1, contador2 = 0;
		for(int i=0; i < caselles.size(); ++i){
			if(caselles.get(i).getValor() != -1){
				mult *= caselles.get(i).getValor();
			}else{
				++contador2;
			}
		}
		if(mult == res){
			b1 = true;
		}else{
			if(mult > res){
				b1 = false;
			}else{
				if(res % mult != 0){
					b1 = false;
				}else{
					int quocient = res/mult;
					Boolean control = false;
					while(contador2 > 0){
						for(int i=n; i > 0 && !control; --i){
							if(quocient%i == 0){
								quocient = quocient / i;
								control = true;
							}
						}
						contador2--;
						control = false;
					}
					b1 = quocient == 1;
				}
			}
		}
		return b1;
	}

	public void calcular_resultat(){
		res = 1;
		for(int i=0; i < caselles.size(); ++i){
			res *= caselles.get(i).getSolucion();
		}
	}

	public void calcular_resultatactual(){
		resact = 1;
		for(int i=0; i < caselles.size(); ++i){

			resact *= caselles.get(i).getValor();
		}
	}

}
