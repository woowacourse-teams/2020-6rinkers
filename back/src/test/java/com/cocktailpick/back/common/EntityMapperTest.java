package com.cocktailpick.back.common;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class EntityMapperTest {
	@Mock
	private Map<String, Object> map;

	@DisplayName("매핑 되어있지 않은 key로 조회 시 예외처리한다.")
	@Test
	void getWithException() {
		given(map.get(any())).willReturn(null);

		EntityMapper<String, Object> entityMapper = new EntityMapper(map);

		assertThatThrownBy(() -> entityMapper.get("test")).isInstanceOf(RuntimeException.class);

	}
}