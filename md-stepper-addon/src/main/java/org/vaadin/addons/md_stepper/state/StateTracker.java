package org.vaadin.addons.md_stepper.state;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Tracker to track the state of elements.
 *
 * @param <E>
 *     The type of elements to track the state for
 *
 * @see State
 */
public class StateTracker<E extends Serializable> implements Serializable {

  private final Map<E, State> states;

  /**
   * Construct a new tracker.
   */
  public StateTracker() {
    this.states = new HashMap<>();
  }

  /**
   * Remove all elements and states from the tracker.
   */
  public void clear() {
    states.clear();
  }

  /**
   * Set the state of an element. Triggers a state change event if the state changed.
   *
   * @param element
   *     The element to set the state for
   * @param state
   *     The state to set
   */
  public void setState(E element, State state) {
    Objects.requireNonNull(element, "Element may not be null");
    Objects.requireNonNull(state, "State may not be null");

    State oldState = getState(element);
    if (!Objects.equals(oldState, state)) {
      states.put(element, state);
    }
  }

  /**
   * Get the state of an element.
   *
   * @param element
   *     The element to get the state for
   *
   * @return The state of the element
   */
  public State getState(E element) {
    Objects.requireNonNull(element, "Element may not be null");
    return Optional.ofNullable(states.get(element)).orElse(State.UNVISITED);
  }

  /**
   * Remove the specified element from the state tracker.
   *
   * @param element
   *     The element to remove
   */
  public void remove(E element) {
    states.remove(element);
  }

  /**
   * Enumeration for different element states.
   */
  public enum State {
    UNVISITED,
    VISITED
  }
}
