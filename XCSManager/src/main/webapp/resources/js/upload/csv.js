let json = {};
let totalData = 0;
let dataPerPage = 0;
let currentPage = 0;

$(function() {
	json = {
		startNo : 1,
		endNo : 10
	};
	
	const targetClass = $('#selectClass');
	selectClassCodeInit(targetClass, "COM");
	
	$(document).on("click", ".page-item", function() {
		let pageItem = $(this);
		
		if (pageItem.hasClass('active') || pageItem.hasClass('disabled'))
			 return false;
		
		if (pageItem.hasClass('next'))
			currentPage = Math.floor((currentPage / 10) + 1) * 10 + 1;
		else if (pageItem.hasClass('previous'))
			currentPage = Math.floor(((currentPage - 10) + 1) / 10) * 10 + 1;
		else if (pageItem.hasClass('last'))
			currentPage = Math.ceil(totalData/dataPerPage);
		else if (pageItem.hasClass('first'))
			currentPage = 1;
		else
			currentPage = pageItem.text();

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
		
		json.startNo = 10 * (parseInt(currentPage) - 1) + 1;
		json.endNo = 10 * parseInt(currentPage);
		json.selectCount = parseInt($('#selectCount').text().replaceAll(',', ''));
		Search.list(json);
	});
	
	$('#fileUploadBtn').on('click', function() {
	    var form = $('#frm-upload');
        var formData = new FormData(form[0]);
        
        formData.append("file", $('input[name=file]')[0].files[0]);
        
        var len = $('input[name="file"]')[0].files.length;
        
        if (len < 1){
        	alert("파일을 선택해주세요.");
        	return false;
        }
        
        formData.append("startNo", 1);
        formData.append("endNo", 10);
        formData.append("selectCount", 0);
        formData.append("classification", $('#selectClass option:selected').val());
        
		let mask = new loading();
		
        $.ajax({
            url : ctx + '/uploadCSV',
            type : 'POST',
            data : formData,
            processData : false,
            contentType : false,
            beforeSend : function() {
            	mask.start();
            },
            success : function(result) {
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
						html += '<td nowrap>' + blankToNull(result.elementList[i].elementId) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].indexKey) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].owner) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].filePath) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].classification) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].status) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].regDate) + '</td>';
						html += '<td nowrap>' + blankToNull(result.elementList[i].processDate) + '</td>';
						
						$('#elementTable tbody').append(html);
					}
					
					if (result.elementCount != null && result.elementCount != "undefined" && result.elementCount != "") {
						totalData = result.elementCount;
						$('#selectCount').text(result.elementCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
					}
					
	            	dataPerPage = 10;
	            	currentPage = 1;
	            	
	            	Paging(totalData, dataPerPage, currentPage);
				}
            },
            error : function(result) {
            	alert(result.msg);
            },
            complete : function() {
            	mask.stop();
            }
        });
    });
    
    $("#fileDownloadBtn").on("click", function() {
		let date = new DateUtil();
		AjaxUtil.xhr(ctx + "/downloadCSVSample", "POST", {}, "sample_" + new DateUtil(date.getDate()) + ".csv");
	});
});

let Search = {
	list : function(json) {
		var form = $('#frm-upload');
        var formData = new FormData(form[0]);
        
        formData.append("startNo", json.startNo);
        formData.append("endNo", json.endNo);
        formData.append("selectCount", json.selectCount);
        formData.append("classification", $('#selectClass option:selected').val());
        
        $.ajax({
            url : ctx + '/selectUploadedElement',
            type : 'POST',
            data : formData,
            processData : false,
            contentType : false,
            success : function(result) {
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
            },
            error : function(result) {
            	alert(result.msg);
            },
        });
	}
}; 

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
};

let Paging = function(totalData, dataPerPage, currentPage) {
	let totalPage = Math.ceil(totalData/dataPerPage);
	let pageGroup = Math.ceil(currentPage/10);
	
	let last = pageGroup * 10;
    if (last > totalPage) { last = totalPage; }
    let first = last - 9;
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