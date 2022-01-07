<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원정보수정" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {
		if (MemberModify__submitDone) {
			return;
		}

	form.loginPwInput.value = form.loginPwInput.value.trim();

	  if(form.loginPwInput.value.length == 0) {
        alert('새 비밀번호를 입력해주세요.');
        form.loginPwInput.focus();
        return;
	      }

		if (form.loginPwInput.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호확인을 입력해주세요.')
				form.loginPwConfirm.focus();

				return;
			}

			if (form.loginPwInput.value != form.loginPwConfirm.value) {
				alert('비밀번호확인이 일치하지 않습니다.');
				form.loginPwConfirm.focus();

				return;
			}
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.')
			form.name.focus();

			return;
		}

		form.nickname.value = form.nickname.value.trim();

		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.')
			form.nickname.focus();

			return;
		}
		
		const deleteProfileImgFileInput = form["deleteFile__member__0__extra__profileImg__1"];
	    if ( deleteProfileImgFileInput.checked ) {
	        form["file__member__0__extra__profileImg__1"].value = '';
	    }
	    const maxSizeMb = 10;
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
			alert('휴대전화번호를 입력해주세요.')
			form.cellphoneNo.focus();

			return;
		}

	  form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.')
			form.email.focus();

			return;
		}
		form.loginPw.value = sha256(form.loginPwInput.value);
	    form.loginPwInput.value = '';
	    form.loginPwConfirm.value = '';

		MemberModify__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" enctype="multipart/form-data" method="POST" action="../member/doModify" onsubmit="MemberModify__submit(this); return false;">
    <input type="hidden" name="memberModifyAuthKey" value="${param.memberModifyAuthKey}"/>
    <input type="hidden" value="loginPw"/>
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>
        <tbody>
          <tr>
            <th>로그인아이디</th>
            <td>${rq.loginedMember.loginId}</td>
          </tr>
          <tr>
            <th>새 로그인비밀번호</th>
            <td><input class="input input-bordered" name="loginPwInput" placeholder="새 비밀번호를 입력해주세요." type="password" /></td>
          </tr>
          <tr>
            <th>새 로그인비밀번호 확인</th>
            <td><input class="input input-bordered" name="loginPwConfirm" placeholder="새 비밀번호를 입력해주세요." type="password" /></td>
          </tr>
          <tr>
            <th>이름</th>
            <td><input class="input input-bordered" name="name" placeholder="이름을 입력해주세요." type="text" value="${rq.loginedMember.name}" /></td>
          </tr>
          <tr>
            <th>별명</th>
            <td><input class="input input-bordered" name="nickname" placeholder="별명을 입력해주세요." type="text" value="${rq.loginedMember.nickname}" />
            </td>
          </tr>
          <tr>
          	<th>프로필 이미지</th>
          	<td>
          		<img class="w-40 h-40 mb-2 object-cover" onerror="${rq.loginedMember.removeProfileImgIfNotExistsOnErrorHtmlAttr}" src="${rq.loginedMember.profileImgUri}" alt="">
          		<div>
                    <label class="cursor-pointer label inline-flex">
                        <span class="label-text mr-2">이미지 삭제</span>
                        <div>
                            <input type="checkbox" name="deleteFile__member__0__extra__profileImg__1" class="checkbox" value="Y">
                            <span class="checkbox-mark"></span>
                        </div>
                    </label>
                </div>
                <input accept="image/gif, image/jpeg, image/png" type="file" name="file__member__0__extra__profileImg__1" placeholder="프로필 이미지를 선택해주세요." />
          	</td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>
              <input class="input input-bordered" name="cellphoneNo" placeholder="전화번호를 입력해주세요." type="tel" value="${rq.loginedMember.cellphoneNo}"/>
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input class="input input-bordered" name="email" placeholder="이메일을 입력해주세요." type="email" value="${rq.loginedMember.email}"/>
            </td>
          </tr>
          <tr>
            <th>회원정보수정</th>
            <td>
              <button type="submit" class="btn btn-primary">회원정보수정</button>
              <button type="button" class="btn btn-outline btn-secondary" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>