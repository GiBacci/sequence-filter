package bacci.giovanni.sequence_filter.io;

import java.io.IOException;
import java.util.logging.Level;

import org.jcvi.jillion.trace.fastq.FastqRecord;
import org.jcvi.jillion.trace.fastq.FastqWriter;

/**
 * {@link StreamerWriter} for fastq sequences
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionFastqStreamerWriter extends StreamerWriter<FastqRecord> {

	private final FastqWriter writer;

	/**
	 * Protected constructor
	 * 
	 * @param writer
	 *            the writer
	 */
	protected JillionFastqStreamerWriter(FastqWriter writer) {
		super();
		this.writer = writer;
	}

	@Override
	public void close() throws IOException {
		writer.close();

	}

	@Override
	public void writeOutput(FastqRecord output) {
		try {
			writer.write(output);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"I/O error occurs during output generation", e);
		}
	}

}
