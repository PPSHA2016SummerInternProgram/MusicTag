<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/artist-overview.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>

<script src="<%=request.getContextPath()%>/js/tag_builder.js"></script>
<script src="<%=request.getContextPath()%>/js/paginator.js"></script>
</head>


<body>
	<div class = "container">
		<div class = "song_brief">
			<div class="artist-overview-basic-info">
				<div class="artist-overview-key">Style:</div>
				<div class="artist-overview-value" data-artist-overview-gender>Pop, Country</div>
				<div class="artist-overview-key">Publisher:</div>
				<div class="artist-overview-value" data-artist-overview-area>AAA</div>
				<div class="artist-overview-key">writer:</div>
				<div class="artist-overview-value" data-artist-overview-area>BBB</div>
				<div class="artist-overview-key">Publisher:</div>
				<div class="artist-overview-value" data-artist-overview-area>AAA</div>
				
			</div>
		</div>
	</div>
</body>
</html>