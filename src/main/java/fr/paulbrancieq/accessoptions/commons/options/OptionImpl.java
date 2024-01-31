package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.binding.GenericBinding;
import fr.paulbrancieq.accessoptions.commons.binding.OptionBinding;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueModificationException;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class OptionImpl<S, T> implements Option<T> {
  private final OptionsStorage<S> storage;

  private final Function<String, T> valueFromString;

  private final OptionBinding<S, T> binding;

  // TODO
  // private final EnumSet<OptionFlag> flags;

  private final Text name;

  private final String optionId;
  private final Text tooltip;
  private T value;
  private T modifiedValue;

  private final boolean enabled;

  private OptionImpl(OptionsStorage<S> storage,
                     String optionId,
                     Text name,
                     Text tooltip,
                     OptionBinding<S, T> binding,
                     Function<String, T> valueFromString,
                     boolean enabled) {
    // TODO add as method argument
    // EnumSet<OptionFlag> flags
    this.storage = storage;
    this.name = name;
    this.optionId = optionId;
    this.tooltip = tooltip;
    this.binding = binding;
    this.valueFromString = valueFromString;
    // TODO
    // this.flags = flags;
    this.enabled = enabled;
  }

  @Override
  public Text getName() {
    return this.name;
  }

  @Override
  public Text getTooltip() {
    return this.tooltip;
  }

  @Override
  public T getValue() {
    return this.modifiedValue;
  }

  @Override
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public void setValue(Object newValue) throws ValueModificationException.OptionTypeMismatch {
    if (newValue instanceof String && !value.getClass().isAssignableFrom(String.class)) {
      try {
        newValue = this.valueFromString.apply((String) newValue);
      }
      catch (Exception e) {
        throw new ValueModificationException.OptionTypeMismatch(this.storage.getModId(), this.name.getString(), value.getClass().getTypeName(), newValue.getClass().getTypeName());
      }
    }
    if (value.getClass().isInstance(newValue)) {
      this.modifiedValue = (T) newValue;
    }
    else {
      throw new ValueModificationException.OptionTypeMismatch(this.storage.getModId(), this.name.getString(), value.getClass().getTypeName(), newValue.getClass().getTypeName());
    }
  }

  @Override
  public T getValueFromString(String newValue) throws ValueModificationException.OptionTypeMismatch {
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
  public void applyChanges() throws ValueModificationException.OptionNotModified {
    if (!this.hasChanged()) {
      throw new ValueModificationException.OptionNotModified(this.storage.getModId(), this.optionId);
    }
    this.binding.setValue(this.storage.getData(), this.modifiedValue);
    this.value = this.modifiedValue;
  }

  // TODO
  // @Override
  // public Collection<OptionFlag> getFlags() {
  //   return this.flags;
  // }

  public static <S, T> Builder<S, T> createBuilder(@SuppressWarnings("unused") Class<T> type, OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S, T> {
    private final OptionsStorage<S> storage;
    private final String optionId;
    private Text name;
    private Text tooltip;
    private OptionBinding<S, T> binding;
    @SuppressWarnings("unchecked")
    private Function<String, T> valueFromString = (value) -> (T) value;
    // TODO
    // private final EnumSet<OptionFlag> flags = EnumSet.noneOf(OptionFlag.class);
    private boolean enabled = true;

    private Builder(OptionsStorage<S> storage, String optionId) {
      this.storage = storage;
      this.optionId = optionId;
    }

    public Builder<S, T> setName(Text name) {
      Validate.notNull(name, "Argument must not be null");

      this.name = name;

      return this;
    }

    public Builder<S, T> setTooltip(Text tooltip) {
      Validate.notNull(tooltip, "Argument must not be null");

      this.tooltip = tooltip;

      return this;
    }

    public Builder<S, T> setBinding(BiConsumer<S, T> setter, Function<S, T> getter) {
      Validate.notNull(setter, "Setter must not be null");
      Validate.notNull(getter, "Getter must not be null");

      this.binding = new GenericBinding<>(setter, getter);

      return this;
    }


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

    public Builder<S, T> setEnabled(boolean value) {
      this.enabled = value;

      return this;
    }

    // TODO
    //public Builder<S, T> setFlags(OptionFlag... flags) {
    //  Collections.addAll(this.flags, flags);
    //
    //  return this;
    //}

    public OptionImpl<S, T> build() {
      Validate.notNull(this.name, "Name must be specified");
      Validate.notNull(this.tooltip, "Tooltip must be specified");
      Validate.notNull(this.binding, "Option binding must be specified");

      return new OptionImpl<>(this.storage, this.optionId, this.name, this.tooltip, this.binding, this.valueFromString, this.enabled);
    }
  }
}
