package leamon.erp.util;

import java.awt.Color;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author mmishra
 * @date MAY 18,2017
 */
public interface LeamonERPConstants {

	public static final Integer VERTICAL_GAP 	= 	50;
	public static final Integer HORIZONTAL_GAP 	=	50;
	
	
	public static final String IMG_SAVE="";
	
	public static final String IMG_SAVE_BUTTON="save_button.png";
	public static final String IMG_EDIT_BUTTON="edit_button.png";
	public static final String IMG_DELETE_BUTTON="delete_button.png";
	public static final String IMG_PRINT_BUTTON="print_button.png";//3.5 ghanshyam code for print stcok item
	public static final String IMG_ADD_BUTTON="add_button.png";
	public static final String IMG_SAVE_BTN="save_btn.png";
	public static final String IMG_CLEAR_BUTTON = "clear_button.png";
	public static final String IMG_VIEW_BUTTON="view.png";
	public static final String IMG_PERCENTAGE_LABEL= "percentage-1.png";
	
	public static final String BILLING = "bill-2.jpg";
	public static final String STOCK_IMAGE ="stock-3.jpg";
	public static final String PARTY_IMAGE ="tradingpartner.png";
	public static final String COMPANY_IMAGE ="company.jpg";
	
	public static final String IMG_STOCK_ITEM_MANAGER_TAB="stockItemtab.png";
	public static final String IMG_STOCK_MANAGER_TAB="stockManagerTab.png";
	
	public static final String IMG_POPUP_MENU_REFRESH="popmenu_refreh.jpg";
	public static final String IMG_POPUP_MENU_DELETE="popup_delete_button.png";
	public static final String IMG_POPUP_MENU_VIEW="popupmenu_view.png";
	
	public static final String RESOURCE="resource";
	
	public static final String DELETE = "Delete";
	public static final String EDIT = "Edit";
	public static final String NEW = "New";
	public static final String SAVE = "Save";
	
	public static final String TAB_STOCK_MANAGER= "Stock Manager";
	public static final String TAB_STOCK_ITEM_MANAGER= "Stock Item Manager";
	
	
	public static final String MYBATIS_CONFIG_FILE="mybatis-config.xml";
	
