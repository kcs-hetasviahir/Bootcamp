package com.kcs.attendancesystem.utils;

import com.google.common.base.Preconditions;

public final class TOTPValidator {

    /** The default window verification size. */
    public static final int DEFAULT_WINDOW = 1;

    private final int window;

    /**
     * Creates a new instance of {@code TOTPValidator} initialized with the
     * specified {@code window} verification size.
     *
     * @param window
     *            the window verification size
     *
     * @throws IllegalArgumentException
     *             if {@code window} is < 0.
     */
    private TOTPValidator(int window) {
        Preconditions.checkArgument(window >= 0);
        this.window = window;
    }

    /**
     * Returns a new {@link TOTPValidator} instance initialized with the
     * {@link #DEFAULT_WINDOW} verification size.
     *
     * @return a new {@link TOTPValidator} instance.
     */
    public static TOTPValidator defaultWindow() {
        return window(DEFAULT_WINDOW);
    }

    /**
     * Returns a new {@link TOTPValidator} instance initialized with the
     * specified {@code window} verification size.
     *
     * @param window
     *            the window verification size
     *
     * @return a new {@link TOTPValidator} instance.
     *
     * @throws IllegalArgumentException
     *             if {@code window} is {@literal <} 0.
     */
    public static TOTPValidator window(int window) {
        return new TOTPValidator(window);
    }

    /**
     * Returns {@code true} if the specified TOTP {@code value} matches the
     * value of the TOTP generated at validation, otherwise {@code false}. The
     * current system time (current time in milliseconds since the UNIX epoch)
     * is used as the validation reference time.
     *
     * @param key
     *            the encoded shared secret key
     * @param timeStep
     *            the time step size in milliseconds
     * @param digits
     *            the number of digits a TOTP should contain
     * @param hmacShaAlgorithm
     *            {@link HmacShaAlgorithm}
     * @param value
     *            the TOTP value to validate
     *
     * @return {@code true} if the specified TOTP {@code code} value matches the
     *         code value of the TOTP generated at validation, otherwise
     *         {@code false}.
     */
    public boolean isValid(byte[] key, long timeStep, int digits, HmacShaAlgorithm hmacShaAlgorithm, String value) {
        return isValid(key, timeStep, digits, hmacShaAlgorithm, value, System.currentTimeMillis());
    }

    /**
     * Returns {@code true} if the specified TOTP {@code value} matches the
     * value of the TOTP generated at validation, otherwise {@code false}.
     *
     * @param key
     *            the encoded shared secret key
     * @param timeStep
     *            the time step size in milliseconds
     * @param digits
     *            the number of digits a TOTP should contain
     * @param hmacShaAlgorithm
     *            {@link HmacShaAlgorithm}
     * @param value
     *            the TOTP value to validate
     * @param validationTime
     *            the validation reference time in milliseconds
     *
     * @return {@code true} if the specified TOTP {@code code} value matches the
     *         code value of the TOTP generated at validation, otherwise
     *         {@code false}.
     */
    public boolean isValid(byte[] key, long timeStep, int digits, HmacShaAlgorithm hmacShaAlgorithm, String value, long validationTime) {
        boolean result = false;
        TOTPBuilder builder = TOTP.key(key).timeStep(timeStep).digits(digits).hmacSha(hmacShaAlgorithm);
        for (int i = -window; i <= window; i++) {
            final long time = validationTime + (i * timeStep);
            final TOTP vtotp = builder.build(time);
            if (vtotp.value().equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }
}