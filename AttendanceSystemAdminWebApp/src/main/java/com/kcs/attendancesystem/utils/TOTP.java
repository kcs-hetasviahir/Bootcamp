package com.kcs.attendancesystem.utils;

public class TOTP {

    private final String value;
    private final long time;
    private final HmacShaAlgorithm hmacShaAlgorithm;
    private final int digits;
    private final long timeStep;

    /**
     * Creates a new instance of a Time-based one time password. Use the static
     * method to obtain a {@link TOTPBuilder} instance and obtain a {@code TOTP}
     * from that. Note that all parameters are assumed to be valid since the
     * {@link TOTPBuilder} is responsible for validation, and creation of
     * {@link TOTP}s.
     *
     * @param time
     *            the time (in milliseconds)
     * @param hmacShaAlgorithm
     *            the {@link HmacShaAlgorithm}
     * @param digits
     *            the number of digits the generated TOTP value contains
     * @param timeStep
     *            the time step size (in milliseconds)
     */
    TOTP(String value, long time, HmacShaAlgorithm hmacShaAlgorithm, int digits, long timeStep) {
        this.value = value;
        this.time = time;
        this.hmacShaAlgorithm = hmacShaAlgorithm;
        this.digits = digits;
        this.timeStep = timeStep;
    }

    /**
     * Returns a new {@link TOTPBuilder} instance initialized with the specified
     * {@code key}.
     *
     * @param key
     *            the shared secret key
     *
     * @return a new {@link TOTPBuilder} instance.
     *
     * @throws NullPointerException
     *             if {@code key} is {@code null}.
     */
    public static TOTPBuilder key(byte[] key) {
        return new TOTPBuilder(key);
    }

    /**
     * Returns the time-based one time password value.
     *
     * @return the time-based one time password value.
     */
    public String value() {
        return value;
    }

    /**
     * Returns the time (in milliseconds) used to generate this {@code TOTP}.
     *
     * @return the time (in milliseconds) used to generate this {@code TOTP}.
     */
    public long time() {
        return time;
    }

    /**
     * Returns the {@link HmacShaAlgorithm} used to generate this {@code TOTP}.
     *
     * @return the {@link HmacShaAlgorithm} used to generate this {@code TOTP}.
     */
    public HmacShaAlgorithm hmacShaAlgorithm() {
        return hmacShaAlgorithm;
    }

    /**
     * Returns the number of digits of this {@code TOTP}.
     *
     * @return the number of digits of this {@code TOTP}.
     */
    public int digits() {
        return digits;
    }

    /**
     * Returns the time step size (in milliseconds) used to generate this
     * {@code TOTP}.
     *
     * @return the time step size (in milliseconds) used to generate this
     *         {@code TOTP}.
     */
    public long timeStep() {
        return timeStep;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TOTP other = (TOTP) obj;
        return value.equals(other.value);
    }
}
