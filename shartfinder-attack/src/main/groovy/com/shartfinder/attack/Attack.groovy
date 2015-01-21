package com.shartfinder.attack

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class Attack {
	@Id BigInteger id
	String name
	AttackType type
	
}
