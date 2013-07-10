<%@page import="it.webookia.backend.model.Loan"%>
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

<%@ include file="shared/commons.jsp"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	List<LoanDescriptor> sentLoans = ServletUtils.getRequestAttribute(
			request, List.class, Loans.SENT_LOANS);
	List<LoanDescriptor> receivedLoans = ServletUtils
			.getRequestAttribute(request, List.class,
					Loans.RECEIVED_LOANS);
	UserResource viewer = UserResource.getUser(ServletUtils
			.getAuthenticatedUserId(request));
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Richieste ricevute</h1>
				<div class="sectionContent">
					<%
						if (receivedLoans.isEmpty()) {
					%>
					<p class="empty">Non hai ricevuto richieste.</p>
					<%
						} else {
					%>
					<table class="elemList">
						<tr class="listHead">
							<td class="property">Libro</td>
							<td class="property">Richiedente</td>
							<td class="property">Data richiesta</td>
							<td class="property">Stato</td>
							<td class="property"></td>
						</tr>
						<%
							for (LoanDescriptor descr : receivedLoans) {
									UserDescriptor borrower = UserResource.getUser(
											descr.getBorrowerId()).getDescriptor();
									BookDescriptor book = BookResource.getBook(
											descr.getBookId(), viewer).getDescriptor();
						%>
						<tr class="elem">
							<td class="property"><%=book.getTitle()%></td>
							<td class="property"><%=profileLink(borrower)%></td>
							<td class="property"><%=descr.getStartDate()%></td>
							<td class="property"><%=loanStatusToHTML(descr.getStatus())%></td>
							<td class="property"><%=viewLinkFor(Loan.class, descr.getId())%></td>
						</tr>
						<%
							}
						%>
					</table>
					<%
						}
					%>
				</div>
			</div>

			<div class="contentSection">
				<h1 class="sectionTitle">Richieste inviate</h1>
				<div class="sectionContent">
					<%
						if (sentLoans.isEmpty()) {
					%>
					<p class="empty">Non hai inviato richieste.</p>
					<%
						} else {
					%>
					<table class="elemList">
						<tr class="listHead">
							<td class="property">Libro</td>
							<td class="property">Destinatario</td>
							<td class="property">Data richiesta</td>
							<td class="property">Stato</td>
							<td class="property"></td>
						</tr>
						<%
							for (LoanDescriptor descr : sentLoans) {
									UserDescriptor owner = UserResource.getUser(
											descr.getOwnerId()).getDescriptor();
									BookDescriptor book = BookResource.getBook(
											descr.getBookId(), viewer).getDescriptor();
						%>
						<tr class="elem">
							<td class="property"><%=book.getTitle()%></td>
							<td class="property"><%=owner.getFullName()%></td>
							<td class="property"><%=descr.getStartDate()%></td>
							<td class="property"><%=loanStatusToHTML(descr.getStatus())%></td>
							<td class="property"><%=viewLinkFor(Loan.class, descr.getId())%></td>
						</tr>
						<%
							}
						%>
					</table>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>