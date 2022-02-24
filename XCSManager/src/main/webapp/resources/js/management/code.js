let json = {};				// 검색을 위한 json 데이터
let totalData = 0;			// 검색 결과 데이터 개수
let dataPerPage = 0;		// 페이지 당 데이터 개수
let currentPage = 0;		// 현재 페이지
let initCheck = false;		// 초기화 구분 값
let selectedCode = null;

$(function() {
	currentPage = 1;
	json = {
		startNo : 1,
		endNo : 10,
		selectCount : 0
	};

	Search.list(json);

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
	
	$(document).on("click", "#codeTable tr", function() {
		let tr = $(this);
		let td = tr.children();
		
		selectedCode = td.eq(0).text();
		
		$("#codeInput").prop("disabled", true);
		
		$("#codeGroupInput").val(td.eq(1).text());
		$("#codeInput").val(td.eq(0).text());
		$("#codeValueInput").val(td.eq(2).text());
		$("#descrInput").val(td.eq(3).text());
	});
	
	$(document).on("click", "#btnInsertCode", function() {
		if ($("#codeInput").val() == null || $("#codeInput").val() == '' || $("#codeInput").val() == undefined) {
			alert("코드는 필수값입니다.");
			return;
		}
		
		currentPage = 1;
		json = {
			startNo : 1,
			endNo : 10,
			codeGroup : '',
			code : '',
			codeValue : '',
			descr : '',
			selectCount: 0
		};
		
		json.codeGroup = $("#codeGroupInput").val();
		json.code = $("#codeInput").val();
		json.codeValue = $("#codeValueInput").val();
		json.descr = $("#descrInput").val();
		
		Management.insert(json);
	});
	
	$(document).on("click", "#btnUpdateCode", function() {
		if (selectedCode == null) {
			alert("선택된 코드가 없습니다.");
		} else {
			if (confirm("[" + selectedCode + "] 코드를 수정하시겠습니까?")) {
				currentPage = 1;
				json = {
					startNo : 1,
					endNo : 10,
					code : '',
					codeGroup : '',
					codeValue : '',
					descr : '',
					selectCount: 0
				};
				
				json.codeGroup = $("#codeGroupInput").val();
				json.code = $("#codeInput").val();
				json.codeValue = $("#codeValueInput").val();
				json.descr = $("#descrInput").val();
				
				Management.update(json);
			}
		}
	});
	
	$(document).on("click", "#btnDeleteCode", function() {
		if (selectedCode == null) {
			alert("선택된 코드가 없습니다.");
		} else {
			if (confirm("[" + selectedCode + "] 코드를 삭제하시겠습니까?")) {
				currentPage = 1;
				json = {
					startNo : 1,
					endNo : 10,
					code : '',
					selectCount: 0
				};
				
				json.code = $("#codeInput").val();
				
				Management.delete(json);
			}
		}
	});
	
	$(document).on("click", "#btnRefresh", function() {
		if ($("#codeInput").prop("disabled") == true) {
			$("#codeInput").prop("disabled", false);
		}
		
		selectedCode = null;
		
		$("#codeGroupInput").val('');
		$("#codeInput").val('');
		$("#codeValueInput").val('');
		$("#descrInput").val('');
	})
});

let Search = {
	list : function(json) {
		let success = function(result) {
			$('#codeTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != ""){
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.codeList.length; i++){
					let html = "";
					
					html += '<tr>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].code) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].codeGroup) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].codeValue) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].descr) + '</td>';
					
					$('#codeTable tbody').append(html);
				}
				
				if (result.codeCount != null && result.codeCount != "undefined" && result.codeCount != ""){
					totalData = result.codeCount;
					$('#selectCount').text(result.codeCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
				}
				
            	dataPerPage = 10;
            	
            	Paging(totalData, dataPerPage, currentPage);
			}
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajax(ctx + "/getManagementCodeList", "POST", json, success, error);
	}
}; 

let Management = {
	insert : function(json) {
		let success = function(result) {
			$('#codeTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != ""){
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.codeList.length; i++){
					let html = "";
					
					html += '<tr><td nowrap>' + blankToNull(result.codeList[i].codeGroup) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].code) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].codeValue) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].descr) + '</td>';
					
					$('#codeTable tbody').append(html);
				}
				
				if (result.codeCount != null && result.codeCount != "undefined" && result.codeCount != ""){
					totalData = result.codeCount;
					$('#selectCount').text(result.codeCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
				}
				
            	dataPerPage = 10;
            	
            	Paging(totalData, dataPerPage, currentPage);
			}
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajax(ctx + "/insertCode", "POST", json, success, error);
	},
	
	update : function(json) {
		let success = function(result) {
			$('#codeTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != ""){
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.codeList.length; i++){
					let html = "";
					
					html += '<tr><td nowrap>' + blankToNull(result.codeList[i].codeGroup) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].code) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].codeValue) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].descr) + '</td>';
					
					$('#codeTable tbody').append(html);
				}
				
				if (result.codeCount != null && result.codeCount != "undefined" && result.codeCount != ""){
					totalData = result.codeCount;
					$('#selectCount').text(result.codeCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
				}
				
            	dataPerPage = 10;
            	
            	Paging(totalData, dataPerPage, currentPage);
			}
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajax(ctx + "/updateCode", "POST", json, success, error);
	},
	
	delete : function(json) {
		let success = function(result) {
			$('#codeTable tbody').html('');
			if (result.msg != null && result.msg != "undefined" && result.msg != ""){
				Paging(0, 10, 0);
				
				alert(result.msg);
				return false;
			}
			else {
				for (let i = 0; i < result.codeList.length; i++){
					let html = "";
					
					html += '<tr><td nowrap>' + blankToNull(result.codeList[i].codeGroup) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].code) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].codeValue) + '</td>';
					html += '<td nowrap>' + blankToNull(result.codeList[i].descr) + '</td>';
					
					$('#codeTable tbody').append(html);
				}
				
				if (result.codeCount != null && result.codeCount != "undefined" && result.codeCount != ""){
					totalData = result.codeCount;
					$('#selectCount').text(result.codeCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
				}
				
            	dataPerPage = 10;
            	
            	Paging(totalData, dataPerPage, currentPage);
			}
		};
		
		let error = function(result) {
			alert(result.responseText);
		};
		
		AjaxUtil.ajax(ctx + "/deleteCode", "POST", json, success, error);
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
};