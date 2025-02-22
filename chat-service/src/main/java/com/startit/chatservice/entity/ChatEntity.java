package com.startit.chatservice.entity;

import jakarta.persistence.GeneratedValue;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "chats")
public class ChatEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long itemId;
    private Long customerId;
}
