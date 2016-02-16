package bacci.giovanni.sequence_filter.io;

import java.io.IOException;
import java.util.logging.Level;

import org.jcvi.jillion.fasta.aa.ProteinFastaRecord;
import org.jcvi.jillion.fasta.aa.ProteinFastaWriter;

/**
 * {@link StreamerWriter} for proteic fasta sequences
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionFastaProteinStreamerWriter extends
		StreamerWriter<ProteinFastaRecord> {

	private final ProteinFastaWriter writer;

	/**
	 * Protected constructor
	 * 
	 * @param writer
	 *            the writer
	 */
	protected JillionFastaProteinStreamerWriter(ProteinFastaWriter writer) {
		super();
		this.writer = writer;
	}

	@Override
	public void close() throws IOException {
		this.writer.close();
	}

	@Override
	public void writeOutput(ProteinFastaRecord output) {
		try {
			writer.write(output);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"I/O error occurs during output generation", e);
		}

	}

}
