package it.webookia.backend.controller.resources;

import java.util.List;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.Descriptor;
import it.webookia.backend.descriptor.DescriptorFactory;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.storage.StorageFacade;
import it.webookia.backend.utils.storage.StorageQuery;

public class UserResource {

    // Static reference to an instance of storage facade to get/store users
    private static StorageFacade<UserEntity> userStorage =
        new StorageFacade<UserEntity>(UserEntity.class);

    // ## Class Methods

    /**
     * Authenticates an user by its Access Token. If no user corresponding to
     * given access token exists, a new user is created.
     * 
     * @param token
     *            - the user Facebook {@link AccessToken}
     * @return an {@link UserResource} to manage the authenticated user.
     */
    public static UserResource authenticateUser(AccessToken token) {
        FacebookConnector connector = FacebookConnector.forToken(token);
        String id = connector.getUserId();
        UserEntity entity = StorageQuery.getUserById(id);

        if (entity == null) {
            entity = new UserEntity();
            entity.setUserId(id);
            entity.setToken(token);
            entity.setName(connector.getFirstName());
            entity.setSurname(connector.getLastName());
            userStorage.persist(entity);
        }

        return new UserResource(entity);
    }

    /**
     * Returns an user given its username.
     * 
     * @param userId
     *            - the user id of the user.
     * @return an instance of UserResource which allows to manage selected user,
     *         null if user doesn't exist.
     * @throws ResourceException
     *             if an user with given username doesn't exist.
     * */
    public static UserResource getUser(String userId) throws ResourceException {
        UserEntity user = StorageQuery.getUserById(userId);
        if (user == null) {
            String message = "User " + userId + " not found";
            throw new ResourceException(ResourceErrorType.NOT_FOUND, message);
        }

        return new UserResource(user);
    }

    // ## Instance Methods

    // User accessed through this instance of UserResource
    private UserEntity decoratedUser;

    // Only other resources can create directly instances. Higher level
    // components can't manage entities directly so constructors are useless for
    // them.
    UserResource(UserEntity u) {
        this.decoratedUser = u;
    }

    /**
     * Iussues an update of fields with name contained in the given list,
     * retrieving new value from facebook.
     * 
     * @param changedFields
     *            - the list of the names of the fields to update.
     */
    public void updateFields(List<String> changedFields) {
        FacebookConnector connector = FacebookConnector.forUser(decoratedUser);

        if (changedFields.contains("first_name")) {
            decoratedUser.setName(connector.getFirstName());
        }

        if (changedFields.contains("last_name")) {
            decoratedUser.setSurname(connector.getLastName());
        }

        // TODO [LOCALIZATION] Update user localization too.

        userStorage.persist(decoratedUser);
    }

    /**
     * Retrieves a {@link Descriptor} that describes the profile of managed
     * user.
     * 
     * @return a {@link Descriptor} of managed user.
     */
    public Descriptor getDescriptor() {
        return DescriptorFactory.createUserDescriptor(decoratedUser);
    }

    /**
     * Retrieves a {@link Descriptor} that describes the notifications received
     * by the user.
     * 
     * @return a {@link Descriptor} of received notifications.
     */
    public Descriptor getNotifications() {
        // TODO [NOTIFICATION] Implement searching of notification
        return null;
    }

    /**
     * @return the id of managed user.
     */

    public String getUserId() {
        return decoratedUser.getUserId();
    }

    // ## Package visible methods, this can be used by other resources.

    UserEntity getEntity() {
        return decoratedUser;
    }

    boolean matches(UserEntity user) {
        return this.decoratedUser.equals(user);
    }

    boolean isFriendWith(UserEntity user) {
        return StorageQuery.getUserFriends(decoratedUser).contains(user);
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