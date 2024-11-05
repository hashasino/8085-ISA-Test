class TaggedString {
	private String value;
	private boolean accessed;

	public TaggedString(String value) {
		this.value = value;
		this.accessed = false;
	}

	public String getValue() {
		this.accessed = true; // Mark as accessed when value is retrieved
		return value;
	}

	public boolean isAccessed() {
		return accessed;
	}
}