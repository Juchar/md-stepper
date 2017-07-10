package org.vaadin.addons.md_stepper.iterator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Abstract observable iterator that provides default implementations for adding and removing
 * iteration listeners.
 *
 * @param <E>
 *     The type of the element to be iterated
 */
public abstract class AbstractObservableIterator<E>
    implements ObservableIterator<E> {

  protected final Collection<StartListener<E>> startListeners;
  protected final Collection<EndListener<E>> endListeners;
  protected final Collection<ElementChangeListener<E>> elementChangeListeners;
  protected final Collection<PreviousListener<E>> previousListeners;
  protected final Collection<NextListener<E>> nextListeners;
  protected final Collection<MoveToListener<E>> moveToListeners;
  protected final Collection<SkipListener<E>> skipListeners;

  protected AbstractObservableIterator() {
    this.startListeners = new HashSet<>();
    this.endListeners = new HashSet<>();
    this.elementChangeListeners = new HashSet<>();
    this.previousListeners = new HashSet<>();
    this.nextListeners = new HashSet<>();
    this.moveToListeners = new HashSet<>();
    this.skipListeners = new HashSet<>();

    addNextListener(this::notifyElementChange);
    addPreviousListener(this::notifyElementChange);
    addMoveToListener(this::notifyElementChange);
    addSkipListener(this::notifyElementChange);
  }

  @Override
  public boolean addNextListener(NextListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return nextListeners.add(listener);
  }

  @Override
  public boolean addPreviousListener(PreviousListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return previousListeners.add(listener);
  }

  @Override
  public boolean removePreviousListener(PreviousListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return previousListeners.remove(listener);
  }

  @Override
  public boolean addMoveToListener(MoveToListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return moveToListeners.add(listener);
  }

  @Override
  public boolean removeMoveToListener(MoveToListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return moveToListeners.remove(listener);
  }

  @Override
  public boolean addSkipListener(SkipListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return skipListeners.add(listener);
  }

  @Override
  public boolean removeSkipListener(SkipListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return skipListeners.remove(listener);
  }

  @Override
  public boolean removeNextListener(NextListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return nextListeners.remove(listener);
  }

  @Override
  public boolean addStartListener(StartListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return startListeners.add(listener);
  }

  @Override
  public boolean removeStartListener(StartListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return startListeners.remove(listener);
  }

  @Override
  public boolean addEndListener(EndListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return endListeners.add(listener);
  }

  @Override
  public boolean removeEndListener(EndListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return endListeners.remove(listener);
  }

  @Override
  public boolean addElementChangeListener(ElementChangeListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return elementChangeListeners.add(listener);
  }

  @Override
  public boolean removeElementChangeListener(ElementChangeListener<E> listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return elementChangeListeners.remove(listener);
  }

  /**
   * Notify about the iteration start
   *
   * @param event
   *     The event that will be triggered after the start
   */
  protected void notifyStart(IteratorListener.IteratorEvent<E> event) {
    startListeners.forEach(l -> l.onStart(event));
  }

  /**
   * Notify about the iteration end.
   *
   * @param event
   *     The event that would be triggered if the iterator would not end
   */
  protected void notifyEnd(IteratorListener.IteratorEvent<E> event) {
    endListeners.forEach(l -> l.onEnd(event));
  }

  /**
   * Notify about an iteration backwards.
   *
   * @param oldElement
   *     The current element before the iteration
   * @param newElement
   *     The current element after the iteration
   */
  protected void notifyPrevious(E oldElement, E newElement) {
    PreviousListener.PreviousEvent<E> event
        = new PreviousListener.PreviousEvent<>(this, oldElement, newElement);
    previousListeners.forEach(l -> l.onPrevious(event));
  }

  /**
   * Notify about an iteration forwards.
   *
   * @param oldElement
   *     The current element before the iteration
   * @param newElement
   *     The current element after the iteration
   */
  protected void notifyNext(E oldElement, E newElement) {
    NextListener.NextEvent<E> event
        = new NextListener.NextEvent<>(this, oldElement, newElement);
    nextListeners.forEach(l -> l.onNext(event));
  }

  /**
   * Notify about an iteration to a certain position.
   *
   * @param oldElement
   *     The current element before the iteration
   * @param newElement
   *     The current element after the iteration
   */
  protected void notifyMoveTo(E oldElement, E newElement) {
    MoveToListener.MoveToEvent<E> event
        = new MoveToListener.MoveToEvent<>(this, oldElement, newElement);
    moveToListeners.forEach(l -> l.onMoveTo(event));
  }

  /**
   * Notify about an iteration skip.
   *
   * @param oldElement
   *     The current element before the iteration
   * @param newElement
   *     The current element after the iteration
   */
  protected void notifySkip(E oldElement, E newElement) {
    SkipListener.SkipEvent<E> event
        = new SkipListener.SkipEvent<>(this, oldElement, newElement);
    skipListeners.forEach(l -> l.onSkip(event));
  }

  /**
   * Notify about an element change.
   *
   * @param event
   *     The event that caused the element change
   */
  protected void notifyElementChange(IterationListener.IterationEvent<E> event) {
    if (!Objects.equals(event.getPrevious(), event.getCurrent())) {
      elementChangeListeners.forEach(l -> l.onElementChange(event));
    }
  }
}
