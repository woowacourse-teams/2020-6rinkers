package com.cocktailpick.back.common.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
public abstract class BaseTimeEntity {
	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime updatedDate;
}
