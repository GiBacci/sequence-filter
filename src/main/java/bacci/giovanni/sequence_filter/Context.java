package bacci.giovanni.sequence_filter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import bacci.giovanni.sequence_filter.filter.Filter;
import bacci.giovanni.sequence_filter.io.SequenceStreamer;
import bacci.giovanni.sequence_filter.io.StreamerFactory;
import bacci.giovanni.sequence_filter.io.StreamerFactory.ReaderType;

/**
 * Context class for sequence filtering.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class Context {

	/**
	 * Main factory
	 */
	private final StreamerFactory factory;

	/**
	 * A list of filters
	 */
	private final List<Filter<?>> filters;

	/**
	 * The logger
	 */
	private final Logger logger;

	/**
	 * Creates a new instance of a context class.
	 * 
	 * @param type
	 *            the type of file to read from
	 */
	public Context(ReaderType type) {
		this.logger = Logger.getLogger("Context");
		this.logger.setLevel(Level.ALL);
		this.filters = new ArrayList<Filter<?>>();
		this.factory = StreamerFactory.getJillionFactory(type);
	}

	/**
	 * Add all conditions present in a file (one condition per line)
	 * 
	 * @param file
	 *            the file
	 * @param any
	 *            if <code>true</code> the filter will match only one of the
	 *            given conditions
	 * @param id
	 *            if the conditions have to be applied to the id
	 */
	public void feedConditionsFromFile(String file, boolean any, boolean id) {
		try {
			this.feedConditions(Files.lines(Paths.get(file)), any, id);
		} catch (IOException e) {
			this.logger.log(Level.SEVERE,
					"I/O error loading conditions from file", e);
			System.exit(-1);
		}
	}

	/**
	 * Add all conditions present in a stream of string
	 * 
	 * @param conditions
	 *            the stream
	 * @param any
	 *            if <code>true</code> the filter will match only one of the
	 *            given conditions
	 * @param id
	 *            if the conditions have to be applied to the id
	 */
	public void feedConditions(Stream<String> conditions, boolean any,
			boolean id) {
		Filter<?> filter = (id) ? factory.getIdFilter() : factory
				.getSeqFilter();
		filter.setAny(any);
		conditions.forEach(c -> {
			filter.addCondition(s -> s.matches(c));
		});
		filters.add(filter);
	}

	/**
	 * Add a single condition
	 * 
	 * @param condition
	 *            the condition
	 * @param id
	 *            if the conditions have to be applied to the id
	 */
	public void feedCondition(String condition, boolean id) {
		Filter<?> filter = (id) ? factory.getIdFilter() : factory
				.getSeqFilter();
		filter.addCondition(s -> s.matches(condition));
		filters.add(filter);
	}

	/**
	 * Stream method
	 * 
	 * @param input
	 *            the onput file
	 * @param output
	 *            the output file
	 */
	public void streamSequences(String input, String output) {
		SequenceStreamer streamer = factory.getSequenceStreamer(input, output);
		streamer.streamSequences(filters.toArray(new Filter<?>[filters.size()]));
	}

}
