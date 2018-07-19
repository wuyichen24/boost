package personal.wuyi.boost.api;

/**
 * A two-argument function that takes arguments of type T1 and T2 and returns an R.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <T1>
 * @param <T2>
 * @param <R>
 */
public interface Function2 <T1, T2, R> {
	public R call(T1 obj1, T2 obj2);
}
