package com.admin.excel;

import com.core.model.KeycloakCredentialDto;
import com.core.model.KeycloakEntityDto;
import com.core.model.KeycloakRoleDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelReader {
    public static List<KeycloakEntityDto> excelToObject(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        return IntStream.range(1, sheet.getPhysicalNumberOfRows())
                .mapToObj(rowIndex -> {
                    Row row = sheet.getRow(rowIndex);
                    KeycloakEntityDto user = new KeycloakEntityDto();
                    int i = row.getFirstCellNum();

                    user.setId(getStringCellValue(row, i, dataFormatter));
                    user.setUsername(getStringCellValue(row, ++i, dataFormatter));
                    user.setEmail(getStringCellValue(row, ++i, dataFormatter));
                    user.setEmailConstraint(getStringCellValue(row, ++i, dataFormatter));
                    user.setLastName(getStringCellValue(row, ++i, dataFormatter));
                    user.setFirstName(getStringCellValue(row, ++i, dataFormatter));
                    user.setRealmId(getStringCellValue(row, ++i, dataFormatter));
                    user.setServiceAccountClientLink(getStringCellValue(row, ++i, dataFormatter));
                    user.setFederationLink(getStringCellValue(row, ++i, dataFormatter));
                    user.setEmailVerified(getBooleanCellValue(row, ++i));
                    user.setCreatedTimestamp(getLongCellValue(row, ++i));
                    user.setEnabled(getBooleanCellValue(row, ++i));
                    user.setNotBefore(getIntCellValue(row, ++i));

                    user.setRoles(getRolesFromString(getStringCellValue(row, ++i, dataFormatter)));
                    user.setCredentials(getCredentialsFromString(getStringCellValue(row, ++i, dataFormatter)));

                    return user;
                })
                .collect(Collectors.toList());
    }

    private static String getStringCellValue(Row row, int cellIndex, DataFormatter dataFormatter) {
        Cell cell = row.getCell(cellIndex);
        return dataFormatter.formatCellValue(cell);
    }

    private static boolean getBooleanCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return cell.getBooleanCellValue();
    }

    private static long getLongCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (long) cell.getNumericCellValue();
    }

    private static int getIntCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (int) cell.getNumericCellValue();
    }

    private static Set<KeycloakRoleDto> getRolesFromString(String rolesString) {
        Set<KeycloakRoleDto> roles = new HashSet<>();
        KeycloakRoleDto role = new KeycloakRoleDto();
        role.setName(rolesString);
        roles.add(role);
        return roles;
    }

    private static List<KeycloakCredentialDto> getCredentialsFromString(String credentialsString) {
        List<KeycloakCredentialDto> credentials = new ArrayList<>();
        KeycloakCredentialDto credential = new KeycloakCredentialDto();
        credential.setSecretData(credentialsString);
        credentials.add(credential);
        return credentials;
    }

}
