package com.fms;

import org.springframework.batch.item.ItemProcessor;


public class UserItemProcessor implements ItemProcessor<User, User> {

 public User process(User user) throws Exception {
  return user;
 }

} 
