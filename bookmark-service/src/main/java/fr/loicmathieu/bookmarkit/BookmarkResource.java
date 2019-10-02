package fr.loicmathieu.bookmarkit;

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
    public List<Bookmark> listAll(){
        return Bookmark.listAll();
    }

    @GET
    @Path("/{id}")
    public Bookmark get(@PathParam("id") Long id) {
        return Bookmark.findById(id);
    }

    @POST
    @Transactional
    public Response create(Bookmark bookmark){
        bookmark.persist();
        return Response.created(URI.create("/bookmarks/" + bookmark.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
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
    public void delete(@PathParam("id") Long id){
        Bookmark.findById(id).delete();
    }
}