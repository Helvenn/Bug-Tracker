package com.fer.hr.model.strategies;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.fer.hr.model.AppUser;

public class UserStrategy implements IdentifierGenerator {



	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		AppUser user = (AppUser)arg1;
		return user.getUserName();
	}


}
