import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kenken extends Frame implements ActionListener{
	private Button b1,b2,b3,b4;
	
	public void actionPerformed(ActionEvent e){
		new login();
	}
	
	
	public Kenken(){
		super("KENKEN");
		
		
		setLayout(new FlowLayout());
		
		b1 = new Button("Nuevo Usuario");
		b1.addActionListener(this);
		add(b1);
		
		b2 = new Button("Login");
		b1.addActionListener(this);
		add(b2);
		
		b3 = new Button("Invitado");
		b1.addActionListener(this);
		add(b3);
		
		b4 = new Button("Salir");
		add(b4);
		
		setSize(400,300);
		setVisible(true);
	}
	
	public static void main(String[]args){
		new Kenken();
	}
}
