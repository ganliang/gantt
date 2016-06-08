package com.gfkd.gantt.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gfkd.gantt.bean.Slbd;
import com.gfkd.gantt.dao.SlbdDao;
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/SlbdServlet")
public class SlbdServlet extends HttpServlet {

	private static final long serialVersionUID = 7736573876091727870L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		SlbdDao dao = new SlbdDao();
		Gson gson = new Gson();
		String json = "";
		switch (type) {
		case "getAll":
			List<Slbd> list = dao.getAll();
			json = gson.toJson(list);
			break;
		default:
			break;
		}
		response.getWriter().println(json);
	}
}
