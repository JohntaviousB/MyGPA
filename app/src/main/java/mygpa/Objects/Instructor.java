package mygpa.Objects;
public class Instructor {
	
	private String firstName, lastName;

	public Instructor(String first, String last) {
		setFirstName(first);
		setLastName(last);
	}

	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }

	public void setFirstName(String s) {
		if ( isValidName(s) ) {
			this.firstName = s;
		}
	}
	public void setLastName(String s) {
		if ( isValidName(s) ) {
			this.lastName = s;
		}
	}

	private boolean isValidName(String s) {
		return s != null && s.length() > 0;
	}
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if ( !(other instanceof Instructor) ) return false;
		Instructor o = (Instructor) other;
		return (this.firstName.equals(o.firstName)) && (this.lastName.equals(o.lastName));
	}
}