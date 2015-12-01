import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
*@version 1.0
*@author Reyes Vera
*/

public class Menu extends Frame implements Runnable{
	private Button b1,b2,b3,b4,b5,b6;
	
	ActionListener escuchador = new ActionListener() {
		@Override
        public void actionPerformed(ActionEvent e) {
           
		Button boton = (Button) e.getSource();
		String nombreBoton = boton.getLabel();
		 
        switch (nombreBoton) {
        	case "Crear Partida":
        		new CrearPartida();
        		break;
        		
        	case "Carregar Partida":
        		new CarregarPartida();
        		break;
        		
        	case "Generar KenKen":
        		new GenerarKenken();
        		break;
        		
        	case "Elimina l'usuari":
        		new Elimina();
        		break;
        	case "Tutorial sobre com jugar al KenKen":
        		MainController mc =new MainController();
        		mc.show_tutorial();
        		break;
        		
        	case "Salir":
        		new Kenken();
        		break;
        }
	}};
	
	public Menu(){
		super("Menu");
		
		
		setLayout(new FlowLayout());
		
		b1 = new Button("Crear Partida");
		b1.addActionListener(escuchador);
		add(b1);
		
		b2 = new Button("Carregar Partida");
		b2.setSize(new Dimension(150, 80));
		b2.addActionListener(escuchador);
		add(b2);
		
		b3 = new Button("Generar KenKen");
		b3.setSize(new Dimension(150, 80));
		b3.addActionListener(escuchador);
		add(b3);
		
		b4 = new Button("Elimina l'usuari");
		b4.setSize(new Dimension(200, 80));
		b4.addActionListener(escuchador);
		add(b4);
		
		b5 = new Button("Tutorial sobre com jugar al KenKen");
		b5.setSize(new Dimension(200, 80));
		b5.addActionListener(escuchador);
		add(b5);
		
		b6 = new Button("Salir");
		b6.setSize(new Dimension(200, 80));
		b6.addActionListener(escuchador);
		add(b6);
		
		setSize(400,300);
		setVisible(true);
	}
	
	public static void main(String[]args){
		new Menu();
	}

	@Override
	public void run() {
	}
}
