package com.kcs.attendancesystem.utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Range;
import com.google.common.io.BaseEncoding;

public final class TOTPBuilder {
    /**
     * The default time step size in milliseconds (30000 milliseconds == 30 seconds).
     */
    public static final long DEFAULT_TIME_STEP = TimeUnit.SECONDS.toMillis(30);

    /** The default number of digits the TOTP value contains. */
    public static final int DEFAULT_DIGITS = 6;

    /** The minimum allowed number of digits the TOTP value can contain. */
    public static final int MIN_ALLOWED_DIGITS = 6;

    /** The maximum allowed number of digits the TOTP value can contain. */
    public static final int MAX_ALLOWED_DIGITS = 8;

    /** The shared secret key. */
    private final byte[] key;

    /** The time step size (defaults to {@link #DEFAULT_TIME_STEP}). */
    private long timeStep = DEFAULT_TIME_STEP;

    /**
     * The number of digits the TOTP value contains (defaults to
     * {@link #DEFAULT_DIGITS}).
     */
    private int digits = DEFAULT_DIGITS;

    /**
     * The HMAC-SHA algorithm used in generating the TOTP value (defaults to
     * {@code HMAC-SHA-1}).
     */
    private HmacShaAlgorithm hmacShaAlgorithm = HmacShaAlgorithm.HMAC_SHA_1;

