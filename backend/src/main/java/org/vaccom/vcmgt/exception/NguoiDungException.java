package org.vaccom.vcmgt.exception;

public class NguoiDungException extends Exception {

	private static final long serialVersionUID = 1L;

	public int status;

	public NguoiDungException(String msg, int status) {
		super(msg);
		setStatus(status);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
