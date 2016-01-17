package at.arz.latte.rodeo.infrastructure;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
public class RodeoSecurity {
	
	@Context
	private SecurityContext context;
	
	public void assertUserIsAdmin(){
		if(!context.isUserInRole("rodeo-admin")){
			throw new Forbidden();
		}
	}
	
}
