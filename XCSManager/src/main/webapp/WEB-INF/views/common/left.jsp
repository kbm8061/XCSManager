<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%-- 
<%
	com.inzent.ecm.vo.UserVO vo = (com.inzent.ecm.vo.UserVO) request.getSession().getAttribute("XMangerUser");
%>
 --%>
<script type="text/javascript">
$(function() {
	// If click nav-item, another nav-item list close
	/* $(document).on("click", ".nav-item", function() {
		$.each($('.nav-item'), function(item) {
			$(this).find('.collapse').removeClass('show');
		});
	}); */
});
</script>
<style>
@media (max-width: 768px) {
	.sidebar > div:first-child {
		display: none;
	}

	.sidebar .nav-item #collapseSetting {
		top: -73px !important;
	}
}
</style>
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion position-fixed" id="accordionSidebar" style="z-index : 999;">
	<div class="my-3 mx-4 text-center" style="color: #FFF !important;">
		<%-- <% request.setAttribute("name", vo.getUserName()); %> --%>
		<%-- <strong>Hello, ${name}</strong> --%>
		<strong>eXpire Contents Separate Management</strong>
	</div>
	<hr class="sidebar-divider my-0">
	
	<hr class="sidebar-divider">
	<div class="sidebar-heading">Search</div>
	<li class="nav-item">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettingSearch" aria-expanded="true" aria-controls="collapseSetting">
			<i class="fas fa-fw fa-list-ul"></i>
			<span>조회</span>
		</a>
		<div id="collapseSettingSearch" class="collapse">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/XCSManager/search/element">전체 대상</a>
				<a class="collapse-item" href="/XCSManager/search/performance">배치 성능</a>
				<a class="collapse-item" href="/XCSManager/search/batchHistory">배치 이력</a>
				<a class="collapse-item" href="/XCSManager/search/errorHistory">오류 이력</a>
		
			</div>
		</div>
	</li>
	
	<hr class="sidebar-divider">
	<div class="sidebar-heading">Statistics</div>
	<li class="nav-item">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettingStatic" aria-expanded="true" aria-controls="collapseSetting">
			<i class="fas fa-fw fa-history"></i> 
			<span>통계</span>
		</a>
		<div id="collapseSettingStatic" class="collapse">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/XCSManager/statistics/dashBoard">진척조회 (그래프)</a>
				<a class="collapse-item" href="/XCSManager/statistics/static">처리일자별 진척조회</a>
			</div>
		</div>
	</li>
	

	<hr class="sidebar-divider">
	<div class="sidebar-heading">Function</div>
	<li class="nav-item">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettingFunction" aria-expanded="true" aria-controls="collapseSetting">
			<i class="fas fa-fw fa-list-ul"></i>
			<span>기능</span>
		</a>
		<div id="collapseSettingFunction" class="collapse">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/XCSManager/function/realtimeProcess">실시간 처리</a>
				<a class="collapse-item" href="/XCSManager/function/recoveryRegist">복원대상 등록</a>
			</div>
		</div>
	</li>
	
	<hr class="sidebar-divider">
	<div class="sidebar-heading">Upload</div>
	<li class="nav-item">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettingUpload" aria-expanded="true" aria-controls="collapseSetting">
			<i class="fas fa-fw fa-list-ul"></i>
			<span>등록</span>
		</a>
		<div id="collapseSettingUpload" class="collapse">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/XCSManager/upload/excel">등록 (Excel)</a>
				<a class="collapse-item" href="/XCSManager/upload/csv">등록 (CSV)</a>
			</div>
		</div>
	</li>
	

	<hr class="sidebar-divider">
	<div class="sidebar-heading">Management</div>
	<li class="nav-item">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettingManage" aria-expanded="true" aria-controls="collapseSetting">
			<i class="fa fa-cog fa-fw"></i>
			<span>관리</span>
		</a>
		<div id="collapseSettingManage" class="collapse">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/XCSManager/management/code">코드</a>
				<a class="collapse-item" href="/XCSManager/management/batch">배치</a>
			</div>
		</div>
	</li>
</ul>
