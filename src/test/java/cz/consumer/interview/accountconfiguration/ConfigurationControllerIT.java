package cz.consumer.interview.accountconfiguration;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cz.consumer.interview.Mappings;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigurationControllerIT {

	private static final String PASSWORD = "PASSWORD";

	private static final String USERNAME = "USERNAME";

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private AccountRepository accountRepository;
    
	
    @Test
    public void testConfiguration_shouldReturnStatusOKAndSaveAccountConfiguration() throws Exception {
    	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
    	objectNode.put("username", USERNAME);
    	objectNode.put("password", PASSWORD);
    	
    	ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity("http://localhost:" + port + Mappings.CONFIGURATION, objectNode, JsonNode.class);
    	Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
    	
    	List<Account> accounts = accountRepository.findAll();
    	Assert.assertThat(accounts.size(), CoreMatchers.is(1));
    	
    	Account account = accounts.get(0);    	
    	Assert.assertThat(account.getUsername(), CoreMatchers.is(USERNAME));
    	Assert.assertThat(account.getPassword(), CoreMatchers.is(PASSWORD));
    }
    
}
