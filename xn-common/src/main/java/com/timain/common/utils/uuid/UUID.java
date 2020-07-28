package com.timain.common.utils.uuid;

import com.timain.common.exception.UtilException;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 9:51
 */
public final class UUID implements Serializable, Comparable<UUID> {

    private static final long serialVersionUID = -2496940013175041662L;

    /**
     * SecureRandom 的单例
     */
    private static class Holder {
        static final SecureRandom numberGenerator = getSecureRandom();
    }

    /** 此UUID的最高64有效位 */
    private final long mostSigBits;

    /** 此UUID的最低64有效位 */
    private final long leastSigBits;

    public UUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * 私有构造
     * @param data
     */
    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 0; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的本地线程伪随机数生成器生成该 UUID.
     * @return
     */
    public static UUID fastUUID() {
        return randomUUID(false);
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID.
     * @return
     */
    public static UUID randomUUID() {
        return randomUUID(true);
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID.
     * @param isSecure
     * @return
     */
    public static UUID randomUUID(boolean isSecure) {
        final Random ng = isSecure ? Holder.numberGenerator : getRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6]  &= 0x0f;  /* clear version        */
        randomBytes[6]  |= 0x40;  /* set to version 4     */
        randomBytes[8]  &= 0x3f;  /* clear variant        */
        randomBytes[8]  |= 0x80;  /* set to IETF variant  */
        return new UUID(randomBytes);
    }

    /**
     * 根据指定的字节数组获取类型 3（基于名称的）UUID 的静态工厂.
     * @param name
     * @return
     */
    public static UUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6]  &= 0x0f;  /* clear version        */
        md5Bytes[6]  |= 0x30;  /* set to version 3     */
        md5Bytes[8]  &= 0x3f;  /* clear variant        */
        md5Bytes[8]  |= 0x80;  /* set to IETF variant  */
        return new UUID(md5Bytes);
    }

    public static UUID fromString(String name) {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UUID string: "+name);
        for (int i=0; i<5; i++)
            components[i] = "0x"+components[i];

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    public long getMostSigBits() {
        return mostSigBits;
    }

    public long getLeastSigBits() {
        return leastSigBits;
    }

    public int version() {
        return (int)((mostSigBits >> 12) & 0x0f);
    }

    public int variant() {
        return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
    }

    /**
     * 与此 UUID 相关联的时间戳值.
     * @return
     * @throws UnsupportedOperationException
     */
    public long timestamp() throws UnsupportedOperationException {
        checkTimeBase();
        return (mostSigBits & 0x0FFFL) << 48
                | ((mostSigBits >> 16) & 0x0FFFFL) << 32
                | mostSigBits >>> 32;
    }

    /**
     * 与此 UUID 相关联的时钟序列值.
     * @return
     * @throws UnsupportedOperationException
     */
    public int chockSequence() throws UnsupportedOperationException {
        checkTimeBase();
        return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
    }

    /**
     * 与此 UUID 相关的节点值.
     * @return
     * @throws UnsupportedOperationException
     */
    public long node() throws UnsupportedOperationException {
        checkTimeBase();
        return leastSigBits & 0x0000FFFFFFFFFFFFL;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean isSimple) {
        final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
        builder.append(digits(mostSigBits >> 32, 8));
        if (false == isSimple) {
            builder.append("-");
        }
        builder.append(digits(mostSigBits >> 16, 4));
        if (false == isSimple) {
            builder.append("-");
        }
        builder.append(digits(mostSigBits, 4));
        if (false == isSimple) {
            builder.append("-");
        }
        builder.append(digits(leastSigBits >> 48, 4));
        if (false == isSimple) {
            builder.append("-");
        }
        builder.append(digits(leastSigBits, 12));
        return builder.toString();
    }

    @Override
    public int hashCode() {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int)(hilo >> 32)) ^ (int) hilo;
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != java.util.UUID.class))
            return false;
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits &&
                leastSigBits == id.leastSigBits);
    }

    @Override
    public int compareTo(UUID val) {
        return (this.mostSigBits < val.mostSigBits ? -1 :
                (this.mostSigBits > val.mostSigBits ? 1 :
                        (this.leastSigBits < val.leastSigBits ? -1 :
                                (this.leastSigBits > val.leastSigBits ? 1 :
                                        0))));
    }

    /**
     * 返回指定数字对应的hex值
     * @param val 值
     * @param digits 位
     * @return 值
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi -1))).substring(1);
    }

    /**
     * 检查是否为time-based版本UUID
     */
    private void checkTimeBase() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
    }

    /**
     * 获取{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)
     * @return
     */
    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 获取随机数生成器对象
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺.
     * @return
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }
}
