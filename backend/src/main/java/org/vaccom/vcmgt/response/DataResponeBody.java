package org.vaccom.vcmgt.response;

public class DataResponeBody {
	public long total;
	public Object data;
	
	public DataResponeBody(long total, Object data) {
		this.total = total;
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
