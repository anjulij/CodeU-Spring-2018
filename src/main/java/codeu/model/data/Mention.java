// Copyright 2017 Google Inc.

package codeu.model.data;

import java.util.UUID;

/**
The Mention class represent just a single mention and collects the data for a single mention.

 */
public class Mention {

	  private final UUID userWhoWasMentioned;// the id of the user who was mentioned
	  private final UUID userWhoDidTheMentioning; // the id of the user who did the mentioning
	  private int[] numberOfStringIndices = new int[2]; ; // represents the bounds of the number of String Indices
	 

	  public Mention(UUID userWhoWasMentioned,UUID userWhoDidTheMentioning, int[] numberOfStringIndices) {
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


	  /** Returns he bounds of the number of String Indices */
	  public int[] getNumberOfStringIndices() {
	    return numberOfStringIndices;
	  }
	  
	}
