package cz.consumer.interview.memsource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cz.consumer.interview.AuthToken;
import cz.consumer.interview.Project;

@Service
public class MemsourceRestApi implements MemsourceApi {
	
	private String memsourceApiUrl;
	
	private RestTemplate restTemplate;

	@Autowired
	public MemsourceRestApi(RestTemplate restTemplate, String memsourceApiUrl) {
		this.restTemplate = restTemplate;
		this.memsourceApiUrl = memsourceApiUrl;
	}

	@Override
	public AuthToken login(String username, String password) throws NotAuthorized {
		try {		
			LoginResponse loginResponse = restTemplate.getForObject(memsourceApiUrl + "/auth/login?userName=" + username + "&password=" + password, LoginResponse.class);
			
			if(loginResponse.getToken() == null) {			
				throw new NotAuthorized("Token not present in response.");
			}
			
			return AuthToken.create(loginResponse.getToken(), loginResponse.getExpires());
		} catch(Exception ex) {
			throw new NotAuthorized(ex);
		}		
	}

	@Override
	public List<Project> list(AuthToken authToken) {
		ProjectResponse[] projectList = restTemplate.getForObject(memsourceApiUrl + "/project/list?token=" + authToken.getToken(), ProjectResponse[].class);
		return mapProjectsFromProjectResponses(projectList);
	}

	private List<Project> mapProjectsFromProjectResponses(ProjectResponse[] projectList) {
		List<Project> projects = new ArrayList<Project>();
		for(ProjectResponse projectResponse : projectList) {
			Project project = mapProjectFromProjectResponse(projectResponse);
			projects.add(project);
		}
		
		return projects;
	}

	private Project mapProjectFromProjectResponse(ProjectResponse projectResponse) {
		Project project = new  Project();
		project.setId(projectResponse.getId());
		project.setName(projectResponse.getName());
		project.setSourceLang(projectResponse.getSourceLang());
		project.setStatus(projectResponse.getStatus());
		project.setTargetLangs(projectResponse.getTargetLangs());
		return project;
	}
	
}