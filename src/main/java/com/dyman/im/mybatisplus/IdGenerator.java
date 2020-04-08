package com.dyman.im.mybatisplus;

import lombok.Getter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

public class IdGenerator {
    private static final long BEGIN_EPOCH = TimeUtils.toMillis(LocalDateTime.of(2019, 1, 1, 0, 0));

    private static final int DATA_CENTER_ID_BITS = 5;
    private static final long MAX_DATA_CENTER_ID = maxPositiveNumberByBits(DATA_CENTER_ID_BITS);

    private static final int WORKER_ID_BITS = 5;
    private static final long MAX_WORKER_ID = maxPositiveNumberByBits(WORKER_ID_BITS);

    private static final int SEQUENCE_BITS = 12;
    private static final long SEQUENCE_MASK = maxPositiveNumberByBits(SEQUENCE_BITS);

    private static final String DATA_CENTER_ID = "dataCenterId";
    private static final String WORKER_ID = "workerId";

    @Getter
    private final long dataCenterId;
    @Getter
    private final long workerId;

    private long lastSequence;
    private long lastTimestamp = -1L;

    private IdGenerator(@Positive long dataCenterId, @Positive long workerId, @Positive long lastSequence) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0 || workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException();
        }

        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.lastSequence = lastSequence;
    }

    private IdGenerator(@Positive long dataCenterId, @Positive long workerId) {
        this(dataCenterId, workerId, 0);
    }

    public static IdGenerator of(@Positive long dataCenterId, @Positive long workerId) {
        return new IdGenerator(dataCenterId, workerId);
    }

    public static IdGenerator of() {
        long dataCenterId = ofNullable(System.getProperty(DATA_CENTER_ID)).map(String::trim).map(Long::parseLong).orElse(0L);
        long workerId = ofNullable(System.getProperty(WORKER_ID)).map(String::trim).map(Long::parseLong).orElse(0L);
        return IdGenerator.of(dataCenterId, workerId);
    }

    private static long maxPositiveNumberByBits(@Positive int bits) {
        return ~(-1L << bits);
    }

    //0(1) | timestamp(43) | dataCenterId(5) | workId(5) | sequence(12)
    public synchronized long generate() {
        this.getSequenceAndSaveTimestamp();

        return ((this.lastTimestamp - BEGIN_EPOCH) << (SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS)) |
                (this.dataCenterId << (SEQUENCE_BITS + WORKER_ID_BITS)) |
                (this.workerId << SEQUENCE_BITS) |
                this.lastSequence;
    }

    private void getSequenceAndSaveTimestamp() {
        long timestamp = this.currentTimeMillis();

        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException("timestamp error.");
        }

        if (timestamp == this.lastTimestamp) {
            this.lastSequence = (this.lastSequence + 1) & SEQUENCE_MASK;
            if (this.lastSequence == 0) {
                timestamp = this.nextTimeMillis(this.lastTimestamp);
            }
        } else {
            this.lastSequence = 0;
        }

        this.lastTimestamp = timestamp;
    }

    private long nextTimeMillis(long lastTimestamp) {
        long timestamp = this.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = this.currentTimeMillis();
        }
        return timestamp;
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}