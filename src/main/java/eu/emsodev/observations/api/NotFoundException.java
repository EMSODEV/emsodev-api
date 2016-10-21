package eu.emsodev.observations.api;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class NotFoundException extends ApiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	public NotFoundException (int code, String msg) {
		super(code, msg);
		this.code = code;
	}
}
