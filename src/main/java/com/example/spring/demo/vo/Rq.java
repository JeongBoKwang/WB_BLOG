package com.example.spring.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.spring.demo.service.MemberService;
import com.example.spring.demo.util.Ut;

import lombok.Getter;
import lombok.ToString;
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		
		paramMap = Ut.getParamMap(req);

		this.session = req.getSession();
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
		this.req.setAttribute("rq", this);
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNotLogined() {
		return !isLogined;
	}

	public void println(String str) {
		print(str + "\n");
	}
	//로그인아이디 받기
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}
	//로그아웃 
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public String historyBackJsOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}
	
	public String historyBackJsOnView(String resultCode, String msg) {
		req.setAttribute("msg", String.format("[%s] %s", resultCode, msg));
		req.setAttribute("historyBack", true);
		return "common/js";
	}
	
	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsHistoryBack(String resultCode ,String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}
	
	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		if(queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}
		
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getUriEncoded(getCurrentUri());
	}
	
	public String getJoinUri() {
		return "../member/join?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getLogoutUri() {
		String requestUri = req.getRequestURI();

		// 필요하다면 활성화
		/*
		switch (requestUri) {
		case "/usr/article/write":
			return "/";
		}
		*/

		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}
	
	public String getAfterLoginUri() {
		String requestUri = req.getRequestURI();
		// 로그인 후 다시 돌아가면 안되는 페이지 URL 들을 적으시면 됩니다.
		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			return Ut.getUriEncoded(Ut.getStrAttr(paramMap, "afterLoginUri", ""));
		}

		return getEncodedCurrentUri();
	}
	

	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}
	
	//이 메서드는 Rq객체가 자연스럽게 생성되도록 유도하는 역할
	//지우면안되고 편의를 위해 BeforeActionInterceptor에서 생성
	public void initOnBeforeActionInterceptor() {
		
	}

	public void printReplaceJs(String msg, String uri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, uri));
		
	}
	//로그인 후 상세페이지 이동 후 뒤로가기 시 버그 수정
	public String getArticleDetailUriFromArticleList(Article article) {
		return "../article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}
}
