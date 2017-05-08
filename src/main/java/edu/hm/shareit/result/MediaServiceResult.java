package edu.hm.shareit.result;

import javax.ws.rs.core.Response.Status;

/**
 * Enum for service result. Contains possible error messages. 
 * 
 * @author Dmitry Dorodnov
 *
 */
public enum MediaServiceResult {
	
	OK(200, Status.OK, "Kein Fehler"),
	
	ISBN_INVALID(400, Status.BAD_REQUEST, "Ungueltige ISBN"),
	ISBN_NOT_FOUND(400, Status.BAD_REQUEST, "ISBN nicht gefunden"),
	ISBN_DUPLICATED(400, Status.BAD_REQUEST, "ISBN bereits vorhanden"),
	ISBN_CONFLICT(400, Status.BAD_REQUEST, "ISBN soll modifiziert werden"),
	
	BOOK_DATA_INCOMPLETE(400, Status.BAD_REQUEST, "Autor oder Titel fehlt"),
	BOOK_DATA_INVALID(400, Status.BAD_REQUEST, "Autor und Titel fehlen"),
	
	DISC_DATA_INCOMPLETE(400, Status.BAD_REQUEST, "Director, FSK oder Titel fehlen"),
	DISC_DATA_INVALID(400, Status.BAD_REQUEST, "Director, FSK und Titel fehlen"),
	
	BARCODE_INVALID(400, Status.BAD_REQUEST, "Ungueltiger Barcode"),
	BARCODE_NOT_FOUND(400, Status.BAD_REQUEST, "Barcode nicht gefunden"),
	BARCODE_DUPLICATED(400, Status.BAD_REQUEST, "Barcode bereits vorhanden"),
	BARCODE_CONFLICT(400, Status.BAD_REQUEST, "Barcode soll modiziert werden"),
	;
	
	MediaServiceResult(int code, Status status, String message){
		this.code = code;
		this.state = status;
		this.message = message;
	}
	
	private int code;
	private Status state;
	private String message;
	
	public int getCode(){
		return this.code;
	}
	public Status getStatus(){
		return this.state;
	}
	public String getMessage(){
		return this.message;
	}
}
