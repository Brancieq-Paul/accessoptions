package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.binding.GenericBinding;
import fr.paulbrancieq.accessoptions.commons.binding.OptionBinding;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class OptionImpl<S, T> implements Option<T> {
  protected final OptionsStorage<S> storage;
  protected final Function<String, T> valueFromString;
  protected final ValueVerifier<T> valueVerifier;
  protected final OptionBinding<S, T> binding;
  protected final List<Reloader> reloaders;
  protected final Text name;
  protected final String optionId;
  protected T value;
  protected T modifiedValue;
  protected final boolean enabled;

  protected OptionImpl(Builder<S, T> builder) {
    Validate.notNull(builder.name, "Name must be specified");
    Validate.notNull(builder.binding, "Option binding must be specified");
    this.storage = builder.storage;
    this.name = builder.name;
    this.optionId = builder.optionId;
    this.binding = builder.binding;
    this.valueFromString = builder.valueFromString;
    this.valueVerifier = builder.valueVerifier;
    this.reloaders = List.copyOf(builder.reloaders);
    this.enabled = builder.enabled;
  }

  @Override
  public Text getName() {
    return this.name;
  }

  @Override
  public T getValue() {
    return this.modifiedValue;
  }

  @Override
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public void setValue(Object newValue) throws AccessOptionsException.OptionTypeMismatch, ValueVerificationException {
    if (newValue instanceof String && !value.getClass().isAssignableFrom(String.class)) {
      try {
        newValue = getValueFromString((String) newValue);
      } catch (Exception e) {
        throw new AccessOptionsException.OptionTypeMismatch(this.storage.getStorageId(), this.name.getString(), value.getClass().getTypeName(), newValue.getClass().getTypeName());
      }
    }
    if (value.getClass().isInstance(newValue)) {
      this.valueVerifier.accept((T) newValue);
      this.modifiedValue = (T) newValue;
    } else {
      throw new AccessOptionsException.OptionTypeMismatch(this.storage.getStorageId(), this.name.getString(), value.getClass().getTypeName(), newValue.getClass().getTypeName());
    }
  }

  protected T getValueFromString(String newValue) {
    return valueFromString.apply(newValue);
  }

  @Override
  public void reset() {
    this.value = this.binding.getValue(this.storage.getData());
    this.modifiedValue = this.value;
  }

  @Override
  public OptionsStorage<?> getStorage() {
    return this.storage;
  }

  @Override
  public boolean isAvailable() {
    return this.enabled;
  }

  @Override
  public boolean hasChanged() {
    return !this.value.equals(this.modifiedValue);
  }

  @Override
  public void applyChanges() throws AccessOptionsException.OptionNotModified {
    if (!this.hasChanged()) {
      throw new AccessOptionsException.OptionNotModified(this.storage.getStorageId(), this.optionId);
    }
    this.binding.setValue(this.storage.getData(), this.modifiedValue);
    this.value = this.modifiedValue;
  }

  @Override
  public Collection<Reloader> getReloaders() {
    return this.reloaders;
  }

  public static <S, T> Builder<S, T> createBuilder(@SuppressWarnings("unused") Class<T> type, OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S, T> {
    protected final OptionsStorage<S> storage;
    protected final String optionId;
    protected Text name;
    protected OptionBinding<S, T> binding;
    @SuppressWarnings("unchecked")
    protected Function<String, T> valueFromString = (value) -> (T) value;
    protected ValueVerifier<T> valueVerifier = (value) -> {
    };
    protected final List<Reloader> reloaders = new ArrayList<>();
    protected boolean enabled = true;

    protected Builder(OptionsStorage<S> storage, String optionId) {
      this.storage = storage;
      this.optionId = optionId;
    }

    public Builder<S, T> setName(Text name) {
      Validate.notNull(name, "Argument must not be null");

      this.name = name;

      return this;
    }

    public Builder<S, T> setBinding(BiConsumer<S, T> setter, Function<S, T> getter) {
      Validate.notNull(setter, "Setter must not be null");
      Validate.notNull(getter, "Getter must not be null");

      this.binding = new GenericBinding<>(setter, getter);

      return this;
    }


    @SuppressWarnings("unused")
    public Builder<S, T> setBinding(OptionBinding<S, T> binding) {
      Validate.notNull(binding, "Argument must not be null");

      this.binding = binding;

      return this;
    }

    public Builder<S, T> setValueFromString(Function<String, T> valueFromString) {
      Validate.notNull(valueFromString, "Argument must not be null");

      this.valueFromString = valueFromString;

      return this;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public Builder<S, T> setValueVerifier(ValueVerifier<T> valueVerifier) {
      Validate.notNull(valueVerifier, "Argument must not be null");

      this.valueVerifier = valueVerifier;

      return this;
    }

    public Builder<S, T> setReloaders(Reloader... reloaders) {
      Validate.notNull(reloaders, "Argument must not be null");

      this.reloaders.addAll(List.of(reloaders));

      return this;
    }

    @SuppressWarnings("unused")
    public Builder<S, T> setEnabled(boolean value) {
      this.enabled = value;

      return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public OptionImpl<S, T> build() {
      return new OptionImpl<>(this);
    }
  }
}
