let json = {};				// 검색을 위한 json 데이터
let initCheck = false;		// 초기화 구분 값

$(function() {
	// Select Box 초기화 이벤트
	const target = $('#selectClass');
	const code = "COM";
	selectBoxInit(target, code);

	// 초기화 이벤트
	json = {
		startDay : $('#startDay').val().replace(/-/g, ''),
		endDay : $('#endDay').val().replace(/-/g, ''),
		classification : $('#selectClass option:selected').val()
	};
	// 검색 버튼 클릭 시 이벤트
	$('#search').on('click', function() {
		json = {
			startDay : $('#startDay').val().replace(/-/g, ''),
			endDay : $('#endDay').val().replace(/-/g, ''),
			classification : $('#selectClass option:selected').val()
		};
		
		Search.list(json);
	});
});


let selectClass = function() {
	let selectClass = $("#selectClass option:selected").attr("value");
	$(".selectClassName").text($('#selectClass option:selected').text());
}


// Select Box 초기화 이벤트
let selectBoxInit = function(target, code) {
	let selectJson = {};
	selectJson.codeGroup = code;

	let success = function(result) {
		target.html('');
		result.forEach(function(resultCol) {
			target.append('<option value="' + resultCol.code + '">' + resultCol.codeValue + '</option>');
		});
		
		selectClass();
	};

	let error = function(result) {
		target.attr('disable', 'disable');
		target.append(result.responseText);
	};

	AjaxUtil.ajax(ctx + "/getCodeList", "POST", selectJson, success, error);
};

let numberWithCommas = function(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

	
// 검색 이벤트
let Search = {
	list : function(json) {
		let mask = new loading();
		mask.start();
		setTimeout(function() {
			AjaxUtil.ajaxAsync(ctx + "/getStandByStat", "POST", json,
				function(result) {
					$('#dayStaticTable tbody').html('');
					$('#totalStaticTable tbody').html('');
				
					if (result.msg != null){
						alert(result.msg);
						return false;
					}
					
					if (result.totalStaticList.length > 0){
						for (var j = 0; j < result.totalStaticList.length; j++){
							let html = "";
							
							html += '<tr><td nowrap>' + blankToNull(numberWithCommas(Number(result.totalStaticList[j].targetCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.totalStaticList[j].processCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.totalStaticList[j].successCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.totalStaticList[j].failCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.totalStaticList[j].waitCount))) + '</td></tr>';
							
							$('#totalStaticTable tbody').append(html);
						}
					}
					else{
						
					}
				
					if (result.dayStaticList.length > 0 && result.dayStaticList != null && result.dayStaticList != undefined && result.dayStaticList != ''){
						for (var i = 0; i < result.dayStaticList.length; i++){
							let html = "";
							
							html += '<tr><td nowrap>' + ((result.dayStaticList[i].statDate == undefined)?'':(result.dayStaticList[i].statDate.substring(0, 4) + '-' + result.dayStaticList[i].statDate.substring(4, 6) + '-' + result.dayStaticList[i].statDate.substring(6, 8))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.dayStaticList[i].targetCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.dayStaticList[i].processCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.dayStaticList[i].successCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.dayStaticList[i].failCount))) + '</td>';
							html += '<td nowrap>' + blankToNull(numberWithCommas(Number(result.dayStaticList[i].waitCount))) + '</td></tr>';
							
							$('#dayStaticTable tbody').append(html);
						}
					}
					else{
						
					}
					
					selectClass();
				},
				function(result) {
					alert(result.responseText);
				},
				function() {
					mask.stop();
				}
			);
		}, 0);
	}
};
