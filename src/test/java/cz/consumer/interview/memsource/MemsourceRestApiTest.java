package cz.consumer.interview.memsource;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import cz.consumer.interview.AuthToken;
import cz.consumer.interview.Project;
import cz.consumer.interview.memsource.MemsourceApi;
import cz.consumer.interview.memsource.MemsourceConfig;
import cz.consumer.interview.memsource.MemsourceRestApi;
import cz.consumer.interview.memsource.NotAuthorized;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/test.properties")
public class MemsourceRestApiTest {
	
	@Autowired
	MemsourceConfig memsourceConfig;
	
	MemsourceApi memsourceRestApi;
	
	@Value("${memsource.api.username}")
    public String username;
	
	@Value("${memsource.api.password}")
    public String password;
	
	@Before
	public void setUp() {
		memsourceRestApi = new MemsourceRestApi(new RestTemplate(), memsourceConfig.url);
	}

	@Test
	public void testLogin_returnsTokenWhenCredentialsAreValid() throws NotAuthorized {
		AuthToken authToken = memsourceRestApi.login(username, password);
		
		Assert.assertThat(authToken.getToken().length(), CoreMatchers.is(Matchers.greaterThan(20)));
	}
	
	@Test(expected = NotAuthorized.class)
	public void testLogin_throwsNotAuthorizedWhenBadPassword() throws NotAuthorized {
		memsourceRestApi.login(username, "BAD_PASSWORD");
	}
	
	@Test
	public void testList_returnsAtLeastOneProjectWithAllRequiredParams() throws NotAuthorized {
		AuthToken authToken = memsourceRestApi.login(username, password);
		
		List<Project> projects = memsourceRestApi.list(authToken);
		
		assertContainsAtLeastOneProject(projects);
		assertFirstProjectContainsAllRequiredParams(projects);
	}

	private void assertContainsAtLeastOneProject(List<Project> projects) {
		Assert.assertThat(projects.size(), CoreMatchers.is(Matchers.greaterThan(0)));
	}

	private void assertFirstProjectContainsAllRequiredParams(List<Project> projects) {
		Project project = projects.get(0);
		
		Assert.assertNotNull(project.getName());
		Assert.assertNotNull(project.getStatus());
		Assert.assertNotNull(project.getSourceLang());
		Assert.assertThat(project.getTargetLangs().size(), CoreMatchers.is(Matchers.greaterThan(0)));
	}
}
