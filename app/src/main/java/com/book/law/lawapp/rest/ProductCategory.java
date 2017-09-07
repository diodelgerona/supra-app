package com.book.law.lawapp.rest;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("parent_category_id")
    @Expose
    private String parentCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("translation_key")
    @Expose
    private String translationKey;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("last_date_updated")
    @Expose
    private String lastDateUpdated;
    @SerializedName("last_updated_by")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("cat_img")
    @Expose
    private String catImg;
    private String path;
    private Bitmap catIcon;
    private Uri catUri;

    public Uri getCatUri() {
        return catUri;
    }

    public void setCatUri(Uri catUri) {
        this.catUri = catUri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(Bitmap catIcon) {
        this.catIcon = catIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastDateUpdated() {
        return lastDateUpdated;
    }

    public void setLastDateUpdated(String lastDateUpdated) {
        this.lastDateUpdated = lastDateUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    @Override
    public String toString() {
        return name;
    }

}