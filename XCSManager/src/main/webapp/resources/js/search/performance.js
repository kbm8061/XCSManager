let json = {};				// 검색을 위한 json 데이터
let totalData = 0;			// 검색 결과 데이터 개수
let dataPerPage = 0;		// 페이지 당 데이터 개수
let currentPage = 0;		// 현재 페이지
let initCheck = false;		// 초기화 구분 값

$(function() {
	$("#selectTimeDiv").on("change", function() {
		let date = $("option:selected", this).attr("value");

	    if (date == 'MIN'){
	    	$("#selectTimeTitle").css("display", "block");
	    	$("#selectTime").css("display", "block");
	    	selectTimeInit();
	    }
	    
	    if (date == 'HOUR'){
	    	$("#selectTimeTitle").css("display", "none");
	    	$("#selectTime").css("display", "none");
	    }
	});
	
	// Select Box 초기화 이벤트
	const targetClass = $('#selectClass');
	selectClassCodeInit(targetClass, "COM");
	selectTimeInit();

	// 초기화 이벤트
	json = {
		startNo : 1,
		endNo : 10,
	};
	
	// 검색 버튼 클릭 시 이벤트
	$('#search').on('click', function() {
		currentPage = 1;
		json = {
			startNo : 1,
			endNo : 5,
			selectCount: 0
		};
		
		let timeDiv = $('#selectTimeDiv option:selected').val();
		json.timeDiv = timeDiv;
		
		let selectDay =  $('#selectDay').val().replace(/-/g, '');
		
		if (timeDiv == 'MIN') {
			let selectTime = $('#selectTime option:selected').val();
			json.startDay = selectDay + selectTime + '0000';
			json.endDay = selectDay + selectTime + '5959';
		}
		
		if (timeDiv == 'HOUR') {
			json.startDay = selectDay + '000000';
			json.endDay = selectDay + '235959';
		}

		let classification = $('#selectClass option:selected').val();
		json.classification = classification;
		
		selectClass();
		
		Search.list(json);
		Search.chart(json);
	});
	
	// Enter 입력 시 검색 버튼 클릭 이벤트
	$(document).on("keyup", "#searchInput", function(e) {
		if (e.keyCode=='13') { $('#search').trigger('click'); }
	});
	
	// Paging 클릭 이벤트
	$(document).on("click", ".page-item", function() {
		let pageItem = $(this);
		
		if (pageItem.hasClass('active') || pageItem.hasClass('disabled'))
			 return false;
		
		if (pageItem.hasClass('next')) {
			currentPage = Math.floor((currentPage / 5) + 1) * 5 + 1;
		} else if (pageItem.hasClass('previous')) {
			currentPage = Math.floor(((currentPage - 5) + 1) / 5) * 5 + 1;
		} else if (pageItem.hasClass('last')) {
			currentPage = Math.ceil(totalData/dataPerPage);
		} else if (pageItem.hasClass('first')) {
			currentPage = 1;
		} else {
			currentPage = pageItem.text();
		}

		Paging(totalData, dataPerPage, currentPage);
		
		json.startNo = 5 * (parseInt(currentPage) - 1) + 1;
		json.endNo = 5 * parseInt(currentPage);
		json.selectCount = parseInt($('#selectCount').text().replaceAll(',', ''));
		Search.list(json);
	});
	
	$(document).on("click", "#goPage", function() {
		if (totalData == 0) {
			alert("검색 결과가 없습니다.");
			return;
		}
		
		let goPageNum = $("#goPageNum").val();

		if (goPageNum > (totalData/dataPerPage) + 1) {
			alert("최대 페이지 수 보다 작은 값을 입력해 주십시오.");
			return;
		}
		
		if (goPageNum == 0) {
			alert("0보다 큰 값을 입력하여주십시오.");
			return;
		}
		
		currentPage = goPageNum;
		
		Paging(totalData, dataPerPage, currentPage);
		
		json.startNo = 5 * (parseInt(currentPage) - 1) + 1;
		json.endNo = 5 * parseInt(currentPage);
		json.selectCount = parseInt($('#selectCount').text().replaceAll(',', ''));
		Search.list(json);
	});
});

const selectClass = function() {
	let selectClass = $("#selectClass option:selected").attr("value");
		
	$(".selectClassName").text($('#selectClass option:selected').text());
}

