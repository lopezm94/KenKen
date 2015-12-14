import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;

public class KenkenView extends JPanel {
int size;
int r,c;
JButton button;
NumOptions nums;
public JPanel container;

// added main for testing
public static void main(String [] args){
    KenkenView kenken = new KenkenView(3);
}

/*public optionView() {

}*/

public void setButton(JButton button) {
  this.button = button;
}

public KenkenView(int size) {
    // TODO Auto-generated constructor stub
    this.size = size;
    //*******************************************
    container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

    JPanel panel1 = new JPanel(new GridLayout(size,size));
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
        for(c = 0; c < size; c++){
            //JPanel grid = new JPanel();
            JButton build = new JButton();
            build.addActionListener(new java.awt.event.ActionListener() {
                final int row = r;
                final int col = c;
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonActionPerformed(evt,row,col);
                }
            });

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
        for(c = 0; c < size; c++){
            //container.add(Region(size,size));
            //add(build);
            //build.setSize(50, 50)
            container.setVisible(true);
        }
    }

    frame.setSize(1000,700);
    frame.setVisible(true);
    //nums = new NumOptions(size);
}

private void jButtonActionPerformed(
  java.awt.event.ActionEvent evt, int row, int col
) {
  setButton(((JButton)evt.getSource()));
  System.out.println(row + " " + col);
  System.out.println(container.getComponentAt(row,col).getClass().getName());
//  System.out.println(container.getComponentAt(row,col)).setText('9');
}
private void jButtonActionPerformed(
  java.awt.event.ActionEvent evt, int row
) {
  if (button == null) return;
  button.setText("" + row);
  System.out.println(row + " ");
  System.out.println(container.getComponentAt(row,0).getClass().getName());
}
}
