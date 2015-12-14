import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.*;

@SuppressWarnings("serial")
public class KenkenView extends JPanel {
	int size;
	int r,v;
	JButton button;
	JPanel panel1;
	Vector<Integer> areas = null;
	Vector<Integer> areas2 = null;
	//NumOptions nums;
	public JPanel container;

	// added main for testing
	public static void main(String [] args){
	    KenkenView kenken = new KenkenView();
	}

	/*public optionView() {

	}*/

	public void setButton(JButton button) {
	  this.button = button;
	}

	public JPanel matriu(){
		return container;
	}

	public JPanel getBoard() {
		return panel1;
	}

	public KenkenView() {
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


	    this.size = MainController.getInstance().tamany();
	    //*******************************************
	    container = new JPanel();
	    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

	    panel1 = new JPanel(new GridLayout(size,size));
	    JPanel panel2 = new JPanel(new GridLayout(size,0));

	    //panel1.set[Preferred/Maximum/Minimum]Size()

	    container.add(panel1);
	    container.add(panel2);
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

	            build.setBackground(new java.awt.Color(((int) areas.get(MainController.getInstance().area(r,v))), ((int) areas2.get(MainController.getInstance().area(r,v))), 255));
	 	       if (MainController.getInstance().num(r, v) != -1)
	            build.setText(Integer.toString(MainController.getInstance().num(r, v)));
	            panel1.add(build);
	            //container.setVisible(true);
	        }
	    }

	    for(r = 0; r < size; r++){
	            //JPanel grid = new JPanel();
	            JButton build = new JButton();
	            build.setText(""+(r+1));
	            build.addActionListener(new java.awt.event.ActionListener() {
	                final int row = r+1;
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                    jButtonActionPerformed(evt,row);
	                }
	            });

	            panel2.add(build);
	            //container.setVisible(true);
	    }
	    for(r = 0; r < size; r++){
	        for(v = 0; v < size; v++){
	            //container.add(Region(size,size));
	            //add(build);
	            //build.setSize(50, 50)
	            container.setVisible(true);
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

	private void jButtonActionPerformed(java.awt.event.ActionEvent evt, int row) {
		if (button == null) return;
		if (! MainController.getInstance().fija(r, v)){
			MainController.getInstance().posar_pos(r,v,String.valueOf(row));
			button.setText("" + row);
		}
	}
}
