package pl.eHouse.web.common.client.widgets;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

public class WidgetTable<E> extends Composite implements IWidget {
	
	private static final int ROW_HEIGHT 	= 22;
	
	private int height;
	private int width;
	
	private WidgetPanel panel;
	private SelectionModel<E> selection;
	private ListDataProvider<E> provider;
	private CellTable<E>  table;
	private SimplePager pager;		
	
	public WidgetTable(SelectionModel<E> selection, int width, int height) {
		this.selection = selection;
		this.height = height;
		this.width = width;
		int rows = (height/ROW_HEIGHT) - 2;

		panel = new WidgetPanel(width, height, "table-panel");		
		provider = new ListDataProvider<E>();						
		table = new CellTable<E>();
		table.setSelectionModel(selection);
		table.setRowData(0,provider.getList());
		provider.addDataDisplay(table);
		table.setPixelSize(width-2, height - ROW_HEIGHT);
		panel.addWidget(0, 0, width-2, height - ROW_HEIGHT, table);		
		
		pager = new SimplePager();
		pager.setDisplay(table);
	    pager.setPageSize(rows);
	    pager.setPixelSize(width-2, ROW_HEIGHT - 2);
	    panel.addWidget(0, height - ROW_HEIGHT + 1, width-1, ROW_HEIGHT - 2, pager);	    
	    
	    initWidget(panel);
	    setPixelSize(width-2,height-2);
	}		
		
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public List<E> getList() {
		return provider.getList();
	}
	
	public SelectionModel<E> getSelectionModel() {
		return selection;
	}

	public void setSelection(SelectionModel<E> selection) {
		this.selection = selection;
	}
	
	public void addColumn(String header, int width, HorizontalAlignmentConstant align, Column<E,?> column) {
		column.setHorizontalAlignment(align);
		column.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		table.addColumn(column,header);
		table.setColumnWidth(column, width, Unit.PX);				
	}	
	
	public void addSelectionHandler(SelectionChangeEvent.Handler handler) {
		selection.addSelectionChangeHandler(handler);		
	}
	
	public void refresh() {
		provider.refresh();
	}

}
