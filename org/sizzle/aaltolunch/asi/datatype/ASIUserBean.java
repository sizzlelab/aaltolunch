package org.sizzle.aaltolunch.asi.datatype;

/**
 * 
 * @author Nalin Chaudhary
 */
public class ASIUserBean 
{
	private ASIUserAddressBean userAddressInfo;
	private ASIUserNameBean userNameInfo;
	private String birthdate;
	private String connection;
	private String isAssociation;
	private String updatedAt;
	private String role;
	private String userName;
	private String gender;
	private ASIUserAvtarBean userAvtarInfo;
	private String id;
	private String phoneNumber;
	private String msnNick;
	private String website;
	private String description;
	private String ircNick;
	private ASIUserStatusBean userStatusInfo;
	private String email;
	
	public ASIUserBean(ASIUserAddressBean userAddressInfo, ASIUserNameBean userNameInfo,
			String birthdate, String connection, String isAssociation,
			String updatedAt, String role, String userName, String gender,
			ASIUserAvtarBean userAvtarInfo, String id, String phoneNumber,
			String msnNick, String website, String description, String ircNick,
			ASIUserStatusBean userStatusInfo, String email) 
	{
		this.userAddressInfo = userAddressInfo;
		this.userNameInfo = userNameInfo;
		this.birthdate = birthdate;
		this.connection = connection;
		this.isAssociation = isAssociation;
		this.updatedAt = updatedAt;
		this.role = role;
		this.userName = userName;
		this.gender = gender;
		this.userAvtarInfo = userAvtarInfo;
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.msnNick = msnNick;
		this.website = website;
		this.description = description;
		this.ircNick = ircNick;
		this.userStatusInfo = userStatusInfo;
		this.email = email;
	}
	
	public ASIUserAddressBean getUserAddressInfo() {
		return userAddressInfo;
	}

	public ASIUserNameBean getUserNameInfo() {
		return userNameInfo;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getConnection() {
		return connection;
	}

	public String getIsAssociation() {
		return isAssociation;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getRole() {
		return role;
	}

	public String getUserName() {
		return userName;
	}

	public String getGender() {
		return gender;
	}

	public ASIUserAvtarBean getUserAvtarInfo() {
		return userAvtarInfo;
	}

	public String getId() {
		return id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMsnNick() {
		return msnNick;
	}

	public String getWebsite() {
		return website;
	}

	public String getDescription() {
		return description;
	}

	public String getIrcNick() {
		return ircNick;
	}

	public ASIUserStatusBean getUserStatusInfo() {
		return userStatusInfo;
	}

	public String getEmail() {
		return email;
	}
}	
