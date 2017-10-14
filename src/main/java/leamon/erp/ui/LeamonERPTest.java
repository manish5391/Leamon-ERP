package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import leamon.erp.util.LeamonERPConstants;

public class LeamonERPTest extends JFrame {

	private JPanel contentPane;
	private String path;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeamonERPTest frame = new LeamonERPTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LeamonERPTest() {
		setTitle("Lemon ERP System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 255, 255), 3, true));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 0, 128), new Color(72, 61, 139),
				new Color(152, 251, 152), new Color(165, 42, 42)));
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JTabbedPane tabbedPaneStock = new JTabbedPane(JTabbedPane.TOP);
		
		
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGap(21)
					.addComponent(tabbedPaneStock, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1004, Short.MAX_VALUE))
		);
		gl_desktopPane.setVerticalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGap(22)
					.addComponent(tabbedPaneStock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(409, Short.MAX_VALUE))
		);
		
		JPanel pnlStockManager = new JPanel();
		pnlStockManager.setBorder(new CompoundBorder(new LineBorder(new Color(58, 110, 165), 2, true), 
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), LeamonERPConstants.TAB_STOCK_MANAGER, TitledBorder.CENTER, TitledBorder.TOP, 
						new Font("DialogInput", Font.BOLD, 16), new Color(51, 51, 51))));
		pnlStockManager.setBackground(new Color(255, 255, 255));
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_STOCK_MANAGER_TAB;
		tabbedPaneStock.addTab(LeamonERPConstants.TAB_STOCK_MANAGER, new ImageIcon(path), pnlStockManager, null);
		tabbedPaneStock.setBackgroundAt(0, Color.CYAN);
		
		JButton btnSTMDelete = new JButton(LeamonERPConstants.DELETE);
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_DELETE_BUTTON;
		btnSTMDelete.setIcon(new ImageIcon(path));
		btnSTMDelete.setForeground(new Color(0, 128, 0));
		btnSTMDelete.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSTMDelete.setBackground(Color.WHITE);
		
		JButton btnSTMEdit = new JButton(LeamonERPConstants.EDIT);
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_EDIT_BUTTON;
		btnSTMEdit.setIcon(new ImageIcon(path));
		btnSTMEdit.setForeground(new Color(0, 128, 0));
		btnSTMEdit.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSTMEdit.setBackground(Color.WHITE);
		
		JButton btnSMNew = new JButton(LeamonERPConstants.NEW);
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_ADD_BUTTON;
		btnSMNew.setIcon(new ImageIcon(path));
		btnSMNew.setForeground(new Color(0, 128, 0));
		btnSMNew.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSMNew.setBackground(Color.WHITE);
		GroupLayout gl_pnlStockManager = new GroupLayout(pnlStockManager);
		gl_pnlStockManager.setHorizontalGroup(
			gl_pnlStockManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStockManager.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_pnlStockManager.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnSTMDelete, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSTMEdit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSMNew, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_pnlStockManager.setVerticalGroup(
			gl_pnlStockManager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStockManager.createSequentialGroup()
					.addGap(29)
					.addComponent(btnSMNew, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSTMEdit, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSTMDelete, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlStockManager.setLayout(gl_pnlStockManager);
		
		JPanel panelStockItemMgr = new JPanel();
		panelStockItemMgr.setBorder(new CompoundBorder(new LineBorder(new Color(58, 110, 165), 2, true), 
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), LeamonERPConstants.TAB_STOCK_ITEM_MANAGER, 
				TitledBorder.CENTER, TitledBorder.TOP, new Font("DialogInput", Font.BOLD, 16), new Color(51, 51, 51))));
		
		panelStockItemMgr.setBackground(new Color(245, 245, 245));
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_STOCK_ITEM_MANAGER_TAB;
		tabbedPaneStock.addTab(LeamonERPConstants.TAB_STOCK_ITEM_MANAGER, new ImageIcon(path), panelStockItemMgr, null);
		
		JButton btnSIMDelete = new JButton(LeamonERPConstants.DELETE);
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_DELETE_BUTTON;
		btnSIMDelete.setIcon(new ImageIcon(path));
		btnSIMDelete.setForeground(new Color(0, 128, 0));
		btnSIMDelete.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSIMDelete.setBackground(Color.WHITE);
		
		JButton btnSIMEdit = new JButton(LeamonERPConstants.EDIT);
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_EDIT_BUTTON;
		btnSIMEdit.setIcon(new ImageIcon(path));
		btnSIMEdit.setForeground(new Color(0, 128, 0));
		btnSIMEdit.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSIMEdit.setBackground(Color.WHITE);
		
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_ADD_BUTTON;
		JButton btnSIMNew = new JButton("New");
		btnSIMNew.setForeground(new Color(0, 128, 0));
		btnSIMNew.setIcon(new ImageIcon(path));
		btnSIMNew.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnSIMNew.setBackground(Color.WHITE);
		GroupLayout gl_panelStockItemMgr = new GroupLayout(panelStockItemMgr);
		gl_panelStockItemMgr.setHorizontalGroup(
			gl_panelStockItemMgr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStockItemMgr.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panelStockItemMgr.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnSIMDelete, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSIMEdit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSIMNew, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_panelStockItemMgr.setVerticalGroup(
			gl_panelStockItemMgr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStockItemMgr.createSequentialGroup()
					.addGap(29)
					.addComponent(btnSIMNew, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSIMEdit, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSIMDelete, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelStockItemMgr.setLayout(gl_panelStockItemMgr);
		
		
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_ADD_BUTTON;
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_EDIT_BUTTON;
		path=System.getProperty("user.dir")+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar
				+LeamonERPConstants.IMG_DELETE_BUTTON;
		desktopPane.setLayout(gl_desktopPane);
		
		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(sc);

		Dimension minimumSize = new Dimension(200, 200);
		this.setMinimumSize(minimumSize);
	}
}
