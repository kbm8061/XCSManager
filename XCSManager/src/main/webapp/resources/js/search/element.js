let json = {};
let totalData = 0;
let dataPerPage = 0;
let currentPage = 0;
let initCheck = false;

$(function() {
	$("#selectSearch").on("change", function() {
		if ('workSearch' == $('#selectSearch option:selected').val()){
			$("#workValueSearch").css('display', 'block');
			$("#keyValueSearch").css('display', 'none');
						
			//Init
			$("#searchInput").val('');
		}
		else if ('keySearch' == $('#selectSearch option:selected').val()){
			$("#workValueSearch").css('display', 'none');
			$("#keyValueSearch").css('display', 'block');

			//Init
			const targetClass = $('#selectClass');
			selectClassCodeInit(targetClass, "COM");
			
			const targetStatus = $('#selectStatus');
			selectStatusCodeInit(targetStatus, "ALL");
			
			$("#selectDate option:eq(0)").prop("selected", true);
			
			$('#startDay').val(DateFormat(new DateUtil().calcDay(-7).toString().slice(0, 8)).replaceAll('.', '-'));
			$('#endDay').val(DateFormat(new DateUtil().calcDay(0).toString().slice(0, 8)).replaceAll('.', '-'));
		}
	});

	$("#selectClass").on("change", function() {
	    const target = $('#selectStatus');
	    const code = $('#selectClass option:selected').val();
	    
		selectStatusCodeInit(target, code);
	});
	
	const targetClass = $('#selectClass');
	selectClassCodeInit(targetClass, "COM");
	
	const targetStatus = $('#selectStatus');
	selectStatusCodeInit(targetStatus, "ALL");

	json = {
		startNo : 1,
		endNo : 10,
		startDay : $('#startDay').val().replace(/-/g, ''),
		endDay : $('#endDay').val().replace(/-/g, '')
	};

	if (Number(json.startDay) > Number(json.endDay)){
		$('#standByTable tbody').html('');
		alert("일자 조회조건을 확인해주세요.");
		return false;
	}
	
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
		if (classification == 'ALL')
			json.classification = '';
		else
			json.classification = classification;
		
		let selectStatus = $('#selectStatus option:selected').val();
		if (selectStatus != 'ALL')
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
			json.owner = $('#ownerInput').val();
		
		Search.list(json);
	});
	
	$('#keySearch').on('click', function() {
		if ($("#searchInput").val() == ''){
			alert("키값을 입력해주세요.");
			return;
		}
		currentPage = 1;
		json = {
			startNo : 1,
			endNo : 10,
			startDay : $('#startDay').val().replace(/-/g, ''),
			endDay : $('#endDay').val().replace(/-/g, ''),
			selectCount: 0
		};
	
		let classification = $('#selectClass option:selected').val();
		if (classification == 'ALL')
			json.classification = '';
		else
			json.classification = classification;
		
		let selectStatus = $('#selectStatus option:selected').val();
		if (selectStatus != 'ALL')
			json.status = selectStatus.substring(2, 4);
	
		let selectDate = $('#selectDate').val();
		if(selectDate == "processDate")
			json.processDate = "processDate";
		else if(selectDate == "regDate")
			json.regDate = "regDate";
		
		let selectId = $('#selectId').val();
		if (selectId == "indexKey")
			json.indexKey = $('#searchInput').val();
		else if(selectId == "elementId")
			json.elementId = $('#searchInput').val();
		else if (selectId == "owner")
			json.owner = $('#searchInput').val();
			
		Search.list(json);
	});
	
	$('#downloadExcel').on('click', function() {
		json = {
			startDay : $('#startDay').val().replace(/-/g, ''),
			endDay : $('#endDay').val().replace(/-/g, '')
		};
	
		let classification = $('#selectClass option:selected').val();
		if (classification == 'ALL')
			json.classification = '';
		else
			json.classification = classification;
		
		let selectStatus = $('#selectStatus option:selected').val();
		if (selectStatus != 'ALL')
			json.status = selectStatus.substring(2, 4);
	
		let selectDate = $('#selectDate').val();
		if(selectDate == "processDate")
			json.processDate = "processDate";
		else if(selectDate == "regDate")
			json.regDate = "regDate";
		
		let selectId = $('#selectId').val();
		if (selectId == "indexKey")
			json.indexKey = $('#searchInput').val();
		else if(selectId == "elementId")
			json.elementId = $('#searchInput').val();
			
		json.selectCount = 0;
		
		downloadExcel();
	})

	$(document).on("keyup", "#searchInput", function(e) {
		if (e.keyCode=='13') { $('#search').trigger('click'); }
	});
	
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

let selectClassCodeInit = function(target, code) {
	let selectJson = {};
	selectJson.codeGroup = code;
	
	let success = function(result) {
		target.html('');
		target.append('<option value = "ALL">전체</option>');
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

let selectStatusCodeInit = function(target, code) {
	let selectJson = {};
	selectJson.codeGroup = code;
	
	if (code == 'ALL') {
		target.html('');
		target.append('<option value="ALL">전체</option>');
	} else {
		let success = function(result) {
			target.html('');
			target.append('<option value="ALL">전체</option>');
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
};

let downloadExcel = function() {
	let date = new DateUtil();
	AjaxUtil.xhr(ctx + "/getElementListByExcel", "POST", json, "XCSM_" + new DateUtil(date.getDate()) + ".xlsx");
};

let Search = {
	list : function(json) {
		let success = function(result) {
			$('#elementTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != ""){
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.elementList.length; i++){
					let html = "";
					
					html += '<tr>';
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