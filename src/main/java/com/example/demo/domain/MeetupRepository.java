package com.example.demo.domain;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MeetupRepository extends PagingAndSortingRepository<Meetup, Long>, QueryDslPredicateExecutor<Meetup> {

}
