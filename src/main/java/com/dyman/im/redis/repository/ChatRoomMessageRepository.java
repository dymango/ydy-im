package com.dyman.im.redis.repository;

import com.dyman.im.entity.ChatRoomMessage;
import org.springframework.data.repository.CrudRepository;

/**
 * @author dyman
 * @describe
 * @date 2020/7/29
 */
public interface ChatRoomMessageRepository extends CrudRepository<ChatRoomMessage, String> {
}
