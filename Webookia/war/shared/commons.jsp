<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.model.Loan"%>
<%@page import="it.webookia.backend.enums.LoanStatus"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%!public String loanStatusToHTML(LoanStatus status) {
		String elemClass, text;
		switch (status) {
		case ACCEPTED:
			elemClass = "accepted";
			text = "accettato";
			break;
		case GIVEN_BACK:
			elemClass = "givenBack";
			text = "restituito";
			break;
		case INITIAL:
			elemClass = "initial";
			text = "in attesa";
			break;
		case REFUSED:
			elemClass = "refused";
			text = "rifiutato";
			break;
		default:
			elemClass = "shipped";
			text = "in prestito";
		}

		final String pattern = "<span class=\"status %s\">%s</span>";
		return String.format(pattern, elemClass, text);
	}

	public <T> String viewLinkFor(Class<T> model, String id) {
		String context;
		String pattern = "<a href=\"%s\">Visualizza</a>";

		if (model.equals(Loan.class)) {
			context = "loans";
		} else if (model.equals(ConcreteBook.class)) {
			context = "books";
		} else {
			throw new IllegalArgumentException("No context for model "
					+ model.getCanonicalName());
		}
		String link = "/" + context + "/detail?id=" + id;
		return String.format(pattern, link);
	}%>