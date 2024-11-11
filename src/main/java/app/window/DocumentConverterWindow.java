package app.window;

import app.converter.LocalDocumentConverter;
import app.enums.ReportFormat;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DocumentConverterWindow {

    private static final float FONT_SIZE = 16f;

    @Getter
    private JFrame jFrame;

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTextArea inputTextArea;

    private JFileChooser fileChooser;

    private JButton button;

    private JTextField textField;

    private JComboBox<String> formatComboBox;

    private File selectedFile;

    private final LocalDocumentConverter converter;

    public DocumentConverterWindow(LocalDocumentConverter converter) {
        this.converter = converter;
        this.setupJFrame();
        this.createTab1();

        this.setFont(tabbedPane);

        final Container container = this.jFrame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(tabbedPane);

        this.jFrame.pack();
    }

    private void setupJFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        // 獲取螢幕解析度
        this.jFrame = new JFrame("DocumentConverterWindow");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 設定視窗大小佔螢幕四分之一
//        this.jFrame.setSize(dimension.width / 2, dimension.height / 2);
        this.jFrame.setSize(500, 500);
        this.jFrame.setResizable(false);

        final int x = (int) ((dimension.getWidth() - this.jFrame.getWidth()) / 2);
        final int y = (int) ((dimension.getHeight() - this.jFrame.getHeight()) / 2);
        this.jFrame.setLocation(x, y);
    }

    private void createTab1() {
        final List<JPanel> panels = new ArrayList<JPanel>();
        this.createInputBlock(panels);
        this.createFormatBlock(panels);
        this.createExecuteButton(panels);

        final JPanel jp = new JPanel(new GridLayout(panels.size(), 1));
        for (final JPanel panel : panels) {
            jp.add(panel);
        }

        //將多行文字框，加到滾動捲軸面板中
        final JScrollPane jScrollPane = new JScrollPane(jp);
        tabbedPane.add("convert", jScrollPane);
    }

    private void createInputBlock(final List<JPanel> panels) {

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JButton button = new JButton("選擇檔案");
        button.addActionListener(ae -> {
            this.fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                this.selectedFile = fileChooser.getSelectedFile();
                this.textField.setText(selectedFile.getAbsolutePath());
                log.debug(selectedFile.getName());
            }
        });

        final JLabel label = new JLabel("來源檔案：");
        this.setFont(label);
        panel.add(label);

        this.textField = new JTextField(50);
        this.setFont(textField);
        panel.add(textField);

        this.setFont(button);
        panel.add(button);
        panels.add(panel);
    }

    private void createFormatBlock(final List<JPanel> panels) {

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel label = new JLabel("轉檔格式：");
        this.setFont(label);
        panel.add(label);

        final String[] formats = Arrays.stream(ReportFormat.values())
                .map(ReportFormat::name)
                .map(String::toLowerCase)
                .toArray(String[]::new);

        this.formatComboBox = new JComboBox<>(formats);
        this.setFont(this.formatComboBox);
        panel.add(this.formatComboBox);
        panels.add(panel);
    }

    private void createExecuteButton(final List<JPanel> panels) {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel label = new JLabel("  ");

        this.button = new JButton("轉檔");
        this.setFont(this.button);
        this.button.addActionListener(event -> {
            try {
                this.checkFile();
                this.converter.convertDocument(selectedFile, (String) formatComboBox.getSelectedItem());

                log.info("done");
                JOptionPane.showMessageDialog(DocumentConverterWindow.this.jFrame, "處理成功");
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(DocumentConverterWindow.this.jFrame, e.getMessage());
            }
        });
        this.addEnterKeyListener(this.button);

        panel.add(label);
        panel.add(this.button);
        panels.add(panel);
    }

    private void checkFile() {
        if (this.selectedFile == null) {
            throw new RuntimeException("請選擇檔案");
        }
        if (this.selectedFile.isDirectory()) {
            throw new RuntimeException("請選擇檔案，而非資料夾");
        }
        if (!this.selectedFile.exists()) {
            throw new RuntimeException("檔案不存在");
        }
    }

    private void addEnterKeyListener(final JButton button) {
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }

    /**
     * 設定字大小
     */
    private void setFont(final Component c) {
        c.setFont(c.getFont().deriveFont(FONT_SIZE));
    }
}
