
public class Contrkenken {
	public void reg(){
		 NewLog n = new NewLog();
	     n.setVisible(true);
	}
	
	public void carga(){
		Log a = new Log();
        a.setVisible(true);
	}
	
	public void guest(){
		MainController mc = new MainController();
        mc.guest();
        Menu m = new Menu();
        m.setVisible(true);
	}
}
