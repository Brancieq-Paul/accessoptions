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

public class OptionImpl<S, T> implements Option<S, T> {
  protected final OptionsStorage<S> storage;
  protected final List<ModificationInputTransformer<?, ? extends T>> inputToValueTransformers;
  protected final ValueVerifier<T> valueVerifier;
  protected final OptionBinding<S, T> binding;
  protected final List<Reloader> reloaders;
  protected final Text name;
  protected final String optionId;
  protected final String description;
  protected T value;
  protected T pendingValue;
  protected final boolean enabled;

  protected OptionImpl(Builder<S, T, ?> builder) {
    Validate.notNull(builder.name, "Name must be specified");
    Validate.notNull(builder.binding, "Option binding must be specified");
    this.storage = builder.storage;
    this.name = builder.name;
    this.description = builder.description == null ? builder.name.getString() : builder.description;
    this.optionId = builder.optionId;
    this.binding = builder.binding;
    this.inputToValueTransformers = builder.inputToValueTransformers;
    this.valueVerifier = builder.valueVerifier;
    this.reloaders = List.copyOf(builder.reloaders);
    this.enabled = builder.enabled;
    reset();
  }

  @Override
  public Text getDisplayName() {
    return this.name;
  }

  @Override
  public String getId() {
    return this.optionId;
  }

  @Override
  public String getDescription() {
    return "";
  }

  @Override
  public T getPendingValue() {
    return this.pendingValue;
  }

  @Override
  public T getOriginalValue() {
    return this.value;
  }

  private <Y> Object applyTransformer(ModificationInputTransformer<Y, ? extends T> transformer, Object newValue) throws Exception {
    if (transformer.getInputType().isInstance(newValue)) {
      return transformer.apply(transformer.getInputType().cast(newValue));
    }
    return newValue;
  }

  @Override
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public void modifyPendingValue(Object newValue) throws AccessOptionsException.OptionTypeMismatch, ValueVerificationException,
      AccessOptionsException.PendingOptionNotDifferent {
    for (ModificationInputTransformer<?, ? extends T> transformer : this.inputToValueTransformers) {
      try {
        newValue = applyTransformer(transformer, newValue);
        break;
      } catch (Exception ignored) {
      }
    }
    if (value.getClass().isInstance(newValue)) {
      this.valueVerifier.accept((T) newValue);
      if (newValue.equals(this.pendingValue)) {
        throw new AccessOptionsException.PendingOptionNotDifferent(this.storage.getStorageId(), this.optionId);
      }
      this.pendingValue = (T) newValue;
    } else {
      throw new AccessOptionsException.OptionTypeMismatch(this.storage.getStorageId(), this.name.getString(), value.getClass().getTypeName(), newValue.getClass().getTypeName());
    }
  }

  @Override
  public void reset() {
    this.value = this.binding.getValue(this.storage.getData());
    this.pendingValue = this.value;
  }

  @Override
  public OptionsStorage<S> getStorage() {
    return this.storage;
  }

  @Override
  public boolean isAvailable() {
    return this.enabled;
  }

  @Override
  public boolean hasChanged() {
    return !this.value.equals(this.pendingValue);
  }

  @Override
  public void applyPendingValue() throws AccessOptionsException.PendingOptionNotDifferent {
    if (!this.hasChanged()) {
      throw new AccessOptionsException.PendingOptionNotDifferent(this.storage.getStorageId(), this.optionId);
    }
    this.binding.setValue(this.storage.getData(), this.pendingValue);
    this.value = this.pendingValue;
  }

  @Override
  public Collection<Reloader> getReloaders() {
    return this.reloaders;
  }

  public static <S, T> Builder<S, T, ?> createBuilder(@SuppressWarnings("unused") Class<T> type, OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  @SuppressWarnings({"unchecked"})
  public static class Builder<S, T, U extends Builder<S, T, ?>> {
    protected final OptionsStorage<S> storage;
    protected final String optionId;
    protected Text name;
    protected String description;
    protected OptionBinding<S, T> binding;
    protected List<ModificationInputTransformer<?, ? extends T>> inputToValueTransformers = new ArrayList<>();
    protected ValueVerifier<T> valueVerifier = (value) -> {
    };
    protected final List<Reloader> reloaders = new ArrayList<>();
    protected boolean enabled = true;

    protected Builder(OptionsStorage<S> storage, String optionId) {
      this.storage = storage;
      this.optionId = optionId;
    }

    public U setName(Text name) {
      Validate.notNull(name, "Argument must not be null");

      this.name = name;

      return (U) this;
    }

    @SuppressWarnings("unused")
    public U setDescription(String description) {
      Validate.notNull(description, "Argument must not be null");

      this.description = description;

      return (U) this;
    }

    public U setBinding(BiConsumer<S, T> setter, Function<S, T> getter) {
      Validate.notNull(setter, "Setter must not be null");
      Validate.notNull(getter, "Getter must not be null");

      this.binding = new GenericBinding<>(setter, getter);

      return (U) this;
    }


    @SuppressWarnings("unused")
    public U setBinding(OptionBinding<S, T> binding) {
      Validate.notNull(binding, "Argument must not be null");

      this.binding = binding;

      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public U addInputToValueTransformers(List<ModificationInputTransformer<?, ? extends T>> transformers) {
      Validate.notNull(transformers, "Argument must not be null");

      for (ModificationInputTransformer<?, ? extends T> transformer : transformers) {
        Validate.notNull(transformer, "Argument must not be null");
      }

      this.inputToValueTransformers.addAll(transformers);

      return (U) this;
    }

    @SafeVarargs
    @SuppressWarnings("UnusedReturnValue")
    public final U addInputToValueTransformers(ModificationInputTransformer<?, ? extends T>... transformers) {
      Validate.notNull(transformers, "Argument must not be null");

      this.addInputToValueTransformers(List.of(transformers));

      return (U) this;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public U setValueVerifier(ValueVerifier<T> valueVerifier) {
      Validate.notNull(valueVerifier, "Argument must not be null");

      this.valueVerifier = valueVerifier;

      return (U) this;
    }

    public U setReloaders(Reloader... reloaders) {
      Validate.notNull(reloaders, "Argument must not be null");

      this.reloaders.addAll(List.of(reloaders));

      return (U) this;
    }

    @SuppressWarnings("unused")
    public U setEnabled(boolean value) {
      this.enabled = value;

      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public OptionImpl<S, T> build() {
      return new OptionImpl<>(this);
    }
  }
}
