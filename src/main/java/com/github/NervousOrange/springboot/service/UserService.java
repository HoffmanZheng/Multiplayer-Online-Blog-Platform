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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private MysqlDao mysqlDao;
    private Map<String, String> usernameAndPassword = new ConcurrentHashMap<>();
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    @SuppressFBWarnings("URF_UNREAD_FIELD")
    public UserService(MysqlDao mysqlDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mysqlDao = mysqlDao;
        usernameAndPassword.put("laowang", bCryptPasswordEncoder.encode("693922"));
        usernameAndPassword.put("lili", bCryptPasswordEncoder.encode("930615"));
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUserById(int id) {
        return this.mysqlDao.selectUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!usernameAndPassword.containsKey(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String encodedPassword = usernameAndPassword.get(username);
        return new org.springframework.security.core.userdetails.User(username, encodedPassword, Collections.emptyList());
    }
}
