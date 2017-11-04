package cz.consumer.interview.memsource;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectResponse {

	private Long id;
	
	private String name;
	
	private String status;
	
	private Locale sourceLang;
	
	private List<Locale> targetLangs;
	
	private Date dateCreated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Locale getSourceLang() {
		return sourceLang;
	}

	public void setSourceLang(Locale sourceLang) {
		this.sourceLang = sourceLang;
	}

	public List<Locale> getTargetLangs() {
		return targetLangs;
	}

	public void setTargetLangs(List<Locale> targetLangs) {
		this.targetLangs = targetLangs;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
