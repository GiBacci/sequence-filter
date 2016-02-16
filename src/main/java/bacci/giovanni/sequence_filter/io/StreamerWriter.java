package bacci.giovanni.sequence_filter.io;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic sequence writer
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 * @param <T>
 *            the type of sequence to write (no upper bound selected)
 */
public abstract class StreamerWriter<T> implements Closeable {

	/**
	 * The logger
	 */
	protected final Logger logger;

	/**
	 * Protected constructor for factory pattern
	 * 
	 * @see StreamerFactory#getSequenceStreamer(String, String)
	 */
	protected StreamerWriter() {
		this.logger = Logger.getLogger("StreamerWriter");
		logger.setLevel(Level.ALL);
	}

	/**
	 * Writing method
	 * 
	 * @param output
	 *            the output sequence to write
	 */
	public abstract void writeOutput(T output);

}
