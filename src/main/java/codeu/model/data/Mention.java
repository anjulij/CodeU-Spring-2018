// Copyright 2017 Google Inc.

package codeu.model.data;

import java.time.Instant;

/**
The Mention class adds the ability to mention a user. 
It also notifies users who have been mentioned
 */
public class Mention {

	  private final UUID idOfMentioner;// the id of this User mentioning another user
	  private final UUID idOfMentioned; // the id of the user being mentioned by this User
	  private final String creationOfMessage;  // allows this user to create a message, mentioning another user
	  private final String NotificationMessage; // the notification message where this user is being mentioned
	  private final String[] NotificationsOfMentions; // a string Array where each item is a string message(which is the
	  												//particular message where this User was mentioned
	 

	  public Mention(UUID idOfMentioner,UUID idOfMentioned, String creationOfMessage, String NotificationMessage, String[] NotificationsOfMentions) {
	    this.idOfMentioner = idOfMentioner;
	    this.idOfMentioned = idOfMentioned;
	    this.creationOfMessage = creationOfMessage;
	    this.NotificationMessage = NotificationMessage;
	    this.NotificationsOfMentions = NotificationsOfMentions;
	  }

	  /** Returns the ID of this User mentioning another user. */
	  public UUID getIdOfMentioner() {
	    return idOfMentioner;
	  }

	  /** Returns the ID of the other user that this User is mentioning */
	  public UUID getIdOfMentioneg() {
		  return idOfMentioned;
	  }

	  /** Returns the password of this User. */
	  public String getPassword() {
	    return password;
	  }

	  /** Returns message created by this User--must require "@" sign */
	  public String getCreationOfMessage {
	    return creationOfMessage;
	  }
	  
	  /** Returns the notification message where this user is being mentioned */
	  public String getNotificationMessagee {
	    return NotificationMessage;
	  }
	  
	  /** Returns a string Array where each item is a string message where this User was mentioned */
	  public String geNotificationsOfMentions {
	    return NotificationsOfMentionse;
	  }
	  
	  
	}
