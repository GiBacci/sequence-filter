package bacci.giovanni.sequence_filter.io;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.jcvi.jillion.core.datastore.DataStore;
import org.jcvi.jillion.core.datastore.DataStoreException;
import org.jcvi.jillion.core.util.iter.StreamingIterator;

import bacci.giovanni.sequence_filter.filter.Filter;

/**
 * A sequence streamer implemented using Jillion 5.1 library
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionSequenceStreamer<T> implements SequenceStreamer {

	/**
	 * The logger
	 */
	protected final Logger logger;

	private final StreamerWriter<T> writer;

	/**
	 * The data store for iterating over a set of sequences
	 */
	@SuppressWarnings("rawtypes")
	protected final DataStore store;

	/**
	 * Protected constructor for Factory pattern
	 * 
	 * @param store
	 *            the data store, usually provided by the Factory object (
	 *            {@link JillionStreamerFactory})
	 */
	protected JillionSequenceStreamer(DataStore<T> store,
			StreamerWriter<T> writer) {
		this.store = store;
		this.writer = writer;
		this.logger = Logger.getLogger("SequenceStreamer");
		logger.setLevel(Level.ALL);
	}

	@Override
	public void streamSequences(Filter<?>... filter) {
		try {
			@SuppressWarnings("unchecked")
			StreamingIterator<T> iterator = store.iterator();
			Stream<T> stream = iterator.toStream();
			for (Filter<?> f : filter) {
				@SuppressWarnings("unchecked")
				Filter<T> filt = (Filter<T>) f;
				stream = stream.filter(filt);
			}
			stream.forEach(s -> writer.writeOutput(s));
		} catch (DataStoreException e) {
			logger.log(Level.WARNING, "Problems occur streaming sequences", e);
		}
		try {
			writer.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Problems occur finalyzing output file", e);
		}
	}

}
