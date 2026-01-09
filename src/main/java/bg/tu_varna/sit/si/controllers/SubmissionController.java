package bg.tu_varna.sit.si.controllers;

import bg.tu_varna.sit.si.dtos.requests.SubmitSurveyRequest;
import bg.tu_varna.sit.si.dtos.responses.SubmissionDetailsResponse;
import bg.tu_varna.sit.si.dtos.responses.SubmitSurveyResponse;
import bg.tu_varna.sit.si.services.SubmissionService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.UUID;

@Path("/surveys/{surveyId}/submissions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @POST
    public Response submit(@PathParam("surveyId") UUID surveyId,
                           @Valid SubmitSurveyRequest request,
                           @Context UriInfo uriInfo) {

        SubmitSurveyResponse created = submissionService.submit(surveyId, request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(created.submissionId.toString())
                .build();

        return Response.created(location).entity(created).build();
    }

    @GET
    @Path("/id/{submissionId}")
    public SubmissionDetailsResponse getById(@PathParam("submissionId") UUID submissionId) {
        return submissionService.getById(submissionId);
    }
}
