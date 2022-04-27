package com.liferay.custom.rest.service;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOfferSearch.Itinerary;
import com.amadeus.resources.FlightOfferSearch.SearchSegment;
import com.liferay.custom.rest.service.FlightDetail.Leg;

@RestController
@RequestMapping(value = "/flight")
public class FlightApiController {

	@Resource
	@Qualifier("amadeusClient")
	Amadeus amadeus;

	@Resource
	@Qualifier("lcpDomain")
	String lcpDomain;

	private static final Logger logger = LoggerFactory.getLogger(FlightApiController.class);

	@ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<String> handleResponseException(ResponseException responseException) {
		logger.error(responseException.getMessage());
        return new ResponseEntity<String>("Response exception: " + responseException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@GetMapping(value = "/search")
	public ResponseEntity<List<FlightDetail>> search(@RequestParam String origin, @RequestParam String destination, @RequestParam Optional<String> departureDate, @RequestParam Optional<Integer> adults) throws ResponseException {
		logger.info("Flight search " + origin + " -> " + destination);

		logger.info("calling API with id " + amadeus.getConfiguration().getClientId());
		logger.info("calling API with secret " + amadeus.getConfiguration().getClientSecret());

		List<FlightDetail> flightDetails = Collections.emptyList();

		try {
			FlightOfferSearch[] flightOffers = amadeus.shopping.flightOffersSearch.get(
				Params.with(
					"originLocationCode", origin
				).and(
					"destinationLocationCode", destination
				).and(
					"departureDate", departureDate.orElse("2022-06-01")
				).and(
					"adults", adults.orElse(2)
				)
			);

			logger.info("# of offers found: " + flightOffers.length);

			flightDetails = Arrays.stream(
				flightOffers
			).map(
				FlightApiController::_toDetail
			).collect(
				Collectors.toList()
			);
		} catch (Exception e) {
			logger.error("API failed, falling back to mock data", e);
			
			flightDetails = _mockDetails(origin, destination);
		}

		return new ResponseEntity<List<FlightDetail>>(flightDetails, HttpStatus.OK);
	}

	private List<FlightDetail> _mockDetails(String origin, String destination) {
		FlightDetail fd = new FlightDetail();

		fd.totalDuration = "PT14H30M";
		Leg leg1 = new Leg();
		leg1.airline = "AA";
		leg1.departureCode = origin;
		leg1.arrivalCode = "LHR";
		leg1.duration = "8hr 44min";

		Leg leg2 = new Leg();
		leg2.airline = "BA";
		leg2.departureCode = "LHR";
		leg2.arrivalCode = destination;
		leg2.duration = "3hr 10min";

		fd.legs = new Leg[] {leg1, leg2};
		
		return Collections.singletonList(fd);
	}

	private static FlightDetail _toDetail(FlightOfferSearch offer) {
		FlightDetail detail = new FlightDetail();
		Itinerary itinerary = offer.getItineraries()[0];
		detail.legs = Arrays.stream(
			itinerary.getSegments()
		).map(
			FlightApiController::_toLeg
		).collect(
			Collectors.toList()
		).toArray(
			new Leg[0]
		);
		detail.totalDuration = itinerary.getDuration();
		return detail;
	}
	
	private static Leg _toLeg(SearchSegment segment) {
		Leg leg = new Leg();
		leg.airline = segment.getCarrierCode();
		leg.departureCode = segment.getDeparture().getIataCode();
		leg.arrivalCode = segment.getArrival().getIataCode();

		Matcher matcher = _durationPattern.matcher(segment.getDuration().trim());

		if (matcher.matches()) {
			leg.duration = matcher.group(1) + (matcher.groupCount() == 5 ? matcher.group(3) : "");
		}
		else {
			leg.duration = segment.getDuration();
		}

		return leg;
	}

	private static Pattern _durationPattern = Pattern.compile("PT(([0-9]+)H)?(([0-9]+)M)?");
}