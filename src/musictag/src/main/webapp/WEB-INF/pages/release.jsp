<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/release.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/js/util.js"></script>
<script src="<%=request.getContextPath()%>/js/release.js"></script>
</head>
<body>

	<%@ include file="_navbar.html"%>
	<div class="artist-background">
		<div class="artist-background-top"></div>
	</div>
	<div class="container release-container">
		<div>
			<div class="release-cover">
				<img width="160" class="avatar"
					src="http://img2-ak.lst.fm/i/u/174s/8cc09636be566a39be4a2c1d2a4aae20.jpg">
			</div>
			<div class="release-info">
				<div class="artist-name">周杰倫</div>
				<div class="release-name">七里香</div>
				<div class="date">2004</div>
				<div class="rate">
					<span class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span><span
						class="glyphicon glyphicon-star-empty"></span>
				</div>
			</div>
		</div>
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