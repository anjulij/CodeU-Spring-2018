package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class MentionTest {
	@Test
	public void testCreate() {
        UUID idOfMention = UUID.randomUUID();
        UUID userWhoWasMentioned = UUID.randomUUID();
		UUID userWhoDidTheMentioning = UUID.randomUUID();
		int start = 0;
		int end = 4;
		Instant creationTime = Instant.now();

		UUID conversationID = UUID.randomUUID();
		UUID messageID = UUID.randomUUID();
		String content = "asdjsldj2jlnncc0ie2";

		Message message = new Message(messageID, conversationID, userWhoDidTheMentioning, content, creationTime);
		Mention mention = new Mention(
		        idOfMention, userWhoWasMentioned, userWhoDidTheMentioning, start, end, creationTime, message);

		Assert.assertEquals(idOfMention, mention.getId());
        Assert.assertEquals(userWhoWasMentioned, mention.getUserWhoWasMentioned());
        Assert.assertEquals(userWhoDidTheMentioning, mention.getUserWhoDidTheMentioning());
        Assert.assertEquals(start, mention.getStart());
        Assert.assertEquals(end, mention.getEnd());
        Assert.assertEquals(creationTime, mention.getCreationTime());
  }
}
