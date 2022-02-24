let limitDay = 30;
let emJson = {};
let chartLabel;

$(function() {
	const targetClass = $('#selectClass');
	selectClassCodeInit(targetClass, "COM");
	
	init();
	
	$("#btnSetWeek").on("click", function() {
		emJson.startDay = new DateUtil().calcDay(-7).toString().slice(0, 8);
		//emJson.endDay = new DateUtil().toString().slice(0, 8);
		emJson.endDay = $('#endDay').val().replace(/-/g, '')
		chartLabel = getLabels.forWeek();
		
		element();
		standBy();
	});
	
	$("#btnSetMonth").on("click", function() {
		emJson.startDay = new DateUtil().calcMonth(-1).calcDay(0).toString().slice(0, 8);
		//emJson.endDay = new DateUtil().toString().slice(0, 8);
		emJson.endDay = $('#endDay').val().replace(/-/g, '')
		chartLabel = getLabels.forMonth();
		
		element();
		standBy();
	});
	
	$("#btnSearchForDate").on("click", function() {
		let stDate = $('#startDay').val().replace(/-/g, '');
		let edDate = $('#endDay').val().replace(/-/g, '');
		let diffDate = new DateUtil(stDate).diffDay(edDate);
	
		if (diffDate < 0) {
			alert("시작 날짜가 종료 날짜보다 더 이후 일 수 없습니다.");
		} else if (diffDate > limitDay) {
			alert("검색 일자가 " + limitDay + "일을 초과할 수 없습니다.");
		} else {
			emJson.startDay = stDate;
			emJson.endDay = edDate;

			chartLabel = getLabels.forDays(emJson.startDay, emJson.endDay);
			
			element();
			standBy();
		}
	})

	$("#sbRefresh").on("click", function() {
		standBy();
	});

	$("#emRefresh").on("click", function() {
		element();
	});
});

let init = function() {
	emJson.startDay = $('#startDay').val().replace(/-/g, '');
	emJson.endDay = $('#endDay').val().replace(/-/g, '');
	
	if ($("#selectClass option:selected").attr("value") == null) {
		emJson.classification = 'SP';
	} else {
		emJson.classification = $("#selectClass option:selected").attr("value")
	};
	
	chartLabel = getLabels.forDays(emJson.startDay, emJson.endDay);
	
	selectClass();
	element();
	standBy();
}

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
}

const selectClass = function() {
	let selectClass = $("#selectClass option:selected").attr("value");
		
	$(".selectClassName").text($('#selectClass option:selected').text());
	
	emJson.classification = selectClass;
}

const standBy = function() {
	selectClass();
	
	AjaxUtil.ajaxStandBy(ctx + "/getStandByStat", "POST", emJson,
		function (result) {
			if (result.totalStaticList[0].targetCount == 0) {
				$('#dtTotalSpan').text("0 (0%)");
				$('#dtTotalBar').css('width', '0%');
			} else {
				let totalPer = result.totalStaticList[0].targetCount * 100 / result.totalStaticList[0].targetCount;
				$('#dtTotalSpan').text(numberWithCommas(result.totalStaticList[0].targetCount));
				$('#dtTotalBar').css('width', 100 + '%');
			}
			
			if (result.totalStaticList[0].processCount == 0) {
				$('#dtProcessSpan').text("0 (0%)");
				$('#dtProcessBar').css('width', '0%');
			} else {
				let processPer = result.totalStaticList[0].processCount * 100 / result.totalStaticList[0].targetCount;
				$('#dtProcessSpan').text(numberWithCommas(result.totalStaticList[0].processCount) + " (" + processPer.toFixed(2) + "%)");
				$('#dtProcessBar').css('width', processPer.toFixed(2) + '%');
			}
			
			if (result.totalStaticList[0].waitCount == 0) {
				$('#dtStandBySpan').text("0 (0%)");
				$('#dtStandByBar').css('width', '0%');
			} else {
				let standByPer = result.totalStaticList[0].waitCount * 100 / result.totalStaticList[0].targetCount;
				$('#dtStandBySpan').text(numberWithCommas(result.totalStaticList[0].waitCount) + " (" + standByPer.toFixed(2) + "%)");
				$('#dtStandByBar').css('width', standByPer.toFixed(2) + '%');
			}
			
			if (result.totalStaticList[0].successCount == 0) {
				$('#dtSuccessSpan').text("0 (0%)");
				$('#dtSuccessBar').css('width', '0%');
			} else {
				let successPer = result.totalStaticList[0].successCount * 100 / result.totalStaticList[0].processCount;
				$('#dtSuccessSpan').text(numberWithCommas(result.totalStaticList[0].successCount) + " (" + successPer.toFixed(2) + "%)");
				$('#dtSuccessBar').css('width', successPer.toFixed(2) + '%');
			}

			if (result.totalStaticList[0].failCount == 0) {
				$('#dtErrorSpan').text("0 (0%)");
				$('#dtErrorBar').css('width', '0%');
			} else {
				let errorPer = result.totalStaticList[0].failCount * 100 / result.totalStaticList[0].processCount;
				$('#dtErrorSpan').text(numberWithCommas(result.totalStaticList[0].failCount) + " (" + errorPer.toFixed(2) + "%)");
				$('#dtErrorBar').css('width', errorPer.toFixed(2) + '%');
			}
			
			emChart(result);
		},
		function (result) {
			alert(result.responseText);
		}
	);
};

