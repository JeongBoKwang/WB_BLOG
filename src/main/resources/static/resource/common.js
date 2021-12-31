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