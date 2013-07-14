package it.webookia.backend.controller.resources;

import java.util.List;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.descriptor.Descriptor;
import it.webookia.backend.descriptor.DescriptorFactory;
import it.webookia.backend.descriptor.ListDescriptor;
import it.webookia.backend.descriptor.LoanDescriptor;
import it.webookia.backend.descriptor.SingleFeedbackDescriptor;
import it.webookia.backend.descriptor.UserDescriptor;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnectorException;
import it.webookia.backend.utils.storage.StorageFacade;
import it.webookia.backend.utils.storage.StorageQuery;

/**
 * Class to manage user entities.
 * 
 */
public class UserResource {

    // Static reference to an instance of storage facade to get/store users
    private static StorageFacade<UserEntity> userStorage =
        new StorageFacade<UserEntity>(UserEntity.class);

    // User accessed through this instance of UserResource
    private UserEntity decoratedUser;

    /**
     * Class constructor -- Only other resources can create directly instances.
     * Higher level components can't manage entities directly so constructors
     * are useless for them.
     * 
     * @param u
     */
    UserResource(UserEntity u) {
        this.decoratedUser = u;
    }

    /**
     * Authenticates an user by its Access Token. If no user corresponding to
     * given access token exists, a new user is created.
     * 
     * @param token
     *            - the user Facebook {@link AccessToken}
     * @return an {@link UserResource} to manage the authenticated user.
     * 
     * @throws ResourceException
     *             if an error occurs while retrieving informations from
     *             Facebook.
     */
    public static UserResource authenticateUser(AccessToken token)
            throws ResourceException {
        FacebookConnector connector = FacebookConnector.forToken(token);
        String id = connector.getUserId();
        UserEntity entity = StorageQuery.getUserById(id);

        try {
            // First time a user logs in
            if (entity == null) {
                entity = new UserEntity();
                entity.setUserId(id);
                entity.setToken(token);
                entity.setName(connector.getFirstName());
                entity.setSurname(connector.getLastName());
                entity.setLocation(connector.getLocation());
                entity.setThumbnailUrl(connector.getThumbnail());
                entity.setPictureUrl(connector.getProfilePicture());

                // Update friends
                List<String> friendIds = connector.getFriendIds();
                System.out.println("checking for "
                    + friendIds.size()
                    + " friends.");

                for (String friendId : friendIds) {
                    UserEntity friend = StorageQuery.getUserById(friendId);

                    System.out.println("checking " + friendId);
                    if (friend == null) {
                        System.out.println(friendId + " is not on Webookia");
                        continue;
                    }

                    // Persist friendship
                    friend.addFriend(entity);
                    userStorage.persist(friend);
                    entity.addFriend(friend);
                    System.out.println(friendId
                        + " and "
                        + id
                        + " are now friends");
                    System.out.println(friend.getFriendList());
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                }

                userStorage.persist(entity);
                System.out.println(entity.getFriendList());
            }
        } catch (FacebookConnectorException e) {
            throw new ResourceException(ResourceErrorType.CONNECTOR_ERROR, e);
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
     *             if malformed user id is given
     * */
    public static UserResource getUser(String userId) throws ResourceException {
        if (userId == null) {
            return null;
        }

        UserEntity user = StorageQuery.getUserById(userId);
        if (user == null) {
            String message = "User " + userId + " not found";
            throw new ResourceException(ResourceErrorType.NOT_FOUND, message);
        }

        return new UserResource(user);
    }

    /**
     * Issues an update of fields with name contained in the given list,
     * retrieving new value from facebook.
     * 
     * @param changedFields
     *            - the list of the names of the fields to update.
     * @throws ResourceException
     *             if an error occurs while retrieving informations from
     *             Facebook.
     */
    public void updateFields(List<String> changedFields)
            throws ResourceException {
        FacebookConnector connector = FacebookConnector.forUser(decoratedUser);

        try {

            if (changedFields.contains("first_name")) {
                decoratedUser.setName(connector.getFirstName());
            }

            if (changedFields.contains("last_name")) {
                decoratedUser.setSurname(connector.getLastName());
            }

            if (changedFields.contains("location")) {
                decoratedUser.setLocation(connector.getLocation());
            }

            if (changedFields.contains("picture")) {
                decoratedUser.setThumbnailUrl(connector.getThumbnail());
            }
        } catch (FacebookConnectorException e) {
            throw new ResourceException(ResourceErrorType.CONNECTOR_ERROR, e);
        }

        userStorage.persist(decoratedUser);
    }

    /**
     * Retrieves a {@link Descriptor} that describes the profile of managed
     * user.
     * 
     * @return a {@link Descriptor} of managed user.
     */
    public UserDescriptor getDescriptor() {
        return DescriptorFactory.createUserDescriptor(decoratedUser);
    }

    /**
     * Retrieves a list of {@link Descriptor} of books belonging to the managed
     * user.
     * 
     * @return a {@link Descriptor} of books.
     */
    public ListDescriptor<BookDescriptor> getUserBooks() {
        return DescriptorFactory.createBookListDescriptor(decoratedUser
            .getOwnedBooks());
    }

    /**
     * Retrieves a list of {@link Descriptor} of books belonging to the managed
     * user that are visible to requestor.
     * 
     * @param requestor
     *            - the requestor.
     * @return a {@link Descriptor} of books.
     */
    public ListDescriptor<BookDescriptor> getVisibleBooks(UserResource requestor) {
        UserEntity requestorEntity =
            requestor == null ? null : requestor.getEntity();
        List<ConcreteBook> visibleBooks =
            StorageQuery.getVisibleBooks(decoratedUser, requestorEntity);

        return DescriptorFactory.createBookListDescriptor(visibleBooks);
    }

    /**
     * Retrieves a list of {@link Descriptor} of feedback belonging to the
     * managed user as owner.
     * 
     * @return a {@link Descriptor} of feedbacks.
     */
    public ListDescriptor<SingleFeedbackDescriptor> getFeedbacksAsOwner() {
        List<Feedback> feedbacks =
            StorageQuery.getFeedbacksAsOwner(decoratedUser);
        return DescriptorFactory.createFeedbackListDescriptor(feedbacks);
    }

    /**
     * Retrieves a list of {@link Descriptor} of feedback belonging to the
     * managed user as borrower.
     * 
     * @return a {@link Descriptor} of feedbacks.
     */
    public ListDescriptor<SingleFeedbackDescriptor> getFeedbacksAsBorrower() {
        List<Feedback> feedbacks =
            StorageQuery.getFeedbacksAsBorrower(decoratedUser);
        return DescriptorFactory.createFeedbackListDescriptor(feedbacks);
    }

    /**
     * Retrieves a {@link Descriptor} that describes the notifications received
     * by the user.
     * 
     * @param requestor
     *            is the user logged in
     * @return a {@link Descriptor} of received notifications.
     * @throws ResourceException
     *             when requestor is not managed user.
     */
    public Descriptor getNotifications(UserResource requestor)
            throws ResourceException {
        if (requestor == null) {
            throw new ResourceException(
                ResourceErrorType.NOT_LOGGED_IN,
                "You need to be logged in");
        }

        if (!requestor.matches(decoratedUser)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "You cannot see this user's notifications");
        }

        List<Notification> list =
            StorageQuery.getNotificationOf(decoratedUser, 15);
        return DescriptorFactory.createNotificationList(list);
    }

    /**
     * Retrieves the number of unread notifications received by the user.
     * 
     * @param requestor
     *            is the user logged in
     * @return the number of unread notifications
     * @throws ResourceException
     *             when requestor is not managed user.
     */
    public int getUnreadNotificationCount(UserResource requestor)
            throws ResourceException {
        if (requestor == null) {
            throw new ResourceException(
                ResourceErrorType.NOT_LOGGED_IN,
                "You need to be logged in");
        }

        if (!requestor.matches(decoratedUser)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "You cannot see this user's notifications");
        }

        return StorageQuery.getNotificationCount(decoratedUser);
    }

