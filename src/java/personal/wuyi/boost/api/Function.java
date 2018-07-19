package personal.wuyi.boost.api;

/**
 * A one-argument function that takes the argument of type T and returns an R.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <T>
 * @param <R>
 */
public interface Function<T, R> {
	/**
	 * @param obj
	 * @return
	 */
	public R call(T f);
}
