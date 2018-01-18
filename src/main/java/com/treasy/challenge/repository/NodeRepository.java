package com.treasy.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.treasy.challenge.model.Node;

@Component
public interface NodeRepository extends PagingAndSortingRepository<Node, Long> {
	
	@Query("select n from Node n where n.parent.id is null")
	public List<Node> findEntireTree();

	public List<Node> findByParentId(final Long parentId);
	
}
