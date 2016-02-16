package bacci.giovanni.sequence_filter.filter;

import org.jcvi.jillion.fasta.FastaRecord;


public class JillionFilterFastaSeq extends JillionFilterFastaId {

	/* (non-Javadoc)
	 * @see bacci.giovanni.sequence_filter.filter.JillionFilterFastaId#apply(org.jcvi.jillion.fasta.FastaRecord)
	 */
	@Override
	public String apply(@SuppressWarnings("rawtypes") FastaRecord t) {		
		return t.getSequence().toString();
	}
	
}
