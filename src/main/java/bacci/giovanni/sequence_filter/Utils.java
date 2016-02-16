package bacci.giovanni.sequence_filter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import bacci.giovanni.sequence_filter.io.StreamerFactory.ReaderType;

public class Utils {

	@SuppressWarnings("serial")
	private static final Map<String, ReaderType> EXTENSION_MAP = new HashMap<String, ReaderType>() {
		{
			put("fasta", ReaderType.FASTA_NUCL);
			put("fas", ReaderType.FASTA_NUCL);
			put("fa", ReaderType.FASTA_NUCL);
			put("seq", ReaderType.FASTA_NUCL);
			put("fsa", ReaderType.FASTA_NUCL);
			put("fna", ReaderType.FASTA_NUCL);
			put("ffn", ReaderType.FASTA_NUCL);
			put("faa", ReaderType.FASTA_PROT);
			put("frn", ReaderType.FASTA_NUCL);

			put("fastq", ReaderType.TRACE_FASTQ);
			put("fq", ReaderType.TRACE_FASTQ);

			put("sff", ReaderType.TRACE_SFF);
		}
	};

	public static ReaderType guessType(String file) {
		String name = Paths.get(file).getFileName().toString().toLowerCase();
		if (name.endsWith(".gz"))
			name = name.substring(0, name.lastIndexOf('.'));
		String extension = name.substring(name.lastIndexOf('.') + 1);
		return EXTENSION_MAP.get(extension);
	}
}
