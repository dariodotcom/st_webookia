package it.webookia.backend.controller.resources;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.storage.StorageFacade;
import it.webookia.backend.utils.storage.StorageQuery;

public class UserResource {

    // Static reference to an instance of storage facade to get/store users
    private static StorageFacade<UserEntity> userStorage =
        new StorageFacade<UserEntity>(UserEntity.class);

    // Class Methods
    /**
     * Creates a new user given its Facebook {@link AccessToken}.
     * 
     * @param token
     *            - the user Facebook {@link AccessToken}
     * @return an {@link UserResource} to manage created user.
     * @throws {@link ResourceException} if a user with the same username as the
     *         one represented by the access token already exists.
     */
    public static UserResource createUser(AccessToken token)
            throws ResourceException {
        UserEntity user = new FacebookConnector(token).createUserEntity();

        String username = user.getId();
        if (StorageQuery.getUserByUsername(username) != null) {
            throw new ResourceException(ResourceErrorType.ALREADY_EXSISTING);
        }

        userStorage.persist(user);
        return new UserResource(user);
    }

    /**
     * Returns an user given its username.
     * 
     * @param username
     *            - the username of the user.
     * @return an instance of UserResource which allows to manage selected user,
     *         null if user doesn't exist.
     * @throws ResourceException
     *             if an user with given username doesn't exist.
     * */
    public static UserResource getUser(String username)
            throws ResourceException {
        UserEntity user = StorageQuery.getUserByUsername(username);
        if (user == null) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND);
        }

        return new UserResource(user);
    }

    // User accessed through this instance of UserResource
    private UserEntity decoratedUser;

    // Only other resources can create directly instances. Higher level
    // components can't manage entities directly so constructors are useless for
    // them.
    UserResource(UserEntity u) {
        this.decoratedUser = u;
    }

    boolean matches(UserEntity user) {
        return this.decoratedUser.equals(user);
    }

    UserEntity getEntity() {
        return decoratedUser;
    }

    boolean isFriendWith(UserEntity user) {
        FacebookConnector connector =
            new FacebookConnector(decoratedUser.getToken());
        return connector.getFriends().contains(user);
    }

    // Public methods

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime
                * result
                + ((decoratedUser == null) ? 0 : decoratedUser.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserResource)) {
            return false;
        }

        UserResource casted = (UserResource) obj;
        return casted.decoratedUser.equals(decoratedUser);
    }
}