## 文件檔案格式轉換工具
> 這是一個使用 Java 17 開發的文件轉換程式，專門用於處理多種常見的文件格式。該程式整合了 LibreOffice 文件轉換工具，能夠高效地將 .docx、.xlsx、.xls、.pdf 等格式的文件轉換為 HTML、PDF、DOCX、XLSX、PPTX 等多種目標格式。
## 1.所用技術
- Spring Boot
- LibreOffice

## 2.安裝指南
> 請確保您的環境中安裝有 Java 17 、 Maven 3 、LibreOffice 24 以上版本，
```bat
# 執行步驟
> git clone https://gitlab.iisigroup.com/ps150/g-p231301/modapc/documentconverter.git
> cd documentConverter
> mvn clean package
> java -jar target/documentConverter-0.0.1-SNAPSHOT.jar
```
## 3.使用情境範例
> 將文件轉換為不同格式以提供使用者不同的文件格式需求。透過「文件檔案格式轉換工具」，可以將 Microsoft Office 文件（如 Word、Excel）轉換為 ODT、ODS或其他格式。

## 4.結果展示
> 可以透過介面選擇來源檔案，設定轉換格式<br/>
![image](https://gitlab.iisigroup.com/ps150/g-p231301/modapc/documentconverter/-/raw/main/demo/1.jpg?ref_type=heads)

> 執行轉檔<br/>
![image](https://gitlab.iisigroup.com/ps150/g-p231301/modapc/documentconverter/-/raw/main/demo/2.jpg?ref_type=heads)

> 轉檔結果<br/>
![image](https://gitlab.iisigroup.com/ps150/g-p231301/modapc/documentconverter/-/raw/main/demo/3.jpg?ref_type=heads)