// Class Select Box 초기화 이벤트
let selectClassCodeInit = function(target, code) {
	let selectJson = {};
	selectJson.codeGroup = code;
	
	let success = function(result) {
		target.html('');
		result.forEach(function(resultCol) {
			target.append('<option value="' + resultCol.code + '">' + resultCol.codeValue + '</option>');
		});
	};
	
	let error = function(result) {
		target.attr('disable', 'disable');
		target.append(result.responseText);
	};
	
	AjaxUtil.ajax(ctx + "/getCodeList", "POST", selectJson, success, error);
}

let selectTimeInit = function() {
	for (i = 0; i <= 23; i++) {
		
		if (i == 9) {
			$("#selectTime").append('<option value="0' + i + '">0' + i + '시 ~ ' + (i + 1) + '시</option>');
			$("#startTime").append('<option value="0' + i + '">0' + i + '시 ~ ' + (i + 1) + '시</option>');
			$("#endTime").append('<option value="0' + i + '">0' + i + '시 ~ ' + (i + 1) + '시</option>');
		} else if (i.toString().length == 1) {
			$("#selectTime").append('<option value="0' + i + '">0' + i + '시 ~ 0' + (i + 1) + '시</option>');
			$("#startTime").append('<option value="0' + i + '">0' + i + '시 ~ 0' + (i + 1) + '시</option>');
			$("#endTime").append('<option value="0' + i + '">0' + i + '시 ~ 0' + (i + 1) + '시</option>');
		} else {
			$("#selectTime").append('<option value="' + i + '">' + i + '시 ~ ' + (i + 1) + '시</option>');
			$("#startTime").append('<option value="' + i + '">' + i + '시 ~ ' + (i + 1) + '시</option>');
			$("#endTime").append('<option value="' + i + '">' + i + '시 ~ ' + (i + 1) + '시</option>');
		}
	}
}

let Search = {
	list : function(json) {
		let success = function(result) {
			let timeDiv = $('#selectTimeDiv option:selected').val();

			setTable(result);
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajaxAsync(ctx + "/getPerformanceListPaging", "POST", json, success, error);
	},
	chart : function(json) {
		let success = function(result) {
			let timeDiv = $('#selectTimeDiv option:selected').val();
			let formattedArray = [];
			
			if (timeDiv == 'MIN') {
				let flag = false;
				for (let i = 0; i < 60; i++) {
					for (let j = 0; j < result.performanceList.length; j++) {					
						if (i.toString().length == 1) {
							i = '0' + i;
						}
						
						if (result.performanceList[j].processDate.substr(10, 2) == i) {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + $('#selectTime option:selected').val() + i, "count" : result.performanceList[j].count});
							flag = true;
						}
					}
					
					if (flag == false) {
						if (i.toString().length == 1) {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + $('#selectTime option:selected').val() + '0' + i, "count" : 0});
						} else {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + $('#selectTime option:selected').val() + i, "count" : 0});
						}
					}
					
					flag = false;
				}
			}
			
			if (timeDiv == 'HOUR') {
				let flag = false;
				for (let i = 0; i < 24; i++) {
					for (let j = 0; j < result.performanceList.length; j++) {
						if (i.toString().length == 1) {
							i = '0' + i;
						}
						
						if (result.performanceList[j].processDate.substr(8, 2) == i) {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + result.performanceList[j].processDate.substr(8, 2) + '0000', "count" : result.performanceList[j].count});
							flag = true;
						}
					}
					
					if (flag == false) {
						if (i.toString().length == 1) {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + '0' + i + '0000', "count" : 0});
						} else {
							formattedArray.push({"processDate" : $('#selectDay').val().replace(/-/g, '') + i + '0000', "count" : 0});
						}
					}
					
					flag = false;
				}
			}
			
			result.performanceList = formattedArray;
			
			setChart(result);
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		AjaxUtil.ajaxAsync(ctx + "/getPerformanceList", "POST", json, success, error);
	}
};

