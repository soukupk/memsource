package cz.consumer.interview.memsource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MemsourceConfig {

	@Value("${memsource.api.url}")
    public String url;

}
