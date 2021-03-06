package codeu.model.data;

import codeu.model.store.basic.UserStore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MentionParser {
    private Message message;
    private UserStore userStore;

    private MentionParser(Message message, UserStore userStore) {
        this.message = message;
        this.userStore = userStore;
    }

    public static MentionParser createParser(Message message, UserStore userStore){
        return new MentionParser(message, userStore);
    }
    public List<Mention> getMentions() {
        List<Mention> mentions = new ArrayList<>();
        String content = message.getContent();

        Mention mention = null;
        boolean inMention = false;
        int startOfMention = -1;
        int endOfMention = -1;
        
        for (int i = 0; i < content.length(); i++) {

            if(content.charAt(i)=='@'){
                if(inMention){
                    inMention = false;
                    break;
                }
                else if(i==0 || content.charAt(i-1)==' '){
                    inMention = true;
                    startOfMention = i;
                }
            } 
            else if(content.charAt(i) == ' ' || i == content.length() - 1){
                    if(inMention){
                        endOfMention = i;
                        mention = getMention(startOfMention, endOfMention, message);
                        if(!(mention == null)){
                            mentions.add(mention);
                        }
                        inMention = false;
                    }
                }
            }
        return mentions;
    }
    private Mention getMention(int s, int e, Message m) {
        String content = m.getContent();
        int start = s;
        int end = e;
        StringBuilder sb = new StringBuilder();

        Mention mention = null;

        for (int i = s; i < end; i++) {
        	if (content.charAt(i) != '@')
              sb.append(content.charAt(i));
        }

        String userNameMentioned = sb.toString();
        System.out.println(userNameMentioned);
        User userMentioned = userStore.getUser(userNameMentioned);
       
        if((userMentioned != null)){
        	UUID userMentionedID = userMentioned.getId();
            mention = new Mention(
                    UUID.randomUUID(),
                    userMentionedID,
                    m.getAuthorId(),
                    start,
                    end,
                    m.getCreationTime(),
                    m.getId());
        }
        return mention;
    }
}