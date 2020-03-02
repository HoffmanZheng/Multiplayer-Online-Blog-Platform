package com.github.NervousOrange.springboot.entity;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class AvatarGenerator {

    private String avatar1 = "https://s2.ax1x.com/2020/03/02/3f1FDe.jpg";
    private String avatar2 = "https://s2.ax1x.com/2020/03/02/3f1kHH.jpg";
    private String avatar3 = "https://s2.ax1x.com/2020/03/02/3f1mCt.jpg";
    private String avatar4 = "https://s2.ax1x.com/2020/03/02/3f1ugf.jpg";
    private String avatar5 = "https://s2.ax1x.com/2020/03/02/3f1lDg.jpg";
    private String avatar6 = "https://s2.ax1x.com/2020/03/02/3f1GUs.jpg";
    private String avatar7 = "https://s2.ax1x.com/2020/03/02/3f1J5n.jpg";
    private String avatar8 = "https://s2.ax1x.com/2020/03/02/3f1N80.jpg";
    private String avatar9 = "https://s2.ax1x.com/2020/03/02/3f1avT.jpg";
    public List<String> avatarList = Arrays.asList(avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, avatar9);

    public String getRandomAvatar() {
        return avatarList.get(new Random().nextInt(9));
    }
}