	Border TEXT_FILED_BOTTOM_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
	Border TEXT_FILED_BOTTOM_BORDER_YELLOW = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.YELLOW);
	Border TEXT_FILED_BOTTOM_BORDER_WHITE = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE);
	
	String IMAGE_DIR="StockItemPics";
	String ACCOUNT_IMAGE_DIR="AccountInfoPics";
	String LEAMON_IMAGE_DIR="images";
	String USER_DIR = System.getProperty("user.dir");
	String IMAGE_PATH=USER_DIR+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar+IMAGE_DIR+File.separatorChar;
	String IMAGE_PATH_ACCOUNT=USER_DIR+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar+ACCOUNT_IMAGE_DIR+File.separatorChar;
	String path=USER_DIR+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar;
	
	String IMAGE_PATH_LEAMON=USER_DIR+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar+LEAMON_IMAGE_DIR+File.separatorChar;
	
	String EMPTY_STR = "";
	String SPACE = "  ";
	
	public static String BUTTON_ACTION_ADD_STOCK_ITEM="AddStockItem";
	public static String BUTTON_ACTION_DELETE_STOCK_ITEM="DeleteStockItem";
	public static String BUTTON_ACTION_PRINT_STOCK_ITEM="PrintStockItem";//3.5 ghanshyam code for print stcok item
	public static String BUTTON_ACTION_VIEW_STOCK_ITEM="ViewStockItem";
	public static String BUTTON_ACTION_EDIT_STOCK_ITEM="EditStockItem";
	public static String BUTTON_ACTION_UPLOAD_IMAGE_STOCK_ITEM="UploadImage";
	public static String BUTTON_ACTION_UPLOAD_IMAGE_ACCOUNT_INFO="UploadImage";
	
	public static String BUTTON_ACTION_ADD_ACCOUNT_INFO="AddAccountInfo";
	public static String BUTTON_ACTION_DELETE_ACCOUNT_INFO="DeleteAccountInfo";
	public static String BUTTON_ACTION_VIEW_ACCOUNT_INFO="ViewAccountInfo";
	public static String BUTTON_ACTION_EDIT_ACCOUNT_INFO="EditAccountInfo";
	
	public static String MENU_ACTION_REFRESH_STOCK_ITEM="Refresh";
	public static String MENU_ACTION_VIEW_STOCK_ITEM="View";
	public static String MENU_ACTION_DELETE_STOCK_ITEM="Delete";
	
	public static String MENU_ACTION_REFRESH_ACCOUNT_INFO="Refresh";
	public static String MENU_ACTION_VIEW_ACCOUNT_INFO="View";
	public static String MENU_ACTION_DELETE_ACCOUNT_INFO="Delete";
	
	public static String MENU_ACTION_REFRESH="Refresh";
	public static String MENU_ACTION_VIEW="View";
	public static String MENU_ACTION_DELETE="Delete";
	
	String TEXTFIELD_NAME_FINISH="txtFinish";
	String TEXTFIELD_NAME_SIZE="txtSize";
	String TEXTFIELD_NAME_STOCK_NAME="txtStockName";
	String TEXTFIELD_NAME_PRODUCT_CODE="txtProductCode";
	String TEXTFIELD_NAME_UNIT="textUnit";
	String TEXTFIELD_NAME_IMAGE="textImage";
	String TEXTFIELD_NAME_SHAPE="txtShape";
	String TEXTFIELD_NAME_SALE_UNIT="txtSaleUnit";
	String TEXTFIELD_NAME_DESCRIPTION = "txtDescription";
	String TEXTFIELD_NAME_SOCK_ITEM_SERACH = "textSearchField";
	String TEXTFIELD_NAME_ACCOUNT_INFO_SERACH = "textSearchFieldAccountInfo";

	/*Account info*/
	String TEXTFIELD_ACCOUNT_PHONE="txtAccountPhone";
	String TEXTFIELD_ACCOUNT_ALTERNATE_PHONE="txtAccountAlternatePhone";//ghan code
	String TEXTFIELD_ACCOUNT_NAME="txtAccountName";
	String TEXTFIELD_ACCOUNT_NICK_NAME="txtAccountNickName";
	String TEXTFIELD_ACCOUNT_GST="txtAccountGST";
	String TEXTFIELD_ACCOUNT_TRANSPORT="txtAccountTransport";
	String TEXTFIELD_ACCOUNT_HOUSE="txtAccountHouse";
	String TEXTFIELD_ACCOUNT_STREET="txtAccountStreet";
	String TEXTFIELD_ACCOUNT_CITY="txtAccountCity";
	String TEXTFIELD_ACCOUNT_STATE="txtAccountState";
	String TEXTFIELD_ACCOUNT_PINCODE="txtAccountPin";
	String TEXTFIELD_ACCOUNT_COUNTRY="txtAccountCountry";
	String TEXTFIELD_ACCOUNT_LANDMARK="txtAccountLandmark";
	String TEXTFIELD_ACCOUNT_ENGAGEDDATE="txtAccountEngageDate";
	String TEXTFIELD_ACCOUNT_PAN="txtAccountPAN";
	String TEXTFIELD_ACCOUNT_LICENCE="txtAccountLicence";
	
	/*Inventory*/
	String TEXTFIELD_INVENTORY_ACCOUNT_NAME="txtInventoryAccountName";
	String TEXTFIELD_INVENTORY_ACCOUNT_CITY="txtInventoryAccountCity";
	String TEXTFIELD_INVENTORY_ACCOUNT_MARKA="txtInventoryAccountMarka";
	String TEXTFIELD_INVENTORY_ACCOUNT_TRANSPORT="txtInventoryAccountTransport";
	String TEXTFIELD_INVENTORY_CATRUN_A="txtInventoryCartunA";
	String TEXTFIELD_INVENTORY_CATRUN_B="txtInventoryCartunB";
	String TEXTFIELD_INVENTORY_BILL_NO="txtInventoryBillNumber";
	String TEXTFIELD_INVENTORY_BILL_DATE="txtInventoryBillDate";
	String TEXTFIELD_INVENTORY_BOOKED_BY="txtInventoryBookedBy";
	
	String TEXTAREA_DESCRIPTION="txtAreaDesc";
	
	/*Grand total Calculator*/
	String TEXTFIELD_GTCALC_TAX = "gstTAX";
	String TEXTFIELD_GTCALC_TOTAL = "gtTotal";
	String TEXTFIELD_GTCALC_AMOUNT = "gtAmount";
	String TEXTFIELD_GTCALC_GRAND_TOTAL = "gtGrandTotal";
	
	String TABLE_HEADER_STOCK_ITEM_NAME="NAMES";
	String TABLE_HEADER_STOCK_ITEM_PRODUCT_CODES="PRODUCTS CODE";
	String TABLE_HEADER_STOCK_ITEM_SIZE="SIZE";
	String TABLE_HEADER_STOCK_ITEM_FINISHES="FINISHES";
	String TABLE_HEADER_STOCK_ITEM_UNITS="UNITS";
	String TABLE_HEADER_STOCK_ITEM_SHAPES="SHAPES";
	String TABLE_HEADER_STOCK_ITEM_SALE_UNITS="SALE UNITS";
	String TABLE_HEADER_STOCK_ITEM_DESCRIPTION="DESCRIPTIONS";

	String TABLE_STOCK_ITEMS="TableStockItems";
	String TABLE_ACCOUNT_INFO_LIST="TableAccountList";
	String TABLE_INVENTORY="TableInventory";
	
	final Integer TABLE_INVENTORY_SNO = 0;
	Integer TABLE_INVENTORY_DESC_OF_GOODS = 1;
	Integer TABLE_INVENTORY_SIZE = 1;
	Integer TABLE_INVENTORY_QTY = 3;
	Integer TABLE_INVENTORY_RATE = 4;
	Integer TABLE_INVENTORY_DISCOUNT = 5;
	Integer TABLE_INVENTORY_VALUE_OF_GOODS = 6;
	
	
	String TABLE_HEADER_SNO="S NO.";
	String TABLE_HEADER_INVENTORY_DESC_OF_GOODS="Description of Goods";
	
	String TABLE_HEADER_INVENTORY_SIZE="Size";
	String TABLE_HEADER_INVENTORY_QTY_UNIT="Qty/Unit";
	String TABLE_HEADER_INVENTORY_RATE="Rate";
	String TABLE_HEADER_INVOICE_RATE="Rate";
	String TABLE_HEADER_INVENTORY_DISC="Disc";
	String TABLE_HEADER_INVENTORY_VALUE_OF_GOODS="Value Of Goods";
	
	String TABLE_HEADER_INVOICE_DESC="Description";
	String TABLE_HEADER_INVOICE_SIZE="Size";
	String TABLE_HEADER_INVOICE_QTY="Qty";
	String TABLE_HEADER_INVOICE_UNIT="Unit";
	String TABLE_HEADER_INVOICE_AMOUNT="Amount";
	String TABLE_HEADER_INVOICE_TD="TD%";
	
	/* Table Header - Payment Received */
	String TABLE_HEADER_INVOICE_NO="Invoice No";
	String TABLE_HEADER_AMOUNT="Amount";
	String TABLE_HEADER_ADJUST="Adjust";
	String TABLE_HEADER_STATUS="STATUS";
	String TABLE_HEADER_BILL_DATE="Invoice Date";
	String TABLE_HEADER_RECEIVED_PAYMENT="Received";
	String TABLE_HEADER_REMAINING_PAYMENT="Remaining";
	String TYPE_BILL_AMOUNT_ADJUSTMENT="BA"; //Billing Amount
	String TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT="WA"; //without billing amount
	String TABLE_HEADER_BILL_NO="Bill No.";
	String TABLE_HEADER_INVOICE_DATE="Invoice Date";
	String TABLE_HEADER_PARTY_NAME="Party Name";
	String TABLE_HEADER_B_AMOUNT="B Amt.";
	String TABLE_HEADER_W_AMOUNT="W Amt.";
	String TABLE_HEADER_B_STATUS="B Status";
	String TABLE_HEADER_W_STATUS="W Status";
	String TABLE_HEADER_G_TOTAL="G.Total";
	String TABLE_HEADER_B_RECEIVED_AMOUNT="B Rec. Amt.";
	String TABLE_HEADER_W_RECEIVED_AMOUNT="W Rec. Amt";
	String TABLE_HEADER_B_REMARK="B Remark";
	String TABLE_HEADER_W_REMARK="W Remark";
	String TABLE_HEADER_DESC="Desc";
	
	String ACTION_COMMAND_="DESCRIPTIONS";
	
	
	String NO_IMAGE="NoPhotoAvailable-icon.jpg";
	
	Integer NO_ROW_SELECTED= -1;
	
	String HYPHEN="-";
	String TOTAL = "Total";
	
	
	
	/*Invoice Panel*/
	String TEXTFIELD_INVOICE_DESC="invoiceDescription";
	String TEXTFIELD_INVOICE_SIZE="invoiceSize";
	String TEXTFIELD_INVOICE_QTY="invoiceQty";
	String TEXTFIELD_INVOICE_UNIT="invoiceUnit";
	String TEXTFIELD_INVOICE_RATE="invoiceRate";
	String TEXTFIELD_INVOICE_AMOUNT="invoiceAmount";
	String TEXTFIELD_INVOICE_TD="invoiceTd";
	String TEXTFIELD_INVOICE_SUB_TOTAL="invoiceSubTotal";
	public static String BUTTON_INVOICE_ACTION_ADD="invoiceAddRow";
	String TABLE_INVOICE="TableInoice";
	String TEXTFIELD_INVOICE_TAX="tax";
	String TEXTFIELD_INVOICE_BILL_AMT="InvoiceBillAmount";
	String TEXTFIELD_INVOICE_PACKET1 = "textFieldGoodsPackets1";
	String TEXTFIELD_INVOICE_PACKET2 = "textFieldGoodsPackets2";
	String TEXTFIELD_INVOICE_ADDRESS = "textFieldADDRESS";
	
	String TABLE_PAYMENT = "TablePayment";
	String TABLE_PAYMENT_RECEIVED_SUMMARY = "TablePaymentSummary";
	
	
	String CITY_PROMPT_MSG = "- - Select City - -";
	
	//3.4 ghanshyam code
	public static final String STOCK_UNIT="doz"; 
	String TEXTFIELD_INVOICE_TEXT_FIELD_COL1="textFieldCol1";
	String TEXTFIELD_INVOICE_TEXT_FIELD_COL2="textFieldCol2";
	String TEXTFIELD_INVOICE_TEXT_FIELD_COL1_VAL="textFieldCol1Val";
	String TEXTFIELD_INVOICE_TEXT_FIELD_COL2_VAL="textFieldCol2Val";
	String INVOICE_UI_COL1_COL2_OPERATION="+";
	//3.4 end of ghanshyam code

	//3.6 ghan code
	public static final String IMG_COMPANY_MASTER="CompanyMaster/Company.png";
	public static final String IMG_INVOICE_MASTER="InvoiceMaster/Invoice.png";
	public static final String IMG_PARTY_MASTER="PartyMaster/Party.png";
	public static final String IMG_PAYMENT_MASTER="PaymentMaster/Payment.png";
	public static final String IMG_REPORT_MASTER="ReportMaster/Report.png";
	public static final String IMG_STOCK_MASTER="StockMaster/Stock.png";
	public static final String IMG_THEME_MASTER="Theme/Theme.png";
	public static final String IMG_TOOLS_MASTER="Tools/Tools.png";
	//3.6 end of ghan code
}
