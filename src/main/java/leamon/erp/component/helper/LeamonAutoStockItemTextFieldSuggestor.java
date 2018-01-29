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
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXList;

import com.google.common.base.Strings;

import leamon.erp.model.StockItem;
import leamon.erp.ui.InvoiceUI;
import leamon.erp.ui.StockItemQuantityUI;
import leamon.erp.ui.model.StockItemsInvoiceListCellRender;

public class LeamonAutoStockItemTextFieldSuggestor  <T extends List<E>, E> extends JWindow implements KeyListener, FocusListener, ActionListener {
	JTextComponent parent = null;
	JXList lst = null;
	java.util.TreeSet<E> val = new java.util.TreeSet<E>();
	java.util.Vector<E> tempVector = new java.util.Vector<E>(1, 1);
	//InvoiceUI ui;
	//StockItemQuantityUI stockItemQuantityUI;
	JInternalFrame ui;
	String txt;
	
	StringBuffer typed = new StringBuffer();

	public LeamonAutoStockItemTextFieldSuggestor() {
		lst = new JXList();
		lst.setCellRenderer(new StockItemsInvoiceListCellRender());
		lst.addKeyListener(this);
		lst.addFocusListener(this); 
		this.getContentPane().add(new JScrollPane(lst), BorderLayout.CENTER);
		setButtons();
		
		((JPanel)this.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
		lst.setToolTipText("Press F5, F6 to navigate, F7 to select");
	}
	/*public LeamonAutoStockItemTextFieldSuggestor(JComponent jc, InvoiceUI ui) {
		this();
		this.ui  = ui;
		parent = (JTextComponent) jc;
		jc.addFocusListener(this);
		jc.addKeyListener(this);
	}*/

	public LeamonAutoStockItemTextFieldSuggestor(JComponent jc, JInternalFrame frameui) {
		this();
		/*if(frameui instanceof InvoiceUI){
			this.ui  = (InvoiceUI)frameui;
		}else if(frameui instanceof StockItemQuantityUI){
			this.stockItemQuantityUI = (StockItemQuantityUI)frameui;
		}*/
		this.ui = frameui;
		parent = (JTextComponent) jc;
		jc.addFocusListener(this);
		jc.addKeyListener(this);
	}
	
	private void setButtons() {
		JButton b[] = {new JButton("Up"), new JButton("Down"), new JButton("Select")};
		char c[] = {'U', 'D', 'S'};
		String txt[] = {"Move Up (F5)","Move Down (F6)","Select (F7)"};
		JPanel p = new JPanel(new FlowLayout());
		for(int i=0; i<b.length; i++) {
			b[i].addActionListener(this);
			b[i].setMnemonic(c[i]);
			b[i].setToolTipText(txt[i]);
			p.add(b[i]);
		}
		this.getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	public void addItems(java.util.List<E> init) { 
		val.addAll(init); 
	}
	public void setItems(java.util.List<E> init) {
		val.clear();
		val.addAll(init);
	}

	private void moveUp() {
		if(!this.isVisible()) return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index=index>0 ? index-1:tempVector.size()-1);
		lst.validate();
		lst.scrollRectToVisible(lst.getCellBounds(index, index-1<0?0:index-1)); 
	}
	private void moveDown() {
		if(!this.isVisible()) return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index=index<tempVector.size()-1 ? index+1:0);
		lst.validate();
		if(index == 0)
			lst.scrollRectToVisible(lst.getCellBounds(0,1));
		else
			lst.scrollRectToVisible(lst.getCellBounds(index+1<tempVector.size()-1?index+1:index, 
					index+1<tempVector.size()-1?index+1:index));
	}
	private void select(boolean enterPressed) {
		if(!this.isVisible()) 
		{
			if(ui instanceof InvoiceUI){
				if(!Strings.isNullOrEmpty(((InvoiceUI)ui).getTextFieldProductDesc().getText())){
					((InvoiceUI)ui).getTextFieldProductSize().requestFocus();
				}
			}
			
			if(ui instanceof StockItemQuantityUI){
				if(!Strings.isNullOrEmpty(((StockItemQuantityUI)ui).getTxtName().getText())){
					((StockItemQuantityUI)ui).getTxtSize().requestFocus();
				}
			}
			return;
		}
		if(parent == null) {
			return;
		}
		
		StockItem info = (StockItem) lst.getSelectedValue();
		parent.setText(info.getName());
		if(ui instanceof InvoiceUI){
			setItemInfoData(((InvoiceUI)ui),info);
		}
		if(ui instanceof StockItemQuantityUI){
			setItemInfoData(((StockItemQuantityUI)ui),info);
		}
		this.setVisible(false);
	}