    /**
     * Retrieves a {@link Descriptor} that describes a list of received loan
     * requests by the user.
     * 
     * @param page
     *            number of results to be shown in the same page
     * @return a {@link Descriptor} of the received loan requests.
     */
    public ListDescriptor<LoanDescriptor> getReceivedLoanRequest(int page) {
        List<Loan> loanList =
            StorageQuery.getUserReceivedLoans(decoratedUser, page);
        return DescriptorFactory.createLoanListDescriptor(loanList);
    }

    /**
     * Retrieves a {@link Descriptor} that describes a list of sent loan
     * requests by the user.
     * 
     * @param page
     *            number of results to be shown in the same page
     * @return a {@link Descriptor} of the sent loan requests.
     */
    public ListDescriptor<LoanDescriptor> getSentLoanRequest(int page) {
        List<Loan> loanList =
            StorageQuery.getUserSentLoans(decoratedUser, page);
        return DescriptorFactory.createLoanListDescriptor(loanList);
    }

    /**
     * Retrieves the id of the managed user.
     * 
     * @return the id
     */
    public String getUserId() {
        return decoratedUser.getUserId();
    }

    // ## Package visible methods, this can be used by other resources.

    /**
     * Retrieves the concrete user associated to the user resource.
     * 
     * @return the decorated user
     */
    UserEntity getEntity() {
        return decoratedUser;
    }

    /**
     * Verifies if the current instance of user resource matches with the
     * concrete user.ú
     * 
     * @param user
     *            the current user to be verifed
     * @return true if the concrete user matches with the user resource, false
     *         otherwise.
     */
    boolean matches(UserEntity user) {
        return this.decoratedUser.equals(user);
    }

    /**
     * Verifies if the current instance of user resource is friend with the
     * concrete user.
     * 
     * @param user
     *            the instance of the user resource.
     * @return true if they are friends, false otherwise.
     */
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