package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmojiPicker extends JDialog {
    private JList<String> emojiList;
    private DefaultListModel<String> emojiModel;
    private String selectedEmoji = "";

    public EmojiPicker(JFrame parent) {
        super(parent, "Select Emoji", true);
        setSize(250, 200);
        setLocationRelativeTo(parent);

        emojiModel = new DefaultListModel<>();
        // Một số emoji mẫu (có thể thêm nhiều)
        String[] emojis = {"😀", "😂", "😍", "👍", "🙏", "🎉", "😢", "🔥", "🥳", "💯"};
        for (String e : emojis) emojiModel.addElement(e);

        emojiList = new JList<>(emojiModel);
        emojiList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        emojiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        emojiList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selectedEmoji = emojiList.getSelectedValue();
                    dispose();
                }
            }
        });

        add(new JScrollPane(emojiList));
    }

    public String getSelectedEmoji() {
        return selectedEmoji;
    }
}
