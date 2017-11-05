package cz.consumer.interview.accountconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import cz.consumer.interview.Mappings;

@RestController
@RequestMapping(Mappings.CONFIGURATION)
public class ConfigurationController {
	
	private AccountConfigurator accountConfigurator;
	
	@Autowired
	public ConfigurationController(AccountConfigurator accountConfigurator) {
		this.accountConfigurator = accountConfigurator;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Validated @RequestBody AccountRequestBody accountRequestBody) {
		accountConfigurator.createOrUpdateConfiguration(Account.create(accountRequestBody.getUsername(), accountRequestBody.getPassword()));
		return new ResponseEntity<JsonNode>(HttpStatus.OK);
	} 
	
}
