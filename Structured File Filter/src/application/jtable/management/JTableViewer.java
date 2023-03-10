package application.jtable.management;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * Shamelessly stolen from
 * https://stackoverflow.com/questions/22864095/reading-data-from-a-specific-csv-file-and-displaying-it-in-a-jtable
 * 
 * TODO Check out again
 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 */

public class JTableViewer extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JTable table;

	public JTableViewer(List<String[]> pData, String[] pColumnNames) {
		super(new BorderLayout(3, 3));
		TableModel tableModel = new TableModel(pData, pColumnNames);
		System.out.println("\nIn the board you should see: ");
		System.out.println("Rows: " + tableModel.getRowCount());
		System.out.println("Cols: " + tableModel.getColumnCount());
		this.table = new JTable(tableModel);
	}// end constructor

	public void createAndShowJTable() {

		this.table.setPreferredScrollableViewportSize(new Dimension(800, 600));
		this.table.setFillsViewportHeight(true);
		JPanel ButtonOpen = new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(ButtonOpen, BorderLayout.SOUTH);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);

		// add a nice border
		setBorder(new EmptyBorder(5, 5, 5, 5));

		// Create and set up the window.
		JFrame frame = new JFrame("JTable for the data");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		frame.setContentPane(this);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
