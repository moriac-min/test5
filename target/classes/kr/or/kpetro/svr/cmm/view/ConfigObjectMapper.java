package kr.or.kpetro.svr.cmm.view;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class ConfigObjectMapper extends ObjectMapper{
	public ConfigObjectMapper(){
		this.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}
}
