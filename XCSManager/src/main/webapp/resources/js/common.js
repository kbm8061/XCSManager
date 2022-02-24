let AjaxUtil = {
	ajax : function($url, $type, $data, $success, $error) {
		let $json = ($data) ? JSON.stringify($data) : "";
	
		$.ajax({
			headers : { 
		        'Accept' : 'application/json',
		        'Content-Type' : 'application/json' 
			},
			url : $url,
			type : $type,
			async : false,
			dataType : 'json',
			data : $json,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(response, textStatus) {
				if (textStatus=="success")
					$success(response);
				else
					$error(response);
			},
			error : function(response) {
				if (response.status=="200")
					$success(response);
				else
					$error(response);
			}
		});
	},
	ajaxAsync : function($url, $type, $data, $success, $error) {
		let $json = ($data) ? JSON.stringify($data) : "";
		let interval;
	
		$.ajax({
			headers : { 
		        'Accept' : 'application/json',
		        'Content-Type' : 'application/json' 
			},
			url : $url,
			type : $type,
			async : true,
			dataType : 'json',
			data : $json,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			beforeSend : function() {
            	$('mask').css('display', 'flex');
				interval = setInterval(function() {
					let text = $('mask strong').text();
					let count = 0;
					let pos = text.indexOf('.');
			
					while (pos !== -1) {
						count++;
						pos = text.indexOf('.', pos + 1);
					}
			
					if (count < 3)
						$('mask strong').text($('mask strong').text() + '.');
					else
						$('mask strong').text('Loading');
				}, 400);
            },
			success : function(response, textStatus) {
				if (textStatus=="success")
					$success(response);
				else
					$error(response);
			},
			error : function(response) {
				if (response.status=="200")
					$success(response);
				else
					$error(response);
			},
            complete : function() {
            	$('mask').css('display', 'none');
				if (interval != undefined)
					clearInterval(interval);
            }
		});
	},
	ajaxStandBy : function($url, $type, $data, $success, $error) {
		let $json = ($data) ? JSON.stringify($data) : "";
	
		$.ajax({
			headers : { 
		        'Accept' : 'application/json',
		        'Content-Type' : 'application/json' 
			},
			url : $url,
			type : $type,
			async : true,
			dataType : 'json',
			data : $json,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			beforeSend : function() {
            	$('#standByDivLeft').fadeTo(200, 0.3);
            	$('#standByDivRight').fadeTo(200, 0.3);
            },
			success : function(response, textStatus) {
				if (textStatus=="success")
					$success(response);
				else
					$error(response);
			},
			error : function(response) {
				if (response.status=="200")
					$success(response);
				else
					$error(response);
			},
            complete : function() {
            	$('#standByDivLeft').fadeTo(200, 1.0);
            	$('#standByDivRight').fadeTo(200, 1.0);
            }
		});
	},
	ajaxElement : function($url, $type, $data, $success, $error) {
		let $json = ($data) ? JSON.stringify($data) : "";
	
		$.ajax({
			headers : { 
		        'Accept' : 'application/json',
		        'Content-Type' : 'application/json' 
			},
			url : $url,
			type : $type,
			async : true,
			dataType : 'json',
			data : $json,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			beforeSend : function() {
            	$('#elementDiv').fadeTo(200, 0.3);
            },
			success : function(response, textStatus) {
				if (textStatus=="success")
					$success(response);
				else
					$error(response);
			},
			error : function(response) {
				if (response.status=="200")
					$success(response);
				else
					$error(response);
			},
            complete : function() {
            	$('#elementDiv').fadeTo(200, 1.0);
            }
		});
	},
	xhr : function($url, $type, $data, $fileName) {
		let mask = new loading();
		
		let xhr = new XMLHttpRequest();
		xhr.open($type, $url, true);
		xhr.setRequestHeader("Content-type","application/json;charset=UTF-8");
		xhr.setRequestHeader("Accept","application/json;charset=UTF-8");
		xhr.responseType = 'blob';
		
		mask.start();
		
		xhr.onreadystatechange = function() {
				if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
				let contentType = xhr.getResponseHeader('content-type');
				
				if (contentType.indexOf('application/octet-stream') == 0) {
					let blob = new Blob([this.response], { type : 'application/octet-stream'})
					
					if (window.navigator && window.navigator.msSaveBlob) {
						window.navigator.msSaveBlob(blob, downloadName);
					} else {
						let url = (window.URL || window.webkitURL).createObjectURL(blob);
						let link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = $fileName;
						link.click();
					}
				} else {
					let blob = new Blob([this.response], { type : 'application/json' })
	
					blob.text().then(function(result) {
						alert(JSON.parse(result).msg);
					});
				}
			}
			
			mask.stop();
		};
		xhr.send(JSON.stringify($data));
	}
};

