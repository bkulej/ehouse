/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertUtil;
/**
 *
 * @author Bartek
 */
public class MessageIn {

	protected int type;
	private boolean finished;
	protected ArrayList<Integer> parts;

	/**
	 * Konstruktor
	 */
	public MessageIn() {
		parts = new ArrayList<Integer>();
	}

	public int getType() {
		return type;
	}

	public boolean isFinished() {
		return finished;
	}

	public List<Integer> getParts() {
		return parts;
	}

	/**
	 * Dodanie
	 * 
	 * @param parts
	 * @return
	 */
	public void addPart(int part) {
		// Poczatek wiadomosci HEADER
		if ((part & Header.HANDF_BITS) == Header.HEADER) {
			// Ustalenie typu
			type = part & Header.TYPE_BITS;
			// Wyczyszczenie listy
			parts.clear();
			// Koniec wiadomosci FOOTER 1
        } else if((part & Header.HANDF_BITS) == Header.FOOTER1) {
			// Koniec wiadomosci FOOTER 2
		} else if ((part & Header.HANDF_BITS) == Header.FOOTER2) {
			finished = true;
			// Czesc wiadomosci
		} else {
			// Zapisanie wartosci
			parts.add(part);
		}
	}

	@Override
	public String toString() {
		String string = "<I";
		if (type == Header.ADDRESS) {
			string += "A>{";
		} else if (type == Header.SERIAL) {
			string += "S>{";
		} else if (type == Header.BOOT) {
			string += "B>{";
		}
		for (Integer part : parts) {
			string += ConvertUtil.byteToHex(part) + ",";
		}
		return string + "}";
	}

}
