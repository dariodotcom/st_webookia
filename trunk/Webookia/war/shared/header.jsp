<%@page import="it.webookia.backend.utils.servlets.Context"%>
<%@page
	import="it.webookia.backend.controller.services.impl.ServiceServlet"%>
<%@page import="it.webookia.backend.controller.resources.UserResource"%>
<%@page import="it.webookia.backend.descriptor.UserDescriptor"%>
<%@page import="it.webookia.backend.utils.ServletUtils"%>
<%@page
	import="it.webookia.backend.utils.foreignws.facebook.FacebookConnector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%!public String contextDisplayName(Context context) {
		switch (context) {
		case AUTHENTICATION:
			return "";
		case BOOKS:
			return "Libreria";
		case HOME:
			return "Home";
		case LOANS:
			return "Prestiti";
		case SEARCH:
			return "Ricerca";
		case USERS:
			return "Utenti";
		default:
			return "";
		}
	}%>

<%
	HttpSession hSession = request.getSession();
	UserDescriptor hUserDescriptor = null;

	Context hCurrentContext = ServletUtils.getRequestAttribute(request,
			Context.class, ServiceServlet.CONTEXT);

	String authUserId = ServletUtils.getAuthenticatedUserId(request);
	if (authUserId != null) {
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
					class="username"><a href="/home/"><%=hUserDescriptor.getFullName()%></a></span>
				<span class="logout">(<a href="/authentication/logout">logout</a>)
				</span>
				<%
					}
				%>
			</div>
			<div id="searchContainer">
				<form name="searchForm" action="/search/result" method="post">
					<input class="searchText" name="title" type="text"
						placeholder="Cerca libro..."></input> <input class="searchSubmit"
						type="submit"></input>
				</form>
			</div>
			<%
				if (hUserDescriptor != null) {
			%>
			<div id="notificationContainer">
				<div class="topBarInteraction notificationButton" title="Notifiche">
					&nbsp;</div>
				<div class="notificationPanel callout">
					<div class="calloutArrow">&nbsp;</div>
					<div class="calloutBody">&nbsp;</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<div id="menuContainer" class="headerContent topWidthElement">
			<ul id="menu">
				<%
					for (Context context : Context.values()) {
						String extraClass = context.equals(hCurrentContext) ? " selected"
								: "";
				%>
				<li class="entry<%=extraClass%>" id="<%=context%>"><a
					class="tabLink" href="/<%=context.getContextName()%>/"> <span
						class="tabTitle"><%=contextDisplayName(context)%></span>
				</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</div>
</div>