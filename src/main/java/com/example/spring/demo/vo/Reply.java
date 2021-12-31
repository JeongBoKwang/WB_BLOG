package com.example.spring.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;

	private String extra__writerName;
	private boolean extra__actorCanModify;
	private boolean extra__actorCanDelete;

	public String getbodyForPrint() {
		return body.replaceAll("\n", "<br>");
	}
	
	public String getRegDateForPrint() {
		return regDate.substring(2, 16).replace(" ", "<br>");
	}
	
	public String getUpdateDateForPrint() {
		return updateDate.substring(2, 16).replace(" ", "<br>");
	}
	
	public String getUpdateDateForPrintType2() {
		return updateDate.substring(2, 16);
	}
	
	public String getRegDateForPrintType2() {
		return updateDate.substring(2, 16);
	}
}