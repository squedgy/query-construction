package com.dfaris.query.construction;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

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

	public static List<String> questionMarks(int i) {
		List<String> qms = new LinkedList<String>();
		for(int f = 0; f < i; f++ ) qms.add("?");
		return qms;
	}

	public static String getBindingValueOf(Object e) {
		if(e instanceof String){
			return ':' + e.toString();
		} else if (e instanceof List) {
			List l = (List) e;
			if(l.size() != 1){
				throw new IllegalArgumentException("If trying to make a list binding, the list must contain only 1 reference element!");
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
