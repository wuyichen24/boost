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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import personal.wuyi.boost.entity.BoostEntry;

/**
 * A collection that maps keys to values, similar to {@code Map}, but in which 
 * each key may be associated with multiple values.
 * 
 * @author  Wuyi Chen
 * @date    06/21/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <K>
 * @param <V>
 */
public class BoostMultimap<K, V> {
	private Map<K, Collection<V>> map;
	
	/** The number of key-value pairs in this BoostMultiMap */
	private int totalSize;
	
	/** Collection of all key-value pairs */
	private Collection<Entry<K, V>> entries;
	
	/**
	 * Construct a {@code BoostMultimap}.
	 */
	public BoostMultimap() {
		this.map = new LinkedHashMap<>();
	}
	
	/**
	 * Returns the collection of values for an explicitly provided key.
	 * 
	 * @param  key
	 *         The key to associate with values in the collection.
	 *         
	 * @return  The collection of values.
	 * 
	 * @since   1.1
	 */
	public List<V> get(K key) {
		Collection<V> collection = map.get(key);
		if (collection == null) {
			collection = createCollection(key);
		}
	    return (List<V>) collection;
	}
	
	/**
	 * Stores a key-value pair in this {@code BoostMultimap}.
	 * 
	 * @param  key
	 *         The key.
	 *         
	 * @param  value
	 *         The value.
	 *         
	 * @return  {@code true} if the method increased the size of the 
	 *                       {@code BoostMultimap};
	 *          {@code false} if the {@code BoostMultimap} already contained 
	 *                        the key-value pair and doesn't allow duplicates.
	 *                        
	 * @since   1.1
	 */
	public boolean put(K key, V value) {
		Collection<V> collection = map.get(key);
	    if (collection == null) {
	    	collection = createCollection(key);
	    	if (collection.add(value)) {
	    		totalSize++;
	    		map.put(key, collection);
	    		return true;
	    	} else {
	        throw new AssertionError("New Collection violated the Collection spec");
	    	}
	    } else if (collection.add(value)) {
	    	totalSize++;
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Return the size of this {@code BoostMultimap}.
	 * 
	 * @return  The size of the {@code BoostMultimap}.
	 * 
	 * @since   1.1
	 */
	public int size() {
	    return totalSize;
	}
	
	/**
     * Check this {@code BoostmultiMap} is empty or not.
     * 
	 * @return  {@code true} if the {@code BoostmultiMap} is empty;
	 *          {@code false} otherwise.
	 *          
	 * @since   1.1
	 */
	public boolean isEmpty() {
	    return size() == 0;
	}
	
	/**
	 * Check the existence of an provided key in the {@code BoostMultimap}.
	 * 
	 * @param  key
	 *         The key needs to be checked.
	 * 
	 * @return  {@code true} if this {@code BoostMultimap} contains at least 
	 *                       one key-value pair with the key;
	 *          {@code false} otherwise.
	 *          
	 * @since   1.1
	 */
	public boolean containsKey(Object key) {
	    return map.containsKey(key);
	}
	
	/**
	 * Check the existence of an provided value.
	 * 
	 * @param  value
	 *         The value needs to be checked.
	 *         
	 * @return  {@code true} if this {@code BoostMultimap} contains at least 
	 *                       one key-value pair with the value;
	 *          {@code false} otherwise.
	 *          
	 * @since   1.1
	 */
	public boolean containsValue(Object value) {
	    for (Collection<V> collection : map.values()) {
	    	if (collection.contains(value)) {
	    		return true;
	    	}
	    }
	    return false;
	}
	
	/**
	 * Check the existence of an provided key-value pair.
	 * 
	 * @param  key
	 *         The key of the pair.
	 *         
	 * @param  value
	 *         The value of the pair.
	 * 
	 * @return  {@code true} if this {@code BoostMultimap} contains at least 
	 *                       one key-value pair with the key and the value;
	 *          {@code false} otherwise;
	 *          
	 * @since   1.1
	 */
	public boolean containsEntry(Object key, Object value) {
	    Collection<V> collection = map.get(key);
	    return collection != null && collection.contains(value);
	}
	
	/**
	 * Remove a provided key-value pair from this {@code BoostMultimap}.
	 * 
	 * @param  key
	 *         The key of the pair which needs to be removed.
	 *         
	 * @param  value
	 *         The value of the pair which needs to be removed.
	 * 
	 * @return  {@code true} if the {@code BoostMultimap} changed;
	 *          {@code false} otherwise.
	 *          
	 * @since   1.1
	 */
	public boolean remove(Object key, Object value) {
	    Collection<V> collection = map.get(key);
	    boolean result = collection != null && collection.remove(value);
	    if (result) {       // Reduce the size only when the pair was existing in this BoostMultiMap  
	    		totalSize--;
	    }
	    return result;
	}
	
	/**
	 * Remove a collection of a provided key from this {@code BoostMultimap}.
	 * 
	 * @param  key
	 *         The key of the pairs which needs to be removed.
	 *         
	 * @return  The values that were removed (possibly empty). The returned 
	 *          collection may be modifiable, but updating it will have no 
	 *          effect on the {@code BoostMultimap}.
	 *          
	 * @since   1.1
	 */
	public Collection<V> removeAll(Object key) {
	    Collection<V> collection = map.remove(key);

	    if (collection == null) {
	      return createUnmodifiableEmptyCollection();
	    }

	    Collection<V> output = createCollection();
	    output.addAll(collection);
	    totalSize -= collection.size();
	    collection.clear();

	    return unmodifiableCollectionSubclass(output);
	}
	
	/**
	 * Creates the collection of values for an explicitly provided key. 
	 * 
	 * <p>By default, it simply calls {@link #createCollection()}, which is 
	 * the correct behavior for most implementations. The 
	 * {@link LinkedHashMultimap} class overrides it.
	 *
	 * @param  key 
	 *         The key to associate with values in the collection.
	 *         
	 * @return  An empty collection of values.
	 * 
	 * @since   1.1
	 */
	private Collection<V> createCollection(K key) {
	    return createCollection();
	}
	
	/**
	 * Creates the collection of values for a single key.
	 *
	 * <p>Collections with weak, soft, or phantom references are not 
	 * supported. Each call to {@code createCollection} should create a new 
	 * instance.
	 *
	 * <p>The returned collection class determines whether duplicate key-value
	 * pairs are allowed.
	 *
	 * @return An empty collection of values.
	 * 
	 * @since   1.1
	 */
	private List<V> createCollection() {
		return new ArrayList<>();
	}
	
	/**
	 * Sorts the key-value pairs in this {@code BoostMultimap} by key.
	 * 
	 * @since   1.1
	 */
	public void sortByKey() {
		if (this.map.size() > 0) {
			Map<K, Collection<V>> sortedTreeMap = new TreeMap<>(this.map);
			this.map = new LinkedHashMap<>();
			for (Map.Entry<K, Collection<V>> entry : sortedTreeMap.entrySet()) {
				this.map.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * Returns a view collection of all distinct keys contained in this 
	 * {@code BoostMultimap}.
	 * 
	 * @return  The set of the keys in this {@code BoostMultimap}.
	 * 
	 * @since   1.1
	 */
	public Set<K> keySet() {
		return this.map.keySet();
	}
	
	/**
	 * Get the list of all the pairs in this {@code BoostMultimap}.
	 * 
	 * <p>The iterator generated by the returned collection traverses the 
	 * values for one key, followed by the values of a second key, and so on.
	 *
	 * <p>Each entry is an immutable snapshot of a key-value mapping in the
	 * {@code BoostMultimap}, taken at the time the entry is returned by a 
	 * method call to the collection or its iterator.
	 * 
	 * @since   1.1
	 */
	public Collection<Entry<K, V>> entries() {
		Collection<Entry<K, V>> result = entries;
		if (result == null) {
			entries = createEntries();
			return entries;
		} else {
			return result;
		}
	}
	  
	/**
	 * Create an empty collection of key-value pairs
	 * 
	 * @return  The empty collection of key-value pairs in this 
	 *          {@code BoostMultimap}.
	 *          
	 * @since   1.1
	 */
	private Collection<Entry<K, V>> createEntries() {
		return new Entries();
	}
	
	/**
	 * Create an unmodifiable empty collection.
	 * 
	 * @return  The new collection.
	 * 
	 * @since   1.1
	 */
	Collection<V> createUnmodifiableEmptyCollection() {
	    return unmodifiableCollectionSubclass(createCollection());
	}
	
	/**
	 * Returns an unmodifiable view of the specified list.
	 * 
	 * @param  collection
	 *         The collection needs to be processed.
	 *         
	 * @return  The unmodifiable view of the collection.
	 * 
	 * @since   1.1
	 */
	Collection<V> unmodifiableCollectionSubclass(Collection<V> collection) {
	    return Collections.unmodifiableList((List<V>) collection);
	}
	
	/**
	 * Get an iterator across all key-value map entries.
	 * 
	 * Returns an iterator across all key-value map entries, used by {@code
	 * entries().iterator()} and {@code values().iterator()}. The default
	 * behavior, which traverses the values for one key, the values for a 
	 * second key, and so on.
	 *
	 * @return An iterator across map entries.
	 * 
	 * @since   1.1
	 */
	Iterator<Entry<K, V>> entryIterator() {
		return new MultimapIterator<Map.Entry<K, V>>() {      
			Entry<K, V> output(K key, V value) {
				return new BoostEntry<>(key, value);
			}
		};
	}
	
	/**
	 * Inner class for representing the entries in the {@code BoostMultimap}.
	 * 
	 * @since   1.1
	 */
	private class Entries extends AbstractCollection<Map.Entry<K, V>> {
		BoostMultimap<K, V> multimap() {
			return BoostMultimap.this;
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return entryIterator();
		}

		@Override
		public int size() {
			return multimap().size();
		}

		@Override
		public boolean contains(Object o) {
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
				return multimap().containsEntry(entry.getKey(),
						entry.getValue());
			}
			return false;
		}

		@Override
		public boolean remove(Object o) {
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
				return multimap().remove(entry.getKey(), entry.getValue());
			}
			return false;
		}
	}
	
	/**
	 * Abstract class for the iterator of the {@code BoostMultimap}.
	 *
	 * @param <T>
	 * 
	 * @since   1.1
	 */
	private abstract class MultimapIterator<T> implements Iterator<T> {
	    final Iterator<Map.Entry<K, Collection<V>>> keyIterator;
	    
	    K             key;
	    Collection<V> collection;
	    Iterator<V>   valueIterator;

	    @SuppressWarnings("unchecked")
		MultimapIterator() {
	    	keyIterator = map.entrySet().iterator();
	    	key = null;
	    	collection = null;
	    	if (generateEmptyModifiableIterator() instanceof Iterator) {
	    		valueIterator = (Iterator<V>) generateEmptyModifiableIterator();
	    	}
	    }

	    abstract T output(K key, V value);

	    @Override
	    public boolean hasNext() {
	    	return keyIterator.hasNext() || valueIterator.hasNext();
	    }

	    @Override
	    public T next() {
	    	if (!valueIterator.hasNext()) {
	    		Map.Entry<K, Collection<V>> mapEntry = keyIterator.next();
	    		key = mapEntry.getKey();
	    		collection = mapEntry.getValue();
	    		valueIterator = collection.iterator();
	    	}
	    	return output(key, valueIterator.next());
	    }

	    @Override
	    public void remove() {
	    	valueIterator.remove();
	    	if (collection.isEmpty()) {
	    		keyIterator.remove();
	    	}
	    	totalSize--;
	    }
	    
		private Iterator<Object> generateEmptyModifiableIterator() {
			return new Iterator<Object>() {
				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public Object next() {
					throw new NoSuchElementException();
				}

				@Override
				public void remove() {
					throw new IllegalStateException("no calls to next() since the last call to remove()");
				}
			};
		}
	}
}
