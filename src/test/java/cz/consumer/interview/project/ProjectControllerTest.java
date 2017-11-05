package cz.consumer.interview.project;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cz.consumer.interview.accountconfiguration.Account;
import cz.consumer.interview.accountconfiguration.AccountConfigurator;
import cz.consumer.interview.accountconfiguration.AccountRepository;
import cz.consumer.interview.accountconfiguration.FakeAccountRepository;
import cz.consumer.interview.memsource.AuthToken;
import cz.consumer.interview.memsource.MemsourceApi;
import cz.consumer.interview.memsource.NotAuthorized;


@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {
	
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String VALID_TOKEN = "valid_token";
	
	private MemsourceApi memsourceApi;
	
	private AccountRepository accountRepository;
	
	private AccountConfigurator accountConfigurator;
	
	private ProjectController projectController;
	
	@Before
	public void setUp() {
    	memsourceApi = Mockito.mock(MemsourceApi.class);
    	accountRepository = new FakeAccountRepository();
    	accountConfigurator = new AccountConfigurator(accountRepository);    	
    	projectController = new ProjectController(memsourceApi, accountConfigurator);
	}
	
    @Test
    public void testList_shouldReturnProjectsFromMemsourceApiWithOkStatus_whenAuthorized() throws Exception {
    	AuthToken authToken = AuthToken.create(VALID_TOKEN, new Date());
    	
    	List<Project> projects = Arrays.asList(
    			createProject(1l, "Memsource project", Locale.CHINA, Arrays.asList(Locale.ENGLISH))
    			);
    	
    	Mockito.when(memsourceApi.login(USERNAME, PASSWORD)).thenReturn(authToken);
    	Mockito.when(memsourceApi.list(authToken)).thenReturn(projects);
    	accountRepository.save(Account.create(USERNAME, PASSWORD));
    	    	
    	ResponseEntity<?> responseEntity = projectController.list();
    	Assert.assertThat(responseEntity.getBody(), CoreMatchers.is(projects));
    	Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
    }
    
    @Test
    public void testList_shouldReturnUnauthorizedStatus_WhenInvalidAuthorization() throws Exception {
    	Mockito.when(memsourceApi.login(USERNAME, PASSWORD)).thenThrow(new NotAuthorized("Not authorized"));
    	accountRepository.save(Account.create(USERNAME, PASSWORD));
    	    	
    	ResponseEntity<?> responseEntity = projectController.list();
    	Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.UNAUTHORIZED));
    }
    
    @Test
    public void testList_shouldReturnUnauthorizedStatus_WhenConfigurationNotSavedInDatabase() throws Exception {
    	ResponseEntity<?> responseEntity = projectController.list();
    	Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.UNAUTHORIZED));
    }

	private Project createProject(long id, String name, Locale sourceLang, List<Locale> targetLangs) {
		Project project = new Project();
    	project.setId(id);
    	project.setName(name);
    	project.setSourceLang(sourceLang);
    	project.setTargetLangs(targetLangs);
		return project;
	}
	
}
