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
package personal.wuyi.boost.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@code BoostEntry}.
 * 
 * @author  Wuyi Chen
 * @date    11/28/2018
 * @version 1.2
 * @since   1.2
 */
public class BoostEntryTest {
	@Test(expected = Exception.class) 
	public void setValueTest() {
		BoostEntry<String, Integer> entry = new BoostEntry<>("aaa", 123);
		entry.setValue(20);
	}
	
	@Test
	public void equalsTest() {
		BoostEntry<String, Integer> entryA = new BoostEntry<>("aaa", 123);
		BoostEntry<String, Integer> entryB = null;
		Assert.assertFalse(entryA.equals(entryB));
		Assert.assertFalse(entryA.equals(new BoostEntry<>("bbb", 123)));
		Assert.assertFalse(entryA.equals(new BoostEntry<>("aaa", 456)));
		Assert.assertTrue(entryA.equals(new BoostEntry<>("aaa", 123)));
	}
	
	@Test
	public void hashCodeTest() {
		Assert.assertEquals(96314, new BoostEntry<>("aaa", 123).hashCode());
		Assert.assertEquals(97369, new BoostEntry<>("bbb", 123).hashCode());
		Assert.assertEquals(96649, new BoostEntry<>("aaa", 456).hashCode());
	}
	
	@Test
	public void toStringTest() {
		BoostEntry<String, Integer> entry = new BoostEntry<>("aaa", 123);
		Assert.assertEquals("aaa=123", entry.toString());
	}
}
