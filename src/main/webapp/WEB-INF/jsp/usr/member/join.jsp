<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script type="text/javascript">
  let validLoginId = "";
	let submitJoinFormDone = false;
	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다.');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

	  if(form.loginId.value.length <= 4) {
       alert('아이디를 5자 이상 입력해주세요.');
       form.loginId.focus();
       return;
	      }

	  if (isAlphaNumeric(form.loginId.value) == false) {
			alert('아이디를 영어로 입력해주세요.');
			form.loginId.focus();
			return false;
		}
		//아이디 소문자
		form.loginId.value = form.loginId.value.toLowerCase();  

	  if(validLoginId != form.loginId.value) {
       alert('다른 로그인 아이디를 입력해주세요.');
       form.loginId.focus();
       return;
	    }
      
		form.loginPwInput.value = form.loginPwInput.value.trim();
		if (form.loginPwInput.value.length == 0) {
			alert('로그인비밀번호를 입력해주세요.');
			form.loginPwInput.focus();
			return;
		}
    
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인비밀번호 확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}
    
		if (form.loginPwInput.value != form.loginPwConfirm.value) {
			alert('로그인비밀번호 확인이 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;
		}
    
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}
    
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요.');
			form.nickname.focus();
			return;
		}
		
		const maxSize = maxSizeMb * 1024 * 1024;
	    const profileImgFileInput = form["file__member__0__extra__profileImg__1"];
	    if (profileImgFileInput.value) {
	        if (profileImgFileInput.files[0].size > maxSize) {
	            alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
	            profileImgFileInput.focus();
	            return;
	        }
	    }
    
		form.cellphoneNo.value = form.cellphoneNo.value.trim();
		if (form.cellphoneNo.value.length == 0) {
			alert('휴대전화번호를 입력해주세요.');
			form.cellphoneNo.focus();
			return;
		}
    
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

	  form.loginPw.value = sha256(form.loginPwInput.value);
      form.loginPwInput.value = '';
      form.loginPwConfirm.value = '';
    
		submitJoinFormDone = true;
		form.submit();
	}

	function checkLoginIdDup(el) {
	    const form = $(el).closest('form').get(0);
	    form.loginId.value = form.loginId.value.trim();
	    if ( form.loginId.value == validLoginId ) {
	        return;
	    }

	    if(isAlphaNumeric(form.loginId.value)==false) {
          return false;
	    }
      
	    validLoginId = "";
	    $('.login-id-input-success-msg').text('');
	    $('.login-id-input-success-msg').hide();
      
	    $('.login-id-input-error-msg').text('');
	    $('.login-id-input-error-msg').hide();
	    if ( form.loginId.value.length > 4 ) {
	        $.get(
	            "../member/getLoginIdDup",
	            {
	                loginId: form.loginId.value
	            },
	            function(data) {
	                if ( data.success ) {
	                	validLoginId = data.data1;
	                    $('.login-id-input-success-msg').text(data.msg);
	                    $('.login-id-input-success-msg').show();
	                }
	                else {
	                    $('.login-id-input-error-msg').text(data.msg);
	                    $('.login-id-input-error-msg').show();
	                }
	            },
	            "json"
	        );
	    }
	}
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" enctype="multipart/form-data" action="../member/doJoin" onsubmit="submitJoinForm(this); return false;">
      <input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
      <input type="hidden" name="loginPw"/>
      	<div class="form-control w-96 join">
  		<label class="label">
    		<span class="label-text">아이디</span>
  		</label> 
  		<input autocomplete="off" onkeyup="checkLoginIdDup(this);" name="loginId" type="text" placeholder="아이디를 입력해주세요." class="input input-bordered">
  			<div class="mt-2 text-red-500 login-id-input-error-msg"></div>
            <div class="mt-2 text-green-500 login-id-input-success-msg"></div>
  		<label class="label">
    		<span class="label-text">비밀번호</span>
  		</label> 
  		<input name="loginPwInput" type="password" placeholder="비밀번호를 입력해주세요." class="input input-bordered">
  		
  		<label class="label">
    		<span class="label-text">비밀번호 확인</span>
  		</label> 
  		<input name="loginPwConfirm" type="password" placeholder="비밀번호 확인을 입력해주세요." class="input input-bordered">
  		
  		<label class="label">
    		<span class="label-text">이름</span>
  		</label> 
  		<input name="name" type="text" placeholder="이름을 입력해주세요." class="input input-bordered">
  		
  		<label class="label">
    		<span class="label-text">별명</span>
  		</label> 
  		<input name="nickname" type="text" placeholder="별명을 입력해주세요." class="input input-bordered">
  		
  		<label class="label">
    		<span class="label-text">프로필 이미지</span>
  		</label> 
  		<input accept="image/gif, image/jpeg, image/png" name="file__member__0__extra__profileImg__1" type="file" placeholder="프로필 이미지를 선택해주세요." />
  		
  		<label class="label">
    		<span class="label-text">전화번호</span>
  		</label> 
  		<input name="cellphoneNo" type="tel" placeholder="전화번호를 입력해주세요." class="input input-bordered">
  		
  		<label class="label">
    		<span class="label-text">이메일</span>
  		</label> 
  		<input name="email" type="email" placeholder="이메일을 입력해주세요." class="input input-bordered">
  		<div class="submit bottom">
  			<button type="submit" class="btn btn-ghost">회원가입</button>
            <button type="button" class="btn btn-ghost" onclick="history.back();">뒤로가기</button>
  		</div>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>