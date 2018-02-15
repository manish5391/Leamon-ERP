package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import org.jdesktop.swingx.JXSearchField;

import leamon.erp.db.StateCityDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.StateCityInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.model.TableStateCityInfoModel;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHyperlink;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import org.jdesktop.swingx.JXTable;
import javax.swing.JTable;

/**
 * State City manager UI
 * @date 15 Feb,2018
 * @author Manish Kumar Mishra
 */
public class StateCityManagerUI extends JInternalFrame {

	static final Logger LOGGER = Logger.getLogger(StateCityManagerUI.class);
	
	private JXTable table;
	private JXSearchField searchField;
	public StateCityManagerUI() {
		setTitle("State & City Manager");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(3, 30, 657, 660);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Operation", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 650, 73);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnAddItem = new JButton();
		btnAddItem.setBackground(Color.WHITE);
		btnAddItem.setBounds(10, 17, 75, 49);
		btnAddItem.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_ADD_BUTTON)));
		panel.add(btnAddItem);
		
		JButton btnedit = new JButton();
		btnedit.setBackground(Color.WHITE);
		btnedit.setActionCommand("EditStockItem");
		btnedit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnedit.setBounds(95, 17, 75, 49);
		panel.add(btnedit);
		
		JButton btnView = new JButton();
		btnView.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_VIEW_BUTTON)));
		btnView.setBackground(Color.WHITE);
		btnView.setActionCommand("ViewStockItem");
		btnView.setBounds(180, 17, 89, 47);
		panel.add(btnView);
		
		JButton btnDelete = new JButton();
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setActionCommand("DeleteStockItem");
		btnDelete.setBounds(280, 17, 75, 49);
		panel.add(btnDelete);
		
		JButton btnPrint = new JButton();
		btnPrint.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_PRINT_BUTTON)));
		btnPrint.setBackground(Color.WHITE);
		btnPrint.setActionCommand("PrintStockItem");
		btnPrint.setBounds(366, 17, 75, 49);
		panel.add(btnPrint);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 72, 650, 557);
		getContentPane().add(scrollPane);
		
		table = new JXTable();
		
		StateCityDaoImpl daoImpl = StateCityDaoImpl.getInstance();
		List<StateCityInfo> stateCityInfos = new ArrayList<StateCityInfo>();
		try{
			stateCityInfos = daoImpl.getItemList();
		}catch(Exception e){
			LOGGER.error(e);
		}
		TableStateCityInfoModel stateCityInfoModel = new TableStateCityInfoModel(stateCityInfos);
		stateCityInfoModel.setStateCityInfos(stateCityInfos);
		table.setModel(stateCityInfoModel);
		
		table.setRowHeight(table.getRowHeight()+20);
		
		table.setAutoCreateRowSorter(true);
		table.setColumnControlVisible(true);
		table.packAll();
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);

		searchField = new JXSearchField("Search");
		searchField.setName(LeamonERPConstants.TEXTFIELD_NAME_STATE_CITY_SERACH);
		searchField.setFont(new Font("Courier New", Font.BOLD, 26));
		searchField.setColumns(10);
		searchField.setBounds(450, 17, 190, 37);
		panel.add(searchField);
		searchField.addFocusListener(new FocusEventHandler(table));
		
	}
}
