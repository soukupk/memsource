package cz.consumer.interview;

import java.util.List;
import java.util.Locale;

public class Project {
	
	private Long id;
	
	private String name;
	
	private String status;
	
	private Locale sourceLang;
	
	private List<Locale> targetLangs;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sourceLang == null) ? 0 : sourceLang.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((targetLangs == null) ? 0 : targetLangs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceLang == null) {
			if (other.sourceLang != null)
				return false;
		} else if (!sourceLang.equals(other.sourceLang))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (targetLangs == null) {
			if (other.targetLangs != null)
				return false;
		} else if (!targetLangs.equals(other.targetLangs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", status=" + status + ", sourceLang=" + sourceLang
				+ ", targetLangs=" + targetLangs + "]";
	}
	
}
