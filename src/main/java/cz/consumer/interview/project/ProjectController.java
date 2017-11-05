package cz.consumer.interview.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cz.consumer.interview.Mappings;
import cz.consumer.interview.accountconfiguration.Account;
import cz.consumer.interview.accountconfiguration.AccountConfigurator;
import cz.consumer.interview.accountconfiguration.ConfigurationNotFound;
import cz.consumer.interview.memsource.AuthToken;
import cz.consumer.interview.memsource.MemsourceApi;
import cz.consumer.interview.memsource.NotAuthorized;

@RestController
@RequestMapping(Mappings.PROJECTS)
public class ProjectController {

	private MemsourceApi memsourceRestApi;
	
	private AccountConfigurator accountConfigurator;
	
	@Autowired
	public ProjectController(MemsourceApi memsourceRestApi, AccountConfigurator accountConfigurator) {
		this.memsourceRestApi = memsourceRestApi;
		this.accountConfigurator = accountConfigurator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> list() {
		Account account;
		try {
			account = accountConfigurator.getConfiguration();
			AuthToken authToken = memsourceRestApi.login(account.getUsername(), account.getPassword());
			return new ResponseEntity<List<Project>>(memsourceRestApi.list(authToken), HttpStatus.OK);
		} catch (ConfigurationNotFound e) {
	    	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
	    	objectNode.put("message", "Configuration not found.");
			return new ResponseEntity<JsonNode>(objectNode, HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorized e) {
	    	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
	    	objectNode.put("message", "Not authorized.");
			return new ResponseEntity<JsonNode>(objectNode, HttpStatus.UNAUTHORIZED);
		}
	} 
	
}
