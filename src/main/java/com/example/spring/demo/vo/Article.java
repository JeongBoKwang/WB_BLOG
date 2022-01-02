package com.example.spring.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private int boardId;
	private int memberId;
	private String title;
	private String body;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
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
