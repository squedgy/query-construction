package com.dfaris.query.construction;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

/**
 * A helper class for converting objects/values to a SQL-friendly version of themselves.
 */
public abstract class ValueConverters {

	public static String getSqlValueOf(LocalDateTime dateTime) {
		return getSqlValueOf(Timestamp.valueOf(dateTime).toString());
	}

	public static String getSqlValueOf(LocalTime time) {
		return getSqlValueOf(Time.valueOf(time).toString());
	}

	public static String getSqlValueOf(LocalDate date) {
		return getSqlValueOf(Date.valueOf(date).toString());
	}

	public static String getSqlValueOf(String s) {
		return '\'' + s + '\'';
	}

	/**
	 * An object you want to get the SQL literal version of
	 * @param e the object to convert
	 * @return the String to put into an SQL query String
	 */
	public static String getSqlValueOf(Object e) {

		if (e instanceof LocalDate) {
			return getSqlValueOf((LocalDate) e);
		} else if (e instanceof LocalDateTime) {
			return getSqlValueOf((LocalDateTime) e);
		} else if (e instanceof LocalTime) {
			return getSqlValueOf((LocalTime) e);
		} else if (e instanceof Number || e instanceof Boolean) {
			return e.toString();
		}

		return '\'' + e.toString() + '\'';
	}

	private static String qm(Integer i) {
		StringBuilder builder = new StringBuilder();
		for(int f = 0; f < i; f++) builder.append("?,");
		return builder.deleteCharAt(builder.length()-1).toString();
	}

	/**
	 * get a list of question marks i marks long.<br>
	 * you can use String.join to quickly make a comma-separated list of them.
	 * @param i the amount of question marks
	 * @return a List of questions marks
	 */
	public static List<String> questionMarks(int i) {
		List<String> qms = new LinkedList<>();
		for(int f = 0; f < i; f++ ) qms.add("?");
		return qms;
	}

	/**
	 * Convert e to a bind value if MUST be a String, List&lt;String or Integer&gt; with 1 item, or Integer
	 * @param e the object to create a bind of
	 * @return a string representative of the requested bind
	 */
	public static String getBindingValueOf(Object e) {
		if(e instanceof String){
			return ':' + e.toString();
		} else if (e instanceof List) {
			List l = (List) e;
			if(l.size() != 1){
				throw new IllegalArgumentException("If trying to make a list binding, the list must contain only 1 reference String!");
			}
			Object o = l.get(0);
			if(o instanceof Integer) return "(" + qm((Integer) o) + ")";
			else if(o instanceof String) return '<' + o.toString() + '>';
			else throw new IllegalArgumentException("The reference object");
		} else if ((e instanceof Integer) && ((Integer)e > 0)) {
			return '(' + qm((Integer) e) + ')';
		} else {
			throw new IllegalArgumentException("Object is not a String, List, or valid Integer. cannot get binding value!");
		}
	}

}
