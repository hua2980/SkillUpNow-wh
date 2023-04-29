package com.skillupnow.demo.security;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.CustomerRepository;
import com.skillupnow.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    UserType userType = user.getUserType();
    Collection<? extends GrantedAuthority> authorities = getAuthoritiesForUserType(userType);

    // this is not the User class defined in this project! so use the fully qualified name
    // variables passed: username, password, a list of authorities (empty here)
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        authorities);
  }

//  // TODO: 暂时不知道怎么实现userType一起和password/username一起验证
//  public UserDetails loadUserByUsernameAndUserType(String username, UserType userType) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username);
//
//    if (user == null) {
//      throw new UsernameNotFoundException(username);
//    } else if (!user.getUserType().equals(userType)) {
//      throw new UsernameNotFoundException(username + " is not a " + userType + " user");
//    }
//
//    Collection<? extends GrantedAuthority> authorities = getAuthoritiesForUserType(userType);
//
//    // this is not the User class defined in this project! so use the fully qualified name
//    // variables passed: username, password, a list of authorities (empty here)
//    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//        authorities);
//  }

  private Collection<? extends GrantedAuthority> getAuthoritiesForUserType(UserType userType) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    if (UserType.CUSTOMER.equals(userType)) {
      authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
    } else if (UserType.ORGANIZATION.equals(userType)) {
      authorities.add(new SimpleGrantedAuthority("ORGANIZATION"));
    }

    return authorities;
  }

}
