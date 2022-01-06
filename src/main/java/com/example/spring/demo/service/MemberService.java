package com.example.spring.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.spring.demo.repository.MemberRepository;
import com.example.spring.demo.util.Ut;
import com.example.spring.demo.vo.Member;
import com.example.spring.demo.vo.ResultData;

@Service
public class MemberService {
	@Value("${custom.siteMainUri}")
    private String siteMainUri;
    @Value("${custom.siteName}")
    private String siteName;
	
	private MailService mailService;
	private MemberRepository memberRepository;
	private AttrService attrService;

	public MemberService(MemberRepository memberRepository, AttrService attrService, MailService mailService) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
		this.mailService = mailService;
	}
	
	//회원가입
	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		// 로그인아이디 중복체크
		Member oldMember = getMemberByLoginId(loginId);

		if (oldMember != null) {
			return ResultData.from("F-7", Ut.f("해당 로그인 아이디(%s)는 이미 사용중입니다.", loginId));
		}
		// 이름 + 이메일 중복체크
		oldMember = getMemberByNameAndEmail(name, email);

		if (oldMember != null) {
			return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
		}

		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}
	
	//이름,메일 가져오기
	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	//로그인 아이디 가져오기
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	//로그인 회원번호 가져오기
	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
	//회원수정(번호,비밀번호,이름,닉네임,전화번호,이메일 가져오기)
	public ResultData modify(int id, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		memberRepository.modify(id, loginPw, name, nickname, cellphoneNo, email);
		
		if(loginPw != null) {
			attrService.remove("member", id, "extra", "useTempPassword");
		}
		
		return ResultData.from("S-1", "회원정보가 수정되었습니다.");
	}
	
	public String genMemberModifyAuthKey(int actorId) {
		String memberModifyAuthKey = Ut.getTempPassword(10);
		
		attrService.setValue("member", actorId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Ut.getDateStrLater(60 * 5));
		
		
		return memberModifyAuthKey;
	}
	
	public ResultData checkMemberModifyAuthKey(int actorId, String memberModifyAuthKey) {
		
		String saved = attrService.getValue("member", actorId, "extra", "memberModifyAuthKey");
		
		if(!saved.equals(memberModifyAuthKey)) {
			return ResultData.from("F-1", "일치하지 않거나 만료되었습니다.");
		}
		
		return ResultData.from("S-1", "정상적인 코드입니다.");
	}

	public ResultData notifyTempLoginPwByEmail(Member actor) {
		String title = "[" + siteName + "] 임시 패스워드 발송";
        String tempPassword = Ut.getTempPassword(6);
        String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
        body += "<a href=\"" + siteMainUri + "/mpaUsr/member/login\" target=\"_blank\">로그인 하러가기</a>";

        ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

        if (sendResultData.isFail()) {
            return sendResultData;
        }
        
        tempPassword = Ut.sha256(tempPassword);

        setTempPassword(actor, tempPassword);

        return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}

	private void setTempPassword(Member actor, String tempPassword) {
		attrService.setValue("member", actor.getId(), "extra", "useTempPassword", "1", null);
		memberRepository.modify(actor.getId(), tempPassword, null, null, null, null);
	}

	public void deleteMember(int id) {
		memberRepository.deleteMember(id);	
	}

	public boolean isUsingTempPassword(int actorId) {
		return attrService.getValue("member", actorId, "extra", "useTempPassword").equals("1");
	}

}
