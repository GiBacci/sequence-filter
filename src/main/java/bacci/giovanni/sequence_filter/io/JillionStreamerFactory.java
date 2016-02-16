package bacci.giovanni.sequence_filter.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;

import org.jcvi.jillion.core.datastore.DataStoreProviderHint;
import org.jcvi.jillion.fasta.aa.ProteinFastaFileDataStoreBuilder;
import org.jcvi.jillion.fasta.aa.ProteinFastaRecord;
import org.jcvi.jillion.fasta.aa.ProteinFastaWriterBuilder;
import org.jcvi.jillion.fasta.nt.NucleotideFastaFileDataStoreBuilder;
import org.jcvi.jillion.fasta.nt.NucleotideFastaRecord;
import org.jcvi.jillion.fasta.nt.NucleotideFastaWriterBuilder;
import org.jcvi.jillion.trace.fastq.FastqFileDataStoreBuilder;
import org.jcvi.jillion.trace.fastq.FastqRecord;
import org.jcvi.jillion.trace.fastq.FastqWriterBuilder;
import org.jcvi.jillion.trace.sff.SffFileDataStore;
import org.jcvi.jillion.trace.sff.SffFileDataStoreBuilder;
import org.jcvi.jillion.trace.sff.SffFlowgram;
import org.jcvi.jillion.trace.sff.SffWriterBuilder;

import bacci.giovanni.sequence_filter.filter.Filter;
import bacci.giovanni.sequence_filter.filter.JillionFilterFastaId;
import bacci.giovanni.sequence_filter.filter.JillionFilterFastaSeq;
import bacci.giovanni.sequence_filter.filter.JillionFilterTraceId;
import bacci.giovanni.sequence_filter.filter.JillionFilterTraceSeq;

/**
 * Streamer factory implemented using Jillion 5.1
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public class JillionStreamerFactory extends StreamerFactory {

	/**
	 * Protected constructor
	 * 
	 * @param type
	 *            the sequence type
	 */
	protected JillionStreamerFactory(ReaderType type) {
		super(type);
	}

	@Override
	public SequenceStreamer getSequenceStreamer(String input, String output) {
		File in = new File(input);
		OutputStream out = getOutputStream(output);
		DataStoreProviderHint h = DataStoreProviderHint.ITERATION_ONLY;
		try {
			switch (type) {
			case FASTA_NUCL:
				return new JillionSequenceStreamer<NucleotideFastaRecord>(
						new NucleotideFastaFileDataStoreBuilder(in).hint(h)
								.build(),
						new JillionFastaNucleotideStreamerWriter(
								new NucleotideFastaWriterBuilder(out).build()));
			case FASTA_PROT:
				return new JillionSequenceStreamer<ProteinFastaRecord>(
						new ProteinFastaFileDataStoreBuilder(in).hint(h)
								.build(),
						new JillionFastaProteinStreamerWriter(
								new ProteinFastaWriterBuilder(out).build()));
			case TRACE_FASTQ:
				return new JillionSequenceStreamer<FastqRecord>(
						new FastqFileDataStoreBuilder(in).hint(h).build(),
						new JillionFastqStreamerWriter(new FastqWriterBuilder(
								out).build()));
			case TRACE_SFF:
				SffFileDataStore sff = new SffFileDataStoreBuilder(in).hint(h)
						.build();
				return new JillionSequenceStreamer<SffFlowgram>(sff,
						new JillionSffStreamerWriter(new SffWriterBuilder(
								new File(output), sff.getFlowSequence(),
								sff.getKeySequence()).build()));
			default:
				break;
			}
		} catch (FileNotFoundException e1) {
			logger.log(Level.SEVERE, "Input file not found", e1);
			System.exit(-1);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, "I/O Error generating stream", e1);
			System.exit(-1);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Errors occur while generating stream", e);
			System.exit(-1);
		}
		return null;
	}

	@Override
	public Filter<?> getIdFilter() {
		switch (type) {
		case FASTA_NUCL:
			return new JillionFilterFastaId();
		case FASTA_PROT:
			return new JillionFilterFastaId();
		case TRACE_FASTQ:
			return new JillionFilterTraceId();
		case TRACE_SFF:
			return new JillionFilterTraceId();
		default:
			return null;
		}
	}

	@Override
	public Filter<?> getSeqFilter() {
		switch (type) {
		case FASTA_NUCL:
			return new JillionFilterFastaSeq();
		case FASTA_PROT:
			return new JillionFilterFastaSeq();
		case TRACE_FASTQ:
			return new JillionFilterTraceSeq();
		case TRACE_SFF:
			return new JillionFilterTraceSeq();
		default:
			return null;
		}
	}

	private OutputStream getOutputStream(String output) {
		File o = new File(output);
		OutputStream out = null;
		try {
			if (output.endsWith(".gz")) {
				out = new GZIPOutputStream(new FileOutputStream(o));
			} else {
				out = new FileOutputStream(o);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "I/O Error building output", e);	
			System.exit(-1);
		}
		return out;
	}

}
