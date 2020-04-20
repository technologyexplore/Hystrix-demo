package com.itmuch.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class PurchaseController {
  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/findById/{id}")
  @HystrixCommand(fallbackMethod = "findByIdFallback")
//  @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"))
//  @HystrixCommand(fallbackMethod = "findByIdFallback", threadPoolKey = "user", threadPoolProperties ={@HystrixProperty(name = "coreSize",value = "5"),@HystrixProperty(name = "maxQueueSize",value = "2")})
  public User findById(@PathVariable Long id) {
    return this.restTemplate.getForObject("http://microservice-provider-user/findById/" + id, User.class);
  }

  public User findByIdFallback(Long id) {
    User user = new User();
    user.setId(0L);
    return user;
  }

}
