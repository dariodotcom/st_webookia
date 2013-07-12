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
<%!public int averageMark(List<SingleFeedbackDescriptor> input) {
		int sum = 0, size = input.size();

		if (size == 0) {
			return 0;
		}

		for (SingleFeedbackDescriptor f : input) {
			sum += f.getMark();
		}

		return (int) (sum / size);
	}%>
<%
	UserResource userToShow = ServletUtils.getRequestAttribute(request,
			UserResource.class, Users.SHOW_USER);

	UserDescriptor descriptor = userToShow.getDescriptor();
	UserResource viewer = ServletUtils.getAuthenticatedUser(request);

	List<SingleFeedbackDescriptor> feedbacksAsOwner = userToShow
			.getFeedbacksAsOwner().getList();
	List<SingleFeedbackDescriptor> feedbacksAsBorrower = userToShow
			.getFeedbacksAsBorrower().getList();
	int ownerFeedbackSize = feedbacksAsOwner.size();
	int borrowerFeedbackSize = feedbacksAsBorrower.size();
%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="userUI" class="content topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Profilo utente</h1>
				<div class="sectionContent">
					<div class="details clearfix">
						<div class="pictureContainer left">
							<div class="userThumb big">
								<img class="profilePicture" src="<%=descriptor.getPicture()%>">
							</div>
						</div>
						<div class="detailContainer left">
							<!-- Details -->
							<div class="heading"><%=descriptor.getFullName()%></div>
							<div class="detail clearfix">
								<div class="detailName">Città</div>
								<div class="detailValue"><%=descriptor.getLocation().getName()%></div>
							</div>
							<div class="detail clearfix">
								<div class="detailName">Feedback come proprietario</div>
								<div class="detailValue">
									<div class="markerPlaceholder"><%=averageMark(feedbacksAsOwner)%></div>
								</div>
							</div>
							<div class="detail clearfix">
								<div class="detailName">Feedback come prestatario</div>
								<div class="detailValue">
									<div class="markerPlaceholder"><%=averageMark(feedbacksAsBorrower)%></div>
								</div>
							</div>
							<div class="detail clearfix">
								<div class="detailName">
									<a href="https://www.facebook.com/<%=descriptor.getUserId()%>">
										Visualizza il profilo facebook</a><br>
								</div>
							</div>
						</div>
					</div>

					<div class="tabbedContainer">
						<div class="tabs">
							<div class="tab selected">Libreria</div>
							<%
								String ownerFeedbackCount = " (" + ownerFeedbackSize + ")";
							%>
							<div class="tab">
								Feedback da proprietario<%=ownerFeedbackCount%>
							</div>
							<%
								String borrowerFeedbackCount = " (" + borrowerFeedbackSize + ")";
							%>
							<div class="tab">
								Feedback da prestatario<%=borrowerFeedbackCount%>
							</div>
						</div>
						<div class="panels animate">
							<div class="panel animate">
								<div class="list bookList">
									<%
										ListDescriptor<BookDescriptor> books = userToShow.getUserBooks();

										if (books.size() == 0) {
									%>
									<div class="empty">L'utente non ha libri condivisi.</div>
									<%
										} else {
											for (BookDescriptor book : books) {
									%>
									<div class="listElement clearfix">
										<div class="pictureContainer left">
											<img class="bookPicture" src="<%=book.getThumbnail()%>" />
										</div>
										<div class="elementDetails left">
											<div class="detail title"><%=book.getTitle()%></div>
											<div class="detail author">
												di
												<%=book.getAuthors()%></div>
											<div class="detail isbn"><%=book.getIsbn()%></div>
										</div>
										<div class="selectButton right">
											<a href="/books/detail?id=<%=book.getId()%>"
												class="button select animate">Visualizza</a>
										</div>
									</div>
									<%
										}
										}
									%>
								</div>
							</div>
							<div class="panel feedbackList list">
								<%
									if (ownerFeedbackSize == 0) {
								%>
								<div class="empty">L'utente non ha ricevuto feedback come
									proprietario.</div>
								<%
									} else {
										for (SingleFeedbackDescriptor feedback : feedbacksAsOwner) {
								%>
								<div class="anonymousFeedback listElement">
									<div class="markerPlaceholder"><%=feedback.getMark()%></div>
									<div class="text"><%=feedback.getText()%></div>
								</div>
								<%
									}
									}
								%>
							</div>
							<div class="panel feedbackList list">
								<%
									if (borrowerFeedbackSize == 0) {
								%>
								<div class="empty">L'utente non ha ricevuto feedback come
									prestatario.</div>
								<%
									} else {
										for (SingleFeedbackDescriptor feedback : feedbacksAsBorrower) {
								%>
								<div class="anonymousFeedback listElement">
									<div class="markerPlaceholder"><%=feedback.getMark()%></div>
									<div class="text"><%=feedback.getText()%></div>
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
		</div>
	</div>
	<%@ include file="shared/footer.jsp"%>
</body>
</html>