// 테이블 생성 이벤트
let setTable = function(result) {
	$('#dataTable tbody').html('');
	
	if (result.msg != null && result.msg != "undefined" && result.msg != "") {
		Paging(0, 5, 0);
		
		alert(result.msg);
		return false;
	}
	else {
		for (let i = 0; i < result.performanceList.length; i++) {
			let html = "";
			
			html += '<tr><td nowrap>' + blankToNull(result.performanceList[i].processDate) + '</td>';
			html += '<td nowrap>' + blankToNull(result.performanceList[i].classification) + '</td>';
			html += '<td nowrap>' + blankToNull(result.performanceList[i].count.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")) + '</td>';
			
			$('#dataTable tbody').append(html);
		}
		
		if (result.performanceCount != null && result.performanceCount != "undefined" && result.performanceCount != ""){
			totalData = result.performanceCount;
			$('#selectCount').text(result.performanceCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
		}
		
    	dataPerPage = 5;
    	
    	Paging(totalData, dataPerPage, currentPage);
	}
}

// 차트 생성 이벤트
let setChart = function(result) {
	let target = $('#dtChartLine');
	let parent = $('#dtChartLine').parent();
	
	target.remove();
	parent.append('<canvas id="dtChartLine"></canvas>');
	
	let chartLabel = [];
	let chartData = [];
	let chartDataItem = {data : []};
	let maxCount = 0;
	let timeDiv = $('#selectTimeDiv option:selected').val();
	
	chartDataItem.borderColor = "#2e59d9";
	chartDataItem.backgroundColor = "#4e73df";
	chartDataItem.fill = "true";
	chartDataItem.lineTension = 0;
	
	for (let i = 0; i < result.performanceList.length; i++) {
		let processDateItem = result.performanceList[i].processDate;
		let formattedDateItem = '';
		
		if (result.performanceList[i].count > maxCount) {
			maxCount =  result.performanceList[i].count;
		}
		
		if (timeDiv == 'MIN') {
			formattedDateItem = processDateItem.substr(10, 2) + '분';
		}
		
		if (timeDiv == 'HOUR') {
			formattedDateItem = processDateItem.substr(8, 2) + '시';
		}
		
		
		chartLabel.push(formattedDateItem);
		chartDataItem.data[i] = result.performanceList[i].count;
	}
	
	chartData.push(chartDataItem);
	ChartUtil.line($('#dtChartLine'), chartLabel, chartData, maxCount);
}

// Paging 처리 이벤트
let Paging = function(totalData, dataPerPage, currentPage) {
	let totalPage = Math.ceil(totalData/dataPerPage);	// 총 페이지 수
	let pageGroup = Math.ceil(currentPage/5);  		// 페이지 그룹
	
	let last = pageGroup * 10;    						// 화면에 보여질 마지막 페이지 번호
    if (last > totalPage) { last = totalPage; }
    let first = last - 9;    							// 화면에 보여질 첫번째 페이지 번호
    if (first < 1) { first = 1; }
	if (first % dataPerPage != 1) { first = Math.ceil(first / dataPerPage) * dataPerPage + 1; }
    let next = last + 1;
    let prev = first - 1;

    let html = "";

	if (first == 1)
		html += '<li class="page-item first disabled"><a class="page-link" href="#" style="background: #EAEAEA;">&#60;&#60;</a></li>';
	else
		html += '<li class="page-item first"><a class="page-link" href="#">&#60;&#60;</a></li>';
    
    if (prev > 0)
    	html += '<li class="page-item previous"><a class="page-link" href="#">&#60;</a></li>';
    else
    	html += '<li class="page-item previous disabled"><a class="page-link" href="#" style="background: #EAEAEA;">&#60;</a></li>';
    	
	for (let i = first; i <= last; i++) {
		if (currentPage == i)
			html += '<li class="page-item active" id="page- ' + i + '"><a class="page-link" href="#">' + i + '</a></li>';
		else
			html += '<li class="page-item" id="page-' + i + '"><a class="page-link" href="#">' + i + '</a></li>';
	}
	
	if (last < totalPage)
		html += '<li class="page-item next"><a class="page-link" href="#">&#62;</a></li>';
	else
		html += '<li class="page-item next disabled"><a class="page-link" href="#" style="background: #EAEAEA;">&#62;</a></li>';

	if (last == totalPage)
		html += '<li class="page-item last disabled"><a class="page-link" href="#" style="background: #EAEAEA;">&#62;&#62;</a></li>';
	else
		html += '<li class="page-item last"><a class="page-link" href="#">&#62;&#62;</a></li>';
		
	$('.pagination').html(html);
	$("#goPageDirect").css("display", "block");
};