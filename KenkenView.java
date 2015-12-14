import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;

public class KenkenView extends JPanel {
int rows = 9;
int cols = 9;
int r,c;
public JPanel container = new JPanel(new GridLayout(rows,cols));

// added main for testing
public static void main(String [] args){
    KenkenView sudoku = new KenkenView();
}

/*public optionView() {

}*/

public KenkenView(/*SudokuBase sb*/) {
    // TODO Auto-generated constructor stub
    JFrame frame = new JFrame();
    frame.add(container);


    Region();
    for(r = 0; r < rows; r++){
        for(c = 0; c < cols; c++){
            //container.add(Region(rows,cols));
            //add(build);
            //build.setSize(50, 50)
            container.setVisible(true);
        }
    }

    frame.setSize(400,400);
    frame.setVisible(true);
}

//class Region extends JPanel {

public void Region( ) {
    //setLayout(new GridLayout(3,3));
    //JPanel grid = new JPanel(new GridLayout(3,3));
    //grid.setSize(50, 50);

    for(r = 0; r < rows; r++){
        for(c = 0; c < cols; c++){
            //JPanel grid = new JPanel();
            JButton build = new JButton();
            build.addActionListener(new java.awt.event.ActionListener() {
                final int row = r;
                final int col = c;
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonActionPerformed(evt,row,col);
                }
            });

            container.add(build);
            //container.setVisible(true);
        }
    }
}

private void jButtonActionPerformed(
  java.awt.event.ActionEvent evt, int row, int col
) {
  ((JButton)evt.getSource()).setText("9");
  System.out.println(row + " " + col);
  System.out.println(container.getComponentAt(row,col).getClass().getName());
//  System.out.println(container.getComponentAt(row,col)).setText('9');
}
}
