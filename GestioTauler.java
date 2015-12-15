
/*S'encarrega de retornar un objecte tauler*/
/**
*@author Marc Ortiz
*@author Joan Grau
**/
public class GestioTauler{
	public static TableroH creaTauler(String nomkenken) throws FicheroNoExiste{
		try{
			int mida = GestioDadesH.getMidaKenken(nomkenken);
			TableroH tablero = new TableroH(mida);
			int[][] caselles;
			caselles = GestioDadesH.getCasellaValors(nomkenken);
			for (int i = 0; i < mida;++i){
				for (int j = 0; j < mida;++j){
					int valor = -1;
					Boolean fija;
					int sol = caselles[i*mida+j][1];
					if(caselles[i*mida+j][0] == 1){
						fija = true;
						valor = sol;
					}else{
						fija = false;
					}
					Casilla cas = new Casilla(valor,fija,sol);
					tablero.setCasilla(cas,i,j);
				}
			}
			String[] operacions = GestioDadesH.getOperacions(nomkenken);
			int total_areas = operacions.length;
			int idarea = 0;
			while (idarea < total_areas){
				String varS = operacions[idarea];
				char var2[] = varS.toCharArray();
				Area a = AreaBuilder.newArea(idarea,var2[0]);
				tablero.afegirArea(a,0);
				++idarea;
			}
			for(int i = 0;i < mida; i++){
				for(int j=0; j < mida; j++){
					idarea = caselles[i*mida+j][2];
					tablero.setid(idarea, i, j);
				}
			}
			tablero.colocaRes();
			return tablero;
		}catch(Exception e){
			throw e;
		}
	}
}
