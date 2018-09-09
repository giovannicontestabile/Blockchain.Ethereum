package com.projects.blockchain.ethereum.poc.microservices.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projects.blockchain.ethereum.utility.microservices.EtherTransferEvent;
import com.projects.blockchain.ethereum.utility.microservices.EventsContainer;
import com.projects.blockchain.ethereum.utility.microservices.SmartContractEvent;

/**
 * Spring Boot RESTful service allowing to interact with the MongoDB EventsDatabase.
 * */
@RestController
@RequestMapping("/events")
public final class EventsRestController {
	
	@Autowired
	private SmartContractEventsRepository smartContractsRepository;
	@Autowired
	private EtherTransferEventsRepository etherTransferRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getEtherTransferEventBySourceAccount/{sourceAccount}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<EtherTransferEvent> getEtherTransferBySourceAccount(@PathVariable final String sourceAccount) {
		return etherTransferRepository.findBySourceAccount(sourceAccount);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getEtherTransferEvents", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<EtherTransferEvent> getEtherTransferEvents() {
		return etherTransferRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addEtherTransferEvent")
	public ResponseEntity<String> addEtherTransferEvent(@RequestBody final EtherTransferEvent etherTransferEvent) {
		try {
			etherTransferRepository.save(etherTransferEvent);
			return new ResponseEntity<String>("Ether transfer event saved.", HttpStatus.OK);
		}
		catch(final Exception e) {
			return new ResponseEntity<String>("Ether transfer event save failure.\n"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addEtherTransferEvents")
	public ResponseEntity<String> addEtherTransferEvents(@RequestBody final EventsContainer<EtherTransferEvent> etherTransferEvents) {
		try {
			etherTransferRepository.saveAll(etherTransferEvents.getEvents());
			return new ResponseEntity<String>("Ether transfer events saved.", HttpStatus.OK);
		}
		catch(final Exception e) {
			return new ResponseEntity<String>("Ether transfer events save failure.\n"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addSmartContractEvents")
	public ResponseEntity<String> addSmartContractEvents(@RequestBody final EventsContainer<SmartContractEvent> etherTransferEvents) {
		try {
			smartContractsRepository.saveAll(etherTransferEvents.getEvents());
			return new ResponseEntity<String>("Ether transfer events saved.", HttpStatus.OK);
		}
		catch(final Exception e) {
			return new ResponseEntity<String>("Ether transfer events save failure.\n"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSmartContractEvents", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<SmartContractEvent> getSmartContractEvents() {
		return smartContractsRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addSmartContractEvent")
	public ResponseEntity<String> addSmartContractEvent(@RequestBody final SmartContractEvent smartContractEvent) {
		try {
			smartContractsRepository.save(smartContractEvent);	
			return new ResponseEntity<String>("Smart contract event saved.", HttpStatus.OK);
		}
		catch(final Exception e) {
			return new ResponseEntity<String>("Smart contract save failure.\n"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}