const element = function() {
	selectClass();
	
	AjaxUtil.ajaxElement(ctx + "/getElementStat", "POST", emJson,
		function(result) {
			if (result.todayStaticList[0].targetCount == 0) {
				$('#todayTotalSpan').text("0 (0%)");
				$('#todayTotalBar').css('width', '0%');
			} else {
				let totalPer = result.todayStaticList[0].targetCount * 100 / result.todayStaticList[0].targetCount;
				$('#todayTotalSpan').text(numberWithCommas(result.todayStaticList[0].targetCount));
				$('#todayTotalBar').css('width', 100 + '%');
			}
			
			if (result.todayStaticList[0].waitCount == 0) {
				$('#todayWaitSpan').text("0 (0%)");
				$('#todayWaitBar').css('width', '0%');
			} else {
				let waitPer = result.todayStaticList[0].waitCount * 100 / result.todayStaticList[0].targetCount;
				$('#todayWaitSpan').text(numberWithCommas(result.todayStaticList[0].waitCount) + " (" + waitPer.toFixed(2) + "%)");
				$('#todayWaitBar').css('width', waitPer.toFixed(2) + '%');
			}
			
			if (result.todayStaticList[0].processCount == 0) {
				$('#todayProcessSpan').text("0 (0%)");
				$('#todayProcessBar').css('width', '0%');
			} else {
				let processPer = result.todayStaticList[0].processCount * 100 / result.todayStaticList[0].targetCount;
				$('#todayProcessSpan').text(numberWithCommas(result.todayStaticList[0].processCount) + " (" + processPer.toFixed(2) + "%)");
				$('#todayProcessBar').css('width', processPer.toFixed(2) + '%');
			}
			
			if (result.todayStaticList[0].successCount == 0) {
				$('#todayDtSuccessSpan').text("0 (0%)");
				$('#todayDtSuccessBar').css('width', '0%');
			} else {
				let successPer = result.todayStaticList[0].successCount * 100 / result.todayStaticList[0].processCount;
				$('#todayDtSuccessSpan').text(numberWithCommas(result.todayStaticList[0].successCount) + " (" + successPer.toFixed(2) + "%)");
				$('#todayDtSuccessBar').css('width', successPer.toFixed(2) + '%');
			}

			if (result.todayStaticList[0].failCount == 0) {
				$('#todayDtErrorSpan').text("0 (0%)");
				$('#todayDtErrorBar').css('width', '0%');
			} else {
				let errorPer = result.todayStaticList[0].failCount * 100 / result.todayStaticList[0].processCount;
				$('#todayDtErrorSpan').text(numberWithCommas(result.todayStaticList[0].failCount) + " (" + errorPer.toFixed(2) + "%)");
				$('#todayDtErrorBar').css('width', errorPer.toFixed(2) + '%');
			}
		},
		
		function(result) {
			alert(result.responseText);
		}
	);
};