	public void actionPerformed(ActionEvent ae) {
		String com = ae.getActionCommand().toLowerCase();
		if(com.equals("up"))
			moveUp();
		else if(com.equals("down"))
			moveDown();
		else if(com.equals("select"))
			select(false);
	}
	public void focusLost(FocusEvent fe) {
		this.setVisible(false);
		typed.setLength(0);
	}
	public void focusGained(FocusEvent fe) {
		if(fe.getSource() == lst){
			parent.requestFocus();
			if(!Strings.isNullOrEmpty(parent.getText())){
				parent.selectAll();
			}
		}
		else if(fe.getSource() == parent ){
			populateList();
		}
	}
	public void keyPressed(KeyEvent ke) {
		int kc = ke.getKeyCode();
		int index = lst.getSelectedIndex();
		if(kc == KeyEvent.VK_F5 || (parent instanceof JTextField && kc==KeyEvent.VK_UP))
			moveUp();
		else if(kc == KeyEvent.VK_F6 || (parent instanceof JTextField && kc==KeyEvent.VK_DOWN))
			moveDown();
	}
	public void keyReleased(KeyEvent ke) {

		int kc = ke.getKeyCode();
		if((kc == KeyEvent.VK_F7 || kc == KeyEvent.VK_ENTER) || (parent instanceof JTextField && kc==KeyEvent.VK_ENTER))
			select(true);
		else if(parent != null)
			populateList();
		else {
			this.setVisible(false);
			typed.setLength(0);
		}
	}
	public void keyTyped(KeyEvent ke) { 
		char c = ke.getKeyChar();
		if(c != '\r' && c != '\n' && c != '\t' && c != '\b')
			typed.append(ke.getKeyChar());
		else typed.setLength(0);
		if(c == '\b')
			typed.setLength(typed.length()>0?typed.length()-1:0);
	}
	private void populateList() {
		txt = "";
		int r=0, c=0;

		txt = parent.getText();
		if(txt!=null && txt.length()==0){
			return;
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
		if(parent != null) {
			try { p = parent.modelToView(parent.getCaretPosition()).getLocation(); }
			catch(Exception ex) {}
			Point loc = parent.getLocationOnScreen();
			p = new Point(p.x+loc.x, p.y+loc.y);
		}


		Rectangle bound = GraphicsEnvironment.getLocalGraphicsEnvironment(). getMaximumWindowBounds();
		if(p.y-this.getHeight()>0)
			this.setLocation(p.x, p.y-this.getHeight());
		else if(p.y+this.getHeight() > bound.y+bound.height)
			this.setLocation(p.x, bound.y+bound.height-this.getHeight());
		else this.setLocation(p.x, p.y+20);

		if(!this.isVisible())
			this.setVisible(true);
		lst.setSelectedIndex(index >= tempVector.size() || index<0 ?0:index);
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
//	private void filter(String text, JList list) {
	private void filter(String text) {
		getPattern(text).ifPresent(pattern -> {
			val.stream()
			//.filter(item -> ((Pattern) pattern).matcher((item instanceof StockItem ? (((StockItem) item).getName()).concat(" ").concat(((StockItem) item).getSize()) : "")).find() )
			.filter(item -> ((Pattern) pattern)
				.matcher((item instanceof StockItem ? 
					(((StockItem) item).getName())
					.concat(Strings.isNullOrEmpty(((StockItem) item).getProductCode()) ? "" : ((StockItem) item).getProductCode())
					.concat(((StockItem) item).getSize()) 
				: "")).find() )
			.forEach(tempVector::add);
		});
		
	}
	
	private void setItemInfoData(InvoiceUI invoiceUI ,StockItem info){
		invoiceUI.setStockItemInfo(info);
	}
	private void setItemInfoData(StockItemQuantityUI stockItemQuantityUI ,StockItem info){
		stockItemQuantityUI.setStockItemInfo(info);
	}
}