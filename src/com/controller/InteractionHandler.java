package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaclasses.*;

/**
 * Servlet implementation class InteractionHandler
 */

public class InteractionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InteractionHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext sc = getServletContext();
		String url = "/index.jsp";
		String action = request.getParameter("action");
		if (action.equals("index")) {
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				List<Entry<String, Integer>> files = new SearchFiles(
						"F:/Windsor Sem-1/Advance Computing Concepts/Assignments-workspace/searchengine/src/webpages").search(keyword);
				if (files.size() != 0) {
					request.setAttribute("files", files);
				} else {
					EditDistance spell = new EditDistance();
					String suggest = spell.printSuggestions(keyword);
					request.setAttribute("suggest", suggest);
				}

			}
			url = "/index.jsp";
		}

		else if (action.equals("phonenumber")) {
			String phonenumber = request.getParameter("phonenumber");
			String valid;
			if (phonenumber != null && !phonenumber.equals("")) {
				String pattern = "(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}";
				Pattern r = Pattern.compile(pattern);
				String str = "";
				ArrayList<String> fileContentCount = new ArrayList<String>();
				if (r.matcher(phonenumber).matches()) // pattern matching
				{

					valid = "valid";

					List<String> files = new SearchFiles(
							"F:/Windsor Sem-1/Advance Computing Concepts/Assignments-workspace/searchengine/src/text")
									.searchphonenumber(phonenumber);
					request.setAttribute("files", files);

				} else {
					valid = "invalid";

				}
				request.setAttribute("valid",valid);
				url = "/phonenumber.jsp";
			}
		} else if (action.equals("pincode")) {
			String city = request.getParameter("city");

			if (city != null && !city.equals("")) {
				String pincodes[] = SymbolGraph.getPincodes(city);
				if (pincodes != null && pincodes.length != 0) {
					request.setAttribute("found", "found");
					request.setAttribute("pincodes", pincodes);
				} else {
					request.setAttribute("found", "not found");
				}
			}
			url = "/pincodes.jsp";
		}
		sc.getRequestDispatcher(url).forward(request, response);
	}

}
