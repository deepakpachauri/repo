package nl.project.lms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import nl.project.lms.service.BaseService;

public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

	@Autowired
	CrudRepository<T, ID> repository;

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		return list;
	}

}
