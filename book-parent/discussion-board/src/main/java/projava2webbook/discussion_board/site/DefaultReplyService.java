package projava2webbook.discussion_board.site;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import projava2webbook.discussion_board.site.entity.Discussion;
import projava2webbook.discussion_board.site.entity.Reply;

@Service
public class DefaultReplyService implements ReplyService {

    @Inject
    ReplyRepository replyRepository;
    @Inject
    DiscussionService discussionService;
    @Inject
    NotificationService notificationService;

    @Override
    public List<Reply> getRepliesForDiscussion(long discussionId) {
        List<Reply> list = this.replyRepository.getForDiscussion(discussionId);
        list.sort((r1, r2) -> r1.getId() < r2.getId() ? -1 : 1);
        return list;
    }

    @Override
    public void saveReply(Reply reply) {
        Discussion discussion = this.discussionService.getDiscussion(reply.getDiscussionId());

        if (reply.getId() < 1) { // If the reply is new (not saved yet)
            discussion.getSubscribedUsers().add(reply.getUser());
            reply.setCreated(Instant.now());
            this.replyRepository.add(reply);

            Set<String> recipients = new HashSet<>(discussion.getSubscribedUsers());
            recipients.remove(reply.getUser());

            this.notificationService.sendNotification("Reply posted",
                    "Someone replied to \"" + discussion.getSubject() + ".\"", recipients);
        } else {
            this.replyRepository.update(reply);
        }
        this.discussionService.saveDiscussion(discussion);
    }

}
