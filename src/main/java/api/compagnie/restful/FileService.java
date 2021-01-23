package api.compagnie.restful;


import api.compagnie.connection.HibernateUtil;
import api.compagnie.controler.PhotoControler;
import api.compagnie.entity.Photo;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("/Image")
public class FileService {
    public static String basePath = "/home/utilisateur/IdeaProjects/stage/compagnieServer/image/";

    private PhotoControler photoControler;

    public FileService() {
        photoControler = new PhotoControler();
    }

    @GET
    @Path("/{url}")
    @Produces("image/jpg")
    public Response getFile(@PathParam("url") String url) {
        File file = new File(basePath+"/"+url);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=\"javatpoint_image.png\"");
        return response.build();

    }

    @GET
    @Produces("image/jpg")
    public Response getDefault() {
        File file = new File(basePath+"/panda.JPG");
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=\"javatpoint_image.png\"");
        return response.build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String fileLocation = basePath + fileDetail.getFileName();
        System.out.println(fileLocation);

        Photo photo =null;

        //saving file
        try {
            System.out.println("entrez dans le try");
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(fileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx=session.beginTransaction();

            photo = photoControler.insertPhoto(fileDetail.getFileName(),null,null);

            tx.commit();


        } catch (Exception e) {e.printStackTrace();}
        String output = fileDetail.getFileName();
        return Response.status(Response.Status.OK).entity(photo).build();
    }
}
