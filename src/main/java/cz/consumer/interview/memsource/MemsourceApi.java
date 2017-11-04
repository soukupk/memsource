package cz.consumer.interview.memsource;

import java.util.List;

import cz.consumer.interview.AuthToken;
import cz.consumer.interview.Project;

public interface MemsourceApi {

	AuthToken login(String username, String password) throws NotAuthorized;

	List<Project> list(AuthToken authToken);

}