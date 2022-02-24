<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${ctx}/resources/js/search/performance.js"></script>
<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
		<h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">조회 - 배치 성능</h4>
	</div>
	<div class="card mb-2 table-card">
		<div class="card-body" style="min-width:500px;">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">시간 구분</h4>
						<select class="custom-select mr-1 text-normal" id="selectTimeDiv">
							<option value="MIN">분당 구분</option>
							<option value="HOUR">시간당 구분</option>
						</select>
					</div>
				</div>
			
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">작업 구분</h4>
						<select class="custom-select mr-1 text-normal" id="selectClass">
						</select>
					</div>
				</div>
				
				<div class="col-md-6">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">날짜 선택</h4>
						<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date()) %>"></c:set>
						<input type="date" class="inp-style form-control text-center text-normal" name="selectDay" id="selectDay" value="${now}" style="margin-right:10px;"/>
						
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;" id="selectTimeTitle">조회 시간</h4>
						<select class="custom-select mr-1 text-normal" id="selectTime" style="margin-right:10px;"></select>
						
						<button class="btn btn-primary text-normal" type="button" id="search">
							<i class="fas fa-search fa-sm"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-12">
	        <div class="card shadow mb-12">
	            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                <h6 class="m-0 font-weight-bold text-primary"><span class="selectClassName">분리보관</span> 배치 성능 그래프</h6>
	                <h6 class="m-0 font-weight-bold text-primary card-header-title"></h6>
	            </div>
				<div class="card-body" style="height: 25vh; min-height: 200px; max-height: 300px;">
					<canvas id="dtChartLine"></canvas>
                </div>
	        </div>
	    </div>
	</div>
	
	<div class="card mb-2 table-card">
		<div class="card-body">
			<span class="small font-weight-bold" style="float:right;"># 총 건수 : <span id="selectCount">0</span> 건</span>
			<table width="80%" class="table table-striped table-bordered table-hover text-normal" id="dataTable">
				<thead>
					<tr style="text-align:center;">
						<th nowrap>수행 날짜</th>
						<th nowrap>분류</th>
						<th nowrap>수행 건수</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="pagination-wrapper d-flex align-items-center">
				<ul class="pagination text-normal justify-content-center" style="margin-bottom: 0px !important;"></ul>
				<!-- <span class="badge border" id="selectCount">0</span> -->
				<div class="goPage-wrapper" id="goPageDirect" style="display:none;">
					<div class="input-group">
						<div class="col-xs-1">
							<input type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" class="form-control bg-light border-0 small text-center text-normal" aria-label="Search" id="goPageNum" style="ime-mode:inactive">
						</div>
						<div class="input-group-append">
							<button class="btn btn-primary text-normal" type="button" id="goPage">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>