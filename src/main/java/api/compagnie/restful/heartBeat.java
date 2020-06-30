package api.compagnie.restful;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/heartBeat")
public class heartBeat {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getResponseHeartBeat() {
        System.out.println();
        return "Retour du GET en TextPlain";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String postResponseHeartBeat() {
        return "Retour du POST en TextPlain";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getResponseHeartBeatHTML() {
        return "<html> "+"<title>"+"heartBeat"+"</title>"+
                "<body>"+"<h1>Retour du GET en TextHTML</h1>"+"</body>"+
                "</html>";
    }


}
