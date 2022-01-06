package com.example.spring.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private int authlevel;
	private String name;
	private String nickname;
	private String cellphoneNo;
	private String email;
	private boolean delStatus;
	private String delDate;
	
	public String getProfileImgUri() {
        return "/common/genFile/file/member/" + id + "/extra/profileImg/1";
    }

    public String getProfileFallbackImgUri() {
        return "https://via.placeholder.com/300?text=^_^";
    }

    public String getProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getProfileFallbackImgUri() + "'";
    }
}

	