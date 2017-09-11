package com.example.demo.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("mvc_integration_test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class MeetupQuerydslPredicatesTest {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	MeetupRepository meetupRepository;

	@Autowired
	VenueRepository venueRepository;

	@Before
	public void setUp() throws Exception {
		Map<String, String> properties0 = new HashMap<>();
		properties0.put("aKey", "aValue");

		Venue venue = new Venue();
		venue.setName("VenueName");
		Venue savedVenue = venueRepository.save(venue);

		Meetup meetup0 = new Meetup();
		meetup0.setName("john");
		meetup0.setVenue(savedVenue);
		meetup0.setProperties(properties0);
		meetupRepository.save(meetup0);

		// Meetup 1
		Map<String, String> properties1 = new HashMap<>();
		properties1.put("aKey1", "aValue1");

		Meetup meetup1 = new Meetup();
		meetup1.setName("fred");
		meetup1.setProperties(properties1);
		meetupRepository.save(meetup1);
	}

	/**
	 * This one is the reason for <a href="https://stackoverflow.com/questions/45989336/querydsl-web-query-on-the-key-of-a-map-field">https://stackoverflow.com/questions/45989336/querydsl-web-query-on-the-key-of-a-map-field</a>
	 */
	@Test
	public void testQueryProperties() throws Exception {

		// ===================================================================================================================
		// Query by meetup.name
		// This test passes as expected
		// ===================================================================================================================
		mvc.perform(get("/meetups")
				            .param("name", "fred"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentTypeCompatibleWith(HAL_JSON))
		   .andExpect(jsonPath("_embedded.meetups", hasSize(1)))
		   .andExpect(jsonPath("_embedded.meetups[0].name", is("fred")));

		// ===================================================================================================================
		// Query by meetup.venue.name
		// This test passes as expected
		// ===================================================================================================================
		mvc.perform(get("/meetups")
				            .param("venue.name", "VenueName"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentTypeCompatibleWith(HAL_JSON))
		   .andExpect(jsonPath("_embedded.meetups", hasSize(1)))
		   .andExpect(jsonPath("_embedded.meetups[0].name", is("john")))
		   .andExpect(jsonPath("_embedded.meetups[0].venue.name", is("VenueName")));

		// ===================================================================================================================
		// Query by meetup.properties
		// This query should filter out meetup1
		// We should get just 1 meetup
		// But since the query param is unrecognized, it's ignored, and we get all meetups. None are filtered out.
		// ===================================================================================================================

		mvc.perform(get("/meetups")
				            .param("properties[aKey]", "aValue"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentTypeCompatibleWith(HAL_JSON))
		   .andExpect(jsonPath("_embedded.meetups", hasSize(1)))
		   .andExpect(jsonPath("_embedded.meetups[0].name", is("john")));
	}


}