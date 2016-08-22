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
<script src="<%=request.getContextPath()%>/js/artist-edit.js"></script>
</head>
<body>
	<%
		int active_tab = 2;
	%>
	<%@ include file="_navbar.html"%>
	<%@ include file="_artist_header.jsp"%>

	<div class="container">
		<div id="artist-edit" style="width: 100%; height: 400px;"></div>

		<div style="margin-top: 100px">
			<p>
				This is the distribution chart of Edit Amount.<br>We can see
				how many persons edited the artist.
			</p>
			<div id="edit_amount"></div>
			<p>
				There are the distribution charts of Listener Amount and Play
				Amount. The data is from last.fm<br>We can see how many persons
				listened the artist.
			</p>
			<div id="listener_amount"></div>
			<div id="play_amount"></div>
			<script>
				$(function() {
					drawDistribution('edit_amount', 'edit_amount',
							'Edit Amount Distribution');
					drawDistribution('listener_amount', 'listener_amount',
							'Listener Amount Distribution');
					drawDistribution('play_amount', 'play_amount',
							'Play Amount Distribution');
				});
			</script>
		</div>
	</div>
</body>
</html>