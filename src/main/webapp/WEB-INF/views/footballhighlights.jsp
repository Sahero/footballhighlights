<%@  taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<%@  taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/highlights.css">

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/icon/favicon.ico"
	type="image/x-icon">
<title>Football Highlights</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-1.4.1.min.js"></script>

<!-- REQUIRED FOR PARALLAX -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.jparallax.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.event.frame.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/magni-pop.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/magnific-popup.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/captcha.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/contact-form.css">


<script type="text/javascript">
	jQuery(document).ready(
			function() {
				$('#parallax .parallax-layer').parallax({
					mouseport : $('#parallax')
				});

				$('.popup-video').magnificPopup({
					disableOn : 700,
					type : 'iframe',
					mainClass : 'mfp-fade',
					removalDelay : 160,
					preloader : false,

					fixedContentPos : false
				});

				$('.item').each(
						function() {
							var url = $(this).data('id');
							var source= $(this).data('source');
							var bgUrl;
							//console.log(source);
							if(source.toLowerCase()=="youtube"){
								//console.log("--"+url);
								var a = url.split("v=")[1];
								a = a !== undefined ? a : url.split("youtu.be/")[1];
								bgUrl = 'http://img.youtube.com/vi/' + a.split("&")[0]	+ '/maxresdefault.jpg';
								$(this).find('.ytp-thumbnail').css(
										'background-image','url('+bgUrl+')');
							}
							else if(source.toLowerCase()=="dailymotion"){
								var m = url
								.match(/^.+dailymotion.com\/embed\/((video|hub)\/([^_]+))?[^#]*(#video=([^_&]+))?/);
								var id = m ? m[5] || m[3] : null;
								//console.log($(this).attr('class'));
								var elem = $(this);
					              $.ajax({
					                type:     "GET",
					                url:      "https://api.dailymotion.com/video/"+id+"?fields=thumbnail_url",
					                dataType: "jsonp",
					                success: function(data){
					                  bgUrl=data['thumbnail_url'];
					                  //console.log($(elem).attr('class'));
					                  $(elem).find('.ytp-thumbnail').css(
												'background-image','url('+bgUrl+')');
					                }
					            });  
							}
							else{
								$(this).find('.ytp-thumbnail').css(
										'background','white');
							}
								
						});

				$('.popup-with-form').magnificPopup({
					type : 'inline',
					preloader : false,
					focus : '#name',
					callbacks : {
						beforeOpen : function() {
							if ($(window).width() < 700) {
								this.st.focus = false;
							} else {
								this.st.focus = '#name';
							}
						}
					}
				});

			});

</script>

</head>
<body>
	<div class="scontainer">

		<!-- <p style="color: white">What I do is play soccer which is what I
			like.</p> -->
		<!-- REQUIRED FOR PARALLAX -->
		<div id="parallax" class="clear">
			<div class="parallax-layer" style="width: 1200px;">
				<img
					src="${pageContext.request.contextPath}/resources/img/background.png"
					alt="" style="width: 1200px;" />
			</div>
			<div class="parallax-layer" style="width: 30px; height: 30px;">
				<img
					src="${pageContext.request.contextPath}/resources/img/soccer.png"
					alt="" style="width: 30px; height: 30px;" />
			</div>
		</div>
		<!-- /REQUIRED FOR PARALLAX -->
		<div class="container">
			<table style="width: 100%">
				<tr>
					<td align="left"><h1 style="text-align: left">Football
							Videos</h1></td>
					<td align="right"><c:forEach varStatus="p" begin="1"
							end="${totalPages}">
							<a style="color: white"
								href="${pageContext.request.contextPath}/home?pg=${p.count}">${p.count}</a>
						</c:forEach></td>
					<td align="right"><form:form action="search"
							modelAttribute="search" method="POST">
							<table>
								<tr>
									<td><form:input path="usingAll" /></td>
									<td><input type="image" title="Filter"
										src="${pageContext.request.contextPath}/resources/img/icon/search.png"
										alt="Submit Form" /></td>

								</tr>
							</table>
						</form:form></td>
					<td align="right"><a
						href="${pageContext.request.contextPath}/home"><img
							title="Reset filter"
							src="${pageContext.request.contextPath}/resources/img/icon/reset.png" /></a></td>

				</tr>
			</table>
			<div class="grid">
				<c:forEach var="footballHighlights" items="${footballHighlights}"
					varStatus="loop">

					<div class="item ${loop.count % 2==0?'odd':'even'}"
					 	data-source="${footballHighlights.videoSource}"
						data-id="${footballHighlights.videoURL}">
						<div class="ytp-thumbnail">
							<div class="ytp-large-play-button popup-video"
								href="${footballHighlights.videoURL}">
								<svg>
				<path fill-rule="evenodd" clip-rule="evenodd" fill="#1F1F1F"
										class="ytp-large-play-button-svg"
										d="M84.15,26.4v6.35c0,2.833-0.15,5.967-0.45,9.4c-0.133,1.7-0.267,3.117-0.4,4.25l-0.15,0.95c-0.167,0.767-0.367,1.517-0.6,2.25c-0.667,2.367-1.533,4.083-2.6,5.15c-1.367,1.4-2.967,2.383-4.8,2.95c-0.633,0.2-1.316,0.333-2.05,0.4c-0.767,0.1-1.3,0.167-1.6,0.2c-4.9,0.367-11.283,0.617-19.15,0.75c-2.434,0.034-4.883,0.067-7.35,0.1h-2.95C38.417,59.117,34.5,59.067,30.3,59c-8.433-0.167-14.05-0.383-16.85-0.65c-0.067-0.033-0.667-0.117-1.8-0.25c-0.9-0.133-1.683-0.283-2.35-0.45c-2.066-0.533-3.783-1.5-5.15-2.9c-1.033-1.067-1.9-2.783-2.6-5.15C1.317,48.867,1.133,48.117,1,47.35L0.8,46.4c-0.133-1.133-0.267-2.55-0.4-4.25C0.133,38.717,0,35.583,0,32.75V26.4c0-2.833,0.133-5.95,0.4-9.35l0.4-4.25c0.167-0.966,0.417-2.05,0.75-3.25c0.7-2.333,1.567-4.033,2.6-5.1c1.367-1.434,2.967-2.434,4.8-3c0.633-0.167,1.333-0.3,2.1-0.4c0.4-0.066,0.917-0.133,1.55-0.2c4.9-0.333,11.283-0.567,19.15-0.7C35.65,0.05,39.083,0,42.05,0L45,0.05c2.467,0,4.933,0.034,7.4,0.1c7.833,0.133,14.2,0.367,19.1,0.7c0.3,0.033,0.833,0.1,1.6,0.2c0.733,0.1,1.417,0.233,2.05,0.4c1.833,0.566,3.434,1.566,4.8,3c1.066,1.066,1.933,2.767,2.6,5.1c0.367,1.2,0.617,2.284,0.75,3.25l0.4,4.25C84,20.45,84.15,23.567,84.15,26.4z M33.3,41.4L56,29.6L33.3,17.75V41.4z"></path>
				<polygon fill-rule="evenodd" clip-rule="evenodd" fill="#FFFFFF"
										points="33.3,41.4 33.3,17.75 56,29.6"></polygon>
										
				</svg>

							</div>

						</div>
						<div class="html5-info-bar">
							<div class="html5-title">
								<div class="html5-title-text-wrapper">
									<a class="html5-title-text" target="_blank" tabindex="3100"
										href="${footballHighlights.videoURL}">${footballHighlights.title}</a>
								</div>
							</div>
						</div>
						<hr>
						<!-- <div class="loading">Loading..</div> -->
					</div>
				</c:forEach>
			</div>
			<br>
		</div>

		<div class="footer">
			We do not own any of the videos. All the content of these videos are
			hosted on 3rd-party servers. Please <a class="popup-with-form"
				href="#contact">contact us</a> if you would like to remove any of
			your videos.
		</div>

	</div>

	<section id="contact" class="white-popup-block mfp-hide"
		style="background: white">
		<div class="content">
			<div id="form">
				<form action="" id="contactForm" method="post">
					<span>Name</span> <input type="text" name="name" class="name"
						placeholder="Enter your name" tabindex=1 /> <span>Email</span> <input
						type="text" name="email" class="email"
						placeholder="Enter your email" tabindex=2 /> <span id="captcha"></span>
					<input type="text" name="captcha" class="captcha" maxlength="4"
						size="4" placeholder="Enter captcha code" tabindex=3 /> <span>Message</span>
					<textarea class="message" placeholder="Enter your message"
						tabindex=4></textarea>
					<input type="submit" name="submit" value="Send e-mail"
						class="submit" tabindex=5>
				</form>
			</div>
		</div>
	</section>

</body>
</html>