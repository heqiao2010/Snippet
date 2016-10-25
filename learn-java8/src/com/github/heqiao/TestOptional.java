package com.github.heqiao;

import java.util.Optional;

public class TestOptional {
	public static void main(String... args) {
		// 创建Optional实例，也可以通过方法返回值得到。
		Optional<String> name = Optional.of("Sanaulla");
		System.out.println(name.get());
		
		Optional<Integer> age =Optional.ofNullable(null);
		System.out.println(age.orElse(0));
	}
}
