package com.cocktailpick.core.util;

import static org.mockito.BDDMockito.*;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;

@ExtendWith({MockitoExtension.class})
class EntityMapperTest {
	@Mock
	private Map<String, Object> map;

	@DisplayName("매핑 되어있지 않은 key로 조회 시 예외처리한다.")
	@Test
	void getWithException() {
		given(map.get(ArgumentMatchers.anyString())).willReturn(null);

		EntityMapper<String, Object> entityMapper = new EntityMapper<>(map);

		Assertions.assertThatThrownBy(() -> entityMapper.get("notContainedKey"))
			.isInstanceOf(EntityNotFoundException.class);
	}
}