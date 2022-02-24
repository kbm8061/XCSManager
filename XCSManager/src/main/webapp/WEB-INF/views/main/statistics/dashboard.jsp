<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<script src="${ctx}/resources/js/statistics/dashboard.js"></script>

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

<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
        <h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">통계 - 진척조회 (그래프)</h4>
        
		<div class="dropdown no-arrow">
			<a role="button" id="sbRefresh">
				<i class="fas fa-redo fa-sm fa-fw text-gray-400"></i>
			</a>
		</div>
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
					<button class="btn btn-primary text-normal" type="button" id="btnSearchForDate">
						<i class="fas fa-search fa-sm"></i>
					</button>
					<input class="btn btn-success text-normal" id="btnSetWeek" type="button" value="최근 1주일">
					<input class="btn btn-danger text-normal" id="btnSetMonth" type="button" value="최근 1달">
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
        <div class="col-md-4 col-12" id="standByDivLeft">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary"># 전일자 기준 총 <span class="selectClassName">분리보관</span> 처리내역</h6>
                </div>
                <div class="card-body" id="dtStatCard">
                    <h4 class="small font-weight-bold"><span class="selectClassName">분리보관</span> 총 건수<span class="float-right" id="dtTotalSpan">0 (0%)</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-success" role="progressbar" id="dtTotalBar" style="width: 0%; transition: all 1.5s ease 0s;" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    
                    <h4 class="small font-weight-bold"><span class="selectClassName">분리보관</span> 대기 건수<span class="float-right" id="dtStandBySpan">0 (0%)</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-info" role="progressbar" id="dtStandByBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    
                    <h4 class="small font-weight-bold"><span class="selectClassName">분리보관</span> 처리 건수<span class="float-right" id="dtProcessSpan">0 (0%)</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-info" role="progressbar" id="dtProcessBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                   
                    <h4 class="small font-weight-bold"><span class="selectClassName">분리보관</span> 정상<span class="float-right" id="dtSuccessSpan">0 (0%)</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar" role="progressbar" id="dtSuccessBar" style="width: 0%; transition: all 1.5s ease 0s;" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    
                    <h4 class="small font-weight-bold"><span class="selectClassName">분리보관</span> 실패<span class="float-right" id="dtErrorSpan">0 (0%)</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-danger" role="progressbar" id="dtErrorBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-md-8 col-12" id="standByDivRight">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">#전일자 기준 <span class="selectClassName">분리보관</span> 처리일자별 처리내역</h6>
                    <h6 class="m-0 font-weight-bold text-primary card-header-title"></h6>
                </div>
                <div class="card-body" style="height: 25vh; min-height: 355px;">
					<canvas id="dtChartBar"></canvas>
                </div>
            </div>
        </div>
        
        <div class="col-12" id="elementDiv">
			<div class="card shadow mb-4">
				<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date()) %>"></c:set>
					<h6 class="m-0 font-weight-bold text-primary"># 당일 <span class="selectClassName">분리보관</span> 처리내역 (${now})</h6>
					<a role="button" id="emRefresh">
						<i class="fas fa-redo fa-sm fa-fw text-gray-400"></i>
					</a>
				</div>
				<div class="card-body" id="sbStatCard">
                    <div class="row">
                        <div class="col-md-12 col-12">
                            <h4 class="small font-weight-bold">당일 <span class="selectClassName">분리보관</span> 총 건수<span class="float-right" id="todayTotalSpan">0 (0%)</span></h4>
                            <div class="progress mb-4">
                                <div class="progress-bar bg-success" role="progressbar" id="todayTotalBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 col-12">
                            <h4 class="small font-weight-bold">당일 <span class="selectClassName">분리보관</span> 대기 건수<span class="float-right" id="todayWaitSpan">0 (0%)</span></h4>
                            <div class="progress mb-4">
                                <div class="progress-bar bg-info" role="progressbar" id="todayWaitBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 col-12">
                            <h4 class="small font-weight-bold">당일 <span class="selectClassName">분리보관</span> 처리 건수<span class="float-right" id="todayProcessSpan">0 (0%)</span></h4>
                            <div class="progress mb-4">
                                <div class="progress-bar bg-info" role="progressbar" id="todayProcessBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 col-12">
		                    <h4 class="small font-weight-bold">당일 <span class="selectClassName">분리보관</span> 정상 건수<span class="float-right" id="todayDtSuccessSpan">0 (0%)</span></h4>
		                    <div class="progress mb-4">
		                        <div class="progress-bar" role="progressbar" id="todayDtSuccessBar" style="width: 0%; transition: all 1.5s ease 0s;" aria-valuemin="0" aria-valuemax="100"></div>
		                    </div>
                        </div>
                        
                        <div class="col-md-3 col-12">
                        	<h4 class="small font-weight-bold">당일 <span class="selectClassName">분리보관</span> 실패 건수<span class="float-right" id="todayDtErrorSpan">0 (0%)</span></h4>
		                    <div class="progress mb-4">
		                        <div class="progress-bar bg-danger" role="progressbar" id="todayDtErrorBar" style="width: 0%; transition: all 1.5s ease 0s;;" aria-valuemin="0" aria-valuemax="100"></div>
		                    </div>
                        </div>
                    </div>

                </div>
            </div>
            
        </div>
    </div>
</div>