import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
*@version 1.0
*@author Reyes Vera
*/

public class Kenken extends Frame implements Runnable{
	private Button b1,b2,b3,b4;
	
	ActionListener escuchador = new ActionListener() {
		@Override
        public void actionPerformed(ActionEvent e) {
           
		Button boton = (Button) e.getSource();
		String nombreBoton = boton.getLabel();
		 
        switch (nombreBoton) {
        	case "Nuevo Usuario":
        		new login();
        		break;
        	case "Login":
        		new Log();
        		break;
        	case "Invitado":
        		
        		break;
        	case "Salir":
        		System.exit(ABORT);
        		break;
        }
	}};
	
	public Kenken(){
		super("KENKEN");
		
		
		setLayout(new FlowLayout());
		
		b1 = new Button("Nuevo Usuario");
		b1.setSize(new Dimension(150, 80));
		b1.addActionListener(escuchador);
		add(b1);
		
		b2 = new Button("Login");
		b2.setSize(new Dimension(150, 80));
		b2.addActionListener(escuchador);
		add(b2);
		
		b3 = new Button("Invitado");
		b3.setSize(new Dimension(150, 80));
		b3.addActionListener(escuchador);
		add(b3);
		
		b4 = new Button("Salir");
		b4.setSize(new Dimension(200, 80));
		b4.addActionListener(escuchador);
		add(b4);
		
		setSize(400,300);
		setVisible(true);
	}
	
	public static void main(String[]args){
		new Kenken();
	}

	@Override
	public void run() {
		//new Kenken();
	}
}
