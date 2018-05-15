// Copyright 2017 Google Inc.

package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.data.Mention;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class MentionStore {
  /** Singleton instance of MentionStore. */
  private static MentionStore instance;

  /**
   * Returns the singleton instance of MentionStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static MentionStore getInstance() {
    if (instance == null) {
      instance = new MentionStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static MentionStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new MentionStore(persistentStorageAgent);
  }

  /** The PersistentStorageAgent responsible for loading Mentions and saving them to Datastore. */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Mentions. */
  private List<Mention> mentions;
  

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private MentionStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    mentions = new ArrayList<>();
  }

  /** Load a set of randomly-generated Mention objects. */
  public void loadTestData() {
    mentions.addAll(DefaultDataStore.getInstance().getAllMentions());
  }

  /**
   * Access the Mention object with the given UUID.
   *
   * @return null if the UUID does not match any existing Mentions.
   */
  public Mention getMentionById(UUID id) {
    for (Mention mention : mentions) {
      if (mention.getId().equals(id)) {
        return mention;
      }
    }
    return null;
  }
  
  /**
   * Access the current list of mentions by the given user UUID.
   *
   * @return an empty list if the user has not mentioned another user.
   */
  public List<Mention> getMentionsByUserId(UUID id) {
	  List<Mention> result = new ArrayList<>();
	  for (Mention mention : mentions) {
		  if (mention.getUserWhoDidTheMentioning().equals(id)) {
			  result.add(mention);
		  }
	  }
	  return result;	  
  }
  
  /**
   * Access the current list of mentions for the given user UUID.
   *
   * @return an empty list if the user has been mentioned by another user.
   */
  public List<Mention> getMentionsForUserId(UUID id) {
	  List<Mention> result = new ArrayList<>();
	  for (Mention mention : mentions) {
		  if (mention.getUserWhoWasMentioned().equals(id)) {
			  result.add(mention);
		  }
	  }
	  return result;
  }
  

  /** Add/replace a new mention to the current set of mentions known to the application. */
  public void addMention(Mention mention) {
    Mention original = getMentionById(mention.getId());
    if (original != null) {
      mentions.remove(original);
    }
    mentions.add(mention);
    persistentStorageAgent.writeThrough(mention);
  }

  /** Access the current set of Mentions within the given Message. */
  public List<Mention> getMentionsInMessage(UUID messageId) {

    List<Mention> mentionsInConversation = new ArrayList<>();

    for (Mention mention : mentions) {
      if (mention.getId().equals(messageId)) {
        mentionsInConversation.add(mention);
      }
    }

    return mentionsInConversation;
  }

  /** Sets the List of Mentions stored by this MentionStore. */
  public void setMentions(List<Mention> mentions) {
    this.mentions = mentions;
  }
}
