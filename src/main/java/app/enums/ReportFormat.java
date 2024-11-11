package app.enums;

import lombok.Getter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;

@Getter
public enum ReportFormat {
    PDF(DefaultDocumentFormatRegistry.PDF),
    DOCX(DefaultDocumentFormatRegistry.DOCX),
    DOC(DefaultDocumentFormatRegistry.DOC),
    XLSX(DefaultDocumentFormatRegistry.XLSX),
    XLS(DefaultDocumentFormatRegistry.XLS),
    ODT(DefaultDocumentFormatRegistry.ODT),
    ODS(DefaultDocumentFormatRegistry.ODS)

    ;

    private final DocumentFormat format;

    ReportFormat(DocumentFormat format) {
        this.format = format;
    }
}
