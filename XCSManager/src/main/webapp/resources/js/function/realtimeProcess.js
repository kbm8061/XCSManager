let json = {};				// 검색을 위한 json 데이터
let totalData = 0;			// 검색 결과 데이터 개수
let dataPerPage = 0;		// 페이지 당 데이터 개수
let currentPage = 0;		// 현재 페이지
let initCheck = false;		// 초기화 구분 값

$(function() {
	$("#allCheck").click(function(){ 
		if($("#allCheck").prop("checked")) { 
			$("input[type=checkbox]").prop("checked",true); 
		} 
		else 
		{ 
			$("input[type=checkbox]").prop("checked",false); 
		} 
	});
	
	$("#selectClass").on("change", function() {
	    const target = $('#selectStatus');
	    const code = $('#selectClass option:selected').val();
	    
		selectStatusCodeInit(target, code);
	});

	// Select Box 초기화 이벤트
	const targetClass = $('#selectClass');
	selectClassCodeInit(targetClass, "COM");
	
	const targetStatus = $('#selectStatus');
	selectStatusCodeInit(targetStatus, targetClass.val());

	// 초기화 이벤트
	json = {
		startNo : 1,
		endNo : 10,
		//startDay : $('#startDay').val().replace(/-/g, ''),
		//endDay : $('#endDay').val().replace(/-/g, '')
	};

	if (Number(json.startDay) > Number(json.endDay)){
		$('#standByTable tbody').html('');
		alert("일자 조회조건을 확인해주세요.");
		return false;
	}
	
	// 검색 버튼 클릭 시 이벤트
	$('#search').on('click', function() {
		currentPage = 1;
		json = {
			startNo : 1,
			endNo : 10,
			startDay : $('#startDay').val().replace(/-/g, ''),
			endDay : $('#endDay').val().replace(/-/g, ''),
			selectCount: 0
		};

		let classification = $('#selectClass option:selected').val();
		json.classification = classification;
		
		let selectStatus = $('#selectStatus option:selected').val();
		json.status = selectStatus.substring(2, 4);

		let selectDate = $('#selectDate').val();
		if (selectDate == "processDate")
			json.processDate = "processDate";
		else if (selectDate == "regDate")
			json.regDate = "regDate";
		
		let selectId = $('#selectId').val();
		if (selectId == "indexKey")
			json.indexKey = $('#searchInput').val();
		else if (selectId == "elementId")
			json.elementId = $('#searchInput').val();
		else if (selectId == "owner")
			json.owner = $('#searchInput').val();

		Search.list(json);
	});
	
	$('#downloadFile').on('click', function() {
		let checkbox = $("input[name=cbElement]:checked");
		
		if (checkbox.length == 0) {
			alert("선택 된 대상이 없습니다.");
			return;
		}
		
		let jsonArray = {};
		let elementId = new Array();
		let filePath = new Array();
		
		let selectStatus = $('#selectStatus option:selected').val().substring(2, 4);
		
		if (selectStatus == '00') {
			checkbox.each(function(i) {
				let tr = checkbox.parent().parent().eq(i);
				let td = tr.children();
				
				elementId.push(td.eq(2).text());
				filePath.push(td.eq(4).text());
			})
			
			jsonArray.elementId = elementId;

			downloadEngine(jsonArray);
		} else if (selectStatus == '01') {
			checkbox.each(function(i) {
				let tr = checkbox.parent().parent().eq(i);
				let td = tr.children();
				
				elementId.push(td.eq(2).text());
				filePath.push(td.eq(4).text());
			})
			
			jsonArray.elementId = elementId;
			jsonArray.filePath = filePath;

			downloadDirect(jsonArray);
		}
	})
	
	$('#setRealtime').on('click', function() {
		let checkbox = $("input[name=cbElement]:checked");
		
		if (checkbox.length == 0) {
			alert("선택 된 대상이 없습니다.");
			return;
		}
		
		let jsonArray = {};
		let elementId = new Array();
		
		checkbox.each(function(i) {
			let tr = checkbox.parent().parent().eq(i);
			let td = tr.children();
			
			elementId.push(td.eq(2).text());
		})
		
		jsonArray.elementId = elementId;
		
		setRealtime(jsonArray);
	})
	
	$('#setStandBy').on('click', function() {
		let checkbox = $("input[name=cbElement]:checked");
		
		if (checkbox.length == 0) {
			alert("선택 된 대상이 없습니다.");
			return;
		}
		
		let jsonArray = {};
		let elementId = new Array();
		
		checkbox.each(function(i) {
			let tr = checkbox.parent().parent().eq(i);
			let td = tr.children();
			
			elementId.push(td.eq(2).text());
		})
		
		jsonArray.elementId = elementId;
		
		setStandBy(jsonArray);
	})
	
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
			currentPage = Math.floor((currentPage / 10) + 1) * 10 + 1;
		} else if (pageItem.hasClass('previous')) {
			currentPage = Math.floor(((currentPage - 10) + 1) / 10) * 10 + 1;
		} else if (pageItem.hasClass('last')) {
			currentPage = Math.ceil(totalData/dataPerPage);
		} else if (pageItem.hasClass('first')) {
			currentPage = 1;
		} else {
			currentPage = pageItem.text();
		}

		Paging(totalData, dataPerPage, currentPage);
		
		json.startNo = 10 * (parseInt(currentPage) - 1) + 1;
		json.endNo = 10 * parseInt(currentPage);
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
		
		json.startNo = 10 * (parseInt(currentPage) - 1) + 1;
		json.endNo = 10 * parseInt(currentPage);
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

// Status Select Box 초기화 이벤트
let selectStatusCodeInit = function(target, code) {
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
};

let downloadEngine = function(jsonArray) {
	let date = new DateUtil();
	AjaxUtil.xhr(ctx + "/downloadEngine", "POST", jsonArray, "XCSM_" + new DateUtil(date.getDate()) + ".zip");
};

let downloadDirect = function(jsonArray) {
	let date = new DateUtil();
	AjaxUtil.xhr(ctx + "/downloadDirect", "POST", jsonArray, "XCSM_" + new DateUtil(date.getDate()) + ".zip");
}

let setRealtime = function(jsonArray) {
	let success = function(result) {
		if (result.msg != null && result.msg != "undefined" && result.msg != ""){
			Paging(0, 10, 0);
			
			alert(result.msg);
			return false;
		}
	};

	let error = function(result) {
		alert(result.responseText);
	};

	AjaxUtil.ajax(ctx + "/setRealtime", "POST", jsonArray, success, error);
};

let setStandBy = function(jsonArray) {
	let success = function(result) {
		if (result.msg != null && result.msg != "undefined" && result.msg != ""){
			Paging(0, 10, 0);
			
			alert(result.msg);
			return false;
		}
		
		$('#search').trigger("click");
	};

	let error = function(result) {
		alert(result.responseText);
	};

	AjaxUtil.ajax(ctx + "/setStandBy", "POST", jsonArray, success, error);
}

let Search = {
	list : function(json) {
		let success = function(result) {
			$('#elementTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != "") {
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.elementList.length; i++){
					let html = "";
					
					html += '<tr>';
					html += '<td nowrap style="text-align:center; vertical-align:middle;"><input type="checkbox" name="cbElement"></td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].elementId) + '</td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].indexKey) + '</td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].owner) + '</td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].filePath) + '</td>';
					html += '<td nowrap>' + blankToNull(parseClassification(result.elementList[i].classification)) + '</td>';
					html += '<td nowrap>' + blankToNull(parseStatus(result.elementList[i].status)) + '</td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].regDate) + '</td>';
					html += '<td nowrap>' + blankToNull(result.elementList[i].processDate) + '</td>';
					
					$('#elementTable tbody').append(html);
				}
				
				if (result.elementCount != null && result.elementCount != "undefined" && result.elementCount != "") {
					totalData = result.elementCount;
					$('#selectCount').text(result.elementCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
				}
				
            	dataPerPage = 10;
            	
            	Paging(totalData, dataPerPage, currentPage);
            	
            	let status = $('#selectStatus option:selected').val().substring(2, 4);
            	
            	selectClass();
            	
            	if (status == "00") {
					$("#downloadFile").css('display', 'block');
					$("#setRealtime").css('display', 'block');
					$("#setStandBy").css('display', 'none');
				}
				
				if (status == "01") {
					$("#downloadFile").css('display', 'block');
					$("#setRealtime").css('display', 'none');
					$("#setStandBy").css('display', 'none');
				}
				
				if (status == "09") {
					$("#downloadFile").css('display', 'none');
					$("#setRealtime").css('display', 'none');
					$("#setStandBy").css('display', 'block');
				}
			}

		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajaxAsync(ctx + "/getElementList", "POST", json, success, error);
	}
};

// Paging 처리 이벤트
let Paging = function(totalData, dataPerPage, currentPage) {
	let totalPage = Math.ceil(totalData/dataPerPage);	// 총 페이지 수
	let pageGroup = Math.ceil(currentPage/10);  		// 페이지 그룹
	
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