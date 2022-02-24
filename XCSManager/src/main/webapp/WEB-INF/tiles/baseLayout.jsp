<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="ko">
<tiles:insertAttribute name="head" />

<!-- Bootstrap core JavaScript-->
<script src="${ctx}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${ctx}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${ctx}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${ctx}/resources/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="${ctx}/resources/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="${ctx}/resources/js/demo/chart-area-demo.js"></script>
<script src="${ctx}/resources/js/demo/chart-pie-demo.js"></script>

<!-- Common -->
<link href="${ctx}/resources/css/common.css" rel="stylesheet">
<script src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript">
	var ctx = "<%=request.getContextPath()%>";
</script>
<style>
#content-wrapper {
	left: 14rem;
	width: calc(100% - 14rem)!important;
}
	
@media (max-width: 768px) {
	#content-wrapper {
		left: 6.5rem;
		width: calc(100% - 6.5rem)!important;
	}
}

mask {
	position: fixed;
	width: 100%;
	height: 100%;
	z-index: 99997;
	top: 0;
	left: 0;
	display: none;
	justify-content: center;
	align-items: center;
}

mask div#maskBackground {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 99998;
	background: #BDBDBD;
	opacity: 0.5;
}

mask div#maskImage {
	z-index: 99999;
	background: #FFF;
	display: flex;
	flex-direction: column;
	align-items: center;
	border-radius: 7px;
}

mask div#maskImage strong {
	font-size: 1.5em !important;
}
</style>
<body id="page-top">
	<div id="wrapper">
		<tiles:insertAttribute name="left" />
		<div id="content-wrapper" class="d-flex flex-column position-absolute" style="height:calc(100% - 5vh) !important">
			<div id="content">
				<tiles:insertAttribute name="body" />
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</div>
	<div id="alertAria">
		<div class="alert alert-success w-25 position-absolute text-normal" style="display: none; top: 1vw; left: 50%; transform: translate(-50%, 0%)" role="alert">
			<h5 class="alert-success-head">Success</h4>
			<p class="alert-success-body"></p>
		</div>
		<div class="alert alert-danger w-25 position-absolute text-normal" style="display: none; top: 1vw; left: 50%; transform: translate(-50%, 0%)" role="alert">
			<h5 class="alert-danger-head">Error</h4>
			<p class="alert-danger-body"></p>
		</div>
	</div>
	<mask>
		<div id="maskBackground"></div>
		<div id="maskImage">
			<img alt="Loading..." src="${ctx}/resources/img/loading.gif">
			<strong>Loading</strong>
		</div>
	</mask>
	
	<mask2>
		<div id="mask2Background"></div>
		<div id="mask2Image">
			<img alt="Loading..." src="${ctx}/resources/img/loading.gif">
			<strong>Loading</strong>
		</div>
	</mask2>
</body>
</html>