const emChart = function(data) {
	$('.card-header-title').text('( ' + DateFormat(emJson.startDay) + ' ~ ' + DateFormat(emJson.endDay) + " )");

	let chartData = [];
	let target = $('#dtChartBar');
	let parent = $('#dtChartBar').parent();
	let successData = {data : []};
	let failData = {data : []};

	target.remove();
	parent.append('<canvas id="dtChartBar"></canvas>');
	
	successData.backgroundColor = "#4e73df";
	successData.hoverBackgroundColor = "#2e59d9";
	successData.borderColor = "#4e73df";
	
	failData.backgroundColor = "#e74a3b";
	failData.hoverBackgroundColor = "#2e59d9";
	failData.borderColor = "#e74a3b";
	
	//let copyLabel = [...chartLabel];
	let copyLabel = chartLabel.slice();
	copyLabel.pop();
	
	//let copyLabel = copyObj(chartLabel);
	
	let maxCount = 0;
	if (data.dayStaticList != null && data.dayStaticList != undefined){
		let index = 0;
		
		let matchStatDate = false;
		
		for (let i = 0; i < copyLabel.length; i++){
			for (let j = 0; j < data.dayStaticList.length; j++){
				if (copyLabel[i] == data.dayStaticList[j].statDate){
					successData.data[index] = data.dayStaticList[j].successCount;
					failData.data[index] = data.dayStaticList[j].failCount;
					matchStatDate = true;
					
					if (maxCount < Number(data.dayStaticList[j].successCount)){
						maxCount = data.dayStaticList[j].successCount;
					}
					if (maxCount < Number(data.dayStaticList[j].failCount)){
						maxCount = data.dayStaticList[j].failCount;
					}
					
					break;
				}
				else{
					matchStatDate = false;
				}
			}
			
			if (matchStatDate == false){
				successData.data[index] = 0;
				failData.data[index] = 0;
			}
			index++;
		}
		
	}
	else{
		//Data Empty
	}
	chartData.push(successData);
	chartData.push(failData);
	ChartUtil.bar($('#dtChartBar'), copyLabel, chartData, maxCount);
};

const getLabels = {
	forDay : function(_day) {
		let arr = [];

		let date = new DateUtil(_day).getDate();
		for (let i=0; i<=24; i++)
			arr.push(new DateUtil(date).toString().slice(0, 8) + ((i<10)?("0"+i):i));

		return arr;
	},
	forDays : function(_startDate, _endDate) {
		let arr = [];

		let startDate = new DateUtil(_startDate);
		let endDate = new DateUtil(_endDate);

		/*
		20211109 차트 시간 표시
		if (startDate.toString() == endDate.toString())
			arr = getLabels.forDay(_startDate);
		else {
		*/
			while (startDate.getDate() <= endDate.getDate()) {
				arr.push(startDate.toString().slice(0, 8));
				startDate = startDate.calcDay(1);
			}
			arr.push(startDate.toString().slice(0, 8));
		/*
		}
		 */

		return arr;
	},
	forWeek : function() {
		let date = new DateUtil();
		return getLabels.forDays(date.calcDay(-7).getDate(), date.calcDay(-1).getDate());
	},
	forMonth : function() {
		let date = new DateUtil();
		return getLabels.forDays(date.calcMonth(-1).getDate(), date.calcDay(-1).getDate());
	},
	forYear : function() {
		let arr = [];

		let date = new DateUtil();
		let startDate = new DateUtil(date.calcYear(-1).getDate());
		let endDate = new DateUtil(date.getDate());

		while (startDate.getDate() <= endDate.getDate()) {
			arr.push(startDate.toString().slice(0, 6));
			startDate = new DateUtil(startDate.calcMonth(1).getDate());
		}
		arr.push(startDate.toString().slice(0, 8));

		return arr.slice(1);
	}
};

let numberWithCommas = function(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function copyObj(obj) {
	const result = {};
	
	for (let key in obj) {
		if (typeof obj[key] === 'object') {
	      result[key] = copyObj(obj[key]);
	    } else {
	      result[key] = obj[key];
	    }
	}

	return result;
}
