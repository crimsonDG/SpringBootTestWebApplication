package com.core.pdf;

import com.core.util.OsDefiner;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

@Service
public class PdfGenerator {

    @SneakyThrows
    public static byte[] generatePdfFromHtml(@NonNull String htmlFile) {
        String defaultPath = OsDefiner.getDefaultPath(OsDefiner.getOperatingSystemType());
        String filePath = defaultPath + "download.html";
        Path pathHtml = Paths.get(filePath);
        Path pathPdf = Paths.get(filePath.replace("html", "pdf"));

        try {
            String htmlContent = new Scanner(Objects.requireNonNull(PdfGenerator.class.getClassLoader().getResourceAsStream(htmlFile)),
                    StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();
            Files.write(pathHtml, htmlContent.getBytes());
            Process generateToPdf = Runtime.getRuntime().exec("wkhtmltopdf " + pathHtml + " " + pathPdf);
            generateToPdf.waitFor();
            return Files.readAllBytes(pathPdf);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Files.delete(pathHtml);
            Files.delete(pathPdf);
        }
    }
}