    /**
     * Creates a new instance of {@code TOTPBuilder} initialized with a shared
     * secret key.
     *
     * @param key
     *            the shared secret key. The contents of the array are copied to
     *            protect against subsequent modification.
     *
     * @throws NullPointerException
     *             if {@code key} is {@code null}.
     */
    TOTPBuilder(byte[] key) {
        Preconditions.checkNotNull(key);
        this.key = new byte[key.length];
        System.arraycopy(key, 0, this.key, 0, key.length);
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the specified
     * {@code timeStep} size.
     *
     * @param timeStep
     *            the time step size in milliseconds
     *
     * @return this {@code TOTPBuilder} instance initialized with the specified
     *         {@code timeStep} size.
     *
     * @throws IllegalArgumentException
     *             if {@code timeStep} is {@literal <=} 0.
     */
    public TOTPBuilder timeStep(long timeStep) {
        Preconditions.checkArgument(timeStep > 0);
        this.timeStep = timeStep;
        return this;
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the specified
     * {@code digits}.
     *
     * @param digits
     *            the number of digits the generated TOTP value should contain
     *            (must be between {@link #MIN_ALLOWED_DIGITS} and
     *            {@link #MAX_ALLOWED_DIGITS} inclusive)
     *
     * @return this {@code TOTPBuilder} instance initialized with the specified
     *         {@code digits}.
     *
     * @throws IllegalArgumentException
     *             if {@code digits} is not in [{@link #MIN_ALLOWED_DIGITS},
     *             {@link #MAX_ALLOWED_DIGITS}].
     */
    public TOTPBuilder digits(int digits) {
        Preconditions.checkArgument(Range.closed(MIN_ALLOWED_DIGITS, MAX_ALLOWED_DIGITS).contains(digits));
        this.digits = digits;
        return this;
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the specified
     * HMAC-SHA {@code algorithm}.
     *
     * @param algorithm
     *            the HMAC-SHA algorithm used in generating the TOTP value
     *
     * @return this {@code TOTPBuilder} instance initialized with the specified
     *         HMAC-SHA {@code algorithm}.
     *
     * @throws NullPointerException
     *             if {@code algorithm} is {@code null}.
     */
    public TOTPBuilder hmacSha(HmacShaAlgorithm algorithm) {
        Preconditions.checkNotNull(algorithm);
        this.hmacShaAlgorithm = algorithm;
        return this;
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the
     * {@link HmacShaAlgorithm#HMAC_SHA_1}.
     *
     * @return this {@code TOTPBuilder} instance initialized with the
     *         {@link HmacShaAlgorithm#HMAC_SHA_1}.
     */
    public TOTPBuilder hmacSha1() {
        return hmacSha(HmacShaAlgorithm.HMAC_SHA_1);
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the
     * {@link HmacShaAlgorithm#HMAC_SHA_256}.
     *
     * @return this {@code TOTPBuilder} instance initialized with the
     *         {@link HmacShaAlgorithm#HMAC_SHA_256}.
     */
    public TOTPBuilder hmacSha256() {
        return hmacSha(HmacShaAlgorithm.HMAC_SHA_256);
    }

    /**
     * Returns this {@code TOTPBuilder} instance initialized with the
     * {@link HmacShaAlgorithm#HMAC_SHA_512}.
     *
     * @return this {@code TOTPBuilder} instance initialized with the
     *         {@link HmacShaAlgorithm#HMAC_SHA_512}.
     */
    public TOTPBuilder hmacSha512() {
        return hmacSha(HmacShaAlgorithm.HMAC_SHA_512);
    }

    /**
     * Build a Time-based One-time Password {@link TOTP} using the current
     * system time (current time in milliseconds since the UNIX epoch). Note
     * that the builder instance can be reused for subsequent
     * configuration/generation calls.
     *
     * @return a Time-based One-time Password {@link TOTP} instance.
     */
    public TOTP build() {
        long time = System.currentTimeMillis();
        return new TOTP(generateTOTP(time), time, hmacShaAlgorithm, digits, timeStep);
    }

    /**
     * Build a Time-based One-time Password {@link TOTP} using an arbitrary
     * time. Note that the builder instance can be reused for subsequent
     * configuration/generation calls.
     *
     * @param time
     *            the time (in milliseconds) (must be {@literal >= 0})
     *
     * @return a Time-based One-time Password {@link TOTP} instance.
     *
     * @throws IllegalArgumentException
     *             if {@code time} {@literal <} 0.
     */
    public TOTP build(long time) {
        Preconditions.checkArgument(time >= 0);
        return new TOTP(generateTOTP(time), time, hmacShaAlgorithm, digits, timeStep);
    }

    /**
     * Returns the HMAC-SHA hash with {@code keyBytes} as the key, and
     * {@code text} as the message.
     *
     * @param keyBytes
     *            the bytes to use for the HMAC key
     * @param text
     *            the message or text to be authenticated
     *
     * @return the HMAC-SHA hash with {@code keyBytes} as the key, and
     *         {@code text} as the message.
     */
    private byte[] computeHmacSha(byte[] keyBytes, byte[] text) {
        try {
            Mac hmac = Mac.getInstance(hmacShaAlgorithm.getAlgorithm());
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * Returns the Time-based One-time Password value against an arbitrary
     * {@code time} using the set of parameters configured in this builder. The
     * value will contain {@link #digits(int)} digits.
     *
     * @param time
     *            the time (in milliseconds)
     *
     * @return the Time-based One-time Password value as numeric String in base
     *         10 that includes {@link #digits(int)} digits.
     */
    private String generateTOTP(long time) {
        // Calculate the number of time steps between the initial counter time
        // (i.e. T0 = 0 = Unix epoch) and the specified 'time'.

        final long tc = (long) Math.floor(time / timeStep);

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        String timeInHex = Strings.padStart(Long.toHexString(tc).toUpperCase(), 16, '0');

        // Step 1: Generate the HMAC-SHA hash.
        byte[] msg = BaseEncoding.base16().decode(timeInHex);
        byte[] hash = computeHmacSha(key, msg);

        // Step 2: Dynamic Truncation as per section 5.3 of RFC 4226.
        // -
        // "... Let OffsetBits be the low-order 4 bits of String[19] (where
        // String = String[0]...String[19]) ..."
        // -
        // "... Let P = String[OffSet]...String[OffSet+3] ... Return the Last 31
        // bits of P ..."
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        // Step 3: Compute the TOTP value.
        int otp = binary % ((int) Math.pow(10, digits));

        // Ensure the TOTP value contains the specified number of digits.
        return Strings.padStart(Integer.toString(otp), digits, '0');
    }
}
