package com.dfaris.query.construction;

/**
 * Forces a class to have a custom toString
 */
public abstract class Stringable {

	/**
	 * A string representing a piece or the entirety of a query
	 *
	 * @return formatted string
	 */
	@Override
	public abstract String toString();

}
