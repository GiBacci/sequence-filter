package bacci.giovanni.sequence_filter.filter;

import org.jcvi.jillion.trace.Trace;

public class JillionFilterTraceSeq extends JillionFilterTraceId {

	/* (non-Javadoc)
	 * @see bacci.giovanni.sequence_filter.filter.JillionFilterTraceId#apply(org.jcvi.jillion.trace.Trace)
	 */
	@Override
	public String apply(Trace t) {
		return t.getNucleotideSequence().toString();
	}
	
}
