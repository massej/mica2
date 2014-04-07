package org.obiba.mica.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.obiba.mica.security.SecurityManagerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@AutoConfigureAfter(CacheConfiguration.class)
public class SecurityConfiguration {

  @Bean
  public SecurityManagerFactory securityManagerFactory() {
    return new SecurityManagerFactory();
  }

  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    return new DefaultAdvisorAutoProxyCreator();
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
    return new AuthorizationAttributeSourceAdvisor();
  }

}
