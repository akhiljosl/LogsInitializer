package com.loggertool.inhouselogsanalyser;

import java.math.BigInteger;

public class SingleRowDescription {
	
	String id;
	BigInteger timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id2) {
		this.id = id2;
	}

	public BigInteger getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(BigInteger ts1) {
		this.timestamp = ts1;
	}

	@Override
	public String toString() {
		return "SingleRowDesc [id=" + id + ", timestamp=" + timestamp + "]";
	}
		
}
