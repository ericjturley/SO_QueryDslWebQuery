package com.example.demo.domain;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Meetup {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	private String name;

//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OneToOne
	@RestResource(exported = false)
	private Venue venue;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "MEETUP_PROPERTY", joinColumns = @JoinColumn(name = "MEETUP_ID"))
	@MapKeyColumn(name = "KEY")
	@Column(name = "VALUE", length = 2048)
	private Map<String, String> properties = new HashMap<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		return this == o || !(o == null || getClass() != o.getClass()) && Objects.equals(id, ((Meetup) o).id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
