package com.turnsole.rbac.domain.beans;

import lombok.*;

import java.util.Set;

/**
 * @author:徐凯
 * @date:2019/9/19,15:01
 * @what I say:just look,do not be be
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    //主题
    private String subject;
    //内容
    private String message;
    //收件人
    private Set<String> receivers;
}
