package com.udemy.resources;

import com.google.common.base.Optional;
import com.udemy.db.BookmarkDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.print.Book;
import java.util.List;

@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarksResource {
    private BookmarkDAO dao;

    public BookmarksResource(BookmarkDAO dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    public List<Bookmark> getBookmarks(@Auth User user) {
        return dao.findForUser(user.getId());
    }

    private Optional<Bookmark> findIfAuthorized(long bookmarkId, long userId) {
        Optional<Bookmark> result = dao.findById(bookmarkId);
        if(result.isPresent() && userId != result.get().getUser().getId()) {
            throw new ForbiddenException("You are not authorized.");
        }
        return result;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Bookmark> getBookmark(@PathParam("id") LongParam id, @Auth User user) {
        return findIfAuthorized(id.get(), user.getId());
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<Bookmark> delete(@PathParam("id") LongParam id, @Auth User user) {
        Optional<Bookmark> bookmark = findIfAuthorized(id.get(), user.getId());
        if(bookmark.isPresent()) {
            dao.delete(bookmark.get());
        }
        return bookmark;
    }

    @POST
    @UnitOfWork
    public Bookmark saveBookmark(Bookmark bookmark, @Auth User user) {
        bookmark.setUser(user);
        return dao.save(bookmark);
    }
}