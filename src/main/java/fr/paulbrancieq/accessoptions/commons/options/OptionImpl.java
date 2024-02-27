package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.binding.GenericBinding;
import fr.paulbrancieq.accessoptions.commons.binding.OptionBinding;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

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
  protected final List<Reloader> reloaders = new ArrayList<>();
  protected final String displayName;
  protected final String optionId;
  protected final String description;
  protected T value;
  protected T pendingValue;
  protected final boolean enabled;
  protected final OptionsAccessHandler handler;

  protected OptionImpl(Builder<S, T, ?> builder) {
    Validate.notNull(builder.binding, "Option binding must be specified");
    Validate.notNull(builder.storage, "Option storage must be specified");
    this.storage = builder.storage;
    this.displayName = builder.displayName == null ? builder.optionId : builder.displayName;
    this.description = builder.description == null ? this.displayName : builder.description;
    this.optionId = builder.optionId;
    this.binding = builder.binding;
    this.inputToValueTransformers = builder.inputToValueTransformers;
    this.valueVerifier = builder.valueVerifier;
    this.handler = this.storage.getOptionsAccessHandler();
    builder.reloadersBuilders.forEach(reloaderBuilder -> reloaderBuilder.setOptionsAccessHandler(handler));
    for (GenericReloader.Builder<?> reloaderBuilder : builder.reloadersBuilders) {
      try {
        this.reloaders.add(reloaderBuilder.build());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    this.enabled = builder.enabled;
    reset();
  }

  @Override
  public String getDisplayName() {
    return this.displayName;
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
      throw new AccessOptionsException.OptionTypeMismatch(this.storage.getStorageId(), this.displayName, value.getClass().getTypeName(), newValue.getClass().getTypeName());
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

  @SuppressWarnings("unused")
  public static <S, T> Builder<S, T, ?> createBuilder(@SuppressWarnings("unused") Class<T> optionRawType, @SuppressWarnings("unused") Class<S> storageRawType, String optionId) {
    return new Builder<>(optionId);
  }

  @SuppressWarnings({"unchecked"})
  public static class Builder<S, T, U extends Builder<S, T, ?>> {
    protected OptionsStorage<S> storage;
    protected final String optionId;
    protected String displayName;
    protected String description;
    protected OptionBinding<S, T> binding;
    protected List<ModificationInputTransformer<?, ? extends T>> inputToValueTransformers = new ArrayList<>();
    protected ValueVerifier<T> valueVerifier = (value) -> {
    };
    protected final List<GenericReloader.Builder<?>> reloadersBuilders = new ArrayList<>();
    protected boolean enabled = true;

    protected Builder(String optionId) {
      this.optionId = optionId;
    }

    public U setStorage(@NotNull OptionsStorage<S> storage) {
      Validate.notNull(storage, "Argument must not be null");

      this.storage = storage;

      return (U) this;
    }

    public U setDisplayName(@NotNull String displayName) {
      Validate.notNull(displayName, "Argument must not be null");

      this.displayName = Text.translatable(displayName).getString();

      return (U) this;
    }

    @SuppressWarnings("unused")
    public U setDescription(@NotNull String description) {
      Validate.notNull(description, "Argument must not be null");

      this.description = Text.translatable(description).getString();

      return (U) this;
    }

    public U setBinding(@NotNull BiConsumer<S, T> setter, Function<S, T> getter) {
      Validate.notNull(setter, "Setter must not be null");
      Validate.notNull(getter, "Getter must not be null");

      this.binding = new GenericBinding<>(setter, getter);

      return (U) this;
    }


    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public U setBinding(@NotNull OptionBinding<S, T> binding) {
      Validate.notNull(binding, "Argument must not be null");

      this.binding = binding;

      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public U addInputToValueTransformers(@NotNull List<ModificationInputTransformer<?, ? extends T>> transformers) {
      Validate.notNull(transformers, "Argument must not be null");

      for (ModificationInputTransformer<?, ? extends T> transformer : transformers) {
        Validate.notNull(transformer, "Argument must not be null");
      }

      this.inputToValueTransformers.addAll(transformers);

      return (U) this;
    }

    @SafeVarargs
    @SuppressWarnings("UnusedReturnValue")
    public final U addInputToValueTransformers(@NotNull ModificationInputTransformer<?, ? extends T>... transformers) {
      Validate.notNull(transformers, "Argument must not be null");

      this.addInputToValueTransformers(List.of(transformers));

      return (U) this;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public U setValueVerifier(@NotNull ValueVerifier<T> valueVerifier) {
      Validate.notNull(valueVerifier, "Argument must not be null");

      this.valueVerifier = valueVerifier;

      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public U setReloadersBuilders(@NotNull Collection<GenericReloader.Builder<?>> reloadersBuilders) {
      Validate.notNull(reloadersBuilders, "Argument must not be null");

      this.reloadersBuilders.addAll(reloadersBuilders);

      return (U) this;
    }

    public U setReloaders(@NotNull GenericReloader.Builder<?>... reloaders) {
      Validate.notNull(reloaders, "Argument must not be null");

      this.setReloadersBuilders(List.of(reloaders));

      return (U) this;
    }

    @SuppressWarnings({"unused","UnusedReturnValue"})
    public U setEnabled(@NotNull Boolean value) {
      this.enabled = value;

      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public OptionImpl<S, T> build() {
      return new OptionImpl<>(this);
    }
  }
}
