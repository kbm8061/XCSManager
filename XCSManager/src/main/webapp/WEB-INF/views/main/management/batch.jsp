<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${ctx}/resources/js/management/batch.js"></script>
<div class="container-fluid p-2" style="height:calc(100% - 5vh) !important;">
	<div class="d-sm-flex align-items-center justify-content-between mb-1">
		<h4 class="ml-2 mb-0 text-gray-800 font-weight-bold">관리 - 배치 코드</h4>
	</div>

	<div class="card table-card">
		<div class="card-body">
			<span class="small font-weight-bold" style="float:right;"># 총 건수 : <span id="selectCount">0</span> 건</span>
			<table width="80%" class="table table-striped table-bordered table-hover text-normal" id="batchTable">
				<thead>
					<tr style="text-align:center;">
						<th nowrap>서버명</th>
						<th nowrap>배치명</th>
						<th nowrap>쓰레드개수</th>
						<th nowrap>기동 여부</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="pagination-wrapper d-flex align-items-center">
				<ul class="pagination text-normal justify-content-center" style="margin-bottom: 0px !important;"></ul>
				<!-- <span class="badge border" id="selectCount">0</span> -->
				<div class="goPage-wrapper" id="goPageDirect">
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
	
	<div class="card mb-2 table-card">
		<div class="card-body" style="min-width:500px;">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">서버 명*</h4>
						<input type="text" class="form-control bg-light border-0 small text-normal" aria-label="Search" id="serverInput" maxlength="10">
					</div>
				</div>
			
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">배치 명*</h4>
						<input type="text" class="form-control bg-light border-0 small text-normal" aria-label="Search" id="agentInput" maxlength="10">
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;">쓰레드 개수</h4>
						<input type="number" class="form-control bg-light border-0 small text-normal" aria-label="Search" id="threadCntInput">
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="input-group">
						<h4 class="small font-weight-bold" style="margin-top:10px; width:70px;" >기동 여부</h4>
						<select class="custom-select mr-1 text-normal" id="runYnInput">
							<option value="Y">기동</option>
							<option value="N">중지</option>
						</select>
						<!-- <input type="text" class="form-control bg-light border-0 small text-normal" aria-label="Search" id="runYnInput" maxlength="1"> -->
					</div>
				</div>
			</div>
			
			<div class="row justify-content-end" style="margin-top:15px;">
				<input class="btn btn-primary text-normal" style="margin-right:5px;" id="btnInsertBatch" type="button" value="생성">
				<input class="btn btn-success text-normal" style="margin-right:5px;" id="btnUpdateBatch" type="button" value="수정">
				<input class="btn btn-danger text-normal" style="margin-right:5px;" id="btnDeleteBatch" type="button" value="삭제">
				<input class="btn btn-info text-normal" style="margin-right:5px;" id="btnRefresh" type="button" value="초기화">
			</div>
		</div>
	</div>
</div>