package com.cditer.free.core.enums;

import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author  chenghuan
 * @email   79763939@qq.com
 * @comment 
 */

public enum OrderEnum implements BaseEnum<String>{
	ASC("asc","ASC"),
	DESC("desc","DESC");

	private String text;
	private String value;
	OrderEnum(String text,String value){
		this.text = text;
		this.value = value;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public final String getValue() {
		return this.value;
	}

	public static OrderEnum parseByText(String text){
		if(!StringUtils.hasText(text)){
			return null;
		}
		OrderEnum[] values = OrderEnum.values();
		Optional<OrderEnum> first = Stream.of(values).filter(item -> text.equals(item.getText())).findFirst();

		if(first.isPresent()){
			return first.get();
		}
		return null;
	}

	public static OrderEnum parseByKey(String key){
		if(!StringUtils.hasText(key)){
			return null;
		}
		OrderEnum[] values = OrderEnum.values();
		Optional<OrderEnum> first = Stream.of(values).filter(item -> key.equals(item.getValue())).findFirst();

		if(first.isPresent()){
			return first.get();
		}
		return null;
	}
}
