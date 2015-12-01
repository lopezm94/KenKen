import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends Frame implements ActionListener{
	private Button b1;
	
	private TextField log, cont, cont2;
	
	public void actionPerformed(ActionEvent e){
		String str = log.getText();
		String str1 = cont.getText();
		String str2 = cont2.getText();
		MainController a = new MainController();
		//if (str1 == str2)
		a.login(str,str1);   //para entrar valores
		//else add.(Panel)
	}
	
	
	public login(){
		super("Nuevo Usuario");
		
		log = new TextField();
		add(log);
		cont = new TextField();
		add(cont);
		cont2 = new TextField();
		add(cont2);
		
		setLayout(new FlowLayout());
		
		b1 = new Button("Registrar");
		b1.addActionListener(this);
		add(b1);
		
		
		setSize(400,300);
		setVisible(true);
	}
	
	public static void main(String[]args){
		login k = new login();
	}
}
