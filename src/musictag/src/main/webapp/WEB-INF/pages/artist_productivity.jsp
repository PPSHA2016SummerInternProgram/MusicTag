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
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/artist_release_dist.js"></script>
</head>
<body>
	<%
		int active_tab = 4;
	%>
	<%@ include file="_navbar.html"%>
	<%@ include file="_artist_header.jsp"%>

	<div class="container">
		<div id="artist-release-dist"></div>

		<div>
			<div id="recording_amount"></div>
			<div id="release_amount"></div>
			<script>
				$(function() {
					drawDistribution('recording_amount', 'recording_amount',
							'Recording Amount Distribution');
					drawDistribution('release_amount', 'release_amount',
							'Release Amount Distribution');
				});
			</script>
		</div>
	</div>
</body>
</html>