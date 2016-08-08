<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/statistics.js"></script>
</head>
<body>
	<h1>Statistics</h1>
	<div id="artist-listeners"></div>
	<div id="artist-playcount"></div>
	<div id="release-listeners"></div>
	<div id="release-playcount"></div>
	<div id="recording-listeners"></div>
	<div id="recording-playcount"></div>
</body>
</html>