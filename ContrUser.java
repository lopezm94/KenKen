import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContrUser {
	
	public Boolean esta(String str){
		MainController a = new MainController();
		if (a.user_exists(str)) return true;
		return false;
	}
	public Boolean correcte(String str, String str1){
		MainController a = new MainController();
		if (a.user_ok(str, str1)) return true;
		return false;
	}
	
	public void carga(String str, String str1){
		MainController a = new MainController();
			a.login(str,str1);   //para entrar valores
			Menu m = new Menu(a);
	        m.setVisible(true);        
	}
	
	public void newU(String str,String str1,String str2){
		MainController a = new MainController();
        try {
        	if (! a.user_exists(str)){
        		a.login_reg(str,str1,str2);   //para entrar valores
        		Menu m = new Menu(a);
        		m.setVisible(true);
        	}
        	} catch (IOException ex) {
            Logger.getLogger(NewLog.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
