package fr.loicmathieu.bookmarkit;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.print.Book;
import java.net.URI;
import java.util.List;

@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarkResource {

    @GET
    @Operation(summary = "List all bookmarks")
    @Counted(name = "listAll.count")
    @Timed(name = "listAll.time")
    public List<Bookmark> listAll(){
        return Bookmark.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a bookmark")
    @Counted(name = "get.count")
    @Timed(name="get.time")
    public Bookmark get(@PathParam("id") Long id) {
        return Bookmark.findById(id);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a bookmark")
    @Counted(name = "create.count")
    @Timed(name="create.time")
    public Response create(Bookmark bookmark){
        bookmark.persist();
        return Response.created(URI.create("/bookmarks/" + bookmark.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a bookmark")
    @Counted(name = "update.count")
    @Timed(name="update.time")
    public void update(Bookmark bookmark){
        Bookmark toUpdate = Bookmark.findById(bookmark.id);
        toUpdate.title = bookmark.title;
        toUpdate.description = bookmark.description;
        toUpdate.url = bookmark.url;
        toUpdate.location = bookmark.location;
        toUpdate.persist();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a bookmark")
    @Counted(name = "delete.count")
    @Timed(name="delete.time")
    public void delete(@PathParam("id") Long id){
        Bookmark.findById(id).delete();
    }
}