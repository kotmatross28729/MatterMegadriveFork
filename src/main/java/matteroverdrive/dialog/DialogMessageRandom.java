package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogMessageSeedable;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DialogMessageRandom extends DialogMessage implements IDialogMessageSeedable {
    public static Random random = new Random();
    protected long seed;
    protected List<String> messages;
    protected List<String> questions;


    public DialogMessageRandom() {
        super();
        init();
    }

    public DialogMessageRandom(String message, String question) {
        super(message, question);
        init();
    }

    public DialogMessageRandom(String message) {
        super(message);
        init();
    }

    private void init() {
        questions = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public DialogMessageRandom addQuestionVariation(String question) {
        questions.add(question);
        return this;
    }

    public DialogMessageRandom addQuestionVariation(String... questions) {
        Collections.addAll(this.questions, questions);
        return this;
    }

    public DialogMessageRandom addMessageVariation(String message) {
        this.messages.add(message);
        return this;
    }

    public DialogMessageRandom addMessageVariation(String... messages) {
        Collections.addAll(this.messages, messages);
        return this;
    }

    @Override
    public String getMessageText(IDialogNpc npc, EntityPlayer player) {
        if (messages.size() > 0) {
            random.setSeed(seed);
            return formatMessage(messages.get(random.nextInt(messages.size())), npc, player);
        }
        return message;
    }

    @Override
    public String getQuestionText(IDialogNpc npc, EntityPlayer player) {
        if (questions.size() > 0) {
            random.setSeed(seed);
            return formatQuestion(questions.get(random.nextInt(questions.size())), npc, player);
        }
        return question;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public DialogMessage loadMessageFromLocalization(String key) {
        addMessageVariation(MOStringHelper.translateToLocal(key).split(";"));
        return this;
    }

    @Override
    public DialogMessage loadQuestionFromLocalization(String key) {
        addQuestionVariation(MOStringHelper.translateToLocal(key).split(";"));
        return this;
    }
}
