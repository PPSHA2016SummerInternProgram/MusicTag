<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
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
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid-new nopadding-nomargin">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="container">
				<div class="span3">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand nopadding-left" href="#">OmniMusic</a>
					</div>
				</div>

				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<form class="navbar-form navbar-left" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search">
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">Link</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">Dropdown <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="#">Separated link</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container">
		<div class="artist-overview" data-artist-overview style="margin-top:50px;">
			<div class="artist-image">
				<img width="160" data-artist-overview-image
					src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Jay_Chou_in_Seoul.jpg/220px-Jay_Chou_in_Seoul.jpg">
			</div>
			<div class="artist-overview-basic-info">
				<p class="artist-overview-name" data-artist-overview-name>周杰倫</p>
				<div class="artist-overview-key">Gender:</div>
				<div class="artist-overview-value" data-artist-overview-gender>Male</div>
				<div class="artist-overview-key">Area:</div>
				<div class="artist-overview-value" data-artist-overview-area>Taiwan</div>
				<div class="artist-overview-key">Profile:</div>
				<div class="artist-overview-value">
					<div class="artist-overview-profile-less"
						id="artist-overview-profile" data-artist-overview-profile>
						Jay Chou (traditional Chinese: 周杰倫; simplified Chinese: 周杰伦;
						pinyin: Zhōu Jiélún; Wade–Giles: Chou Chieh-lun; Pe̍h-ōe-jī: Chiu
						Kia̍t-lûn; born January 18, 1979) is a Taiwanese musician,
						singer-songwriter, music and film producer, actor and director who
						has won the World Music Award four times. He is well-known for
						composing all his own songs and songs for other singers. In 1998
						he was discovered in a talent contest where he displayed his piano
						and song-writing skills. Over the next two years, he was hired to
						compose for popular Mandarin singers. Although he was trained in
						classical music, Chou combines Chinese and Western music styles to
						produce songs that fuse R B, rock and pop genres, covering issues
						such as domestic violence, war, and urbanization. <br> <br>
						In 2000, Chou released his first album, titled Jay, under the
						record company Alfa Music. Since then he has released one album
						per year except in 2009, selling several million copies each. His
						music has gained recognition throughout Asia, most notably in
						regions such as Taiwan, China, Hong Kong, Japan, Malaysia,
						Indonesia, India, Singapore, Thailand, Vietnam and in overseas
						Asian communities, winning more than 20 awards each year. He has
						sold more than 28 million albums worldwide up to 2010.[2] He
						debuted his acting career in Initial D (2005), for which he won
						Best Newcomer Actor in both the Hong Kong Film Awards and the
						Golden Horse Awards, and was nominated for Best Supporting Actor
						by Hong Kong Film Awards for his role in Curse of the Golden
						Flower (2006). He produced the theme song for the film Ocean
						Heaven starring Jet Li. His career now extends into directing and
						running his own record company JVR Music.
					</div>
					<div class="readmore_toggler">
						<span class="readmore_single_toggler"
							id="artist-overview-profile-read-more">More <i
							style="font-size: 10" class="glyphicon glyphicon-triangle-bottom"></i></span>
						<span class="readmore_single_toggler" style="display: none;"
							id="artist-overview-profile-read-less">Less <i
							style="font-size: 10;" class="glyphicon glyphicon-triangle-top"></i>
						</span>
					</div>
				</div>
				<div class="artist-overview-key">Life Span:</div>
				<div class="artist-overview-value" data-artist-overview-life-span>1979-01-18~?</div>
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
		<div id="albums-wrapper" style="display: none">
			<%@ include file="_albums.html"%>
		</div>
	</div>


</body>
</html>
