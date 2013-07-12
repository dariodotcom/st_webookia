<%@page import="java.io.ObjectInputStream.GetField"%>
<%@page
	import="it.webookia.backend.controller.resources.exception.ResourceException"%>
<%@page import="it.webookia.backend.utils.Mapper"%>
<%@page import="it.webookia.backend.utils.CollectionUtils"%>
<%@page import="it.webookia.backend.model.Feedback"%>
<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.descriptor.SingleFeedbackDescriptor"%>
<%@page import="it.webookia.backend.utils.storage.PermissionManager"%>
<%@page import="it.webookia.backend.controller.services.Users"%>
<%@page import="it.webookia.backend.controller.resources.LoanResource"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	UserDescriptor userToShow = ServletUtils.getRequestAttribute(
			request, UserResource.class, Users.SHOW_USER)
			.getDescriptor();
	List<String> friendIds = userToShow.getFriendsIds();
%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="userUI" class="content topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Amici</h1>
				<div class="sectionContent">
					<p class="paragraph">
						In questa pagina visualizzi tutti i tuoi amici iscritti a Webookia
						. Se vuoi aggiungere nuovi amici visita la community su facebook e
						invitali per cominciare a scambiare libri con loro! E ricorda che
						solo i tuoi amici su facebook possono essere aggiunti alla
						community.<br> Vai su <a
							href="https://www.facebook.com/WebookiaCommunity"> Webookia
							Community </a>
					</p>
					<div class="list userList narrowList withBorder">
						<%
							if (friendIds.isEmpty()) {
						%>
						<div class="empty">Non hai amici.</div>
						<%
							} else {
								for (String id : friendIds) {
									UserDescriptor user = UserResource.getUser(id)
											.getDescriptor();
						%>
						<div class="listElement clearfix">
							<div class="pictureContainer left">
								<div class="userThumb">
									<img class="profilePicture" src="<%=user.getPicture()%>" />
								</div>
							</div>
							<div class="elementDetails left">
								<div class="detail title"><%=user.getFullName()%></div>
								<div class="detail location"><%=user.getLocation().getName()%></div>
							</div>
							<div class="selectButton right">
								<a href="/users/profile?uid=<%=user.getUserId()%>"
									class="button select animate">Visualizza</a>
							</div>
						</div>
						<%
							}
							}
						%>
					</div>
				</div>
			</div>
		</div>


	</div>
</body>
</html>