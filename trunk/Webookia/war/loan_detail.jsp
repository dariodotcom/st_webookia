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

<%!private final static String loanProgressionButton = "<button class=\"button\" id=\"%s\">%s</button>";

	private String getButtonText(LoanStatus status) {
		switch (status) {
		case ACCEPTED:
			return "Accetta";
		case GIVEN_BACK:
			return "Mi &egrave; stato restituito";
		case REFUSED:
			return "Rifiuta";
		case SHIPPED:
			return "Mi &egrave; stato consegnato";
		default:
			throw new IllegalArgumentException();
		}
	}

	private String createButton(LoanStatus status, String loanId) {
		String name = status.name().toLowerCase();

		return String
				.format(loanProgressionButton, name, getButtonText(status));
	}

	private String getProgressionButton(LoanStatus status, String loanId) {
		String text = null;
		if (status.equals(LoanStatus.INITIAL)) {
			text = createButton(LoanStatus.ACCEPTED, loanId)
					+ createButton(LoanStatus.REFUSED, loanId);
		} else {
			int index = LoanStatus.indexOf(status) + 1;
			int length = LoanStatus.values().length;
			if (index < length - 1) {
				text = createButton(LoanStatus.valueOf(index), loanId);
			}
		}

		return text;
	}%>

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
	LoanStatus loanStatus = loanDesc.getStatus();
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="contentSection" id="loanUI">
				<h1 class="sectionTitle">Dettaglio prestito</h1>
				<div class="sectionContent">
					<div class="details loanDetails clearfix">
						<div class="id hidden"><%=descriptor.getId()%></div>
						<div class="pictureContainer left">
							<img class="bookPicture" src="<%=bookDesc.getThumbnail()%>" />
						</div>
						<div class="detailContainer left">
							<!-- Book details -->
							<div class="heading"><%=bookDesc.getTitle()%></div>
							<div class="detail clearfix">
								<div class="detailName">Autore</div>
								<div class="detailValue"><%=bookDesc.getAuthors()%></div>
							</div>
							<div class="detail clearfix">
								<%
									if (isOwner) {
								%>
								<div class="detailName">Mittente</div>
								<div class="detailValue"><%=borrowerName%></div>
								<%
									} else {
								%>
								<div class="detailName">Destinatario</div>
								<div class="detailValue"><%=ownerName%></div>
								<%
									}
								%>
							</div>
							<div class="detail clearfix">
								<div class="detailName">Stato</div>
								<div class="detailValue"><%=loanStatusToHTML(loanStatus)%></div>
							</div>

							<!-- Actions -->
							<%
								Boolean canTakeAction;
								if (isOwner) {
									canTakeAction = loanStatus.equals(LoanStatus.INITIAL)
											|| loanStatus.equals(LoanStatus.SHIPPED);
								} else {
									canTakeAction = loanStatus.equals(LoanStatus.ACCEPTED);
								}

								if (canTakeAction) {
							%>
							<div class="bottomLine"><%=getProgressionButton(loanStatus, loanDesc.getId())%></div>
							<%
								}
							%>
						</div>
					</div>
				</div>
			</div>
			<%
				if (loanStatus.equals(LoanStatus.GIVEN_BACK)) {
			%>
			<div class="contentSection">
				<h2 class="sectionTitle">Feedback</h2>
				<div class="sectionContent">
					<%
						LoanFeedbackDescriptor feedbacks = loanRes.getFeedbacks();
							SingleFeedbackDescriptor ownerFeedback = feedbacks
									.getOwnerFeedback();
							SingleFeedbackDescriptor borrowerFeedback = feedbacks
									.getBorrowerFeedback();

							Boolean canSendFeedback = isOwner ? ownerFeedback == null
									: borrowerFeedback == null;

							if (ownerFeedback != null) {
					%>
					<div class="message feedback self clearfix">
						<div class="userThumb">
							<img class="profilePicture" src="<%=ownerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader"><%=ownerFeedback.getDate()%></p>
							<div class="markerPlaceholder"><%=ownerFeedback.getMark()%></div>
							<p class="messageText"><%=ownerFeedback.getText()%></p>
						</div>
					</div>
					<%
						} else if (!isOwner) {
					%>
					<p class="empty">Il proprietario non ha ancora rilasciato un
						feedback.</p>
					<%
						}

							if (borrowerFeedback != null) {
					%>
					<div class="message feedback other clearfix">
						<div class="userThumb">
							<img class="profilePicture"
								src="<%=borrowerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader"><%=borrowerFeedback.getDate()%></p>
							<div class="markerPlaceholder"><%=borrowerFeedback.getMark()%></div>
							<p class="messageText"><%=borrowerFeedback.getText()%></p>
						</div>
					</div>
					<%
						} else if (isOwner) {
					%>
					<p class="empty">Il prestatario non ha ancora rilasciato un
						feedback.</p>
					<%
						}

							if (canSendFeedback) {
					%>
					<div id="sendFeedback"
						class="message feedback <%=isOwner ? "self" : "other"%> clearfix">
						<div class="userThumb">
							<img class="profilePicture" src="<%=viewerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader">Feedback</p>
							<div class="markerPlaceholder editable" id="feedbackMark"></div>
							<textarea id="feedbackText" class="messageInput"></textarea>
							<button id="feedbackSubmit" class="button">Invia</button>
						</div>
					</div>
					<%
						}
					%>
				</div>
			</div>
			<%
				}
			%>
			<div class="contentSection">
				<h2 class="sectionTitle">Messaggi</h2>
				<div class="sectionContent">
					<%
						List<MessageDescriptor> messages = loanRes.getMessages().getList();

						if (messages.isEmpty()) {
					%>
					<p class="empty">Non ci sono messaggi.</p>
					<%
						} else {
							for (MessageDescriptor message : messages) {
								String authorId = message.getAuthorUserId();
								UserDescriptor author = authorId.equals(ownerId) ? ownerDesc
										: borrowerDesc;
								String userClass = authorId.equals(ownerId) ? "self"
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

					<div
						class="message review <%=viewerId.equals(ownerId) ? "self" : "other"%> clearfix"
						id="sendMessage">
						<div class="userThumb">
							<img class="profilePicture" src="<%=viewerDesc.getThumbnail()%>">
						</div>
						<div class="arrow">&nbsp;</div>
						<div class="messageBody">
							<p class="messageHeader">Invia un messaggio:</p>
							<textarea id="messageText" class="messageInput"></textarea>
							<button class="button" id="messageSubmit">Invia</button>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>