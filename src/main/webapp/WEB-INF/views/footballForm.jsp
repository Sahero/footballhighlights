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
<style type="text/css">
.content table tbody td:first-child {
	border-left: none;
	width: 100px;
}

input[type="text"] {
	width: 100%;
}
</style>
</head>
<body>
	<jsp:include page="headerAdmin.jsp"  />
	<div id="wrapper">
		<div id="header">
			<h2>Add New Highlight</h2>
		</div>
	</div>

	<div class="contents">
		<form:form action="saveHighlight" modelAttribute="football"
			method="POST">
			<form:hidden path="id" />
			<div class="content">
				<table>
					<tbody>
						<tr>
							<td><label>Home team name:</label></td>
							<td><form:input path="homeTeamName" /></td>
							<td><font color="red"> <form:errors path="homeTeamName"></form:errors></font></td> 
						</tr>
						<tr class="alt">
							<td><label>Home team score:</label></td>
							<td><form:input path="homeTeamScore" /></td>
							<td><font color="red"> <form:errors path="homeTeamScore"></form:errors></font></td>
						</tr>
						<tr>
							<td><label>Away team name:</label></td>
							<td><form:input path="awayTeamName" /></td>
							<td><font color="red"> <form:errors path="awayTeamName"></form:errors></font></td>
						</tr>
						<tr class="alt">
							<td><label>Away team score:</label></td>
							<td><form:input path="awayTeamScore" /></td>
							<td><font color="red"> <form:errors path="awayTeamScore"></form:errors></font></td>
						</tr>
						<tr>
							<td><label>Fixture Date:</label></td>
							<td><form:input path="fixtureDate" /></td>
							<td><font color="red"> <form:errors path="fixtureDate"></form:errors></font></td>
						</tr>
						<tr class="alt">
							<td><label>Video URL:</label></td>
							<td><form:input path="videoURL" /></td>
							<td><font color="red"> <form:errors path="videoURL"></form:errors></font></td>
						</tr>

						<tr>
							<td><label>Video Source:</label></td>
							<td><%-- <form:input path="videoSource" /> --%>
							<form:select items="${videoSources}" path="videoSource"/>
							</td>
							<td><font color="red"> <form:errors path="videoSource"></form:errors></font></td>
						</tr>

						<%-- <tr class="alt">
							<td><label>Tags:</label></td>
							<td><form:input path="tags" /></td>
						</tr> --%>
						<tr class="alt">
							<td><label>Categories:</label></td>
							<td><form:input path="categories" /></td>
							<td></td>
						</tr>
						<tr>
							<td><label>Remarks:</label></td>
							<td><form:input path="remarks" /></td>
							<td></td>
						</tr>

						<tr>
							<td><a class="button"
								href="${pageContext.request.contextPath}/admin">Back to list</a></td>
							<td><input type="submit" value="Save" class="button" /></td>
						</tr>

					</tbody>
				</table>
			</div>
			<input name="pg" type="hidden"  value="${param.pg}"/>
		</form:form>
	</div>
</body>
</html>