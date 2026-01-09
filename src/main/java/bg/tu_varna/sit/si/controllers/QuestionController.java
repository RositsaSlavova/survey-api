package bg.tu_varna.sit.si.controllers;

import bg.tu_varna.sit.si.dtos.requests.AddQuestionRequest;
import bg.tu_varna.sit.si.dtos.responses.QuestionResponse;
import bg.tu_varna.sit.si.services.QuestionService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.UUID;

@Path("/surveys/{surveyId}/questions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @POST
    public Response addQuestion(@PathParam("surveyId") UUID surveyId,
                                @Valid AddQuestionRequest request,
                                @Context UriInfo uriInfo) {

        QuestionResponse created = questionService.addQuestion(surveyId, request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(created.id.toString())
                .build();

        return Response.created(location).entity(created).build();
    }

    @GET
    @Path("/{id}")
    public QuestionResponse getById(@PathParam("id") UUID id) {
        return questionService.getById(id);
    }
}