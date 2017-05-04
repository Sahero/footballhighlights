<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
</head>
<body>
	<table style="width: 100%">
		<tr>
			<td class="normal" colspan="4"
				style="font-size: 11.5px; float: right"><strong>${user}</strong>
				| <a href='<c:url value="/logout"/>'>Logout</a></td>
		</tr>
	</table>
</body>
</html>