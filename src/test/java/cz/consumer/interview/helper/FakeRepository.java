package cz.consumer.interview.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FakeRepository<T> {

	Map<Long,T> entities = new HashMap<>();
	
	long nextId = 1l;
	
	protected abstract Long getId(T entity);
	protected abstract void setId(T entity, Long id);

	public void save(T entity) {
		if(getId(entity) == null) {
			setId(entity, nextId);
			nextId++;
		}
		entities.put(getId(entity), entity);
	}

	public List<T> findAll() {
		return new ArrayList<>(entities.values());
	}

}
