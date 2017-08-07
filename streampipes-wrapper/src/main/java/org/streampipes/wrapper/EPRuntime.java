package org.streampipes.wrapper;


public abstract class EPRuntime { // routing container

	protected final EPEngine<?> engine;

	protected final OutputCollector collector;


	public EPRuntime(RuntimeParameters<?> params)
	{
		this.collector = new OutputCollector();
		engine = params.getPreparedEngine(this, params.getEngineParameters().getGraph(), collector);

	}
	
	public EPEngine<?> getEngine() {
		return engine;
	}

	public OutputCollector getOutputCollector() {
		return collector;
	}

	public void discard() {
		preDiscard();
		engine.discard();
		postDiscard();
	}
	
	public abstract void initRuntime();
	
	public abstract void preDiscard();
	
	public abstract void postDiscard();
}