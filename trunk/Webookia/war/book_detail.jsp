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

<%!private String textOf(PrivacyLevel level) {
		switch (level) {
		case FRIENDS_ONLY:
			return "Solo amici";
		case PRIVATE:
			return "Nessuno";
		default:
			return "Tutti";
		}
	}%>

<%
	BookResource contextBook = ServletUtils.getRequestAttribute(
			request, BookResource.class, Books.CONTEXT_BOOK);
	UserResource viewer = ServletUtils.getAuthenticatedUser(request);
	UserDescriptor viewerDescriptor = (viewer == null ? null : viewer
			.getDescriptor());
	UserDescriptor ownerDescriptor = contextBook.getOwner();
	BookDescriptor contextBookDescriptor = contextBook.getDescriptor();

	boolean isOwner = contextBook.isOwner(viewer);
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Dettaglio libro</h1>
				<div class="sectionContent">
					<div id="bookUI">
						<div class="details clearfix">
							<div class="pictureContainer left">
								<img class="bookPicture"
									src="<%=contextBookDescriptor.getThumbnail()%>" />
							</div>
							<div class="detailContainer left">
								<!-- Details -->
								<div class="id hidden"><%=contextBookDescriptor.getId()%></div>
								<div class="heading"><%=contextBookDescriptor.getTitle()%></div>
								<%
									String authors = contextBookDescriptor.getAuthors();
								%>
								<div class="detail clearfix">
									<div class="detailName">Autori</div>
									<div class="detailValue"><%=authors%></div>
								</div>
								<div class="detail clearfix">
									<div class="detailName">Editore</div>
									<div class="detailValue"><%=contextBookDescriptor.getPublisher()%></div>
								</div>
								<div class="detail clearfix">
									<div class="detailName">ISBN</div>
									<div class="detailValue"><%=contextBookDescriptor.getIsbn()%></div>
								</div>
								<div class="detail clearfix">
									<div class="detailName">Proprietario</div>
									<div class="detailValue">
										<a href="/users/profile?uid=<%=ownerDescriptor.getUserId()%>">
											<%=ownerDescriptor.getFullName()%>
										</a>
									</div>
								</div>
								<div class="detail clearfix">
									<div class="detailName">Stato</div>
									<div class="detailValue"><%=bookStatusToHMTL(contextBookDescriptor.getStatus())%></div>
								</div>
								<div class="detail clearfix">
									<div class="detailName">Privacy</div>
									<div class="detailValue"><%=bookPrivacyToHTML(contextBookDescriptor.getPrivacy())%></div>
								</div>
								<a target="_blank" class="gBooksLink"
									href="<%=contextBookDescriptor.getGBooksLink()%>">Visualizza
									su Google Books</a>
							</div>
							<%
								if (contextBook.canBeLentBy(viewer)) {
							%>
							<div class="bottomLine">
								<button class="button askLoan">Chiedi in prestito</button>
							</div>
							<%
								}
							%>
						</div>

						<%
							if (isOwner) {
						%>
						<!-- Settings -->
						<div class="contentSection">
							<h2 class="sectionTitle">Impostazioni</h2>
							<div class="sectionContent">
								<label for="bookPrivacy" class="privacyLabel">Mostra
									questo libro a: </label><select name="privacy" id="bookPrivacy">
									<%
										for (PrivacyLevel level : PrivacyLevel.values()) {
												String value = level.name();
												String text = textOf(level);
												boolean selected = level.equals(contextBookDescriptor
														.getPrivacy());
									%>
									<option value="<%=value%>"
										<%=selected ? " selected=\"selected\"" : ""%>><%=text%></option>
									<%
										}

											BookStatus status = contextBookDescriptor.getStatus();
											boolean chSelected = status.equals(BookStatus.AVAILABLE);
											boolean chActive = !status.equals(BookStatus.LENT);
									%>

								</select> <br> <input type="checkbox" name="available"
									class="bookStatus" id="bookStatus"
									<%=chSelected ? "checked=\"checked\"" : ""%>
									<%=chActive ? "" : " disabled"%> /> <label for="bookStatus"
									class="statusLabel">Il libro è disponibile per il
									prestito.</label>
							</div>
						</div>
						<%
							}
						%>

						<!-- review -->
						<div class="contentSection">
							<h2 class="sectionTitle">Recensione</h2>
							<div class="sectionContent">
								<%
									ReviewDescriptor review = contextBook.getReview();
									if (review == null) {
										if (isOwner) {
								%>
								<div class="message review self clearfix">
									<div class="userThumb">
										<img class="profilePicture"
											src="<%=ownerDescriptor.getThumbnail()%>">
									</div>
									<div class="arrow">&nbsp;</div>
									<div class="messageBody">
										<p class="messageHeader">Inserisci una recensione</p>
										<div class="markerPlaceholder editable" id="reviewMark"></div>
										<textarea name="review" class="messageInput" id="reviewText"></textarea>
										<br>
										<button class="button" id="reviewSubmit">Invia</button>

									</div>
								</div>
								<%
									} else {
								%>
								<p class="empty">Il proprietario non ha ancora inserito una
									recensione.</p>
								<%
									}
									} else {
								%>
								<div class="message review self clearfix">
									<div class="userThumb">
										<img class="profilePicture"
											src="<%=ownerDescriptor.getThumbnail()%>">
									</div>
									<div class="arrow">&nbsp;</div>
									<div class="messageBody">
										<p class="messageHeader"><%=ownerDescriptor.getName()%>
											<%=ownerDescriptor.getSurname()%>
											-
											<%=review.getDate()%></p>
										<div class="markerPlaceholder"><%=review.getMark()%></div>
										<p class="reviewText"><%=review.getText()%></p>
									</div>
								</div>
								<%
									for (CommentDescriptor comment : review.getComments()) {
											String authorId = comment.getAuthorId();
											String ownerId = ownerDescriptor.getUserId();

											String userClass;
											UserDescriptor msgAuthor;

											if (authorId.equals(ownerId)) {
												userClass = "self";
												msgAuthor = ownerDescriptor;
											} else {
												userClass = "other";
												msgAuthor = UserResource.getUser(authorId)
														.getDescriptor();
											}
								%>
								<div class="message comment <%=userClass%> clearfix">
									<div class="userThumb">
										<img class="profilePicture"
											src="<%=msgAuthor.getThumbnail()%>">
									</div>
									<div class="arrow">&nbsp;</div>
									<div class="messageBody">
										<p class="messageHeader"><%=msgAuthor.getFullName()%>
											-
											<%=comment.getDate()%></p>
										<p class="reviewText"><%=comment.getText()%></p>
									</div>
								</div>
								<%
									}

										if (viewer != null) {
											String userClass = isOwner ? "self" : "other";
								%>
								<div class="message comment <%=userClass%> clearfix">
									<div class="userThumb">
										<img class="profilePicture"
											src="<%=viewer.getDescriptor().getThumbnail()%>">
									</div>
									<div class="arrow">&nbsp;</div>
									<div class="messageBody">
										<p class="messageHeader">Inserisci un commento:</p>
										<textarea id="commentText" class="messageInput"></textarea>
										<button class="button" id="commentSubmit">Invia</button>
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
		</div>
	</div>
</body>
</html>