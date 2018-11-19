package pl.eHouse.web.common.client.widgets;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class WidgetList<E> extends ListBox implements IWidget {

	private int height = 22;
	private int width;
	private ArrayList<E> values = new ArrayList<E>();

	public WidgetList(int width) {
		super();
		this.width = width;
		setPixelSize(width - 3, height - 3);
	}
	
	public WidgetList(int width, String[] elements) {
		super();
		this.width = width;
		setPixelSize(width - 3, height - 3);
		for(String element: elements) {
			addItem(element);
		}
	}

	public WidgetList(int width, ChangeHandler handler) {
		this(width);
		addChangeHandler(handler);
	}

	public void addItem(String item, E value) {
		addItem(item);
		values.add(value);
	}	

	public E getSelectedItem() {
		if ((getSelectedIndex() >= 0) && (getSelectedIndex() < values.size())) {
			return values.get(getSelectedIndex());
		} else {
			return null;
		}
	}
	
	@Override
	public void clear() {
		super.clear();
		values.clear();
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

}
