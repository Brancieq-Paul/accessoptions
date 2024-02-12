package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.binding.OptionBinding;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.function.Function;

public class RangedOptionImpl<S> extends OptionImpl<S, Number> implements Ranged {

  protected final Number min;
  protected final Number max;

  protected RangedOptionImpl(OptionsStorage<S> storage,
                     String optionId,
                     Text name,
                     OptionBinding<S, Number> binding,
                     Function<String, Number> valueFromString,
                     ValueVerifier<Number> valueVerifier,
                     Collection<Reloader> reloaders,
                     boolean enabled,
                     Number min,
                     Number max) {
    super(storage, optionId, name, binding, valueFromString, valueVerifier, reloaders, enabled);
    this.min = min;
    this.max = max;
  }

  @Override
  public Number getMin() {
    return min;
  }

  @Override
  public Number getMax() {
    return max;
  }

  public static class Builder<S> extends OptionImpl.Builder<S, Number> {
    protected Number min;
    protected Number max;

    protected ValueVerifier<Number> valueVerifier = number -> {
      if (number.doubleValue() < min.doubleValue() || number.doubleValue() > max.doubleValue()) {
        throw new ValueVerificationException.ValueNotInRange(storage.getStorageId(), optionId, number, min, max);
      }
    };

    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
    }

    public Builder<S> setRange(Number min, Number max) {
      this.min = min;
      this.max = max;
      return this;
    }

    @Override
    public RangedOptionImpl<S> build() {
      super.build();
      Validate.notNull(min, "Min value must be set");
      Validate.notNull(max, "Max value must be set");

      return new RangedOptionImpl<>(storage, optionId, name, binding, valueFromString, valueVerifier, reloaders, enabled, min, max);
    }
  }
}
