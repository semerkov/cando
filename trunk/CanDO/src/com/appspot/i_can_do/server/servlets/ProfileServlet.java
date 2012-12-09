package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Address;
import com.appspot.i_can_do.master.model.Email;
import com.appspot.i_can_do.master.model.Phone;
import com.appspot.i_can_do.master.security.AddressEmailType;
import com.appspot.i_can_do.master.security.PhoneType;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class ProfileServlet extends HttpServlet{
    private Logger log = Logger.getLogger(ImageUpload.class.getName());
	private static final long serialVersionUID = -178538787888163996L;
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList("addAddress","edit","remove","addEmail","addPhone", "save", "saveProfile","addAvatar");
	private static final AddressEmailType[] addressEmailTypes = AddressEmailType.values();
	private static final PhoneType[] phoneTypes = PhoneType.values();
	private static final CanDOSecurityService service = CanDOSecurityService.instance();
	private User user;
	
	protected boolean isLoginState(HttpServletRequest request) {
		user = (User) request.getSession().getAttribute("user");
		return user != null;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!isLoginState(request)){
            request.getRequestDispatcher("/").forward(request, response);
        }


        String type=request.getParameter("type");
        if(type == null)
        {
            request.setAttribute("user", user);
            request.getRequestDispatcher("profile.jsp").forward(request, response);

        }else{
            if(type.equalsIgnoreCase("showAvatar"))
            {
                Blob avatar = user.getProfile().getImageFile();
                response.setContentType("image/jpeg");
                response.getOutputStream().write(avatar.getBytes());
            }
        }
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart){
            if (SECURITY_ACTIONS.contains(action)) {
                  if (!isLoginState(request)) {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                  "Only authorized user can perform this action"); return;
                  }
            } else {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                        "Not existing action!");
                return;
            }

            if(action.equals("addAddress")){
                addAddress(request,response);
            } else if(action.equals("addAddress")){
                addAddress(request,response);
            } else if(action.equals("edit")){
                edit(request,response);
            } else if(action.equals("remove")){
                remove(request,response);
            } else if(action.equals("addEmail")){
                addEmail(request,response);
            } else if(action.equals("addPhone")){
                addPhone(request,response);
            } else if(action.equals("save")){
                save(request,response);
            } else if(action.equals("saveProfile")){
                saveProfile(request,response);
            }
        }else{
            addAvatar(request,response);
        }
	}

    private void addPhone(HttpServletRequest request,
			HttpServletResponse response) {
		String phone = request.getParameter("phone");
		String phoneType =request.getParameter("phoneTypeCode");
		if(phone != null && phoneType != null){
			int code = Integer.parseInt(phoneType);
			Phone p = new Phone(phone,phoneTypes[code]);
			user.getProfile().getPhones().add(p);
			service.saveUser(user);
		}
	}

	private void addEmail(HttpServletRequest request,
			HttpServletResponse response) {
		String email = request.getParameter("email");
		String emailType =request.getParameter("emailTypeCode");
		if(email != null && emailType != null){
			int code = Integer.parseInt(emailType);
			Email e = new Email(email,addressEmailTypes[code]);
			user.getProfile().getEmails().add(e);
			service.saveUser(user);
		}
		
	}
	
	private void addAddress(HttpServletRequest request,
			HttpServletResponse response){
		String address = request.getParameter("address");
		String addressType =request.getParameter("addressTypeCode");
		if(address != null && addressType != null){
			int code = Integer.parseInt(addressType);
			Address adr = new Address(address,addressEmailTypes[code]);
			user.getProfile().getAddresses().add(adr);
			service.saveUser(user);
		}
	}
	
	private void remove(HttpServletRequest request,
			HttpServletResponse response){
        String type = request.getParameter("type");
        String key = request.getParameter("key");

        if(type!= null && key != null){
            Key k = KeyFactory.stringToKey(key);
            if(type.equals("address")) {
                service.removeAddress(user.getProfile(), k);
            }else if(type.equals("email")){
                service.removeEmail(user.getProfile(), k);
            }else if(type.equals("phone")){
                service.removePhone(user.getProfile(), k);
            }
        }
		
	}

    private  void edit(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String key = request.getParameter("key");

        if(type!= null && key != null){
            Key k = KeyFactory.stringToKey(key);
            if(type.equals("address")) {
                for(Address a: user.getProfile().getAddresses()){
                    if(a.getKey().equals(k)){
                        request.setAttribute(type,a);
                        break;
                    }
                }
            }else if(type.equals("email")){
                for(Email o: user.getProfile().getEmails()){
                    if(o.getKey().equals(k)){
                        request.setAttribute(type,o);
                        break;
                    }
                }
            }else if(type.equals("phone")){
                for(Phone o: user.getProfile().getPhones()){
                    if(o.getKey().equals(k)){
                        request.setAttribute(type,o);
                        break;
                    }
                }
            }
        }
        request.getRequestDispatcher("WEB-INF/pages/profile/editItem.jsp").forward(request, response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String key = request.getParameter("key");
        String typeCode = request.getParameter("typeCode");
        String text = request.getParameter("text");

        if(type != null && key != null && typeCode != null && text!=null){
            Key k = KeyFactory.stringToKey(key);
            int code = Integer.parseInt(typeCode);

            if(type.equals("address")) {
                for(Address a: user.getProfile().getAddresses()){
                    if(a.getKey().equals(k)){
                       a.setAddress(text);
                       a.setType(addressEmailTypes[code]);
                        break;
                    }
                }
            }else if(type.equals("email")){
                for(Email o: user.getProfile().getEmails()){
                    if(o.getKey().equals(k)){
                        o.setEmail(text);
                        o.setType(addressEmailTypes[code]);
                        break;
                    }
                }
            }else if(type.equals("phone")){
                for(Phone o: user.getProfile().getPhones()){
                    if(o.getKey().equals(k)){
                        o.setPhoneNumber(text);
                        o.setType(phoneTypes[code]);
                        break;
                    }
                }
            }

            service.saveUser(user);
        }
    }

    private void saveProfile(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String sername = request.getParameter("sername");
        String about = request.getParameter("about");
        if(name != null){
            user.getProfile().setName(name);
        }
        if(sername != null){
            user.getProfile().setSername(sername);
        }
        if(about != null ){
            user.getProfile().setAbout(about);
        }
        service.saveUser(user);
    }

    private void addAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            ServletFileUpload upload = new ServletFileUpload();
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

                        request.setAttribute("user",user);
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                    }

                }
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
	
}
