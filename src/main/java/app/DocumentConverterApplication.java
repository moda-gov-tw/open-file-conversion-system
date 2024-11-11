package app;

import app.converter.LocalDocumentConverter;
import app.window.DocumentConverterWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class DocumentConverterApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        final ConfigurableApplicationContext context = SpringApplication.run(DocumentConverterApplication.class, args);
        final LocalDocumentConverter converter = context.getBean(LocalDocumentConverter.class);

        EventQueue.invokeLater(() -> {
            final DocumentConverterWindow window = new DocumentConverterWindow(converter);
            window.getJFrame().setVisible(true);
        });
    }

}
