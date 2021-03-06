<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/artist_header.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/release.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/albums.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/artist-overview.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script src="<%=request.getContextPath()%>/js/world.js"></script>
<script src="<%=request.getContextPath()%>/js/artist-area.js"></script>
</head>
<body>
	<%
		int active_tab = 3;
	%>
	<%@ include file="_navbar.html"%>
    <%@ include file="_artist_header.jsp"%>

    <div class="container" >
        <div id="artist-area" style="width: 100%; height: 400px;"></div>
		<div id="artist-area-data"></div>		
		<div>
			<div id="country_amount"></div>
			<script>
				$(function() {
					drawDistribution('country_amount', 'country_amount',
							'Country Amount Distribution');
				});
			</script>
		</div>
	</div>
</body>
</html>