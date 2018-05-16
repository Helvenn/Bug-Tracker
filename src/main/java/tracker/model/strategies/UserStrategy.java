package tracker.model.strategies;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import tracker.model.AppUser;

public class UserStrategy implements IdentifierGenerator {

	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		AppUser user = (AppUser)arg1;
		return user.getUserName();
	}


}
