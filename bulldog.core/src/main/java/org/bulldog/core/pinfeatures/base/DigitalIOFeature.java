package org.bulldog.core.pinfeatures.base;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.IODirection;
import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.DigitalIO;
import org.bulldog.core.pinfeatures.DigitalInput;
import org.bulldog.core.pinfeatures.DigitalOutput;
import org.bulldog.core.pinfeatures.FeatureFinder;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeature;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.PinFeatureProvider;
import org.bulldog.core.pinfeatures.event.InterruptEventArgs;
import org.bulldog.core.pinfeatures.event.InterruptListener;

public class DigitalIOFeature extends AbstractPinFeature implements DigitalIO, PinFeatureProvider {

	private static final String NAME_FORMAT = "Digital IO on Pin %s";
	private DigitalOutput output;
	private DigitalInput input;
	private IODirection direction;
	private List<PinFeature> features = new ArrayList<PinFeature>();
	
	public DigitalIOFeature(Pin pin, DigitalInput input, DigitalOutput output) {
		super(pin);
		this.output = output;
		this.input = input;
		features.add(input);
		features.add(output);
	}

	@Override
	public Signal read() {
		setupInputIfNecessary();
		return input.read();
	}

	private void setupInputIfNecessary() {
		if(!input.isSetup()) {
			if(output.isSetup()) { output.teardown(); }
			input.setup(getConfiguration());
			direction = IODirection.In;
		}
	}
	
	private void setupOutputIfNecessary() {
		if(!output.isSetup()) {
			if(input.isSetup()) { input.teardown(); };
			output.setup(getConfiguration());
			direction = IODirection.Out;
		}
	}

	@Override
	public Signal readDebounced(int debounceMilliseconds) {
		setupInputIfNecessary();
		return input.readDebounced(debounceMilliseconds);
	}

	@Override
	public void disableInterrupts() {
		setupInputIfNecessary();
		input.disableInterrupts();
	}

	@Override
	public void enableInterrupts() {
		setupInputIfNecessary();
		input.enableInterrupts();
	}

	@Override
	public boolean areInterruptsEnabled() {
		setupInputIfNecessary();
		return input.areInterruptsEnabled();
	}

	@Override
	public void setInterruptDebounceMs(int milliSeconds) {
		setupInputIfNecessary();
		input.setInterruptDebounceMs(milliSeconds);
	}

	@Override
	public int getInterruptDebounceMs() {
		setupInputIfNecessary();
		return input.getInterruptDebounceMs();
	}

	@Override
	public void setInterruptTrigger(Edge edge) {
		setupInputIfNecessary();
		input.setInterruptTrigger(edge);
	}

	@Override
	public Edge getInterruptTrigger() {
		setupInputIfNecessary();
		return input.getInterruptTrigger();
	}

	@Override
	public void fireInterruptEvent(InterruptEventArgs args) {
		setupInputIfNecessary();
		input.fireInterruptEvent(args);
	}

	@Override
	public void addInterruptListener(InterruptListener listener) {
		setupInputIfNecessary();
		input.addInterruptListener(listener);
	}

	@Override
	public void removeInterruptListener(InterruptListener listener) {
		setupInputIfNecessary();
		input.removeInterruptListener(listener);
	}

	@Override
	public void clearInterruptListeners() {
		setupInputIfNecessary();
		input.clearInterruptListeners();
	}

	@Override
	public List<InterruptListener> getInterruptListeners() {
		setupInputIfNecessary();
		return input.getInterruptListeners();
	}

	@Override
	public String getName() {
		return String.format(NAME_FORMAT, getPin().getName());
	}

	@Override
	public void write(Signal signal) {
		setupOutputIfNecessary();
		output.write(signal);
	}

	@Override
	public void applySignal(Signal signal) {
		setupOutputIfNecessary();
		output.applySignal(signal);
	}

	@Override
	public Signal getAppliedSignal() {
		setupOutputIfNecessary();
		return output.getAppliedSignal();
	}

	@Override
	public void high() {
		setupOutputIfNecessary();
		output.high();
	}

	@Override
	public void low() {
		setupOutputIfNecessary();
		output.low();
	}

	@Override
	public void toggle() {
		setupOutputIfNecessary();
		output.toggle();
	}

	@Override
	public boolean isHigh() {
		setupOutputIfNecessary();
		return output.isHigh();
	}

	@Override
	public boolean isLow() {
		setupOutputIfNecessary();
		return output.isLow();
	}

	@Override
	public void startBlinking(int periodLengthMilliseconds) {
		setupOutputIfNecessary();
		output.startBlinking(periodLengthMilliseconds);
	}

	@Override
	public void startBlinking(int periodLengthMilliseconds,
			int durationMilliseconds) {
		setupOutputIfNecessary();
		output.startBlinking(periodLengthMilliseconds, durationMilliseconds);
		
	}

	@Override
	public void blinkTimes(int periodLengthMilliseconds, int times) {
		setupOutputIfNecessary();
		output.blinkTimes(periodLengthMilliseconds, times);
	}

	@Override
	public void stopBlinking() {
		setupOutputIfNecessary();
		output.stopBlinking();
	}
	
	@Override
	public boolean isBlinking() {
		setupOutputIfNecessary();
		return output.isBlinking();
	}

	@Override
	public void awaitBlinkingStopped() {
		setupOutputIfNecessary();
		output.awaitBlinkingStopped();
	}
	
	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
		if(configuration.getDesiredFeature().isAssignableFrom(input.getClass())) {
			input.setup(configuration);
		} else if(configuration.getDesiredFeature().isAssignableFrom(output.getClass())) {
			output.setup(configuration);
		}
	}

	@Override
	protected void teardownImpl() {
		if(isInputActive()) {
			input.teardown();
		}
		
		if(isOutputActive()) {
			output.teardown();
		}
	}
	
	public boolean isBlocking() {
		return getPin().getBlocker() == this || getPin().getBlocker() == output || getPin().getBlocker() == input;
	}

	@Override
	public boolean isInputActive() {
		return input.isSetup();
	}

	@Override
	public boolean isOutputActive() {
		return output.isSetup();
	}

	@Override
	public void setDirection(IODirection direction) {
		this.direction = direction;
		if(direction == IODirection.Out) {
			setupOutputIfNecessary();
		} else if(direction == IODirection.In) {
			setupInputIfNecessary();
		}
	}

	@Override
	public IODirection getDirection() {
		return this.direction;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends PinFeature> T getFeature(Class<T> featureClass) {
		return (T) FeatureFinder.findFeature(this, featureClass);
	}

	@Override
	public boolean hasFeature(Class<? extends PinFeature> featureClass) {
		return FeatureFinder.findFeature(this, featureClass) != null;
	}

	@Override
	public List<PinFeature> getFeatures() {
		return features;
	}

}
