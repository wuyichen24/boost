/*
 * Copyright 2018 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
