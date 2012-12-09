package com.appspot.i_can_do.service.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.AddressEmailType;
import com.appspot.i_can_do.master.security.PhoneType;
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

    public static String addressEmailOptions(AddressEmailType type) {
        String result = "";
        if(type != null){
            result = "<option value=" + type.ordinal() + ">"
                    + type.toString() + "</option>";
        }
        AddressEmailType[] types = AddressEmailType.values();
        for (AddressEmailType t : types) {
            if (!t.equals(type)) {
                result += "<option value=" + t.ordinal() + ">" + t.toString()
                        + "</option>";
            }
        }
        return result;
    }
    public static String phoneOptions(PhoneType type) {
        String result = "";
        if(type != null){
            result = "<option value=" + type.ordinal() + ">"
                    + type.toString() + "</option>";
        }
        PhoneType[] types = PhoneType.values();
        for (PhoneType t : types) {
            if (!t.equals(type)) {
                result += "<option value=" + t.ordinal() + ">" + t.toString()
                        + "</option>";
            }
        }
        return result;
    }
}
