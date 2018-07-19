package personal.wuyi.boost.api;

/**
 * A function with no return value.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <E>
 */
public interface VoidFunction<E> {
	public void call(E f);
}
