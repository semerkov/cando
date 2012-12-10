package com.appspot.i_can_do.server.servlets;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = -3208409086358916855L;
    private ServletContext ctx = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ctx = config.getServletContext();
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");
        if(action.equalsIgnoreCase("showAvatar"))
        {
            Blob avatar = user.getProfile().getImageFile();
            if(avatar == null){

                InputStream is = ctx.getResourceAsStream("/IMG/avatar.jpg");
                resp.setContentType("image/jpeg");
                resp.getOutputStream().write(IOUtils.toByteArray(is));
            }else{
                resp.setContentType(user.getProfile().getMimeType());
                resp.getOutputStream().write(avatar.getBytes());
            }
        }
    }
}