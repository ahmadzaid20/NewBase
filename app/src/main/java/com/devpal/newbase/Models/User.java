package com.devpal.newbase.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * User: Data model to represent a user object.
 * @Entity: Used to define this class as an entity (table) in the Room database.
 * tableName: The name of the table to be created in the database.
 * @ColumnInfo: Used to define column names in the database.
 * @Ignore: Used to tell Room to ignore a specific constructor or method.
 */
@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "username")
    @SerializedName("username")
    private String username;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email;

    @ColumnInfo(name = "password_hash") // Corresponds to 'password_hash' in DB
    @SerializedName("password_hash")
    private String passwordHash;

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    private String lastName;

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "profile_picture_url")
    @SerializedName("profile_picture_url")
    private String profilePictureUrl;

    @ColumnInfo(name = "bio")
    @SerializedName("bio")
    private String bio;

    // Address Details
    @ColumnInfo(name = "street_address")
    @SerializedName("street_address")
    private String streetAddress;

    @ColumnInfo(name = "city")
    @SerializedName("city")
    private String city;

    @ColumnInfo(name = "state_province")
    @SerializedName("state_province")
    private String stateProvince;

    @ColumnInfo(name = "zip_postal_code")
    @SerializedName("zip_postal_code")
    private String zipPostalCode;

    @ColumnInfo(name = "country")
    @SerializedName("country")
    private String country;

    // Security and Administrative
    @ColumnInfo(name = "login_attempts")
    @SerializedName("login_attempts")
    private int loginAttempts;

    @ColumnInfo(name = "locked_until")
    @SerializedName("locked_until")
    private Long lockedUntil; // Use Long for nullable TIMESTAMP

    @ColumnInfo(name = "account_status")
    @SerializedName("account_status")
    private String accountStatus; // e.g., "active", "inactive", "suspended", "pending_verification"

    @ColumnInfo(name = "is_email_verified")
    @SerializedName("is_email_verified")
    private boolean isEmailVerified;

    @ColumnInfo(name = "email_verified_at")
    @SerializedName("email_verified_at")
    private Long emailVerifiedAt; // Use Long for nullable TIMESTAMP

    @ColumnInfo(name = "is_phone_verified")
    @SerializedName("is_phone_verified")
    private boolean isPhoneVerified;

    @ColumnInfo(name = "phone_verified_at")
    @SerializedName("phone_verified_at")
    private Long phoneVerifiedAt; // Use Long for nullable TIMESTAMP

    @ColumnInfo(name = "two_factor_enabled")
    @SerializedName("two_factor_enabled")
    private boolean twoFactorEnabled;

    @ColumnInfo(name = "two_factor_secret")
    @SerializedName("two_factor_secret")
    private String twoFactorSecret;

    @ColumnInfo(name = "locale")
    @SerializedName("locale")
    private String locale;

    @ColumnInfo(name = "role_id")
    @SerializedName("role_id")
    private int roleId;

    @ColumnInfo(name = "last_login_at")
    @SerializedName("last_login_at")
    private Long lastLoginAt; // Use Long for nullable TIMESTAMP

    @ColumnInfo(name = "last_activity_at")
    @SerializedName("last_activity_at")
    private Long lastActivityAt; // Use Long for nullable TIMESTAMP

    // Privacy and Compliance
    @ColumnInfo(name = "consent_to_terms")
    @SerializedName("consent_to_terms")
    private boolean consentToTerms;

    @ColumnInfo(name = "consent_to_marketing")
    @SerializedName("consent_to_marketing")
    private boolean consentToMarketing;

    // Administrative
    @ColumnInfo(name = "created_by")
    @SerializedName("created_by")
    private String createdBy; // UUID of the user who created this account

    // Technical/Future
    @ColumnInfo(name = "metadata")
    @SerializedName("metadata")
    private String metadata; // JSON string

    // Timestamps
    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private Long createdAt; // Use Long for timestamp (Unix epoch seconds/millis)

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    private Long updatedAt; // Use Long for timestamp

    @ColumnInfo(name = "deleted_at")
    @SerializedName("deleted_at")
    private Long deletedAt; // Use Long for timestamp, can be null for soft delete

    // Placeholder for API token, not stored in DB usually but sent by API
    @SerializedName("token")
    @Ignore // This field is for API response, not for Room DB
    private String token;

    // Default constructor for Room to read from DB
    public User() {
    }

    // Constructor for login/registration requests (ignoring most fields)
    // NOTE: This constructor only includes fields typically sent in login/register requests.
    // Password is sent as 'password' to API, but stored as 'password_hash' in DB.
    // For simplicity, we'll use 'password' field in this model for API requests.
    @Ignore
    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.passwordHash = password; // Use passwordHash field for password
        this.accountStatus = "pending_verification"; // Default for new registrations
        this.isEmailVerified = false;
        this.twoFactorEnabled = false;
        this.consentToTerms = false;
        this.consentToMarketing = false;
        this.loginAttempts = 0;
        this.locale = "en"; // Default locale
        this.roleId = 1; // Default role ID (user)
    }

    // Full constructor for creating/updating user objects in app/Room DB
    @Ignore // Room doesn't use this constructor for reading from DB
    public User(@NonNull String id, String username, String email, String passwordHash, String firstName,
                String lastName, String phoneNumber, String profilePictureUrl, String bio,
                String streetAddress, String city, String stateProvince, String zipPostalCode, String country,
                int loginAttempts, Long lockedUntil, String accountStatus, boolean isEmailVerified, Long emailVerifiedAt,
                boolean isPhoneVerified, Long phoneVerifiedAt, boolean twoFactorEnabled, String twoFactorSecret,
                String locale, int roleId, Long lastLoginAt, Long lastActivityAt,
                boolean consentToTerms, boolean consentToMarketing, String createdBy, String metadata,
                Long createdAt, Long updatedAt, Long deletedAt, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.streetAddress = streetAddress;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipPostalCode = zipPostalCode;
        this.country = country;
        this.loginAttempts = loginAttempts;
        this.lockedUntil = lockedUntil;
        this.accountStatus = accountStatus;
        this.isEmailVerified = isEmailVerified;
        this.emailVerifiedAt = emailVerifiedAt;
        this.isPhoneVerified = isPhoneVerified;
        this.phoneVerifiedAt = phoneVerifiedAt;
        this.twoFactorEnabled = twoFactorEnabled;
        this.twoFactorSecret = twoFactorSecret;
        this.locale = locale;
        this.roleId = roleId;
        this.lastLoginAt = lastLoginAt;
        this.lastActivityAt = lastActivityAt;
        this.consentToTerms = consentToTerms;
        this.consentToMarketing = consentToMarketing;
        this.createdBy = createdBy;
        this.metadata = metadata;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.token = token;
    }


    // Getters and Setters for all fields

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Note: For password management, typically password is only sent for login/register,
    // and not stored in the model after that. passwordHash is for internal DB storage.
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Long getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(Long lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Long getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Long emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public Long getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(Long phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Long getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Long lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Long getLastActivityAt() {
        return lastActivityAt;
    }

    public void setLastActivityAt(Long lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public boolean isConsentToTerms() {
        return consentToTerms;
    }

    public void setConsentToTerms(boolean consentToTerms) {
        this.consentToTerms = consentToTerms;
    }

    public boolean isConsentToMarketing() {
        return consentToMarketing;
    }

    public void setConsentToMarketing(boolean consentToMarketing) {
        this.consentToMarketing = consentToMarketing;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    public Long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Long deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Token is for API response, not part of persisted user data typically
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                // ... add other important fields for logging/debugging, but omit sensitive ones like passwordHash
                '}';
    }
}
