package com.programmers.ticketing.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Theater {
    @Id
    @GeneratedValue
    private Long theaterId;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    public Theater() {

    }

    public Theater(String name, String address) {
        nullCheck(name, address);
        validateName(name);
        validateAddress(address);
        this.name = name;
        this.address = address;
    }

    private void nullCheck(String name, String address) {
        if (name == null) {
            throw new IllegalArgumentException("Theater name must not be null");
        }
        if (address == null) {
            throw new IllegalArgumentException("Theater address must not be null");
        }
    }

    private void validateName(String name) {
        if (isNameLengthOutOfRange(name)) {
            throw new IllegalArgumentException("Theater name length must less than 100");
        }
    }

    private boolean isNameLengthOutOfRange(String name) {
        return name.length() > 100;
    }

    private void validateAddress(String address) {
        if (isAddressLengthOutOfRange(address)) {
            throw new IllegalArgumentException("Theater address length must less than 200");
        }
    }

    private boolean isAddressLengthOutOfRange(String address) {
        return address.length() > 200;
    }

    public void update(String name, String address) {
        if (name != null) {
            validateName(name);
            this.name = name;
        }
        if (address != null) {
            validateAddress(address);
            this.address = address;
        }
    }
}
