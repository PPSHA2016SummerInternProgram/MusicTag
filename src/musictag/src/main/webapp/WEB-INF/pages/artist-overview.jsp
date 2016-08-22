<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/release.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/albums.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/artist-overview.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/artist.js"></script>
<script src="<%=request.getContextPath()%>/js/albums.js"></script>
<script src="<%=request.getContextPath()%>/js/tag_builder.js"></script>
<script src="<%=request.getContextPath()%>/js/paginator.js"></script>
<script src="<%=request.getContextPath()%>/js/statistics.js"></script>
</head>
<body>

	<%
		int active_tab = 0;
	%>
	<%@ include file="_navbar.html"%>
	<%@ include file="_artist_header.jsp"%>
	<div class="container">
		<div class="artist-overview" data-artist-overview
			style="padding-top: 0;">
			<div style="height: 0">
				<div id=""
					style="position: relative; float: left; left: 30px; top: 70px; width: 100px; height: 100px;"></div>
			</div>
			<div class="artist-overview-basic-info">
				<!-- <div class="container"></div> -->
				<div style="margin-top: -30px;">
					<div class="artist-overview-key">Gender:</div>
					<div class="artist-overview-value" data-artist-overview-gender></div>
					<div class="artist-overview-key">Area:</div>
					<div class="artist-overview-value" data-artist-overview-area></div>


					<div class="artist-overview-key">Profile:</div>
					<div class="artist-overview-value">
						<div class="artist-overview-profile-less"
							id="artist-overview-profile" data-artist-overview-profile>
						</div>
						<div class="readmore_toggler">
							<span class="readmore_single_toggler"
								id="artist-overview-profile-read-more">More <i
								style="font-size: 10px"
								class="glyphicon glyphicon-triangle-bottom"></i></span> <span
								class="readmore_single_toggler" style="display: none;"
								id="artist-overview-profile-read-less">Less <i
								style="font-size: 10px;"
								class="glyphicon glyphicon-triangle-top"></i>
							</span>
						</div>
					</div>
					<div class="artist-overview-key">Life Span:</div>
					<div class="artist-overview-value" data-artist-overview-life-span>1979-01-18~?</div>
					<div class="artist-overview-key" style="width: 100%; display: none"
						data-artist-listeners-char>
						<div style="float: left" data-artist-listeners-chart-key>周杰伦
							beats 97% artists:</div>
						<div class="artist-overview-value"
							style="margin-left: 10px; float: left">
							<div class="readmore_toggler">
								<span class="readmore_single_toggler"
									id="artist-hot-chart-read-more">See More <i
									style="font-size: 10px"
									class="glyphicon glyphicon-triangle-bottom"></i></span> <span
									class="readmore_single_toggler" style="display: none;"
									id="artist-hot-chart-read-less">Hide <i
									style="font-size: 10px;"
									class="glyphicon glyphicon-triangle-top"></i>
								</span>
							</div>
						</div>
					</div>

					<div class="artist-overview-key" data-artist-listeners-chart-value
						style="width: 110%; margin-left: -100px; height: 0; overflow: hidden"
						id="artist-radar"></div>
					<div style="clear: both"></div>
					<div class="artist-overview-key" style="margin-top: 5px;">
						<button class="btn btn-sm" style="margin-left: -10px"
							data-artist-overview-link-btn="hide">Show Links</button>
					</div>
					<div class="artist-overview-value"
						style="padding: 10px; display: none;" data-artist-overview-links>
						<ul class="list-group" data-artist-overview-links-wrapper>
							<li class="list-group-item"><label
								class="artist-overview-link-label">IMDb</label><a href="">http://www.imdb.com/name/nm1727100/</a></li>
							<li class="list-group-item"><label
								class="artist-overview-link-label">VIAF</label><a href="">http://viaf.org/viaf/86517081</a></li>
							<li class="list-group-item"><label
								class="artist-overview-link-label">discogs</label><a href="">http://www.discogs.com/artist/1705275</a></li>
							<li class="list-group-item"><label
								class="artist-overview-link-label">last.fm</label><a href="">http://www.last.fm/music/%E5%91%A8%E6%9D%B0%E5%80%AB</a></li>
						</ul>
					</div>
				</div>
				<div style="clear: both"></div>
			</div>
		</div>


		<div id="albums-wrapper" style="display: none">
			<%@ include file="_albums.jsp"%>
		</div>

		<div id="similar-artists" style="display: none">
			<h3>Similar Artists</h3>
			<ol id="similar-artist-list" class="grid-items">
				<li class="grid-items-item">
					<div class="grid-items-item-wrapper">
						<div class="similar-artist-image">
							<img
								src="https://lastfm-img2.akamaized.net/i/u/avatar300s/2e3a9cef44244f31b3382f9ab214e9ea.jpg">
						</div>
						<div class="grid-items-item-details">
							<div>不能说的秘密</div>
						</div>
						<a class="grid-items-item-link" href=""></a>
					</div>
				</li>
				<li class="grid-items-item">
					<div class="grid-items-item-wrapper">
						<div class="similar-artist-image">
							<img
								src="https://lastfm-img2.akamaized.net/i/u/avatar300s/43d0c438db3c401aab783461a388e9d6.jpg">
						</div>
						<div class="grid-items-item-details">
							<div>不能说的秘密</div>
						</div>
						<a class="grid-items-item-link" href=""></a>
					</div>
				</li>
				<li class="grid-items-item">
					<div class="grid-items-item-wrapper">
						<div class="similar-artist-image">
							<img
								src="http://img2-ak.lst.fm/i/u/avatar300s/68467618652143bb9817c0fc366fe18a.jpg">
						</div>
						<div class="grid-items-item-details">
							<div>不能说的秘密</div>
						</div>
						<a class="grid-items-item-link" href=""></a>
					</div>
				</li>
			</ol>
		</div>

	</div>

</body>
</html>
