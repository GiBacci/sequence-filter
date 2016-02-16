package bacci.giovanni.sequence_filter.io;


import bacci.giovanni.sequence_filter.filter.Filter;




/**
 * Inteface for sequence streaming.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public interface SequenceStreamer {

	/**
	 * Main method
	 * 
	 * @param filter
	 *            the filter
	 * @return a Stream of object of the same type of the given filter
	 */
	public void streamSequences(Filter<?>... filter);
}
