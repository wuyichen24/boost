package personal.wuyi.boost.api;

/**
 * A function that returns zero or more output records from each input record.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <A>
 * @param <B>
 */
public interface FlatMapFunction<A, B> {
	public Iterable<B> call(A f);
}
