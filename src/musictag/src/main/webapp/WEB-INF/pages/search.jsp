<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/release.css" />
<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/albums.css" />
<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/artist-overview.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/albums.js"></script>
<script src="<%=request.getContextPath()%>/js/tag_builder.js"></script>
<script src="<%=request.getContextPath()%>/js/paginator.js"></script>

</head>
<body>

	<%@ include file="_navbar.html"%>
	<div class="container" style="margin-top:80px;">
			<div class="container">
				<form class="navbar-form" style="text-align:center;" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
	
		<div class="search_count">
			搜索“lin”，找到 9999 首单曲
		</div>
		<ul class="nav nav-tabs">
  			<li role="presentation" class="active"><a href="#">Trackings</a></li>
  			<li role="presentation"><a href="#">Artists</a></li>
  			<li role="presentation"><a href="#">Albums</a></li>
		</ul>


		<div class="trackingTable" style="max-width: 1140px;">
			<h2 style="margin-left: 165px">  </h2>
			<table class="table track-table" data-track-table>
				<tr>
					<td class="id">1</td>
					<td class="name" style="width:500px" data-name>我的地盤
					</td>
					<td class="artistname">Jay Chou</td>
					<td class="albumname">《我很忙》</td>
					<td class="time">3:59</td>
				</tr>
				<tr>
					<td class="id">1</td>
					<td class="name" data-name>我的地盤
					</td>
					<td class="artistname">Jay Chou</td>
					<td class="albumname">《我很忙》</td>
					<td class="time">3:59</td>
				</tr>
				<tr>
					<td class="id">1</td>
					<td class="name" data-name>我的地盤
					</td>
					<td class="artistname">Jay Chou</td>
					<td class="albumname">《我很忙》</td>
					<td class="time">3:59</td>
				</tr>
				
			</table>
		</div>

	
		

	</div>

</body>
</html>