<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${ctx}/resources/js/upload/excel.js"></script>
<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
		<h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">등록 - Excel 등록</h4>
	</div>
	<div class="card mb-2 table-card">
		<div class="card-body">
			<!-- <div class="row justify-content-center"> -->
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">파일 등록</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form id="frm-upload" enctype="multipart/form-data" action="/XCSManager/uploadExcel" method="post">
							<div class="input-group">
								<input class="form-control-file" type="file" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6" style="margin-top:10px;">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px;">작업 구분</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="custom-select mr-1 text-normal" id="selectClass" style="width:auto;"></select>
						<button id="fileUploadBtn" class="btn btn-primary text-normal" type="button">
							<i class="fas fa-upload fa-sm"> 업로드</i>
						</button>
						<button id="fileDownloadBtn" class="btn btn-primary text-normal" type="button" style="margin-left:5px;">
							<i class="fas fa-download fa-sm"> 양식 다운로드</i>
						</button>
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
			</div>
		</div>
	</div>
</div>