<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
</head>

<body>

	<h3>Click the following links, you will send some AJAX requests to
		server.</h3>
	<ul>
		<li><a href="javascript:void(0)">hello</a></li>
		<li><a href="javascript:void(0)">+</a></li>
		<li><a href="javascript:void(0)">-</a></li>
		<li><a href="javascript:void(0)">*</a></li>
		<li><a href="javascript:void(0)">/</a></li>
		<li><a href="javascript:void(0)">wrong operation</a></li>
		<li><a href="javascript:void(0)">no operation</a></li>
	</ul>

	<div>
		<label>Request URL:</label>
		<div id="request"></div>
	</div>
	<br>
	<div>
		<label>Request Data:</label>
		<div id="request-data"></div>
	</div>
	<br>
	<div>
		<label>Response Data:</label>
		<div id="result"></div>
	</div>

	<script>
		$(function(){
			$('li a').on('click', function(){
				var text = $(this).text();
				if(text == 'hello'){
					sendHelloRequest();
				}else{
					sendParamRequest(text);
				}
			});
		});
		
		function sendParamRequest(operation){
			if(operation == 'no operation'){
				operation = null;
			}
			var url = '<%=request.getContextPath()%>/ajax/params';
			var data = {
				a : 1,
				b : 2,
			};
			if(operation){
				data['operation'] = operation;
			}
			sendAjax(url, data);
		}
		
		function sendHelloRequest(){
			var url = '<%=request.getContextPath()%>/ajax/hello';
			sendAjax(url, null);
		}

		function sendAjax(url, requestData) {
			$('#request').text(url);
			$('#request-data').text(JSON.stringify(requestData))
			$('#result').text('waiting response..')
			$.ajax({
				url : url,
				type : 'get',
				dataType : 'json',
				data : requestData,
				success : function(data) {
					$('#result').text(JSON.stringify(data));
				},
				error : function(jqueryXHR) {
					alert('error: ' + jqueryXHR.status);
				}
			})
		}
	</script>
</body>
</html>