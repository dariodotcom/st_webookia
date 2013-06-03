<%@page import="it.webookia.backend.controller.resources.UserResource"%>
<%@page import="it.webookia.backend.descriptor.UserDescriptor"%>
<%@page import="it.webookia.backend.utils.ServletUtils"%>
<%@page
	import="it.webookia.backend.utils.foreignws.facebook.FacebookConnector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	HttpSession hSession = request.getSession();
	UserDescriptor hUserDescriptor = null;

	String authUserId = ServletUtils.getAuthenticatedUserId(request);
	if (authUserId != null) {
		UserDescriptor descriptor;
		hUserDescriptor = UserResource.getUser(authUserId)
				.getDescriptor();
	}

	String hLoginUrl = FacebookConnector.getOauthDialogUrl();
%>

<div id="headerContainer">
	<div id="header" class="topWidthElement">
		<div id="topbar" class="headerContent topWidthElement clearfix">
			<div id="logo">
				<img src="/resources/logo.png" alt="Webookia Logo" />
			</div>
			<div id="userCorner">
				<%
					if (hUserDescriptor == null) {
				%>
				<div id="fbLogin" class="animate">
					<a href="<%=hLoginUrl%>"><img src="/resources/pix.png" /> <span
						class="loginText">login</span> </a>
				</div>
				<%
					} else {
				%>
				<img class="profilePicture"
					src="<%=hUserDescriptor.getThumbnail()%>" /> <span
					class="username"><%=hUserDescriptor.getName()%> <%=hUserDescriptor.getSurname()%></span>
				<%
					}
				%>
			</div>
			<div id="searchContainer">
				<form name="searchForm">
					<input class="searchText" type="text" placeholder="Cerca libro..."></input>
					<input class="searchSubmit" type="submit"></input>
				</form>
			</div>
		</div>
		<div id="menuContainer" class="headerContent topWidthElement">
			<ul id="menu">
				<li class="entry selected" id=""><span class="tabLink" href="">
						<span class="tabTitle">Libreria</span>
				</span></li>
				<li class="entry animate" id=""><a class="tabLink" href="">
						<span class="tabTitle">Prestiti</span>
				</a></li>
			</ul>
		</div>
	</div>
</div>