package cs.ualberta.ca.tunein;

public class CommenterController implements CommenterControllerInterface {

	private Commenter user;
	
	public CommenterController(Commenter aUser) {
		this.user = aUser;
	}

	@Override
	public void loadUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeUsername(String name) {
		this.user.setName(name);
	}

}
