package org.dropwizard.anushree.Resources;

import org.dropwizard.anushree.Service.DropBoxService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/dropbox")
@Produces(MediaType.APPLICATION_JSON)
public class DropBoxResource {
    private final DropBoxService service;

    public DropBoxResource(DropBoxService service) {
        this.service = service;
    }

    @GET
    @Path("/auth")
    public Response auth() {
        System.out.println("within auth");
        String url = service.buildAuthUrl();
        return Response.seeOther(java.net.URI.create(url)).build();
    }

    @GET
    @Path("/callback")
    public Response callback(@QueryParam("code") String code) {

        if (code == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Missing ?code")).build();

        }
        try {
            String json = service.exchangeCodeForToken(code);
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getTeamInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamInfo(@Context HttpHeaders headers) {

        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Missing or invalid Authorization header")
                    .build();
        }

        String accessToken = authHeader.substring("Bearer ".length());
        String account;
        try {
            // Now call Dropbox using this accessToken
            account = service.getTeamInfo(accessToken);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok(account).build();
    }

}
