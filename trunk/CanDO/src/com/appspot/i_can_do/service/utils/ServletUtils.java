package com.appspot.i_can_do.service.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ServletUtils {
	private ServletUtils() {
	}

	public static void writeJson(HttpServletResponse response, Object object)
			throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(object));
	}
}
