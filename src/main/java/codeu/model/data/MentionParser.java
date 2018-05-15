package codeu.model.data;

import codeu.model.store.basic.UserStore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MentionParser {
    private Message message;
    private UserStore userStore;

    private MentionParser(Message message, UserStore userStore) {
        message = this.message;
        userStore = this.userStore;
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
            else if(content.charAt(i) == ' '){
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
            sb.append(content.charAt(i));
        }

        String userNameMentioned = sb.toString();
        User userMentioned = userStore.getUser(userNameMentioned);
        UUID userMentionedID = userMentioned.getId();

        if((userMentionedID != null)){
            mention = new Mention(
                    UUID.randomUUID(),
                    userMentionedID,
                    m.getAuthorId(),
                    start,
                    end,
                    m.getCreationTime(),
                    m);
        }
        return mention;
    }
    //Could be moved to UserStore
    public UUID searchForUser(String username){
        UUID userID = null;


        for (User user : userStore.getAllUsers()) {
            if(username.equalsIgnoreCase(user.getName())){
                userID = user.getId();
            }
        }

        return userID;
    }
}