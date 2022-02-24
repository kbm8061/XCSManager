<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<%request.setAttribute("ctx", request.getContextPath()); // Root Path Absolute URL %>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>XCSManager - 로그인</title>

<!-- Custom fonts for this template-->
<link href="${ctx}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template-->
<link href="${ctx}/resources/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Bootstrap core JavaScript-->
<script src="resources/vendor/jquery/jquery.min.js"></script>
<script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="resources/js/sb-admin-2.min.js"></script>
<script>
$(document).ready(function(){
	var message = "${message}";
	if (message != "" &&  message != null)
		$('#loginError').css('display', 'block');
	
	$('#userId').focus();
	
    $(document).on("keyup", "#userId", function(e) {
		if(e.keyCode=='13') { login(); }
	});

    $(document).on("keyup", "#passWD", function(e) {
		if(e.keyCode=='13') { login(); }
	});
});

function login() {
	errorManage();
	
	var userId = $('#userId').val().replace(/ /g, '');
	if (checkSpecial(userId)) {
		$('#userId').val('');
		$('#idWordError').css('display', 'block');
		return false;
	}
	
	var passWD = $('#passWD').val();
	if (userId == null || userId == undefined || userId == "") {
		$('#idError').css('display', 'block');
		return false;
	} else if (passWD == null || passWD == undefined || passWD == "") {
		$('#pwError').css('display', 'block');
		return false;
	}
	
	$("#loginForm").submit();
}

function errorManage() {
	$('#loginError').css('display', 'none');
	$('#idError').css('display', 'none');
	$('#idWordError').css('display', 'none');
	$('#pwError').css('display', 'none');
}

function checkSpecial(str) { 
	var special_pattern = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	return special_pattern.test(str) == true;
}
</script>
</head>
<body class="bg-gradient-primary">
    <div class="container h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-5">
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block" style="background: url(/XCSManager/resources/img/logo.png); background-position: center; background-size: cover;"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <form class="user" action="actionLogin" id="loginForm" name="loginForm" method="POST">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user" id="userId" name="userId" placeholder="User Id">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user" id="passWD" name="passWD" placeholder="Password">
                                        </div>
                                        <a href="javascript:login()" class="btn btn-primary btn-user btn-block">Login</a>
                                        <div style="display: none;" class="text-center" id="loginError">
                                        	<span class="text-danger">아이디와 비밀번호를 확인해주세요.</span>
                                        </div>
                                        <div style="display: none;" class="text-center" id="idError">
                                        	<span class="text-danger">아이디를 입력해주세요.</span>
                                        </div>
                                        <div style="display: none;" class="text-center" id="idWordError">
                                        	<span class="text-danger">아이디에 특수문자를 사용할 수 없습니다.</span>
                                        </div>
                                        <div style="display: none;" class="text-center" id="pwError">
                                        	<span class="text-danger">비밀번호를 입력해주세요.</span>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>