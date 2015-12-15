import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.*;

@SuppressWarnings("serial")
public class KenkenViewMostra extends JPanel {
	int size;
	int r,v;
	JButton button;
	Generar g;
	Vector<Integer> areas = null;
	Vector<Integer> areas2 = null;
	Vector<Integer> areas3 = null;
	//NumOptions nums;
	public JPanel container;
	
	// added main for testing
	public static void main(String [] args){
	    KenkenViewMostra kenken = new KenkenViewMostra(null);
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
	
	public KenkenViewMostra(Generar a) {
	    // TODO Auto-generated constructor stub
		g = a;
		areas = new Vector<Integer>(g.getNumAreas());
	    int c = 0;
	    int var = 20;
		for (int i = 0; i < g.getNumAreas(); ++i){
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
		areas2 = new Vector<Integer>(g.getNumAreas());
	    c = 255;
	    var = 40;
		for (int i = 1; i <= g.getNumAreas(); ++i){
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
		
		areas3 = new Vector<Integer>( g.getNumAreas());
	    c = 0;
	    var = 10;
		for (int i = 0; i < g.getNumAreas(); ++i){
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
		
	
	    this.size = g.mida();
	    //*******************************************
	    container = new JPanel();
	    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
	
	    JPanel panel1 = new JPanel(new GridLayout(size,size));
	
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
	
	            build.setBackground(new java.awt.Color(((int) areas.get(g.getAreaID(r, v))), ((int) areas2.get(g.getAreaID(r, v))), ((int) areas3.get(g.getAreaID(r, v)))));
	            build.setText(Integer.toString(g.getCasillaSol(r, v)));
	            panel1.add(build);
	            //container.setVisible(true);
	        }
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
	
	
	private void jButtonActionPerformed(java.awt.event.ActionEvent evt, int row, int col) {
		setButton(((JButton)evt.getSource()));
		r = row;
		v = col;
	//  System.out.println(container.getComponentAt(row,col)).setText('9');
	}
	
}
