package bacci.giovanni.sequence_filter.io;

import java.io.IOException;
import java.util.logging.Level;

import org.jcvi.jillion.fasta.nt.NucleotideFastaRecord;
import org.jcvi.jillion.fasta.nt.NucleotideFastaWriter;

/**
 * {@link StreamerWriter} for nucleoic fasta sequences
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionFastaNucleotideStreamerWriter extends
		StreamerWriter<NucleotideFastaRecord> {

	/**
	 * The writer
	 */
	private final NucleotideFastaWriter writer;

	/**
	 * Protected constructor
	 * 
	 * @param writer
	 *            the writer
	 */
	protected JillionFastaNucleotideStreamerWriter(NucleotideFastaWriter writer) {
		super();
		this.writer = writer;
	}

	@Override
	public void writeOutput(NucleotideFastaRecord output) {
		try {
			writer.write(output);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"I/O error occurs during output generation", e);
		}
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

}
