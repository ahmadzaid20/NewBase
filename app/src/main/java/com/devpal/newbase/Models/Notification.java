package com.devpal.newbase.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * Notification: Data model to represent a notification object.
 * @Entity: Used to define this class as an entity (table) in the Room database.
 * tableName: The name of the table to be created in the database.
 * @ColumnInfo: Used to define column names in the database.
 * @Ignore: Used to tell Room to ignore a specific constructor or method.
 */
@Entity(tableName = "notifications")
public class Notification {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    private String userId;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type; // e.g., "general", "message", "alert"

    @ColumnInfo(name = "category")
    @SerializedName("category")
    private String category; // e.g., "social", "transactional", "marketing"

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "body")
    @SerializedName("body")
    private String body; // Detailed content

    @ColumnInfo(name = "short_description")
    @SerializedName("short_description")
    private String shortDescription;

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    private String imageUrl;

    @ColumnInfo(name = "action_type")
    @SerializedName("action_type")
    private String actionType; // e.g., "none", "open_url", "open_screen", "custom"

    @ColumnInfo(name = "action_value")
    @SerializedName("action_value")
    private String actionValue; // e.g., URL, screen name

    @ColumnInfo(name = "payload")
    @SerializedName("payload")
    private String payload; // JSON string for additional data

    @ColumnInfo(name = "read_status")
    @SerializedName("read_status")
    private String readStatus; // e.g., "unread", "read"

    @ColumnInfo(name = "priority")
    @SerializedName("priority")
    private String priority; // e.g., "low", "medium", "high", "critical"

    @ColumnInfo(name = "delivery_channel")
    @SerializedName("delivery_channel")
    private String deliveryChannel; // e.g., "in_app", "push_notification", "email", "sms"

    @ColumnInfo(name = "sent_at")
    @SerializedName("sent_at")
    private Long sentAt; // Timestamp when sent from server

    @ColumnInfo(name = "delivered_at")
    @SerializedName("delivered_at")
    private Long deliveredAt; // Timestamp when delivered to device

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private Long createdAt; // Timestamp when record created in DB

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    private Long updatedAt; // Timestamp of last update to record

    // Default constructor for Room
    public Notification() {
    }

    // Constructor for creating new notifications (e.g., from server response)
    @Ignore // Room doesn't use this constructor for reading from DB
    public Notification(@NonNull String id, String userId, String type, String category, String title,
                        String body, String shortDescription, String imageUrl, String actionType,
                        String actionValue, String payload, String readStatus, String priority,
                        String deliveryChannel, Long sentAt, Long deliveredAt, Long createdAt, Long updatedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.category = category;
        this.title = title;
        this.body = body;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.actionType = actionType;
        this.actionValue = actionValue;
        this.payload = payload;
        this.readStatus = readStatus;
        this.priority = priority;
        this.deliveryChannel = deliveryChannel;
        this.sentAt = sentAt;
        this.deliveredAt = deliveredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters for all fields

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDeliveryChannel() {
        return deliveryChannel;
    }

    public void setDeliveryChannel(String deliveryChannel) {
        this.deliveryChannel = deliveryChannel;
    }

    public Long getSentAt() {
        return sentAt;
    }

    public void setSentAt(Long sentAt) {
        this.sentAt = sentAt;
    }

    public Long getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Long deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", readStatus='" + readStatus + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
