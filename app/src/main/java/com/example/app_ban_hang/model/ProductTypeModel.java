package com.example.app_ban_hang.model;

import java.util.List;

public class ProductTypeModel {
    boolean success;
    String message;
    List<ProductType> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductType> getResult() {
        return result;
    }

    public void setResult(List<ProductType> result) {
        this.result = result;
    }

}
