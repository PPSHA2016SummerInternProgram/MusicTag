<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.css" />
<style type="text/css">
.artist-image {
	width: 160px;
	float: left;
	padding-top: 10px;
	margin-right: 10px;
}

.artist-overview-name {
	font-size: 20px;
	font-weight: bold;
}

.artist-overview-key {
	width: 80px;
	float: left;
}

.artist-overview-value {
	margin-left: 80px;
}

.artist-overview-profile-less {
	overflow: hidden;
	max-height: 140px;
}

.readmore_toggler {
	background: white;
	color: #868379;
	display: block;
	height: 20px;
}

.readmore_toggler:hover {
	background: white;
	color: black;
	display: block;
	height: 20px;
}

.readmore_single_toggler:hover {
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="container">
		<div style="padding-top: 30px">
			<div class="artist-image">
				<img width="160"
					src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Jay_Chou_in_Seoul.jpg/220px-Jay_Chou_in_Seoul.jpg">
			</div>
			<div style="margin-left: 170px; margin-right: 100px">
				<p class="artist-overview-name">周杰倫</p>
				<div class="artist-overview-key">Gender:</div>
				<div class="artist-overview-value">Male</div>
				<div class="artist-overview-key">Area:</div>
				<div class="artist-overview-value">Taiwan</div>
				<div class="artist-overview-key">Profile:</div>
				<div class="artist-overview-value">
					<div class="artist-overview-profile-less"
						id="artist-overview-profile">
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
						produce songs that fuse R&B, rock and pop genres, covering issues
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
				<div class="artist-overview-value">1979-01-18~?</div>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			var profile = $('#artist-overview-profile');
			var more = $('#artist-overview-profile-read-more');
			var less = $('#artist-overview-profile-read-less');
			var hiddenClass = 'artist-overview-profile-less';
			more.on('click', function() {
				profile.removeClass(hiddenClass);
				more.hide();
				less.show();
			});
			less.on('click', function() {
				profile.addClass(hiddenClass);
				more.show();
				less.hide();
			});
		});
	</script>
</body>
</html>
