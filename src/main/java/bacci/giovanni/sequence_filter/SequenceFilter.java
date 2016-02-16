package bacci.giovanni.sequence_filter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import bacci.giovanni.sequence_filter.io.StreamerFactory.ReaderType;

/**
 * Hello world!
 *
 */
public class SequenceFilter {

	private final static char ARG_SEP = ',';

	private final static OptionParser PARSER = new OptionParser();

	private final static Logger logger = Logger.getLogger("MainLogger");

	public static void main(String[] args) {

		String description = "file | condition1,condition2...";

		OptionSpec<String> type = PARSER
				.accepts("type",
						"the input file type. One of: fasta_prot, fasta_nucl, fastq or sff")
				.withRequiredArg().ofType(String.class)
				.describedAs("file type");

		OptionSpec<String> idAny = PARSER
				.accepts("id-any",
						"search for ANY of the given patterns in each sequence id")
				.withRequiredArg().ofType(String.class)
				.describedAs(description).withValuesSeparatedBy(ARG_SEP);

		OptionSpec<String> seqAny = PARSER
				.accepts("seq-any",
						"search for ANY of the given patterns in each sequence")
				.withRequiredArg().ofType(String.class)
				.describedAs(description).withValuesSeparatedBy(ARG_SEP);

		OptionSpec<String> idAll = PARSER
				.accepts("id-all",
						"search for ALL the given patterns in each sequence id")
				.withRequiredArg().ofType(String.class)
				.describedAs(description).withValuesSeparatedBy(ARG_SEP);

		OptionSpec<String> seqAll = PARSER
				.accepts("seq-all",
						"search for ALL the given patterns in each sequence")
				.withRequiredArg().ofType(String.class)
				.describedAs(description).withValuesSeparatedBy(ARG_SEP);

		OptionSpec<String> id = PARSER
				.accepts("id",
						"search for the given pattern in each sequence id")
				.withRequiredArg().ofType(String.class).describedAs("pattern");

		OptionSpec<String> seq = PARSER
				.accepts("seq", "search for the given pattern in each sequence")
				.withRequiredArg().ofType(String.class)
				.describedAs("condition");

		OptionSpec<File> in = PARSER.accepts("in", "input file")
				.withRequiredArg().ofType(File.class).describedAs("file")
				.required();

		OptionSpec<File> out = PARSER.accepts("out", "output file")
				.withRequiredArg().ofType(File.class).describedAs("file")
				.defaultsTo(new File("filtered_<input_file>"));

		OptionSpec<Void> help = PARSER.accepts("help", "show help").forHelp();

		OptionSet options = null;

		try {
			options = PARSER.parse(args);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problems parsing command line options", e);
			System.exit(-1);
		}

		if (options.has(help))
			printHelpAndExit();

		Path inPath = options.valueOf(in).toPath();
		String input = inPath.toAbsolutePath().toString();

		String output = inPath.getParent()
				.resolve("filtered_" + inPath.getFileName().toString())
				.toString();

		ReaderType readerType = (options.has(type)) ? getReaderType(options
				.valueOf(type)) : Utils.guessType(input);

		if (readerType == null) {
			logger.log(Level.SEVERE, "Unable to detect input file type"
					+ System.lineSeparator()
					+ "Re-run with '--help' option for help");
			System.exit(-1);
		}

		Context c = new Context(readerType);

		if (options.has(idAny))
			c = feedFilters(c, options.valuesOf(idAny), true, true);

		if (options.has(idAll))
			c = feedFilters(c, options.valuesOf(idAll), true, false);

		if (options.has(seqAny))
			c = feedFilters(c, options.valuesOf(seqAny), false, true);

		if (options.has(seqAll))
			c = feedFilters(c, options.valuesOf(seqAny), false, false);

		if (options.has(id))
			c = feedFilters(c, options.valuesOf(id), true);

		if (options.has(seq))
			c = feedFilters(c, options.valuesOf(seq), false);

		if (options.has(out))
			output = options.valueOf(out).toString();

		c.streamSequences(input, output);
	}

	private static void printHelpAndExit() {
		System.out
				.println("Usage: java -jar <jar_name> --in <input_file> [OPTIONS]");
		System.out
				.println("=======================================================");
		try {
			PARSER.printHelpOn(System.out);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot print help", e);
			System.exit(-1);
		}
		System.out
				.println("=======================================================");
		System.out.println("Developed and maintained by: Giovanni Bacci");
		System.out.println("mail: giovanni.bacci[AT]unifi.it");
		System.exit(0);
	}

	private static ReaderType getReaderType(String type) {
		switch (type) {
		case "fasta_prot":
			return ReaderType.FASTA_PROT;
		case "fasta_nucl":
			return ReaderType.FASTA_NUCL;
		case "fastq":
			return ReaderType.TRACE_FASTQ;
		case "sff":
			return ReaderType.TRACE_SFF;
		default:
			return null;
		}
	}

	private static Context feedFilters(Context c, List<String> filters,
			boolean id, boolean any) {
		if (filters.size() == 1 && Files.exists(Paths.get(filters.get(0)))) {
			c.feedConditionsFromFile(filters.get(0), any, id);
			return c;
		}
		c.feedConditions(filters.parallelStream(), any, id);
		return c;
	}

	private static Context feedFilters(Context c, List<String> filters,
			boolean id) {
		filters.parallelStream().forEach(f -> {
			c.feedCondition(f, id);
		});
		return c;
	}
}
