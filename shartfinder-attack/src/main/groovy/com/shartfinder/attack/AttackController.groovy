package com.shartfinder.attack

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
class AttackController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttackController.class)
	
	private RedisTemplate<String, Attack> redisTemplateAttack
	
	@Autowired
	public AttackController(RedisTemplate<String, Attack> redisTemplateAttack) {
		this.redisTemplateAttack = redisTemplateAttack
	}

	@RequestMapping("/attack")
	@ResponseBody
	ResponseEntity<Attack> attack() {
		def attack = new Attack(id:1, name: "Spell attack")
		if(!attack) throw new AttackException("Attack error")
		
		LOGGER.info("Sending attack message")
		redisTemplateAttack.convertAndSend("attack", attack)
		
		new ResponseEntity<Attack>(attack, HttpStatus.OK)
	}

	@ExceptionHandler
	ResponseEntity<String> handle(AttackException infe) {
		new ResponseEntity<String>(infe.message, HttpStatus.NOT_FOUND)
	}
}
