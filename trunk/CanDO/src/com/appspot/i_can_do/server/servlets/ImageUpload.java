package com.appspot.i_can_do.server.servlets;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class ImageUpload extends HttpServlet {
    private static final long serialVersionUID = -3208409086358916855L;
    private Logger log = Logger.getLogger(ImageUpload.class.getName());
    private static final Integer MAX_SIZE = 2<<20;     // 1 MB
    private static final CanDOSecurityService service = CanDOSecurityService.instance();
    private User user;

    protected boolean isLoginState(HttpServletRequest request) {
        user = (User) request.getSession().getAttribute("user");
        return user != null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!isLoginState(request)){
            request.getRequestDispatcher("/").forward(request, response);
        }

        try {

            //if(request.getContentLength() > MAX_SIZE) return;

            ServletFileUpload upload = new ServletFileUpload();
            response.setContentType("text/plain");

            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                InputStream stream = item.openStream();

                if (item.isFormField()) {
                    log.warning("Got a form field: " + item.getFieldName());
                } else {
                    log.warning("Got an uploaded file: " + item.getFieldName() +
                            ", name = " + item.getName());

                    // You now have the filename (item.getName() and the
                    // contents (which you can read from stream). Here we just
                    // print them back out to the servlet output stream, but you
                    // will probably want to do something more interesting (for
                    // example, wrap them in a Blob and commit them to the
                    // datastore).

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int read;
                    while ((read = stream.read()) != -1) {
                        baos.write(read);
                    }
                    byte[] imageBytes = baos.toByteArray();
                    if(imageBytes.length>0){
                        ImagesService imagesService = ImagesServiceFactory.getImagesService();

                        Image oldImage = ImagesServiceFactory.makeImage(imageBytes);

                        Transform resize = ImagesServiceFactory.makeResize(300, 300);

                        Image newImage = imagesService.applyTransform(resize, oldImage);
                        byte[] newImageData = newImage.getImageData();

                        user.getProfile().setImageFile(new Blob(newImageData));
                        service.saveUser(user);
                        log.warning("User profile image saved");
                    }

                }
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
        /*
    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        URL url;
        if ("exists".equals(req.getParameter("image"))) {
            url = new URL("https://www.google.com/logos/classicplus.png");
        } else if ("notImage".equals(req.getParameter("image"))) {
            url = new URL("http://www.google.com");
        } else {
            url = new URL("http://foo.bar/image.png");
        }

        resp.setContentType("text/plain");
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final InputStream inputStream = url.openStream();
            int read;
            while ((read = inputStream.read()) != -1) {
                baos.write(read);
            }

            final Image image = ImagesServiceFactory.makeImage(baos.toByteArray());

            resp.getWriter().println("image width: " + image.getWidth());
            resp.getWriter().println("image height: " + image.getHeight());
        } catch (final IOException e) {
            resp.getWriter().println("image doesn't exists!");
        } catch (final IllegalArgumentException e) {
            resp.getWriter().println("invalid image!");
        }

    }    */
}