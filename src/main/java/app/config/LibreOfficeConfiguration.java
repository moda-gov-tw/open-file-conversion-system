package app.config;

import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Slf4j
@Configuration
public class LibreOfficeConfiguration {

    @Bean(
            initMethod = "start",
            destroyMethod = "stop"
    )
    public OfficeManager officeManager() {
        try {
            return LocalOfficeManager.builder()
                    .portNumbers(2002, 2003, 2004, 2005)
                    .build();
        } catch (Exception e) {
            log.warn("OfficeManager init failed");
            return null;
        }
    }

    @Bean
    public DocumentConverter jodConverter(@Nullable final OfficeManager officeManager) {
        try {
            if (officeManager == null) {
                log.warn("OfficeManager not found");
                return null;
            }
            return LocalConverter.make(officeManager);
        } catch (Exception e) {
            log.warn("DocumentConverter init failed");
            return null;
        }
    }
}
