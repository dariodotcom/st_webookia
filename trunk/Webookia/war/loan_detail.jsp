<%@page import="it.webookia.backend.descriptor.MessageDescriptor"%>
<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.descriptor.SingleFeedbackDescriptor"%>
<%@page import="it.webookia.backend.model.LoanTest"%>
<%@page import="it.webookia.backend.descriptor.LoanFeedbackDescriptor"%>
<%@page import="it.webookia.backend.enums.LoanStatus"%>
<%@page import="it.webookia.backend.enums.BookStatus"%>
<%@page import="it.webookia.backend.descriptor.LoanDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Loans"%>
<%@page import="it.webookia.backend.controller.resources.LoanResource"%>
<%@page
	import="it.webookia.backend.descriptor.ReviewDescriptor.CommentDescriptor"%>
<%@page import="it.webookia.backend.descriptor.ReviewDescriptor"%>
<%@page import="it.webookia.backend.controller.resources.BookResource"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ include file="shared/commons.jsp"%>

<%
	LoanResource loanRes = ServletUtils.getRequestAttribute(request,
			LoanResource.class, Loans.CONTEXT_LOAN);

	LoanDescriptor descriptor = loanRes.getDescriptor();

	String ownerId = descriptor.getOwnerId();
	String borrowerId = descriptor.getBorrowerId();
	String viewerId = ServletUtils.getAuthenticatedUserId(request);
	String bookId = descriptor.getBookId();
	boolean isOwner = viewerId.equals(ownerId);

	// Resources
	UserResource owner = UserResource.getUser(ownerId);
	UserResource borrower = UserResource.getUser(borrowerId);
	BookResource book = BookResource.getBook(bookId, isOwner ? owner
			: borrower);

	// Descriptors
	UserDescriptor ownerDesc = owner.getDescriptor();
	UserDescriptor borrowerDesc = borrower.getDescriptor();
	UserDescriptor viewerDesc = isOwner ? ownerDesc : borrowerDesc;
	BookDescriptor bookDesc = book.getDescriptor();
	LoanDescriptor loanDesc = loanRes.getDescriptor();

	String nameFormat = "%s %s";
	String ownerName = String.format(nameFormat, ownerDesc.getName(),
			ownerDesc.getSurname());
	String borrowerName = String.format(nameFormat,
			borrowerDesc.getName(), borrowerDesc.getSurname());
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Dettaglio prestito</h1>
				<div class="sectionContent">
					<div class="details loanDetails clearfix">
						<div class="pictureContainer left">
							<img class="bookPicture"
								src="http://www.oasidellibro.it/wp-content/uploads/2010/04/Il-Signore-Degli-Anelli.jpg" />
						</div>
						<div class="detailContainer left">
							<!-- Book details -->
							<div class="contentSection">
								<h2 class="sectionTitle">Dettagli libro</h2>
								<div class="sectionContent">
									<div class="title"><%=bookDesc.getTitle()%></div>
									<div class="author">di <%=bookDesc.getAuthors()%></div>
								</div>
							</div>

							<!-- Loan details -->
							<div class="contentSection">
								<h2 class="sectionTitle">Dettagli richiesta</h2>
								<div class="sectionContent">
									<%
										LoanStatus status = loanDesc.getStatus();
										if (isOwner) {
									%>
									Mittente:
									<%=borrowerName%>
									<%
										} else {
									%>
									Destinatario:
									<%=ownerName%>.
									<%
										}
									%>
									<br>Stato del prestito:
									<%=loanStatusToHTML(status)%>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
			<div class="contentSection">
				<h1 class="sectionTitle">Feedback</h1>
				<div class="sectionContent">
					<%
						LoanFeedbackDescriptor feedbacks = loanRes.getFeedbacks();
						SingleFeedbackDescriptor ownerFeedback = feedbacks
								.getOwnerFeedback();
						SingleFeedbackDescriptor borrowerFeedback = feedbacks
								.getBorrowerFeedback();

						if (!loanDesc.getStatus().equals(LoanStatus.GIVEN_BACK)) {
					%>
					<p class="empty">Non &egrave; possibile rilasciare
						feedback prima della fine del prestito.</span>
					<%
						} else {

							boolean canSendFeedback = isOwner ? ownerFeedback == null
									: borrowerFeedback == null;

							// Owner feedback
							if (ownerFeedback != null) {
								String userClass = isOwner ? "self" : "other";
					%>
					<div class="message feedback <%=userClass%> clearfix">
						<div class="userThumb">
							<img class="profilePicture" src="<%=ownerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader">Feedback</p>
							<div class="rank"><%=ownerFeedback.getMark()%></div>
							<p class="reviewText"><%=ownerFeedback.getText()%></p>
						</div>
					</div>
					<%
						} else {
					%>
					<div class="empty">Il proprietario non ha ancora rilasciato
						feedback.</div>
					<%
						}

							// Borrower feedback
							if (ownerFeedback != null) {
								String userClass = isOwner ? "other" : "self";
					%>
					<div class="message feedback <%=userClass%> clearfix">
						<div class="userThumb">
							<img class="profilePicture"
								src="<%=borrowerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader">Feedback</p>
							<div class="rank"><%=borrowerFeedback.getMark()%></div>
							<p class="reviewText"><%=borrowerFeedback.getText()%></p>
						</div>
					</div>
					<%
						} else {
					%>
					<div class="empty">Il ricevente non ha ancora rilasciato
						feedback.</div>
					<%
						}

							if (canSendFeedback) {
								UserDescriptor author = isOwner ? ownerDesc : borrowerDesc;
					%>
					<div class="message feedback self clearfix">
						<p class="messageHeader">Inserisci feedback</p>
						<form name="insertFeedback">
							<textarea name="feedbackText" class="messageInput"></textarea>
						</form>
					</div>
					<%
						}
						}
					%>
				</div>
			</div>
			<div class="contentSection">
				<h1 class="sectionTitle">Messaggi</h1>
				<div class="sectionContent">
					<%
						List<MessageDescriptor> messages = loanRes.getMessages().getList();

						if (messages.isEmpty()) {
					%>
					<p class="empty">Non ci sono messaggi.</p>
					<%
						} else {
					%>

					<%
						for (MessageDescriptor message : messages) {
								String authorId = message.getAuthorUsername();
								UserDescriptor author = authorId.equals(ownerId) ? ownerDesc
										: borrowerDesc;
								String userClass = authorId.equals(viewerId) ? "self"
										: "other";
					%>
					<div class="message <%=userClass%> clearfix">
						<div class="userThumb">
							<img class="profilePicture" src="<%=author.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader"><%=author.getName()%>
								<%=author.getSurname()%>
								-
								<%=message.getDate()%></p>
							<p class="messageText"><%=message.getText()%></p>
						</div>
					</div>
					<%
						}
						}
					%>

					<div class="message review self clearfix">
						<div class="userThumb">
							<img class="profilePicture" src="<%=viewerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader">Invia messaggio:</p>
							<form name="sendMessage">
								<textarea name="message" class="messageInput"></textarea>
							</form>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>