// Date Util
const DateUtil = function(_date) {
	if (0 === arguments.length || _date == undefined)
		_date = new Date();

	function clone() {
		if (typeof(_date) == 'object')
			return new Date(_date.valueOf());
		else if (typeof(_date) == 'string') {
			if (_date.length == 6)
				return new Date( _date.substring(0, 4), _date.substring(4, 6) - 1);
			else if (_date.length == 8)
				return new Date( _date.substring(0, 4), _date.substring(4, 6) - 1, _date.substring(6, 8));
			else if (_date.length == 10)
				return new Date( _date.substring(0, 4), _date.substring(4, 6) - 1, _date.substring(6, 8), _date.substring(8, 10), '00', '00');
			else
				return;
		} else
			return;
	}

	this.getDate = function() {
		return clone();
	};

	this.toString = function() {
		let date = clone();
		return new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString().replace(/[^0-9]/gi, '');
	};
	
	this.calcDay = function(num) {
        let date = clone();
        date.setDate(date.getDate() + num);
        return new DateUtil(date);
    };

	this.calcMonth = function(num) {
		let date = clone();
		date.setMonth(date.getMonth() + num);
        return new DateUtil(date);
	};

	this.calcYear = function(num) {
		let date = clone();
		date.setFullYear(date.getFullYear() + num);
        return new DateUtil(date);
	};

	this.diffDay = function(day) {
		let stDate = clone();
		let edDate = new DateUtil(day).getDate();

		let diffTime = edDate.getTime() - stDate.getTime();
		if (diffTime == 0)
			return 0;
		else
			return diffTime / (1000*60*60*24);
	}
};

// Alert Util
let Alert = {
	error : function(message) {
		$('#alertAria .alert-danger .alert-danger-body').text(message);
		$('#alertAria .alert-danger').show(0).delay(2000).hide(0);
	},
	success : function(message) {
		$('#alertAria .alert-success .alert-success-body').text(message);
		$('#alertAria .alert-success').show(0).delay(2000).hide(0);
	}
};

/*
	example: number_format(1234.56, 2, ',', ' ');
	return: '1 234,56'
*/
let NumberFormat = function(number, decimals, dec_point, thousands_sep) {
	number = (number + '').replace(',', '').replace(' ', '');
	let n = !isFinite(+number) ? 0 : +number,
	prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
	sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
	dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
	s = '',
	
	toFixedFix = function(n, prec) {
		let k = Math.pow(10, prec);
		return '' + Math.round(n * k) / k;
	};
	
	// Fix for IE parseFloat(0.55).toFixed(0) = 0;
  	s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	if (s[0].length > 3) {
		s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
	}
	
	if ((s[1] || '').length < prec) {
		s[1] = s[1] || '';
		s[1] += new Array(prec - s[1].length + 1).join('0');
	}
	
	return s.join(dec);
};

let DateFormat = function(dateVal) {
	if (dateVal.length == 6)
		return dateVal.substring(0, 4) + "." + dateVal.substring(4, 6);
    else if (dateVal.length == 8)
		return dateVal.substring(0, 4) + "." + dateVal.substring(4, 6) + "." + dateVal.substring(6, 8);
	else if (dateVal.length == 10)
		return dateVal.substring(0, 4) + "." + dateVal.substring(4, 6) + "." + dateVal.substring(6, 8) + " " + dateVal.substring(8, 10) + "h";
	else
		return dateVal;
};

let DateFormatHangul = function(dateVal) {
	if (dateVal.length == 8)
		return dateVal.substring(0, 4) + "년 " + dateVal.substring(4, 6) + "월 " + dateVal.substring(6, 8) + "일";
	else if (dateVal.length == 10)
		return dateVal.substring(0, 4) + "년 " + dateVal.substring(4, 6) + "월 " + dateVal.substring(6, 8) + "일 " + dateVal.substring(8, 10) + "시 ";
	else if (dateVal.length == 12)
		return dateVal.substring(0, 4) + "년 " + dateVal.substring(4, 6) + "월 " + dateVal.substring(6, 8) + "일 " + dateVal.substring(8, 10) + "시 " + dateVal.substring(10, 12) + "분";
	else if (dateVal.length == 14)
		return dateVal.substring(0, 4) + "년 " + dateVal.substring(4, 6) + "월 " + dateVal.substring(6, 8) + "일 " + dateVal.substring(8, 10) + "시 " + dateVal.substring(10, 12) + "분 " + dateVal.substring(12, 14) + "초";
	else if (dateVal.length == 16)
		return dateVal.substring(0, 4) + "년 " + dateVal.substring(4, 6) + "월 " + dateVal.substring(6, 8) + "일 " + dateVal.substring(8, 10) + "시 " + dateVal.substring(10, 12) + "분 " + dateVal.substring(12, 14) + "초 " + dateVal.substring(14, 16);
	else
		return dateVal;
};

