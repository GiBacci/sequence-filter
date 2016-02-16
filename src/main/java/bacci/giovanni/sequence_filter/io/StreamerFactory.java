package bacci.giovanni.sequence_filter.io;

import java.util.logging.Level;
import java.util.logging.Logger;

import bacci.giovanni.sequence_filter.filter.Filter;

/**
 * Abstract factory for sequence file streamers. This class is a Factory for the
 * three main classes used in this package:<br>
 * 
 * <ul>
 * <li>{@link SequenceStreamer} for iterating over sequence entries in a file</li>
 * <li>{@link Filter} for ids</li>
 * <li>{@link Filter} for sequences</li>
 * </ul>
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public abstract class StreamerFactory {

	/**
	 * Type of sequences
	 */
	protected final ReaderType type;

	/**
	 * The logger
	 */
	protected final Logger logger;

	/**
	 * Protected constructor for Factory pattern
	 * 
	 * @param type
	 *            the type of sequences to read
	 */
	protected StreamerFactory(ReaderType type) {
		this.type = type;
		this.logger = Logger.getLogger("StreamerFactory");
		this.logger.setLevel(Level.ALL);
	}

	/**
	 * This method returns an instace of a {@link JillionStreamerFactory}
	 * 
	 * @param type
	 *            the type of sequences
	 * @return an instance of a {@link JillionStreamerFactory}
	 */
	public static StreamerFactory getJillionFactory(ReaderType type) {
		return new JillionStreamerFactory(type);
	}

	/**
	 * This method must return a valid implementation of
	 * {@link SequenceStreamer} class
	 * 
	 * @param input
	 *            the input file
	 * @return an implementation of a {@link SequenceStreamer}
	 */
	public abstract SequenceStreamer getSequenceStreamer(String input,
			String output);

	/**
	 * @return the correct filter implementaion for ids
	 */
	public abstract Filter<?> getIdFilter();

	/**
	 * @return the correct filter implementaion for sequences
	 */
	public abstract Filter<?> getSeqFilter();

	/**
	 * Type of sequence files supported. Actually this class supports the same
	 * types supported by jillion library
	 * 
	 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
	 *         Bacci</a>
	 *
	 */
	public enum ReaderType {
		/**
		 * Fasta nucleotidic sequences
		 */
		FASTA_NUCL,

		/**
		 * Fasta proteic sequences
		 */
		FASTA_PROT,

		/**
		 * Fastq sequences
		 */
		TRACE_FASTQ,

		/**
		 * SFF sequences
		 */
		TRACE_SFF;
	}

}
