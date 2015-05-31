package Tokenizer;

public class SymbolTableEntry {

	private int index;
	private String nameOfSymbol;
	private String id;
	private String type;
	private Integer block;
	private Integer offset;
	private Integer quadNumber;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getNameOfSymbol() {
		return nameOfSymbol;
	}
	public void setNameOfSymbol(String nameOfSymbol) {
		this.nameOfSymbol = nameOfSymbol;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getQuadNumber() {
		return quadNumber;
	}
	public void setQuadNumber(Integer quadNumber) {
		this.quadNumber = quadNumber;
	}
	@Override
	public String toString() {
		return "SymbolTableEntry [index=" + index + ", nameOfSymbol="
				+ nameOfSymbol + ", id=" + id + ", type=" + type + ", block="
				+ block + ", offset=" + offset + ", quadNumber=" + quadNumber
				+ "]";
	}
	public SymbolTableEntry(int index, String nameOfSymbol, String id,
			String type, Integer block, Integer offset, Integer quadNumber) {
		super();
		this.index = index;
		this.nameOfSymbol = nameOfSymbol;
		this.id = id;
		this.type = type;
		this.block = block;
		this.offset = offset;
		this.quadNumber = quadNumber;
	}
	
	
	
	
}
