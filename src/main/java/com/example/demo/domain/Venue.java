package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Venue {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object o) {
		return this == o || !(o == null || getClass() != o.getClass()) && Objects.equals(id, ((Venue) o).id);
	}
}
