package bacci.giovanni.sequence_filter.filter;

import org.jcvi.jillion.fasta.FastaRecord;

@SuppressWarnings("rawtypes")
public class JillionFilterFastaId extends Filter<FastaRecord> {

	@Override
	public String apply(FastaRecord t) {
		return t.getId();
	}

}
