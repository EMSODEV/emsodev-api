package eu.emsodev.observations.model;

public class ObservationsOld {


	private final long id;
	private final String content;

	public ObservationsOld(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}


}
