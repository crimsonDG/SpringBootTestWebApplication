package com.admin.excel;

import com.core.model.KeycloakCredentialDto;
import com.core.model.KeycloakEntityDto;
import com.core.model.KeycloakRoleDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class ExcelWriter {

    static String[] HEADER = {
            "ID",
            "Username",
            "Email",
            "Email Constraint",
            "Last name",
            "First name",
            "Realm ID",
            "Service account client link",
            "Federation link",
            "Email verified",
            "Timestamp",
            "Enabled",
            "Not before",
            "Roles",
            "Credentials"};

    static String SHEET = "Users";

    public static ByteArrayInputStream usersToExcel(List<KeycloakEntityDto> users) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow = sheet.createRow(0);

            IntStream.range(0, HEADER.length)
                    .forEach(col -> headerRow.createCell(col).setCellValue(HEADER[col]));

            IntStream.range(0, users.size()).forEach(idx -> {
                Row row = sheet.createRow(idx + 1);
                KeycloakEntityDto user = users.get(idx);

                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getEmailConstraint());
                row.createCell(4).setCellValue(user.getLastName());
                row.createCell(5).setCellValue(user.getFirstName());
                row.createCell(6).setCellValue(user.getRealmId());
                row.createCell(7).setCellValue(user.getServiceAccountClientLink());
                row.createCell(8).setCellValue(user.getFederationLink());
                row.createCell(9).setCellValue(user.isEmailVerified());
                row.createCell(10).setCellValue(user.getCreatedTimestamp());
                row.createCell(11).setCellValue(user.isEnabled());
                row.createCell(12).setCellValue(user.getNotBefore());
                row.createCell(13).setCellValue(getStringFromRoles(user.getRoles()));
                row.createCell(14).setCellValue(getStringFromCredentials(user.getCredentials()));
            });

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    private static String getStringFromRoles(Set<KeycloakRoleDto> roles) {
        return roles.stream()
                .map(KeycloakRoleDto::getName)
                .reduce("", (acc, role) -> acc + role);
    }

    private static String getStringFromCredentials(List<KeycloakCredentialDto> credentials) {
        return credentials.stream()
                .map(KeycloakCredentialDto::getSecretData)
                .reduce("", (acc, credential) -> acc + credential);
    }

}