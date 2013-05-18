package it.webookia.backend.controller.resources;

import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.StorageFacade;

public class UserResource {

    // Static reference to an instance of storage facade to get/store users
    private static StorageFacade<UserEntity> userStorage =
        new StorageFacade<UserEntity>(UserEntity.class);

    // User accessed through this instance of UserResource
    private UserEntity decoratedUser;

    /**
     * Returns an user given it's id.
     * 
     * @param id
     *            - the id of the user.
     * @return an instance of UserResource which allows to manage selected user,
     *         null if user doesn't exsist.
     * */
    public static UserResource getUser(String id) {
        // UserEntity result = userStorage.get(id);
        // if (result == null) {
        // throw new ResourceException(ResourceErrorType.NOT_FOUND);
        // } else {
        // return new UserResource(result);
        // }
        return null;
    }

    /**
     * Creates an user given an id and accessToken
     * 
     * @param id
     *            - the user id
     * @param accessToken
     *            - the accessToken used to access user informations.
     * */
    public static UserResource createUser(String id, String fbAccessToken) {
        // UserEntity user = userStorage.get(id);
        //
        // if (user != null) {
        // /*
        // * If this happens the cause is a bad implementation of the
        // * application interface
        // */
        // throw new ResourceException(ResourceErrorType.SERVER_FAULT);
        // }
        //
        // UserEntity newUser = new UserEntity(id, fbAccessToken);
        // userStorage.persist(newUser);
        //
        // return new UserResource(newUser);
        return null;
    }

    // Only other resources can create directly instances. Higher level
    // components can't manage entities directly so constructors are useless for
    // them.
    protected UserResource(UserEntity u) {
        this.decoratedUser = u;
    }

    boolean matches(UserEntity user) {
        return this.decoratedUser.equals(user);
    }

    UserEntity getEntity() {
        return decoratedUser;
    }

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