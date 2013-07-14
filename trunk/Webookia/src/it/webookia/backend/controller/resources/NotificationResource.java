package it.webookia.backend.controller.resources;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.model.Comment;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.StorageException;
import it.webookia.backend.utils.storage.StorageFacade;

/**
 * Class to manage notification entities.
 * 
 */
public class NotificationResource {

    // Storage accessory
    private static StorageFacade<Notification> notificationStorage =
        new StorageFacade<Notification>(Notification.class);

    private Notification decoratedNotification;

    /**
     * Class constructor
     * 
     * @param notification
     */
    NotificationResource(Notification notification) {
        this.decoratedNotification = notification;
    }

    /**
     * Creates a new notification.
     * 
     * @param target
     *            - the {@link UserResource} representing the target of the
     *            resource.
     * @param type
     *            - the {@link NotificationType} of the request.
     * @param context
     *            - the Entity context of the notification.
     * @return the created notification.
     * @throws {@link ResourceException} if an error occurs.
     */
    public static NotificationResource createNotification(UserResource target,
            NotificationType type, Object contextEntity)
            throws ResourceException {
        Notification notification = new Notification();
        notification.setReceiver(target.getEntity());
        notification.setType(type);

        UserEntity notificationSender;
        String entityID;

        try {
            if (type == NotificationType.NEW_REVIEW_COMMENT) {
                Comment comment = (Comment) contextEntity;
                notificationSender = comment.getAuthor();
                Review relatedReview = comment.getReview();
                ConcreteBook relatedBook = relatedReview.getReviewedBook();
                entityID = relatedBook.getId();
            } else {
                Loan loan = (Loan) contextEntity;
                UserEntity owner = loan.getLentBook().getOwner();
                notificationSender =
                    target.matches(owner) ? loan.getBorrower() : owner;
                entityID = loan.getId();
            }
        } catch (ClassCastException e) {
            throw new ResourceException(ResourceErrorType.SERVER_FAULT, e);
        }

        notification.setSender(notificationSender);
        notification.setTargetId(entityID);

        notificationStorage.persist(notification);
        return new NotificationResource(notification);
    }

    /**
     * Retrieves a {@link NotificationResource} by its id.
     * 
     * @param id
     *            - the id of the {@link Notification}
     * @return the {@link NotificationResource} that manages the desired
     *         notification.
     * @throws ResourceException
     *             if an error occurs.
     */
    public static NotificationResource getNotification(String id)
            throws ResourceException {
        Notification notification;
        try {
            notification = notificationStorage.get(id);
        } catch (StorageException e) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND, e);
        }

        if (notification == null) {
            String message = "notification " + id + "not found";
            throw new ResourceException(ResourceErrorType.NOT_FOUND, message);
        } else {
            return new NotificationResource(notification);
        }
    }

    /**
     * Marks the request as read.
     * 
     * @throws ResourceException
     *             if an error occurs
     */
    public void markAsRead(UserResource requestor) throws ResourceException {
        if (requestor == null) {
            throw new ResourceException(
                ResourceErrorType.NOT_LOGGED_IN,
                "You need to be logged in");
        }

        if (!requestor.matches(decoratedNotification.getReceiver())) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "You cannot see this user's notifications");
        }

        decoratedNotification.setRead(true);
        notificationStorage.persist(decoratedNotification);
    }
}
