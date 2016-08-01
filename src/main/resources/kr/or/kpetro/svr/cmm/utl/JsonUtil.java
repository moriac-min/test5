package kr.or.kpetro.svr.cmm.utl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class JsonUtil {
	protected Log log = LogFactory.getLog(this.getClass());
	
	public String getParameter(String _jsonString, String _findKey, String _nvlString){
		String ret = "";
		if(_jsonString==null||_jsonString.equals("")) return (ret==null || ret.equals(""))?_nvlString : ret;
		
		try{
			JSONParser localJSONParser = new JSONParser();
			Object localObject = localJSONParser.parse(_jsonString);
		
			JSONObject localJSONObject = ((JSONObject)localObject);
			ret = (String)localJSONObject.get(_findKey);

		}catch(Exception e){
			log.debug(e);
		}
		
		return (ret==null || ret.equals(""))?_nvlString : ret;
	}
	
	public JSONObject getJSONObject(String _jsonString){
		
		try{
			JSONParser localJSONParser = new JSONParser();
			Object localObject = localJSONParser.parse(_jsonString);
		
			return ((JSONObject)localObject);

		}catch(Exception e){
			log.debug(e);
		}
		
		return null;
	}
	
	public JSONObject getJSONObject(JSONObject _jsonobj, String _findKey){
		try{
		
			return (JSONObject)_jsonobj.get(_findKey);

		}catch(Exception e){
			log.debug(e);
		}
		
		return null;
	}
	
	public JSONArray getJSONList(JSONObject _jsonobj, String _findKey){
		try{
			
			Object localObject = _jsonobj.get(_findKey);
			if (localObject == null) return null;
			
			if (localObject instanceof JSONArray){
				JSONArray localJSONArray = (JSONArray)localObject;
				return localJSONArray;
			}

		}catch(Exception e){
			log.debug(e);
		}
		
		return null;
	}
	
	public JSONObject getJSONObject(String _jsonString, String _findKey){
		try{
			JSONParser localJSONParser = new JSONParser();
			Object localObject = localJSONParser.parse(_jsonString);
		
			JSONObject localJSONObject = ((JSONObject)localObject);
			localJSONObject = (JSONObject)localJSONObject.get(_findKey);
			return localJSONObject;

		}catch(Exception e){
			log.debug(e);
		}
		
		return null;
	}

	public String getString(JSONObject _jsonSvcDef, String _findKey) {
		try{
		
			return (String)_jsonSvcDef.get(_findKey);

		}catch(Exception e){
			log.debug(e);
		}
		return null;
	}

	public String getString(String _jsonString, String _findKey) {
		
		try{
			JSONParser localJSONParser = new JSONParser();
			JSONObject localJSONObject = (JSONObject)localJSONParser.parse(_jsonString);
			return (String)localJSONObject.get(_findKey);

		}catch(Exception e){
			log.debug(e);
		}
		
		return null;
	}
}
