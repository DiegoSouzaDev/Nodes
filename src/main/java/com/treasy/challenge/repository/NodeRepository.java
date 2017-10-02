package com.treasy.challenge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.treasy.challenge.model.Node;

public interface NodeRepository extends PagingAndSortingRepository<Node, Long> {
	
}
