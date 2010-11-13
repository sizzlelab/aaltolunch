package org.sizzle.aaltolunch.asi.datatype;

/**
 * The class represents the searched user from the ASI. It hold the ASIUserBean
 * object and along with that additional information about the friendship status
 * in respect to the user who is searching the users from ASI.
 * @author Nalin Chaudhary
 */
public class ASISearchedUserBean 
{
	public enum FriendshipStatus
	{
		FRIEND,			// the searched user is the friend of the user who is searching
		NOT_A_FRIEND,	// the searched user is not the friend of the user who is searching
		MYSELF			// Denotes that this user is the same user who is making the search operation
	}
	
	private ASIUserBean userBean = null;
	private FriendshipStatus status = null;
	
	public ASISearchedUserBean(ASIUserBean userBean, FriendshipStatus status)
	{
		this.userBean = userBean;
		this.status = status;
	}

	public ASIUserBean getUserBean() {
		return userBean;
	}

	public FriendshipStatus getStatus() {
		return status;
	}
}
