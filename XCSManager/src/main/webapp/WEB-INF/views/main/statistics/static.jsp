<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<script src="${ctx}/resources/js/statistics/static.js"></script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/i18n/jquery-ui-i18n.min.js"></script>

<script>
$.datepicker.setDefaults($.datepicker.regional['ko']); //한국어 설정
$(function() {
	var ieName = navigator.appName; 
	var agent = navigator.userAgent.toLowerCase(); 
	
	/*** 1. IE 버전 체크 ***/ 
	// IE old version ( IE 10 or Lower ) 
	if ( ieName == "Microsoft Internet Explorer" )
	{ 
	}else
	{ 
		// IE 11 
		if( agent.search("trident") > -1 )
		{ 
		    $('#startDay').datepicker({  
		        dateFormat:"yy-mm-dd",   
		        onSelect:function(d){    
		        }
		    });
		    
		    $('#endDay').datepicker({    
		        dateFormat:"yy-mm-dd",   
		        onSelect:function(d){    
		        }
		    });
		} 
		// IE 12 ( Microsoft Edge ) 
		else if( agent.search("edge/") > -1 )
		{
		}
		else{
		}
	}
});
</script>

<style>

th,td {
	vertical-align: middle;
  	text-align : center;
}
</style>
<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
		<h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">통계 - 처리일자별 진척조회</h4>
	</div>
	<div class="card mb-2 table-card">
		<div class="card-body" style="min-width:500px;">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">조회 구분</h4>
						<select class="custom-select mr-1 text-normal" id="selectClass">
						</select>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">시작 일자</h4>
						<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date().getTime()-(60*60*24*1000*7)) %>"></c:set>
						<input type="date" class="inp-style form-control text-center text-normal" name="endDay" id="startDay" value="${now}"/>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">종료 일자</h4>
						<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date().getTime()-(60*60*24*1000*1)) %>"></c:set>
						<input type="date" class="inp-style form-control text-center text-normal" name="endDay" id="endDay" value="${now}"/>
					</div>
				</div>
				
				<div class="col-md-3">
					<button class="btn btn-primary text-normal" type="button" id="search">
						<i class="fas fa-search fa-sm"></i>
					</button>
				</div>
			</div>
			
			
		</div>
	</div>
	
	<div class="card table-card">
		<h4 class="small font-weight-bold" style="margin-top:20px; margin-left:20px;"># 전일자 기준 <span class="selectClassName">분리보관</span> 총 처리내역(전일자 기준에 대한 내역 조회)</h4>
		<div class="card-body" style="max-height:250px;">
			<table width="80%" class="table table-striped table-bordered table-hover text-normal" id="totalStaticTable">
				<thead>
					<tr style="text-align:center;">
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 총 건수</th>
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 처리건수</th>
						<th colspan="2"><span class="selectClassName">분리보관</span> 처리 결과</th>
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 대기건수</th>
					</tr>
					<tr style="text-align:center;">
						<th nowrap><span class="selectClassName">분리보관</span> 정상</th>
						<th nowrap><span class="selectClassName">분리보관</span> 오류</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="card table-card">
		<h4 class="small font-weight-bold" style="margin-top:20px; margin-left:20px;"># <span class="selectClassName">분리보관</span> 처리일자별 처리내역</h4>
		<div class="card-body" style="overflow-y:scroll; max-height:498px;">
			<table width="80%" class="table table-striped table-bordered table-hover text-normal" id="dayStaticTable">
				<thead>
					<tr style="text-align:center;">
		 				<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 처리일자</th>
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 대상건수</th>
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 처리건수</th>
						<th colspan="2"><span class="selectClassName">분리보관</span> 처리 결과</th>
						<th rowspan="2" style="vertical-align:middle;"><span class="selectClassName">분리보관</span> 대기건수</th>
					</tr>
					<tr style="text-align:center;">
					<th nowrap><span class="selectClassName">분리보관</span> 정상</th>
					<th nowrap><span class="selectClassName">분리보관</span> 오류</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</div>