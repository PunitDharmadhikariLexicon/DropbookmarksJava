package com.udemy;

import com.udemy.auth.DBAuthenticator;
import com.udemy.resources.Bookmark;
import com.udemy.resources.User;
import com.udemy.db.BookmarkDAO;
import com.udemy.db.UserDAO;
import com.udemy.resources.BookmarksResource;
import com.udemy.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;



public class DropBookmarksApplication extends Application<DropBookmarksConfiguration> {

    private final HibernateBundle<DropBookmarksConfiguration> hibernateBundle = new HibernateBundle<DropBookmarksConfiguration>(User.class, Bookmark.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DropBookmarksConfiguration dropBookmarksConfiguration) {
            return dropBookmarksConfiguration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new DropBookmarksApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropBookmarks";
    }

    @Override
    public void initialize(final Bootstrap<DropBookmarksConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<DropBookmarksConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DropBookmarksConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final DropBookmarksConfiguration configuration,
                    final Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final BookmarkDAO bookmarkDAO = new BookmarkDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(
                new BookmarksResource(bookmarkDAO)
        );

        environment.jersey().register(
                new HelloResource()
        );

        environment.jersey().register(
                AuthFactory.binder(
                        new BasicAuthFactory<>(new DBAuthenticator(userDAO), "SECURITY REALM", User.class)
                )
        );
    }
}