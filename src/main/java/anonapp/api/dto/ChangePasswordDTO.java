package anonapp.api.dto;

import javax.validation.constraints.NotNull;

/**
 *
 * This class represents a java object that will be deserialized from the password change request payload(json).
 *
 * @author Orlov Diga
 */
public class ChangePasswordDTO {

    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String matchingNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMatchingNewPassword() {
        return matchingNewPassword;
    }

    public void setMatchingNewPassword(String matchingNewPassword) {
        this.matchingNewPassword = matchingNewPassword;
    }
}
