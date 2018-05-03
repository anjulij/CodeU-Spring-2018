// Copyright 2017 Google Inc.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/**
The Mention class represent just a single mention and collects the data for a single mention.

 */
public class Mention {

	  private final UUID userWhoWasMentioned;// the id of the user who was mentioned
	  private final UUID userWhoDidTheMentioning; // the id of the user who did the mentioning
	  private final int start;// represents the lower bound of the number of String Indices
	  private final int end;// represents the upper bound of the number of String Indices
	  private final Instant creationTime;// represents the creation time of this Mention
	  
	  
	  public Mention(UUID userWhoWasMentioned,UUID userWhoDidTheMentioning, int start, int end) {
	    this.userWhoWasMentioned = userWhoWasMentioned;
	    this.userWhoDidTheMentioning = userWhoDidTheMentioning;
	    this. numberOfStringIndices = numberOfStringIndices;
	  }

	  /** Returns id of the user who was mentioned. */
	  public UUID getUserWhoWasMentioned() {
	    return userWhoWasMentioned;
	  }

	  /** Returns the the id of the user who did the mentioning */
	  public UUID getUserWhoDidTheMentioning() {
		  return userWhoDidTheMentioning;
	  }


	  /** Returns the lower bound of the number of String Indices */
	  public int getEnd() {
	    return end;
	  }
	  
	  /** Returns the upper bound of the number of String Indices */
	  public int getStart getNumberOfStringIndices() {
	    return start;
	  }
	  
	  /** Returns the creation time of this mention. */
	  public Instant getCreationTime() {
	    return creationTime;
	  }
	  
	}
