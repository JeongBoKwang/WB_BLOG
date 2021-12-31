//최초 1회 data-value패턴 찾고 받아오기
$('select[data-value]').each(function(index, el) {
  const $el = $(el);
  //공백 제거
  const defaultValue = $el.attr('data-value').trim();
  //값이 존재할경우
  if ( defaultValue.length > 0 ) {
	$el.val(defaultValue);	
  }
}); 

//아이디 영어로 입력
function isAlphaNumeric(str) {
	var code, i, len;
	
	for(i = 0, len = str.length; i < len; i++) {
		code = str.charCodeAt(i);
		if(!(code > 47 && code < 58) && //numeric (0~9)
		!(code > 64 && code < 91) && //upper alpha (A~Z)
		!(code > 96 && code < 123))
			return false;
	}
	return true;
}