package app.converter;

import app.enums.ReportFormat;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
public class LocalDocumentConverter {

    private final Optional<DocumentConverter> converter;

    public LocalDocumentConverter(final Optional<DocumentConverter> documentConverter) {
        this.converter = documentConverter;
    }

    /**
     * 將檔案轉換成指定格式的檔案。
     * @param file 原始檔案
     * @param format 轉換格式
     * @return 轉換後的檔案
     */
    public File convertDocument(final File file, final String format) {
        return converter.map(c -> {
            final ReportFormat reportFormat = ReportFormat.valueOf(format.toUpperCase(Locale.TAIWAN));
            final File newFile = changeFileExtension(file, reportFormat.getFormat().getExtension());
            try {
                c.convert(file)
                        .to(newFile)
                        .execute();
            } catch (OfficeException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("文件轉換時發生錯誤", e);
            }
            return newFile;
        }).orElse(file);
    }

    private File changeFileExtension(final File file, final String newExtension) {
        final String fileName = file.getName();
        final int dotIndex = fileName.lastIndexOf('.');

        // 如果找不到副檔名，則直接加上新的副檔名
        if (dotIndex == -1) {
            return this.getFile(fileName, newExtension, file);
        } else {
            final String nameWithoutExtension = fileName.substring(0, dotIndex);
            return this.getFile(nameWithoutExtension, newExtension, file);
        }
    }

    private File getFile(final String fileName, final String newExtension, final File file) {
        File f = new File(file.getParent(), fileName + "." + newExtension);
        if (f.exists()) {
            f = new File(file.getParent(), fileName + "(1)." + newExtension);
            int no = 2;
            while (f.exists()) {
                f = new File(file.getParent(), String.format("%s(%d).%s", fileName, no, newExtension));
                no++;
            }
        }
        return f;
    }
}
