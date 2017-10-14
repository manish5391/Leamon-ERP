package leamon.erp.component.helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXList;

import leamon.erp.model.InventoryInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.InventoryUI;
import leamon.erp.ui.model.StockItemCellRender;
import leamon.erp.ui.model.TableInventoryItemModel;
import leamon.erp.util.LeamonERPConstants;


public class LeamonAutoTableCellsValueSuggestor<T extends List<E>, E> extends JWindow implements KeyListener, FocusListener, ActionListener{
	
	private static final Logger LOGGER = Logger.getLogger(LeamonAutoTableCellsValueSuggestor.class);
	
	private static String CLASS_NAME="LeamonAutoTableCellsValueSuggestor";
	
	JXList lst = null;
	java.util.TreeSet<E> val = new java.util.TreeSet<E>();
	java.util.Vector<E> tempVector = new java.util.Vector<E>(1, 1);
	InventoryUI ui;
	
	//declarations for table
	JTable tableParent = null;
	int activeColumn = 0;
	StringBuffer typed = new StringBuffer();

	public LeamonAutoTableCellsValueSuggestor() {
		final String METHOD_NAME="LeamonAutoTableCellsValueSuggestor";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		lst = new JXList();
		lst.setCellRenderer(new StockItemCellRender());
		lst.addKeyListener(this);
		lst.addFocusListener(this); 
		this.getContentPane().add(new JScrollPane(lst), BorderLayout.CENTER);
		setButtons();
		((JPanel)this.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.red, 3));
		lst.setToolTipText("Press F3, F4 to navigate, F5 to select");
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	public LeamonAutoTableCellsValueSuggestor(JComponent jc, InventoryUI ui, int col) {
		this();
		final String METHOD_NAME="LeamonAutoTableCellsValueSuggestor";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		this.ui  = ui;
		if(jc instanceof JTable){
			tableParent = (JTable)jc;
		}else{
			return ;
		}
		activeColumn = col;
		jc.addFocusListener(this);
		jc.addKeyListener(this);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	public void setActiveColumn(int col) {
		activeColumn = col; 
	}
	private void setButtons() {
		JButton b[] = {new JButton("Up"), new JButton("Down"), new JButton("Select")};
		char c[] = {'U', 'D', 'S'};
		String txt[] = {"Move Up (F3)","Move Down (F4)","Select (F5)"};
		JPanel p = new JPanel(new FlowLayout());
		for(int i=0; i<b.length; i++) {
			b[i].addActionListener(this);
			b[i].setMnemonic(c[i]);
			b[i].setToolTipText(txt[i]);
			p.add(b[i]);
		}
		this.getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	public void setItems(java.util.List<E> init) {
		val.clear();
		val.addAll(init);
	}
	
	private void moveUp() {
		final String METHOD_NAME="moveUp";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(!this.isVisible()) return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index=index>0 ? index-1:tempVector.size()-1);
		lst.validate();
		lst.scrollRectToVisible(lst.getCellBounds(index, index-1<0?0:index-1)); 
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	private void moveDown() {
		final String METHOD_NAME="moveDown";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(!this.isVisible()) return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index=index<tempVector.size()-1 ? index+1:0);
		lst.validate();
		if(index == 0)
			lst.scrollRectToVisible(lst.getCellBounds(0,1));
		else{
			lst.scrollRectToVisible(lst.getCellBounds(index, index+1<0?0:index+1));
			//tableParent.requestFocus();
		}
		//else
			//lst.scrollRectToVisible(lst.getCellBounds(index+1<tempVector.size()-1?index+1:index, 
				//	index+1<tempVector.size()-1?index+1:index));
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	private void select(boolean enterPressed) {
		final String METHOD_NAME="select";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(!this.isVisible()) return;
		if(tableParent != null) {
			tableSelection();
		}
		this.setVisible(false);
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	private void tableSelection() {
		final String METHOD_NAME="tableSelection";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		int r = tableParent.getSelectedRow(), c = tableParent.getSelectedColumn();
		try { 
			tableParent.getCellEditor(r,c).stopCellEditing(); 
		} catch(Exception ex) {
			LOGGER.error(ex.toString());
		}
		lst.validate();
		StockItem item = (StockItem) lst.getSelectedValue();
		setInventoryStockItem(item,ui);
		typed.setLength(0);
		this.setVisible(false);
		tableParent.repaint();
		typed.setLength(0);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	public void actionPerformed(ActionEvent ae) {
		final String METHOD_NAME="actionPerformed";
		
		if(activeColumn != tableParent.getSelectedColumn()){
			return;
		}
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		String com = ae.getActionCommand().toLowerCase();
		if(com.equals("up"))
			moveUp();
		else if(com.equals("down"))
			moveDown();
		else if(com.equals("select"))
			select(false);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	public void focusLost(FocusEvent fe) {
		final String METHOD_NAME="focusLost";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));

		this.setVisible(false);
		typed.setLength(0);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	public void focusGained(FocusEvent fe) {
		final String METHOD_NAME="focusGained";
		
		if(tableParent!=null && activeColumn != tableParent.getSelectedColumn()){
			return;
		}
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(fe.getSource() == lst)
			tableParent.requestFocus();
		else if( (fe.getSource() == tableParent && 
				tableParent.getSelectedColumn() == activeColumn) )
			populateList();
	}
	public void keyPressed(KeyEvent ke) {
		final String METHOD_NAME="actionPerformed";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		int kc = ke.getKeyCode();
		int index = lst.getSelectedIndex();
		if(kc == KeyEvent.VK_F4 )
			moveUp();
		else if(kc == KeyEvent.VK_F3 )
			moveDown();
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	public void keyReleased(KeyEvent ke) {
		final String METHOD_NAME="keyReleased";
		
		if(tableParent!=null && activeColumn != tableParent.getSelectedColumn()){
			return;
		}
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(tableParent != null) {
			int r = tableParent.getSelectedRow(), c = tableParent.getSelectedColumn();
			this.setVisible(c == activeColumn);
			if(r < 0 || c < 0 || c != activeColumn)
				return;
		}
		int kc = ke.getKeyCode();
		if(kc == KeyEvent.VK_F5)
			select(true);
		else if(tableParent != null && tableParent.getSelectedColumn() == activeColumn)
			populateList();
		else {
			this.setVisible(false);
			typed.setLength(0);
		}
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	public void keyTyped(KeyEvent ke) {
		final String METHOD_NAME="keyTyped";
		if(tableParent!=null && activeColumn != tableParent.getSelectedColumn()){
			return;
		}
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		char c = ke.getKeyChar();
		if(c != '\r' && c != '\n' && c != '\t' && c != '\b')
			typed.append(ke.getKeyChar());
		else typed.setLength(0);
		if(c == '\b')
			typed.setLength(typed.length()>0?typed.length()-1:0);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	private void populateList() {
		final String METHOD_NAME="populateList";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		String txt = "";
		int r=0, c=0;
		if(tableParent != null) {
			r = tableParent.getSelectedRow();
			c = tableParent.getSelectedColumn();
			if(r < 0 || c < 0 || c != activeColumn)
				return;
			txt = tableParent.getValueAt(r, c) != null? (String)tableParent.getValueAt(r, c):"";
			txt += typed.toString();
			
			if(txt == null){
				txt = "";
			}
		}
		
		tempVector.clear();
		int index = lst.getSelectedIndex();
		filter(txt);
		
		lst.setListData(tempVector);
		lst.validate();
		if(tempVector.size() == 0) {
			this.setVisible(false);
			return;
		}
		this.pack();
		Point p = new Point(0,0);
		p = tableParent.getLocationOnScreen(); 
		if(tableParent != null) {
			Rectangle rect = tableParent.getCellRect(r, c, true);
			p.x += rect.x;
			p.y += rect.y;
		}
		Rectangle bound = GraphicsEnvironment.getLocalGraphicsEnvironment(). getMaximumWindowBounds();
		if(p.y-this.getHeight()>0)
			this.setLocation(p.x, p.y-this.getHeight());
		else if(p.y+this.getHeight() > bound.y+bound.height)
			this.setLocation(p.x, bound.y+bound.height-this.getHeight());
		else this.setLocation(p.x, p.y);

		if(!this.isVisible())
			this.setVisible(true);
		lst.setSelectedIndex(index >= tempVector.size() || index<0 ?0:index);
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	private Optional getPattern(String text) {
		try {
			return Optional.ofNullable("(?i)"+text)
					.filter(s -> !s.isEmpty())
					.map(Pattern::compile);
		} catch (PatternSyntaxException ex) {
			return Optional.empty();
		}
	}
	private void filter(String text) {
		final String METHOD_NAME="filter";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		//((StockItem) item).
		
		filterVal(text);
		
		if(tempVector.size()==0){
			TableInventoryItemModel tableInventoryItemModel = (TableInventoryItemModel)ui.getTblInventory().getModel();
			int r = ui.getTblInventory().getSelectedRow();
			InventoryInfo inventoryInfo = tableInventoryItemModel.getInventoryInfos().get(r);
			text = inventoryInfo.getSerachValue();
			filterVal(text);
		}
		
		if(tempVector.size()==0){
			filterVal(null);
		}
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	private void setInventoryStockItem(StockItem info, InventoryUI inventoryUI){
		final String METHOD_NAME="setInventoryStockItem";
		LOGGER.debug(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside."));
		
		LOGGER.debug(" StockInfo : ".concat(info.toString()));
		
		TableInventoryItemModel tableInventoryItemModel = (TableInventoryItemModel)inventoryUI.getTblInventory().getModel();
		int r = inventoryUI.getTblInventory().getSelectedRow();
		if(r == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(inventoryUI.getTblInventory().getRowSorter() != null){
			LOGGER.debug(" Row sorter is not null");
			r = inventoryUI.getTblInventory().getRowSorter().convertRowIndexToModel(r);
		}
		
		int c = inventoryUI.getTblInventory().getSelectedColumn();
		LOGGER.debug("Selected row ["+r+"] column ["+c+"]");
		
		InventoryInfo inventoryInfo = tableInventoryItemModel.getInventoryInfos().get(r);
		if(inventoryInfo.getIsTotalRow()){
			LOGGER.warn("Total table row can't be replace");
			return ;
		}
		inventoryInfo.setDescOfGoods(info.getName().concat(LeamonERPConstants.HYPHEN).concat(info.getProductCode()).concat(LeamonERPConstants.SPACE).concat(info.getFinish()));
		inventoryInfo.setStockItemID(info.getId());
		
		String unit = info.getUnit();
		if(unit!=null && unit.equals("inch")){
			unit="\"";
		}
		inventoryInfo.setSize(info.getSize().concat(unit));
		
		String name = info.getName();
		String productoCode = info.getProductCode();
		String size = info.getSize();
		
		String finish = info.getFinish();
		String shape= "(".concat(info.getShape()).concat(")");
		
		String displayName = name.concat(LeamonERPConstants.HYPHEN)
							.concat(productoCode).concat(LeamonERPConstants.SPACE)
							.concat(size).concat(unit).concat(LeamonERPConstants.SPACE)
							.concat(shape).concat(LeamonERPConstants.SPACE)
							.concat(finish);
		inventoryInfo.setSerachValue(displayName);
		inventoryUI.getTblInventory().changeSelection(r, c+2, false, false);
		inventoryUI.getTblInventory().repaint();
		
		LOGGER.debug(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside."));
	}
	
	public void filterVal(String text){
		final String METHOD_NAME="filterVal";
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		getPattern(text).ifPresent(pattern -> {
			val.stream()
			.filter(item -> ((Pattern) pattern).matcher((item instanceof StockItem ? (((StockItem) item).getName()).concat(LeamonERPConstants.HYPHEN)
					.concat(((StockItem) item).getProductCode().concat(LeamonERPConstants.SPACE)).concat(((StockItem) item).getSize())
					.concat(((StockItem) item).getUnit()!=null && ((StockItem) item).getUnit().equals("inch")? "\"" : ((StockItem) item).getUnit()).concat(LeamonERPConstants.SPACE)
					.concat(((StockItem) item).getShape()).concat(LeamonERPConstants.SPACE).concat(((StockItem) item).getFinish())
					: "")).find() )
			.forEach(tempVector::add);
		});
		
		LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
	}
}