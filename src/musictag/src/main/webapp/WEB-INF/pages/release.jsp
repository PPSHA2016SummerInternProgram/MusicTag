<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/release.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/release_cover_builder.js"></script>
<script src="<%=request.getContextPath()%>/js/release.js"></script>
<script src="<%=request.getContextPath()%>/js/statistics.js"></script>
</head>
<body>

	<%@ include file="_navbar.html"%>
	<div class="artist-background">
		<div class="artist-background-top"></div>
	</div>
	<div class="container release-container">
		<div style="height: 120px;">
			<div class="release-cover img-circle-wrapper">
				<img width="160" class="avatar" data-release-cover
					src="<%=request.getContextPath()%>/images/default_album_cover.jpg">
			</div>
			<div class="release-info">
				<div class="artist-name" data-artist-name>周杰倫</div>
				<div class="release-name" data-release-name>七里香</div>
				<div class="date" data-date>2004</div>
				<div class="rate" data-rate>
					<span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span>
				</div>
			</div>
		</div>
		<div style="height: 0; margin-left: 300px;">
			<div id="release-hot-div" style="padding-top: 30px; display: none">
				<span style="padding-top: 30px;" id="release-hot-rank-hint">beats
					100% releases:</span> <span class="artist-overview-value"> <span
					class="readmore_toggler"> <span
						class="readmore_single_toggler" id="release-hot-chart-read-more">See
							More <i style="font-size: 10"
							class="glyphicon glyphicon-triangle-bottom"></i>
					</span> <span class="readmore_single_toggler" style="display: none;"
						id="release-hot-chart-read-less">Hide <i
							style="font-size: 10;" class="glyphicon glyphicon-triangle-top"></i>
					</span>
				</span>
				</span>
			</div>
		</div>
		<div id="release-listeners"
			style="margin-top: 0px; height: 0; overflow: hidden; margin-left: 0px"></div>
		<div style="max-width: 840px;">
			<h2 style="margin-left: 165px">Tracklist</h2>
			<table class="table track-table" style="display: none;"
				data-track-table>
				<tr>
					<td class="id">1</td>
					<td class="name" data-name>我的地盤
						<div class="detail" style="display: none;">
							<div>
								arranger: <a>林邁可</a>
							</div>
							<div>
								background vocals and lead vocals: <a>林邁可</a>
							</div>
							<div>
								background vocals arranger: <a>周杰倫</a>
							</div>
							<div>
								mixer: <a>林邁可</a>
							</div>
							<div>
								recorded by: <a>楊瑞代</a>
							</div>
							<div>
								lyricist: <a>方文山</a>
							</div>
						</div>
					</td>
					<td class="more" data-more="true"><a style="display: none;">more
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a></td>
					<td class="time">3:59</td>
					<td class="rate"><span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span></td>
					<td class="play-amount">1000</td>
				</tr>
				<tr>
					<td class="id">1</td>
					<td class="name" data-name>我的地盤
						<div class="detail" style="display: none;">
							<div>
								arranger: <a>林邁可</a>
							</div>
							<div>
								background vocals and lead vocals: <a>林邁可</a>
							</div>
							<div>
								background vocals arranger: <a>周杰倫</a>
							</div>
							<div>
								mixer: <a>林邁可</a>
							</div>
							<div>
								recorded by: <a>楊瑞代</a>
							</div>
							<div>
								lyricist: <a>方文山</a>
							</div>
						</div>
					</td>
					<td class="more" data-more="true"><a style="display: none;">more
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a></td>
					<td class="time">3:59</td>
					<td class="rate"><span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span></td>
					<td class="play-amount">1000</td>
				</tr>
				<tr>
					<td class="id">1</td>
					<td class="name" data-name>我的地盤
						<div class="detail" style="display: none;">
							<div>
								arranger: <a>林邁可</a>
							</div>
							<div>
								background vocals and lead vocals: <a>林邁可</a>
							</div>
							<div>
								background vocals arranger: <a>周杰倫</a>
							</div>
							<div>
								mixer: <a>林邁可</a>
							</div>
							<div>
								recorded by: <a>楊瑞代</a>
							</div>
							<div>
								lyricist: <a>方文山</a>
							</div>
						</div>
					</td>
					<td class="more" data-more="true"><a style="display: none;">more
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a></td>
					<td class="time">3:59</td>
					<td class="rate"><span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span></td>
					<td class="play-amount">1000</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>