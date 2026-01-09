package bg.tu_varna.sit.si.controllers;

import bg.tu_varna.sit.si.dtos.requests.CreateSurveyRequest;
import bg.tu_varna.sit.si.dtos.responses.SurveyFillResponse;
import bg.tu_varna.sit.si.dtos.responses.SurveyResponse;
import bg.tu_varna.sit.si.services.SurveyService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.UUID;

@Path("/surveys")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @POST
    public Response create(@Valid CreateSurveyRequest request, @Context UriInfo uriInfo) {

        SurveyResponse response = surveyService.create(request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(response.id.toString())
                .build();

        return Response.created(location).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id,
                            @QueryParam("code") String code) {

        if (code != null && !code.trim().isEmpty()) {

            SurveyFillResponse fillResponse = surveyService.getForFilling(id, code);
            return Response.ok(fillResponse).build();
        }

        SurveyResponse adminResponse = surveyService.getById(id);

        return Response.ok(adminResponse).build();
    }
}
