package Tokenizer;

public class QuadEntry {

	private int index;
	private String quad1;
	private String quad2;
	private String quad3;
	private String quad4;
	public String getQuad1() {
		return quad1;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getQuad2() {
		return quad2;
	}
	public void setQuad2(String quad2) {
		this.quad2 = quad2;
	}
	public String getQuad3() {
		return quad3;
	}
	public void setQuad3(String quad3) {
		this.quad3 = quad3;
	}
	public String getQuad4() {
		return quad4;
	}
	public void setQuad4(String quad4) {
		this.quad4 = quad4;
	}
	public void setQuad1(String quad1) {
		this.quad1 = quad1;
	}
	public QuadEntry(int index, String quad1, String quad2, String quad3,
			String quad4) {
		super();
		this.index = index;
		this.quad1 = quad1;
		this.quad2 = quad2;
		this.quad3 = quad3;
		this.quad4 = quad4;
	}
	@Override
	public String toString() {
		return "QuadEntry [index=" + index + ", quad1=" + quad1 + ", quad2="
				+ quad2 + ", quad3=" + quad3 + ", quad4=" + quad4 + "]";
	}
	
	
	

}
