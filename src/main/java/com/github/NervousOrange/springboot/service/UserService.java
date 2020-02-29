package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.entity.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    private MysqlDao mysqlDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    @SuppressFBWarnings("URF_UNREAD_FIELD")
    public UserService(MysqlDao mysqlDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mysqlDao = mysqlDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String insertNewUser(String username, String password) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("encryptedPassword", encryptedPassword);
        this.mysqlDao.insertNewUser(param);
        return username;
    }

    public User getUserByUsername(String username) {
        return this.mysqlDao.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if ((user = getUserByUsername(username)) == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String encodedPassword = user.getEncryptedPassword();
        return new org.springframework.security.core.userdetails.User(username, encodedPassword, Collections.emptyList());
    }

}