// Feedback Util
let feedback = {
	init : function() {
		$('.card-form input').each(function(index, item) {
		    if ($(this).hasClass('is-invalid'))
		        $(this).removeClass('is-invalid');
		});
		
		$('.card-form .invalid-feedback').each(function(index, item) {
		    if($(this).css('display') == 'block')
		        $(this).css('display', 'none');
		});
	},
	inputCheck : function(input) {
		let inputValue = input.val();
		
		if (inputValue == null || inputValue == undefined || inputValue.replace(/ /g, '') == '') {
			if (!input.hasClass('is-invalid'))
				input.addClass('is-invalid');
			$('#input-' + input.attr('id') + '-feedback').css('display', 'block');

			input.val('');
			input.focus();

			return false;
		} else
			return true;
	},
	alphabetCheck : function(input) {
		let pattern = /[a-zA-Z]/;
		let inputValue = input.val();

		if (!pattern.test(inputValue)) {
			if (!input.hasClass('is-invalid'))
				input.addClass('is-invalid');

			$('#alp-' + input.attr('id') + '-feedback').css('display', 'block');

			input.val('');
			input.focus();

			return false;
		} else
			return true;
	},
	nonSpecialCharCheck : function(input) {
		let pattern = /[~!@#$%^&*()_+|<>?:{}]/;//;
		let inputValue = input.val();

		if (pattern.test(inputValue)) {
			if (!input.hasClass('is-invalid'))
				input.addClass('is-invalid');

			$('#speChar-' + input.attr('id') + '-feedback').css('display', 'block');

			input.val('');
			input.focus();

			return false;
		} else
			return true;
	},
	wrongCheck : function(input) {
		if (!input.hasClass('is-invalid'))
			input.addClass('is-invalid');

		input.css('display', 'block');

		input.attr('id').split("-").forEach(function(listCol, index) {
			if (listCol != 'wrong' && listCol != 'feedback') {
				if (index == 1)
					$('#' + listCol).focus();
				$('#' + listCol).val('');
			}
		});
	}
};

// Chart Util
let ChartUtil = {
	bar : function(_target, _labels, _data, _max) {
		new Chart(_target, {
			type : 'bar',
			data : {
				labels : _labels,
				datasets : _data
			},
			options : {
				maintainAspectRatio : false,
				scales : {
					xAxes : [{
		                gridLines : {
							display : false,
							drawBorder : false
						},
						ticks : {
							maxTicksLimit : (_labels.length/1),
							callback : function (value, index, values) {
								return DateFormat(value + "");
							}
						}
					}],
					yAxes : [{
						ticks : {
							min : 0,
							max : _max,
							maxTicksLimit : 5,
							padding : 10,
							callback : function (value, index, values) {
								return NumberFormat(value + "");
							}
						},
						gridLines : {
							color : "rgb(234, 236, 244)",
							zeroLineColor: "rgb(234, 236, 244)",
		                    drawBorder : false,
		                    borderDash : [2],
		                    zeroLineBorderDash : [2]
		                }
		            }],
		        },
		        legend : {
		            display : false
		        },
		        tooltips : {
		            titleMarginBottom : 10,
		            titleFontColor : '#6e707e',
		            titleFontSize : 14,
		            backgroundColor : "rgb(255,255,255)",
		            bodyFontColor : "#858796",
		            borderColor : '#dddfeb',
		            borderWidth : 1,
		            xPadding : 15,
		            yPadding : 15,
		            titleAlign : 'center',
		            footerAlign : 'center',
		            bodyAlign : 'center',
		            displayColors : false,
		            caretPadding : 10,
		            callbacks : {
		            	title : function (tooltipItem, chart) {
		                    return DateFormat(tooltipItem[0].xLabel);
		                },
		                label : function (tooltipItem, chart) {
		                    return NumberFormat(tooltipItem.yLabel);
		                }
		            }
		        }
		    }
		});
	},
	doughnut : function(_target, _labels, _data, _bgColor, _bdColor) {
		new Chart(_target, {
			type : 'doughnut',
			data : {
				labels : _labels,
				datasets : [{
					data : _data,
					backgroundColor : _bgColor,
					borderColor : _bdColor,
					borderWidth : 1
				}]
			},
			options: {
				maintainAspectRatio: false,
				tooltips: {
					backgroundColor: "rgb(255,255,255)",
					bodyFontColor: "#858796",
					borderColor: '#dddfeb',
					borderWidth: 1,
					titleFontSize: 16,
					titleFontColor: '#0066ff',
					xPadding: 15,
					yPadding: 15,
					displayColors: false,
					caretPadding: 10,
					callbacks: {
						title: function(tooltipItem, data) {
							return data['labels'][tooltipItem[0]['index']];
						},
						label: function(tooltipItem, data) {
							let item = data.datasets[0].data[tooltipItem.index];
							let total = 0;
							data.datasets[0].data.forEach(function(i) { total += i; })
							return item + " ( " + Math.round(item * 100 / total) + "% )";
						}
					}
				},
				legend: {
					display: false
				},
				cutoutPercentage: 80
			}
		});
	},
	line : function(_target, _labels, _data, _max) {
		new Chart(_target, {
			type : 'line',
			data : {
				labels : _labels,
				datasets : _data
			},
			options : {
				maintainAspectRatio : false,
				scales : {
					xAxes : [{
		                gridLines : {
							display : false,
							drawBorder : false
						},
						ticks : {
							maxTicksLimit : 35,
							callback : function (value, index, values) {
								return DateFormat(value + "");
							}
						}
					}],
					yAxes : [{
						ticks : {
							min : 0,
							max : _max,
							maxTicksLimit : 5,
							padding : 10,
							callback : function (value, index, values) {
								return NumberFormat(value + "");
							}
						},
						gridLines : {
							color : "rgb(234, 236, 244)",
							zeroLineColor: "rgb(234, 236, 244)",
		                    drawBorder : false,
		                    borderDash : [2],
		                    zeroLineBorderDash : [2]
		                }
		            }],
		        },
		        legend : {
		            display : false
		        },
		        tooltips : {
		            titleMarginBottom : 10,
		            titleFontColor : '#6e707e',
		            titleFontSize : 14,
		            backgroundColor : "rgb(255,255,255)",
		            bodyFontColor : "#858796",
		            borderColor : '#dddfeb',
		            borderWidth : 1,
		            xPadding : 15,
		            yPadding : 15,
		            titleAlign : 'center',
		            footerAlign : 'center',
		            bodyAlign : 'center',
		            displayColors : false,
		            caretPadding : 10,
		            callbacks : {
		            	title : function (tooltipItem, chart) {
		                    return DateFormat(tooltipItem[0].xLabel);
		                },
		                label : function (tooltipItem, chart) {
		                    return NumberFormat(tooltipItem.yLabel);
		                }
		            }
		        }
		    }
		});
	},
};

let blankToNull = function(str) {
	if (str != null && str != undefined)
		return str;
	else
		return '-';
};

let loading = function() {
	let loading = false;
	let interval;

	function move() {
		let text = $('mask strong').text();
		let count = 0;
		let pos = text.indexOf('.');

		while (pos !== -1) {
			count++;
			pos = text.indexOf('.', pos + 1);
		}

		if (count < 3)
			$('mask strong').text($('mask strong').text() + '.');
		else
			$('mask strong').text('Loading');

	}

	this.start = function() {
		$('mask').css('display', 'flex');
		interval = setInterval(move, 400);
	};

	this.stop = function() {
		$('mask').css('display', 'none');
		if (interval != undefined)
			clearInterval(interval);
	};
};

/*let sleep = {
	async : function(ms) {
		return new Promise((r) => setTimeout(r, ms));
	},
	sync : function(ms) {
		const wakeUpTime = Date.now() + ms;
		while (Date.now() < wakeUpTime) {}
	}
};*/

let parseClassification = function(val) {
	let parseVal = "";
	
	if (val == 'SP') {
		parseVal = '분리보관';
	} else if (val == 'RV') {
		parseVal = '복원';
	} else if (val == 'DT') {
		parseVal = '파기';
	} else {
		parseVal = '기타';
	}
	
	return parseVal;
};

let parseStatus = function(val) {
	let parseVal = '';
	
	if (val == '00') {
		parseVal = '대기';
	} else if (val == '01') {
		parseVal = '성공';
	} else if (val == '09') {
		parseVal = '실패';
	} else {
		parseVal = '기타';
	}
	
	return parseVal;
};

String.prototype.startsWith = function(str) {
	if (this.length < str.length) { return false; }
	return this.indexOf(str) == 0;
}

String.prototype.endsWith = function(str) {
	if (this.length < str.length) { return false; }
	return this.lastIndexOf(str) + str.length == this.length;
}