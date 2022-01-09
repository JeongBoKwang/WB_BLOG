<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원정보" />
<%@ include file="../common/head.jspf"%>  
<%@ page import="com.example.spring.demo.util.Ut" %>  
<section class="mt-5">
  <div class="container mx-auto px-3">
    <div class="table-box-type-2">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>
        <tbody>
          <tr>
          	<td class="profile-img">
          		<img class="w-40 h-40 object-cover " onerror="${rq.loginedMember.profileFallbackImgOnErrorHtmlAttr}" src="${rq.loginedMember.profileImgUri}" alt="">
          		<span>프로필 이미지</span>
          	</td>
          </tr>
          <tr>
            <th>로그인아이디  |</th>
            <td>
              ${rq.loginedMember.loginId}
            </td>
          </tr>
          <tr>
            <th>이름    |</th>
            <td>${rq.loginedMember.name}</td>
          </tr>
          <tr>
            <th>별명    |</th>
            <td>${rq.loginedMember.nickname}</td>
          </tr>
          <tr>
            <th>전화번호    |</th>
            <td>${rq.loginedMember.cellphoneNo}</td>
          </tr>
          <tr>
            <th>이메일    |</th>
            <td>${rq.loginedMember.email}</td>
          </tr>
        </tbody>
      </table>
      <div class="bottom-option">
      	 <a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modify')}" class="btn btn-ghost">회원정보수정</a>
         <a onclick="if(confirm('회원 탈퇴를 하시겠습니까?')==false) return false;" href="../member/doDeleteMember?id=${rq.loginedMember.id}" class="btn btn-ghost">회원 탈퇴</a>
         <button type="button" class="btn btn-ghost" onclick="history.back();">뒤로가기</button>
      </div>
    </div>
  </div>
</section>