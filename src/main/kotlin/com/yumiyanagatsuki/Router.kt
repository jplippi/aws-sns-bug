package com.yumiyanagatsuki

import java.io.InputStream
import javax.inject.Singleton
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
class Router : ExceptionMapper<Throwable> {

    @GET
    @Path("/ping")
    fun ping(): String {
        return "Pong!"
    }

    @POST
    @Path("/handle")
    fun handle(stream: InputStream): Boolean {
        return Process().handle(stream)
    }

    @POST
    @Path("/send")
    fun send(): Boolean {
        return Process().send(true)
    }

    @POST
    @Path("/send-without-special")
    fun sendWithoutSpecial(): Boolean {
        return Process().send(false)
    }

    override fun toResponse(e: Throwable?): Response {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ExceptionModel(e?.localizedMessage))
            .type(MediaType.APPLICATION_JSON)
            .build()
    }

    class ExceptionModel(val message: String?)

}

