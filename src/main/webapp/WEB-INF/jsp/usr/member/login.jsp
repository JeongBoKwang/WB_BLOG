<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="로그인" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script>
let MemberLogin__submitFormDone = false;
function MemberLogin__submitForm(form) {
    if ( MemberLogin__submitFormDone ) {
        return;
    }
    form.loginId.value = form.loginId.value.trim();
    if ( form.loginId.value.length == 0 ) {
        alert('로그인아이디를 입력해주세요.');
        form.loginId.focus();
        return;
    }
    form.loginPwInput.value = form.loginPwInput.value.trim();
    if ( form.loginPwInput.value.length == 0 ) {
        alert('로그인비밀번호을 입력해주세요.');
        form.loginPwInput.focus();
        return;
    }
    form.loginPw.value = sha256(form.loginPwInput.value);
    form.loginPwInput.value = '';
    form.submit();
    MemberLogin__submitFormDone = true;
}
</script>

<div class="section section-article-list px-3 login">
	<div class="container mx-auto">
	    <form method="POST" action="../member/doLogin" onsubmit="MemberLogin__submitForm(this); return false;">
	        <input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
          	<input type="hidden" name="loginPw"/>
	          <div class="form-control w-96">
                <label class="label">
                   	아이디
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="loginId" placeholder="아이디를 입력해주세요." />
              </div>
            <div class="form-control w-96">
                <label class="label">
                   	비밀번호
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwInput" placeholder="비밀번호를 입력해주세요." />
            </div>
            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" class="btn btn-ghost">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>로그인</span>
                </button>

                <a href="../member/findLoginId" type="submit" class="link link-hover">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>아이디 찾기</span>
                </a>

                <a href="../member/findLoginPw" type="submit" class="link link-hover">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>비밀번호 찾기</span>
                </a>

                <a href="/" class="link link-hover">
                	&nbsp;
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>홈</span>
                </a>
            </div>
	    </form>
	</div>
</div>
<div id="footerwrap_member">
		<div class="footerlogo_member"></div>
        <div class="footer after">
        	<div class="mail">
        		<i class="far fa-envelope"></i>
        	</div>
        	<p class="mail-ad">inchby112@gmail.com</p>
            <p class="blog-copy">
            	Copyright ⓒ 2021. Jeong Bo-Kwang. All rights reserved
            </p> 
        </div>
</div>
<%@ include file="../common/foot.jspf"%>