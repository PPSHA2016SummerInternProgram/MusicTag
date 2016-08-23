<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/release.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/song.css">

<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>

<script src="<%=request.getContextPath()%>/js/release_cover_builder.js"></script>
<script src="<%=request.getContextPath()%>/js/song.js"></script>
<script src="<%=request.getContextPath()%>/js/lyric.js"></script>
</head>

<body>
	<%@ include file="_navbar.html"%>
 	<div class="artist-background">
 		<div class="artist-background-top"></div>
 	</div>
	<div class = "container song-container">
 		<div style="height:120px">
 			<div class="release-cover img-circle-wrapper" >
 				<img width="160" class="avatar" data-release-cover
 					src="<%=request.getContextPath()%>/images/default_album_cover.jpg">
 			</div>
 			<div class="release-info">
 				<div class="artist-name" data-artist-name>Carpenters</div>
 				<div class="release-name" data-recording-name>Yesterday Once More</div>
 				<div class="date" data-song-length>3:00</div>
				<div class="rate" data-rate>
					<span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span>
				</div>
 			</div>
 		</div>

		<div class="song-basic-info">
			<h2>Basic Info</h2>
			<div class="song-key">Style:</div>
			<!-- recording/{mbid}?inc=tags-->
			<div class="song-value" data-song-style>Pop, Country</div>
			<div class="song-key">Producer:</div>
			<div class="song-value" data-song-producer>AAA</div>
			<div class="song-key">Composer:</div>
			<div class="song-value" data-song-composer>BBB</div>
			<div class="song-key">Lyricist:</div>
			<div class="song-value" data-song-lyricist>CCC</div>
			<div class="song-key">Publisher:</div>
			<div class="song-value" data-song-publisher>DDD</div>
			<div class="song-key">Later translated versions:</div>
			<div class="song-value" data-song-versions>version1...</div>
		</div>

		<div class="lyric-wrap">
            <h2>Lyrics</h2>
			<div class="lyric-box"></div>
		</div>

		<div class="song-releases-info">
			<h2>Appears on releases</h2>
			<div class="table-responsive ">
				<table class="table">
					<thead>
						<tr>
							<th>Release Title</th>
							<th>Release Artist</th>
							<th>Date</th>
							<th>Country</th>
						</tr>	
					</thead>
					<tbody data-song-releases style="display: none">
						<tr>
							<td>Classics, Volume 2</td>
							<td>Carpenters</td>
							<td>1987</td>
							<td>United States</td>
						</tr>
						<tr>
							<td>Classics, Volume 2Classics</td>
							<td>Various Artists</td>
							<td>1987</td>
							<td>United States</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>