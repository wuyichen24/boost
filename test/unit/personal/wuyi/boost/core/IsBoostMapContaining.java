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

package personal.wuyi.boost.core;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Map.Entry;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * New matcher class to simulate the {@code IsMapContaining} class for testing 
 * {@code BoostMap}.
 * 
 * <p>Because of {@code IsMapContaining} is only applicable to test the 
 * classes which implements the {@code Map} interface. But {@code BoostMap} 
 * didn't implements the {@code Map} interface so that {@code IsMapContaining} 
 * can not be used to test {@code BoostMap}. So this class is the alternate 
 * solution for testing {@code BoostMap} in the same style.
 * 
 * @author  Wuyi Chen
 * @date    11/13/2018
 * @version 1.2
 * @since   1.2
 *
 * @param <K>  The type for key in {@code BoostMap}
 * @param <V>  The type for value in {@code BoostMap}
 */
public class IsBoostMapContaining<K, V> extends BaseMatcher<BoostMap<? extends K, ? extends V>> {
	private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;
    
    final private Class<?> expectedType = BoostMap.class;     // restrict the type of input object should be BoostMap. 

    /**
     * Construct a {@code IsBoostMapContaining}.
     * 
     * @param  keyMatcher
     *         The matcher for the key.
     * 
     * @param  valueMatcher
     *         The matcher for the value.
     * 
     * @since   1.2
     */
    public IsBoostMapContaining(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        this.keyMatcher   = keyMatcher;
        this.valueMatcher = valueMatcher;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean matches(Object obj) {
		return obj != null                                                   // check input object is not null
                && expectedType.isInstance(obj)                              // check input object is a BoostMap
                && matchesSafely((BoostMap<? extends K, ? extends V>) obj);  // check the BoostMap matches a certain criteria
	}
	
	/**
	 * Customize the matching criteria for {@code BoostMap}.
	 * 
	 * <p>This method will check a {@code BoostMap} has a certain key-value 
	 * pair or not.
	 * 
	 * @param  boostMap
	 *         The input {@code BoostMap} needs to be verified or not.
	 * 
	 * @return  {@code true} if the {@code BoostMap} has at least one certain 
	 *                       key-value pair;
	 *          {@code false} otherwise;
	 *          
     * @since   1.2
	 */
	protected boolean matchesSafely(BoostMap<? extends K, ? extends V> boostMap) {
        for (Entry<? extends K, ? extends V> entry : boostMap.entries()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

	@Override
	public void describeTo(Description description) {
		description.appendText("BoostMap containing [")
        		.appendDescriptionOf(keyMatcher)
        		.appendText("->")
        		.appendDescriptionOf(valueMatcher)
        		.appendText("]");
	}
	
	/**
     * Creates a matcher for {@code BoostMap} matching when the examined 
     * {@code BoostMap} contains at least one entry whose key equals the 
     * specified <code>key</code> <b>and</b> whose value equals the specified 
     * <code>value</code>.
     * 
     * <p>For example:
     * <pre>
     *     assertThat(myBoostMap, IsBoostMapContaining.hasEntry("bar", "foo"))
     * </pre>
     *  
     * @param  key
     *         The key that, in combination with the value, must be describe 
     *         at least one entry.
     *         
     * @param  value
     *         The value that, in combination with the key, must be describe 
     *         at least one entry.
     *         
     * @return  The instance of the matcher for {@code BoostMap}.
     *         
     * @since   1.2
     */
	@Factory
    public static <K,V> Matcher<BoostMap<? extends K,? extends V>> hasEntry(K key, V value) {
        return new IsBoostMapContaining<K,V>(equalTo(key), equalTo(value));
    }
}
