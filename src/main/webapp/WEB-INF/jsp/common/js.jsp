<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
  var msg = '${msg}'.trim();
  if (msg) {
    alert(msg);
  }
  var historyBack = '${historyBack}' == 'true';
  if (historyBack) {
    history.back();
  }
  var replaceUri = '${replaceUri}'.trim();
  if (replaceUri) {
    location.replace(replaceUri);
  }
</script>