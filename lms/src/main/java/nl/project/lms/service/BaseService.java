package nl.project.lms.service;

import java.util.List;

public interface BaseService<T, ID> {
	
	List<T> findAll();

}
