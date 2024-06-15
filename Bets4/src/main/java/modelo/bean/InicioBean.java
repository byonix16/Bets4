package modelo.bean;

import businessLogic.BLFacadeImplementation;


public class InicioBean {

	private static BLFacadeImplementation blf = null;

	public static BLFacadeImplementation getBLF() {
		return blf;
	}

	public InicioBean() {
		if (blf == null)
			blf = new BLFacadeImplementation();
	}

	public String queryQuestions() {
		return "Query";
	}

	public String createQuestions() {
		return "Create";
	}
}
