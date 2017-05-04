<%@  taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/icon/favicon.ico"
	type="image/x-icon">
		
<title>Football</title>

</head>
<body>
	<jsp:include page="headerAdmin.jsp" />
	<div id="wrapper">
		<div id="header">
			<table>
				<tr>
					<td><img style="transform: rotate(180deg);"
						src="${pageContext.request.contextPath}/resources/img/icon/soccer-24.png"></td>
					<td><h2>Football Highlights List</h2></td>
					<td><img
						src="${pageContext.request.contextPath}/resources/img/icon/soccer-24.png"></td>
			</table>
		</div>
	</div>


	<div id="container">
		<table style="width: 100%">
			<tr>
				<td><input type="button" class="button"
					value="Add New Highlight"
					onclick="window.location.href='addNewHighlight'; return false;" />
					</td>
				<td align="right"><c:forEach varStatus="p" begin="1"
						end="${totalPages}">
						<a href="${pageContext.request.contextPath}/admin?pg=${p.count}">${p.count}</a>
					</c:forEach></td>
				<td align="right"><form:form action="searchHighlight"
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
					href="${pageContext.request.contextPath}/admin?pg=" ${param.pg}><img
						title="Reset filter"
						src="${pageContext.request.contextPath}/resources/img/icon/reset.png" /></a></td>
			</tr>
		</table>

	</div>
	<div class="content">

		<table>
			<thead>
				<tr>
					<th>S.N</th>
					<th>Modify</th>
					<th>Home Team Name</th>
					<th>Home Team Score</th>
					<th>Away Team Name</th>
					<th>Away Team Score</th>
					<th>Date</th>
					<th>Video URL</th>
					<th>Video Source</th>
					<th>Tags</th>
					<th>Categories</th>
					<th>Remarks</th>
				</tr>
			</thead>
			<c:forEach var="varHighlights" items="${highlights}" varStatus="loop">
				<c:url var="updateLink" value="/admin/updateHighlight">
					<c:param name="highlightsId" value="${varHighlights.id}" />
					<c:param name="pg" value="${param.pg}" />

				</c:url>
				<c:url var="deleteLink" value="/admin/deleteHighlight">
					<c:param name="highlightsId" value="${varHighlights.id}" />
					<c:param name="pg" value="${param.pg}" />
				</c:url>
				<tr class="${loop.count % 2==0?'alt':''}">
					<td>${loop.count}</td>
					<%-- <td>${varHighlights.id}</td> --%>
					<td><a href="${updateLink}"><img title="Update"
							src="${pageContext.request.contextPath}/resources/img/icon/edit.png" /></a>
						<a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to delete this Highlight?'))) return false;">
							<img title="Delete"
							src="${pageContext.request.contextPath}/resources/img/icon/delete.png" />
					</a></td>
					<td>${varHighlights.homeTeamName}</td>
					<td>${varHighlights.homeTeamScore}</td>
					<td>${varHighlights.awayTeamName}</td>
					<td>${varHighlights.awayTeamScore}</td>
					<td>${varHighlights.fixtureDate}</td>
					<td>${varHighlights.videoURL}</td>
					<td>${varHighlights.videoSource}</td>
					<td>${varHighlights.tags}</td>
					<td>${varHighlights.categories}</td>
					<td>${varHighlights.remarks}</td>
				</tr>
			</c:forEach>
		</table>
	</div>


</body>
</html>