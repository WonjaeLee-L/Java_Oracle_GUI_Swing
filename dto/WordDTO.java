package dto;

public class WordDTO {
	private String eword = null;
	private String kword = null;

	public String getEword() {
		return eword;
	}

	public void setEword(String eword) {
		this.eword = eword;
	}

	public String getKword() {
		return kword;
	}

	public void setKword(String kword) {
		this.kword = kword;
	}

	@Override
	public String toString() {
		return "WordDTO [eword=" + eword + ", kword=" + kword + "]";
	}

}
