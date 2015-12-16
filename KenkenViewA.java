import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.*;
/**
 * 
 * @author reyes
 *
 */
@SuppressWarnings("serial")
public class KenkenViewA extends JPanel {
	private int size;
	private int r,v;
	private JButton button;
	private JPanel panel1;
	private Vector<Integer> areas = null;
	private Vector<Integer> areas2 = null;
	private Vector<Integer> areas3 = null;
	//NumOptions nums;
	public JPanel container;

	// added main for testing
	public static void main(String [] args){
	    KenkenView kenken = new KenkenView();
	}

	/*public optionView() {

	}*/

	public Vector<Integer> areas(){
		return areas;
	}

	public Vector<Integer> areas2(){
		return areas2;
	}

	public Vector<Integer> areas3(){
		return areas3;
	}

	public void setButton(JButton button) {
	  this.button = button;
	}

	public JPanel matriu(){
		return container;
	}

	public JPanel getBoard() {
		return panel1;
	}

	public KenkenViewA() {
	    // TODO Auto-generated constructor stub
		areas = new Vector<Integer>(MainController.getInstance().tam());
	    int c = 0;
	    int var = 20;
		for (int i = 0; i < MainController.getInstance().tam(); ++i){
			c += var;
			if (c > 255){
				var += 10;
				c = 0;
				if (var > 255){
					var = 20;
				}
			}
			areas.add(c);
		}
		areas2 = new Vector<Integer>(MainController.getInstance().tam());
	    c = 255;
	    var = 40;
		for (int i = 1; i <= MainController.getInstance().tam(); ++i){
			c -= var;
			if (c < 0){
				var += 30;
				c = 255;
				if (var > 255){
					var = 40;
				}
			}
			areas2.add(c);

		}


		areas3 = new Vector<Integer>(MainController.getInstance().tam());
	    c = 0;
	    var = 10;
		for (int i = 0; i < MainController.getInstance().tam(); ++i){
			c += var;
			if (c > 255){
				var += 10;
				c = 0;
				if (var > 255){
					var = 20;
				}
			}
			areas3.add(c);
		}

	    this.size = MainController.getInstance().tamany();
	    //*******************************************
	    container = new JPanel();
	    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

	    panel1 = new JPanel(new GridLayout(size,size));

	    //panel1.set[Preferred/Maximum/Minimum]Size()

	    container.add(panel1);
	    ////*****************************************
	    /*
	    container = new JPanel(new GridLayout(size,size));
	    */
	    JFrame frame = new JFrame();
	    frame.add(container);

	    for(r = 0; r < size; r++){
	        for(v = 0; v < size; v++){
	            //JPanel grid = new JPanel();
	            JButton build = new JButton();
	            build.addActionListener(new java.awt.event.ActionListener() {
	                final int row = r;
	                final int col = v;
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                    jButtonActionPerformed(evt,row,col);
	                }
	            });

	         build.setBackground(new java.awt.Color(((int) areas.get(MainController.getInstance().area(r,v))), ((int) areas2.get(MainController.getInstance().area(r,v))), ((int) areas3.get(MainController.getInstance().area(r,v)))));

	            build.setText((MainController.getInstance().areaTipo(r, v)));
	            panel1.add(build);
	            //container.setVisible(true);
	        }
	    }


	    frame.setSize(1000,700);
	    //frame.setVisible(true);
	    //nums = new NumOptions(size);
	}

	public void actualizar() {

	}


	private void jButtonActionPerformed(java.awt.event.ActionEvent evt, int row, int col) {
		setButton(((JButton)evt.getSource()));
		r = row;
		v = col;
	//  System.out.println(container.getComponentAt(row,col)).setText('9');
	}

}
