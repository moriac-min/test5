package kr.or.kpetro.svr.mdwr.processor;

import java.util.Map;

public class ProcessorManager{

	private Map<String, ProcessorController> serviceType;

	public void setServiceType(Map<String, ProcessorController> _serviceType)
	{
		this.serviceType = _serviceType;
	}

	public ProcessorController getServiceType(String _serviceType) {
		return (this.serviceType != null) && (this.serviceType.containsKey(_serviceType)) ? (ProcessorController)this.serviceType.get(_serviceType) : new InitializeProcessor();
	}
}
