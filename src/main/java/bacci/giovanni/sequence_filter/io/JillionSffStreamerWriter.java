package bacci.giovanni.sequence_filter.io;

import java.io.IOException;
import java.util.logging.Level;

import org.jcvi.jillion.trace.sff.SffFlowgram;
import org.jcvi.jillion.trace.sff.SffWriter;

/**
 * {@link StreamerWriter} for sff sequences
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionSffStreamerWriter extends StreamerWriter<SffFlowgram> {

	private final SffWriter writer;

	/**
	 * Protected constructor
	 * 
	 * @param writer
	 *            the writer
	 */
	protected JillionSffStreamerWriter(SffWriter writer) {
		super();
		this.writer = writer;
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public void writeOutput(SffFlowgram output) {
		try {
			writer.write(output);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"I/O error occurs during output generation", e);
		}

	}

}
