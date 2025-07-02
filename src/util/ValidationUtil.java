package util;

import exception.BadRequestException;

public class ValidationUtil {

    public static void requireNonEmpty(String fieldValue, String fieldName) {
        if (fieldValue == null || fieldValue.trim().isEmpty()) {
            throw new BadRequestException(fieldName + " tidak boleh kosong");
        }
    }

    public static void requireEmailFormat(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new BadRequestException("Format email tidak valid");
        }
    }

    public static void requirePhoneFormat(String phone) {
        if (phone == null || !phone.matches("^[0-9]{8,15}$")) {
            throw new BadRequestException("Format nomor telepon tidak valid (hanya angka 8–15 digit)");
        }
    }
}
