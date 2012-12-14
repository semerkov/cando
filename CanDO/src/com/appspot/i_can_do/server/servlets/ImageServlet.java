package com.appspot.i_can_do.server.servlets;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;
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
	private static final Logger log = Logger.getLogger(ImageServlet.class.getName());
    private static final long serialVersionUID = -3208409086358916855L;
    private ServletContext ctx = null;
    private static final CanDOSecurityService service= CanDOSecurityService.instance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ctx = config.getServletContext();
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        user = service.findUserBykey(KeyFactory.keyToString(user.getKey())); // get user from db, not from session
        String action = req.getParameter("action");
        log.warning("Action is: " + String.valueOf(action != null ? action : "null"));
        if(action.equalsIgnoreCase("showAvatar"))
        {
            Blob avatar = user.getProfile().getImageFile();
            if(avatar == null){
                InputStream is = ctx.getResourceAsStream("/IMG/avatar.jpg");
                resp.setContentType("image/jpeg");
                resp.getOutputStream().write(IOUtils.toByteArray(is));
                log.warning("Avatar is null!");
            }else{
                resp.setContentType(user.getProfile().getMimeType());
                resp.getOutputStream().write(avatar.getBytes());
            }
        }
    }
}