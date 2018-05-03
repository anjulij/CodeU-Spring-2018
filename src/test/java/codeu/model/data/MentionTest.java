// Copyright 2017 Google Inc.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class MentionTest {
	@Test
	public void testCreate() {
		UUID userWhoWasMentioned = UUID.randomUUID();
		UUID userWhoDidTheMentioning = UUID.randomUUID();
		int start = 0;
		int end = 4;
		Instant creationTime = Instant.now();

		Mention mention = new Mention(userWhoWasMentioned, userWhoDidTheMentioning, start, end, creationTime);

		Assert.assertEquals(userWhoWasMentioned, mention.getUserWhoWasMentioned());
		Assert.assertEquals(userWhoDidTheMentioning, mention.getUserWhoDidTheMentioning());
		Assert.assertEquals(start, mention.getStart());
		Assert.assertEquals(end, mention.getEnd());
		Assert.assertEquals(creationTime, mention.getCreationTime());
	}
}

