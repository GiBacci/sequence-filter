package bacci.giovanni.sequence_filter.filter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * A set of Predicate for sequence objects as string. Given the fact that every
 * implementation of biological sequences has its own set of methods to retrive
 * sequence and id, this class provides a mapping function able to obtain the id
 * or the sequence as a string. every implementation of thsi class must provide
 * this function based on its needs.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 *
 */
public abstract class Filter<T> implements Predicate<T>, Function<T, String> {

	/**
	 * If <code>true</code> returns whether any predicates of this object match
	 * the provided sequence
	 */
	private boolean any = false;

	/**
	 * The set of predictaed
	 */
	private final Set<Predicate<String>> conditions = new HashSet<Predicate<String>>();

	/**
	 * Adds a predicate to this set
	 * 
	 * @param predicate
	 *            the predicate to add
	 */
	public void addCondition(Predicate<String> predicate) {
		conditions.add(predicate);
	}

	/**
	 * Removes a predicate from this set
	 * 
	 * @param predicate
	 *            the predicate to remove
	 */
	public void removeIdCondition(Predicate<String> predicate) {
		if (conditions.contains(predicate))
			conditions.remove(predicate);
	}

	/**
	 * @return the any
	 */
	public boolean isAny() {
		return any;
	}

	/**
	 * @param any
	 *            the any to set
	 */
	public void setAny(boolean any) {
		this.any = any;
	}

	@Override
	public boolean test(T t) {
		if (any)
			return conditions.stream().anyMatch(p -> p.test(apply(t)));
		return conditions.stream().allMatch(p -> p.test(apply(t)));
	}

}
