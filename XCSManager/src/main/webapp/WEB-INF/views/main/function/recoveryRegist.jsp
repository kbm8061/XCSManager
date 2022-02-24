<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${ctx}/resources/js/function/recoveryRegist.js"></script>
<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
		<h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">기능 - 복원대상등록</h4>
		<p style="color:red;">**분리보관 정상처리 된 건만 조회됩니다.**</p>
	</div>
	<div class="card mb-1 table-card">
		<div class="card-body">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">작업 구분</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="custom-select mr-1 text-normal" id="selectClass">
						</select>
					</div>
				</div>
			
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">상태 구분</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="custom-select mr-1 text-normal" id="selectStatus">
						</select>
					</div>
				</div>
				
				<div class="col-md-2">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">일자 구분</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="custom-select mr-1 text-normal" id="selectDate">
							<option value="regDate">등록일자</option>
							<option value="processDate">수행일자</option>
						</select>
					</div>
				</div>
				
				<div class="col-md-5">
					<div class="input-group" id="divDate">
						<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date().getTime()-(60*60*24*1000*7)) %>"></c:set>
						<h4 class="small font-weight-bold" style="margin-top:10px;">시작일</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="date" class="inp-style form-control mr-2 text-center text-normal" name="startDay" id="startDay" value="${now}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<c:set var="now" value="<%= new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(new java.util.Date()) %>"></c:set>
						<h4 class="small font-weight-bold" style="margin-top:10px;">종료일</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="date" class="inp-style form-control ml-2 text-center text-normal" name="endDay" id="endDay" value="${now}"/>
					</div>
				</div>
				
			</div>
			
			<div class="row">
				<div class="col-md-12" style="margin-top:10px;">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">이미지 키</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="custom-select mr-1 text-normal" id="selectId">
							<option value="elementId">엘리먼트 아이디</option>
							<option value="indexKey">업무키</option>
							<option value="owner">소유계정명</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form class="d-none d-sm-inline-block form-inline mw-50 navbar-search w-50 text-normal" onSubmit="return false;">
							<div class="input-group">
								<input type="text" class="form-control bg-light border-0 small text-center text-normal" aria-label="Search" id="searchInput">
								<div class="input-group-append">
									<button class="btn btn-primary text-normal" type="button" id="search">
										<i class="fas fa-search fa-sm"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="card table-card">
		<div class="card-body">
			<span class="small font-weight-bold" style="float:right;"># 총 건수 : <span id="selectCount">0</span> 건</span>
			<table width="80%" class="table table-striped table-bordered table-hover text-normal" id="elementTable">
				<thead>
					<tr style="text-align:center;">
						<th nowrap style="text-align:center; vertical-align:middle;"><input type="checkbox" id="allCheck"/></th>
						<th nowrap>엘리먼트 아이디</th>
						<th nowrap>업무키</th>
						<th nowrap>소유계정명</th>
						<th nowrap>분리보관된 파일경로</th>
						<th nowrap>분류</th>
						<th nowrap>상태값</th>
		 				<th nowrap>등록날짜</th>
		 				<th nowrap>처리일시</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="pagination-wrapper d-flex align-items-center">
				<ul class="pagination text-normal" style="margin-bottom: 0px !important;"></ul>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <span class="badge border" id="selectCount">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
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
				<div class="button-wrapper d-flex align-items-center" style="margin-left:auto;">
					<button id="recoveryElements" class="btn btn-primary text-normal" type="button" style="display:none; margin-left:10px;">
						<i class="fa fa-history"> 복원 대기상태로 등록</i>
					</button>
					<button id="downloadExcel" class="btn btn-primary text-normal" type="button" style="display:none; margin-left:10px;">
						<i class="fas fa-download fa-sm"> 엑셀 다운로드</i>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>