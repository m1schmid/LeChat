import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class PprBean implements Serializable {

	private String firstname;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}
