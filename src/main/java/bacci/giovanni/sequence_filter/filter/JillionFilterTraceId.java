package bacci.giovanni.sequence_filter.filter;

import org.jcvi.jillion.trace.Trace;

public class JillionFilterTraceId extends Filter<Trace> {

	@Override
	public String apply(Trace t) {
		return t.getId();
	}
	
	
	
}
