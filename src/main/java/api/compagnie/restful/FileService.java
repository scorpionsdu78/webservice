package api.compagnie.restful;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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

    @GET
    @Produces("image/jpg")
    public Response getFile() {
        File file = new File(basePath+"/panda.JPG");
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=\"javatpoint_image.png\"");
        return response.build();

    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String fileLocation = basePath + fileDetail.getFileName();
        System.out.println(fileLocation);
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
        } catch (Exception e) {e.printStackTrace();}
        String output = "File successfully uploaded to : " + fileLocation;
        return Response.status(Response.Status.OK).entity(output).build();
    }
}
