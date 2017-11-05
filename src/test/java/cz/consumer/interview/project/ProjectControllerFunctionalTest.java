package cz.consumer.interview.project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import cz.consumer.interview.Mappings;
import cz.consumer.interview.accountconfiguration.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class ProjectControllerFunctionalTest {

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private cz.consumer.interview.accountconfiguration.AccountConfigurator accountConfigurator;
    
	@Value("${memsource.api.username}")
    public String username;
	
	@Value("${memsource.api.password}")
    public String password;

    @Test
    public void testList_shouldReturnAtLeastOneProjectAndFirstElementHasAllRequiredProperties() throws Exception {
    	accountConfigurator.createOrUpdateConfiguration(Account.create(username, password));
    	
    	JsonNode response = restTemplate.getForObject("http://localhost:" + port + Mappings.PROJECTS, JsonNode.class);
    	
    	Assert.assertEquals(response.elements().hasNext(), true);
    	JsonNode jsonNode = response.elements().next();
    	Assert.assertEquals(jsonNode.has("name"), true);
    	Assert.assertEquals(jsonNode.has("status"), true);
    	Assert.assertEquals(jsonNode.has("sourceLang"), true);
    	Assert.assertEquals(jsonNode.has("targetLangs"), true);
    	Assert.assertEquals(jsonNode.get("targetLangs").hasNonNull(0), true);
    }
	
}
